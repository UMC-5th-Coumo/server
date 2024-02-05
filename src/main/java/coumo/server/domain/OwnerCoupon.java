package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.StampMax;
import coumo.server.domain.mapping.CustomerStore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
/**
 * 사장님이 등록한 쿠폰
 * 최대 스탬프 개수: StampMax
 */
public class OwnerCoupon extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner; //사장님 아이디

    @Column(nullable = false, length = 50)
    private String color; //색깔

    @Column(nullable = false, length = 64)
    private String store_name; //매장 이름

    @Column(nullable = false, length = 256)
    private String stampImage; //스탬프 이미지

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(8)")
    private StampMax stampMax; //최대 스탬프 개수

    //======== 비즈니스 로직 메서드 ========
    public boolean isAvailable(){
        if (color.isEmpty() || color.equals("") || store_name.isEmpty()
                || store_name.equals("") || stampImage.isEmpty() || stampImage.equals(""))
            return false;
        return true;
    }
}
