package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/**
 * 쿠모 구독 영수증
 * 시작 날짜, 종료 날짜 기입 필요
 */
public class SubscriptionReceipt extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner; //사장님 아이디

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(16)")
    private SubscriptionType subscriptionType;
}
