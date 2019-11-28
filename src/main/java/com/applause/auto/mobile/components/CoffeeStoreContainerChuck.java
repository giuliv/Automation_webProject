package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.views.OrderView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;

/** The coffee store container chunk. */
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = CoffeeStoreContainerChuck.class, on = Platform.MOBILE_IOS)
public class CoffeeStoreContainerChuck extends BaseComponent {

  /* -------- Elements -------- */
  @Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[%s]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[contains(@resource-id, 'com.wearehathway.peets.development:id/storeDetail') and descendant::*[contains(@resource-id,'storeDetailContainer')]]",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSearchResultsContainer;

  // TODO for iOS
  @Locate(xpath = "", on = Platform.MOBILE_IOS)
  @Locate(id = "storeName", on = Platform.MOBILE_ANDROID)
  protected Text getStoreName;

  // TODO for iOS
  @Locate(xpath = "", on = Platform.MOBILE_IOS)
  @Locate(id = "acceptMobileOrderIcon", on = Platform.MOBILE_ANDROID)
  protected Button getOrderButton;

  /* -------- Actions -------- */

  /** Swipe search results containers to the left */
  public void swipeCoffeeStoreContainerLeft() {
    DeviceControl.swipeAcrossScreenCoordinates(
        getSearchResultsContainer.getLocation().getX() + 30,
        getSearchResultsContainer.getLocation().getY() + 30,
        getSearchResultsContainer.getLocation().getX()
            + getSearchResultsContainer.getDimension().width
            - 30,
        getSearchResultsContainer.getLocation().getY() + 30,
        1000);
    SyncHelper.sleep(2000);
  }

  /** Swipe search results containers to the right */
  public void swipeCoffeeStoreContainerRight() {
    DeviceControl.swipeAcrossScreenCoordinates(
        getSearchResultsContainer.getLocation().getX()
            + getSearchResultsContainer.getDimension().width
            - 30,
        getSearchResultsContainer.getLocation().getY() + 30,
        getSearchResultsContainer.getLocation().getX() + 30,
        getSearchResultsContainer.getLocation().getY() + 30,
        1000);
    SyncHelper.sleep(2000);
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
    logger.info(String.format("Store name is: %s", getStoreName.getText()));
    return getStoreName.getText();
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
}
