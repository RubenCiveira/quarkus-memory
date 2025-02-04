package org.acme.common.projection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import com.api.jsonata4java.Expression;
import com.api.jsonata4java.expressions.ParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExecutionPlan {

  @Builder
  @Getter
  public static class PendingRequest {
    private final List<String> codes;
    private final Map<String, ExecutionAggregation> agregation;
  }
  
  private final ExecutionNode node;
  private final ExecutionTree tree;
  private final List<ExecutionAggregation> selection;
  private final String transformation;

  private final List<Map<String, Object>> responseData = new ArrayList<>();
  private final Map<RelationshipDefinition, PendingRequest> pendings = new HashMap<>();

  @SuppressWarnings("unchecked")
  /* default */ Object execute(Client client, ObjectMapper mapper, 
      Map<String, String> params, Map<String, List<String>> headers) {
    Invocation.Builder request =
        client.target(node.target(params)).request(MediaType.APPLICATION_JSON);
    headers.forEach(request::header);
    Response response = request.get();
    if (node.isList()) {
      List<Map<String, Object>> list =
          (List<Map<String, Object>>) response.readEntity(Map.class).get("items");
      if (list != null) {
        list.forEach(row -> addData(map("", (Map<String, Object>) row, getSelection(), node)));
      }
    } else {
      addData(map("", (Map<String, Object>) response.readEntity(Map.class), getSelection(), node));
    }
    while (!pendings.isEmpty()) {
      Map<RelationshipDefinition, PendingRequest> partPendings = new HashMap<>(pendings);
      pendings.clear();
      partPendings.forEach((def, vals) -> {
        batchExecute(client, headers, def, vals);
      });
    }
    if( !StringUtils.isBlank(transformation) ) {
      try {
        Expression expr = Expression.jsonata(transformation );
        JsonNode inputJson = mapper.valueToTree( responseData );
        JsonNode result = expr.evaluate(inputJson);
        List<Map<String, Object>> value = mapper.convertValue(result, new TypeReference<>() {});
        return node.isList() ? listResult(value) : itemResult(value);
      } catch (ParseException | IOException e) {
        e.printStackTrace();
      }
    }
    return node.isList() ? listResult(responseData) : itemResult(responseData);
  }

  @SuppressWarnings("unchecked")
  private void batchExecute(Client client, Map<String, List<String>> headers, RelationshipDefinition relation,
      PendingRequest vals) {
    List<String> props = new ArrayList<>(new HashSet<>(vals.getAgregation().keySet()));
    List<String> codes = new ArrayList<>(new HashSet<>(vals.getCodes()));

    List<List<String>> splitList = splitList(codes, 20);
    for (List<String> list : splitList) {
      String target =
          relation.getUrl() + "?" + relation.getBatchParam() + "=" + String.join(",", list);
      Invocation.Builder request = client.target(target).request(MediaType.APPLICATION_JSON);
      headers.forEach(request::header);
      Response response = request.get();
      List<Map<String, Object>> partials =
          (List<Map<String, Object>>) response.readEntity(Map.class).get("items");
      Map<String, List<Map<String, Object>>> indexed = new HashMap<>();
      for (Map<String, Object> with : partials) {
        String defref = (String) with.get(relation.getReferenceField());
        if (!indexed.containsKey(defref)) {
          indexed.put(defref, new ArrayList<>());
        }
        indexed.get(defref).add(with);
      }
      responseData.forEach(row -> {
        props.forEach(prop -> {
          String[] walk = prop.split("\\.");
          Map<String, Object> container = row;
          Object base = row;
          String containerName = prop;
          for (String walkProp : walk) {
            container = ((Map<String, Object>) base);
            base = container.get(walkProp);
            containerName = walkProp;
          }
          String from = (String) base;
          List<Map<String, Object>> rowData = indexed.get(from);
          if (null == rowData) {
            container.remove(containerName);
          } else if (relation.isList()) {
            Optional<ExecutionNode> byId = tree.byId(relation.getId());
            if (byId.isPresent()) {
              container.put(containerName, rowData.stream().map(eachRow -> map(prop + ".", eachRow,
                  vals.getAgregation().get(prop).getSelection(), byId.get())).toList());
            }
          } else {
            Optional<ExecutionNode> byId = tree.byId(relation.getId());
            if (byId.isPresent()) {
              container.put(containerName, map(prop + ".", rowData.get(0),
                  vals.getAgregation().get(prop).getSelection(), byId.get()));
            }
          }
        });
      });
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> map(String prefix, Map<String, Object> map,
      List<ExecutionAggregation> selection, ExecutionNode on) {
    Map<String, Object> row = new HashMap<>();
    selection.forEach(agregation -> {
      Object mapped = map.get(agregation.getName());
      // Vale
      RelationshipDefinition relation = on.getRelations().get(agregation.getName());
      if (relation != null) {
        if (null != relation.getOn()) {
          mapped = ((Map<String, Object>) mapped).get(relation.getOn());
        }
        row.put(agregation.getAlias(), String.valueOf(mapped));
        addPending(on.getRelations().get(agregation.getName()), prefix + agregation.getAlias(),
            agregation, String.valueOf(mapped));
      } else if (mapped instanceof Map) {
        row.put(agregation.getAlias(), map(agregation.getName() + ".", (Map<String, Object>) mapped,
            agregation.getSelection(), on));
      } else if (null != mapped) {
        if (agregation.getSelection().isEmpty()) {
          row.put(agregation.getAlias(), mapped);
        } else {
          System.err.println("Valor a expandir sin información detallada.");
        }
      }
    });
    return row;
  }

  private List<List<String>> splitList(List<String> originalList, int chunkSize) {
    return IntStream.range(0, (int) Math.ceil((double) originalList.size() / chunkSize))
        .mapToObj(i -> originalList.subList(i * chunkSize,
            Math.min((i + 1) * chunkSize, originalList.size())))
        .toList();
  }


  private void addData(Map<String, Object> map) {
    responseData.add(map);
  }

  private Object listResult(List<Map<String, Object>> data) {
    return data;
  }

  private Object itemResult(List<Map<String, Object>> data) {
    return data.isEmpty() ? null : data.get(0);
  }

  private void addPending(RelationshipDefinition def, String name, ExecutionAggregation property,
      String val) {
    if (!pendings.containsKey(def)) {
      pendings.put(def,
          PendingRequest.builder().agregation(new HashMap<>()).codes(new ArrayList<>()).build());
    }
    PendingRequest pendingRequest = pendings.get(def);
    pendingRequest.getCodes().add(val);
    pendingRequest.getAgregation().put(name, property);
  }

}
