package com.sip.demo.security_role_demo.Exception;

import javax.security.sasl.AuthenticationException;

public class UsernameAlreadyExistsException extends AuthenticationException{

    public UsernameAlreadyExistsException(final String msg){
        super(msg);
    }

    
    
}
