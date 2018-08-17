package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Numeric {

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    /**
     * 最小值（默认：0）
     */
    String min() default "0";

    /**
     * 最大值（默认：Long.MAX_VALUE）
     */
    String max() default "9223372036854775807";

    /**
     * 小数部分位数最大限制（该值小于0表示对数字小数无限制）
     */
    int scale() default -1;

    String message() default "{com.github.codehorde.validation.constraints.Numeric.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Numeric[] value();
    }
}
