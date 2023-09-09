package com.nisum.ccplnisumusersapi.service.impl.mapper;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.model.UserDto;

public interface IUserMapper {

    UserEntity mapInUserDtoToEntity(UserDto userDto);

    UserDto mapOutUserEntityToDto(UserEntity userCreatedEntity);

}
