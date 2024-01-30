package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class CustomerDTO {
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
    public CustomerDTO(Long id, String name, Gender gender, String birthday, String ageGroup, String phone, Integer totalStamp, LocalDateTime createdAt, LocalDateTime updatedAt){
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
}

