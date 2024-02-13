package coumo.server.service.coupon;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.web.dto.CouponRequestDTO;

public interface CouponService {

    OwnerCoupon register(Owner owner, CouponRequestDTO.registerCouponDTO dto);

}
