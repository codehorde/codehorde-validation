package com.raycloud.codehorde.validator.spring;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Advice：校验每个Controller执行那个的方法参数、返回值等
 * <p>
 * Created by baomingfeng at 2017-09-11 14:58:12
 *
 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
@SuppressWarnings("serial")
public class ControllerMethodValidationPostProcessor
        extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private Validator validator;

    public ControllerMethodValidationPostProcessor() {
    }

    public ControllerMethodValidationPostProcessor(ValidatorFactory validatorFactory) {
        this(validatorFactory.getValidator());
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public ControllerMethodValidationPostProcessor(Validator validator) {
        // Unwrap to the native Validator with forExecutables support
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        } else if (validator instanceof SpringValidatorAdapter) {
            this.validator = validator.unwrap(Validator.class);
        } else {
            this.validator = validator;
        }
    }

    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new ControllerMethodMatchingPointcut();
        this.advisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice(this.validator));
    }

    protected Advice createMethodValidationAdvice(Validator validator) {
        return (validator != null ? new MethodValidationInterceptor(validator) : new MethodValidationInterceptor());
    }

}
