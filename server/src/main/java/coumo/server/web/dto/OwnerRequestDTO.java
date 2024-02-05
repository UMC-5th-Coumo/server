package coumo.server.web.dto;


import lombok.Getter;

public class OwnerRequestDTO {
    /* 회원가입(Web) */
    @Getter
    public static class JoinDTO{
        String loginId;
        String password;
        String name;
        String email;
        String phone;
    }

    @Getter
    public static class LoginDTO{
        String loginId;
        String password;
    }
}
