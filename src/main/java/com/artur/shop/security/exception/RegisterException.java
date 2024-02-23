package com.artur.shop.security.exception;

import com.artur.shop.common.exception.BusinessException;

public class RegisterException extends BusinessException {
    public RegisterException(String message) {
        super(message);
    }
}
