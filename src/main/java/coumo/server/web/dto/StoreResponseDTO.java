package coumo.server.web.dto;

import coumo.server.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class StoreResponseDTO {

    //==============웹==============

    //가게 (기본 정보)
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreBasicDTO {
        String name;
        List<TimeInfo> time;
        String telePhone;
        String category;
        String location;
        String latitude;
        String longitude;
    }

    //가게 (매장 설명)
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreDetailDTO {
        List<String> storeImages;
        String description;
        List<MenuInfo> menus;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuInfo {
        String name;
        String image;
        String description;
        Boolean isNew;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TimeInfo {
        String day;
        String startTime;
        String endTime;
    }

    //==============앱==============

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FamousStoreDTO {
        Long storeId;
        String name;
        String location;
        String description;
        String storeImage;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NearestStoreDTO {
        Long storeId;
        String name;
        String location;
        Integer couponCnt;
        String storeImage;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MoreDetailStoreDTO{
        String name;
        String description;
        String location;
        String longitude;
        String latitude;
        Coupon coupon;
        List<String> images;
        List<MenuInfo> menus;
    }
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreStampInfo {
        private Store store;
        private String image;
        private int stampTotal;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Coupon{
        String title;
        Integer cnt;
        String stampType;
        String color;
    }
}
