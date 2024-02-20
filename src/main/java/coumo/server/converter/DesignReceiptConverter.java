package coumo.server.converter;

import coumo.server.domain.DesignReceipt;
import coumo.server.domain.Owner;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import coumo.server.web.dto.DesignReceiptResponseDTO;
import coumo.server.web.dto.DesignReceiptServiceResponseDTO;

public class DesignReceiptConverter {
    public static DesignReceipt fromDTO(DesignReceiptRequestDTO dto, Owner owner) {
        return DesignReceipt.builder()
                .owner(owner)
                .storeName(dto.getStoreName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .storeType(dto.getStoreType())
                .couponTitle("")
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

    public static DesignReceiptServiceResponseDTO toServiceResponseDTO(DesignReceipt receipt) {
        return DesignReceiptServiceResponseDTO.builder()
                .receiptId(receipt.getId())
                .storeName(receipt.getStoreName())
                .storeType(receipt.getStoreType())
                .couponDescription(receipt.getCouponDescription())
                .receiptState(receipt.getReceiptState())
                .createdAt(receipt.getCreatedAt())
                .build();
    }
}
