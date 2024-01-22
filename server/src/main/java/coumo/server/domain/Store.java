package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.StoreType;
import coumo.server.domain.mapping.CustomerStore;
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
 * 매장 테이블
 * 매장 종류: StoreType
 */
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner; //사장님 아이디

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 50)
    private String telephone; //매장 전화 번호

    @Column(nullable = false, length = 256)
    private String storeLocation; //매장 위치

    @Column(nullable = false, length = 2048)
    private String storeDescription; //매장 설명

    @Column(nullable = false, length = 64)
    private String businessNumber; //사업자 번호

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(16)")
    private StoreType storeType; //매장 종류

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Notice> noticeList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreImage> storeImageList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<DesignReceipt> designReceiptList = new ArrayList<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<CustomerStore> customerStoreList = new ArrayList<>();

}
