package coumo.server.service.design;

import coumo.server.converter.DesignReceiptConverter;
import coumo.server.domain.DesignReceipt;
import coumo.server.domain.Owner;
import coumo.server.domain.enums.ReceiptState;
import coumo.server.repository.DesignReceiptRepository;
import coumo.server.web.dto.DesignReceiptRequestDTO;
import coumo.server.web.dto.DesignReceiptResponseDTO;
import coumo.server.web.dto.DesignReceiptServiceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignReceiptServiceImpl implements DesignReceiptService {

    private final DesignReceiptRepository designReceiptRepository;

    @Override
    public DesignReceipt createDesignReceipt(DesignReceipt designReceipt) {
        return designReceiptRepository.save(designReceipt);
    }

    @Override
    public List<DesignReceiptServiceResponseDTO> findAllDesignReceiptsByOwnerId(Long ownerId) {
        List<DesignReceipt> receipts = designReceiptRepository.findAllByOwnerId(ownerId);
        return receipts.stream()
                .map(DesignReceiptConverter::toServiceResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void changeDesignReceiptState(Long receiptId, ReceiptState newState) {
        DesignReceipt receipt = designReceiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("해당 receiptId를 찾을 수 없습니다."));
        receipt.changeReceiptState(newState);
        designReceiptRepository.save(receipt);
    }
}
