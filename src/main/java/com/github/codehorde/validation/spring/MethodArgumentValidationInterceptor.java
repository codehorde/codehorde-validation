package com.github.codehorde.validation.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.groups.Default;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-07-23 21:39:01
 */
public class MethodArgumentValidationInterceptor implements MethodInterceptor {

    private Validator validator;

    private ValidationExceptionFactory exceptionFactory;

    public MethodArgumentValidationInterceptor() {
    }

    public MethodArgumentValidationInterceptor(
            ValidationExceptionFactory exceptionFactory) {
        this(Validation.buildDefaultValidatorFactory(), exceptionFactory);
    }

    public MethodArgumentValidationInterceptor(
            ValidatorFactory validatorFactory, ValidationExceptionFactory exceptionFactory) {
        this(validatorFactory.getValidator(), exceptionFactory);
    }

    public MethodArgumentValidationInterceptor(
            Validator validator, ValidationExceptionFactory exceptionFactory) {
        this.validator = validator;
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?>[] groups = determineValidationGroups(invocation);

        ExecutableValidator executableValidator = validator.forExecutables();
        Method methodToValidate = invocation.getMethod();
        Set<ConstraintViolation<Object>> constraintViolations;

        try {
            constraintViolations = executableValidator.validateParameters(
                    invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        } catch (IllegalArgumentException ex) {
            // Probably a generic type mismatch between interface and impl as reported in SPR-12237 / HV-1011
            // Let's try to find the bridged method on the implementation class...
            methodToValidate = BridgeMethodResolver.findBridgedMethod(
                    ClassUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass()));

            constraintViolations = executableValidator.validateParameters(
                    invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        }

        if (!constraintViolations.isEmpty()) {
            throw exceptionFactory.buildValidationException(constraintViolations);
        }

        //noinspection UnnecessaryLocalVariable
        Object returnValue = invocation.proceed();

        /*
        constraintViolations = executableValidator.validateReturnValue(invocation.getThis(), methodToValidate, returnValue, groups);

        if (!constraintViolations.isEmpty()) {
            throw ValidationExceptionFactory.buildValidationException(constraintViolations);
        }*/

        return returnValue;
    }

    private final static Class<?>[] DefaultGroups = new Class[]{Default.class};

    /**
     * 获取方法执行时的校验组
     *
     * @param invocation MethodInvocation
     * @return 校验组Class数组
     */
    protected Class<?>[] determineValidationGroups(MethodInvocation invocation) {
        Validated validatedAnn = AnnotationUtils.findAnnotation(invocation.getMethod(), Validated.class);
        if (validatedAnn == null) {
            validatedAnn = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Validated.class);
        }
        Class<?>[] groups = DefaultGroups;
        if (validatedAnn != null) {
            Class<?>[] defGroups = validatedAnn.value();
            if (defGroups.length > 0) {
                groups = new Class[defGroups.length + 1];
                System.arraycopy(defGroups, 0, groups, 0, defGroups.length);
                groups[defGroups.length] = Default.class;
            }
        }
        return groups;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setExceptionFactory(ValidationExceptionFactory exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }
}
