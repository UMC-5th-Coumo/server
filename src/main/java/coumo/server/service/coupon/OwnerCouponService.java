package coumo.server.service.coupon;

import coumo.server.domain.OwnerCoupon;

public interface OwnerCouponService {

    OwnerCoupon findStoreCoupon(Long storeId);

}
