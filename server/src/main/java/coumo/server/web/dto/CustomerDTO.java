package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String name;
    private Gender gender;
    private String birthday;
    private Integer totalStamp;
}

