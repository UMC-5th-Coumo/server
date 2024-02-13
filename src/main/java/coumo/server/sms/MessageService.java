package coumo.server.sms;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class MessageService {

    private final DefaultMessageService messageService;
    private final VerificationCodeStorage verificationCodeStorage;

    @Value("${coolsms.from.number}")
    private String fromNumber;

    @Autowired
    public MessageService(@Value("${coolsms.api.key}") String apiKey,
                          @Value("${coolsms.api.secret}") String apiSecret,
                          VerificationCodeStorage verificationCodeStorage) {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
        this.verificationCodeStorage = verificationCodeStorage;
    }

    public void sendMessage(String toNumber) {
        String verificationCode = generateRandomNumber();
        Message message = new Message();
        message.setFrom(fromNumber); // 발신번호 설정
        message.setTo(toNumber); // 수신번호 설정
        message.setText("[Coumo] 아래의 인증번호를 입력하세요'\n" + verificationCode); // 메시지 내용 설정

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println("Message sent successfully: " + response.getMessageId());
            //인증번호 저장
            verificationCodeStorage.saveCode(toNumber, verificationCode);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    private String generateRandomNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number); // 6자리 인증번호 생성
    }
}