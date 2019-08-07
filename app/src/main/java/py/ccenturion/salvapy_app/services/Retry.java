package py.ccenturion.salvapy_app.services;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by isabelinorolandolopezroman on 21/7/17.
 */

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Retry {
    int value() default 3;
}
