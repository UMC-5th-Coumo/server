package coumo.server.service.coupon;

import coumo.server.domain.mapping.CustomerStore;
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

    @Override
    public CustomerStore findCustomerStoreCoupon(Long storeId, Long customerId) {
        CustomerStore customerStore = customerStoreRepository.findByCustomerIdAndStoreId(customerId, storeId).get();
        return customerStore;
    }

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponLatest(Long customerId) {
//        return customerStoreRepository.findCustomerStoreCouponsMost(customerId);
        return null;
    }

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerCouponMost(Long customerId) {
//        return customerStoreRepository.findCustomerStoreCouponsMost(customerId);
        return null;
    }
}
