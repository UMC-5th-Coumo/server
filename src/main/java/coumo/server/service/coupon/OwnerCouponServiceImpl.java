package coumo.server.service.coupon;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.repository.OwnerCouponRepository;
import coumo.server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerCouponServiceImpl implements OwnerCouponService{

    private final OwnerRepository ownerRepository;
    private final OwnerCouponRepository ownerCouponRepository;
    @Override
    public OwnerCoupon findStoreCoupon(Long storeId) {
        Owner owner = ownerRepository.findByStoreId(storeId).get();
        OwnerCoupon ownerCoupon = ownerCouponRepository.findByOwnerId(owner.getId()).get();

        return ownerCoupon;
    }
}
