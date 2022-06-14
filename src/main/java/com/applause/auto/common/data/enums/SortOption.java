package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SortOption {
  LOWEST_TO_HIGHEST_RATING("Lowest to Highest Rating", "negative");
  @Getter private String name;
  @Getter private String value;
}
