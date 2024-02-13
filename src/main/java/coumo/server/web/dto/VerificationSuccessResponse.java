package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationSuccessResponse {
    private String verificationCode;

    public static VerificationSuccessResponse successWithCode(String verificationCode){
        return new VerificationSuccessResponse(verificationCode);
    }
}
