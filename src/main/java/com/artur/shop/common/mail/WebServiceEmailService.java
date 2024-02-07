package com.artur.shop.common.mail;

public class WebServiceEmailService implements EmailSander{
    @Override
    public void send(String to, String subject, String msg) {
        System.out.println("WebServiceEmail");
    }
}
