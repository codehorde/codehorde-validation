package com.github.codehorde.validator.internal;

import com.github.codehorde.validator.constraints.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

/**
 * Created by baomingfeng at 2017-09-28 10:21:24
 */
public class URLValidator implements ConstraintValidator<URL, CharSequence> {

    private boolean passIfEmpty;
    private String[] protocols;
    private Pattern hostPattern;
    private int port;

    @Override
    public void initialize(URL annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
        this.protocols = annotation.protocols();
        if (annotation.hostPattern().length() != 0) {
            this.hostPattern = Pattern.compile(annotation.hostPattern());
        }
        this.port = annotation.port();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || (passIfEmpty && value.length() == 0)) {
            return true;
        }

        java.net.URL url;
        try {
            url = new java.net.URL(value.toString());
        } catch (MalformedURLException e) {
            return false;
        }

        if (protocols != null && protocols.length > 0) {
            if (!supports(url)) {
                return false;
            }
        }

        if (hostPattern != null && !hostPattern.matcher(url.getHost()).matches()) {
            return false;
        }

        return port == -1 || url.getPort() == port;
    }

    private boolean supports(java.net.URL url) {
        for (String protocol : protocols) {
            if (protocol.equalsIgnoreCase(url.getProtocol())) {
                return true;
            }
        }
        return false;
    }
}
