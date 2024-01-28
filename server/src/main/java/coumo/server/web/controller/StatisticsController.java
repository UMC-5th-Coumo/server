package coumo.server.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.SuccessStatus;
import coumo.server.domain.Customer;
import coumo.server.service.StatisticsService;
import coumo.server.web.dto.CustomerDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}