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
    public ResponseEntity<?> getAllCustomers(@PathVariable Long storeId) {
        Map<String, Object> result = statisticsService.getAllCustomers(storeId);
        ApiResponse<Map<String, Object>> response = ApiResponse.onSuccess(result);
        return ResponseEntity.ok(response);
    }
}