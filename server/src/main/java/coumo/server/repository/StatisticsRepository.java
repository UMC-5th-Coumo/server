package coumo.server.repository;

import coumo.server.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.stampTotal > 0")
    List<Customer> getAllCustomers(@Param("storeId") Long storeId);

}
