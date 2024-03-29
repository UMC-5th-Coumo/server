package coumo.server.service.owner;

import coumo.server.converter.OwnerConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.domain.Store;
import coumo.server.domain.enums.State;
import coumo.server.repository.OwnerRepository;
import coumo.server.service.store.StoreCommandService;
import coumo.server.web.dto.LoginIdDTO;
import coumo.server.web.dto.OwnerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StoreCommandService storeCommandService;

    // Id로 사장님 찾기
    @Override
    public Optional<Owner> findOwner(Long ownerId) {

        return ownerRepository.findById(ownerId);
    }

    @Override
    public Owner findByLoginId(String loginId) {
        //loginId기반으로 계정조회
        return ownerRepository.findByLoginId(loginId);
    }

    @Override
    public Store findStore(String loginId) {
        Owner owner = ownerRepository.findByLoginIdWithStore(loginId).orElse(null);

        return (owner == null) ? null : owner.getStore();
    }

    // 회원가입 기능
    @Override
    @Transactional
    public Owner joinOwner(OwnerRequestDTO.JoinDTO request){

        Owner newOwner = OwnerConverter.toOwner(request);
        // 비밀번호를 BCrypt로 인코딩
        newOwner.setPassword(passwordEncoder.encode(request.getPassword()));
        Owner owner = ownerRepository.save(newOwner);
        storeCommandService.createStore(owner.getId());
        return owner;
    }

    // 로그인 기능
    @Override
    public Owner loginOwner(OwnerRequestDTO.LoginDTO request){
        String loginId = request.getLoginId();
        String password = request.getPassword();

        // loginId로 계정 조회.
        Owner owner = findByLoginId(loginId);

        // 소유자가 존재하고 비밀번호가 일치하는지 확인합니다.
        if (owner != null && isPasswordMatch(password, owner.getPassword())) {
            // 로그인 성공 시, 상태를 ACTIVE로 변경
            owner.setState(State.ACTIVE);
            ownerRepository.save(owner);
            return owner; // 로그인 성공 시 소유자 엔터티를 반환합니다.
        } else {
            return null; // 로그인 실패 시 null을 반환하거나 해당하는 방식으로 처리합니다.
        }
    }

    private boolean isPasswordMatch(String rawPassword, String encodedPassword){
        // BCryptPasswordEncoder를 사용하여 비밀번호 일치 여부 확인
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    // 로그인ID 중복확인
    @Override
    public boolean isLoginIdAvailable(String loginId){
        // 해당 로그인 ID가 데이터베이스에 존재하는지 확인
        return !ownerRepository.existsByLoginId(loginId);
    }

    //인증번호
    @Override
    public Optional<Owner> findOwnerByNameAndPhone(String name, String phone) {
        return ownerRepository.findByNameAndPhone(name, phone);
    }
    @Override
    public Optional<Owner> findOwnerByLoginIdAndPhone(String loginId, String phone) {
        return ownerRepository.findByLoginIdAndPhone(loginId, phone);
    }
    @Override
    public void resetPassword(String loginId, String newPassword) {
        Owner owner = ownerRepository.findByLoginId(loginId);
        if (owner != null) {
            owner.setPassword(passwordEncoder.encode(newPassword));
            ownerRepository.save(owner);
        }
    }

    //인증번호 검증
    @Override
    public Optional<LoginIdDTO> findLoginIdByPhone(String phone){
        return ownerRepository.findLoginIdByPhone(phone);
    }

    @Override
    public Owner saveOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}
