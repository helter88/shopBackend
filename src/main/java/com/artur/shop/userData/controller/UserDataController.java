package com.artur.shop.userData.controller;

import com.artur.shop.userData.model.dto.UserDataDto;
import com.artur.shop.userData.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userData")
public class UserDataController {

    private final UserDataService userDataService;

    @PostMapping
    public ResponseEntity<?> saveUserData (@RequestBody UserDataDto userDataDto, @AuthenticationPrincipal String name){

        try {
            userDataService.saveUserData(userDataDto, name);
            return ResponseEntity.ok().body("{\"message\":\"User data added successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Invalid user data\"}");
        }
    }

    @GetMapping
    public UserDataDto getUserData(@AuthenticationPrincipal String name){
        if (name.equals("anonymousUser")){
//            throw new IllegalArgumentException("No user");
            return null;
        }
        return userDataService.getUserData(name);
    }
}
