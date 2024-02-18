package coumo.server.web.controller;

import com.google.zxing.WriterException;
import coumo.server.apiPayload.ApiResponse;
import coumo.server.service.coupon.CustomerStoreService;
import coumo.server.service.customer.CustomerService;
import coumo.server.service.qr.QRService;
import coumo.server.service.store.StoreQueryService;
import coumo.server.web.dto.QRRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QRController {

    private final QRService qrService;
    private final StoreQueryService storeQueryService;
    private final CustomerService customerService;
    private final CustomerStoreService customerStoreService;


    @Operation(summary = "고객 : 도장 적립용 QR 생성")
    @GetMapping("/customer/stamp/{customerId}/{storeId}")
    @Parameters({
            @Parameter(name = "customerId", description = "고객 아이디, path variable"),
            @Parameter(name = "storeId", description = "도장을 적립하고자 하는 가게, path variable")})
    public ResponseEntity<byte[]> QRCustomerStamp(@PathVariable("customerId") Integer customerId, @PathVariable("storeId") Integer storeId) throws WriterException {

        return qrService.createQR(customerId, storeId);
    }

    @Operation(summary = "고객 : 도장 사용용 QR 생성")
    @GetMapping("/customer/payment/{customerId}/{storeId}")
    @Parameters({
            @Parameter(name = "customerId", description = "고객 아이디, path variable"),
            @Parameter(name = "storeId", description = "도장을 적립하고자 하는 가게, path variable")})
    public ResponseEntity<byte[]> QRCustomerPayment(@PathVariable("customerId") Integer customerId, @PathVariable("storeId") Integer storeId) throws WriterException {

        return qrService.createQR(customerId, storeId);
    }


    @Operation(summary = "사장 : 도장 적립 정보 전달")
    @PostMapping("/owner/stamp")
    public ApiResponse<?> QROwnerStamp(@RequestBody QRRequestDTO dto){

        if(customerService.findCustomerById(dto.getCustomerId()).isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 고객입니다.", dto.getCustomerId());
        if(storeQueryService.findStore(dto.getStoreId()).isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 매장입니다.", dto.getStoreId());

        Integer stampCurrent = qrService.stampToCoupon(dto);
        return ApiResponse.onSuccess(stampCurrent);
    }

    @Operation(summary = "사장 : 도장 사용 정보 전달")
    @PostMapping("/owner/payment")
    public ApiResponse<?> QROwnerPayment(@RequestBody QRRequestDTO dto){

        if(customerService.findCustomerById(dto.getCustomerId()).isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 고객입니다.", dto.getCustomerId());
        if(storeQueryService.findStore(dto.getStoreId()).isEmpty()) return ApiResponse.onFailure("400", "존재하지 않는 매장입니다.", dto.getStoreId());

        Integer stampCurrent = qrService.stampFromCoupon(dto);
        if(stampCurrent < 0) return ApiResponse.onFailure("400", "도장 개수가 부족합니다.", -1 * stampCurrent - 1);
        else if(stampCurrent == null) return ApiResponse.onFailure("400", "쿠폰이 존재하지 않습니다.", null);
        else return ApiResponse.onSuccess(stampCurrent);
    }
}
