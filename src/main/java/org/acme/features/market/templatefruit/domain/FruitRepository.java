package org.acme.features.market.templatefruit.domain;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitRepository {
  // implements FruitRepositoryGateway {
  // }
  // private final DataSource datasource;
  //
  // @Override
  // public Uni<Slide<Fruit>> list(FruitFilter filter, FruitCursor cursor) {
  // try (var template = new SqlTemplate(datasource)) {
  // SqlResult<Fruit> result = template.query("select uid, name, version from fruits",
  // row -> Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
  // .versionValue(row.getInt(3)).build());
  // return Uni.createFrom()
  // .item(new FruitRepositorySlice(
  // cursor.getLimit().map(limit -> result.limit(limit)).orElseGet(() -> result.all()),
  // filter, cursor, this));
  // }
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> retrieve(FruitFilter filter) {
  // try (var template = new SqlTemplate(datasource)) {
  // SqlResult<Fruit> result =
  // template.query("select uid, name, version from fruits where uid = :uid",
  // row -> Fruit.builder().uidValue(row.getString(1)).nameValue(row.getString(2))
  // .versionValue(row.getInt(3)).build(),
  // new SqlParam("uid", filter.getUid().get(), SqlType.STRING));
  // return Uni.createFrom().item(result.one());
  // }
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> update(String uid, Fruit entity) {
  // return null;
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> create(Fruit entity) {
  // return createAndVerify(entity, null);
  // }
  //
  // @Override
  // public Uni<Optional<Fruit>> createAndVerify(Fruit entity, Function<Fruit, Boolean> verificator)
  // {
  // try (var template = new SqlTemplate(datasource)) {
  // template.execute("insert into fruits(uid, name, version) values (:uid, :name, 0)",
  // new SqlParam("uid", entity.getUid().getValue(), SqlType.STRING),
  // new SqlParam("name", entity.getName().getValue(), SqlType.STRING));
  // template.commit();
  // if (null == verificator || verificator.apply(entity)) {
  // return Uni.createFrom().item(Optional.of(entity));
  // } else {
  // rollback(entity);
  // return Uni.createFrom().item(Optional.empty());
  // }
  // }
  // }
  //
  // private Uni<Void> rollback(Fruit entity) {
  // try (var template = new SqlTemplate(datasource)) {
  // template.execute("delete from fruits where uid = :uid",
  // new SqlParam("uid", entity.getUid(), SqlType.STRING));
  // return Uni.createFrom().voidItem();
  // }
  // }
}
