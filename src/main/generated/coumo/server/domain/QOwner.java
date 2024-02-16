package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOwner is a Querydsl query type for Owner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOwner extends EntityPathBase<Owner> {

    private static final long serialVersionUID = -850365391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOwner owner = new QOwner("owner");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final ListPath<OwnerCoupon, QOwnerCoupon> ownerCouponList = this.<OwnerCoupon, QOwnerCoupon>createList("ownerCouponList", OwnerCoupon.class, QOwnerCoupon.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<coumo.server.domain.enums.State> state = createEnum("state", coumo.server.domain.enums.State.class);

    public final QStore store;

    public final ListPath<SubscriptionReceipt, QSubscriptionReceipt> subscriptionReceiptList = this.<SubscriptionReceipt, QSubscriptionReceipt>createList("subscriptionReceiptList", SubscriptionReceipt.class, QSubscriptionReceipt.class, PathInits.DIRECT2);

    public final EnumPath<coumo.server.domain.enums.SubscriptionType> subscriptionType = createEnum("subscriptionType", coumo.server.domain.enums.SubscriptionType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QOwner(String variable) {
        this(Owner.class, forVariable(variable), INITS);
    }

    public QOwner(Path<? extends Owner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOwner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOwner(PathMetadata metadata, PathInits inits) {
        this(Owner.class, metadata, inits);
    }

    public QOwner(Class<? extends Owner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

