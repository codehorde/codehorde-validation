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
 */
public class XSSValidatorForCharSequenceIterable
        implements ConstraintValidator<XSS, Iterable<? extends CharSequence>> {

    private boolean passIfEmpty;

    private Whitelist whitelist;

    public void initialize(XSS constraintAnn) {
        this.passIfEmpty = constraintAnn.passIfEmpty();
        this.whitelist = XSSDescriber.mappedWhitelist(constraintAnn);
    }

    public boolean isValid(Iterable<? extends CharSequence> iterable, ConstraintValidatorContext context) {
        if (iterable == null) {
            return true;
        }

        for (CharSequence value : iterable) {
            if (!isValid(value)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValid(CharSequence value) {
        if (Util.isEmpty(value, passIfEmpty)) {
            return true;
        }

        if (whitelist == null) {
            return !XSSDescriber.hasEscapeChar(value.toString());
        }

        return Jsoup.isValid(value.toString(), whitelist);
    }
}
