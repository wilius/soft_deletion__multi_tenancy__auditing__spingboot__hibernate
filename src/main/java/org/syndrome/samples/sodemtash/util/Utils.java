package org.syndrome.samples.sodemtash.util;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

public class Utils {
    private Utils() {
    }

    public static Object findProcessorMethod(HttpServletRequest request) {
        try {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            if (webApplicationContext == null) {
                return null;
            }

            Map<String, HandlerMapping> handlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(webApplicationContext, HandlerMapping.class, true, false);
            for (HandlerMapping handlerMapping : handlerMappingMap.values()) {
                HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
                if (handlerExecutionChain != null) {
                    return handlerExecutionChain.getHandler();
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T extends Annotation> Optional<T> extractAnnotationFromProcessorMethod(Object handler, Class<T> annotationClass) {
        T result = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            result = method.getMethodAnnotation(annotationClass);
            if (result == null) {
                result = method.getBeanType().getAnnotation(annotationClass);
            }
        }

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(result);
    }
}
