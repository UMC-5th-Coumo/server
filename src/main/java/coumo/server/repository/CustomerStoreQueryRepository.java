package coumo.server.repository;

import coumo.server.web.dto.CouponResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerStoreQueryRepository {

    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsMost(@Param("customerId") Long customerId);

    List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsLatest(@Param("customerId") Long customerId);

}
