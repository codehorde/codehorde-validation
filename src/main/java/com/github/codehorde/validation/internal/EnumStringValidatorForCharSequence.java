package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
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
        if (Util.isEmpty(field, passIfEmpty)) {
            return true;
        }

        if (ignoreCase) {
            return enumValuesString.contains(field.toString().toLowerCase());
        }

        return enumValuesString.contains(field.toString());
    }

}