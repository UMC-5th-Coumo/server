package coumo.server.service.design;

import coumo.server.domain.DesignReceipt;
import coumo.server.web.dto.DesignReceiptRequestDTO;

public interface DesignReceiptService {
    DesignReceipt createDesignReceipt(DesignReceipt designReceipt);
}
