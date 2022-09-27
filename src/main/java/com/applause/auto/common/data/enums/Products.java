package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Products {
  CARAMEL_LATTE("Caramel Latte"),
  ICED_CINNAMON_ROLL("Iced Cinnamon Roll"),
  CAFFE_LATTE("Caffe Latte"),
  ICED_ESPRESSO("Iced Espresso"),
  NEW_THIS_SEASON("New This Season"),
  HOT_COFFEE("Hot Coffee"),
  HOT_TEA("Hot Tea"),
  COLD_COFFEE("Cold Coffee"),
  ICED_TEA("Iced Tea"),
  ARTINSANAL_FOOD("Artisanal Food"),

  PAPER_FILTERS("Test Peet’s Paper Filters #4");
  @Getter private String value;
}
