package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;

import java.time.Duration;

@Implementation(is = AndroidNearbySelectCoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = NearbySelectCoffeeBarView.class, on = Platform.MOBILE_IOS)
public class NearbySelectCoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar[@name='Select Coffeebar']",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeContainer", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  @Locate(id = "search magnifier", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSearchButton;

  @Locate(id = "Search for coffeebar text field", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*searchRow\"). childSelector(new UiSelector().classNameMatches(\".*TextView\"))",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchTextBox;

  @Locate(id = "Search for coffeebar edit field", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*searchField\")",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchTextBoxEdit;

  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  @Locate(id = "todo", on = Platform.MOBILE_IOS)
  protected Button getChangeStoreButton;

  /* -------- Actions -------- */

  public CoffeeStoreContainerChuck getCoffeeStoreContainerChuck() {
    return ComponentFactory.create(CoffeeStoreContainerChuck.class);
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
  //  public NewOrderView openCoffeebarFromSearchResults(int index) {
  //    logger.info("Tap on Search result");
  //    int counter = 5;
  //    while (counter-- != 0) {
  //      if (isStorePresent()) {
  //        getSearchResults.click();
  //        break;
  //      } else {
  //        MobileHelper.swipeAcrossScreenCoordinates(0.8, 0.8, 0.2, 0.8, 2000);
  //      }
  //    }
  //    return ComponentFactory.create(NewOrderView.class);
  //  }

}

class AndroidNearbySelectCoffeeBarView extends NearbySelectCoffeeBarView {

  /* -------- Actions -------- */

  public void search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    getSearchTextBox.click();
    SyncHelper.wait(
        Until.uiElement(getSearchTextBoxEdit).present().setTimeout(Duration.ofSeconds(3)));
    getSearchTextBoxEdit.sendKeys(searchTxt);
    SyncHelper.sleep(2000);
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
  }
}
