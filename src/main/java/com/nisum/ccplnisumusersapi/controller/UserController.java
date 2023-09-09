package com.nisum.ccplnisumusersapi.controller;

import com.nisum.ccplnisumusersapi.api.UsersApi;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import com.nisum.ccplnisumusersapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController implements UsersApi {

    @Autowired
    private IUserService service;

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        UserDto user = this.service.createUser(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(user);

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
