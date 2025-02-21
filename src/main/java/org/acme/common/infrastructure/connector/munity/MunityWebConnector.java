/* @autogenerated */
package org.acme.common.infrastructure.connector.munity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.acme.common.connector.RemoteConnection;
import org.acme.common.connector.RemoteConnector;
import org.acme.common.connector.RemoteQuery;
import org.acme.common.infrastructure.connector.munity.MunityWebQuery.Method;
import io.opentelemetry.api.trace.Tracer;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;

public class MunityWebConnector implements RemoteConnector {
  private final WebClient client;
  private final Tracer tracer;

  public MunityWebConnector(Vertx vertx, Tracer tracer) {
//    Vertx vertx = Vertx.vertx();
    this.client = WebClient.create(vertx);
    this.tracer = tracer;
  }

  @Override
  public RemoteQuery get(String target) {
    return MunityWebQuery.create(tracer, client, null, target, null);
  }

  @Override
  public RemoteQuery delete(String target) {
    return MunityWebQuery.create(tracer, client, Method.DELETE, target, null);
  }

  @Override
  public RemoteQuery post(String target, Object body) {
    return MunityWebQuery.create(tracer, client, Method.POST, target, body);
  }

  @Override
  public RemoteQuery put(String target, Object body) {
    return MunityWebQuery.create(tracer, client, Method.PUT, target, body);
  }

  @Override
  public RemoteQuery patch(String target, Object body) {
    return MunityWebQuery.create(tracer, client, Method.PATCH, target, body);
  }

  @Override
  public void send(RemoteConnection... request) {
    send(Arrays.asList(request));
  }

  @Override
  public void send(List<RemoteConnection> request) {
    if (request.size() == 1) {
      map(request.get(0)).await().indefinitely();
    } else if (!request.isEmpty()) {
      Uni.combine().all().unis(map(request)).with(result -> "").await().indefinitely();
    }
  }

  @Override
  public void send(Stream<RemoteConnection> request) {
    send(request.toList());
  }

  private Uni<String> map(RemoteConnection conn) {
    return ((MunityWebConnection) conn).buffer();
  }

  private List<Uni<String>> map(List<RemoteConnection> conn) {
    return conn.stream().map(this::map).toList();
  }
}
