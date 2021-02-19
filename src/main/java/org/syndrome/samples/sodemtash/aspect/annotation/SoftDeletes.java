package org.syndrome.samples.sodemtash.aspect.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SoftDeletes {
    boolean deleted();
}
