package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.converter.StoreConverter;
import coumo.server.domain.OwnerCoupon;
import coumo.server.domain.Store;
import coumo.server.service.store.StoreCommandService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.web.dto.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<StoreResponseDTO.MoreDetailStoreDTO> getStoreDetail
            (@PathVariable("customerId") Long customerId,
             @PathVariable("storeId") Long storeId) {
        Store store = storeQueryService.findStore(storeId).orElseThrow();
        // <수정 필요> 유저도 없으면 예외 날려야 하니 위에 처럼 작성해야 됌.

        //가게 사장이 쿠폰에 대한 정보가 부족하다면 자세한 정보를 조회 할 수 없으니!
        List<OwnerCoupon> ownerCouponList = store.getOwner().getOwnerCouponList();
        if (ownerCouponList.isEmpty() || ownerCouponList.get(0).isAvailable() == false)
            throw new StoreHandler(ErrorStatus.STORE_NOT_ACCEPTABLE);

        return ApiResponse.onSuccess(StoreConverter.toMoreDetailStoreDTO(store, customerId));
    }

    @GetMapping("/test")
    public ApiResponse<Long> getStoreDetail
            () {
        Store store = storeCommandService.createStore(2L);
        return ApiResponse.onSuccess(store.getId());
    }
}
