package com.nisum.ccplnisumusersapi.service.impl;

import com.nisum.ccplnisumusersapi.crosscutting.constant.MessageErrorEnum;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IPhoneRepository;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import com.nisum.ccplnisumusersapi.exception.BusinessException;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import com.nisum.ccplnisumusersapi.security.IJWTService;
import com.nisum.ccplnisumusersapi.service.impl.mapper.UserMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static com.nisum.ccplnisumusersapi.DummyMock.JWT_TOKEN_MOCK;
import static com.nisum.ccplnisumusersapi.DummyMock.getCreateUserRequestDto;
import static com.nisum.ccplnisumusersapi.DummyMock.getPageUserEntities;
import static com.nisum.ccplnisumusersapi.DummyMock.getUpdateUserDto;
import static com.nisum.ccplnisumusersapi.DummyMock.getUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPhoneRepository phoneRepository;

    @Mock
    private IJWTService jwtService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Spy
    private UserMapperImpl mapper;

    @InjectMocks
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUserOkTest() {

        // Given
        String jwtTokenMock = JWT_TOKEN_MOCK;
        String encodedPasswordMock = "encodedPassword";
        UserDto createUserRequestDto = getCreateUserRequestDto();
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(jwtService.generateJwtToken(anyString())).thenReturn(jwtTokenMock);
        when(passwordEncoder.encode(any())).thenReturn(encodedPasswordMock);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userCreatedEntity);
        UserDto result = service.createUser(createUserRequestDto);

        // Then
        assertNotNull(result);
        assertEquals("pedro.chavez@gmail.com", result.getEmail());
        assertFalse(result.getPhones().isEmpty());

        verify(userRepository).findByEmail(anyString());
        verify(mapper).mapInUserDtoToEntity(any(UserDto.class), anyString(), anyString());
        verify(jwtService).generateJwtToken(anyString());
        verify(userRepository).save(any(UserEntity.class));
        verify(userRepository).save(any(UserEntity.class));
        verify(mapper).mapOutUserEntityToDto(any(UserEntity.class), anyBoolean());
    }

    @Test
    void createUserEmailRegisteredTest() {

        // Given
        UserDto createUserRequestDto = getCreateUserRequestDto();
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");

        // When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userCreatedEntity));
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createUser(createUserRequestDto));

        // Then
        assertNotNull(exception);
        assertEquals(MessageErrorEnum.NISUM002.getCode(), exception.getCodeError());
        assertEquals(String.format(MessageErrorEnum.NISUM002.getDescription(), createUserRequestDto.getEmail()), exception.getMessage());

        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void deleteUserTest() {
        // Given
        UUID userId = UUID.randomUUID();
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");
        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userCreatedEntity));
        service.deleteUser(userId);
        // Then
        verify(userRepository).findById(any());
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void getAllUsersTest() {
        // Given
        int page = 0;
        int size = 3;
        // When
        when(userRepository.findByIsActive(anyBoolean(), any(Pageable.class))).thenReturn(getPageUserEntities());
        PageUserDto result = service.getAllUsers(page, size);
        // Then
        assertNotNull(result);
        assertEquals(3, result.getTotalItems());
        assertFalse(result.getUsers().isEmpty());

        verify(userRepository).findByIsActive(anyBoolean(), any(Pageable.class));
        verify(mapper).mapOutUserEntityToPageDto(any(Page.class));

    }

    @Test
    void getUserByIdTest() {
        // Given
        UUID userId = UUID.randomUUID();
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");
        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userCreatedEntity));
        UserDto result = service.getUserById(userId);
        // Then
        assertNotNull(result);
        assertEquals("pedro.chavez@gmail.com", result.getEmail());
        assertFalse(result.getPhones().isEmpty());

        verify(userRepository).findById(any());
        verify(mapper).mapOutUserEntityToDto(any(UserEntity.class), anyBoolean());
    }

    @Test
    void getUserByIdUserNotFoundTest() {
        // Given
        UUID userId = UUID.randomUUID();

        // When
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> service.getUserById(userId));

        // Then
        assertNotNull(exception);
        assertEquals(MessageErrorEnum.NISUM001.getCode(), exception.getCodeError());
        assertEquals(String.format(MessageErrorEnum.NISUM001.getDescription(), userId), exception.getMessage());

        verify(userRepository).findById(any());
    }

    @Test
    void updateUserTest() {
        // Given
        UUID userId = UUID.randomUUID();
        String encodedPasswordMock = "encodedPassword";
        UpdateUserDto updateUserDto = getUpdateUserDto();
        UserEntity userCreatedEntity = getUserEntity("Pedro Chavez");
        // When
        when(userRepository.findById(any())).thenReturn(Optional.of(userCreatedEntity));
        service.updateUser(userId, updateUserDto);
        // Then
        verify(userRepository).findById(any());
        verify(mapper).mapInUpdateUser(any(UserEntity.class), any(UpdateUserDto.class), any());
    }
}