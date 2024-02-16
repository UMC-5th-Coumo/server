package coumo.server.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTimetable is a Querydsl query type for Timetable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTimetable extends EntityPathBase<Timetable> {

    private static final long serialVersionUID = 219296671L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTimetable timetable = new QTimetable("timetable");

    public final StringPath day = createString("day");

    public final StringPath endTime = createString("endTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath startTime = createString("startTime");

    public final QStore store;

    public QTimetable(String variable) {
        this(Timetable.class, forVariable(variable), INITS);
    }

    public QTimetable(Path<? extends Timetable> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTimetable(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTimetable(PathMetadata metadata, PathInits inits) {
        this(Timetable.class, metadata, inits);
    }

    public QTimetable(Class<? extends Timetable> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store"), inits.get("store")) : null;
    }

}

