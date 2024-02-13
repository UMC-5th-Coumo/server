package coumo.server.service.store;

import coumo.server.apiPayload.code.status.ErrorStatus;
import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.aws.s3.AmazonS3Manager;
import coumo.server.aws.s3.Filepath;
import coumo.server.domain.*;
import coumo.server.repository.*;
import coumo.server.web.dto.StoreRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreCommandServiceImpl implements StoreCommandService{
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    private final TimetableRepository timetableRepository;
    private final MenuRepository menuRepository;
    private final StoreImageRepository storeImageRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    @Transactional
    public Store createStore(Long ownerId) {
        //엔티티 조회
        Owner owner = ownerRepository.findById(ownerId).orElseThrow();

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

        //삭제
        timetableRepository.deleteAllByStore(store);
        store.getTimetableList().clear();

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
    public void updateStore(Long storeId, String description, MultipartFile[] storeImages, MultipartFile[] menuImages, StoreRequestDTO.MenuDetail[] menuDetail) {
        if(menuImages.length != menuDetail.length) throw new StoreHandler(ErrorStatus.STORE_MENU_COUNT_BAD_REQUEST);

        //엔티티 조회
        Store store = storeRepository.findById(storeId).orElseThrow();

        //삭제
        storeImageRepository.deleteAllByStore(store);
        menuRepository.deleteAllByStore(store);
        store.getStoreImageList().clear();
        store.getMenuList().clear();

        //엔티티 업데이트
        for(MultipartFile image : storeImages){
            String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.makeKeyName(Filepath.STORE), image);
            StoreImage storeImage = StoreImage.builder().storeImage(imageUrl).store(store).build();
            store.addStoreImage(storeImage);
        }

        for (int i = 0; i < menuImages.length; i++) {
            String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.makeKeyName(Filepath.MENU), menuImages[i]);
            log.info("imageUrl={}", imageUrl);
            Menu menu = Menu.builder()
                    .menuImage(imageUrl)
                    .menuDescription(menuDetail[i].getDescription())
                    .name(menuDetail[i].getName())
                    .isNew(menuDetail[i].getIsNew())
                    .store(store)
                    .build();

            store.addMenu(menu);
        }
    }
}
