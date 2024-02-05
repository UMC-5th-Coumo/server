package coumo.server.repository;

import coumo.server.domain.Customer;
import coumo.server.web.dto.StatisticsResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.stampTotal > 0")
    List<Customer> getAllCustomers(@Param("storeId") Long storeId);

    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.createdAt > :oneMonthAgo")
    List<Customer> getNewCustomers(@Param("storeId") Long storeId, @Param("oneMonthAgo") LocalDateTime oneMonthAgo);

    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.stampTotal >= 10")
    List<Customer> getRegularCustomers(@Param("storeId") Long storeId);

    @Query("SELECT DATE(cs.updatedAt) AS date, COUNT(DISTINCT cs.customer.id) " +
            "FROM CustomerStore cs " +
            "WHERE cs.store.id = :storeId AND DATE(cs.updatedAt) BETWEEN :startDate AND :endDate " +
            "GROUP BY date")
    List<Object[]> getWeekStatistics(Long storeId, LocalDate startDate, LocalDate endDate);


    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.stampTotal > 0 AND cs.updatedAt BETWEEN :startOfMonth AND :endOfMonth")
    List<Customer> getAllCustomerCount(@Param("storeId") Long storeId, @Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);

    @Query("SELECT cs.customer FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.createdAt BETWEEN :startOfMonth AND :endOfMonth")
    List<Customer> getNewCustomerCount(@Param("storeId") Long storeId, @Param("startOfMonth") LocalDateTime startOfMonth, @Param("endOfMonth") LocalDateTime endOfMonth);
}