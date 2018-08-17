package com.github.codehorde.validation.internal;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Bao.mingfeng at 2018-08-07 11:04:52
 */
public class NotBlankValidatorForIterable
        implements ConstraintValidator<NotBlank, Iterable<? extends CharSequence>> {

    @Override
    public void initialize(NotBlank constraintAnnotation) {

    }

    @Override
    public boolean isValid(Iterable<? extends CharSequence> iterable, ConstraintValidatorContext context) {
        if (iterable == null) {
            return true;
        }

        for (CharSequence element : iterable) {
            if (!isValid(element.toString())) {
                return false;
            }
        }

        return true;
    }

    private boolean isValid(CharSequence element) {
        return element.toString().trim().length() > 0;
    }
}
