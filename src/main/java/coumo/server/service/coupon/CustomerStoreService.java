package coumo.server.service.coupon;

import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CouponResponseDTO;

import java.util.List;

public interface CustomerStoreService {
    CustomerStore findCustomerStoreCoupon(Long storeId, Long customerId);

    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponLatest(Long customerId);

    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponMost(Long customerId);
}
