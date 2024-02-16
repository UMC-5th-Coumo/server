package coumo.server.service.design;

import coumo.server.converter.DesignReceiptConverter;
import coumo.server.domain.DesignReceipt;
import coumo.server.domain.Owner;
import coumo.server.repository.DesignReceiptRepository;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignReceiptServiceImpl implements DesignReceiptService {

    private final DesignReceiptRepository designReceiptRepository;

    @Override
    public DesignReceipt createDesignReceipt(DesignReceipt designReceipt) {
        return designReceiptRepository.save(designReceipt);
    }
}
