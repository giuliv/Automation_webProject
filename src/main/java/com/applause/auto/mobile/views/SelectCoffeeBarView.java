package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.AllowLocationServicesPopupChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;

@Implementation(is = AndroidSelectCoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SelectCoffeeBarView.class, on = Platform.MOBILE_IOS)
public class SelectCoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar[@name='Select Coffeebar']",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/storeContainer",
      on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Enable Location\"]/preceding-sibling::*",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/noStoreMessage", on = Platform.MOBILE_ANDROID)
  protected Text getEnableLocationDescriptionText;

  @Locate(id = "Enable Location", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/enableLocationServiceButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getEnableLocationButton;

  @Locate(id = "search magnifier", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSearchButton;

  @Locate(id = "Search for coffeebar", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/searchField", on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchTextBox;

  @Locate(xpath = "//XCUIElementTypeTable/XCUIElementTypeCell[%s]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/storeDetail'])[%s]",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchResultText;

  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  @Locate(id = "todo", on = Platform.MOBILE_IOS)
  protected Button getChangeStoreButton;

  /* -------- Actions -------- */

  /**
   * Gets enable location description.
   *
   * @return the enable location description
   */
  public String getEnableLocationDescription() {
    return getEnableLocationDescriptionText.getText();
  }

  /**
   * Is enable location button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isEnableLocationButtonDisplayed() {
    return getEnableLocationButton.isDisplayed();
  }

  /**
   * Enable location allow location services popup chunk.
   *
   * @return the allow location services popup chunk
   */
  public AllowLocationServicesPopupChunk enableLocation() {
    logger.info("Tap enable location button");
    getEnableLocationButton.click();
    return ComponentFactory.create(AllowLocationServicesPopupChunk.class);
  }

  /**
   * Search.
   *
   * @param searchTxt the search txt
   */
  public void search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    try {
      getChangeStoreButton.click();
    } catch (Throwable throwable) {
      logger.info("Unable to Change Store");
    }
    getSearchButton.click();
    getSearchTextBox.sendKeys(searchTxt + "\n");
  }

  /**
   * Open coffeebar from search results new order view.
   *
   * @param index the index
   * @return the new order view
   */
  public NewOrderView openCoffeebarFromSearchResults(int index) {
    logger.info("Tap on Search result" );
    int counter = 5;
    while (counter-- != 0) {
      if (getSearchResultText.isDisplayed()) {
        getSearchResultText.click();
        break;
      } else {
        MobileHelper.swipeAcrossScreenCoordinates(0.8, 0.8,
                0.2, 0.8, 2000);
      }
    }
    return ComponentFactory.create(NewOrderView.class);
  }

  public boolean isStoresDisplayed() {
    return getSearchResultText.isDisplayed();
  }
}

class AndroidSelectCoffeeBarView extends SelectCoffeeBarView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    // Workaround for Automator hang
    try {
      ComponentFactory.create(AllowLocationServicesPopupChunk.class, "").notNow();
    } catch (AssertionError ase) {
      logger.warn("No popup found");
    }
    SyncHelper.wait(Until.uiElement(getSignature).present());
  }

  /* -------- Actions -------- */

  public void search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    getChangeStoreButton.click();
    getSearchButton.click();
    getSearchTextBox.sendKeys(searchTxt);
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
  }

  public AllowLocationServicesPopupChunk enableLocation() {
    logger.info("Tap enable location button");
    getEnableLocationButton.click();
    return ComponentFactory.create(AllowLocationServicesPopupChunk.class, "");
  }
}
