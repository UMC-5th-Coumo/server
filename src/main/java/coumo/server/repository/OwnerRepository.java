package coumo.server.repository;


import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.domain.Store;
import coumo.server.web.dto.LoginIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Owner findByLoginId(String loginId);

    @Override
    Optional<Owner> findById(Long ownerId);

    //로그인 중복확인
    boolean existsByLoginId(String loginId);

    Optional<Owner> findByNameAndPhone(String name, String phone);

    Optional<Owner> findByLoginIdAndPhone(String loginID, String phone);
    // JPQL 쿼리를 사용하여 loginId 필드만 조회
    @Query("SELECT new coumo.server.web.dto.LoginIdDTO(c.loginId) FROM Owner c WHERE c.phone = :phone")
    Optional<LoginIdDTO> findLoginIdByPhone(@Param("phone") String phone);
    Optional<Owner> findByStoreId(Long storeId);
}