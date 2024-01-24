package coumo.server.web.dto;

import lombok.Getter;

import java.util.List;

public class StoreRequestDTO {

    //==============웹==============
    //가게 수정(기본 정보)
    @Getter
    public static class UpdateBasicDTO {
        public String name;
        public List<TimeInfo> time;
        public String telePhone;
        public String category;
        public String location;
        public String longitude;
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
