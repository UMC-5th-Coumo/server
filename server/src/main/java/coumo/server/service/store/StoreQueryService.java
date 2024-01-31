package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.web.dto.StoreRequestDTO;
import coumo.server.web.dto.StoreResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {
    public Optional<Store> findStore(Long storeId);

    public Page<Store> findFamousStore(double longitude, double latitude, double distance);
    public Optional<List<Store>> findNearestStore(double longitude, double latitude, String category, Long customerId);
    public Optional<Store> findStoreInfoMoreDetail(Long storeId, Long customerId);

}
