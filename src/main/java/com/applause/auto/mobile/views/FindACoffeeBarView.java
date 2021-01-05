package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;

@Implementation(is = FindACoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = FindACoffeeBarView.class, on = Platform.MOBILE_IOS)
public class FindACoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(iOSClassChain = "**/*[`label == 'Order'`]", on = Platform.MOBILE_IOS)
  protected TextBox orderButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No recent coffeebars.\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/noStoreTitle", on = Platform.MOBILE_ANDROID)
  protected Text titleNoRecentCoffeebars;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"No favorited coffeebars.\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/noStoreTitle", on = Platform.MOBILE_ANDROID)
  protected Text titleNoFavoriteCoffeebars;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected Text messageText;

  @Locate(
      xpath = "//android.widget.TextView[contains(@text,\"Favorites\")]",
      on = Platform.MOBILE_ANDROID)
  @Locate(iOSClassChain = "**/*[`label == 'Favorites'`]", on = Platform.MOBILE_IOS)
  protected TextBox favoritesTab;

  /* -------- Actions -------- */

  /**
   * Open new Order view.
   *
   * @return new order view
   */
  public NewOrderView selectFirstRecentCoffeeBar() {
    logger.info("Select First Recent Coffee Bar");
    orderButton.click();
    return this.create(NewOrderView.class);
  }

  /**
   * Gets coffee store container chuck.
   *
   * @return the coffee store container chuck
   */
  public CoffeeStoreContainerChuck getCoffeeStoreContainerChuck() {
    return this.create(CoffeeStoreContainerChuck.class);
  }

  /**
   * Is title no recent coffeebars displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTitleNoRecentCoffeebarsDisplayed() {
    logger.info("Checking if No Recent coffeebars displayed");
    return titleNoRecentCoffeebars.isDisplayed();
  }

  /**
   * Is title no favorite coffeebars displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTitleNoFavoriteCoffeebarsDisplayed() {
    logger.info("Checking if No Favorite coffeebars displayed");
    return titleNoFavoriteCoffeebars.isDisplayed();
  }

  /**
   * Is message displayed boolean.
   *
   * @param message the message
   * @return the boolean
   */
  public boolean isMessageDisplayed(String message) {
    logger.info("Checking if message displayed: " + message);
    return messageText.format(message).isDisplayed();
  }

  /** Start new order. */
  public void startNewOrder() {}

  /**
   * Open favorites tab find a coffee bar view.
   *
   * @return the find a coffee bar view
   */
  public FindACoffeeBarView openFavoritesTab() {
    logger.info("Tap on Favorites Tab");
    getSyncHelper().sleep(5000);
    favoritesTab.click();
    return this.create(FindACoffeeBarView.class);
  }
}
