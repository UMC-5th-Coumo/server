package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreQueryServiceImpl implements StoreQueryService{
    private final StoreRepository storeRepository;

    @Override
    public Optional<Store> findStore(Long storeId) {
        return storeRepository.findById(storeId);
    }

    @Override
    public Optional<List<Store>> findFamousStore(double x, double y) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Store>> findNearestStore(double x, double y, String category, Long customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Store> findStoreInfoMoreDetail(Long storeId, Long customerId) {
        return Optional.empty();
    }
}
