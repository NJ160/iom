package com.gxzygygs.iom.exceptions;

import com.gxzygygs.iom.exceptions.customExceptions.AccountException;
import com.gxzygygs.iom.response.Result;
import lombok.extern.slf4j.Slf4j;
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
//                if (errors.size() > 0) {
//                    FieldError fieldError = (FieldError) errors.get(0);
//                    message = fieldError.getDefaultMessage();
//                }
            }
        }
        return new Result().error(HttpStatus.BAD_REQUEST.value(), message.toString());
    }


    /**
     * AccountException校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(AccountException.class)
    public Result AuthorizedExceptionHandler(AccountException exception) {
        log.error(exception.getMessage());
        return new Result().error(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
}
