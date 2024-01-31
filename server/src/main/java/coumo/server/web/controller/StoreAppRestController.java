package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.domain.Store;
import coumo.server.domain.enums.StoreType;
import coumo.server.service.store.StoreCommandService;
import coumo.server.web.dto.StoreRequestDTO;
import coumo.server.web.dto.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class StoreAppRestController {
    private final StoreCommandService storeCommandService;

    @GetMapping("/store")
    public ApiResponse<List<StoreResponseDTO.FamousStoreDTO>> getFamousStore(@RequestBody StoreRequestDTO.PointDTO pointDTO) {
        return ApiResponse.onSuccess(new ArrayList<>());
    }

    @GetMapping("/{customerId}/store/{storeId}")
    public ApiResponse<List<StoreResponseDTO.FamousStoreDTO>> getNearestStore
            (@RequestParam("category") StoreType category,
             @PathVariable("customerId") Long customerId,
             @PathVariable("storeId") Long storeId) {
        return ApiResponse.onSuccess(new ArrayList<>());
    }

    @GetMapping("/{customerId}/store/{storeId}/detail")
    public ApiResponse<List<StoreResponseDTO.MoreDetailStoreDTO>> getStoreDetail
            (@PathVariable("customerId") Long customerId,
             @PathVariable("storeId") Long storeId) {
        return ApiResponse.onSuccess(new ArrayList<>());
    }

    @GetMapping("/test")
    public ApiResponse<Long> getStoreDetail
            () {
        Store store = storeCommandService.createStore(2L);
        return ApiResponse.onSuccess(store.getId());
    }
}
