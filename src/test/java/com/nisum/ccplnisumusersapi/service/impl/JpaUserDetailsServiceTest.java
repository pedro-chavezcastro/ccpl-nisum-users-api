package com.nisum.ccplnisumusersapi.service.impl;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.nisum.ccplnisumusersapi.DummyMock.getUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private JpaUserDetailsService service;

    @Test
    void loadUserByUsernameTest() {
        // Given
        String username = "pedro.chavez@gmail.com";
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userCreatedEntity));
        UserDetails result = service.loadUserByUsername(username);

        // Then
        assertNotNull(result);
        assertEquals("pedro.chavez@gmail.com", result.getUsername());
        assertEquals("password", result.getPassword());

        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void loadUserByUsernameUsernameNotFoundExceptionTest() {
        // Given
        String username = "pedro.chavez@gmail.com";
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));
        // Then
        assertNotNull(exception);
        assertEquals(String.format(JpaUserDetailsService.USER_NOT_FOUNT, username), exception.getMessage());

        verify(userRepository).findByEmail(anyString());
    }
}