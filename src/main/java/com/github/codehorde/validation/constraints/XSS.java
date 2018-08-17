package com.github.codehorde.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 需要XSS处理的字段，支持多个XSS过滤级别
 * <p>
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 *
 * @see org.hibernate.validator.constraints.SafeHtml
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface XSS {

    /**
     * 为空字符串时，是否校验通过
     */
    boolean passIfEmpty() default true;

    /**
     * XSS过滤级别
     */
    PassType passType() default PassType.NONE_TAG;

    /**
     * 额外支持的tag列表
     */
    String[] additionalTags() default {};
    
    /**
     * 约定的XSS限制类型
     */
    enum PassType {

        /**
         * 不允许出现任何需要html转义的字符(转义字符: <, >, &, ', " ).
         *
         * @see <a href='https://stackoverflow.com/questions/7381974/which-characters-need-to-be-escaped-on-html'>Which characters need to be escaped on HTML?</a>
         */
        NONE_ESCAPE,

        /**
         * 不允许出现任何html标签
         */
        NONE_TAG,

        /**
         * 最多只允许出现简单的text类型标签，e.g. <b>, <em>, <i>, <u>, ..
         */
        SIMPLE_TEXT,

        /**
         * 允许出现的标签列表:
         * <code>a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, strike, strong, sub, sup, u, ul</code>
         * <p/>
         * 其中链接 (<code>a</code>) 标签允许的指向地址：
         * <code>http, https, ftp, mailto</code>, <code>rel=nofollow</code>.
         * <p/>
         * 不支持图片
         */
        BASIC,

        /**
         * 在 {@link PassType#BASIC}基础上允许 <code>img</code>标签，包括相关的属性如：src='<code>http</code> or <code>https</code>..'
         */
        BASIC_WITH_IMAGES,

        /**
         * 允许所有文本和结构体范围的HTML:
         * <code>a, b, blockquote, br, caption, cite, code, col, colgroup, dd, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li,
         * ol, p, pre, q, small, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul</code>
         * <p/>
         * Links do not have an enforced <code>rel=nofollow</code> attribute, but you can add that if desired.
         */
        RELAXED
    }

    String message() default "{com.github.codehorde.validation.constraints.XSS.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        XSS[] value();
    }

}

