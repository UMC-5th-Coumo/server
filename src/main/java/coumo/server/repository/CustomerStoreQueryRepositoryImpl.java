package coumo.server.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import coumo.server.domain.QOwnerCoupon;
import coumo.server.domain.QStore;
import coumo.server.domain.mapping.QCustomerStore;
import coumo.server.web.dto.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerStoreQueryRepositoryImpl implements CustomerStoreQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsMost(Long customerId) {
        QCustomerStore qCustomerStore = QCustomerStore.customerStore;
        QOwnerCoupon qOwnerCoupon = QOwnerCoupon.ownerCoupon;
        QStore qStore = QStore.store;

        List<CouponResponseDTO.CustomerStoreCouponDTO> results = queryFactory
                .select(Projections.constructor(
                        CouponResponseDTO.CustomerStoreCouponDTO.class,
                        Projections.fields(CouponResponseDTO.CustomerStoreStampDTO.class,
                                qCustomerStore.stampCurrent.as("stampCurrent"),
                                qCustomerStore.stampMax.as("stampMax"),
                                qCustomerStore.updatedAt.as("updatedAt")),
                        Projections.fields(CouponResponseDTO.StoreCouponDTO.class,
                                qStore.id.as("storeId"),
                                qStore.name.as("storeName"),
                                qOwnerCoupon.couponColor.as("couponColor"),
                                qOwnerCoupon.fontColor.as("fontColor"),
                                qOwnerCoupon.stampImage.as("stampImage")
                        )
                ))
                .from(qCustomerStore)
                .leftJoin(qCustomerStore.store, qStore)
                .leftJoin(qOwnerCoupon).on(qOwnerCoupon.owner.eq(qStore.owner))     //qOwnerCoupon.owner가 root entity가 아니므로 조인을 명확히 정의해야함.
                .where(qCustomerStore.customer.id.eq(customerId))
                .orderBy(qCustomerStore.stampCurrent.desc())
                .fetch();

        return results;
    }

    @Override
    public List<CouponResponseDTO.CustomerStoreCouponDTO> findCustomerStoreCouponsLatest(Long customerId) {

        QCustomerStore qCustomerStore = QCustomerStore.customerStore;
        QOwnerCoupon qOwnerCoupon = QOwnerCoupon.ownerCoupon;
        QStore qStore = QStore.store;

        List<CouponResponseDTO.CustomerStoreCouponDTO> results = queryFactory
                .select(Projections.constructor(
                        CouponResponseDTO.CustomerStoreCouponDTO.class,
                        Projections.fields(CouponResponseDTO.CustomerStoreStampDTO.class,
                                qCustomerStore.stampCurrent.as("stampCurrent"),
                                qCustomerStore.stampMax.as("stampMax"),
                                qCustomerStore.updatedAt.as("updatedAt")),
                        Projections.fields(CouponResponseDTO.StoreCouponDTO.class,
                                qStore.id.as("storeId"),
                                qStore.name.as("storeName"),
                                qOwnerCoupon.couponColor.as("couponColor"),
                                qOwnerCoupon.fontColor.as("fontColor"),
                                qOwnerCoupon.stampImage.as("stampImage")
                        )
                ))
                .from(qCustomerStore)
                .leftJoin(qCustomerStore.store, qStore)
                .leftJoin(qOwnerCoupon).on(qOwnerCoupon.owner.eq(qStore.owner))
                .where(qCustomerStore.customer.id.eq(customerId))
                .orderBy(qCustomerStore.updatedAt.desc())
                .fetch();

        return results;
    }
}
