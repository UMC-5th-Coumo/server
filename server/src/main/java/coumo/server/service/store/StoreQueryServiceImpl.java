package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.repository.StoreRepository;
import coumo.server.util.geometry.Direction;
import coumo.server.util.geometry.GeometryUtil;
import coumo.server.util.geometry.Location;
import coumo.server.web.dto.StoreRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Store> findFamousStore(double longitude, double latitude, double distance, Pageable pageable) {
        Location northEast = GeometryUtil.calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil.calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        return storeRepository.findNearByStores(x1, y1, x2, y2, pageable);
    }

    @Override
    public Optional<List<Store>> findNearestStore(double longitude, double latitude, String category, Long customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Store> findStoreInfoMoreDetail(Long storeId, Long customerId) {
        return Optional.empty();
    }
}
