package coumo.server.repository;


import coumo.server.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Owner findByLoginId(String loginId);

    @Override
    Optional<Owner> findById(Long ownerId);

    Optional<Owner> findByStoreId(Long storeId);

}