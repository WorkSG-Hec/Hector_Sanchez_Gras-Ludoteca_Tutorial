package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ccsw
 *
 */
public interface LoanService {
    /**
     * Recupera un {@link Loan} a partir de su ID
     *
     * @param id PK de la entidad
     * @return {@link Loan}
     */
    Loan get(Long id);

    /**
     * Método para recuperar todos los {@link Loan}
     *
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findAll();

    /**
     * Recupera los préstamos filtrando opcionalmente por título y/o cliente y/o fecha
     *
     * @param idGame PK del juego
     * @param idClient PK del cliente
     * @param filterDate fecha del filtro
     * @return {@link List} de {@link Loan}
     */
    List<Loan> find(Long idGame, Long idClient, LocalDate filterDate);

    /**
     * Guarda un préstamo.
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto);
}
