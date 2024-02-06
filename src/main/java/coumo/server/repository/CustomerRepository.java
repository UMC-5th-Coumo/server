package coumo.server.repository;

import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByLoginId(String loginId);

    //로그인 중복확인
    boolean existsByLoginId(String loginId);
}
