package coumo.server.service.owner;

import coumo.server.domain.Owner;
import coumo.server.repository.OwnerRepository;
import coumo.server.web.dto.CustomOwnerDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomOwnerDetailsService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    public CustomOwnerDetailsService(OwnerRepository ownerRepository){
        this.ownerRepository = ownerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
        //DB조회
        Owner ownerData = ownerRepository.findByLoginId(loginId);

        if(ownerData != null){
            //UserDetails에 담아서 return 하면 AuthenticationManager가 검증
            return new CustomOwnerDetails(ownerData);
        }

        throw new UsernameNotFoundException("User not found with loginId: " + loginId); //아이디 못찾은 경우
    }
}
