package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by baomingfeng at 2017-09-07 20:16:54
 */
public class EnumStringValidatorForCharSequence
        implements ConstraintValidator<EnumString, CharSequence> {

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
    public boolean isValid(CharSequence field, ConstraintValidatorContext ctx) {
        if (field == null || (passIfEmpty && field.length() == 0)) {
            return true;
        }

        if (ignoreCase) {
            return enumValuesString.contains(field.toString().toLowerCase());
        }

        return enumValuesString.contains(field.toString());
    }

}