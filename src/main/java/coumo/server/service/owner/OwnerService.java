package coumo.server.service.owner;

import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.web.dto.LoginIdDTO;
import coumo.server.web.dto.OwnerRequestDTO;

import java.util.Optional;

public interface OwnerService {
    Optional<Owner> findOwner(Long ownerId);

    Owner findByLoginId(String loginId);

    // 회원가입
    Owner joinOwner(OwnerRequestDTO.JoinDTO request);

    //로그인
    Owner loginOwner(OwnerRequestDTO.LoginDTO request);

    //로그인 중복확인
    boolean isLoginIdAvailable(String loginId);

    //인증번호
    Optional<Owner> findOwnerByNameAndPhone(String name, String phone);

    //인증번호 검증
    Optional<LoginIdDTO> findLoginIdByPhone(String phone);

    //로그아웃
    void logoutOwner(Long ownerId);

    //회원탈퇴
    void deleteOwner(Long ownerId);
}
