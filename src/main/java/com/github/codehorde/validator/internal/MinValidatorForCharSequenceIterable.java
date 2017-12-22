package com.github.codehorde.validator.internal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * 补充Min校验的实现，如List<String>, Set<String>
 * <p>
 * Created by baomingfeng at 2017-11-01 20:02:07
 */
public class MinValidatorForCharSequenceIterable
        implements ConstraintValidator<Min, Iterable<? extends CharSequence>> {

    private BigDecimal minValue;

    @Override
    public void initialize(Min minValue) {
        this.minValue = BigDecimal.valueOf(minValue.value());
    }

    @Override
    public boolean isValid(Iterable<? extends CharSequence> value, ConstraintValidatorContext context) {
        //null values are valid
        if (value == null) {
            return true;
        }

        for (CharSequence str : value) {
            if (!validate(str)) {
                return false;
            }
        }

        return true;
    }

    private boolean validate(CharSequence str) {
        try {
            return new BigDecimal(str.toString()).compareTo(minValue) > -1;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
