package coumo.server.service.coupon;

import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.repository.OwnerCouponRepository;
import coumo.server.util.StampURL;
import coumo.server.web.dto.CouponRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static coumo.server.util.StampURL.getURL;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final OwnerCouponRepository ownerCouponRepository;

    @Override
    public Long register(Owner owner, CouponRequestDTO.registerCouponDTO dto) {

        String stampUrl = getURL(dto.getStampImage());

        Optional<OwnerCoupon> ownerCoupon = ownerCouponRepository.findByOwnerId(owner.getId());
        if(ownerCoupon.isEmpty())
        {
            OwnerCoupon newCoupon = dto.toEntity(owner);
            OwnerCoupon newOwnerCoupon = ownerCouponRepository.save(newCoupon);
            return newOwnerCoupon.getId();
        }
        else
        {
            ownerCoupon.get().update(dto, stampUrl);
            return ownerCoupon.get().getId();
        }
    }
}
