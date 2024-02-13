package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationResponseDTO {
    private String loginId;
    private String verificationCode;

    public static VerificationResponseDTO success(String loginId, String verificationCode){
        return new VerificationResponseDTO(loginId, verificationCode);
    }

}
