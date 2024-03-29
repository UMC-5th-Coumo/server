package coumo.server.web.controller;

import coumo.server.apiPayload.ApiResponse;
import coumo.server.converter.OwnerConverter;
import coumo.server.domain.Owner;
import coumo.server.domain.enums.State;
import coumo.server.jwt.JWTUtil;
import coumo.server.service.owner.OwnerService;
import coumo.server.service.store.StoreQueryService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
@Slf4j
public class OwnerRestController {

    private final OwnerService ownerService;
    private final JWTUtil jwtUtil;
    private final MessageService messageService;
    private final VerificationCodeStorage verificationCodeStorage;
    private final StoreQueryService storeQueryService;

    @PostMapping("/join")
    @Operation(summary = "WEB 회원가입 API",
            description = "WEB 회원가입 API 구현")
    public ApiResponse<OwnerResponseDTO.JoinResultDTO> join(@RequestBody @Valid OwnerRequestDTO.JoinDTO request){
        Owner owner = ownerService.joinOwner(request);
        return ApiResponse.onSuccess(OwnerConverter.toJoinResultDTO(owner));
    }

    @PostMapping("/login")
    @Operation(summary = "WEB 로그인 API",
            description = "WEB 로그인 API 구현")
    public ApiResponse<Object> login(@RequestBody @Valid OwnerRequestDTO.LoginDTO request){
        Owner owner = ownerService.loginOwner(request);

        if (owner != null) {
            // 로그인 성공 시 토큰 생성
            String token = jwtUtil.createJwt(request.getLoginId(), 3600000L); //토큰 만료 시간 1시간

            //강제 정보 작성 여부
            Boolean isWrite = storeQueryService.isWriteStore(owner);

            log.info("isWrite={}", isWrite);

            // LoginResultDTO를 생성하여 반환
            return ApiResponse.onSuccess(OwnerConverter.toLoginResultDTO(owner, token, isWrite));
        } else {
            return ApiResponse.onFailure("400", "로그인에 실패했습니다.", null);
        }

    }

    @PostMapping("/join/check-login-id")
    @Operation(summary = "WEB 로그인 ID 중복 확인 API",
            description = "WEB 로그인 ID 중복 확인 API 구현")
    public ApiResponse<OwnerResponseDTO.CheckLoginIdResponseDTO> checkLoginId(@RequestBody @Valid OwnerRequestDTO.CheckLoginIdDTO request){
        boolean isLoginIdAvailable = ownerService.isLoginIdAvailable(request.getLoginId());

        if(isLoginIdAvailable){
            // 로그인 ID가 사용 가능할 경우
            return ApiResponse.onSuccess(OwnerResponseDTO.CheckLoginIdResponseDTO.builder()
                    .loginId(request.getLoginId())
                    .build());
        } else {
            // 로그인 ID가 이미 사용 중인 경우
            return ApiResponse.onFailure("400", "이미 사용 중인 로그인 ID입니다.", null);
        }
    }

//    @GetMapping("/mypage/{ownerId}/profile")
//    @Operation(summary = "WEB 마이페이지 내 프로필 조회 API", description = "로그인한 사장님의 마이페이지 프로필을 조회합니다.")
//    @Parameters({
//            @Parameter(name = "ownerId", description = "ownerId, path variable 입니다!"),
//    })
//    public ApiResponse<OwnerResponseDTO.MyPageDTO> myPage(@PathVariable Long ownerId){
//        return ownerService.findOwner(ownerId)
//                .map(owner -> ApiResponse.onSuccess(OwnerConverter.toMyPageDTO(owner)))
//                .orElse(ApiResponse.onFailure("400", "사용자 정보를 찾을 수 없습니다.", null));
//    }

    @PostMapping("/find-id")
    @Operation(summary = "[아이디찾기] WEB 인증번호 전송 API", description = "인증번호가 전송되었는지 확인합니다.")
    public ApiResponse<String> sendVerificationCode(@RequestBody OwnerRequestDTO.OwnerLoginVerificationRequest request) {
        Optional<Owner> ownerOpt = ownerService.findOwnerByNameAndPhone(request.getName(), request.getPhone());
        if (ownerOpt.isPresent()) {
            messageService.sendMessage(request.getPhone());
            return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자 정보를 찾을 수 없습니다.", null);
        }
    }

    @PostMapping("/verify-code")
    @Operation(summary = "[아이디찾기] WEB 인증번호 검증 및 로그인 ID 반환 API", description = "인증번호가 일치하는지 확인하고 loginId를 반환합니다.")
    public ApiResponse<VerificationResponseDTO> verifyCode(@RequestBody OwnerRequestDTO.OwnerLoginVerificationCodeDTO dto) {
        boolean isVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());

