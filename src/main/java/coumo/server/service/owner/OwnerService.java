package coumo.server.service.owner;

import coumo.server.domain.Owner;
import coumo.server.web.dto.OwnerRequestDTO;

import java.util.Optional;

public interface OwnerService {
    Optional<Owner> findOwner(Long ownerId);

    Owner findByLoginId(String loginId);

    // 회원가입
    Owner joinOwner(OwnerRequestDTO.JoinDTO request);

    //로그인
    Owner loginOwner(OwnerRequestDTO.LoginDTO request);
}
