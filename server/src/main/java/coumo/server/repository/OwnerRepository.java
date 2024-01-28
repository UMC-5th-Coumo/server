package coumo.server.repository;


import coumo.server.domain.Owner;
import coumo.server.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
