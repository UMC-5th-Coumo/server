package coumo.server.sms;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class VerificationCodeStorage {
    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    public void saveCode(String phone, String code) {
        verificationCodes.put(phone, code);
    }

    public boolean verifyCode(String phone, String code) {
        String savedCode = verificationCodes.get(phone);
        return savedCode != null && savedCode.equals(code);
    }
}
