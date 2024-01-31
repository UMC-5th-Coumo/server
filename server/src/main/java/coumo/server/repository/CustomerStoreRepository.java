package coumo.server.repository;

import coumo.server.domain.Customer;
import coumo.server.domain.Store;
import coumo.server.domain.mapping.CustomerStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerStoreRepository  extends JpaRepository<CustomerStore, Long> {
    List<CustomerStore> findAllByCustomerIdAndStoreId(Long customerId, Long storeId);

    @Query("SELECT COUNT(DISTINCT cs.id) FROM CustomerStore cs WHERE cs.store = :store AND HOUR(cs.updatedAt) = :hour AND DATE(cs.updatedAt) = :date")
    int countByStoreAndHourAndDate(@Param("store") Store store, @Param("hour") int hour, @Param("date") LocalDate date);

}