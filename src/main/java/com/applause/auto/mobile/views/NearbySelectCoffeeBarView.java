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


import com.applause.auto.pageobjectmodel.helper.sync.Until;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import java.time.Duration;

@Implementation(is = AndroidNearbySelectCoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = NearbySelectCoffeeBarView.class, on = Platform.MOBILE_IOS)
public class NearbySelectCoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@value='1' and @name='Nearby'] | //XCUIElementTypeSearchField[@name='Enter Zip or City, State']",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeContainer", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  @Locate(id = "search magnifier", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSearchButton;

  @Locate(id = "Enter Zip or City, State", on = Platform.MOBILE_IOS)
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

  @Locate(iOSNsPredicate = "name=Not Now", on = Platform.MOBILE_IOS)
  protected Button getLocationServicesNotAllowBtn;

  @Locate(iOSNsPredicate = "name='Allow'", on = Platform.MOBILE_IOS)
  protected Button getLocationServicesAllowBtn;

  @Locate(id = "Allow While Using App", on = Platform.MOBILE_IOS)
  protected Button allowWhileUsing;

  @Locate(xpath = "//android.widget.TextView[@text='Recents']", on = Platform.MOBILE_ANDROID)
  @Locate(iOSClassChain = "**/*[`label == 'Recents'`]", on = Platform.MOBILE_IOS)
  protected TextBox recentTab;

  @Locate(
      xpath = "//android.widget.TextView[contains(@text,\"Favorites\")]",
      on = Platform.MOBILE_ANDROID)
  @Locate(iOSClassChain = "**/*[`label == 'Favorites'`]", on = Platform.MOBILE_IOS)
  protected TextBox favoritesTab;

  @Locate(id = "com.wearehathway.peets.development:id/cancelButton", on = Platform.MOBILE_ANDROID)
  @Locate(iOSClassChain = "**/*[`label == 'Cancel'`]", on = Platform.MOBILE_IOS)
  protected Button cancelSearchButton;

  @Locate(accessibilityId = "No Thanks", on = Platform.MOBILE_IOS)
  protected Button dismissFreeDeliveryButton;

  /* -------- Actions -------- */

  public void afterInit() {
    try {
      dismissFreeDeliveryButton.click();
    } catch (Throwable throwable) {
      logger.info("No free delivery popup found");
    }
    getSyncHelper().wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
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
   * Search.
   *
   * @param searchTxt the search txt
   */
  public void search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    logger.info("" + getDriver().getPageSource());
    getSearchTextBox.click();
    getSearchTextBox.initialize();
    getSearchTextBox.sendKeys(searchTxt + "\n");
  }

  /** Allow Location Service. */
  public void allow() {
    logger.info("Allow Location Services");
    try {
      getLocationServicesAllowBtn.click();
      getSyncHelper().sleep(2000);
      allowWhileUsing.click();
    } catch (Throwable throwable) {
      logger.error("Could not Allow for Location Service");
    }
  }

  /**
   * Open recent tab
   *
   * @return
   */
  public FindACoffeeBarView openRecentTab() {
    logger.info("Tap on Recent Tab");
    getSyncHelper().sleep(5000);
    recentTab.click();
    return this.create(FindACoffeeBarView.class);
  }

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

  /** Cancel search. */
  public void cancelSearch() {
    logger.info("Tap on Cancel search");
    cancelSearchButton.click();
    cancelSearchButton.click();
  }
}

class AndroidNearbySelectCoffeeBarView extends NearbySelectCoffeeBarView {

  /* -------- Actions -------- */
  @Override
  public void afterInit() {}

  @Override
  public void cancelSearch() {
    logger.info("Do nothing for android");
  }

  @Override
  public void search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    getSearchTextBox.click();
    getSyncHelper().wait(
        Until.uiElement(getSearchTextBoxEdit).present().setTimeout(Duration.ofSeconds(3)));
    getSearchTextBoxEdit.sendKeys(searchTxt);
    getSyncHelper().sleep(2000);
    AppiumDriver driver = (AppiumDriver) getDriver();
    driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
  }
}
