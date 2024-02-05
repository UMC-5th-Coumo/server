package coumo.server.validation.annotation;

import coumo.server.validation.validator.CheckPageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckPageValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPage {

    String message() default "유효하지 않은 페이지입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
