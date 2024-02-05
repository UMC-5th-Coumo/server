package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.Getter;

public class CustomerRequestDTO {
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

    @Getter
    public static class CustomerLoginDTO{
        String loginId;
        String password;
    }
}
