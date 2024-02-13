package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.CustomerConverter;
import coumo.server.converter.OwnerConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.jwt.JWTUtil;
import coumo.server.service.customer.CustomerService;
import coumo.server.web.dto.CustomerRequestDTO;
import coumo.server.web.dto.CustomerResponseDTO;
import coumo.server.web.dto.OwnerRequestDTO;
import coumo.server.web.dto.OwnerResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerRestController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    @PostMapping("/join")
    @Operation(summary = "APP 회원가입 API",
            description = "APP 회원가입 API 구현")
    public ApiResponse<CustomerResponseDTO.CustomerJoinResultDTO> join(@RequestBody @Valid CustomerRequestDTO.CustomerJoinDTO request){
        Customer customer = customerService.joinCustomer(request);
        return ApiResponse.onSuccess(CustomerConverter.toJoinResultDTO(customer));
    }

    @PostMapping("/login")
    @Operation(summary = "APP 로그인 API",
            description = "APP 로그인 API 구현")
    public ApiResponse<CustomerResponseDTO.CustomerLoginResultDTO> login(@RequestBody @Valid CustomerRequestDTO.CustomerLoginDTO request){
        Customer customer = customerService.loginCustomer(request);

        if (customer != null) {
            // 로그인 성공 시 토큰 생성
            String token = jwtUtil.createJwt(request.getLoginId(), 3600000L); //토큰 만료 시간 1시간

            // LoginResultDTO를 생성하여 반환
            return ApiResponse.onSuccess(CustomerConverter.toCustomerLoginResultDTO(customer, token));
        } else {
            return ApiResponse.onFailure("400", "로그인에 실패했습니다.", null);
        }

    }
}
