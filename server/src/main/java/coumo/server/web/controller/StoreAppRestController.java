package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.converter.StoreConverter;
import coumo.server.domain.Store;
import coumo.server.domain.enums.StoreType;
import coumo.server.service.store.StoreCommandService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.web.dto.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
@Slf4j
public class StoreAppRestController {
    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;

    @GetMapping("/store")
    public ApiResponse<List<StoreResponseDTO.FamousStoreDTO>> getFamousStore(@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude) {
        if (longitude < -180|| longitude > 180 || latitude > 90 || latitude < -90)  throw new StoreHandler(ErrorStatus.STORE_POINT_BAD_REQUEST);
        List<StoreResponseDTO.StoreStampInfo> famousStore = storeQueryService.findFamousStore(longitude, latitude, 0.5, PageRequest.of(0, 5));
        return ApiResponse.onSuccess(StoreConverter.toFamousStoreDTO(famousStore));
    }


    @GetMapping("/{customerId}/store/{storeId}")
    public ApiResponse<List<StoreResponseDTO.NearestStoreDTO>> getNearestStore
            (@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude,
             @RequestParam("category") String category, @RequestParam("page") Integer page,
             @PathVariable("customerId") Long customerId) {
        if (longitude < -180|| longitude > 180 || latitude > 90 || latitude < -90)  throw new StoreHandler(ErrorStatus.STORE_POINT_BAD_REQUEST);
        if (page < 0) throw new StoreHandler(ErrorStatus._PAGE_OVER_RANGE);

        Page<Store> nearestStore = storeQueryService.findNearestStore(longitude, latitude, 0.5, Optional.of(category), PageRequest.of(page, 10));
        return ApiResponse.onSuccess(StoreConverter.toNearestStoreDTO(nearestStore, customerId));
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
