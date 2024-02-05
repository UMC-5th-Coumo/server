package coumo.server.validation.validator;

import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.repository.NoticeRepository;
import coumo.server.validation.annotation.ExistNotice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistNoticeValidator implements ConstraintValidator<ExistNotice, Long> {

    private final NoticeRepository noticeRepository;

    @Override
    public void initialize(ExistNotice constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long noticeId, ConstraintValidatorContext context){
        if(!noticeRepository.findById(noticeId).isPresent()) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(ErrorStatus.NOTICE_BAD_REQUEST.getMessage()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
