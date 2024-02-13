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


        public CustomerStoreCouponDTO(String storeName, String couponColor, String fontColor,
                                      String stampImage, Integer stampCurrent, StampMax stampMax) {
            this.storeCouponDTO = new StoreCouponDTO(storeName, couponColor, fontColor, stampImage);
            this.customerStoreStampDTO = new CustomerStoreStampDTO(stampCurrent, stampMax);
        }

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerStoreCouponDTO2 {

        public String storeName;
        public String couponColor;
        public String fontColor;
        public String stampImage;
        public Integer stampCurrent;
        public StampMax stampMax;

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
}
