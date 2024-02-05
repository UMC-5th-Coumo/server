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
        Long id;
        String loginId;
        String password;
        private final String token;
        LocalDateTime createdAt;

    }
}
