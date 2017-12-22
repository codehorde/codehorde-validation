package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.EscapeString;
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
public class EscapeStringValidatorForCharSequence
        implements ConstraintValidator<EscapeString, CharSequence> {

    private boolean passIfEmpty;

    private Whitelist whitelist;

    public void initialize(EscapeString constraintAnn) {
        this.passIfEmpty = constraintAnn.passIfEmpty();
        this.whitelist = Util.getWhitelistConfig(constraintAnn);
    }

    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || (passIfEmpty && value.length() == 0)) {
            return true;
        }

        if (whitelist == null) {
            return !Util.hasEscapeChar(value.toString());
        }

        return Jsoup.isValid(value.toString(), whitelist);
    }
}
