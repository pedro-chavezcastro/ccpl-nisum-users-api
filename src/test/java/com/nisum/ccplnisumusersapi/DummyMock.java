package com.nisum.ccplnisumusersapi;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.PhoneEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.PhoneDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DummyMock {

    public static final String JWT_TOKEN_MOCK = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWxhYXJhZ29uMDhAZ21haWwuY29tIiwiZXhwIjoxNjk0Mzg0OTU4fQ.yLzArmMtrFECUjE7nUYaP8YYhMTax4PyFFBWrzNUKOLHAR4vXvO-LkZ0BSSgk1cs3M1O4eSltSlxUNm_mBZJow";
    public static final String JWT_BEARER_TOKEN_MOCK = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYW5pZWxhYXJhZ29uMDhAZ21haWwuY29tIiwiZXhwIjoxNjk0Mzg0OTU4fQ.yLzArmMtrFECUjE7nUYaP8YYhMTax4PyFFBWrzNUKOLHAR4vXvO-LkZ0BSSgk1cs3M1O4eSltSlxUNm_mBZJow";

    public static UserEntity getUserEntity(String userName) {

        List<PhoneEntity> phoneEntityList = new ArrayList<>();
        phoneEntityList.add(createPhoneEntity("28f51bb0-0afe-4d93-911d-a4b988a86f1d", "3106995189"));
        phoneEntityList.add(createPhoneEntity("606c8a78-8bf8-446b-a025-630085553c3e", "3002156982"));
        phoneEntityList.add(createPhoneEntity("26263e7b-9ab0-4fac-be02-435ba92208f4", "3228806264"));

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setName(userName);
        userEntity.setEmail("pedro.chavez@gmail.com");
        userEntity.setPassword("password");
        userEntity.setIsActive(true);
        userEntity.setPhones(phoneEntityList);
        return userEntity;
    }

    public static PhoneEntity createPhoneEntity(String id, String number) {
        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setId(UUID.fromString(id));
        phoneEntity.setNumber(number);
        phoneEntity.setCityCode("57");
        phoneEntity.setCountryCode("1");
        return phoneEntity;
    }

    public static Page<UserEntity> getPageUserEntities() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(getUserEntity("User 1"));
        userEntities.add(getUserEntity("User 2"));
        userEntities.add(getUserEntity("User 3"));

        Pageable pageable = PageRequest.of(0, 3);
        return new PageImpl<>(userEntities, pageable, userEntities.size());
    }

    public static UserDto getCreateUserRequestDto() {
        UserDto userDto = new UserDto();
        userDto.setId(UUID.randomUUID());
        userDto.setName("Pedro Luis Chavez");
        userDto.setEmail("pedro.chavez@gmail.com");
        userDto.setPassword("Nisum2023*");
        userDto.setPhones(Collections.singletonList(createPhoneDto("28f51bb0-0afe-4d93-911d-a4b988a86f1d", "3106995189")));
        return userDto;
    }

    public static PhoneDto createPhoneDto(String id, String number) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(Objects.nonNull(id) ? UUID.fromString(id) : null);
        phoneDto.setNumber(number);
        phoneDto.setCitycode("57");
        phoneDto.setCountrycode("1");
        return phoneDto;
    }

    public static UpdateUserDto getUpdateUserDto() {
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        phoneDtoList.add(createPhoneDto("28f51bb0-0afe-4d93-911d-a4b988a86f1d", "3106995185"));
        phoneDtoList.add(createPhoneDto(null, "3227685986"));

        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setName("Pedro Luis Chavez");
        updateUserDto.setPassword("Nisum2023*");
        updateUserDto.setPhones(phoneDtoList);
        return updateUserDto;
    }

    public static PageUserDto getPageUserDto() {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(getCreateUserRequestDto());
        userDtoList.add(getCreateUserRequestDto());
        userDtoList.add(getCreateUserRequestDto());

        PageUserDto pageUserDto = new PageUserDto();
        pageUserDto.setTotalItems(3L);
        pageUserDto.setTotalPages(1);
        pageUserDto.setCurrentPage(0);
        pageUserDto.setUsers(userDtoList);
        return pageUserDto;
    }
}
