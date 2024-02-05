package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/{storeId}/customer")
    public ApiResponse<Map<String, Object>> getAllCustomers(@PathVariable Long storeId) {
        return ApiResponse.onSuccess(statisticsService.getAllCustomers(storeId));
    }
    @GetMapping("/{storeId}/customer/new")
    public ApiResponse<Map<String, Object>> getNewCustomers(@PathVariable Long storeId) {
        return ApiResponse.onSuccess(statisticsService.getNewCustomers(storeId));
    }

    @GetMapping("/{storeId}/customer/regular")
    public ApiResponse<Map<String, Object>> getRegularCustomers(@PathVariable Long storeId) {
        return ApiResponse.onSuccess(statisticsService.getRegularCustomers(storeId));
    }

    @GetMapping("/{storeId}/day")
    public ApiResponse<?> getDayStatistics(@PathVariable Long storeId) {
        return ApiResponse.onSuccess(statisticsService.getWeekStatistics(storeId));
    }

    @GetMapping("/{storeId}/time")
    public ApiResponse<?> getTimeStatistics(@PathVariable Long storeId) {
        return ApiResponse.onSuccess(statisticsService.getTimeStatistics(storeId));
    }

    @GetMapping("/{storeId}/gender")
    public ApiResponse<?> getGenderRatio(@PathVariable Long storeId, @RequestParam(required = false) String period,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.onSuccess(statisticsService.getGenderRatio(storeId, period, startDate, endDate));
    }

    @GetMapping("/{storeId}/age")
    public ApiResponse<?> getAgeGroup(@PathVariable Long storeId, @RequestParam(required = false) String period,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.onSuccess(statisticsService.getAgeGroup(storeId, period, startDate, endDate));
    }

    @GetMapping("/{storeId}/month-statistics")
    public ApiResponse<?> getMonthStatistics(@PathVariable Long storeId, @RequestParam int year,@RequestParam int month) {
        return ApiResponse.onSuccess(statisticsService.getMonthStatistics(storeId, year, month));
    }

    @GetMapping("/{storeId}/month-age")
    public ApiResponse<?> getMonthAgeStatistics(@PathVariable Long storeId, @RequestParam int year,@RequestParam int month) {
        return ApiResponse.onSuccess(statisticsService.getMonthAgeGroup(storeId, year, month));
    }

    @GetMapping("/{storeId}/month-day")
    public ApiResponse<?> getMonthDayStatistics(@PathVariable Long storeId, @RequestParam int year, @RequestParam int month) {
        return ApiResponse.onSuccess(statisticsService.getMonthDayGroup(storeId, year, month));
    }

}