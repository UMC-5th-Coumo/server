package coumo.server.converter;

import coumo.server.apiPayload.exception.handler.StoreHandler;
import coumo.server.domain.Store;
import coumo.server.web.dto.StoreResponseDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StoreConverter {
    public static StoreResponseDTO.StoreBasicDTO toResultBasicDTO(Store store){
        StoreResponseDTO.StoreBasicDTO storeBasicDTO = StoreResponseDTO.StoreBasicDTO.builder()
                                                            .name(store.getName())
                                                            .telePhone(store.getTelephone())
                                                            .longitude(String.valueOf(store.getPoint().getX()))
                                                            .latitude(String.valueOf(store.getPoint().getY()))
                                                            .time(new ArrayList<>())
                                                            .location(store.getStoreLocation())
                                                            .category(store.getStoreType().toString())
                                                            .build();
        store.getTimetableList().stream()
                .map(item -> storeBasicDTO.getTime()
                        .add(new StoreResponseDTO.TimeInfo(item.getDay(), item.getStartTime(), item.getEndTime())))
                .collect(Collectors.toList());

        return storeBasicDTO;
    }

    public static StoreResponseDTO.StoreDetailDTO toResultDetailDTO(Store store){
        StoreResponseDTO.StoreDetailDTO storeDetailDTO = StoreResponseDTO.StoreDetailDTO.builder()
                                                                .storeImages(new ArrayList<>())
                                                                .menus(new ArrayList<>())
                                                                .description(store.getStoreDescription())
                                                                .build();
        store.getStoreImageList().stream().
                map(item -> storeDetailDTO.getStoreImages().add(item.getStoreImage()))
                .collect(Collectors.toList());

        store.getMenuList().stream().
                map(item -> storeDetailDTO.getMenus()
                        .add(new StoreResponseDTO.MenuInfo(item.getName(), item.getMenuImage(), item.getMenuDescription(), item.getIsNew())))
                .collect(Collectors.toList());
        return storeDetailDTO;
    }

    public static List<StoreResponseDTO.FamousStoreDTO> toFamousStoreDTO(List<StoreResponseDTO.StoreStampInfo> storeStampInfoList){
        if(storeStampInfoList.isEmpty()) return Collections.emptyList();

        //이미지 개수 예외처리 시급함
        List<StoreResponseDTO.FamousStoreDTO> resultList = new ArrayList<>();
        storeStampInfoList.stream()
                .map(item -> resultList.add(StoreResponseDTO.FamousStoreDTO.builder()
                        .storeId(item.getStore().getId())
                        .name(item.getStore().getName())
                        .description(item.getStore().getStoreDescription())
                        .location(item.getStore().getStoreLocation())
                        .storeImage(item.getStore().getStoreImageList().get(0).getStoreImage())
                        .build()))
                .collect(Collectors.toList());

        return resultList;
    }

    public static List<StoreResponseDTO.NearestStoreDTO> toNearestStoreDTO(Page<Store> storePage, Long customerId){
        if(storePage.isEmpty()) return Collections.emptyList();

        List<StoreResponseDTO.NearestStoreDTO> resultList = new ArrayList<>();
        List<Store> storeList = storePage.getContent();
        //이미지 개수 예외처리 시급함
        storeList.stream()
                .map(item -> resultList.add(StoreResponseDTO.NearestStoreDTO.builder()
                                .storeId(item.getId())
                                .storeImage(item.getStoreImageList().get(0).getStoreImage())
                                .location(item.getStoreLocation())
                                .couponCnt(item.getCustomerCouponLength(customerId))
                                .name(item.getName())
                        .build()))
                .collect(Collectors.toList());

        return resultList;
    }

    public static StoreResponseDTO.MoreDetailStoreDTO toMoreDetailStoreDTO(Store store, Long customerId){
        StoreResponseDTO.MoreDetailStoreDTO result = StoreResponseDTO.MoreDetailStoreDTO.builder()
                .name(store.getName())
                .location(store.getStoreLocation())
                .description(store.getStoreDescription())
                .longitude(String.valueOf(store.getPoint().getX()))
                .latitude(String.valueOf(store.getPoint().getY()))
                .coupon(StoreResponseDTO.Coupon.builder()
                        .title(store.getOwner().getOwnerCouponList().get(0).getStore_name())
                        .cnt(store.getCustomerCouponLength(customerId))
                        .color(store.getOwner().getOwnerCouponList().get(0).getColor())
                        .build())
                .images(new ArrayList<>())
                .menus(new ArrayList<>())
                .build();

        store.getStoreImageList().stream()
                .map(item->result.getImages().add(item.getStoreImage()))
                .collect(Collectors.toList());

        store.getMenuList().stream()
                .map(item->result.getMenus().add(StoreResponseDTO.MenuInfo.builder()
                                .name(item.getName())
                                .description(item.getMenuDescription())
                                .image(item.getMenuImage())
                                .isNew(item.getIsNew())
                        .build()))
                .collect(Collectors.toList());

        return result;
    }
}
