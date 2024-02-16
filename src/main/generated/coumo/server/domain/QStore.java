package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -846759329L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    public final StringPath businessNumber = createString("businessNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<coumo.server.domain.mapping.CustomerStore, coumo.server.domain.mapping.QCustomerStore> customerStoreList = this.<coumo.server.domain.mapping.CustomerStore, coumo.server.domain.mapping.QCustomerStore>createList("customerStoreList", coumo.server.domain.mapping.CustomerStore.class, coumo.server.domain.mapping.QCustomerStore.class, PathInits.DIRECT2);

    public final ListPath<DesignReceipt, QDesignReceipt> designReceiptList = this.<DesignReceipt, QDesignReceipt>createList("designReceiptList", DesignReceipt.class, QDesignReceipt.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Menu, QMenu> menuList = this.<Menu, QMenu>createList("menuList", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final ListPath<Notice, QNotice> noticeList = this.<Notice, QNotice>createList("noticeList", Notice.class, QNotice.class, PathInits.DIRECT2);

    public final QOwner owner;

    public final ComparablePath<org.locationtech.jts.geom.Point> point = createComparable("point", org.locationtech.jts.geom.Point.class);

    public final StringPath storeDescription = createString("storeDescription");

    public final ListPath<StoreImage, QStoreImage> storeImageList = this.<StoreImage, QStoreImage>createList("storeImageList", StoreImage.class, QStoreImage.class, PathInits.DIRECT2);

    public final StringPath storeLocation = createString("storeLocation");

    public final EnumPath<coumo.server.domain.enums.StoreType> storeType = createEnum("storeType", coumo.server.domain.enums.StoreType.class);

    public final StringPath telephone = createString("telephone");

    public final ListPath<Timetable, QTimetable> timetableList = this.<Timetable, QTimetable>createList("timetableList", Timetable.class, QTimetable.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new QOwner(forProperty("owner"), inits.get("owner")) : null;
    }

}

