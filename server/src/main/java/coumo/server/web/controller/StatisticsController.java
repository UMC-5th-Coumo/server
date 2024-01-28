package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}