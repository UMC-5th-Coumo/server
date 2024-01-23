package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

public class StoreRequestDTO {

    //가게 수정(기본 정보)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class updateDTO{
        public String name;
        public List<String> time;
        public String telePhone;
        public String category;
        public String location;
        public String longitude;
        public String latitude;
    }
}
