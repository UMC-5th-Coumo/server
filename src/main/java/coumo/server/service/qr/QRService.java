package coumo.server.service.qr;

import com.google.zxing.WriterException;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.QRRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface QRService {
    ResponseEntity<byte[]> createQR(Integer customerId, Integer storeId) throws WriterException;

    Optional<CustomerStore> stampToCoupon(QRRequestDTO dto);

    Optional<CustomerStore> stampFromCoupon(QRRequestDTO dto);
}
