package coumo.server.repository;

import coumo.server.domain.Store;
import coumo.server.domain.enums.Gender;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.CouponResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CustomerStoreRepository  extends JpaRepository<CustomerStore, Long> {
    Optional<CustomerStore> findByCustomerIdAndStoreId(Long customerId, Long storeId);

    @Query("SELECT COUNT(DISTINCT cs.customer.id) FROM CustomerStore cs WHERE cs.store = :store AND HOUR(cs.updatedAt) = :hour AND DATE(cs.updatedAt) = :date")
    int countCustomersByHour(@Param("store") Store store, @Param("hour") int hour, @Param("date") LocalDate date);

    @Query("SELECT COUNT(DISTINCT cs.customer.id) FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.customer.gender = :gender AND cs.updatedAt BETWEEN :startDate AND :endDate")
    int countGenderByDate(@Param("storeId") Long storeId, @Param("gender") Gender gender, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT cs.customer.birthday FROM CustomerStore cs WHERE cs.store.id = :storeId AND cs.customer.gender = :gender AND cs.updatedAt BETWEEN :startDate AND :endDate")
    List<String> findCustomerBirthday(@Param("storeId") Long storeId, @Param("gender") Gender gender, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT FUNCTION('DAYNAME', cs.updatedAt) AS day, COUNT(cs) as totalCustomer " +
            "FROM CustomerStore cs " +
            "WHERE cs.store.id = :storeId AND FUNCTION('YEAR', cs.updatedAt) = :year AND FUNCTION('MONTH', cs.updatedAt) = :month " +
            "GROUP BY day")
    List<Object[]> findCustomerCountPerDay(Long storeId, int year, int month);

    @Query("SELECT new coumo.server.web.dto.CouponResponseDTO.CustomerStoreCouponDTO(" +
            "oc.storeName, oc.couponColor, oc.fontColor, oc.stampImage, cs.stampCurrent, oc.stampMax) " +
            "FROM CustomerStore cs " +
            "JOIN cs.store s " +
            "JOIN s.owner.ownerCouponList oc " +
            "WHERE cs.customer.id = :customerId " +
            "AND oc.storeName = s.name " +
            "ORDER BY cs.stampCurrent DESC")
    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsMost(@Param("customerId") Long customerId);

    @Query("SELECT new coumo.server.web.dto.CouponResponseDTO.CustomerStoreCouponDTO(" +
            "oc.storeName, oc.couponColor, oc.fontColor, oc.stampImage, cs.stampCurrent, oc.stampMax) " +
            "FROM CustomerStore cs " +
            "JOIN cs.store s " +
            "JOIN s.owner.ownerCouponList oc " +
            "WHERE cs.customer.id = :customerId " +
            "AND oc.storeName = s.name " +
            "ORDER BY cs.updatedAt DESC")
    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsLatest(@Param("customerId") Long customerId);
}