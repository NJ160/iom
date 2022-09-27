package com.gxzygygs.iom.exceptions;

import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.exceptions.customExceptions.UtilsException;
import com.gxzygygs.iom.exceptions.customExceptions.PromException;
import com.gxzygygs.iom.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    /**
     * Validated校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validationBodyException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        StringBuilder message = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.error("数据校验失败 : 对象{" + fieldError.getObjectName() + "},字段{" + fieldError.getField() +
                            "},错误信息{" + fieldError.getDefaultMessage() + "}");
                    message.append(fieldError.getDefaultMessage()+"; ");
                });
            }
        }
        return new Result().error(HttpStatus.BAD_REQUEST.value(), "(Validate错误： "+message.toString()+")");
    }


    /**
     * AccountException校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(AccountException.class)
    public Result AccountExceptionHandler(AccountException exception) {
        log.error(exception.getMessage());
        return new Result().error(HttpStatus.BAD_REQUEST.value(), "(Account错误： "+exception.getMessage()+")");
    }

    /**
     * AuthorizationException校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result AuthorizedExceptionHandler(AuthorizationException exception) {
        log.error(exception.getMessage());
        return new Result().error(HttpStatus.BAD_REQUEST.value(), "(Authorization错误： "+exception.getMessage()+")");
    }

    /**
     * PromException 普罗米修斯脚本执行错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(PromException.class)
    public Result PromExceptionHandler(PromException exception) {
        log.error(exception.getMessage());
        return new Result().error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "(普罗米修斯错误： "+exception.getMessage()+")");
    }

    /**
     * Util 工具类错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(UtilsException.class)
    public Result CustomExceptionHandler(UtilsException exception) {
        log.error(exception.getMessage());
        return new Result().error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "(Utils工具类错误： "+exception.getMessage()+")");
    }
}