        if (isVerified) {
            // 인증번호 검증 성공 시, loginId를 찾아서 함께 반환
            Optional<LoginIdDTO> loginIdDto = ownerService.findLoginIdByPhone(dto.getPhone());
            if (loginIdDto.isPresent()) {
                String loginId = loginIdDto.get().getLoginId();
                return ApiResponse.onSuccess(VerificationResponseDTO.success(loginId, dto.getVerificationCode()));
            } else {
                return ApiResponse.onFailure("404", "로그인 ID를 찾을 수 없습니다.", null);
            }
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);

        }
    }

    @PostMapping("/join/send-verification-code")
    @Operation(summary = "[회원가입] WEB 인증번호 전송 API", description = "인증번호가 전송되었는지 확인합니다.")
    public ApiResponse<String> joinSendVerificationCode(@RequestBody OwnerRequestDTO.OwnerVerificationRequest request){
        messageService.sendMessage(request.getPhone());
        return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
    }

    @PostMapping("/join/verify-code")
    @Operation(summary = "[회원가입] WEB 인증번호 검증 API", description = "인증번호가 일치하는지 확인합니다.")
    public ApiResponse<VerificationSuccessResponse> joinVerifyCode(@RequestBody OwnerRequestDTO.OwnerVerificationCodeDTO dto) {
        boolean isWebVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());
        if (isWebVerified) {
            return ApiResponse.onSuccess(VerificationSuccessResponse.successWithCode(dto.getVerificationCode()));
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);
        }
    }

//    @PatchMapping("/logout/{ownerId}")
//    @Operation(summary = "WEB 로그아웃 API", description = "Owner의 State = SLEEP으로 설정.")
//    @Parameters({
//            @Parameter(name = "ownerId", description = "ownerId, path variable 입니다!"),
//    })
//    public ApiResponse<String> logoutOwner(@PathVariable Long ownerId) {
//        return ownerService.findOwner(ownerId)
//                .map(owner -> {
//                    owner.setState(State.SLEEP);
//                    ownerService.saveOwner(owner);
//                    return ApiResponse.onSuccess("로그아웃에 성공했습니다.");
//                })
//                .orElse(ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null));
//    }

    @DeleteMapping("/delete/{ownerId}")
    @Operation(summary = "WEB 회원탈퇴 API", description = "회원정보 삭제")
    @Parameters({
            @Parameter(name = "ownerId", description = "ownerId, path variable 입니다!"),
    })
    public ApiResponse<String> deleteOwner(@PathVariable Long ownerId) {
        Optional<Owner> ownerOptional = ownerService.findOwner(ownerId);
        if(ownerOptional.isPresent()){
            ownerService.deleteOwner(ownerId);
            return ApiResponse.onSuccess("회원탈퇴가 완료되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null);
        }
//        return ownerService.findOwner(ownerId)
//                .map(owner -> {
//                    owner.setState(State.LEAVE);
//                    ownerService.saveOwner(owner);
//                    return ApiResponse.onSuccess("회원탈퇴가 완료되었습니다.");
//                })
//                .orElse(ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다", null));
    }

    @PostMapping("/reset-password/send-code")
    @Operation(summary = "[비밀번호찾기] WEB 인증번호 전송 API",description = "비밀번호 재설정을 위한 인증 코드 전송")
    public ApiResponse<String> sendResetPasswordCode(@RequestBody OwnerRequestDTO.OwnerPasswordResetSendCodeDTO dto) {
        Optional<Owner> ownerOptional = ownerService.findOwnerByLoginIdAndPhone(dto.getLoginId(), dto.getPhone());
        if (ownerOptional.isPresent()) {
            messageService.sendMessage(dto.getPhone());
            return ApiResponse.onSuccess("인증번호가 전송되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "사용자를 찾을 수 없습니다.", null);
        }
    }

    @PostMapping("/reset-password/verify-code")
    @Operation(summary = "[비밀번호찾기] WEB 인증번호 검증 API", description = "코드 검증")
    public ApiResponse<VerificationPwSuccessResponse> verifyCodePassword(@RequestBody OwnerRequestDTO.OwnerPasswordVerifyCodeDTO dto) {
        boolean isVerified = verificationCodeStorage.verifyCode(dto.getPhone(), dto.getVerificationCode());
        if (isVerified) {
            return ApiResponse.onSuccess(VerificationPwSuccessResponse.successWithCode("인증번호 검증에 성공했습니다.", dto.getVerificationCode()));
        } else {
            return ApiResponse.onFailure("400", "인증번호가 일치하지 않습니다.", null);
        }
    }

    @PostMapping("/reset-password/set-pw")
    @Operation(summary = "[비밀번호찾기] WEB 비밀번호 재설정 API", description = "비밀번호 재설정")
    public ApiResponse<String> resetPassword(@RequestBody OwnerRequestDTO.OwnerPasswordResetDTO dto){
        Owner owner = ownerService.findByLoginId(dto.getLoginId());
        if (owner != null) {
            ownerService.resetPassword(dto.getLoginId(), dto.getNewPassword());
            return ApiResponse.onSuccess("비밀번호가 성공적으로 재설정되었습니다.");
        } else {
            return ApiResponse.onFailure("404", "해당 사용자를 찾을 수 없습니다.", null);
        }
    }
}
