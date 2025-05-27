package hieunv.dev.products.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // The annotation will be available at runtime
@Target(ElementType.METHOD) // This annotation can only be applied to methods
public @interface DeveloperInfo {

    String name();
    String date();
    String description() default "No description provided";
}
