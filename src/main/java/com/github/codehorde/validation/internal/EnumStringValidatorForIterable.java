package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class EnumStringValidatorForIterable
        implements ConstraintValidator<EnumString, Iterable<? extends CharSequence>> {

    private boolean ignoreCase;

    private boolean passIfEmpty;

    private Set<String> enumValuesString;

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(EnumString annotation) {
        this.ignoreCase = annotation.ignoreCase();
        this.passIfEmpty = annotation.passIfEmpty();
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
        if (field == null || (passIfEmpty && "".equals(field))) {
            return true;
        }

        if (ignoreCase) {
            return enumValuesString.contains(field.toLowerCase());
        }

        return enumValuesString.contains(field);
    }
}
