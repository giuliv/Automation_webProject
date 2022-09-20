package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoffeeSubMenu {
  SUBSCRIPTIONS("Curated Subscriptions", "Featured", "subscriptions"),
  BEST_SELLERS("Best Sellers", "Featured", "coffee-best-sellers"),
  EXCLUSIVES("Limited Releases", "Featured", "limited-releases"),
  DECAF("Decaf", "Featured", "decaf-coffee"),
  COFFEE_FINDER("Coold Brew", "Featured", "cold-brew"),

  DARK_ROAST("Dark Roast", "Roast", "dark-roast"),
  MEDIUM_ROAST("Medium Roast", "Roast", "medium-roast"),
  LIGHT_ROAST("Light Roast", "Roast", "light-roast"),

  COFFEE_BEANS("Coffee Beans", "Format", "coffee-beans"),
  KCUP_PODS("K-Cup Pods", "Format", "k-cup-pods"),
  ESPRESSO_CAPSULES("Espresso Capsules", "Format", "espresso-capsules"),

  ALL_COFFEE("All Coffee", "None", "all-coffee");
  @Getter private String text;
  @Getter private String menuLocation;
  @Getter private String link;

  public String getText() {
    return text;
  }

  public String getMenuLocation() {
    return menuLocation;
  }

  public String getLink() {
    return link;
  }
}
