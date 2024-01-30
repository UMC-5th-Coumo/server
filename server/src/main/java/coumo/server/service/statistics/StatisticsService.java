package coumo.server.service.statistics;

import coumo.server.converter.StatisticsConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.repository.StatisticsRepository;
import coumo.server.web.dto.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
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
        List<CustomerDTO> customerList = this.createCustomerDtoList(customers, storeId);

        Map<String, Object> result = new HashMap<>();
        result.put("cnt", customers.size());
        result.put("customers", customerList);

        return result;
    }

    /**
     * 신규 고객 데이터 확인
     * @param storeId 매장 ID
     */
    public Map<String, Object> getNewCustomers(Long storeId) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Customer> newCustomers = statisticsRepository.getNewCustomers(storeId, oneMonthAgo);
        List<CustomerDTO> newCustomerList = this.createCustomerDtoList(newCustomers, storeId);

        Map<String, Object> result = new HashMap<>();
        result.put("cnt", newCustomers.size());
        result.put("customers", newCustomerList);

        return result;
    }

    /**
     * 단골 고객 데이터 확인
     * @param storeId 매장 ID
     */
    public Map<String, Object> getRegularCustomers(Long storeId) {
        List<Customer> regularCustomers = statisticsRepository.getRegularCustomers(storeId);
        List<CustomerDTO> regularCustomerList = this.createCustomerDtoList(regularCustomers, storeId);

        Map<String, Object> result = new HashMap<>();
        result.put("cnt", regularCustomers.size());
        result.put("customers", regularCustomerList);

        return result;
    }

    private List<CustomerDTO> createCustomerDtoList(List<Customer> customers, Long storeId) {
        return customers.stream()
                .map(customer -> {
                    CustomerStore customerStore = customerStoreRepository.findByCustomerIdAndStoreId(customer.getId(), storeId);
                    int totalStamp = (customerStore != null) ? customerStore.getStampTotal() : 0;

                    CustomerDTO customerDto = StatisticsConverter.toDTO(customer, customerStore);
                    customerDto.setTotalStamp(totalStamp);

                    return customerDto;
                })
                .collect(Collectors.toList());
    }
}