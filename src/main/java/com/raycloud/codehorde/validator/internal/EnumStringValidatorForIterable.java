package com.raycloud.codehorde.validator.internal;

import com.raycloud.codehorde.validator.constraints.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by baomingfeng at 2017-09-07 20:17:22
 */
public class EnumStringValidatorForIterable
        implements ConstraintValidator<EnumString, Iterable<? extends CharSequence>> {

    private boolean ignoreCase;

    private Set<String> enumValuesString;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(EnumString annotation) {
        this.ignoreCase = annotation.ignoreCase();
        this.enumValuesString = new HashSet<String>();
        Class<? extends Enum> enumClass = annotation.value();
        Enum[] enumConstants = enumClass.getEnumConstants();
        for (Enum e : enumConstants) {
            String name = annotation.ignoreCase() ? e.name().toLowerCase() : e.name();
            enumValuesString.add(name);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(
            Iterable<? extends CharSequence> field, ConstraintValidatorContext ctx) {
        if (field == null) {
            return true;
        }

        for (CharSequence str : field) {
            if (!isValid(str.toString())) {
                return false;
            }
        }

        return true;
    }

    private boolean isValid(String field) {
        if (ignoreCase) {
            return enumValuesString.contains(field.toLowerCase());
        }
        return enumValuesString.contains(field);
    }
}
