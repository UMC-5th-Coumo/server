package coumo.server.validation.validator;

import coumo.server.validation.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckPageValidator implements ConstraintValidator<CheckPage, Long>{

    @Override
    public void initialize(CheckPage constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long pageId, ConstraintValidatorContext context) {
        if (pageId == null) return false;

        if (pageId >= 1) return true; // 1을 0으로 취급
        else
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("페이지 번호는 0 이상이어야 합니다.")
                    .addConstraintViolation();
            return false; // 0 이하의 값은 유효하지 않음.
        }

    }
}
