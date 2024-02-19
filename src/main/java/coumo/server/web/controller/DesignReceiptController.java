package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.DesignReceiptConverter;
import coumo.server.domain.DesignReceipt;
import coumo.server.domain.enums.ReceiptState;
import coumo.server.service.design.DesignReceiptService;
import coumo.server.service.owner.OwnerService;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import coumo.server.web.dto.DesignReceiptServiceResponseDTO;
import coumo.server.web.dto.DesignReceiptStateChangeRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{ownerId}/receipts")
    @Operation(summary = "쿠폰 UI 서비스 신청내역 보기")
    public ApiResponse<List<DesignReceiptServiceResponseDTO>> getDesignReceiptsByOwner(@PathVariable Long ownerId) {
        List<DesignReceiptServiceResponseDTO> response = designReceiptService.findAllDesignReceiptsByOwnerId(ownerId);
        if(!response.isEmpty()){
            return ApiResponse.onSuccess(response);
        } else {
            return ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다.", null);
        }
    }

    @PostMapping("/{ownerId}/receipts/{receiptId}/change-state")
    @Operation(summary = "쿠폰 UI 서비스 영수증 작업상태 변경하기" , description = "APPLIED:신청접수, WORKING:작업중, COMPLETED:작업완료")
    public ApiResponse<?> changeReceiptState(
            @PathVariable Long ownerId,
            @PathVariable Long receiptId,
            @RequestBody DesignReceiptStateChangeRequestDTO requestDTO) {
        designReceiptService.changeDesignReceiptState(receiptId, requestDTO.getState());
        return ApiResponse.onSuccess("state가 " + requestDTO.getState().name() + "로 변경되었습니다.");
    }
}
