package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.CustomerConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.domain.enums.State;
import coumo.server.jwt.JWTUtil;
import coumo.server.service.customer.CustomerService;
import coumo.server.sms.MessageService;
import coumo.server.sms.VerificationCodeStorage;
import coumo.server.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerRestController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;
    private final MessageService messageService;
    private final VerificationCodeStorage verificationCodeStorage;

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

    @PostMapping("/join/check-login-id")
    @Operation(summary = "APP 로그인 ID 중복 확인 API",
            description = "APP 로그인 ID 중복 확인 API 구현")
    public ApiResponse<CustomerResponseDTO.CheckCustomerLoginIdResponseDTO> checkCustomerLoginId(@RequestBody @Valid CustomerRequestDTO.CheckCustomerLoginIdDTO request){
        boolean isLoginIdAvailable = customerService.isLoginIdAvailable(request.getLoginId());

        if(isLoginIdAvailable){
            // 로그인 ID가 사용 가능할 경우
            return ApiResponse.onSuccess(CustomerResponseDTO.CheckCustomerLoginIdResponseDTO.builder()
                    .loginId(request.getLoginId())
                    .build());
        } else {
            // 로그인 ID가 이미 사용 중인 경우
            return ApiResponse.onFailure("400", "이미 사용 중인 로그인 ID입니다.", null);
        }
    }

    @GetMapping("/mypage/{customerId}/profile")
    @Operation(summary = "APP 마이페이지 내 프로필 조회 API", description = "로그인한 사용자의 마이페이지 프로필을 조회합니다.")
    @Parameters({
            @Parameter(name = "customerId", description = "customerId, path variable 입니다!"),
    })
    public ApiResponse<CustomerResponseDTO.CustomerMyPageDTO> getCustomerMyPage(@PathVariable Long customerId){
        Optional<Customer> customerOpt = customerService.findCustomerById(customerId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerResponseDTO.CustomerMyPageDTO customerMyPageDTO = CustomerConverter.toCustomerMyPageDTO(customer);
            return ApiResponse.onSuccess(customerMyPageDTO);
        } else {
            return ApiResponse.onFailure("404", "사용자 정보를 찾을 수 없습니다.", null);
        }
    }

    @PostMapping("/find-id")
    @Operation(summary = "[아이디찾기] APP 인증번호 전송 API", description = "인증번호가 전송되었는지 확인합니다.")
    public ApiResponse<String> sendVerificationCode(@RequestBody CustomerRequestDTO.VerificationRequest request) {
        Optional<Customer> customerOpt = customerService.findCustomerByNameAndPhone(request.getName(), request.getPhone());
        if (customerOpt.isPresent()) {
            messageService.sendMessage(request.getPhone());
            return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자 정보를 찾을 수 없습니다.", null);
        }
    }

    @GetMapping("/verify-code")
    @Operation(summary = "[아이디찾기] APP 인증번호 검증 및 로그인 ID 반환 API", description = "인증번호가 일치하는지 확인하고 loginId를 반환합니다.")
    public ApiResponse<String> verifyCode(@RequestBody CustomerRequestDTO.VerificationCodeDTO dto) {
        boolean isVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());

         if (isVerified) {
             String loginId = customerService.findLoginIdByPhone(dto.getPhone())
                     .map(LoginIdDTO::getLoginId) // LoginIdDTO 객체에서 loginId필드값 추출
                     .orElse("로그인 ID를 찾을 수 없습니다.");
            return ApiResponse.onSuccess(loginId);
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);
        }
    }

    @PostMapping("/join/send-verification-code")
    @Operation(summary = "[회원가입] APP 인증번호 전송 API", description = "인증번호가 전송되었는지 확인합니다.")
    public ApiResponse<String> joinSendVerificationCode(@RequestBody CustomerRequestDTO.CustomerVerificationRequest request){
        messageService.sendMessage(request.getPhone());
        return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
    }

    @PostMapping("/join/verify-code")
    @Operation(summary = "[회원가입] APP 인증번호 검증 API", description = "인증번호가 일치하는지 확인합니다.")
    public ApiResponse<CustomerVerificationSuccessResponse> joinVerifyCode(@RequestBody CustomerRequestDTO.CustomerVerificationCodeDTO dto) {
        boolean isAPPVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());
        if (isAPPVerified) {
            return ApiResponse.onSuccess(CustomerVerificationSuccessResponse.customerSuccessWithCode(dto.getVerificationCode()));
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);
        }
    }

    @PatchMapping("/logout/{customerId}")
    @Operation(summary = "APP 로그아웃 API", description = "Customer의 State = SLEEP으로 설정.")
    @Parameters({
            @Parameter(name = "customerId", description = "customerId, path variable 입니다!"),
    })
    public ApiResponse<String> logoutCustomer(@PathVariable Long customerId) {
        return customerService.findCustomerById(customerId)
                .map(customer -> {
                    customer.setState(State.SLEEP);
                    customerService.saveCustomer(customer);
                    return ApiResponse.onSuccess("로그아웃에 성공했습니다.");
                })
                .orElse(ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null));
    }
    @DeleteMapping("/delete/{customerId}")
    @Operation(summary = "APP 회원탈퇴 API", description = "회원정보 삭제")
    @Parameters({
            @Parameter(name = "customerId", description = "customerId, path variable 입니다!"),
    })
    public ApiResponse<String> deleteCustomer(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findCustomerById(customerId);
        if(customerOptional.isPresent()){
            customerService.deleteCustomer(customerId);
            return ApiResponse.onSuccess("회원탈퇴가 완료되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null);
        }
//        return customerService.findCustomerById(customerId)
//                .map(customer -> {
//                    customer.setState(State.LEAVE);
//                    customerService.saveCustomer(customer);
//                    return ApiResponse.onSuccess("회원탈퇴가 완료되었습니다.");
//                })
//                .orElse(ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null));
    }

    @PostMapping("/reset-password/send-code")
    @Operation(summary = "[비밀번호찾기] APP 인증번호 전송 API",description = "비밀번호 재설정을 위한 인증 코드 전송")
    public ApiResponse<String> sendResetPasswordCode(@RequestBody CustomerRequestDTO.CustomerPasswordResetSendCodeDTO dto) {
        Optional<Customer> customerOptional = customerService.findCustomerByLoginIdAndPhone(dto.getLoginId(), dto.getPhone());
        if (customerOptional.isPresent()) {
            messageService.sendMessage(dto.getPhone());
            return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다.", null);
        }
    }

    @PostMapping("/reset-password/verify-code")
    @Operation(summary = "[비밀번호찾기] APP 인증번호 검증 API", description = "코드 검증")
    public ApiResponse<VerificationAppPwSuccessResponse> verifyCodePasswordApp(@RequestBody CustomerRequestDTO.CustomerPasswordVerifyCodeDTO dto) {
        boolean isVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());
        if (isVerified) {
            return ApiResponse.onSuccess(VerificationAppPwSuccessResponse.successWithCode("인증번호 검증에 성공했습니다.", dto.getVerificationCode()));
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);
        }
    }

    @PatchMapping("/reset-password/set-pw")
    @Operation(summary = "[비밀번호찾기] APP 비밀번호 재설정 API", description = "비밀번호 재설정")
    public ApiResponse<String> verifyCodeAndResetPassword(@RequestBody CustomerRequestDTO.CustomerPasswordResetDTO dto) {
        Customer customer = customerService.findByLoginId(dto.getLoginId());
        if (customer != null) {
            customerService.resetPassword(dto.getLoginId(), dto.getNewPassword());
            return ApiResponse.onSuccess("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "해당 사용자를 찾을 수 없습니다.", null);
        }
    }
}
