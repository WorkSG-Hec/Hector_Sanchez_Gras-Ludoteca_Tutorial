package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {
        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> find(Long idGame, Long idClient, LocalDate filterDate) {
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", idClient));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("loanDate", ":", filterDate));

        Specification<Loan> spec = Specification.where(gameSpec).and(clientSpec).and(dateSpec);

        return loanRepository.findAll(spec);
    }

    @Override
    public void save(Long id, LoanDto dto) {
        Loan loan;

        if (id == null) {
            loan = new Loan();
        } else {
            loan = loanRepository.findById(id).orElse(null);
        }

        BeanUtils.copyProperties(dto, loan, "id", "game", "client");

        loan.setGame(gameService.get(dto.getGame().getId()));
        loan.setClient(clientService.get(dto.getClient().getId()));

        this.loanRepository.save(loan);
    }
}
