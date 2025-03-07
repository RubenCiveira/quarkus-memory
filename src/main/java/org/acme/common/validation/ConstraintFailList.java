/* @autogenerated */
package org.acme.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ConstraintFailList extends AbstractFailList {

  public ConstraintFailList() {
    super();
  }

  public ConstraintFailList(String code, String field, Object wrongValue) {
    super(new ConstraintFail(code, field, wrongValue));
  }

  public ConstraintFailList(String code, String field, Object wrongValue, String errorMessage) {
    super(new ConstraintFail(code, field, wrongValue, errorMessage));
  }

  public ConstraintFailList(ConstraintFail fail) {
    super(new ArrayList<>(List.of(fail)));
  }

  public ConstraintFailList(List<ConstraintFail> fails) {
    super(fails);
  }

  public void add(ConstraintFail fail) {
    super.add(fail);
  }
}
