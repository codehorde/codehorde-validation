package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.Numeric;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
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
