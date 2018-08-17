package com.github.codehorde.validation.spring;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-08-02 21:42:04
 */
public interface ValidationExceptionFactory {

    /**
     * 校验的错误统一抛出unchecked例外
     */
    RuntimeException buildValidationException(Set<ConstraintViolation<Object>> violations);

}
