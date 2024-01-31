package coumo.server.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeResponseDTO {
    private LocalTime startTime;
    private Integer totalCustomer;

    @Builder
    public TimeResponseDTO(LocalTime startTime, Integer totalCustomer) {
        this.startTime = startTime;
        this.totalCustomer = totalCustomer;
    }

}