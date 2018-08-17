package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.XSS;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validate that the string does not contain malicious code.
 * <p>
 * It uses <a href="http://www.jsoup.org">JSoup</a> as the underlying parser/sanitizer library.
 * <p>
 * //@see org.hibernate.validator.internal.constraintvalidators.SafeHtmlValidator
 *
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class XSSValidatorForCharSequence
        implements ConstraintValidator<XSS, CharSequence> {

    private boolean passIfEmpty;

    private Whitelist whitelist;

    public void initialize(XSS constraintAnn) {
        this.passIfEmpty = constraintAnn.passIfEmpty();
        this.whitelist = XSSDescriber.mappedWhitelist(constraintAnn);
    }

    public boolean isValid(CharSequence field, ConstraintValidatorContext context) {
        if (Util.isEmpty(field, passIfEmpty)) {
            return true;
        }

        if (whitelist == null) {
            return !XSSDescriber.hasEscapeChar(field.toString());
        }

        return Jsoup.isValid(field.toString(), whitelist);
    }
}
