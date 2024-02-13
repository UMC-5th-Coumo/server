package coumo.server.repository;

import coumo.server.domain.Store;
import coumo.server.domain.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
    void deleteAllByStore(Store store);
    Optional<List<StoreImage>> findAllByStore(Store store);
}
