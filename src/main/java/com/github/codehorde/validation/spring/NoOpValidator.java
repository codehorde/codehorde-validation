package com.github.codehorde.validation.spring;

import com.github.codehorde.validation.spring.controller.ControllerValidationPostProcessor;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

/**
 * <pre>
 *  不作校验操作，参数校验工作统一交给{@link MethodArgumentValidationInterceptor}处理
 *
 *  spring-webmvc参照配置见{@link ControllerValidationPostProcessor}
 * </pre>
 * Created by Bao.mingfeng at 2018-08-02 17:48:36
 *
 * @see ControllerValidationPostProcessor
 * @see MethodArgumentValidationInterceptor
 */
public class NoOpValidator implements SmartValidator {

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}
