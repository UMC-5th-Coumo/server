package coumo.server.service.coupon;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.repository.OwnerCouponRepository;
import coumo.server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerCouponServiceImpl implements OwnerCouponService{

    private final OwnerRepository ownerRepository;
    private final OwnerCouponRepository ownerCouponRepository;
    @Override
    public OwnerCoupon findStoreCoupon(Long storeId) {
        Owner owner = ownerRepository.findByStoreId(storeId).orElseThrow();
        List<OwnerCoupon> ownerCoupons = ownerCouponRepository.findByOwnerCouponId(owner.getId()).orElse(Collections.emptyList());

        return (ownerCoupons.isEmpty()) ? null : ownerCoupons.get(0);
    }
}
