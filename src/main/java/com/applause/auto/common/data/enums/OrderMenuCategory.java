package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderMenuCategory {
  FOOD("Food"),
  HOT_COFFEE("Hot Coffee");

  @Getter private String category;
}
