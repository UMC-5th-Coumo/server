package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOwnerCoupon is a Querydsl query type for OwnerCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOwnerCoupon extends EntityPathBase<OwnerCoupon> {

    private static final long serialVersionUID = 2095222711L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOwnerCoupon ownerCoupon = new QOwnerCoupon("ownerCoupon");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    public final StringPath couponColor = createString("couponColor");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fontColor = createString("fontColor");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final QOwner owner;

    public final StringPath stampImage = createString("stampImage");

    public final EnumPath<coumo.server.domain.enums.StampMax> stampMax = createEnum("stampMax", coumo.server.domain.enums.StampMax.class);

    public final StringPath storeName = createString("storeName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOwnerCoupon(String variable) {
        this(OwnerCoupon.class, forVariable(variable), INITS);
    }

    public QOwnerCoupon(Path<? extends OwnerCoupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOwnerCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOwnerCoupon(PathMetadata metadata, PathInits inits) {
        this(OwnerCoupon.class, metadata, inits);
    }

    public QOwnerCoupon(Class<? extends OwnerCoupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new QOwner(forProperty("owner"), inits.get("owner")) : null;
    }

}

