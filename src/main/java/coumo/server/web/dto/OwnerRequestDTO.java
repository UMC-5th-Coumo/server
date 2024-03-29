package coumo.server.web.dto;


import jakarta.validation.constraints.NotBlank;
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

    /* 로그인(Web) */
    @Getter
    public static class LoginDTO{
        String loginId;
        String password;
    }

    /* 로그인 중복확인(Web) */
    @Getter
    public static class CheckLoginIdDTO {
        @NotBlank(message = "로그인 ID는 필수 입력 항목입니다.")
        String loginId;
    }

    /* 아이디 찾기(WEB) */
    @Getter
    public static class OwnerVerificationRequest {
        String name;
        String phone;
    }

    @Getter
    public static class OwnerVerificationCodeDTO{
        String phone;
        String verificationCode;
    }

    @Getter
    public static class OwnerLoginVerificationRequest {
        String name;
        String phone;
    }

    @Getter
    public static class OwnerLoginVerificationCodeDTO{
        String phone;
        String verificationCode;
    }

    @Getter
    public static class OwnerPasswordResetSendCodeDTO{
        String loginId;
        String phone;
    }

    @Getter
    public static class OwnerPasswordVerifyCodeDTO {
        String loginId;
        String phone;
        String verificationCode;
    }

    @Getter
    public static class OwnerPasswordResetDTO{
        String loginId;
        String newPassword;
    }
}
