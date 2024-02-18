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
    private final OwnerService ownerService;

    public String getCurrentOwnerLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication != null) && (authentication.getPrincipal() instanceof UserDetails)) {
            return authentication.getName();
        }
        throw new IllegalStateException("User not logged in");
    }

    public Boolean isAvailableStoreId(Long storeId) {
        String currentOwnerLoginId = getCurrentOwnerLoginId();

        Store store = ownerService.findStore(currentOwnerLoginId);

        if (store == null) {
            log.info("FAILED: Token - not find store of owner");
            return false;
        }

        log.info("Token: store.getId().equals(storeId) {}", store.getId().equals(storeId));
        return store.getId().equals(storeId);
    }
}
