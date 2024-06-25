package org.spring.codingStory.member.exception;

import org.springframework.security.core.AuthenticationException;

public class GuestLoginException extends AuthenticationException {

    public GuestLoginException(String message) {
        super(message);
    }
}