package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.CustomerStoreRepository;
import coumo.server.repository.StoreRepository;
import coumo.server.util.geometry.Direction;
import coumo.server.util.geometry.GeometryUtil;
import coumo.server.util.geometry.Location;
import coumo.server.web.dto.StoreResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreQueryServiceImpl implements StoreQueryService{
    private final StoreRepository storeRepository;
    private final CustomerStoreRepository customerStoreRepository;

    @Override
    public Optional<Store> findStore(Long storeId) {
        return storeRepository.findById(storeId);
    }

    @Override
    public List<StoreResponseDTO.StoreStampInfo> findFamousStore(double longitude, double latitude, double distance, Pageable pageable) {
        //근처 매장 찾기
        Page<Store> pageStore = findNearestStore(longitude, latitude, distance, pageable);
        if (pageStore.isEmpty()) return Collections.emptyList();

        //쿠폰 도장이 기준
        List<Store> stores = pageStore.getContent();
        List<StoreResponseDTO.StoreStampInfo> storeStampInfos = new ArrayList<>();
        for (Store store : stores) {
            int total = 0;
            for (CustomerStore item : store.getCustomerStoreList()) {
                total += item.getStampTotal();
            }
            storeStampInfos.add(StoreResponseDTO.StoreStampInfo.builder()
                                .store(store).stampTotal(total).build());
        }

        // StampTotal이 높은 순으로 정렬
        storeStampInfos.sort((s1, s2) -> Integer.compare(s2.getStampTotal(), s1.getStampTotal()));

        return storeStampInfos;
    }

    @Override
    public Page<Store> findNearestStore(double longitude, double latitude, double distance, Pageable pageable) {
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
