package feign.hystrix;

import java.lang.annotation.Retention;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author tsai
 * 针对泛型返回值类型情况下，需要配合@MockGuide使用
 */
@java.lang.annotation.Target(METHOD)
@Retention(RUNTIME)
public @interface Mock {

//    boolean enable() default false;
//
//    Class<?> type() default Object.class;
}
