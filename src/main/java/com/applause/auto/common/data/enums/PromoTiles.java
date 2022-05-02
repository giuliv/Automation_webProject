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
      "ONE OF AMERICA'S BEST LOYALTY PROGRAMS",
      "Newsweek named Peetnik Rewards as one of America's Best Loyalty Programs. Join today to find out why",
      "GET THE APP"),
  promoTile3(
      "Anniversary Blend 2022",
      "Colombia’s cherry & chocolate meet Rwanda’s red fruits in this limited release coffee that’s lush and 100% women-produced. Plus, $1 from each pound sold goes to women farmers at origin.",
      "SHOP NOW"),
  promoTile4(
      "BE ON THE CUTTING EDGE",
      "Every month experience a different limited release, rare microlot coffee from smallholder farms in the world’s best coffee-growing regions.",
      "CUSTOMIZE MY SUBSCRIPTION"),
  promoTile5(
      "DECAF DONE DIFFERENT",
      "All Peet’s beans and K-Cup® pods are decaffeinated by water process. Peet’s uses the same high-quality beans we use in our regular coffees to ensure a decaf with so much richness and depth, it tastes as good as the real thing.",
      "SHOP NOW"),
  promoTile6(
      "MAY’S ONLINE EXCLUSIVE COFFEE",
      "Like liquid sunshine, a honey-sweet coffee with floral aromatics and notes of ripe stone fruit.",
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
