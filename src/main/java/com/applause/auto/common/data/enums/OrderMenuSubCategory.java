package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderMenuSubCategory {
  WARM_BREAKFAST("Warm Breakfast"),
  ESPRESSO("Espresso"),
  LATTES("Lattes"),
  BAKED_GOODS("Baked Goods");

  @Getter private String subCategory;
}
