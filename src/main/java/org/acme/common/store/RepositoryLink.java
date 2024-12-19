package org.acme.common.store;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RepositoryLink {
  private final String key;
  private final String publicUrl;

  public Optional<String> getPublicUrl() {
    return Optional.ofNullable(publicUrl);
  }
}