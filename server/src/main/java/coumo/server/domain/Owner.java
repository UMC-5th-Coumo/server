package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.Gender;
import coumo.server.domain.enums.State;
import coumo.server.domain.enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/**
 * 사장 테이블
 * 구독 종류: SubscriptionType
 * 계정 상태: State
 * 성별: Gender
 */
public class Owner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 16)
    private String birthday;

    @Column(nullable = false, length = 32)
    private String nickname;

    @Column(nullable = false, length = 16)
    private String phone;

    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false, length = 32)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(8)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(16)")
    private SubscriptionType subscriptionType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(32)")
    private State state;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Store> storeList = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<OwnerCoupon> ownerCouponList = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<SubscriptionReceipt> subscriptionReceiptList = new ArrayList<>();
}
