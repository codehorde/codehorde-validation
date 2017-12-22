package com.raycloud.codehorde.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by baomingfeng at 2017-09-27 19:16:27
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Numeric {

    String message() default "{com.raycloud.common.validator.constraints.Numeric.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 为空字符串时，是否忽略
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
}
