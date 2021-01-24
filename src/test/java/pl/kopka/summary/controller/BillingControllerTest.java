package pl.kopka.summary.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.exception.exceptions.EmailExistException;
import pl.kopka.summary.exception.exceptions.UsernameExistException;
import pl.kopka.summary.repository.UserRepo;
import pl.kopka.summary.service.BillingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BillingControllerTest {

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJSZXZvbHV0IFN0b2NrIENhbGN1bGF0b3IiLCJzdWIiOiJLdWJhIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Ikpha3ViIEtvcGthLCBQTCIsImV4cCI6OTk5OTk5OTk5OSwiaWF0IjoxNjExMjc1NDQyfQ.sE6JD6lnrCvjDA9xqvkWhSW8Wy2NzyI9osradYUAEsHKmZt9NasMcLPU8_cCFZU8OaB82rPnnN3mxN20Xw28Sw";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllUserMonths() throws Exception {
        mockMvc.perform(get("/billing/months").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].number").value(1));
    }

    @Test
    void shouldReturnMonthsAndCategories() throws Exception {
        mockMvc.perform(get("/billing/all").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }
}