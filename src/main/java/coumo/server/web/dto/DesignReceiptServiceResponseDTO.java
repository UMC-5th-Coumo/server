package coumo.server.web.dto;

import coumo.server.domain.enums.ReceiptState;
import coumo.server.domain.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignReceiptServiceResponseDTO {
    private Long receiptId;
    private String storeName;
    private StoreType storeType;
    private String couponDescription;
    private ReceiptState receiptState;
    private LocalDateTime createdAt;
}
