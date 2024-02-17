package coumo.server.converter;

import coumo.server.domain.DesignReceipt;
import coumo.server.domain.Owner;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import coumo.server.web.dto.DesignReceiptResponseDTO;

public class DesignReceiptConverter {
    public static DesignReceipt fromDTO(DesignReceiptRequestDTO dto, Owner owner) {
        return DesignReceipt.builder()
                .owner(owner)
                .storeName(dto.getStoreName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .storeType(dto.getStoreType())
                .couponDescription(dto.getCouponDescription())
                .build();
    }

    public static DesignReceiptResponseDTO toResponseDTO(DesignReceipt designReceipt){
        return new DesignReceiptResponseDTO(
                designReceipt.getId(),
                designReceipt.getStoreName(),
                designReceipt.getPhone(),
                designReceipt.getEmail(),
                designReceipt.getStoreType(),
                designReceipt.getCouponDescription(),
                designReceipt.getCreatedAt(),
                designReceipt.getUpdatedAt()
        );
    }
}
