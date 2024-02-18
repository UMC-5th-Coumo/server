package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.converter.MainCouponConverter;
import coumo.server.domain.OwnerCoupon;
import coumo.server.service.coupon.OwnerCouponService;
import coumo.server.web.dto.CouponResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MyCouponRestController {

    private final OwnerCouponService ownerCouponService;

    @Operation(summary = "대표 쿠폰 조회")
    @GetMapping("/maincoupon/{storeId}")
    @Parameter(name = "storeId", description = "가게 아이디, path variable")
    public ApiResponse<CouponResponseDTO.MainCouponDTO> registerCoupon(@PathVariable("storeId") Long storeId){
        OwnerCoupon storeCoupon = ownerCouponService.findStoreCoupon(storeId);

        if (storeCoupon == null) {
            throw new StoreHandler(ErrorStatus.OWNER_COUPON_BAD_REQUEST);
        }

        return ApiResponse.onSuccess(MainCouponConverter.toMainCouponDTO(storeCoupon));
    }
}
