package com.github.codehorde.validation.internal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 补充Max校验的实现，如List<Integer>, Set<Long>
 * <p>
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class MaxValidatorForNumberIterable
        implements ConstraintValidator<Max, Iterable<? extends Number>> {

    private long maxValue;

    @Override
    public void initialize(Max maxValue) {
        this.maxValue = maxValue.value();
    }

    @Override
    public boolean isValid(Iterable<? extends Number> value, ConstraintValidatorContext context) {
        //null values are valid
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

        // handling of NaN, positive infinity and negative infinity
        if (value instanceof Double) {
            if ((Double) value == Double.NEGATIVE_INFINITY) {
                return true;
            } else if (Double.isNaN((Double) value) || (Double) value == Double.POSITIVE_INFINITY) {
                return false;
            }
        } else if (value instanceof Float) {
            if ((Float) value == Float.NEGATIVE_INFINITY) {
                return true;
            } else if (Float.isNaN((Float) value) || (Float) value == Float.POSITIVE_INFINITY) {
                return false;
            }
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(maxValue)) < 1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(maxValue)) < 1;
        } else {
            long longValue = value.longValue();
            return longValue <= maxValue;
        }
    }
}
