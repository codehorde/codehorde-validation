package com.github.codehorde.validation.spring;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;

/**
 * Created by Bao.mingfeng at 2018-07-23 21:39:01
 *
 * @see org.springframework.validation.beanvalidation.MethodValidationPostProcessor
 */
public class InheritedAnnotationMethodPointcut implements Pointcut {

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    public InheritedAnnotationMethodPointcut(
            Class<? extends Annotation> classAnnotationType, Class<? extends Annotation> methodAnnotationType) {
        if (classAnnotationType == null) {
            this.classFilter = ClassFilter.TRUE;
        } else {
            this.classFilter = new AnnotationClassFilter(classAnnotationType, true);
        }

        if (methodAnnotationType == null) {
            this.methodMatcher = MethodMatcher.TRUE;
        } else {
            this.methodMatcher = new InheritedAnnotationMethodMatcher(methodAnnotationType);
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
        if (!(other instanceof InheritedAnnotationMethodPointcut)) {
            return false;
        }
        InheritedAnnotationMethodPointcut that = (InheritedAnnotationMethodPointcut) other;
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

}
