package com.github.codehorde.validation.internal;

import com.github.codehorde.validation.constraints.HttpUrl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.MalformedURLException;
import java.util.regex.Pattern;

/**
 * Created by Bao.mingfeng at 2018-07-23 19:27:47
 */
public class HttpUrlValidator implements ConstraintValidator<HttpUrl, CharSequence> {

    private boolean passIfEmpty;

    private String[] protocols;

    private Pattern hostRegex;

    private int port;

    @Override
    public void initialize(HttpUrl annotation) {
        this.passIfEmpty = annotation.passIfEmpty();
        this.protocols = annotation.protocols();
        if (annotation.hostRegex().length() != 0) {
            this.hostRegex = Pattern.compile(annotation.hostRegex());
        }
        this.port = annotation.port();
    }

    @Override
    public boolean isValid(CharSequence field, ConstraintValidatorContext constraintValidatorContext) {
        if (Util.isEmpty(field, passIfEmpty)) {
            return true;
        }

        java.net.URL url;
        try {
            url = new java.net.URL(field.toString());
        } catch (MalformedURLException e) {
            return false;
        }

        if (protocols != null && protocols.length > 0) {
            if (!supports(url)) {
                return false;
            }
        }

        if (hostRegex != null && !hostRegex.matcher(url.getHost()).matches()) {
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
