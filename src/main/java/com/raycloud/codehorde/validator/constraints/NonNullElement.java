package com.raycloud.codehorde.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验集合中每个元素不能为NULL
 * <p>
 * Created by baomingfeng at 2017-12-03 19:19:32
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface NonNullElement {

    /**
     * 为空字符串时，是否忽略
     */
    boolean passIfEmpty() default true;

    String message() default "{com.raycloud.common.validator.constraints.NonNullElement.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}