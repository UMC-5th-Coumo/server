package coumo.server.service.statistics;

import coumo.server.converter.StatisticsConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Store;
import coumo.server.domain.Timetable;
import coumo.server.domain.enums.Gender;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.repository.StatisticsRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.repository.TimetableRepository;
import coumo.server.web.dto.CustomerResponseDTO;
import coumo.server.web.dto.StatisticsResponseDTO;
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
            List<CustomerStore> customerStores = customerStoreRepository.findByCustomerIdAndStoreId(customer.getId(), storeId);
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
    public List<StatisticsResponseDTO.WeekResponseDTO> getWeekStatistics(Long storeId) {
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

        List<StatisticsResponseDTO.WeekResponseDTO> weekStatistics = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            String day = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).toUpperCase();
            long totalCustomer = resultByDate.getOrDefault(date, 0L);

            StatisticsResponseDTO.WeekResponseDTO weekResponseDto = new StatisticsResponseDTO.WeekResponseDTO(day, date, totalCustomer);
            weekStatistics.add(weekResponseDto);
        }

        return weekStatistics;

    }

    /**
     * 시간대 별 방문 통계 조회
     * @param storeId 매장 ID
     */
    public List<StatisticsResponseDTO.TimeResponseDTO> getTimeStatistics(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장이 올바르지 않습니다."));

        String day = LocalDate.now().getDayOfWeek().name();

        Timetable timetable = timetableRepository.findByStoreAndDay(store, day)
                .orElseThrow(() -> new IllegalArgumentException("시간 설정이 올바르지 않습니다."));

        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = LocalTime.parse(timetable.getStartTime());
        LocalTime endTime = LocalTime.parse(timetable.getEndTime());

        List<StatisticsResponseDTO.TimeResponseDTO> result = new ArrayList<>();

        for (int i = startTime.getHour(); i <= endTime.getHour(); i++) {
            LocalTime time = LocalTime.of(i, 0);
            if (time.isAfter(currentTime.minusHours(1))) {
                result.add(new StatisticsResponseDTO.TimeResponseDTO(time, null));
            } else {
                int totalCustomer = customerStoreRepository.countCustomersByHour(store, i, LocalDate.now());
                result.add(new StatisticsResponseDTO.TimeResponseDTO(time, totalCustomer));
            }
        }
        return result;
    }

    /**
     * 인구통계별 방문 통계 조회 - 성별 원형 그래프
     * @param storeId 매장 ID
     * @param period 통계 기간 ('today', 'week', 'month', 'quarter')
     * @param startDate 통계 시작 날짜
     * @param endDate 통계 종료 날짜
     */
    public StatisticsResponseDTO.GenderRatioDTO getGenderRatio(Long storeId, String period, LocalDate startDate, LocalDate endDate) {
        if (period != null) {
            switch (period) {
                case "today":
                    startDate = endDate = LocalDate.now();
                    break;
                case "week":
                    startDate = LocalDate.now().minusWeeks(1);
                    endDate = LocalDate.now();
                    break;
                case "month":
                    startDate = LocalDate.now().minusMonths(1);
                    endDate = LocalDate.now();
                    break;
                case "quarter":
                    startDate = LocalDate.now().minusMonths(3);
                    endDate = LocalDate.now();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period: " + period);
            }
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Gender maleGender = Gender.valueOf("MALE");
        Gender femaleGender = Gender.valueOf("FEMALE");

        int maleCount = customerStoreRepository.countGenderByDate(storeId, maleGender, startDateTime, endDateTime);
        int femaleCount = customerStoreRepository.countGenderByDate(storeId, femaleGender, startDateTime, endDateTime);

        int totalCount = maleCount + femaleCount;

        double maleRatio = (double) maleCount / totalCount * 100;
        double femaleRatio = (double) femaleCount / totalCount * 100;

        return StatisticsResponseDTO.GenderRatioDTO.builder()
                .male(maleRatio)
                .female(femaleRatio)
                .build();
    }

    /**
     * 인구통계별 방문 통계 조회 - 연령대별 막대그래프
     * @param storeId 매장 ID
     * @param period 통계 기간 ('today', 'week', 'month', 'quarter')
     * @param startDate 통계 시작 날짜
     * @param endDate 통계 종료 날짜
     */
    public List<StatisticsResponseDTO.AgeGroupDTO> getAgeGroup(Long storeId, String period, LocalDate startDate, LocalDate endDate) {
        if (period != null) {
            switch (period) {
                case "today":
                    startDate = endDate = LocalDate.now();
                    break;
                case "week":
                    startDate = LocalDate.now().minusWeeks(1);
                    endDate = LocalDate.now();
                    break;
                case "month":
                    startDate = LocalDate.now().minusMonths(1);
                    endDate = LocalDate.now();
                    break;
                case "quarter":
                    startDate = LocalDate.now().minusMonths(3);
                    endDate = LocalDate.now();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period: " + period);
            }
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<String> maleBirthdays = customerStoreRepository.findCustomerBirthday(storeId, Gender.MALE, startDateTime, endDateTime);
        List<String> femaleBirthdays = customerStoreRepository.findCustomerBirthday(storeId, Gender.FEMALE, startDateTime, endDateTime);

        Map<String, StatisticsResponseDTO.AgeGroupDTO> ageGroupDTOMap = new HashMap<>();
        for (String birthday : maleBirthdays) {
            String ageGroup = StatisticsConverter.calcAgeGroup(birthday);
            StatisticsResponseDTO.AgeGroupDTO ageGroupDTO = ageGroupDTOMap.getOrDefault(ageGroup, new StatisticsResponseDTO.AgeGroupDTO(ageGroup, 0, 0, 0));
            ageGroupDTO.setMaleRatio(ageGroupDTO.getMaleRatio() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }
        for (String birthday : femaleBirthdays) {
            String ageGroup = StatisticsConverter.calcAgeGroup(birthday);
            StatisticsResponseDTO.AgeGroupDTO ageGroupDTO = ageGroupDTOMap.getOrDefault(ageGroup, new StatisticsResponseDTO.AgeGroupDTO(ageGroup, 0, 0, 0));
            ageGroupDTO.setFemaleRatio(ageGroupDTO.getFemaleRatio() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }

        List<StatisticsResponseDTO.AgeGroupDTO> ageGroupDTOs = new ArrayList<>(ageGroupDTOMap.values());
        for (StatisticsResponseDTO.AgeGroupDTO ageGroupDTO : ageGroupDTOs) {
            int totalCount = ageGroupDTO.getTotal();
            ageGroupDTO.setMaleRatio((ageGroupDTO.getMaleRatio() / totalCount) * 100);
            ageGroupDTO.setFemaleRatio((ageGroupDTO.getFemaleRatio() / totalCount) * 100);
        }

        return ageGroupDTOs;

    }
}