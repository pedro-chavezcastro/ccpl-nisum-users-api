package com.nisum.ccplnisumusersapi.service.impl.mapper;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import org.springframework.data.domain.Page;

public interface IUserMapper {

    UserEntity mapInUserDtoToEntity(UserDto userDto, String accessToken, String password);

    UserDto mapOutUserEntityToDto(UserEntity userCreatedEntity, Boolean isQuery);

    PageUserDto mapOutUserEntityToPageDto(Page<UserEntity> userPage);

    void mapInUpdateUser(UserEntity userEntity, UpdateUserDto userDto, String password);
}
