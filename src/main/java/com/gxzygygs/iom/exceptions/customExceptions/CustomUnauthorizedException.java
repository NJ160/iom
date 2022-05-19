package com.gxzygygs.iom.exceptions.customExceptions;

/**
 * 自定义401无权限异常(UnauthorizedException)
 */
public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(String msg){
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
