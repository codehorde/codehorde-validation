package com.github.codehorde.validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by baomingfeng at 2017-11-15 20:00:49
 */
public abstract class Util {

    public static <T> void echo(Set<ConstraintViolation<T>> constraintViolations) {
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            System.err.println("rootBeanClass: " + constraintViolation.getRootBeanClass()
                    + ", propertyPath: " + constraintViolation.getPropertyPath()
                    + ", invalidValue: " + constraintViolation.getInvalidValue()
                    + ", message: " + constraintViolation.getMessage());
        }
    }
}
