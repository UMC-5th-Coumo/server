package coumo.server.web.dto;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.domain.enums.StampMax;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

public class CouponRequestDTO {

    // --------WEB-----------------------

    // 쿠폰
    @Getter
    @NoArgsConstructor
    public static class registerCouponDTO{
        @NotBlank
        public String storeName;
        @NotBlank
        public String couponColor;
        @NotBlank
        public String fontColor;
        @Nullable
        public StampMax stampMax;
        @Nullable
        public String stampImage;


        public OwnerCoupon toEntity(Owner owner){
            return OwnerCoupon.builder()
                    .owner(owner)
                    .storeName(storeName)
                    .couponColor(couponColor)
                    .fontColor(fontColor)
                    .stampMax(stampMax)
                    .stampImage(stampImage)
                    .build();
        }
    }
}
