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
import com.nisum.ccplnisumusersapi.service.IUserService;
import com.nisum.ccplnisumusersapi.service.impl.mapper.IUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPhoneRepository phoneRepository;

    @Autowired
    private IUserMapper mapper;

    @Autowired
    private IJWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) {

        this.validateExistingUser(userDto);

        UserEntity userEntity = this.mapper.mapInUserDtoToEntity(userDto,
                this.jwtService.generateJwtToken(userDto.getEmail()), passwordEncoder.encode(userDto.getPassword()));
        UserEntity userCreatedEntity = this.saveUserEntity(userEntity);

        return this.mapper.mapOutUserEntityToDto(userCreatedEntity, Boolean.FALSE);
    }

    @Override
    public void deleteUser(UUID userId) {
        UserEntity userEntity = this.findUserEntityById(userId);
        userEntity.setIsActive(Boolean.FALSE);

        this.saveUserEntity(userEntity);
    }

    @Override
    public PageUserDto getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> userPage = this.userRepository.findByIsActive(Boolean.TRUE, pageable);
        return this.mapper.mapOutUserEntityToPageDto(userPage);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        UserEntity userEntity = this.findUserEntityById(userId);
        return this.mapper.mapOutUserEntityToDto(userEntity, Boolean.TRUE);
    }

    @Override
    public void updateUser(UUID userId, UpdateUserDto userDto) {
        UserEntity userEntity = this.findUserEntityById(userId);
        this.mapper.mapInUpdateUser(userEntity, userDto, passwordEncoder.encode(userDto.getPassword()));
        this.saveUserEntity(userEntity);
    }

    private void validateExistingUser(UserDto userDto) {
        Optional<UserEntity> userEntity = this.userRepository.findByEmail(userDto.getEmail());
        if (userEntity.isPresent()) {
            throw new BusinessException(String.format(
                    MessageErrorEnum.NISUM002.getDescription(), userDto.getEmail()),
                    MessageErrorEnum.NISUM002.getCode());
        }
    }

    private UserEntity saveUserEntity(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    private UserEntity findUserEntityById(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        String.format(MessageErrorEnum.NISUM001.getDescription(), userId),
                        MessageErrorEnum.NISUM001.getCode()));
    }
}