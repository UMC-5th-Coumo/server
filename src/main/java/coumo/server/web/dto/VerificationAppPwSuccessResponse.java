package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationAppPwSuccessResponse {
    private String message;
    private String verificationCode;

    public static VerificationAppPwSuccessResponse successWithCode(String message, String verificationCode){
        return new VerificationAppPwSuccessResponse(message,verificationCode);
    }
}
