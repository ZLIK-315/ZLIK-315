package com.markerhub.common.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author zhang Bowen
 * @date 2021-10-25 17:57
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
