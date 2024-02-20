package coumo.server.web.dto;

import coumo.server.domain.enums.ReceiptState;
import lombok.Getter;

@Getter
public class DesignReceiptStateChangeRequestDTO {
    ReceiptState state;
}
