package coumo.server.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Getter
public class WeekResponseDTO {
    private String day;
    private LocalDate date;
    private long totalCustomer;



    @Builder
    public WeekResponseDTO(String day, LocalDate date, long totalCustomer) {
        this.day = day;
        this.date = date;
        this.totalCustomer = totalCustomer;
    }
}