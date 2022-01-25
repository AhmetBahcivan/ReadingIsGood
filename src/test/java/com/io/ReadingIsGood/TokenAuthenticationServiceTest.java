package com.io.ReadingIsGood;

import com.io.ReadingIsGood.db.entity.Customer;
import com.io.ReadingIsGood.security.jwt.JwtProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenAuthenticationServiceTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/test")).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldGenerateAuthToken() throws Exception {
        String token = jwtProvider.generateJwtToken(new Customer("ahmet","ahmetbahcivan","ahmetbahcivan@hotmail.com"));

        assertNotNull(token);

        mvc.perform(MockMvcRequestBuilders.get("/test").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }
}
