package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.DesignReceiptConverter;
import coumo.server.domain.DesignReceipt;
import coumo.server.service.design.DesignReceiptService;
import coumo.server.service.owner.OwnerService;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class DesignReceiptController {

    private final OwnerService ownerService;
    private final DesignReceiptService designReceiptService;

    @PostMapping("/{ownerId}/coupon-ui-service")
    @Operation(summary = "쿠폰 UI 서비스 구매하기")
    @Parameters({@Parameter(name = "ownerId", description = "ownerId, path variable 입니다!"),
    })
    public ApiResponse<?> purchaseCouponUIService(@PathVariable Long ownerId, @RequestBody DesignReceiptRequestDTO requestDTO) {
        return ownerService.findOwner(ownerId).map(owner -> {
            DesignReceipt designReceipt = DesignReceiptConverter.fromDTO(requestDTO, owner);
            DesignReceipt savedReceipt = designReceiptService.createDesignReceipt(designReceipt);
            return ApiResponse.onSuccess(DesignReceiptConverter.toResponseDTO(savedReceipt));
        }).orElseGet(() -> ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다.",null));
    }
}
