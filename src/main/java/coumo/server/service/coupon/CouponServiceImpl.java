package coumo.server.service.coupon;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.repository.OwnerCouponRepository;
import coumo.server.web.dto.CouponRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final OwnerCouponRepository ownerCouponRepository;

    @Override
    public OwnerCoupon register(Owner owner, CouponRequestDTO.registerCouponDTO dto) {

        OwnerCoupon newCoupon = dto.toEntity(owner);
        return ownerCouponRepository.save(newCoupon);
    }
}
