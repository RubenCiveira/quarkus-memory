/* @autogenerated */
package org.acme.common.batch;

import java.util.ArrayList;
import java.util.List;

import org.acme.common.batch.stepper.ItemDescriptor;
import org.acme.common.batch.stepper.ItemProcessor;
import org.acme.common.batch.stepper.ItemReader;
import org.acme.common.batch.stepper.ItemWriter;
import org.acme.common.batch.stepper.StepContext;
import org.acme.common.batch.stepper.StepCounter;
import org.acme.common.batch.stepper.StepFinalizer;
import org.acme.common.batch.stepper.StepInitializer;
import org.acme.common.exception.AbstractFailsException;
import org.acme.common.validation.ExecutionFail;

import io.quarkus.arc.Arc;
import io.quarkus.arc.ManagedContext;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public class ExecutorByDeferSteps<T, R, P, S> implements Executor<P> {
  @Builder.Default
  private long readSleep = 100;
  @Builder.Default
  private long processSleep = 100;
  @Builder.Default
  private long writeSleep = 100;
  @Builder.Default
  private int bufferSize = 10;
  // private final Class<? extends StepInitializer<P, S>> initializer;
  // private final Class<? extends ItemProcessor<T, R, P, S>> processor;
  // private final Class<? extends ItemReader<T, P, S>> reader;
  // private final Class<? extends ItemWriter<R, P, S>> writer;
  // private final Class<? extends ItemDescriptor<T, P, S>> descriptor;
  // private final Class<? extends StepFinalizer<P, S>> finalizer;
  // private final Class<? extends StepCounter<P, S>> counter;
  private final Class<?> initializer;
  private final Class<?> processor;
  private final Class<?> reader;
  private final Class<?> writer;
  private final Class<?> descriptor;
  private final Class<?> finalizer;
  private final Class<?> counter;

  @Override
  @SuppressWarnings("unchecked")
  public void run(BatchStepProgress result, Monitor store, P param) {
    sleep("Initial delay to close request context", 3000);
    StepContext<P, S> context = new StepContext<P, S>(param, store);

    ManagedContext requestContext = Arc.container().requestContext();
    if (!requestContext.isActive()) {
      System.out.println("EL contexto no está activo, lo activamnos");
      requestContext.activate(); // Activar contexto manualmente
    } else {
      System.out.println("El contexto ya está activo, lo dejamos");
    }
    try {
      StepInitializer<P, S> forInit = null == initializer ? null
          : (StepInitializer<P, S>) Arc.container().instance(initializer).get();
      ItemDescriptor<T, P, S> forDescriptor = null == descriptor ? new ItemDescriptor<T, P, S>() {
        @Override
        public String itemDescription(T item, StepContext<P, S> context) {
          return item.toString();
        }
      } : (ItemDescriptor<T, P, S>) Arc.container().instance(descriptor).get();
      ItemReader<T, P, S> forReader = (ItemReader<T, P, S>) Arc.container().instance(reader).get();
      ItemWriter<R, P, S> forWriter = (ItemWriter<R, P, S>) Arc.container().instance(writer).get();
      ItemProcessor<T, R, P, S> forProcessor =
          (ItemProcessor<T, R, P, S>) Arc.container().instance(processor).get();
      StepFinalizer<P, S> forFinalizer = null == finalizer ? null
          : (StepFinalizer<P, S>) Arc.container().instance(finalizer).get();
      StepCounter<P, S> forCounter =
          null == counter ? null : (StepCounter<P, S>) Arc.container().instance(counter).get();

      if (null != forInit) {
        forInit.init(context);
      }
      if (null != forCounter) {
        result.setTotalItems(forCounter.approximatedItems(context));
        store.updateState(result);
      }

      List<T> itemsWindow;
      List<Object[]> results = new ArrayList<>();
      while (procesable(itemsWindow = forReader.read(context))) {
        for (T item : itemsWindow) {
          R processed = process(item, forProcessor, forDescriptor, result, store, context);
          if (null != processed) {
            results.add(new Object[] {item, processed});
          }
          if (results.size() == bufferSize) {
            write(results, forWriter, forDescriptor, result, store, context);
            store.updateState(result);
            sleep("after write items", writeSleep);
            results.clear();
          }
        }
        sleep("after read items", readSleep);
      }
      write(results, forWriter, forDescriptor, result, store, context);
      store.updateState(result);
      if (null != forFinalizer) {
        forFinalizer.finish(context);
      }
      store.updateState(result);
    } finally {
      requestContext.terminate();
    }
  }

  private boolean procesable(List<T> items) {
    return items != null && !items.isEmpty();
  }

  private R process(T item, ItemProcessor<T, R, P, S> processor, ItemDescriptor<T, P, S> descriptor,
      BatchStepProgress result, Monitor store, StepContext<P, S> context) {
    String ref = descriptor.itemDescription(item, context);
    try {
      R processed = processor.process(item, context);
      sleep("after process items", processSleep);
      return processed;
    } catch (AbstractFailsException ex) {
      ex.getFails().forEach(fail -> result.addError(ref, fail));
      store.updateState(result);
      return null;
    } catch (Exception ex) {
      result.addError(ref, new ExecutionFail(ex.getClass().getName(), ex.getMessage()));
      store.updateState(result);
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private void write(List<Object[]> items, ItemWriter<R, P, S> writer,
      ItemDescriptor<T, P, S> descriptor, BatchStepProgress result, Monitor store,
      StepContext<P, S> context) {
    List<T> inputs = new ArrayList<>();
    List<R> outputs = new ArrayList<>();
    for (Object[] rows : items) {
      inputs.add((T) rows[0]);
      outputs.add((R) rows[1]);
    }
    try {
      writer.write(outputs, context);
      inputs.forEach(input -> {
        result.addOk(descriptor.itemDescription(input, context));
      });
    } catch (Exception ex) {
      inputs.forEach(input -> {
        result.addError(descriptor.itemDescription(input, context),
            new ExecutionFail(ex.getClass().getName(), ex.getMessage()));
      });
    }
  }

  private void sleep(String label, long time) {
    if (time > 0) {
      try {
        Thread.sleep(time);
      } catch (InterruptedException e) {
        log.error("Interrupt the thread " + label, e);
      }
    }
  }
}
