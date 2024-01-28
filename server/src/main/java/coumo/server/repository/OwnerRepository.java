package coumo.server.repository;


import coumo.server.domain.Owner;
import coumo.server.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Override
    Optional<Owner> findById(Long ownerId);
}
