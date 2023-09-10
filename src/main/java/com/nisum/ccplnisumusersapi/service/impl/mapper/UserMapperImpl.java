package com.nisum.ccplnisumusersapi.service.impl.mapper;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.PhoneEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.model.PageUserDto;
import com.nisum.ccplnisumusersapi.model.PhoneDto;
import com.nisum.ccplnisumusersapi.model.UpdateUserDto;
import com.nisum.ccplnisumusersapi.model.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements IUserMapper {

    @Override
    public UserEntity mapInUserDtoToEntity(UserDto userDto, String accessToken, String password) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(password);
        userEntity.setCreated(LocalDateTime.now());
        userEntity.setIsActive(Boolean.TRUE);
        userEntity.setAccessToken(accessToken);

        userDto.getPhones().forEach(phoneDto -> this.mapInPhoneDtoToEntity(userEntity, phoneDto));

        return userEntity;
    }

    @Override
    public UserDto mapOutUserEntityToDto(UserEntity userEntity, Boolean isQuery) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setCreated(userEntity.getCreated());
        userDto.setModified(userEntity.getModified());
        userDto.setIsActive(userEntity.getIsActive());
        userDto.setLastLogin(Objects.nonNull(userEntity.getLastLogin()) ? userEntity.getLastLogin() : userEntity.getCreated());
        userDto.setPhones(userEntity.getPhones().stream().map(this::mapOutPhoneEntityToDto).collect(Collectors.toList()));
        userDto.setToken(isQuery ? null : userEntity.getAccessToken());
        return userDto;
    }

    @Override
    public PageUserDto mapOutUserEntityToPageDto(Page<UserEntity> userPage) {
        PageUserDto pageUserDto = new PageUserDto();
        pageUserDto.setTotalItems(userPage.getTotalElements());
        pageUserDto.setCurrentPage(userPage.getNumber());
        pageUserDto.setTotalPages(userPage.getTotalPages());

        pageUserDto.setUsers(userPage.getContent()
                .stream().map(userEntity -> this.mapOutUserEntityToDto(userEntity, Boolean.TRUE))
                .collect(Collectors.toList()));

        return pageUserDto;
    }

    @Override
    public void mapInUpdateUser(UserEntity userEntity, UpdateUserDto userDto, String password) {
        userEntity.setName(userDto.getName());
        userEntity.setPassword(password);
        userEntity.setModified(LocalDateTime.now());

        List<PhoneEntity> existingPhones = userEntity.getPhones();
        List<PhoneDto> updatedPhones = userDto.getPhones();

        if (!updatedPhones.isEmpty()) {
            for (PhoneDto phoneDto : updatedPhones) {

                if (Objects.nonNull(phoneDto.getId())) {
                    PhoneEntity actualPhoneEntity = this.getActualPhone(existingPhones, phoneDto.getId());
                    actualPhoneEntity.setNumber(phoneDto.getNumber());
                    actualPhoneEntity.setCityCode(phoneDto.getCitycode());
                    actualPhoneEntity.setCountryCode(phoneDto.getCountrycode());
                } else {
                    PhoneEntity newPhoneEntity = new PhoneEntity();
                    newPhoneEntity.setNumber(phoneDto.getNumber());
                    newPhoneEntity.setCityCode(phoneDto.getCitycode());
                    newPhoneEntity.setCountryCode(phoneDto.getCountrycode());
                    userEntity.addPhone(newPhoneEntity);
                }
            }

            existingPhones.removeIf(phone -> updatedPhones.stream()
                    .noneMatch(phoneDto -> Objects.equals(phoneDto.getId(), phone.getId())));
        }
    }

    private PhoneEntity getActualPhone(List<PhoneEntity> phones, UUID id) {
        return phones.stream()
                .filter(phoneEntity -> phoneEntity.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    private void mapInPhoneDtoToEntity(UserEntity userEntity, PhoneDto phoneDto) {

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setNumber(phoneDto.getNumber());
        phoneEntity.setCityCode(phoneDto.getCitycode());
        phoneEntity.setCountryCode(phoneDto.getCountrycode());

        userEntity.addPhone(phoneEntity);
    }

    private PhoneDto mapOutPhoneEntityToDto(PhoneEntity phone) {
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setId(phone.getId());
        phoneDto.setNumber(phone.getNumber());
        phoneDto.setCitycode(phone.getCityCode());
        phoneDto.setCountrycode(phone.getCountryCode());
        return phoneDto;
    }

}
