package coumo.server.service.statistics;

import coumo.server.converter.StatisticsConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Store;
import coumo.server.domain.Timetable;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.repository.StatisticsRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.repository.TimetableRepository;
import coumo.server.web.dto.CustomerResponseDTO;
import coumo.server.web.dto.TimeResponseDTO;
import coumo.server.web.dto.WeekResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final TimetableRepository timetableRepository;
    private final StoreRepository storeRepository;

    /**
     * 전체 고객 데이터 확인
     * @param storeId 매장 ID
     */
    public Map<String, Object> getAllCustomers(Long storeId) {
        List<Customer> customers = statisticsRepository.getAllCustomers(storeId);
        List<CustomerResponseDTO> customerList = this.createCustomerDtoList(customers, storeId);

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
        List<CustomerResponseDTO> newCustomerList = this.createCustomerDtoList(newCustomers, storeId);

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
        List<CustomerResponseDTO> regularCustomerList = this.createCustomerDtoList(regularCustomers, storeId);

        Map<String, Object> result = new HashMap<>();
        result.put("cnt", regularCustomers.size());
        result.put("customers", regularCustomerList);

        return result;
    }

    private List<CustomerResponseDTO> createCustomerDtoList(List<Customer> customers, Long storeId) {
        List<CustomerResponseDTO> customerResponseDtoList = new ArrayList<>();
        for (Customer customer : customers) {
            List<CustomerStore> customerStores = customerStoreRepository.findAllByCustomerIdAndStoreId(customer.getId(), storeId);
            for (CustomerStore customerStore : customerStores) {
                int totalStamp = (customerStore != null) ? customerStore.getStampTotal() : 0;

                CustomerResponseDTO customerResponseDto = StatisticsConverter.toDTO(customer, customerStore);
                customerResponseDto.setTotalStamp(totalStamp);

                customerResponseDtoList.add(customerResponseDto);
            }
        }
        return customerResponseDtoList;
    }


    /**
     * 요일 별 방문 통계 조회
     * @param storeId 매장 ID
     */
    public List<WeekResponseDTO> getWeekStatistics(Long storeId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6);

        List<Object[]> results = statisticsRepository.getWeekStatistics(storeId, startDate, endDate);

        Map<LocalDate, Long> resultByDate = results.stream()
                .collect(Collectors.toMap(
                        result -> {
                            java.sql.Date sqlDate = (java.sql.Date) result[0];
                            java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                            return utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        },
                        result -> (Long) result[1]
                ));

        List<WeekResponseDTO> weekStatistics = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            String day = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();
            long totalCustomer = resultByDate.getOrDefault(date, 0L);

            WeekResponseDTO weekResponseDto = new WeekResponseDTO(day, date, totalCustomer);
            weekStatistics.add(weekResponseDto);
        }

        return weekStatistics;

    }

    /**
     * 시간대 별 방문 통계 조회
     * @param storeId 매장 ID
     */
    public List<TimeResponseDTO> getTimeStatistics(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장이 올바르지 않습니다."));

        String day = LocalDate.now().getDayOfWeek().name();

        Timetable timetable = timetableRepository.findByStoreAndDay(store, day)
                .orElseThrow(() -> new IllegalArgumentException("시간 설정이 올바르지 않습니다."));

        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.parse(timetable.getStartTime());
        LocalTime endTime = LocalTime.parse(timetable.getEndTime());

        List<TimeResponseDTO> result = new ArrayList<>();

        for (int i = startTime.getHour(); i <= endTime.getHour(); i++) {
            LocalTime time = LocalTime.of(i, 0);
            if (time.isAfter(currentTime.minusHours(1))) {
                result.add(new TimeResponseDTO(time, null));
            } else {
                int totalCustomer = customerStoreRepository.countByStoreAndHourAndDate(store, i, LocalDate.now());
                result.add(new TimeResponseDTO(time, totalCustomer));
            }
        }


        return result;
    }
}