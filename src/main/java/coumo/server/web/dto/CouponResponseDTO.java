package coumo.server.web.dto;

import coumo.server.domain.enums.StampMax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    }


    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreCouponDTO {
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
