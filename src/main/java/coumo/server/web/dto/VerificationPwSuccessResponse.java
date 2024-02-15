package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationPwSuccessResponse {
    private String message;
    private String verificationCode;

    public static VerificationPwSuccessResponse successWithCode(String message, String verificationCode){
        return new VerificationPwSuccessResponse(message,verificationCode);
    }
}
