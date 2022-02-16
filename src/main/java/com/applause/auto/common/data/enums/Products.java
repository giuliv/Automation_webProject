package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Products {
  MAPLE_LATTE("Maple Latte"),
  ICED_CINNAMON_ROLL("Iced Cinnamon Roll"),
  CAFFE_LATTE("Caffe Latte"),
  ICED_ESPRESSO("Iced Espresso");

  @Getter private String value;
}
