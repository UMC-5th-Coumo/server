package coumo.server.service.owner;

import coumo.server.domain.Owner;
import coumo.server.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;


    // Id로 사장님 찾기
    @Override
    public Optional<Owner> findOwner(Long ownerId) {

        return ownerRepository.findById(ownerId);
    }
}
