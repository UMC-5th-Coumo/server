package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.domain.DesignReceipt;
import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.service.coupon.CouponService;
import coumo.server.service.coupon.CustomerStoreService;
import coumo.server.service.coupon.OwnerCouponService;
import coumo.server.service.design.DesignReceiptService;
import coumo.server.service.owner.OwnerService;
import coumo.server.validation.annotation.ExistOwner;
import coumo.server.web.dto.CouponRequestDTO;
import coumo.server.web.dto.CouponResponseDTO;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class CouponController {

    private final OwnerService ownerService;
    private final CouponService couponService;
    private final OwnerCouponService ownerCouponService;
    private final CustomerStoreService customerStoreService;

    @Operation(summary = "사장님 : 쿠폰 등록하기")
    @PostMapping("register/{ownerId}")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> registerCoupon(@ExistOwner @PathVariable Long ownerId, @RequestBody CouponRequestDTO.registerCouponDTO dto){

        Optional<Owner> owner = ownerService.findOwner(ownerId);
        OwnerCoupon newCoupon = couponService.register(owner.get(), dto);
        // 스탬프 이미지 미리 저장해둘 건지 의논이 필요함.
        return ApiResponse.onSuccess(newCoupon.getId());
    }

    @Operation(summary = "고객 : 해당 가게의 내 쿠폰 보기")
    @GetMapping("{storeId}/{customerId}")
    @Parameters({
            @Parameter(name = "storeId", description = "매장 아이디, path variable"),
            @Parameter(name = "customerId", description = "고객 아이디, path variable"),
    })
    public ApiResponse<?> storeCoupon(@PathVariable Long storeId, @PathVariable Long customerId){

        CustomerStore customerStore = customerStoreService.findCustomerStoreCoupon(storeId, customerId);
        OwnerCoupon ownerCoupon = ownerCouponService.findStoreCoupon(storeId);

        // 해당 매장의 고객 스탬프 개수
        CouponResponseDTO.CustomerStoreStampDTO customerStoreStampDTO = CouponResponseDTO.CustomerStoreStampDTO.builder()
                .stampCurrent(customerStore.getStampCurrent())
                .stampMax(customerStore.getStampMax())
                .build();

        // 해당 매장의 쿠폰 디자인
        CouponResponseDTO.StoreCouponDTO storeCouponDTO = CouponResponseDTO.StoreCouponDTO.builder()
                .couponColor(ownerCoupon.getCouponColor())
                .storeName(ownerCoupon.getStoreName())
                .fontColor(ownerCoupon.getFontColor())
                .stampImage(ownerCoupon.getStampImage())
                .build();

        CouponResponseDTO.CustomerStoreCouponDTO customerStoreCouponDTO = CouponResponseDTO.CustomerStoreCouponDTO.builder()
                .customerStoreStampDTO(customerStoreStampDTO)
                .storeCouponDTO(storeCouponDTO)
                .build();

        return ApiResponse.onSuccess(customerStoreCouponDTO);
    }


    @Operation(summary = "고객 : 내 쿠폰 보기 (필터 두 가지)")
    @GetMapping("{customerId}/list")
    @Parameter(name = "customerId", description = "고객 아이디, path variable")
    public ApiResponse<?> storeCoupon(@PathVariable Long customerId, @RequestParam("filter") String filter){

        if(filter == "latest")          // latest : 최근 적립한 순서
        {
            List<CouponResponseDTO.CustomerStoreCouponDTO> customerStoreCouponDTOS = customerStoreService.findCustomerCouponLatest(customerId);
            return ApiResponse.onSuccess(customerStoreCouponDTOS);
        }
        else if (filter == "most")      // most : 많이 적립한 순서
        {
            List<CouponResponseDTO.CustomerStoreCouponDTO> customerStoreCouponDTOS = customerStoreService.findCustomerCouponMost(customerId);
            return ApiResponse.onSuccess(customerStoreCouponDTOS);
        }
        return ApiResponse.onFailure("400", "필터 값을 올바르게 입력하세요", filter);
    }
}
