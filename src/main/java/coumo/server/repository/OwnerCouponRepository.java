package coumo.server.repository;

import coumo.server.domain.OwnerCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerCouponRepository extends JpaRepository<OwnerCoupon, Long> {

    Optional<OwnerCoupon> findByOwnerId(Long id);

}
