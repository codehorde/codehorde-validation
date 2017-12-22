package com.raycloud.codehorde.validator.internal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;
import java.math.BigDecimal;

/**
 * 补充Max校验的实现，如List<String>, Set<String>
 * <p>
 * Created by baomingfeng at 2017-11-02 11:09:20
 */
public class MaxValidatorForCharSequenceIterable
        implements ConstraintValidator<Max, Iterable<? extends CharSequence>> {

    private BigDecimal maxValue;

    @Override
    public void initialize(Max maxValue) {
        this.maxValue = BigDecimal.valueOf(maxValue.value());
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
            return new BigDecimal(str.toString()).compareTo(maxValue) < 1;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
