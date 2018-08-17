package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-08-13 12:07:21
 */
public class EnumValueValidatorForIterable
        implements ConstraintValidator<EnumValue, Iterable<?>> {

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
    public boolean isValid(Iterable<?> field, ConstraintValidatorContext context) {
        if (field == null) {
            return true;
        }

        for (Object obj : field) {
            if (!isValid(obj)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValid(Object obj) {
        if (obj == null || (passIfEmpty && "".equals(obj))) {
            return true;
        }

        return enumValues.contains(obj);
    }
}
