package com.nisum.ccplnisumusersapi.controller;

import com.nisum.ccplnisumusers.api.UsersApi;
import com.nisum.ccplnisumusers.model.PageUserDto;
import com.nisum.ccplnisumusers.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        return UsersApi.super.createUser(userDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID userId) {
        return UsersApi.super.deleteUser(userId);
    }

    @Override
    public ResponseEntity<PageUserDto> getAllUsers(Integer page, Integer size) {
        return UsersApi.super.getAllUsers(page, size);
    }

    @Override
    public ResponseEntity<UserDto> getUserById(UUID userId) {
        return UsersApi.super.getUserById(userId);
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID userId, UserDto userDto) {
        return UsersApi.super.updateUser(userId, userDto);
    }
}
