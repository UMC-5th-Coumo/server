package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.ReceiptState;
import coumo.server.domain.enums.StoreType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/**
 * 쿠폰 디자인 의뢰 영수증
 * 매장 종류 : StoreType
 */
public class DesignReceipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner; //사장님 고유 ID

    @Column(nullable = true, length = 50)
    private String couponTitle; //쿠폰 이름

    @Column(nullable = false, length = 50)
    private String storeName; //매장명

    @Column(nullable = false, length = 16)
    private String phone; //휴대폰

    @Column(nullable = false, length = 64)
    private String email; //이메일

    @Column(nullable = false, length = 2048)
    private String couponDescription; //쿠폰 설명

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(16)")
    private StoreType storeType; //매장 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; //매장 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(16) default 'APPLIED'")
    @Builder.Default
    private ReceiptState receiptState = ReceiptState.APPLIED;

    public void changeReceiptState(ReceiptState newState) {
        this.receiptState = newState;
    }
}
