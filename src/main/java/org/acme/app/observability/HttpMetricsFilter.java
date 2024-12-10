package org.acme.app.observability;

import java.io.IOException;

import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class HttpMetricsFilter implements ContainerResponseFilter, ContainerRequestFilter {
  private static final String STRATING_TIME_PROPERTY = "starting-time";
  private final LongCounter requestCount;

  private final DoubleHistogram requestLatence;

  public HttpMetricsFilter(Meter meter) {
    requestCount = meter.counterBuilder("http_requests_total")
        .setDescription("Total number of HTTP requests").setUnit("1").build();
    requestLatence = meter.histogramBuilder("http_request_duration_seconds")
        .setDescription("HTTP request latency").setUnit("s").build();
  }

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    requestContext.setProperty(STRATING_TIME_PROPERTY, System.currentTimeMillis());
  }

  @Override
  public void filter(ContainerRequestContext requestContext,
      ContainerResponseContext responseContext) {
    // Increment the request count
    requestCount.add(1);
    // Example: measure latency (replace with actual timing logic)
    Object property = requestContext.getProperty(STRATING_TIME_PROPERTY);
    if (property instanceof Long st) {
      double latencyInSeconds = (System.currentTimeMillis() - st) / 1_000d; // Replace with real
                                                                            // latency
      requestLatence.record(latencyInSeconds);
    } else {
      double latencyInSeconds = Math.random(); // Replace with real latency
      requestLatence.record(latencyInSeconds);
    }
  }
}
