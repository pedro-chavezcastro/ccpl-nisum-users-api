package com.nisum.ccplnisumusersapi.security;

import com.nisum.ccplnisumusersapi.DummyMock;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IJWTService jwtService;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private Authentication authentication;

    @Mock
    private AuthenticationException authenticationException;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void attemptAuthenticationTest() {
        // Given
        String usernameParameter = "username";
        String passwordParameter = "password";
        String username = "pedro.chavez@gmail.com";
        String password = "password";
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // When
        when(request.getParameter(usernameParameter)).thenReturn(username);
        when(request.getParameter(passwordParameter)).thenReturn(password);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(usernamePasswordAuthenticationToken);
        Authentication result = jwtAuthenticationFilter.attemptAuthentication(request, response);
        // Then
        assertNotNull(result);
        verify(request).getParameter(usernameParameter);
        verify(request).getParameter(passwordParameter);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void successfulAuthenticationTest() throws Exception {
        // Given
        String username = "pedro.chavez@gmail.com";
        String token = DummyMock.JWT_BEARER_TOKEN_MOCK;
        UserEntity userEntity = DummyMock.getUserEntity(username);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        // When
        when(authentication.getName()).thenReturn(username);
        when(jwtService.generateJwtToken(anyString())).thenReturn(token);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(response.getWriter()).thenReturn(printWriter);
        jwtAuthenticationFilter.successfulAuthentication(request, response, chain, authentication);
        // Then
        verify(authentication).getName();
        verify(jwtService).generateJwtToken(anyString());
        verify(userRepository).findByEmail(anyString());
        verify(response).getWriter();
        verify(userRepository).save(any());
    }

    @Test
    void unsuccessfulAuthenticationTest() throws Exception {
        // Give
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        // When
        when(response.getWriter()).thenReturn(printWriter);
        jwtAuthenticationFilter.unsuccessfulAuthentication(request, response, authenticationException);
        // Then
        verify(response).getWriter();
    }
}