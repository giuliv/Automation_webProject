package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Products {
  CARAMEL_LATTE("Caramel Latte"),
  ICED_CINNAMON_ROLL("Iced Cinnamon Roll"),
  CAFFE_LATTE("Caffe Latte"),
  ICED_ESPRESSO("Iced Espresso"),
  NEW_SEASONAL_BEVERAGES("New Seasonal Beverages"),
  HOT_COFFEE("Hot Coffee"),
  HOT_TEA("Hot Tea"),
  COLD_COFFEE("Cold Coffee"),
  ICED_TEA("Iced Tea"),
  ARTINSANAL_FOOD("Artisanal Food");
  @Getter private String value;
}