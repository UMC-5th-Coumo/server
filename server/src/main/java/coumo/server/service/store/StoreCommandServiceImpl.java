package coumo.server.service.store;

import coumo.server.domain.*;
import coumo.server.repository.StoreRepository;
import coumo.server.web.dto.StoreRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreCommandServiceImpl implements StoreCommandService{
    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Store createStore(Long ownerId) {
        //엔티티 조회
        Owner owner = Owner.builder().build(); //<수정 필요>

        //가게 생성
        Store store = Store.createStore(owner);

        //가게 저장
        storeRepository.save(store);

        return store;
    }

    @Override
    @Transactional
    public void updateStore(Long storeId, StoreRequestDTO.UpdateBasicDTO updateBasicDTO) {
        //엔티티 조회
        Store store = storeRepository.findById(storeId).orElseThrow();

        //엔티티 업데이트
        for (StoreRequestDTO.TimeInfo item: updateBasicDTO.getTime()) {
            store.addTimeTable(Timetable.builder()
                                .day(item.getDay()).startTime(item.getStartTime()).endTime(item.getEndTime())
                                .build());
        }

        store.updateStore(updateBasicDTO.getName(), updateBasicDTO.getTelePhone(), updateBasicDTO.getLocation(),
                updateBasicDTO.getCategory(), updateBasicDTO.getLongitude(), updateBasicDTO.getLatitude());
    }

    @Override
    @Transactional
    public void updateStore(Long storeId, String description, String[] storeImages, String[] menuImages, StoreRequestDTO.MenuDetail[] menuDetail) {
        //엔티티 조회
        Store store = storeRepository.findById(storeId).orElseThrow();

        //엔티티 업데이트
        for(String image : storeImages){
            StoreImage storeImage = StoreImage.builder().storeImage(image).store(store).build();
            store.addStoreImage(storeImage);
        }

        for (int i = 0; i < menuImages.length; i++) {
            Menu menu = Menu.builder()
                    .menuImage(menuImages[i])
                    .menuDescription(menuDetail[i].getDescription())
                    .name(menuDetail[i].getName())
                    .store(store)
                    .build();

            store.addMenu(menu);
        }
    }
}
