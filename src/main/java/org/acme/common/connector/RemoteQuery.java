/* @autogenerated */
package org.acme.common.connector;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface RemoteQuery {

  RemoteQuery header(String name, String value);

  RemoteQuery headers(String name, List<String> values);

  RemoteQuery header(Map<String, List<String>> headers);

  <T> RemoteConnection processor(Class<T> type, Consumer<T> consumer);

  RemoteConnection processor(Runnable runnable);
}
