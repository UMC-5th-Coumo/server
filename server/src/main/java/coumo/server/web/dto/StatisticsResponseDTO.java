package coumo.server.web.dto;

import coumo.server.domain.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StatisticsResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerResponseDTO {
        private Long id;
        private String name;
        private Gender gender;
        private String birthday;
        private String ageGroup;
        private String phone;
        private Integer totalStamp;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeResponseDTO {
        private LocalTime startTime;
        private Integer totalCustomer;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeekResponseDTO {
        private String day;
        private LocalDate date;
        private long totalCustomer;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GenderRatioDTO {
        private Double male;
        private Double female;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AgeGroupDTO {
        private String ageGroup;
        private double maleRatio;
        private double femaleRatio;
        private int total;
    }
}
