package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private Gender gender;
    private String birthday;
    private Integer totalStamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CustomerDTO(Integer id, String name, Gender gender, String birthday, Integer totalStamp, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.birthday=birthday;
        this.totalStamp=totalStamp;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }
}

