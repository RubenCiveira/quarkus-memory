/* @autogenerated */
package org.acme.common.batch;

public interface Executor<T> {
  void run(BatchStepProgress progress, Monitor t, T params);
}
