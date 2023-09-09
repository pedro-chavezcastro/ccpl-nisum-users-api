package com.nisum.ccplnisumusersapi.service.impl.mapper;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import org.springframework.data.domain.Page;

public interface IUserMapper {

    UserEntity mapInUserDtoToEntity(UserDto userDto);

    UserDto mapOutUserEntityToDto(UserEntity userCreatedEntity);

    PageUserDto mapOutUserEntityToPageDto(Page<UserEntity> userPage);

}
