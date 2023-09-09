package com.nisum.ccplnisumusersapi.service;

import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;

import java.util.UUID;

public interface IUserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(UUID userId);

    PageUserDto getAllUsers(Integer page, Integer size);

    UserDto getUserById(UUID userId);

    void updateUser(UUID userId, UpdateUserDto userDto);

}
