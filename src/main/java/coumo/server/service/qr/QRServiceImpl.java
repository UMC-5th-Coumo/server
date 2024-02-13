package coumo.server.service.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.domain.Store;
import coumo.server.domain.enums.StampMax;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.repository.*;
import coumo.server.web.dto.QRRequestDTO;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class QRServiceImpl implements QRService{

    private final CustomerStoreRepository customerStoreRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;
    private final OwnerCouponRepository ownerCouponRepository;

    @Override
    public ResponseEntity<byte[]> createQR(Integer customerId, Integer storeId) throws WriterException {
        int width = 200;
        int height = 200;

        // JSON 객체 생성
        JSONObject json = new JSONObject();
        json.put("customerId", customerId);
        json.put("storeId", storeId);

        // JSON 문자열을 QR 코드로 인코딩
        String data = json.toString();
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix encode = writer.encode(data, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(encode, "PNG", out); // QR Code를 이미지로 변환 후 스트림에 쓰기

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(out.toByteArray());
        } catch (Exception e) {
            System.out.println("QR Code 생성 중 예외 발생: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<CustomerStore> stampToCoupon(QRRequestDTO dto) {

        Optional<CustomerStore> customerStore = customerStoreRepository.findByCustomerIdAndStoreId(dto.getCustomerId(), dto.getStoreId());
        Integer stampCurrent = 0;
        Integer stampTotal = 0;
        if(customerStore.isPresent()){
            stampCurrent = customerStore.get().getStampCurrent();
            stampTotal = customerStore.get().getStampTotal();
        }

        Customer customer = customerRepository.findById(dto.getCustomerId()).get();
        Store store = storeRepository.findById(dto.getStoreId()).get();
        Owner owner = ownerRepository.findByStoreId(store.getId()).get();
        StampMax stampMax = (ownerCouponRepository.findByOwnerId(owner.getId())).get().getStampMax();

        CustomerStore updateCustomerStore = CustomerStore.builder()
                .customer(customer)
                .store(store)
                .stampMax(stampMax)
                .stampCurrent(stampCurrent + dto.getStampCnt())
                .stampTotal(stampTotal + dto.getStampCnt())
                .build();

        CustomerStore newCustomerStore = customerStoreRepository.save(updateCustomerStore);

        return Optional.of(newCustomerStore);
    }

    @Override
    public Optional<CustomerStore> stampFromCoupon(QRRequestDTO dto) {

        Optional<CustomerStore> customerStore = customerStoreRepository.findByCustomerIdAndStoreId(dto.getCustomerId(), dto.getStoreId());
        Integer stampCurrent = customerStore.get().getStampCurrent();
        Integer stampTotal = customerStore.get().getStampTotal();
        if(customerStore.isEmpty()){
            return null;
        }

        Customer customer = customerRepository.findById(dto.getCustomerId()).get();
        Store store = storeRepository.findById(dto.getStoreId()).get();
        Owner owner = ownerRepository.findByStoreId(store.getId()).get();
        StampMax stampMax = (ownerCouponRepository.findByOwnerId(owner.getId())).get().getStampMax();

        CustomerStore updateCustomerStore = CustomerStore.builder()
                .customer(customer)
                .store(store)
                .stampMax(stampMax)
                .stampCurrent(stampCurrent - dto.getStampCnt())
                .stampTotal(stampTotal + dto.getStampCnt())
                .build();

        CustomerStore newCustomerStore = customerStoreRepository.save(updateCustomerStore);

        return Optional.of(newCustomerStore);
    }
}
