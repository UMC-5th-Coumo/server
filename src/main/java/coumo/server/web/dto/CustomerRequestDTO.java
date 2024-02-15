package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class CustomerRequestDTO {

    /* 회원가입(App) */
    @Getter
    public static class CustomerJoinDTO {
        String loginId;
        String password;
        String name;
        String birthday;
        Gender gender;
        String email;
        String phone;
    }

    /* 로그인(App) */
    @Getter
    public static class CustomerLoginDTO {
        String loginId;
        String password;
    }


    /* 로그인 중복확인(App) */
    @Getter
    public static class CheckCustomerLoginIdDTO {
        @NotBlank(message = "로그인 ID는 필수 입력 항목입니다.")
        String loginId;
    }

    /* 아이디 찾기(App) */
    @Getter
    public static class VerificationRequest {
        String name;
        String phone;
    }

    @Getter
    public static class VerificationCodeDTO{
        String phone;
        String verificationCode;
    }

    /* 회원 가입 시 휴대폰 인증 */
    @Getter
    public static class CustomerVerificationRequest {
        String name;
        String phone;
    }

    @Getter
    public static class CustomerVerificationCodeDTO{
        String phone;
        String verificationCode;
    }
}