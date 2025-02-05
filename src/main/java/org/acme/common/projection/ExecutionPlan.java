/* @autogenerated */
package org.acme.common.projection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.acme.common.connector.RemoteConnector;

import com.fasterxml.jackson.databind.ObjectMapper;

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

  private final String path;
  private final ExecutionTree tree;
  private final List<ExecutionAggregation> selection;

  private final List<Map<String, Object>> responseData = new ArrayList<>();
  private final Map<RelationshipDefinition, PendingRequest> pendings = new HashMap<>();

  @SuppressWarnings("unchecked")
  /* default */ <T> List<T> execute(ExecutionNode node, Class<T> type, RemoteConnector client,
      ObjectMapper mapper, Map<String, String> params, Map<String, List<String>> headers) {
    Map<String, String> pathParams = new HashMap<>();
    Map<String, String> queryParams = new HashMap<>();
    Map<String, List<String>> headerParams = new HashMap<>(headers);
    params.forEach((key, value) -> {
      if (ParamKind.PATH == node.getParams().get(key)) {
        pathParams.put(key, value);
      } else if (ParamKind.HEADER == node.getParams().get(key)) {
        if (headerParams.containsKey(key)) {
          headerParams.get(key).add(value);
        } else {
          headerParams.put(key, List.of(value));
        }
      } else {
        queryParams.put(key, value);
      }
    });
    client.send(client.get(node.target(params)).pathParam(pathParams).queryParam(queryParams)
        .headers(headerParams).processor(Map.class, read -> {
          if (node.isList()) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) read.get("items");
            if (list != null) {
              list.forEach(row -> addData(map("", row, getSelection(), node)));
            }
          } else {
            addData(map("", read, getSelection(), node));
          }
        }));
    while (!pendings.isEmpty()) {
      Map<RelationshipDefinition, PendingRequest> partPendings = new HashMap<>(pendings);
      pendings.clear();
      partPendings.forEach((def, vals) -> {
        batchExecute(client, headers, def, vals);
      });
    }

    List<Map<String, Object>> response = responseData.stream().map(this::flattenMap).toList();
    return Map.class.isAssignableFrom(type) ? (List<T>) response
        : mapper.convertValue(response,
            mapper.getTypeFactory().constructCollectionType(List.class, type));
  }

  private Map<String, Object> flattenMap(Map<String, Object> originalMap) {
    Map<String, Object> flatMap = new LinkedHashMap<>();
    flatten(originalMap, flatMap);
    return flatMap;
  }


  @SuppressWarnings("unchecked")
  private void flatten(Map<String, Object> source, Map<String, Object> target) {
    for (Map.Entry<String, Object> entry : source.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof Map) {
        // Si el valor es otro mapa, llamar recursivamente
        flatten((Map<String, Object>) value, target);
      } else {
        String[] parts = key.split("\\.");
        Map<String, Object> container = target;
        Map<String, Object> base = target;
        String last = key;
        for (String part : parts) {
          base = container;
          Object with = container.get(part);
          if (null == with) {
            with = new HashMap<>();
            container.put(part, with);
            container = (Map<String, Object>) with;
          } else if (with instanceof Map) {
            container = (Map<String, Object>) with;
          } else {
            container = null;
            break;
          }
          last = part;
        }
        if (base != null) {
          // Si no, guardar el valor en el mapa final
          base.put(last, value);
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void batchExecute(RemoteConnector client, Map<String, List<String>> headers,
      RelationshipDefinition relation, PendingRequest vals) {
    List<String> props = new ArrayList<>(new HashSet<>(vals.getAgregation().keySet()));
    List<String> codes = new ArrayList<>(new HashSet<>(vals.getCodes()));

    client.send(splitList(codes, 20).map(list -> {
      String target =
          relation.getUrl() + "?" + relation.getBatchParam() + "=" + String.join(",", list);
      return client.get(target).processor(Map.class, response -> {
        List<Map<String, Object>> partials = (List<Map<String, Object>>) response.get("items");
        Map<String, List<Map<String, Object>>> indexed = new HashMap<>();
        for (Map<String, Object> with : partials) {
          String defref = (String) with.get(relation.getReferenceField());
          if (!indexed.containsKey(defref)) {
            indexed.put(defref, new ArrayList<>());
          }
          indexed.get(defref).add(with);
        }
        synchronized (responseData) {
          processResponseData(responseData, props, indexed, relation, vals);
        }
      });
    }));
  }

  @SuppressWarnings("unchecked")
  private void processResponseData(List<Map<String, Object>> responseData, List<String> props,
      Map<String, List<Map<String, Object>>> indexed, RelationshipDefinition relation,
      PendingRequest vals) {
    responseData.forEach(eachrow -> {
      props.forEach(prop -> {
        String[] walk = prop.split("\\.");
        Map<String, Object> container = eachrow;
        Object base = eachrow;
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

  @SuppressWarnings("unchecked")
  private Map<String, Object> map(String prefix, Map<String, Object> map,
      List<ExecutionAggregation> selection, ExecutionNode on) {
    Map<String, Object> row = new HashMap<>();
    selection.forEach(agregation -> {
      System.out.println("MIRO EL ENCAJE DE " + agregation.getName());
      Object mapped = map.get(agregation.getName());
      // Vale
      RelationshipDefinition relation = on.getRelations().get(agregation.getName());
      System.out.println("\tMIRO SI " + agregation.getName() + " tiene relaciones");
      if (relation != null) {
        if (null != relation.getOn()) {
          mapped = ((Map<String, Object>) mapped).get(relation.getOn());
          System.out.println("Extraigo el valor de " + relation.getOn());
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

  private Stream<List<String>> splitList(List<String> originalList, int chunkSize) {
    return IntStream.range(0, (int) Math.ceil((double) originalList.size() / chunkSize))
        .mapToObj(i -> originalList.subList(i * chunkSize,
            Math.min((i + 1) * chunkSize, originalList.size())));
  }

  private void addData(Map<String, Object> map) {
    responseData.add(map);
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
