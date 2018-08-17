package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-08-13 12:06:49
 */
public class EnumValueValidatorForObject
        implements ConstraintValidator<EnumValue, Object> {

    private boolean passIfEmpty;

    private Set<Object> enumValues;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(EnumValue annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
        String property = annotation.property();
        String method = annotation.method();

        if ((property.length() == 0 && method.length() == 0)
                || (property.length() > 0 && method.length() > 0)) {
            throw new IllegalStateException("Error configuration of "
                    + annotation + ": select one from 'property' or 'method'");
        }

        Class<? extends Enum> enumClass = annotation.value();
        if (property.length() > 0) {
            enumValues = Util.getEnumPropertyValues(enumClass, property);
        } else {
            enumValues = Util.getEnumMethodValues(enumClass, method);
        }

    }

    @Override
    public boolean isValid(Object field, ConstraintValidatorContext ctx) {
        if (Util.isEmpty(field, passIfEmpty)) {
            return true;
        }

        return enumValues.contains(field);
    }
}
