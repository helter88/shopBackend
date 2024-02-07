package com.artur.shop.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailClientService {

    @Value("@{app.email.sender}")
    private String implementationType;
    private final Map<String, EmailSander> emailSanderMap;

    public EmailSander getInstance(){
        if(implementationType.equals("emailSimpleService")){
            return emailSanderMap.get("emailSimpleService");
        }
        return emailSanderMap.get("fakeEmailService");
    }
    public EmailSander getInstance(String beanName){
        if(implementationType.equals("emailSimpleService")){
            return emailSanderMap.get("emailSimpleService");
        }
        return emailSanderMap.get(beanName);
    }
}
