package com.artur.shop.userData.service.mapper;

import com.artur.shop.common.model.UserData;
import com.artur.shop.userData.model.dto.UserDataDto;

public class UserDataMapper {

    public static UserData createUserData(UserDataDto userDataDto, Long userId, Long userDataId){
        return UserData.builder()
                .id(userDataId)
                .firstname(userDataDto.firstname())
                .lastname(userDataDto.lastname())
                .street(userDataDto.street())
                .zipcode(userDataDto.zipcode())
                .city(userDataDto.city())
                .phone(userDataDto.phone())
                .email(userDataDto.email())
                .userId(userId)
                .build();
    }

    public static UserDataDto mapUserDataToDto(UserData userData){
        return new UserDataDto(
                userData.getFirstname(),
                userData.getLastname(),
                userData.getStreet(),
                userData.getZipcode(),
                userData.getCity(),
                userData.getEmail(),
                userData.getPhone()
        );
    }
}
