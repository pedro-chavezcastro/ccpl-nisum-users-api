package com.nisum.ccplnisumusersapi.security;

import com.nisum.ccplnisumusersapi.DummyMock;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {

    @Mock
    private IJWTService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private JwtAuthorizationFilter jwtAuthorizationFilter;

    @Test
    void doFilterInternalTest() throws Exception {
        // Given
        String token = DummyMock.JWT_TOKEN_MOCK;
        DefaultClaims claims = new DefaultClaims();
        claims.setSubject("pedro.chavezcastro@gmail.com");
        // When
        when(jwtService.resolveToken(any())).thenReturn(token);
        when(jwtService.resolveClaims(any())).thenReturn(claims);
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);
        // Then
        verify(jwtService).resolveToken(any());
        verify(jwtService).resolveClaims(any());
    }

    @Test
    void doFilterInternalAccessTokenNullTest() throws Exception {
        // Given
        // When
        when(jwtService.resolveToken(any())).thenReturn(null);
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);
        // Then
        verify(jwtService).resolveToken(any());
    }

    @Test
    void doFilterInternalClaimsNullTest() throws Exception {
        // Given
        String token = DummyMock.JWT_TOKEN_MOCK;
        // When
        when(jwtService.resolveToken(any())).thenReturn(token);
        when(jwtService.resolveClaims(any())).thenReturn(null);
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);
        // Then
        verify(jwtService).resolveToken(any());
        verify(jwtService).resolveClaims(any());
    }

    @Test
    void doFilterInternalExpiredJwtExceptionTest() throws Exception {
        // Given
        String token = DummyMock.JWT_TOKEN_MOCK;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        // When
        when(jwtService.resolveToken(any())).thenReturn(token);
        when(jwtService.resolveClaims(any())).thenThrow(ExpiredJwtException.class);
        when(response.getWriter()).thenReturn(printWriter);
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);
        // Then
        verify(jwtService).resolveToken(any());
        verify(jwtService).resolveClaims(any());
    }
}