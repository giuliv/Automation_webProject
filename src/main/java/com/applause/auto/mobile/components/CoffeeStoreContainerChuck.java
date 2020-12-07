package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.StoreDetailsView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import org.openqa.selenium.WebDriverException;

/** The coffee store container chunk. */
@Implementation(is = AndroidCoffeeStoreContainerChuck.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_IOS)
public class CoffeeStoreContainerChuck extends BaseComponent {

  /* -------- Elements -------- */
  @Locate(
      xpath =
          "(//XCUIElementTypeButton[(@name=\"Order\" or @name=\"Reorder\") and @visible=\"true\"])[last()]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSearchResultsContainer;

  @Locate(
      xpath =
          "(//XCUIElementTypeButton[@name=\"Order\" and @visible=\"true\"])[1]/../XCUIElementTypeButton[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]"
              + "//*[contains(@resource-id, 'storeName')]",
      on = Platform.MOBILE_ANDROID)
  protected Text getStoreName;

  @Locate(
      xpath =
          //
          // "//XCUIElementTypeNavigationBar[@name='Order']/following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText[@value='Pickup at:']/following-sibling::XCUIElementTypeStaticText[contains(@name,'%s')]",
          "//*[contains(normalize-space(@name),'%s') and @visible='true']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[contains(@resource-id,'storeDetail')]//android.widget.TextView[contains(@text,'%s')]",
      on = Platform.MOBILE_ANDROID)
  protected Text getStoreDetailsItem;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Order\" and @visible=\"true\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/order_button", on = Platform.MOBILE_ANDROID)
  protected Button getOrderButton;

  /* -------- Actions -------- */

  /**
   * Is store persent
   *
   * @return boolean
   */
  public boolean isStorePresent() {
    try {
      return getSearchResultsContainer.isDisplayed();
    } catch (Throwable th) {
      return false;
    }
  }

  /**
   * Get visible coffee shop container name
   *
   * @return String
   */
  public String getStoreName() {
    try {
      String storeName = getStoreName.getText();
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
   * Open store details store details view.
   *
   * @return the store details view
   */
  public StoreDetailsView openStoreDetails() {
    logger.info("Tap on store name");
    getStoreName.initialize();
    getStoreName.click();
    return this.create(StoreDetailsView.class);
  }

  /**
   * Is coffeebar store name displayed boolean.
   *
   * @param storeName the store name
   * @return the boolean
   */
  public boolean isCoffeebarStoreNameDisplayed(String storeName) {
    logger.info("Verifying if store name displayed: " + storeName);
    getStoreDetailsItem.format(storeName);
    getStoreDetailsItem.initialize();
    return getStoreDetailsItem.isDisplayed();
  }

  /**
   * Is coffeebar distance displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarDistanceDisplayed() {
    logger.info("Verifying if store distance displayed");
    getStoreDetailsItem.format("miles away");
    getStoreDetailsItem.initialize();
    return getStoreDetailsItem.isDisplayed();
  }

  /**
   * Is coffeebar location displayed boolean.
   *
   * @param address the address
   * @return the boolean
   */
  public boolean isCoffeebarLocationDisplayed(String address) {
    logger.info("Verifying if store location displayed");
    getStoreDetailsItem.format(address.replace("\n", " "), address.replace("\n", " "));
    getStoreDetailsItem.initialize();
    return getStoreDetailsItem.isDisplayed();
  }

  /**
   * Is coffeebar open hours displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarOpenHoursDisplayed() {
    logger.info("Verifying if open hours displayed");
    getStoreDetailsItem.format("Open until");
    getStoreDetailsItem.initialize();
    return getStoreDetailsItem.isDisplayed();
  }

  /**
   * Is coffeebar order button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarOrderButtonDisplayed() {
    logger.info("Verifying if Order button displayed");
    getOrderButton.initialize();
    return getOrderButton.isDisplayed();
  }
}

class AndroidCoffeeStoreContainerChuck extends CoffeeStoreContainerChuck {

  @Override
  public boolean isCoffeebarLocationDisplayed(String address) {
    logger.info("Verifying if store location displayed");
    getStoreDetailsItem.format(address, address);
    getStoreDetailsItem.initialize();
    return getStoreDetailsItem.isDisplayed();
  }
}
