package com.github.codehorde.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Created by baomingfeng at 2017-09-07 20:16:13
 * <p>
 * 校验字段串是否为一枚举类型
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface EnumString {

    String message() default "{com.github.common.validator.constraints.EnumString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举class
     */
    Class<? extends Enum> value();

    /**
     * 为空字符串时，是否忽略
     */
    boolean passIfEmpty() default true;

    /**
     * 是否区分大小写
     */
    boolean ignoreCase() default false;
}