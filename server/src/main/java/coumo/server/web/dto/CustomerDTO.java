package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.*;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private Gender gender;
    private String birthday;
    private Integer totalStamp;

    @Builder
    public CustomerDTO(Integer id, String name, Gender gender, String birthday, Integer totalStamp){
        this.id=id;
        this.name=name;
        this.gender=gender;
        this.birthday=birthday;
        this.totalStamp=totalStamp;
    }
}

