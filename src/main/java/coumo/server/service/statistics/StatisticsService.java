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
import coumo.server.service.owner.OwnerService;
import coumo.server.util.TokenCheck;
import coumo.server.web.dto.CustomOwnerDetails;
import coumo.server.web.dto.CustomerResponseDTO;
import coumo.server.web.dto.StatisticsResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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
            Optional<CustomerStore> optionalCustomerStore = customerStoreRepository.findByCustomerIdAndStoreId(customer.getId(), storeId);
            if (optionalCustomerStore.isPresent()) {
                CustomerStore customerStore = optionalCustomerStore.get();
                int totalStamp = customerStore.getStampTotal();

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
    public Object getTimeStatistics(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("매장이 올바르지 않습니다."));

        String day = LocalDate.now().getDayOfWeek().name();

        Timetable timetable = timetableRepository.findByStoreAndDay(store, day)
                .orElseThrow(() -> new IllegalArgumentException("시간 설정이 올바르지 않습니다."));

        if (timetable.getStartTime() == null || timetable.getEndTime() == null) {
            return new StatisticsResponseDTO.HolidayResponseDTO("휴무일입니다.");  // 휴무일인 경우 HolidayResponseDTO 반환
        }

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
            ageGroupDTO.setMale(ageGroupDTO.getMale() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }
        for (String birthday : femaleBirthdays) {
            String ageGroup = StatisticsConverter.calcAgeGroup(birthday);
            StatisticsResponseDTO.AgeGroupDTO ageGroupDTO = ageGroupDTOMap.getOrDefault(ageGroup, new StatisticsResponseDTO.AgeGroupDTO(ageGroup, 0, 0, 0));
            ageGroupDTO.setFemale(ageGroupDTO.getFemale() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }

        List<StatisticsResponseDTO.AgeGroupDTO> ageGroupDTOs = new ArrayList<>(ageGroupDTOMap.values());

        return ageGroupDTOs;

    }

    /**
     * 월간 레포트 분석 - 방문 고객, 신규 고객 수
     * @param storeId 매장 ID
     * @param year 통계 년도
     * @param month 통계 월
     */
    public StatisticsResponseDTO.MonthStatisticsResponseDTO getMonthStatistics(Long storeId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        // 해당 달 총 방문 고객 수
        List<Customer> allCustomers = statisticsRepository.getAllCustomerCount(storeId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        int allCustomerCount = allCustomers.size();

        // 해당 달 총 신규 고객 수
        List<Customer> newCustomers = statisticsRepository.getNewCustomerCount(storeId, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        int newCustomerCount = newCustomers.size();

        // 이전 달 데이터
        LocalDate prevStartDate = startDate.minusMonths(1);
        LocalDate prevEndDate = startDate.minusDays(1);

        List<Customer> prevAllCustomers = statisticsRepository.getAllCustomerCount(storeId, prevStartDate.atStartOfDay(), prevEndDate.atTime(23, 59, 59));
        int prevAllCustomerCount = prevAllCustomers.size();

        List<Customer> prevNewCustomers = statisticsRepository.getNewCustomerCount(storeId, prevStartDate.atStartOfDay(), prevEndDate.atTime(23, 59, 59));
        int prevNewCustomerCount = prevNewCustomers.size();

        return new StatisticsResponseDTO.MonthStatisticsResponseDTO(allCustomerCount, newCustomerCount, prevAllCustomerCount, prevNewCustomerCount);
    }

    /**
     * 월간 레포트 분석 - 연령대, 성별 막대그래프
     * @param storeId 매장 ID
     * @param year 통계 년도
     * @param month 통계 월
     */
    public List<StatisticsResponseDTO.AgeGroupDTO> getMonthAgeGroup(Long storeId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        List<String> maleBirthdays = customerStoreRepository.findCustomerBirthday(storeId, Gender.MALE, startDateTime, endDateTime);
        List<String> femaleBirthdays = customerStoreRepository.findCustomerBirthday(storeId, Gender.FEMALE, startDateTime, endDateTime);

        Map<String, StatisticsResponseDTO.AgeGroupDTO> ageGroupDTOMap = new HashMap<>();
        for (String birthday : maleBirthdays) {
            String ageGroup = StatisticsConverter.calcAgeGroup(birthday);
            StatisticsResponseDTO.AgeGroupDTO ageGroupDTO = ageGroupDTOMap.getOrDefault(ageGroup, new StatisticsResponseDTO.AgeGroupDTO(ageGroup, 0, 0, 0));
            ageGroupDTO.setMale(ageGroupDTO.getMale() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }
        for (String birthday : femaleBirthdays) {
            String ageGroup = StatisticsConverter.calcAgeGroup(birthday);
            StatisticsResponseDTO.AgeGroupDTO ageGroupDTO = ageGroupDTOMap.getOrDefault(ageGroup, new StatisticsResponseDTO.AgeGroupDTO(ageGroup, 0, 0, 0));
            ageGroupDTO.setFemale(ageGroupDTO.getFemale() + 1);
            ageGroupDTO.setTotal(ageGroupDTO.getTotal() + 1);
            ageGroupDTOMap.put(ageGroup, ageGroupDTO);
        }

        List<StatisticsResponseDTO.AgeGroupDTO> ageGroupDTOs = new ArrayList<>(ageGroupDTOMap.values());

        return ageGroupDTOs;

    }

    /**
     * 월간 레포트 분석 - 방문 요일 고객수
     * @param storeId 매장 ID
     * @param year 통계 년도
     * @param month 통계 월
     */
    public List<StatisticsResponseDTO.MonthDayResponseDTO> getMonthDayGroup(Long storeId, int year, int month) {
        Map<String, Long> dayCustomerCount = new LinkedHashMap<>();
        dayCustomerCount.put("MON", 0L);
        dayCustomerCount.put("TUE", 0L);
        dayCustomerCount.put("WED", 0L);
        dayCustomerCount.put("THU", 0L);
        dayCustomerCount.put("FRI", 0L);
        dayCustomerCount.put("SAT", 0L);
        dayCustomerCount.put("SUN", 0L);

        List<Object[]> dayGroup = customerStoreRepository.findCustomerCountPerDay(storeId, year, month);

        for (Object[] objects : dayGroup) {
            String day = ((String) objects[0]).toUpperCase().substring(0, 3); // Convert day name to abbreviation
            Long totalCustomer = ((Number) objects[1]).longValue();
            dayCustomerCount.put(day, totalCustomer);
        }

        List<StatisticsResponseDTO.MonthDayResponseDTO> result = dayCustomerCount.entrySet().stream()
                .map(entry -> new StatisticsResponseDTO.MonthDayResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return result;
    }

}