package coumo.server.service.store;

import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.domain.*;
import coumo.server.domain.enums.StoreType;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.*;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreQueryServiceImpl implements StoreQueryService{
    private final StoreRepository storeRepository;
    private final StoreImageRepository storeImageRepository;
    private final CustomerStoreRepository customerStoreRepository;
    private final MenuRepository menuRepository;
    private final OwnerCouponRepository ownerCouponRepository;

    @Override
    public Optional<Store> findStore(Long storeId) {
        return storeRepository.findById(storeId);
    }

    @Override
    public Optional<List<Menu>> findMenus(Long storeId) {
        Optional<Store> optionalStore= storeRepository.findByIdWithMenus(storeId);
        if (optionalStore.isEmpty()) return Optional.empty();
        return Optional.of(optionalStore.get().getMenuList());
    }

    @Override
    public Optional<List<StoreImage>> findStoreImages(Long storeId) {
        Optional<Store> optionalStore = storeRepository.findByIdWithImages(storeId);
        if (optionalStore.isEmpty()) return Optional.empty();
        return Optional.of(optionalStore.get().getStoreImageList());
    }

    @Override
    public Optional<List<Timetable>> findTimeTables(Long storeId) {
        Optional<Store> optionalStore = storeRepository.findByIdWithTimetables(storeId);
        if (optionalStore.isEmpty()) return Optional.empty();
        return Optional.of(optionalStore.get().getTimetableList());
    }

    @Override
    public List<StoreResponseDTO.StoreStampInfo> findFamousStore(double latitude, double longitude, double distance, Pageable pageable) {
        //근처 매장 찾기
        Page<Store> pageStore = findNearestStore(latitude, longitude, distance, Optional.empty(), pageable);
        if (pageStore.isEmpty()) return Collections.emptyList();

        //쿠폰 도장이 기준
        List<Store> stores = pageStore.getContent();
        List<StoreResponseDTO.StoreStampInfo> storeStampInfos = new ArrayList<>();
        for (Store store : stores) {
            int total = 0;
            for (CustomerStore item : store.getCustomerStoreList()) {
                total += item.getStampTotal();
            }
            List<StoreImage> storeImages = storeImageRepository.findAllByStore(store).orElse(Collections.emptyList());
            storeStampInfos.add(StoreResponseDTO.StoreStampInfo.builder()
                                .store(store)
                                .image(storeImages.isEmpty() ?  null : storeImages.get(0).getStoreImage())
                                .stampTotal(total)
                                .build());
        }

        // StampTotal이 높은 순으로 정렬
        storeStampInfos.sort((s1, s2) -> Integer.compare(s2.getStampTotal(), s1.getStampTotal()));

        return storeStampInfos;
    }

    @Override
    public Page<Store> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        log.info("x1={}, y1={}, x2={}, y2={}", x1, y1, x2, y2);

        if (category.isPresent()) return storeRepository.findNearByStores(x1, y1, x2, y2, category.get(), pageable);
        else return storeRepository.findNearByStores(x1, y1, x2, y2, pageable);
    }

    @Override
    public List<StoreResponseDTO.NearestStoreDTO> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable, Long customerId) {
        //근처 매장 찾기
        Page<Store> storePage = findNearestStore(longitude, latitude, distance, Optional.empty(), pageable);
        if (storePage.isEmpty()) return Collections.emptyList();

        List<StoreResponseDTO.NearestStoreDTO> resultList = new ArrayList<>();
        List<Store> storeList = storePage.getContent();
        storeList.forEach(item -> {
            List<StoreImage> storeImages = storeImageRepository.findAllByStore(item).orElse(Collections.emptyList());
            List<CustomerStore> customerStoreList = customerStoreRepository.findByCustomerIdAndStoreId(customerId, item.getId());

            resultList.add(StoreResponseDTO.NearestStoreDTO.builder()
                    .storeId(item.getId())
                    .storeImage(storeImages.isEmpty() ?  null : storeImages.get(0).getStoreImage())
                    .location(item.getStoreLocation())
                    .couponCnt(customerStoreList.isEmpty() ?  0 : customerStoreList.get(0).getStampTotal())
                    .name(item.getName())
                    .build());
        });

        return resultList;
    }

    @Override
    public StoreResponseDTO.MoreDetailStoreDTO findStoreInfoDetail(Long storeId, Long customerId) {

        Store store = storeRepository.findByIdWithOwner(storeId).orElseThrow();
        List<StoreImage> storeImages = storeImageRepository.findAllByStore(store).orElse(null);
        List<Menu> menus = menuRepository.findByStore(store).orElse(null);
        List<OwnerCoupon> ownerCoupons = ownerCouponRepository.findByOwnerCouponId(store.getOwner().getId()).orElse(Collections.emptyList());
        List<CustomerStore> customerStoreList = customerStoreRepository.findByCustomerIdAndStoreId(customerId, storeId);

        //쿠폰에 대한 준비가 부족하면
        if (ownerCoupons.isEmpty() || ownerCoupons.get(0).isAvailable() == false)
            throw new StoreHandler(ErrorStatus.STORE_NOT_ACCEPTABLE);

        StoreResponseDTO.MoreDetailStoreDTO result = StoreResponseDTO.MoreDetailStoreDTO.builder()
                .name(store.getName())
                .location(store.getStoreLocation())
                .description(store.getStoreDescription())
                .longitude(String.valueOf(store.getPoint().getX()))
                .latitude(String.valueOf(store.getPoint().getY()))
                .coupon(StoreResponseDTO.Coupon.builder()
                        .title(ownerCoupons.get(0).getStore_name())
                        .cnt(customerStoreList.get(0).getStampCurrent())
                        .color(ownerCoupons.get(0).getColor())
                        .build())
                .images(new ArrayList<>())
                .menus(new ArrayList<>())
                .build();

        if(!storeImages.isEmpty()){
            storeImages.forEach(item -> result.getImages().add(item.getStoreImage()));
        }

        if(!menus.isEmpty()){
            menus.forEach(item -> result.getMenus().add(StoreResponseDTO.MenuInfo.builder()
                    .name(item.getName())
                    .description(item.getMenuDescription())
                    .image(item.getMenuImage())
                    .isNew(item.getIsNew())
                    .build()));
        }

        return result;
    }
}
