package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.domain.OwnerCoupon;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.service.coupon.CouponService;
import coumo.server.service.coupon.CustomerStoreService;
import coumo.server.service.coupon.OwnerCouponService;
import coumo.server.service.customer.CustomerService;
import coumo.server.service.owner.OwnerService;
import coumo.server.util.StampURL;
import coumo.server.validation.annotation.ExistOwner;
import coumo.server.web.dto.CouponRequestDTO;
import coumo.server.web.dto.CouponResponseDTO;
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
    private final CustomerService customerService;
    private final CouponService couponService;
    private final OwnerCouponService ownerCouponService;
    private final CustomerStoreService customerStoreService;

    @Operation(summary = "사장님 : 쿠폰 등록하기")
    @PostMapping("register/{ownerId}")
    @Parameter(name = "ownerId", description = "사장님 아이디, path variable")
    public ApiResponse<?> registerCoupon(@ExistOwner @PathVariable("ownerId") Long ownerId, @RequestBody CouponRequestDTO.registerCouponDTO dto){

        Optional<Owner> owner = ownerService.findOwner(ownerId);
        if(owner.isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 사장입니다.", ownerId);
        Long newCouponId = couponService.register(owner.get(), dto);
        // 스탬프 이미지 미리 저장해둘 건지 의논이 필요함.
        return ApiResponse.onSuccess(newCouponId);
    }

    @Operation(summary = "고객 : 해당 가게의 내 쿠폰 보기")
    @GetMapping("{storeId}/{customerId}")
    @Parameters({
            @Parameter(name = "storeId", description = "매장 아이디, path variable"),
            @Parameter(name = "customerId", description = "고객 아이디, path variable"),
    })
    public ApiResponse<?> storeCoupon(@PathVariable("storeId") Long storeId, @PathVariable("customerId") Long customerId){

        CustomerStore customerStore = customerStoreService.findCustomerStoreCoupon(storeId, customerId);
        OwnerCoupon ownerCoupon = ownerCouponService.findStoreCoupon(storeId);

        // 해당 매장의 고객 스탬프 개수
        CouponResponseDTO.CustomerStoreStampDTO customerStoreStampDTO = CouponResponseDTO.CustomerStoreStampDTO.builder()
                .stampCurrent(customerStore.getStampCurrent())
                .stampMax(customerStore.getStampMax())
                .updatedAt(customerStore.getUpdatedAt())
                .build();

        // 해당 매장의 쿠폰 디자인
        CouponResponseDTO.StoreCouponDTO storeCouponDTO = CouponResponseDTO.StoreCouponDTO.builder()
                .couponColor(ownerCoupon.getCouponColor())
                .storeName(ownerCoupon.getStoreName())
                .fontColor(ownerCoupon.getFontColor())
                .stampImage(StampURL.getURL(ownerCoupon.getStampImage()))
                .storeId(storeId)
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
    public ApiResponse<?> storeCoupon(@RequestParam(value = "filter") String filter , @PathVariable("customerId") Long customerId){

        Optional<Customer> customer = customerService.findCustomerById(customerId);
        if(customer.isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 유저입니다.", customerId);

        List<CouponResponseDTO.CustomerStoreCouponDTO> customerStoreCouponDTOS;
        switch (filter) {
            case "latest":
                customerStoreCouponDTOS = customerStoreService.findCustomerCouponLatest(customerId);
                break;
            case "most":
                customerStoreCouponDTOS = customerStoreService.findCustomerCouponMost(customerId);
                break;
            default:
                return ApiResponse.onFailure("400", "필터 값을 올바르게 입력하세요.", filter);
        }

        if (customerStoreCouponDTOS.isEmpty()) {
            return ApiResponse.onFailure("400", "해당 유저에게 쿠폰이 존재하지 않습니다.", filter);
        }

        customerStoreCouponDTOS.forEach(item -> {
            String stampUrl = StampURL.getURL(item.getStoreCouponDTO().getStampImage());
            item.getStoreCouponDTO().setStampImage(stampUrl);
        });
        return ApiResponse.onSuccess(customerStoreCouponDTOS);
    }
}
