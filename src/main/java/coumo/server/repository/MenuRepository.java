package coumo.server.repository;

import coumo.server.domain.Menu;
import coumo.server.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    void deleteAllByStore(Store store);
}
