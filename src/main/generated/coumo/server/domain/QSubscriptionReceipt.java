package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubscriptionReceipt is a Querydsl query type for SubscriptionReceipt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubscriptionReceipt extends EntityPathBase<SubscriptionReceipt> {

    private static final long serialVersionUID = -666520743L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubscriptionReceipt subscriptionReceipt = new QSubscriptionReceipt("subscriptionReceipt");

    public final coumo.server.domain.common.QBaseEntity _super = new coumo.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QOwner owner;

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final EnumPath<coumo.server.domain.enums.SubscriptionType> subscriptionType = createEnum("subscriptionType", coumo.server.domain.enums.SubscriptionType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSubscriptionReceipt(String variable) {
        this(SubscriptionReceipt.class, forVariable(variable), INITS);
    }

    public QSubscriptionReceipt(Path<? extends SubscriptionReceipt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubscriptionReceipt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubscriptionReceipt(PathMetadata metadata, PathInits inits) {
        this(SubscriptionReceipt.class, metadata, inits);
    }

    public QSubscriptionReceipt(Class<? extends SubscriptionReceipt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.owner = inits.isInitialized("owner") ? new QOwner(forProperty("owner"), inits.get("owner")) : null;
    }

}

