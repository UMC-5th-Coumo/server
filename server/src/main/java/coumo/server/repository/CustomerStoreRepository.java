package coumo.server.repository;

import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerStoreRepository  extends JpaRepository<CustomerStore, Long> {
    CustomerStore findByCustomerIdAndStoreId(Long id, Long storeId);
}
