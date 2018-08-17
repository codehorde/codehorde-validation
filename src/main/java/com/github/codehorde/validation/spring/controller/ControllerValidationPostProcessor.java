package com.github.codehorde.validation.spring.controller;

import com.github.codehorde.validation.spring.InheritedAnnotationMethodPointcut;
import com.github.codehorde.validation.spring.MethodArgumentValidationInterceptor;
import com.github.codehorde.validation.spring.ValidationExceptionFactory;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;

/**
 * <pre>
 *  校验每个Controller执行那个的方法参数、返回值等
 *
 *  参照配置：
 *     &lt;bean name="defaultValidator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"/&gt;
 *     &lt;!-- spring-webmvc框架默认不做参数校验(可以通过DataBinder添加自定义校验器)--&gt;
 *     &lt;bean name="noOpValidator" class="com.github.codehorde.validation.spring.NoOpValidator"/&gt;
 *     &lt;!-- 参数校验统一由自定义校验拦截器处理 --&gt;
 *     &lt;bean name="mvcValidatorPostProcessor"
 *           class="com.github.codehorde.validation.spring.controller.ControllerValidationPostProcessor"&gt;
 *         &lt;property name="validator" ref="defaultValidator"/&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;mvc:annotation-driven
 *             content-negotiation-manager="contentNegotiationManager" validator="noOpValidator"&gt;
 *     &lt;/mvc:annotation-driven&gt;
 * </pre>
 * <p>
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 *
 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
@SuppressWarnings("WeakerAccess")
public class ControllerValidationPostProcessor
        extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private final static Class<? extends Annotation>
            controllerClassAnnotationType = Controller.class;

    private final static Class<? extends Annotation>
            controllerMethodAnnotationType = RequestMapping.class;

    private Validator validator;

    private ValidationExceptionFactory exceptionFactory = new ControllerValidationExceptionFactory();

    public ControllerValidationPostProcessor() {
    }

    public ControllerValidationPostProcessor(
            ValidatorFactory validatorFactory, ValidationExceptionFactory exceptionFactory) {
        this.setValidator(validatorFactory.getValidator());
        this.setExceptionFactory(exceptionFactory);
    }

    public ControllerValidationPostProcessor(
            Validator validator, ValidationExceptionFactory exceptionFactory) {
        this.setValidator(validator);
        this.setExceptionFactory(exceptionFactory);
    }

    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        Assert.notNull(exceptionFactory, "'exceptionFactory' must not be null");
        this.validator = validatorFactory.getValidator();
    }

    public void setValidator(Validator validator) {
        Assert.notNull(validator, "'validator' must not be null");
        // Unwrap to the native Validator with forExecutables support
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        } else if (validator instanceof SpringValidatorAdapter) {
            this.validator = validator.unwrap(Validator.class);
        } else {
            this.validator = validator;
        }
    }

    public void setExceptionFactory(ValidationExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(validator, "'validator' must not be null");
        Assert.notNull(exceptionFactory, "'validator' must not be null");
        Pointcut pointcut = new InheritedAnnotationMethodPointcut(
                controllerClassAnnotationType, controllerMethodAnnotationType);
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice(this.validator));
    }

    protected Advice createMethodValidationAdvice(Validator validator) {
        return (validator == null ?
                new MethodArgumentValidationInterceptor(exceptionFactory)
                : new MethodArgumentValidationInterceptor(validator, exceptionFactory));
    }
}
