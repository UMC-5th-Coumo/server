package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; //매장 ID

    @Column(nullable = false, length = 256)
    private String name; //메뉴 이름

    @Column(nullable = false, length = 64)
    private String menuImage; //메뉴 이미지

    @Column(nullable = false, length = 2048)
    private String menuDescription; //메뉴 설명

    @Column(nullable = false)
    private Boolean isNew; //신메뉴 여부

    public void setStore(Store store) {
        this.store = store;
    }
}
