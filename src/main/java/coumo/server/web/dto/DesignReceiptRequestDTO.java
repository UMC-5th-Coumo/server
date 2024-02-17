package coumo.server.web.dto;

import coumo.server.domain.enums.StoreType;
import lombok.Getter;

@Getter
public class DesignReceiptRequestDTO {
    String storeName;
    String phone;
    String email;
    StoreType storeType;
    String couponDescription;
}
