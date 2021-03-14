package feign.hystrix;

import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author tsai
 * 针对泛型返回值类型情况下，需要显式指明class type
 */
@java.lang.annotation.Target(FIELD)
@Retention(RUNTIME)
public @interface MockGuide {

    boolean enable() default false;

    Class<?> type() default Object.class;
}
