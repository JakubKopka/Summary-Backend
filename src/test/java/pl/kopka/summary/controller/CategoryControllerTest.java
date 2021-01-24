package pl.kopka.summary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJSZXZvbHV0IFN0b2NrIENhbGN1bGF0b3IiLCJzdWIiOiJLdWJhIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Ikpha3ViIEtvcGthLCBQTCIsImV4cCI6OTk5OTk5OTk5OSwiaWF0IjoxNjExMjc1NDQyfQ.sE6JD6lnrCvjDA9xqvkWhSW8Wy2NzyI9osradYUAEsHKmZt9NasMcLPU8_cCFZU8OaB82rPnnN3mxN20Xw28Sw";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/category/all").header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}