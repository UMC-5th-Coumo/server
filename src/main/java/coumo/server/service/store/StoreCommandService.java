package coumo.server.service.store;

import coumo.server.domain.Store;
import coumo.server.web.dto.StoreRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface StoreCommandService {
    public Store createStore(Long ownerId);
    public void updateStore(Long storeId, StoreRequestDTO.UpdateBasicDTO updateBasicDTO);
    public void updateStore(Long storeId, String description, MultipartFile[] storeImages, MultipartFile[] menuImages, StoreRequestDTO.MenuDetail[] menuDetail);
}
