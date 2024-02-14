package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class OwnerResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        Long id;
        String loginId;
        String name;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LoginResultDTO{
        Long ownerId;
        Long storeId;
        private final String token;
        boolean isWrite;
        LocalDateTime createdAt;
        String name;
        String loginId;
        String email;
        String phone;
    }

    @Builder
    @Getter
    public static class CheckLoginIdResponseDTO{
        private String loginId;
    }

    @Builder
    @Getter
    public static class MyPageDTO{
        String name;
        String loginId;
        String email;
        String phone;
    }
}
