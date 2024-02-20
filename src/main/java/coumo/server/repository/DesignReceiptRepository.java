package coumo.server.repository;

import coumo.server.domain.DesignReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesignReceiptRepository extends JpaRepository<DesignReceipt, Long> {
    List<DesignReceipt> findAllByOwnerId(Long ownerId);
}
