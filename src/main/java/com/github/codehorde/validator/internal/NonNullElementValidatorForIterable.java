package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.NonNullElement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by baomingfeng at 2017-12-03 19:19:32
 */
public class NonNullElementValidatorForIterable
        implements ConstraintValidator<NonNullElement, Iterable<?>> {

    private boolean passIfEmpty;

    @Override
    public void initialize(NonNullElement annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(
            Iterable<?> iterable, ConstraintValidatorContext ctx) {
        if (iterable == null) {
            return true;
        }

        for (Object obj : iterable) {
            if (obj == null
                    || (!passIfEmpty && (obj instanceof CharSequence) && ((CharSequence) obj).length() == 0)) {
                return false;
            }
        }

        return true;
    }

}
