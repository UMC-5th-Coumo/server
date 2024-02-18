package coumo.server.util;

import coumo.server.domain.Store;
import coumo.server.service.store.StoreQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import coumo.server.service.owner.OwnerService;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenCheck {
    private final StoreQueryService storeQueryService;
    private static OwnerService ownerService;

    public static void setOwnerService(OwnerService ownerService) {
        TokenCheck.ownerService = ownerService;
    }

    //현재 로그인된 사용자의 userId 반환
    public static String getCurrentOwnerLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
            return authentication.getName();
        }
        return null; // 인증된 사용자가 없을 경우 null 반환
    }


    public static Boolean isAvailable(Long storeId) {
        String currentOwnerLoginId = getCurrentOwnerLoginId();

        //검증
        if(currentOwnerLoginId == null) {
            log.info("FAILED: Token - not find owner");
            return false;
        }

        //유효한 이이디인데
        Store store = ownerService.findStore(currentOwnerLoginId);

        //토큰에서 찾은 아이디로 검색 실패
        if (store == null) {
            log.info("FAILED: Token - not find store of owner");
            return false;
        }

        //비교
        log.info("Token: store.getId() == storeId {}", store.getId() == storeId);
        return store.getId() == storeId;
    }
}
