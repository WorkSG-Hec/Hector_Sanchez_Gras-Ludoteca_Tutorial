package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.springframework.data.domain.Page;

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
     * Recupera un listado paginado de {@link Loan} filtrando opcionalmente por título y/o cliente y/o fecha
     *
     * @param idGame PK del juego
     * @param idClient PK del cliente
     * @param filterDate fecha del filtro
     * @return {@link Page} de {@link Loan}
     */
    Page<Loan> findPage(Long idGame, Long idClient, LocalDate filterDate);

    /**
     * Metodo para guardar un {@link Loan}.
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, LoanDto dto);

    /**
     * Metodo para eliminar un {@link Loan}.
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Método para recuperar todos los {@link Loan}
     *
     * @return {@link List} de {@link Loan}
     */
    List<Loan> findAll();
}
