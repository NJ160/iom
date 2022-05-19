package com.gxzygygs.iom.exceptions.customExceptions;

public class AccountException extends RuntimeException{
    public AccountException(String msg){
        super(msg);
    }
    public AccountException() {
        super();
    }
}
