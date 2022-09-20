package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HomepageFreshnessStamp {
  FRESHNESS_STAMP_HEADER(
      "Enjoy the Freshest Peet's You Can Get",
      "Coffee that goes from the roaster to the brewed cup in the shortest amount of time is the freshest, best tasting coffee—period."),
  COMMITTED_TO_CRAFT("Committed to Craft", "Our craft roasters are what make Peet’s special. Our beans are roasted by hand to get you the perfect cup every time."),
  SEALED_FOR_FRESHNESS(
      "Sealed for Freshness", "Sealed after roasting and marked with the Freshness Stamp"),
  DELIVERED_TO_YOU(
      "Delivered Fresh to You", "Your time is precious. Say the word and we ship the world’s best coffee direct from our roastery to your door.");

  @Getter private String title;
  @Getter private String description;

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }
}
