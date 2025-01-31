package org.acme.common.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

// Un slide es un stream, que permite avanzar más datos...
public abstract class Slider<T> {
  private final int limit;
  private final Iterator<T> multi;

  public Slider(Iterator<T> multi, int limit) {
    this.multi = multi;
    this.limit = limit;
  }

  public abstract Iterator<T> next(List<T> valids, int limit);

  public Iterator<T> slide(Predicate<T> predicate) {
    return applySlide(predicate);
  }
  
  private Iterator<T> applySlide(Predicate<T> predicate) {
    // Tengo que construir un iterador qeu cuando se termine llame al siguiente
    return new FD<>(multi, filteredData -> {
      int registrosUtiles = filteredData.size();
      int registrosFaltantes = limit - filteredData.size();
      double ratioDescarte = 1.0 - ((double) registrosUtiles / limit);
      int nuevosRegistros = (int) Math.ceil(registrosFaltantes / (1.0 - ratioDescarte));
      return registrosFaltantes > 0 ? next(filteredData, nuevosRegistros) : null;
    });
  }
}


class FD<T> implements Iterator<T> {
  private Iterator<T> current;
  private final Function<List<T>, Iterator<T>> next;
  private final List<T> items = new ArrayList<>();
  
  public FD(Iterator<T> initial, Function<List<T>, Iterator<T>> next) {
    super();
    this.current = initial;
    this.next = next;
  }

  @Override
  public boolean hasNext() {
    if( current.hasNext() ) {
      return true;
    } else {
      current = next.apply(items);
      return null == current ? false : current.hasNext();
    }
  }

  @Override
  public T next() {
    T item = current.next();
    items.add( item );
    return item;
  }
  
  
}