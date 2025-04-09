package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END " + "FROM Loan l " + "WHERE l.game.id = :gameId " + "AND (l.loanDate <= :returnDate AND l.returnDate >= :loanDate)")
    boolean existsByGameIdAndDateRange(@Param("gameId") Long gameId, @Param("loanDate") LocalDate loanDate, @Param("returnDate") LocalDate returnDate);

    @Query("SELECT COUNT(1) " + "FROM Loan l " + "WHERE l.client.id = :clientId " + "AND (l.loanDate <= :returnDate AND l.returnDate >= :loanDate)")
    long countActiveLoansByClient(@Param("clientId") Long clientId, @Param("loanDate") LocalDate loanDate, @Param("returnDate") LocalDate returnDate);

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    Page<Loan> findAll(Specification<Loan> spec, Pageable pageable);

}
