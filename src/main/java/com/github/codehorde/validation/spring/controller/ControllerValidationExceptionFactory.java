package com.github.codehorde.validation.spring.controller;

import com.tongbanjie.xd.commons.exception.ArgumentException;
import com.github.codehorde.validation.spring.ValidationExceptionFactory;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Bao.mingfeng at 2018-08-02 21:40:29
 */
public class ControllerValidationExceptionFactory implements ValidationExceptionFactory {

    @Override
    public RuntimeException buildValidationException(Set<ConstraintViolation<Object>> violations) {
        if (violations != null && !violations.isEmpty()) {
            ConstraintViolation<Object> violation = violations.iterator().next();//一条错误信息就够了
            if (violation != null) {
                String propertyPath = extractPrettyPropertyPath(violation);
                Object invalidValue = violation.getInvalidValue();
                String reason = violation.getMessage();
                throw new ArgumentException(propertyPath, reason, invalidValue);
            }
        }

        return new ArgumentException("参数错误");//default..
    }

    private String extractPrettyPropertyPath(ConstraintViolation<Object> violation) {
        Path _propertyPath = violation.getPropertyPath();

        if (_propertyPath instanceof PathImpl) {
            PathImpl propertyPath = (PathImpl) _propertyPath;
            StringBuilder methodPath = new StringBuilder();
            Iterator<Path.Node> iterator = propertyPath.iterator();
            boolean first = true;
            for (; iterator.hasNext(); ) {
                Path.Node next = iterator.next();
                ElementKind kind = next.getKind();
                switch (kind) {
                    case METHOD:
                    case BEAN:
                    case CONSTRUCTOR:
                    case RETURN_VALUE: {
                        break;
                    }

                    case PARAMETER:
                    case CROSS_PARAMETER:
                    case PROPERTY: {
                        if (!first) {
                            methodPath.append(".");
                        }
                        methodPath.append(next.getName());
                        first = false;
                        break;
                    }

                    default:
                        break;
                }
            }

            return methodPath.toString();
        }

        return extractLastPropertyPath(_propertyPath.toString());
    }

    private String extractLastPropertyPath(String propertyPath) {
        if (propertyPath == null) {
            return null;
        }

        int index = propertyPath.lastIndexOf(".");

        if (index < 0) {
            return propertyPath;
        }

        return propertyPath.substring(index + 1);
    }

}
