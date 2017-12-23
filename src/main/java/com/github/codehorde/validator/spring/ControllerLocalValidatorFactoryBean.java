package com.github.codehorde.validator.spring;

import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 直接抛出ConstraintViolationException交给HandlerExceptionResolver处理，而不是由spring-webmvc框架抛出BindingException
 * <p>
 * Created by baomingfeng at 2017-09-11 14:58:12
 */
public class ControllerLocalValidatorFactoryBean extends LocalValidatorFactoryBean {
    /**
     * <pre>
     * see ->
     *  org.springframework.web.bind.annotation.support.HandlerMethodInvoker.resolveHandlerArguments
     *  org.springframework.web.method.annotation.ModelAttributeMethodProcessor.validateIfApplicable
     *  org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver.validateIfApplicable
     * </pre>
     */
    @Override
    protected void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {
        throw new ConstraintViolationException(violations);
    }
}