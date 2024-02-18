package coumo.server.web.dto;

import lombok.Getter;
@Getter
public class LoginAppIdDTO {
    private String loginId;

    public LoginAppIdDTO(String loginId){
        this.loginId = loginId;
    }
}