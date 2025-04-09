package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public Page<Loan> findPage(Long idGame, Long idClient, LocalDate filterDate, Pageable pageable) {
        LoanSpecification gameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", idGame));
        LoanSpecification clientSpec = new LoanSpecification(new SearchCriteria("client.id", ":", idClient));
        LoanSpecification dateSpec = new LoanSpecification(new SearchCriteria("filterDate", ":", filterDate));

        Specification<Loan> spec = Specification.where(gameSpec).and(clientSpec).and(dateSpec);

        return loanRepository.findAll(spec, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto dto) {
        if (dto.getReturnDate().isBefore(dto.getLoanDate())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");
        }

        long daysBetween = ChronoUnit.DAYS.between(dto.getLoanDate(), dto.getReturnDate());
        if (daysBetween > 14) {
            throw new IllegalArgumentException("El período máximo de préstamo son 14 días.");
        }

        if (loanRepository.existsByGameIdAndDateRange(dto.getGame().getId(), dto.getLoanDate(), dto.getReturnDate())) {
            throw new IllegalArgumentException("El juego " + dto.getGame().getTitle() + " ya está prestado a " + dto.getClient().getName() + " en el rango de fechas seleccionado.");
        }

        long activeLoansCount = loanRepository.countActiveLoansByClient(dto.getClient().getId(), dto.getLoanDate(), dto.getReturnDate());
        if (activeLoansCount >= 2) {
            throw new IllegalArgumentException("El cliente " + dto.getClient().getName() + " no puede tener más de 1 juego prestado simultáneamente.");
        }

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Loan> findAll() {
        return (List<Loan>) this.loanRepository.findAll();
    }
}
