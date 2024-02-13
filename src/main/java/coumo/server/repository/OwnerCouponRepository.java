package coumo.server.repository;

import coumo.server.domain.OwnerCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerCouponRepository  extends JpaRepository<OwnerCoupon, Long> {
    @Query("SELECT DISTINCT o FROM OwnerCoupon o JOIN FETCH o.owner WHERE o.owner.id = :ownerId")
    Optional<List<OwnerCoupon>> findByOwnerCouponId(@Param("ownerId") Long ownerId);

    Optional<OwnerCoupon> findByOwnerId(Long id);

}
