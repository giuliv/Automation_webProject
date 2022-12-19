package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GrindDropdown {
  DRIP("Drip"),
  WHOLE_BEAN("Whole Bean");

  @Getter private String value;
}
