package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 检验是否为枚举中的属性值或者某个无参方法返回值
 * <p>
 * Created by Bao.mingfeng at 2018-08-13 12:04:56
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface EnumValue {

    /**
     * 枚举class
     */
    Class<? extends Enum> value();

    /**
     * 属性名称，设置property和method中一个
     */
    String property() default "";

    /**
     * 方法名称，设置property和method中一个
     */
    String method() default "";

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    String message() default "{com.github.codehorde.validation.constraints.EnumValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        EnumValue[] value();
    }
}
