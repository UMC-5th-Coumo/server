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

    Optional<Owner> findOwnerByLoginIdAndPhone(String loginId, String phone);

    void resetPassword(String loginId, String newPassword);

    //인증번호 검증
    Optional<LoginIdDTO> findLoginIdByPhone(String phone);

    Owner saveOwner(Owner owner);

    void deleteOwner(Long ownerId);
}
