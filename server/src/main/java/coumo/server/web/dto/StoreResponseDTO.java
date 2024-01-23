package coumo.server.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class StoreResponseDTO {

    //가게 (기본 정보)
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class resultBasicDTO{
        String name;
        List<time> time;
        String telePhone;
        String category;
        String location;
        String longitude;   //경도
        String latitude;    //위도
    }

    //가게 (매장 설명)
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class resultDetailDTO{
        List<String> storeImages;
        String description;
        List<menu> menus;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class menu{
        String name;
        String image;
        String description;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class time{
        String day;
        String startTime;
        String endTime;
    }
}
