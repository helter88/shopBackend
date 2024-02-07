package com.artur.shop.common.mail;

public interface EmailSander {

  void send(String to, String subject, String msg);
}
