package coumo.server.service.coupon;

import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreQueryRepository;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.web.dto.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerStoreServiceImpl implements CustomerStoreService{

    private final CustomerStoreRepository customerStoreRepository;
    private final CustomerStoreQueryRepository customerStoreQueryRepository;

    @Override
    public CustomerStore findCustomerStoreCoupon(Long storeId, Long customerId) {
        CustomerStore customerStore = customerStoreRepository.findByCustomerIdAndStoreId(customerId, storeId).get();
        return customerStore;
    }

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponLatest(Long customerId) {
        return customerStoreQueryRepository.findCustomerStoreCouponsLatest(customerId);
    }

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponMost(Long customerId) {
        return customerStoreQueryRepository.findCustomerStoreCouponsMost(customerId);
    }
}
