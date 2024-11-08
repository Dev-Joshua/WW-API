package com.apirest.wwapi.config;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@SpringBootTest
class ApplicationConfigTest {

    @Mock
    private DaoAuthenticationProvider authenticationProvider;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(null);
        
    }
}
