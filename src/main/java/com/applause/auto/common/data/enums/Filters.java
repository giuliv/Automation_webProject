package com.applause.auto.common.data.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Filters {
  ROAST("Roast", Arrays.asList("Dark", "Medium", "Light")),
  BREWING_METHOD(
      "Brewing Method",
      Arrays.asList(
          "Drip",
          "Pour Over",
          "Press Pot",
          "Espresso",
          "Cold Brew",
          "K-Cup® Pod",
          "Espresso Capsule")),
  CAFFEINE("Caffeine", Arrays.asList("Regular", "Decaf", "Half-Caf")),
  FLAVOR(
      "Flavor",
      Arrays.asList(
          "Spicy / Complex",
          "Nutty / Chocolate",
          "Bright / Citrus / Sweet",
          "Fruity / Floral",
          "Herbal / Earthy",
          "Flavored K-Cup® Pods")),
  REGION("Region", Arrays.asList("Americas", "Africa/Arabia", "Indo-Pacific")),
  FEATURED(
      "Featured",
      Arrays.asList(
          "Signature Blend", "Limited Release", "Online Exclusive", "Organic", "Single Origin")),
  TEA_BREWING_METHOD("Brewing Method", Collections.emptyList()),
  TEA_TYPE("Tea Type", Collections.emptyList()),
  TEA_CAFFEINE(
      "Caffeine",
      Arrays.asList(
          "Very Light Caffeine",
          "Light Caffeine",
          "Moderate Caffeine",
          "No Caffeine",
          "Decaffeinated")),
  TEA_REGION(
      "Region",
      Arrays.asList(
          "Mighty Leaf Blend", "China", "Japan", "India & Sri Lanka", "India", "South Africa"));
  @Getter private String name;
  @Getter private List<String> options;
}
