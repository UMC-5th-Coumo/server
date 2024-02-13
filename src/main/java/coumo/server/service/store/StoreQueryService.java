package coumo.server.service.store;

import coumo.server.domain.*;
import coumo.server.domain.enums.StoreType;
import coumo.server.web.dto.StoreResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreQueryService {
    public Optional<Store> findStore(Long storeId);
    public Optional<List<Timetable>> findTimeTables(Long storeId);
    public Optional<List<Menu>> findMenus(Long storeId);
    public Optional<List<StoreImage>> findStoreImages(Long storeId);
    public List<StoreResponseDTO.StoreStampInfo> findFamousStore(double latitude, double longitude, double distance, Pageable pageable);
    public Page<Store> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable);
    public List<StoreResponseDTO.NearestStoreDTO> findNearestStore(double latitude, double longitude, double distance, Optional<String> category, Pageable pageable, Long customerId);
    public StoreResponseDTO.MoreDetailStoreDTO findStoreInfoDetail(Long storeId, Long customerId);
    public Boolean isWriteStore(Owner owner);
}
