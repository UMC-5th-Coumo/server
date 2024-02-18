package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationAppResponseDTO {
    private String loginId;
    private String verificationCode;

    public static  VerificationAppResponseDTO success(String loginId, String verificationCode){
        return new VerificationAppResponseDTO(loginId, verificationCode);
    }
}