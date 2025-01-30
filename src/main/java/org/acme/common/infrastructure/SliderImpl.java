package org.acme.common.infrastructure;

import java.util.List;
import java.util.function.Function;
import org.acme.common.reactive.Slider;
import org.acme.common.reactive.Stream;
import io.smallrye.mutiny.Multi;

// Un slide es un stream, que permite avanzar más datos...
public abstract class SliderImpl<T> extends StreamImpl<T> implements Slider<T> {
  private final int limit;

  public SliderImpl(Stream<T> multi, int limit) {
    super(multi);
    this.limit = limit;
  }

  public abstract SliderImpl<T> next(List<T> valids, int limit);


  @Override
  public Stream<T> slide(Function<T, Stream<Boolean>> predicate) {
    return copy(applySlide(predicate));
  }

  private Multi<T> applySlide(Function<T, Stream<Boolean>> predicate) {
    Multi<T> filteredMulti = multi.flatMap(value -> {
      StreamImpl<Boolean> apply = (StreamImpl<Boolean>) predicate.apply(value);
      return apply.multi.flatMap(
          include -> include ? Multi.createFrom().item(value) : Multi.createFrom().empty());
    });

    return filteredMulti.onCompletion().switchTo(() -> {
      // FIXME: ¿cuando se avanza entre páginas, filteredData tiene todos, o sólo el siguiente
      // bloque?
      List<T> filteredData = filteredMulti.collect().asList().await().indefinitely();
      int registrosUtiles = filteredData.size();
      int registrosFaltantes = limit - filteredData.size();
      double ratioDescarte = 1.0 - ((double) registrosUtiles / limit);
      int nuevosRegistros = (int) Math.ceil(registrosFaltantes / (1.0 - ratioDescarte));
      return registrosFaltantes > 0 ? next(filteredData, nuevosRegistros).applySlide(predicate)
          : Multi.createFrom().items();
    });
  }

}
