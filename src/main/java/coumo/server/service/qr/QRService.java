package coumo.server.service.qr;

import com.google.zxing.WriterException;
import coumo.server.web.dto.QRRequestDTO;
import org.springframework.http.ResponseEntity;

public interface QRService {
    ResponseEntity<byte[]> createQR(Integer customerId, Integer storeId) throws WriterException;

    Integer stampToCoupon(QRRequestDTO dto);

    Integer stampFromCoupon(QRRequestDTO dto);
}
