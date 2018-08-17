package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验字段串是否为指定的枚举类型
 * <p>
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface EnumString {
    /**
     * 枚举class
     */
    Class<? extends Enum> value();

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    /**
     * 是否区分大小写
     */
    boolean ignoreCase() default false;

    String message() default "{com.github.codehorde.validation.constraints.EnumString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EnumString[] value();
    }
}