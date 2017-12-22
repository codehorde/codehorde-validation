package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.Numeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Created by baomingfeng at 2017-09-27 19:17:13
 */
public class NumericValidatorForCharSequence implements ConstraintValidator<Numeric, CharSequence> {

    private boolean passIfEmpty;
    private BigDecimal min;
    private BigDecimal max;
    private int scale;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        this.passIfEmpty = constraintAnnotation.passIfEmpty();
        this.min = convert(constraintAnnotation.min());
        this.max = convert(constraintAnnotation.max());
        this.scale = constraintAnnotation.scale();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || (passIfEmpty && value.length() == 0)) {
            return true;
        }

        BigDecimal decimal;

        try {
            decimal = new BigDecimal(value.toString());
        } catch (NumberFormatException ignore) {
            return false;
        }

        if (min != null) {
            if (decimal.compareTo(min) < 0) {
                return false;
            }
        }

        if (max != null) {
            if (decimal.compareTo(max) > 0) {
                return false;
            }
        }

        if (scale > -1 && decimal.scale() > scale) {
            return false;
        }

        return true;
    }

    private BigDecimal convert(String number) {
        if (number == null || number.length() == 0) {
            return null;
        }
        return new BigDecimal(number);
    }
}
