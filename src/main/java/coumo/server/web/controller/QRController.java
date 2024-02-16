package coumo.server.web.controller;

import com.google.zxing.WriterException;
import coumo.server.apiPayload.ApiResponse;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.service.qr.QRService;
import coumo.server.web.dto.QRRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QRController {

    private final QRService qrService;

    @Operation(summary = "고객 : 도장 적립용 QR 생성")
    @GetMapping("/customer/stamp/{customerId}/{storeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "고객 아이디, path variable"),
            @Parameter(name = "storeId", description = "도장을 적립하고자 하는 가게, path variable")})
    public ResponseEntity<byte[]> QRCustomerStamp(@PathVariable("customerId") Integer customerId, @PathVariable("storeId") Integer storeId) throws WriterException {

        return qrService.createQR(customerId, storeId);
    }

    @Operation(summary = "고객 : 도장 사용용 QR 생성")
    @GetMapping("/customer/payment/{customerId}/{storeId}")
    @Parameters({
            @Parameter(name = "ownerId", description = "고객 아이디, path variable"),
            @Parameter(name = "storeId", description = "도장을 적립하고자 하는 가게, path variable")})
    public ResponseEntity<byte[]> QRCustomerPayment(@PathVariable("customerId") Integer customerId, @PathVariable("storeId") Integer storeId) throws WriterException {

        return qrService.createQR(customerId, storeId);
    }


    @Operation(summary = "사장 : 도장 적립 정보 전달")
    @PostMapping("/owner/stamp")
    public ApiResponse<?> QROwnerStamp(@RequestBody QRRequestDTO dto){

        Optional<CustomerStore> updateCustomerStore = qrService.stampToCoupon(dto);
        return ApiResponse.onSuccess(updateCustomerStore.get());
    }

    @Operation(summary = "사장 : 도장 사용 정보 전달")
    @PostMapping("/owner/payment")
    public ApiResponse<?> QROwnerPayment(@RequestBody QRRequestDTO dto){

        Optional<CustomerStore> updateCustomerStore = qrService.stampFromCoupon(dto);
        if(updateCustomerStore == null) return ApiResponse.onFailure("400", "쿠폰을 사용할 수 없습니다.", null);
        return ApiResponse.onSuccess(updateCustomerStore);
    }
}
