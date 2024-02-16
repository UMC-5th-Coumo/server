package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDesignReceipt is a Querydsl query type for DesignReceipt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDesignReceipt extends EntityPathBase<DesignReceipt> {

    private static final long serialVersionUID = -1212179944L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDesignReceipt designReceipt = new QDesignReceipt("designReceipt");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    public final StringPath couponDescription = createString("couponDescription");

    public final StringPath couponTitle = createString("couponTitle");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phone = createString("phone");

    public final QStore store;

    public final EnumPath<coumo.server.domain.enums.StoreType> storeType = createEnum("storeType", coumo.server.domain.enums.StoreType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDesignReceipt(String variable) {
        this(DesignReceipt.class, forVariable(variable), INITS);
    }

    public QDesignReceipt(Path<? extends DesignReceipt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDesignReceipt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDesignReceipt(PathMetadata metadata, PathInits inits) {
        this(DesignReceipt.class, metadata, inits);
    }

    public QDesignReceipt(Class<? extends DesignReceipt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

