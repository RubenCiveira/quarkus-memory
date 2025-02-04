package org.acme.common.projection;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class ProjectionRunner {
  private final ObjectMapper mapper;
  private final Client client = ClientBuilder.newClient();

  public Object execute(ExecutionPlan plan, Map<String, String> params,
      Map<String, List<String>> headers) {
    return plan.execute(client, mapper, params, headers);
  }
}
