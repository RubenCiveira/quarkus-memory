/* @autogenerated */
package org.acme.common.batch.storage;

import java.time.Instant;
import java.util.Optional;

import org.acme.common.batch.BatchProgress;

public interface MasiveOperationStorage {

  public void save(String taks, String actor, BatchProgress result);

  public Optional<BatchProgress> restores(String task, String actor);

  public void finish(String taskUid, Instant until, String actor);
}
