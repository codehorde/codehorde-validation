package com.github.codehorde.validator.spring;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Pointcut：为每个Controller补上方法校验
 * <p>
 * Created by baomingfeng at 2017-09-11 14:58:12
 *
 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
public class ControllerMethodMatchingPointcut implements Pointcut {

    private final static Class<? extends Annotation>
            defaultControllerClassAnnotationType = Controller.class;

    private final static Class<? extends Annotation>
            defaultControllerMethodAnnotationType = RequestMapping.class;

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    public ControllerMethodMatchingPointcut() {
        this(null, null);
    }

    public ControllerMethodMatchingPointcut(
            Class<? extends Annotation> classAnnotationType, Class<? extends Annotation> methodAnnotationType) {
        if (classAnnotationType == null) {
            this.classFilter = new AnnotationClassFilter(defaultControllerClassAnnotationType, true);
        } else {
            this.classFilter = new AnnotationClassFilter(classAnnotationType, true);
        }

        if (methodAnnotationType == null) {
            this.methodMatcher = new ControllerAnnotationMethodMatcher(defaultControllerMethodAnnotationType);
        } else {
            this.methodMatcher = new ControllerAnnotationMethodMatcher(methodAnnotationType);
        }
    }


    @Override
    public ClassFilter getClassFilter() {
        return this.classFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.methodMatcher;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ControllerMethodMatchingPointcut)) {
            return false;
        }
        ControllerMethodMatchingPointcut that = (ControllerMethodMatchingPointcut) other;
        return ObjectUtils.nullSafeEquals(that.classFilter, this.classFilter) &&
                ObjectUtils.nullSafeEquals(that.methodMatcher, this.methodMatcher);
    }

    @Override
    public int hashCode() {
        int code = 17;
        if (this.classFilter != null) {
            code = 37 * code + this.classFilter.hashCode();
        }
        if (this.methodMatcher != null) {
            code = 37 * code + this.methodMatcher.hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + this.classFilter + ", " + this.methodMatcher;
    }

    public static class ControllerAnnotationMethodMatcher extends StaticMethodMatcher {

        private final Class<? extends Annotation> annotationType;
        private final boolean checkInherited;

        public ControllerAnnotationMethodMatcher(Class<? extends Annotation> annotationType) {
            this(annotationType, false);
        }

        public ControllerAnnotationMethodMatcher(Class<? extends Annotation> annotationType, boolean checkInherited) {
            this.annotationType = annotationType;
            this.checkInherited = checkInherited;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            //validateApplicable(method);
            if (inheritedMatch(method, targetClass)) {
                return true;
            }
            // The method may be on an interface, so let's check on the target class as well.
            Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            return specificMethod != method && inheritedMatch(specificMethod, targetClass);
        }

        @SuppressWarnings("unused")
        private boolean inheritedMatch(Method method, Class<?> targetClass) {
            return this.checkInherited ?
                    (AnnotationUtils.findAnnotation(method, this.annotationType) != null) :
                    method.isAnnotationPresent(this.annotationType);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ControllerAnnotationMethodMatcher)) {
                return false;
            }
            ControllerAnnotationMethodMatcher otherMm = (ControllerAnnotationMethodMatcher) other;
            return this.annotationType.equals(otherMm.annotationType)
                    && this.checkInherited == otherMm.checkInherited;
        }

        @Override
        public int hashCode() {
            return this.annotationType.hashCode();
        }

        @Override
        public String toString() {
            return getClass().getName() + ": " + this.annotationType;
        }
    }
}
