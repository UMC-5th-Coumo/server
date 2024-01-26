package coumo.server.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class StoreRequestDTO {

    //==============웹==============
    //가게 수정(기본 정보)
    @Getter
    public static class UpdateBasicDTO {
        @NotBlank
        public String name;
        @NotNull
        public List<TimeInfo> time;
        @NotNull
        public String telePhone;
        @NotBlank
        public String category;
        @NotBlank
        public String location;
        @NotBlank
        public String longitude;
        @NotBlank
        public String latitude;
    }

    @Getter
    public static class TimeInfo {
        String day;
        String startTime;
        String endTime;
    }

    @Getter
    public static class MenuDetail{
        String name;
        String description;
    }

    //==============앱==============

    @Getter
    public static class PointDTO{
        public String longitude;
        public String latitude;
    }
}
