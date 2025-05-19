package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Author get(Long id) {

        return this.authorRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Author> findPage(AuthorSearchDto dto) {

        return this.authorRepository.findAll(dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, AuthorDto data) {
        String trimmedName = data.getName() != null ? data.getName().trim() : "";
        String trimmedNationality = data.getNationality() != null ? data.getNationality().trim() : "";
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("El nombre del autor no puede estar vacío o ser solo espacios");
        }
        if (trimmedNationality.isEmpty()) {
            throw new IllegalArgumentException("La nacionalidad del autor no puede estar vacía o ser solo espacios");
        }

        Author author;

        if (id == null) {
            author = new Author();
        } else {
            author = this.get(id);
        }

        BeanUtils.copyProperties(data, author, "id");

        author.setName(trimmedName);
        author.setNationality(trimmedNationality);
        this.authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.authorRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Author> findAll() {

        return (List<Author>) this.authorRepository.findAll();
    }
}
