package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Pattern(regexp = "")
public @interface HttpUrl {

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    /**
     * 协议，默认支持http, https
     */
    String[] protocols() default {"http", "https"};

    /**
     * 域名正则表达式，空表示支持所有的域名
     */
    String hostRegex() default "";

    /**
     * 端口，-1表示支持所有的端口
     */
    int port() default -1;

    @OverridesAttribute(constraint = Pattern.class, name = "regexp") String regexp() default ".*";

    @OverridesAttribute(constraint = Pattern.class, name = "flags") Pattern.Flag[] flags() default {};

    String message() default "{com.github.codehorde.validation.constraints.HttpUrl.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        HttpUrl[] value();
    }
}