package org.syndrome.samples.sodemtash.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public abstract class BaseAspect {

    protected Object[] getArgs(JoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    protected Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    protected Class<?> getClass(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass();
    }
}
