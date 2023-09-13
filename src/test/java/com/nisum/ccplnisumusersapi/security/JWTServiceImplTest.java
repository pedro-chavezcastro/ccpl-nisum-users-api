package com.nisum.ccplnisumusersapi.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.nisum.ccplnisumusersapi.DummyMock.JWT_BEARER_TOKEN_MOCK;
import static com.nisum.ccplnisumusersapi.DummyMock.JWT_TOKEN_MOCK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTServiceImplTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private JWTServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "jwtExpiration", 5);
    }

    @Test
    void generateJwtTokenTest() {
        // Given
        String username = "pedro.chavezcastro@gmail.com";
        // When
        String result = service.generateJwtToken(username);
        // then
        assertNotNull(result);
    }

    @Test
    void resolveTokenTest() {
        // Given
        String username = "pedro.chavezcastro@gmail.com";
        // When
        when(request.getHeader(anyString())).thenReturn(JWT_BEARER_TOKEN_MOCK);
        String result = service.resolveToken(request);
        // then
        assertNotNull(result);
        assertEquals(JWT_TOKEN_MOCK, result);
    }

    @Test
    void resolveTokenNullTest() {
        // Given
        String username = "pedro.chavezcastro@gmail.com";
        // When
        when(request.getHeader(anyString())).thenReturn(null);
        String result = service.resolveToken(request);
        // then
        assertNull(result);
    }

    @Test
    void resolveClaimsTest() {
        // Given
        String username = "pedro.chavezcastro@gmail.com";
        String token = service.generateJwtToken(username);
        // When
        when(request.getHeader(anyString())).thenReturn("Bearer ".concat(token));
        Claims result = service.resolveClaims(request);
        // then
        assertNotNull(result);
        assertEquals(username, result.getSubject());
    }

    @Test
    void resolveClaimsNullTest() {
        // Given
        String username = "pedro.chavezcastro@gmail.com";
        String token = service.generateJwtToken(username);
        // When
        Claims result = service.resolveClaims(request);
        // then
        assertNull(result);
    }

}