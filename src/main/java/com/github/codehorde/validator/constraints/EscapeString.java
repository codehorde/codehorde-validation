package com.github.codehorde.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 摘自org.hibernate.validator.constraints.SafeHtml，PassType补充了NONE_ESCAPE
 * <p>
 * 有关XSS问题，处理的原则：<br>
 * 1. 不是所有的field都需要XSS处理，如枚举的字符串只需要做枚举校验即可com.github.common.validator.constraints.EnumString<br>
 * 2. 需要XSS处理的字段，需要补上该注解，留意PassType区别，如NONE_ESCAPE情况传值(前端)时就需要处理掉这些特殊字符<br>
 * <p>
 * Validate a rich text value provided by the user to ensure that it contains no malicious code, such as embedded
 * &lt;script&gt; elements.
 * <p>
 * //@see org.hibernate.validator.constraints.SafeHtml
 * <p>
 * Created by baomingfeng at 2017-09-07 20:28:22
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = { })
public @interface EscapeString {

    String message() default "{com.github.common.validator.constraints.EscapeString.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 为空字符串时，是否忽略
     */
    boolean passIfEmpty() default true;

    /**
     * @return The built-in whitelist type which will be applied to the rich text value
     */
    PassType passType() default PassType.RELAXED;

    /**
     * @return Additional whitelist tags which are allowed on top of the tags specified by the
     * {@link #passType()}.
     */
    String[] additionalTags() default {};

    /**
     * Defines several {@code @org.hibernate.validator.constraints.SafeHtml} annotations on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        EscapeString[] value();
    }

    /**
     * 约定的XSS限制类型，自定义拓展
     */
    public enum PassType {
        /**
         * 不允许出现任何转义的字符(default escapes: '<', '>', '&', '\'', '\"' ).
         */
        NONE_ESCAPE,

        /**
         * 不允许出现任何html标签
         */
        NONE_TAG,

        /**
         * 只允许出现简单的text类型标签，e.g. <b>, <em>, <i>, <u>, ..
         */
        SIMPLE_TEXT,

        /**
         * This whitelist allows a fuller range of text nodes:
         * <code>a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul</code>
         * , and appropriate attributes.
         * <p/>
         * Links (<code>a</code> elements) can point to <code>http, https, ftp, mailto</code>, and have an enforced
         * <code>rel=nofollow</code> attribute.
         * <p/>
         * Does not allow images.
         */
        BASIC,

        /**
         * This whitelist allows the same text tags as {@link PassType#BASIC}, and also allows <code>img</code>
         * tags,
         * with
         * appropriate attributes, with <code>src</code> pointing to <code>http</code> or <code>https</code>.
         */
        BASIC_WITH_IMAGES,

        /**
         * This whitelist allows a full range of text and structural body HTML:
         * <code>a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li,
         * ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul</code>
         * <p/>
         * Links do not have an enforced <code>rel=nofollow</code> attribute, but you can add that if desired.
         */
        RELAXED
    }
}

