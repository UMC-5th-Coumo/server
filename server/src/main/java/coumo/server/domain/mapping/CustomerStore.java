package coumo.server.domain.mapping;

import coumo.server.domain.Customer;
import coumo.server.domain.Store;
import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.StampMax;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerStore extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer; //고객 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; //매장 아이디

    private Integer stampTotal; //고객 총 스탬프 개수

    private Integer stampCurrent; //고객의 현재 스탬프 개수

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(8)")
    private StampMax stampMax; //최대 스탬프 개수
}
