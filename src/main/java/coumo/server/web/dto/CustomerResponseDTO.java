package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private Gender gender;
    private String birthday;
    private String ageGroup;
    private String phone;
    private Integer totalStamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CustomerResponseDTO(Long id, String name, Gender gender, String birthday, String ageGroup, String phone, Integer totalStamp, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.birthday=birthday;
        this.ageGroup=ageGroup;
        this.phone=phone;
        this.totalStamp=totalStamp;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    // 회원가입
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerJoinResultDTO {
        long id;
        String loginId;
        String name;
        LocalDateTime createdAt;
    }


    //로그인
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CustomerLoginResultDTO{
        Long customerId;
        private final String token;
    }

    //로그인 중복확인
    @Builder
    @Getter
    public static class CheckCustomerLoginIdResponseDTO{
        private String loginId;
    }

    //내 프로필 조회
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CustomerMyPageDTO{
        String name;
        String birthday;
        String gender;
        String phone;
    }

}

