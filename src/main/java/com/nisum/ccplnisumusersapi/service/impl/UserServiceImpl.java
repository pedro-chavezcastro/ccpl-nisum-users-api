package com.nisum.ccplnisumusersapi.service.impl;

import com.nisum.ccplnisumusers.model.PageUserDto;
import com.nisum.ccplnisumusers.model.UserDto;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IPhoneRepository;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import com.nisum.ccplnisumusersapi.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPhoneRepository phoneRepository;


    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(UUID userId) {

    }

    @Override
    public PageUserDto getAllUsers(Integer page, Integer size) {
        return null;
    }

    @Override
    public UserDto getUserById(UUID userId) {
        return null;
    }

    @Override
    public void updateUser(UUID userId, UserDto userDto) {

    }
}