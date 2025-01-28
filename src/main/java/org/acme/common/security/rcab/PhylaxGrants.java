package org.acme.common.security.rcab;

import java.util.Map;
import java.util.Set;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class PhylaxGrants {

  private String rolename;

  private Set<String> allowedScopes;

  private Map<String, Set<String>> restrictedFields;
}
