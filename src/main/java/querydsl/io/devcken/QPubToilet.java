package io.devcken;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QPubToilet is a Querydsl query type for QPubToilet
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QPubToilet extends com.querydsl.sql.RelationalPathBase<QPubToilet> {

    private static final long serialVersionUID = 1690291753;

    public static final QPubToilet pubToilet = new QPubToilet("PUB_TOILET");

    public final StringPath addressJibeon = createString("addressJibeon");

    public final StringPath addressStreet = createString("addressStreet");

    public final StringPath builtDate = createString("builtDate");

    public final SimplePath<Object> geom = createSimple("geom", Object.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath name = createString("name");

    public final StringPath operatingTime = createString("operatingTime");

    public final StringPath orgInCharge = createString("orgInCharge");

    public final StringPath orgTelNo = createString("orgTelNo");

    public final StringPath toiletFemale = createString("toiletFemale");

    public final StringPath toiletFemaleChild = createString("toiletFemaleChild");

    public final StringPath toiletFemaleHandicapped = createString("toiletFemaleHandicapped");

    public final StringPath toiletMale = createString("toiletMale");

    public final StringPath toiletMaleChild = createString("toiletMaleChild");

    public final StringPath toiletMaleHandicapped = createString("toiletMaleHandicapped");

    public final StringPath type = createString("type");

    public final StringPath unisex = createString("unisex");

    public final StringPath updatedDate = createString("updatedDate");

    public final StringPath urinalMale = createString("urinalMale");

    public final StringPath urinalMaleChild = createString("urinalMaleChild");

    public final StringPath urinalMaleHandicapped = createString("urinalMaleHandicapped");

    public final com.querydsl.sql.PrimaryKey<QPubToilet> constraint8 = createPrimaryKey(id);

    public QPubToilet(String variable) {
        super(QPubToilet.class, forVariable(variable), "PUBLIC", "PUB_TOILET");
        addMetadata();
    }

    public QPubToilet(String variable, String schema, String table) {
        super(QPubToilet.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QPubToilet(String variable, String schema) {
        super(QPubToilet.class, forVariable(variable), schema, "PUB_TOILET");
        addMetadata();
    }

    public QPubToilet(Path<? extends QPubToilet> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "PUB_TOILET");
        addMetadata();
    }

    public QPubToilet(PathMetadata metadata) {
        super(QPubToilet.class, metadata, "PUBLIC", "PUB_TOILET");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(addressJibeon, ColumnMetadata.named("ADDRESS_JIBEON").withIndex(4).ofType(Types.VARCHAR).withSize(1000));
        addMetadata(addressStreet, ColumnMetadata.named("ADDRESS_STREET").withIndex(5).ofType(Types.VARCHAR).withSize(1000));
        addMetadata(builtDate, ColumnMetadata.named("BUILT_DATE").withIndex(19).ofType(Types.VARCHAR).withSize(20));
        addMetadata(geom, ColumnMetadata.named("GEOM").withIndex(21).ofType(Types.OTHER).withSize(2147483647));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.BIGINT).withSize(19).notNull());
        addMetadata(latitude, ColumnMetadata.named("LATITUDE").withIndex(23).ofType(Types.DOUBLE).withSize(17));
        addMetadata(longitude, ColumnMetadata.named("LONGITUDE").withIndex(22).ofType(Types.DOUBLE).withSize(17));
        addMetadata(name, ColumnMetadata.named("NAME").withIndex(3).ofType(Types.VARCHAR).withSize(255));
        addMetadata(operatingTime, ColumnMetadata.named("OPERATING_TIME").withIndex(18).ofType(Types.VARCHAR).withSize(20));
        addMetadata(orgInCharge, ColumnMetadata.named("ORG_IN_CHARGE").withIndex(16).ofType(Types.VARCHAR).withSize(255));
        addMetadata(orgTelNo, ColumnMetadata.named("ORG_TEL_NO").withIndex(17).ofType(Types.VARCHAR).withSize(20));
        addMetadata(toiletFemale, ColumnMetadata.named("TOILET_FEMALE").withIndex(13).ofType(Types.VARCHAR).withSize(10));
        addMetadata(toiletFemaleChild, ColumnMetadata.named("TOILET_FEMALE_CHILD").withIndex(15).ofType(Types.VARCHAR).withSize(10));
        addMetadata(toiletFemaleHandicapped, ColumnMetadata.named("TOILET_FEMALE_HANDICAPPED").withIndex(14).ofType(Types.VARCHAR).withSize(10));
        addMetadata(toiletMale, ColumnMetadata.named("TOILET_MALE").withIndex(7).ofType(Types.VARCHAR).withSize(10));
        addMetadata(toiletMaleChild, ColumnMetadata.named("TOILET_MALE_CHILD").withIndex(11).ofType(Types.VARCHAR).withSize(10));
        addMetadata(toiletMaleHandicapped, ColumnMetadata.named("TOILET_MALE_HANDICAPPED").withIndex(9).ofType(Types.VARCHAR).withSize(10));
        addMetadata(type, ColumnMetadata.named("TYPE").withIndex(2).ofType(Types.VARCHAR).withSize(30));
        addMetadata(unisex, ColumnMetadata.named("UNISEX").withIndex(6).ofType(Types.CHAR).withSize(1));
        addMetadata(updatedDate, ColumnMetadata.named("UPDATED_DATE").withIndex(20).ofType(Types.VARCHAR).withSize(10));
        addMetadata(urinalMale, ColumnMetadata.named("URINAL_MALE").withIndex(8).ofType(Types.VARCHAR).withSize(10));
        addMetadata(urinalMaleChild, ColumnMetadata.named("URINAL_MALE_CHILD").withIndex(12).ofType(Types.VARCHAR).withSize(10));
        addMetadata(urinalMaleHandicapped, ColumnMetadata.named("URINAL_MALE_HANDICAPPED").withIndex(10).ofType(Types.VARCHAR).withSize(10));
    }

}

