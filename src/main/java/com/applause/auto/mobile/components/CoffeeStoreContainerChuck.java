package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.mobile.views.StoreDetailsView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;

/** The coffee store container chunk. */
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_IOS)
public class CoffeeStoreContainerChuck extends BaseComponent {

  /* -------- Elements -------- */
  @Locate(xpath = "(//XCUIElementTypeButton[@name=\"Order\"])[1]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSearchResultsContainer;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"Order\"])[1]/../XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]"
              + "//*[contains(@resource-id, 'storeName')]",
      on = Platform.MOBILE_ANDROID)
  protected Text getStoreName;

  @Locate(xpath = "(//XCUIElementTypeButton[@name=\"Order\"])[1]", on = Platform.MOBILE_IOS)
  @Locate(id = "acceptMobileOrderIcon", on = Platform.MOBILE_ANDROID)
  protected Button getOrderButton;

  /* -------- Actions -------- */

  /** Swipe search results containers */
  public void swipeCoffeStoreContainer(SwipeDirection swipeDirection) {
    Point getSearchResultsContainerLocation = getSearchResultsContainer.getLocation();
    Dimension getSearchResultsContainerDimension = getSearchResultsContainer.getDimension();
    int offsetValue = 30;
    long millis = 1000;
    if (swipeDirection.equals(SwipeDirection.LEFT)) {
      DeviceControl.swipeAcrossScreenCoordinates(
          getSearchResultsContainerLocation.getX() + offsetValue,
          getSearchResultsContainerLocation.getY() + offsetValue,
          getSearchResultsContainerLocation.getX()
              + getSearchResultsContainerDimension.width
              - offsetValue,
          getSearchResultsContainerLocation.getY() + offsetValue,
          millis);
    }
    if (swipeDirection.equals(SwipeDirection.RIGHT)) {
      DeviceControl.swipeAcrossScreenCoordinates(
          getSearchResultsContainerLocation.getX()
              + getSearchResultsContainerDimension.width
              - offsetValue,
          getSearchResultsContainerLocation.getY() + offsetValue,
          getSearchResultsContainerLocation.getX() + offsetValue,
          getSearchResultsContainerLocation.getY() + offsetValue,
          millis);
    }
    SyncHelper.sleep(3000);
  }

  /**
   * Is store persent
   *
   * @return boolean
   */
  public boolean isStorePresent() {
    return getSearchResultsContainer.isDisplayed();
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
      return ComponentFactory.create(OrderView.class);
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
    getStoreName.click();
    return ComponentFactory.create(StoreDetailsView.class);
  }
}
