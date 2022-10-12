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
      "FOR THOSE WHO CAN’T WAIT",
      "We look forward to each year’s Holiday Blend, and 2022 is no different. All the comfort, warmth, and spice that the season evokes, all in one of extraordinary cup. Who’s ready?",
      "SHOP NOW"),
  promoTile4(
      "LIMITED RELEASE COFFEES SUBSCRIPTION",
      "Subscribe today and experience Peet’s iconic, seasonal coffees—without marking your calendar. For 30% Off your first shipment, use code NEWSUB30",
      "SIGN UP NOW"),
  promoTile5(
      "Free Home Delivery",
      "Why go to the store? Enjoy our freshest coffee delivered directly to your door, exactly when you want it. A Peet's subscription gives you free shipping and access to discounts on all of your coffee purchases. Plus, new subscribers get 30% off their first order using code NEWSUB30.",
      "Learn More"),
  promoTile6(
      "VINE & WALNUT AUTUMN 2022",
      "Taste the artful blend that’s inspired by the flavors of the season, an ode to the 1st Peet’s at the corner of Vine & Walnut in Berkeley.",
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
