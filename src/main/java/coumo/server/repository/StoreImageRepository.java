package coumo.server.repository;

import coumo.server.domain.Store;
import coumo.server.domain.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
    void deleteAllByStore(Store store);
}
