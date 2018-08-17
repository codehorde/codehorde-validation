package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.In;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class InValidator
        implements ConstraintValidator<In, Object> {

    private boolean passIfEmpty;

    private Set<String> values;

    public final void initialize(final In annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
        this.values = new HashSet<String>();
        String[] defVal = annotation.value();
        Collections.addAll(values, defVal);
    }

    public final boolean isValid(final Object value, final ConstraintValidatorContext context) {
        if (value == null
                || (passIfEmpty && (value instanceof CharSequence) && ((CharSequence) value).length() == 0)) {
            return true;
        }
        return values.contains(valueOf(value)); // check if value is in this.values
    }


    private String valueOf(Object obj) {
        if (obj == null) {
            return null;
        }

        return String.valueOf(obj);
    }
}