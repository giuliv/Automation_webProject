package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PromoTiles {
  promoTile1(
      "NOW DROPPING",
      "It's never too early to enjoy the autumnal sweetness of caramel apples layered with Espresso Forte®—try our NEW Caramel Apple Latte, available iced or hot.",
      "ORDER NOW"),
  promoTile2(
      "ONE OF AMERICA'S BEST LOYALTY PROGRAMS",
      "Newsweek named Peetnik Rewards as one of America's Best Loyalty Programs. Join today to find out why",
      "GET THE APP"),
  promoTile3(
      "HOLIDAY BLEND PREVIEW ROAST",
      "an entire year in the making, 2022 holiday blend is brimming with velvety chocolate, candied zest & holiday spice. early access for any peet’s subscriber (and a great excuse to subscribe if you haven’t already).",
      "SUBSCRIBE TO BUY"),
  promoTile4(
      "VINE & WALNUT AUTUMN 2022",
      "Taste the artful blend that’s inspired by the flavors of the season, an ode to the 1st Peet’s at the corner of Vine & Walnut in Berkeley.",
      "SHOP NOW"),
  promoTile5(
      "BE ON THE CUTTING EDGE",
      "Every month experience a different limited release, rare microlot coffee from smallholder farms in the world’s best coffee-growing regions.",
      "CUSTOMIZE MY SUBSCRIPTION"),
  promoTile6(
      "GROUND CONTROL TO MAJOR DICKASON’S",
      "Elevate your coffee experience with this bold blend, a bestseller since 1969.",
      "SHOP NOW"),
  promoTile7(
      "Shop Coffee", "Hand roasted on demand and delivered fresh to your door.", "ALL COFFEE"),
  promoTile8(
      "Shop Tea", "Savor Mighty Leaf’s tea collection: Different from the start.", "ALL TEA");

  @Getter private String Title;
  @Getter private String Description;
  @Getter private String Actions;

  public String getTitle() {
    return Title;
  }

  public String getDescription() {
    return Description;
  }

  public String getActions() {
    return Actions;
  }
}
