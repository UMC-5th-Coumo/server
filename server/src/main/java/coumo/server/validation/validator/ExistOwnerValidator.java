package coumo.server.validation.validator;

import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.repository.OwnerRepository;
import coumo.server.validation.annotation.ExistOwner;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistOwnerValidator implements ConstraintValidator<ExistOwner, Long> {

    private final OwnerRepository ownerRepository;

    @Override
    public void initialize(ExistOwner constraintAnnotation){
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long ownerId, ConstraintValidatorContext context){
        if(!ownerRepository.findById(ownerId).isPresent()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(ErrorStatus.OWNER_BAD_REQUEST.getMessage()).addConstraintViolation();
            return false;
        }

        return true;
    }
}
