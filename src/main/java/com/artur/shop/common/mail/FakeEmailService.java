package com.artur.shop.common.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class FakeEmailService implements EmailSander{
    @Override
    public void send(String to, String subject, String msg) {
        log.info("email send");
        log.info("To: " + to);
        log.info("Subject: " + subject);
        log.info("Message: " + msg);
    }
}
