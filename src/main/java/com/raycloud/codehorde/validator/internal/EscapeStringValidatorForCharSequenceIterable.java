package com.raycloud.codehorde.validator.internal;

import com.raycloud.codehorde.validator.constraints.EscapeString;
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
public class EscapeStringValidatorForCharSequenceIterable
        implements ConstraintValidator<EscapeString, Iterable<? extends CharSequence>> {

    private Whitelist whitelist;

    public void initialize(EscapeString constraintAnn) {
        this.whitelist = Util.getWhitelistConfig(constraintAnn);
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
        if (whitelist == null) {
            return !Util.hasEscapeChar(value.toString());
        }

        return Jsoup.isValid(value.toString(), whitelist);
    }
}
