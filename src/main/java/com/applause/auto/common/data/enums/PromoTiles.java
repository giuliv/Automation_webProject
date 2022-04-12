package com.applause.auto.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PromoTiles {
  promoTile1(
      "PLANTS FIRST, FOR BREAKFAST",
      "Savor the flavors of Spring. Try our new plant-based beverages and Mediterranean Flatbread.",
      "ORDER NOW"),
  promoTile2(
      "FREE PLANT-BASED FOOD OR BEV FOR NEW MEMBERS",
      "New Members get a free food or beverage exclusively from our new Plant-Based Menu. Get Peet’s app & use PLANTSRULE at signup by 3/31. See app for items in Plant-Based Menu.",
      "DOWNLOAD NOW"),
  promoTile3(
      "Anniversary Blend 2022",
      "Colombia’s cherry & chocolate meet Rwanda’s red fruits in this limited release coffee that’s lush and 100% women-produced. Plus, $1 from each pound sold goes to women farmers at origin.",
      "SHOP NOW"),
  promoTile4(
      "Free Home Delivery",
      "Why go to the store? Enjoy our freshest coffee delivered directly to your door, exactly when you want it. A Peet's subscription gives you free shipping and access to discounts on all of your coffee purchases. Plus, new subscribers get 30% off their first order using code NEWSUB30.",
      "LEARN MORE"),
  promoTile5(
      "Online Exclusive",
      "Limited Release Red Mocha Haraaz is produced at an uncommon level of care. Delicate aromas of cherry blossom, deep wine and fruit notes, and a dry cocoa finish.",
      "SHOP NOW"),
  promoTile6(
      "DECAF DONE DIFFERENT",
      "All Peet’s beans and K-Cup® pods are decaffeinated by water process. Peet’s uses the same high-quality beans we use in our regular coffees to ensure a decaf with so much richness and depth, it tastes as good as the real thing.",
      "SHOP NOW"),
  promoTile7(
      "Shop Coffee", "Hand roasted on demand and delivered fresh to your door.", "All Coffee"),
  promoTile8(
      "Shop Tea", "Savor Mighty Leaf’s tea collection: Different from the start.", "All Tea");

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
