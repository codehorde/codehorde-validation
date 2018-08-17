package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 判断校验的字段是否在某个取值列表中
 * <p>
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface In {

    /**
     * String数组取值列表，如果校验的字段为数组或其他，则转成字符串作比较
     */
    String[] value();

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    String message() default "{com.github.codehorde.validation.constraints.In.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        In[] value();
    }
}