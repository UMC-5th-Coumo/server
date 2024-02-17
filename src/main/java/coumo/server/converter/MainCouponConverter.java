package coumo.server.converter;

import coumo.server.domain.OwnerCoupon;
import coumo.server.domain.Store;
import coumo.server.domain.Timetable;
import coumo.server.web.dto.CouponResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class MainCouponConverter {
    public static CouponResponseDTO.MainCouponDTO toMainCouponDTO(OwnerCoupon ownerCoupon){
        return CouponResponseDTO.MainCouponDTO.builder()
                .storeName(ownerCoupon.getStoreName())
                .couponColor(ownerCoupon.getCouponColor())
                .fontColor(ownerCoupon.getFontColor())
                .stampImage(ownerCoupon.getStampImage())
                .stampMax(ownerCoupon.getStampMax().toString())
                .build();
    }
}
