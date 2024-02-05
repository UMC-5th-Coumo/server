package coumo.server.validation.annotation;

import coumo.server.validation.validator.ExistOwnerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistOwnerValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistOwner {

    String message() default "존재하지 않는 사장님입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
