package coumo.server.service;

import coumo.server.converter.StatisticsConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Store;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.repository.StatisticsRepository;
import coumo.server.web.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final CustomerStoreRepository customerStoreRepository;

    /**
     * 전체 고객 데이터 확인
     * @param storeId 매장 ID
     */
    public Map<String, Object> getAllCustomers(Long storeId) {
        List<Customer> customers = statisticsRepository.getAllCustomers(storeId);
        List<CustomerDTO> customerList = customers.stream()
                .map(customer -> {
                    CustomerStore customerStore = customerStoreRepository.findByCustomerIdAndStoreId(customer.getId(), storeId);
                    int totalStamp = (customerStore != null) ? customerStore.getStampTotal() : 0;

                    CustomerDTO customerDTO = StatisticsConverter.toDTO(customer);
                    customerDTO.setTotalStamp(totalStamp);

                    return customerDTO;
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("cnt", customers.size());
        result.put("customers", customerList);

        return result;
    }

}