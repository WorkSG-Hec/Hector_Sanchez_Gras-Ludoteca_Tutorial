package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    public static final Long EXISTS_LOAN_ID = 1L;
    public static final Long NOT_EXISTS_LOAN_ID = 0L;

    public static final Long EXISTS_GAME_ID = 1L;
    public static final Long OTHER_CLIENT_ID = 2L;

    public static final int ALL_LOANS_IN_DB = 6;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<LoanDto>> responseType = new ParameterizedTypeReference<>() {
    };

    private String url() {
        return LOCALHOST + port + SERVICE_PATH;
    }

    @Test
    public void findAllShouldReturnAllLoansInDB() {
        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(url(), HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(ALL_LOANS_IN_DB, response.getBody().size());
    }

    @Test
    public void saveValidLoanShouldCreateNewLoan() {
        LoanDto dto = new LoanDto();

        dto.setLoanDate(LocalDate.of(2025, 4, 11));
        dto.setReturnDate(LocalDate.of(2025, 4, 13));

        ClientDto client = new ClientDto();
        client.setId(OTHER_CLIENT_ID);

        GameDto game = new GameDto();
        game.setId(5L);

        dto.setClient(client);
        dto.setGame(game);

        restTemplate.exchange(url(), HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<LoanDto>> response = restTemplate.exchange(url(), HttpMethod.GET, null, responseType);
        assertEquals(ALL_LOANS_IN_DB + 1, response.getBody().size());
    }

    @Test
    public void saveLoanWithInvalidDateShouldReturnError() {
        LoanDto dto = new LoanDto();

        dto.setLoanDate(LocalDate.of(2025, 4, 10));
        dto.setReturnDate(LocalDate.of(2025, 4, 5));

        dto.setClient(new ClientDto() {{
            setId(OTHER_CLIENT_ID);
        }});

        dto.setGame(new GameDto() {{
            setId(3L);
        }});

        ResponseEntity<?> response = restTemplate.exchange(url(), HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveLoanLongerThan14DaysShouldReturnError() {
        LoanDto dto = new LoanDto();

        dto.setLoanDate(LocalDate.of(2025, 4, 4));
        dto.setReturnDate(LocalDate.of(2025, 4, 20));

        dto.setClient(new ClientDto() {{
            setId(OTHER_CLIENT_ID);
        }});

        dto.setGame(new GameDto() {{
            setId(5L);
        }});

        ResponseEntity<?> response = restTemplate.exchange(url(), HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveLoanForAlreadyBorrowedGameShouldReturnError() {
        LoanDto dto = new LoanDto();

        dto.setLoanDate(LocalDate.of(2025, 4, 4));
        dto.setReturnDate(LocalDate.of(2025, 4, 10));

        dto.setClient(new ClientDto() {{
            setId(3L);
        }});

        dto.setGame(new GameDto() {{
            setId(EXISTS_GAME_ID);
        }});

        ResponseEntity<?> response = restTemplate.exchange(url(), HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteExistsLoanShouldRemoveIt() {
        ResponseEntity<Void> response = restTemplate.exchange(url() + "/" + EXISTS_LOAN_ID, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<List<LoanDto>> afterDelete = restTemplate.exchange(url(), HttpMethod.GET, null, responseType);
        assertEquals(ALL_LOANS_IN_DB - 1, afterDelete.getBody().size());
    }

    @Test
    public void deleteNotExistsLoanShouldReturnError() {
        ResponseEntity<?> response = restTemplate.exchange(url() + "/" + NOT_EXISTS_LOAN_ID, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
