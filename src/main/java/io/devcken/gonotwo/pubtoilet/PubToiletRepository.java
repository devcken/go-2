package io.devcken.gonotwo.pubtoilet;

import com.querydsl.core.types.Projections;
import com.querydsl.sql.SQLQueryFactory;
import io.devcken.QPubToilet;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class PubToiletRepository {
  private final SQLQueryFactory queryFactory;

  @Inject
  public PubToiletRepository(SQLQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  List<PubToilet> findToilets() {
    QPubToilet qPubToilet = QPubToilet.pubToilet;

    return queryFactory
      .select(Projections.bean(PubToilet.class, qPubToilet.all()))
      .from(qPubToilet)
      .where(qPubToilet.geom.isNotNull())
      .orderBy(qPubToilet.longitude.asc(), qPubToilet.latitude.asc())
      .fetch();
  }
}
