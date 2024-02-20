package coumo.server.service.design;

import coumo.server.domain.DesignReceipt;
import coumo.server.domain.enums.ReceiptState;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import coumo.server.web.dto.DesignReceiptResponseDTO;
import coumo.server.web.dto.DesignReceiptServiceResponseDTO;

import java.util.List;

public interface DesignReceiptService {
    DesignReceipt createDesignReceipt(DesignReceipt designReceipt);
    List<DesignReceiptServiceResponseDTO> findAllDesignReceiptsByOwnerId(Long ownerId);

    void changeDesignReceiptState(Long receiptId, ReceiptState newState);
}
