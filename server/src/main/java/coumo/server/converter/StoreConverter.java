package coumo.server.converter;

import coumo.server.domain.Store;
import coumo.server.web.dto.StoreRequestDTO;
import coumo.server.web.dto.StoreResponseDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class StoreConverter {
    public static StoreResponseDTO.resultBasicDTO toResultBasicDTO(Store store){
        StoreResponseDTO.resultBasicDTO resultBasicDTO = StoreResponseDTO.resultBasicDTO.builder()
                                                            .name(store.getName())
                                                            .telePhone(store.getTelephone())
                                                            .longitude(String.valueOf(store.getPoint().getX()))
                                                            .latitude(String.valueOf(store.getPoint().getY()))
                                                            .time(new ArrayList<>())
                                                            .location(store.getStoreLocation())
                                                            .category(store.getStoreType().toString())
                                                            .build();
        store.getTimetableList().stream()
                .map(item -> resultBasicDTO.getTime()
                        .add(new StoreResponseDTO.time(item.getDay(), item.getStartTime(), item.getEndTime())))
                .collect(Collectors.toList());

        return resultBasicDTO;
    }

    public static StoreResponseDTO.resultDetailDTO toResultDetailDTO(Store store){
        StoreResponseDTO.resultDetailDTO resultDetailDTO = StoreResponseDTO.resultDetailDTO.builder()
                                                                .storeImages(new ArrayList<>())
                                                                .menus(new ArrayList<>())
                                                                .description(store.getStoreDescription())
                                                                .build();
        store.getStoreImageList().stream().
                map(item -> resultDetailDTO.getStoreImages().add(item.getStoreImage()))
                .collect(Collectors.toList());

        store.getMenuList().stream().
                map(item -> resultDetailDTO.getMenus()
                        .add(new StoreResponseDTO.menu(item.getName(), item.getMenuImage(), item.getMenuDescription())))
                .collect(Collectors.toList());
        return resultDetailDTO;
    }
}
