package com.artur.shop.common.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

//@Configuration
public class EmailConfig {

    @Bean
    @ConditionalOnProperty(name="app.email.sender", havingValue = "emailSimpleService")
    public EmailSander emailSimpleService(JavaMailSender javaMailSender){
        return new EmailService(javaMailSender);
    }
    @Bean
    @ConditionalOnProperty(name="app.email.sender", havingValue = "fakeEmailService", matchIfMissing = true)
    public EmailSander fakeEmailService(JavaMailSender javaMailSender){
        return new FakeEmailService();
    }

    @Bean
    @ConditionalOnProperty(name="app.email.sender", havingValue = "webService")
    public EmailSander webServiceEmailService(){
        return new WebServiceEmailService();
    }
}
