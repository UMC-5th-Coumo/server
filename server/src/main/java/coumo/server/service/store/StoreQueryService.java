package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.web.dto.StoreResponseDTO;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {
    public Optional<Store> findStore(Long storeId);

    public Optional<List<Store>> findFamousStore(double x, double y);
    public Optional<List<Store>> findNearestStore(double x, double y, String category, Long customerId);
    public Optional<Store> findStoreInfoMoreDetail(Long storeId, Long customerId);

}
