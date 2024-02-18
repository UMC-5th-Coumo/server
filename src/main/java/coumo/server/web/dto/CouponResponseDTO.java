package coumo.server.web.dto;

import coumo.server.domain.enums.StampMax;
import lombok.*;

import java.time.LocalDateTime;

public class CouponResponseDTO {

    // --------APP-----------------------


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerStoreCouponDTO {
        public CustomerStoreStampDTO customerStoreStampDTO;
        public StoreCouponDTO storeCouponDTO;

    }

    // 쿠폰
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerStoreStampDTO {
        public Integer stampCurrent;
        public StampMax stampMax;
        public LocalDateTime updatedAt;
    }


    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreCouponDTO {
        public Long storeId;
        public String storeName;
        public String couponColor;
        public String fontColor;
        public String stampImage;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MainCouponDTO {
        public String storeName;
        public String couponColor;
        public String fontColor;
        public String stampImage;
        public String stampMax;
    }
}
