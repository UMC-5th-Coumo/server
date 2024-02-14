package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVerificationSuccessResponse {
    private String verificationCode;

    public static CustomerVerificationSuccessResponse customerSuccessWithCode(String verificationCode){
        return new CustomerVerificationSuccessResponse(verificationCode);
    }
}
