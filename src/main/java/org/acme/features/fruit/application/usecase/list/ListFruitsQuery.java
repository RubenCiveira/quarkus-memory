package org.acme.features.fruit.application.usecase.list;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.fruit.domain.query.FruitCursor;
import org.acme.features.fruit.domain.query.FruitFilter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListFruitsQuery {
  private FruitFilter filter;
  private FruitCursor cursor;
  private final Actor actor;
  private final Connection connection;
}
