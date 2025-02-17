/* @autogenerated */
package org.acme.common.batch;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.common.validation.AbstractFail;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class BatchStepProgress {
  public enum Status {
    PENDING, PROCESSING, FINISHED, FAILED
  }

  @Data
  @RegisterForReflection
  public static class ErrorInfo {
    private List<AbstractFail> fails;

    public ErrorInfo() {
      /* empty constructor */
    }

    public ErrorInfo(AbstractFail constraint) {
      fails = new ArrayList<>();
      fails.add(constraint);
    }

    public void add(AbstractFail runtime) {
      if (null == fails) {
        fails = new ArrayList<>();
      }
      fails.add(runtime);
    }
  }


  private String name;
  private Status status = Status.PROCESSING;
  private String error;
  private Instant startTime = Instant.now();
  private Instant endTime = Instant.now();

  private long totalItems = 0;
  private long processedItems = 0;
  private List<String> oks;
  private Map<String, ErrorInfo> warns;
  private Map<String, ErrorInfo> errors;

  public void addOk(String referencia) {
    if (null == oks) {
      oks = new ArrayList<>(Arrays.asList(referencia));
    } else {
      oks.add(referencia);
    }
    ++processedItems;
  }

  public void addWarns(String codigo, AbstractFail referencia) {
    if (null == warns) {
      warns = new HashMap<>();
      warns.put(codigo, new ErrorInfo(referencia));
    } else if (!warns.containsKey(codigo)) {
      warns.put(codigo, new ErrorInfo(referencia));
    } else {
      warns.get(codigo).add(referencia);
    }
    ++processedItems;
  }

  public void addError(String codigo, AbstractFail referencia) {
    if (null == errors) {
      errors = new HashMap<>();
      errors.put(codigo, new ErrorInfo(referencia));
    } else if (!errors.containsKey(codigo)) {
      errors.put(codigo, new ErrorInfo(referencia));
    } else {
      errors.get(codigo).add(referencia);
    }
    ++processedItems;
  }
}
