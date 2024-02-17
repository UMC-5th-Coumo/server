package coumo.server.web.dto;

import coumo.server.domain.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DesignReceiptResponseDTO {
    private Long receiptId;
    private String storeName;
    private String phone;
    private String email;
    private StoreType storeType;
    private String couponDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
