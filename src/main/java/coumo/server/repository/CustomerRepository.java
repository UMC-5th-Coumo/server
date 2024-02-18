package coumo.server.repository;

import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.web.dto.LoginAppIdDTO;
import coumo.server.web.dto.LoginIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByLoginId(String loginId);

    //로그인 중복확인
    boolean existsByLoginId(String loginId);

    Optional<Customer> findByNameAndPhone(String name, String phone);

    Optional<Customer> findByLoginIdAndPhone(String loginID, String phone);

    // JPQL 쿼리를 사용하여 loginId 필드만 조회
    @Query("SELECT new coumo.server.web.dto.LoginAppIdDTO(c.loginId) FROM Customer c WHERE c.phone = :phone")
    Optional<LoginAppIdDTO> findLoginIdByPhone(@Param("phone") String phone);
}
