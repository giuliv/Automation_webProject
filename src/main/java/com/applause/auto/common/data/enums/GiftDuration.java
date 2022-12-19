package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GiftDuration {
  MONTHS_3("3 Months"),
  MONTHS_6("6 Months"),
  MONTHS_12("12 Months");

  @Getter private String option;
}
