package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private GameService gameService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private LoanServiceImpl loanService;

    private LoanDto dto;

    @BeforeEach
    public void setup() {
        dto = new LoanDto();
        dto.setLoanDate(LocalDate.of(2024, 1, 1));
        dto.setReturnDate(LocalDate.of(2024, 1, 10));

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        gameDto.setTitle("Juego test");
        dto.setGame(gameDto);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setName("Cliente test");
        dto.setClient(clientDto);
    }

    @Test
    public void saveShouldThrowWhenReturnDateIsBeforeLoanDate() {
        dto.setReturnDate(dto.getLoanDate().minusDays(1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loanService.save(null, dto));
        assertEquals("La fecha de fin no puede ser anterior a la de inicio.", exception.getMessage());
    }

    @Test
    public void saveShouldThrowWhenLoanPeriodIsTooLong() {
        dto.setReturnDate(dto.getLoanDate().plusDays(15));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loanService.save(null, dto));
        assertEquals("El período máximo de préstamo son 14 días.", exception.getMessage());
    }

    @Test
    public void saveShouldThrowIfGameAlreadyLoaned() {
        Loan existingLoan = new Loan();
        Client client = new Client();
        client.setName("Cliente1");
        existingLoan.setClient(client);
        existingLoan.setLoanDate(LocalDate.of(2024, 4, 10));
        existingLoan.setReturnDate(LocalDate.of(2024, 4, 12));

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);
        clientDto.setName("Cliente1");

        GameDto gameDto = new GameDto();
        gameDto.setId(1L);
        gameDto.setTitle("Catan");

        LoanDto dto = new LoanDto();
        dto.setClient(clientDto);
        dto.setGame(gameDto);
        dto.setLoanDate(LocalDate.of(2024, 4, 10));
        dto.setReturnDate(LocalDate.of(2024, 4, 12));

        when(loanRepository.findConflictingLoansForGame(anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.singletonList(existingLoan));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loanService.save(null, dto));
        assertTrue(exception.getMessage().contains("ya está prestado a"));
    }

    @Test
    public void saveShouldThrowIfClientAlreadyHasLoan() {
        when(loanRepository.findConflictingLoansForGame(anyLong(), any(), any())).thenReturn(Collections.emptyList());

        when(loanRepository.countActiveLoansByClient(anyLong(), any(), any())).thenReturn(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loanService.save(null, dto));
        assertTrue(exception.getMessage().contains("no puede tener más de 1 juego prestado"));
    }

    @Test
    public void saveShouldInsertLoanWhenValid() {
        when(loanRepository.findConflictingLoansForGame(anyLong(), any(), any())).thenReturn(Collections.emptyList());

        when(loanRepository.countActiveLoansByClient(anyLong(), any(), any())).thenReturn(0L);

        when(gameService.get(1L)).thenReturn(mock(com.ccsw.tutorial.game.model.Game.class));
        when(clientService.get(1L)).thenReturn(mock(com.ccsw.tutorial.client.model.Client.class));

        loanService.save(null, dto);

        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void getShouldReturnLoan() {
        Loan loan = mock(Loan.class);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        Loan result = loanService.get(1L);

        assertNotNull(result);
        verify(loanRepository).findById(1L);
    }

    @Test
    public void getShouldReturnNullIfNotFound() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        Loan result = loanService.get(1L);

        assertNull(result);
    }

    @Test
    public void deleteShouldThrowIfLoanDoesNotExist() {
        when(loanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> loanService.delete(1L));
    }

    @Test
    public void deleteShouldCallRepositoryIfExists() throws Exception {
        Loan loan = mock(Loan.class);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.delete(1L);

        verify(loanRepository).deleteById(1L);
    }

    @Test
    public void findAllShouldReturnList() {
        when(loanRepository.findAll()).thenReturn(Collections.singletonList(mock(Loan.class)));

        var result = loanService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findPageShouldBuildCorrectSpecsAndReturnPage() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Loan> mockPage = new PageImpl<>(Collections.singletonList(mock(Loan.class)));

        when(loanRepository.findAll(any(), eq(pageable))).thenReturn(mockPage);

        Page<Loan> result = loanService.findPage(1L, 2L, LocalDate.now(), pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}
