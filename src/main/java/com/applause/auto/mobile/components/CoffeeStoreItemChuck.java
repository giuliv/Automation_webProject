package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

import org.openqa.selenium.WebDriverException;

/** The coffee store container chunk. */
@Implementation(is = CoffeeStoreItemChuck.class, on = Platform.MOBILE)
@Implementation(is = AndroidCoffeeStoreItemChuck.class, on = Platform.MOBILE_ANDROID)
public class CoffeeStoreItemChuck extends BaseComponent {

  /* -------- Elements -------- */
  @Locate(
      xpath = "./XCUIElementTypeOther/XCUIElementTypeButton/XCUIElementTypeButton[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]"
              + "//*[contains(@resource-id, 'storeName')]",
      on = Platform.MOBILE_ANDROID)
  protected Text getStoreDetailsItem;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Order\" and @visible=\"true\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/order_button", on = Platform.MOBILE_ANDROID)
  protected Button getOrderButton;

  @Locate(id = "com.wearehathway.peets.development:id/storeName", on = Platform.MOBILE_ANDROID)
  protected Text getStoreNameText;

  @Locate(id = "com.wearehathway.peets.development:id/distance", on = Platform.MOBILE_ANDROID)
  protected Text getStoreDistanceText;

  /* -------- Actions -------- */

  /**
   * Get visible coffee shop container name
   *
   * @return String
   */
  public String getStoreName() {
    try {
      getStoreDetailsItem.initialize();
      String storeName = getStoreDetailsItem.getText().split(" - ")[0];
      logger.info(String.format("Store name is: %s", storeName));
      return storeName;
    } catch (WebDriverException e) {
      return null;
    }
  }

  /**
   * click on enabled order button
   *
   * @return OrderView
   */
  public OrderView clickOrderButton() {
    if (getOrderButton.isEnabled()) {
      getOrderButton.click();
      return this.create(OrderView.class);
    }
    throw new IllegalStateException("Order button is not enabled for click");
  }

  /**
   * Is coffeebar distance displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarDistanceDisplayed() {
    logger.info("Verifying if store distance displayed");
    return getStoreDetailsItem.getText().matches("(?s).*\\d miles away");
  }

  /**
   * Is coffeebar open hours displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarOpenHoursDisplayed() {
    logger.info("Verifying if open hours displayed");
    return getStoreDetailsItem.getText().matches("(?s).*\\d:\\d\\d\\S(AM|PM).*");
  }

  /**
   * Swipe left coffee store item chuck.
   *
   * @return the coffee store item chuck
   */
  public CoffeeStoreItemChuck swipeLeft() {
    getDeviceControl().swipeAcrossElementWithDirection(getParent(), SwipeDirection.LEFT);
    getSyncHelper().sleep(2000);
    return this;
  }

  /**
   * Gets xposition.
   *
   * @return the xposition
   */
  public int getXposition() {
    return this.getParent().getMobileElement().getLocation().x;
  }
}

class AndroidCoffeeStoreItemChuck extends CoffeeStoreItemChuck {
  @Override
  public String getStoreName() {
    try {
      getStoreNameText.initialize();
      String storeName = getStoreNameText.getText();
      logger.info(String.format("Store name is: %s", storeName));
      return storeName;
    } catch (WebDriverException e) {
      return null;
    }
  }

  @Override
  public boolean isCoffeebarDistanceDisplayed() {
    logger.info("Verifying if store distance displayed");
    return getStoreDistanceText.getText().matches("(?s).*\\d miles away");
  }
}
