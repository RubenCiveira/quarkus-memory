package org.acme.features.market.color.domain.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorActionType {

  /**
   * @autogenerated ActionTypeGenerator
   */
  CREATE(true, false, false),
  /**
   * @autogenerated ActionTypeGenerator
   */
  UPDATE(false, false, true),
  /**
   * @autogenerated ActionTypeGenerator
   */
  DELETE(false, true, false);

  /**
   * @autogenerated ActionTypeGenerator
   */
  private final boolean create;

  /**
   * @autogenerated ActionTypeGenerator
   */
  private final boolean delete;

  /**
   * @autogenerated ActionTypeGenerator
   */
  private final boolean update;
}