package com.github.codehorde.validator.internal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 补充Min校验的实现，如List<Integer>, Set<Long>
 * <p>
 * Created by baomingfeng at 2017-11-02 10:05:25
 */
public class MinValidatorForNumberIterable
        implements ConstraintValidator<Min, Iterable<? extends Number>> {

    private long minValue;

    @Override
    public void initialize(Min minValue) {
        this.minValue = minValue.value();
    }

    @Override
    public boolean isValid(Iterable<? extends Number> value, ConstraintValidatorContext context) {
        // null values are valid
        if (value == null) {
            return true;
        }

        for (Number num : value) {
            if (!validate(num)) {
                return false;
            }
        }

        return true;
    }

    private boolean validate(Number value) {
        if (value == null) {
            return false;
        }
        
        //handling of NaN, positive infinity and negative infinity
        if (value instanceof Double) {
            if ((Double) value == Double.POSITIVE_INFINITY) {
                return true;
            } else if (Double.isNaN((Double) value) || (Double) value == Double.NEGATIVE_INFINITY) {
                return false;
            }
        } else if (value instanceof Float) {
            if ((Float) value == Float.POSITIVE_INFINITY) {
                return true;
            } else if (Float.isNaN((Float) value) || (Float) value == Float.NEGATIVE_INFINITY) {
                return false;
            }
        }

        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(minValue)) > -1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(minValue)) > -1;
        } else {
            long longValue = value.longValue();
            return longValue >= minValue;
        }
    }
}
