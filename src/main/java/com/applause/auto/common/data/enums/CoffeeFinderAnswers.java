package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoffeeFinderAnswers {
  DRIP("Drip"),
  POUR_OVER("Pour Over"),
  I_JUST_KNOW_I_LIKE_IT("I just know I like it"),
  FULL_CAFFEINE("Full caffeine"),
  A_NEW_EVERYDAY_COFFEE("A new everyday coffee");

  @Getter private String value;
}
