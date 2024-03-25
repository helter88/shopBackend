package com.artur.shop.userData.service;

import com.artur.shop.common.model.UserData;
import com.artur.shop.common.repository.UserDataRepository;
import com.artur.shop.order.model.OrderSummary;
import com.artur.shop.security.repository.UserRepository;
import com.artur.shop.userData.model.dto.UserDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.artur.shop.userData.service.mapper.UserDataMapper.createUserData;
import static com.artur.shop.userData.service.mapper.UserDataMapper.mapUserDataToDto;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;
    @Transactional
    public void saveUserData(UserDataDto userDataDto, String userName) {
        Long userId = userRepository.findByUsername(userName).getId();
        userDataRepository.save(createUserData(userDataDto, userId));
    }

    public UserDataDto getUserData(String userName) {
        Long userId = userRepository.findByUsername(userName).getId();
        UserData userData = userDataRepository.findByUserId(userId);
        return mapUserDataToDto(userData);
    }
}
