package coumo.server.service.owner;

import coumo.server.domain.Owner;

import java.util.Optional;

public interface OwnerService {
    Optional<Owner> findOwner(Long ownerId);
}
