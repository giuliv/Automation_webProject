package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HomepageFreshnessStamp {
  FRESHNESS_STAMP_HEADER(
      "Always Look for the Freshness Stamp",
      "Coffee that goes from the roaster to the brewed cup in the shortest amount of time is the freshest, best tasting coffee—period."),
  COFFEE_STAMP("Hand Roasted to Order", "Our beans aren’t roasted until you order them"),
  SEALED_FOR_FRESHNESS(
      "Sealed for Freshness", "Sealed after roasting and marked with the Freshness Stamp"),
  DELIVERED_TO_YOU(
      "Delivered Fresh to You", "Coffees ship the same day they are roasted for peak flavor");

  @Getter private String title;
  @Getter private String description;

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }
}
