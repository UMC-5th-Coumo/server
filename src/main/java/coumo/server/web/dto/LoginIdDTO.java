package coumo.server.web.dto;

import lombok.Getter;

@Getter
public class LoginIdDTO {
    private String loginId;

    public LoginIdDTO(String loginId){
        this.loginId = loginId;
    }
}
