package coumo.server.domain;

import coumo.server.domain.common.BaseEntity;
import coumo.server.domain.enums.StoreType;
import coumo.server.domain.mapping.CustomerStore;
import coumo.server.web.dto.StoreRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
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

    @Column(columnDefinition = "POINT SRID 4326")
    private Point point;

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

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Timetable> timetableList = new ArrayList<>();

    public static Point createPoint(double latitude, double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public void updateStore(String name, String telephone, String storeLocation, String category,
                            String longitude,  String latitude){
        this.name = name;
        this.telephone = telephone;
        this.storeLocation = storeLocation;
        this.point = createPoint(Double.valueOf(latitude), Double.valueOf(longitude));
        this.storeType = StoreType.fromString(category);
    }

    //======== 연관 관계 편의 메서드 ========
    public void setOwner(Owner owner) {
        this.owner = owner;
        owner.setStore(this);
    }

    public void addMenu(Menu menu){
        menuList.add(menu);
        menu.setStore(this);
    }

    public void addStoreImage(StoreImage storeImage){
        storeImageList.add(storeImage);
        storeImage.setStore(this);
    }

    public void addTimeTable(Timetable timetable){
        timetableList.add(timetable);
        timetable.setStore(this);
    }

    //======== 생성 메서드 ========
    public static Store createStore(Owner owner){
        Point point = createPoint(0f, 0f);
        Store store = Store.builder()
                        .name("")
                        .telephone("")
                        .storeLocation("")
                        .storeDescription("")
                        .businessNumber("")
                        .storeType(StoreType.NONE)
                        .menuList(new ArrayList<>())
                        .noticeList(new ArrayList<>())
                        .storeImageList(new ArrayList<>())
                        .designReceiptList(new ArrayList<>())
                        .customerStoreList(new ArrayList<>())
                        .timetableList(new ArrayList<>())
                        .point(point)
                        .build();
        store.setOwner(owner);
        return store;
    }
}
