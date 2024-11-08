package com.apirest.wwapi.Jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testDoFilterInternal() throws Exception {
        request.addHeader("Authorization", "Bearer jwt-token");
        when(jwtService.getEmailFromToken("jwt-token")).thenReturn("test@example.com");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("test@example.com")).thenReturn(userDetails);
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);

        MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        jwtAuthenticationFilter.doFilterInternal(request, response, (req, res) -> {});

        verify(jwtService).getEmailFromToken("jwt-token");
        verify(jwtService).isTokenValid(anyString(), any(UserDetails.class));
    }
}
