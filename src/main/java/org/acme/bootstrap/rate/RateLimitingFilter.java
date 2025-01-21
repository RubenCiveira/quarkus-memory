package org.acme.bootstrap.rate;

import java.time.Duration;

import io.github.bucket4j.Bucket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class RateLimitingFilter implements ContainerRequestFilter, ContainerResponseFilter {

  private final Bucket bucket;

  public RateLimitingFilter() {
    this.bucket = Bucket.builder()
        .addLimit(limit -> limit.capacity(200).refillGreedy(100, Duration.ofMinutes(1))).build();
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    // Verificar si hay tokens disponibles
    if (!bucket.tryConsume(1)) {
      // Limitar la solicitud si no hay tokens
      Response response =
          Response.status(Response.Status.TOO_MANY_REQUESTS).entity("Too many requests").build();
      requestContext.abortWith(response);
    }
  }

  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) {
    // Añadir cabeceras de información al cliente
    responseContext.getHeaders().add("X-RateLimit-Remaining",
        String.valueOf(bucket.getAvailableTokens()));
  }
}
