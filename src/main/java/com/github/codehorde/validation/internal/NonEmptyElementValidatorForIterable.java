package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.NonEmptyElement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class NonEmptyElementValidatorForIterable
        implements ConstraintValidator<NonEmptyElement, Iterable<?>> {

    private boolean passIfEmpty;

    @Override
    public void initialize(NonEmptyElement annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
    }

    @Override
    public boolean isValid(
            Iterable<?> iterable, ConstraintValidatorContext ctx) {
        if (iterable == null) {
            return true;
        }

        for (Object obj : iterable) {
            if (Util.isEmpty(obj, passIfEmpty)) {
                return false;
            }
        }

        return true;
    }

}
