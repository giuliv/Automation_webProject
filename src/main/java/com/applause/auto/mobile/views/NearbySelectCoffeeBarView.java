package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.CoffeeStoreContainerChuck;
import com.applause.auto.mobile.components.CoffeeStoreItemChuck;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.helpers.sync.Until;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import com.applause.auto.framework.SdkHelper;
import java.time.Duration;

/** The type Nearby select coffee bar view. */
@Implementation(is = AndroidNearbySelectCoffeeBarView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = NearbySelectCoffeeBarView.class, on = Platform.MOBILE_IOS)
public class NearbySelectCoffeeBarView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeButton[(@name='ORDER' or @name='Order') and @visible='true']",
      on = Platform.MOBILE_IOS)
  // @Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"ORDER\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeContainer", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignature;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar[@name=\"FIND A COFFEEBAR\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeContainer", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getSignatureForFindCoffee;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Enter Zip or City, State\"]/following-sibling::XCUIElementTypeImage",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/storeSearchIcon",
      on = Platform.MOBILE_ANDROID)
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

  @Locate(xpath = "//android.widget.TextView[@text='Nearby']", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Nearby\"`]",
      on = Platform.MOBILE_IOS)
  protected TextBox nearbyTab;

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

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text titleText;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Close\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button closeButton;

  @Locate(
      xpath = "//XCUIElementTypeButton[(@name='ORDER' or @name='Order') and @visible='true']",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/order_button", on = Platform.MOBILE_ANDROID)
  protected Button orderButton;

  @Locate(
      xpath =
          "//XCUIElementTypeMap/following-sibling::XCUIElementTypeOther/XCUIElementTypeOther[@value='Store']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.view.View[@content-desc='Google Map. Use standard google maps touch gestures to move the map. Location details are located after the map.']/android.view.View[ends-with(@content-desc,'Location. ')]",
      on = Platform.MOBILE_ANDROID)
  protected LazyList<Button> pinsOnMapButton;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"user location\"]/following-sibling::XCUIElementTypeCollectionView/XCUIElementTypeCell",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeDetail", on = Platform.MOBILE_ANDROID)
  protected LazyList<CoffeeStoreItemChuck> coffeeStoreItemChucks;

  @Locate(accessibilityId = "user location", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/directionButton",
      on = Platform.MOBILE_ANDROID)
  protected Button mapLocationButton;

  /* -------- Actions -------- */

  public void afterInit() {
    try {
      logger.info("Click 'No Thanks' popUp, if displayed");
      SdkHelper.getSyncHelper()
          .wait(
              Until.uiElement(dismissFreeDeliveryButton)
                  .present()
                  .setTimeout(Duration.ofSeconds(10)));
      dismissFreeDeliveryButton.click();
    } catch (Throwable throwable) {
      logger.info("No free delivery popup found");
    }

    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
    } catch (Throwable throwable) {
      SdkHelper.getSyncHelper()
          .wait(
              Until.uiElement(getSignatureForFindCoffee)
                  .present()
                  .setTimeout(Duration.ofSeconds(45)));
      logger.info("You are on Find CoffeeBar Section");
    }
  }

  /**
   * Gets coffee store container chuck.
   *
   * @return the coffee store container chuck
   */
  public CoffeeStoreContainerChuck getCoffeeStoreContainerChuck() {
    return SdkHelper.create(CoffeeStoreContainerChuck.class);
  }

  /**
   * Gets coffee store container chucks.
   *
   * @return the coffee store container chucks
   */
  public LazyList<CoffeeStoreItemChuck> getCoffeeStoreContainerChucks() {
    return coffeeStoreItemChucks;
  }

  /**
   * Search.
   *
   * @param searchTxt the search txt
   */
  public NearbySelectCoffeeBarView search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    logger.info("" + SdkHelper.getDriver().getPageSource());
    getSearchTextBox.click();
    getSearchTextBox.initialize();
    getSearchTextBox.sendKeys(searchTxt + "\n");
    return SdkHelper.create(NearbySelectCoffeeBarView.class);
  }

  /** Allow Location Service. */
  public void allow() {
    logger.info("Allow Location Services");
    try {
      getLocationServicesAllowBtn.click();
      SdkHelper.getSyncHelper().sleep(2000);
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
    SdkHelper.getSyncHelper().sleep(5000);
    recentTab.click();
    return SdkHelper.create(FindACoffeeBarView.class);
  }

  /**
   * Open favorites tab find a coffee bar view.
   *
   * @return the find a coffee bar view
   */
  public FindACoffeeBarView openFavoritesTab() {
    logger.info("Tap on Favorites Tab");
    SdkHelper.getSyncHelper().sleep(5000);
    favoritesTab.click();
    return SdkHelper.create(FindACoffeeBarView.class);
  }

  /** Cancel search. */
  public void cancelSearch() {
    logger.info("Tap on Cancel search");
    cancelSearchButton.click();
    cancelSearchButton.click();
  }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    logger.info("Getting title...");
    return titleText.getText();
  }

  /**
   * Is close button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCloseButtonDisplayed() {
    logger.info("Check if close button displayed");
    return closeButton.isDisplayed();
  }

  /**
   * Is search field displayed boolean.
   *
   * @return the boolean
   */
  public boolean isSearchFieldDisplayed() {
    logger.info("Check if search field displayed");
    return getSearchTextBox.isDisplayed();
  }

  /**
   * Is search icon displayed boolean.
   *
   * @return the boolean
   */
  public boolean isSearchIconDisplayed() {
    logger.info("Check if search button displayed");
    return getSearchButton.exists();
  }

  /**
   * Is nearby tab displayed boolean.
   *
   * @return the boolean
   */
  public boolean isNearbyTabDisplayed() {
    logger.info("Check if NearBy tab displayed");
    return nearbyTab.isDisplayed();
  }

  /**
   * Is recents tab displayed boolean.
   *
   * @return the boolean
   */
  public boolean isRecentsTabDisplayed() {
    logger.info("Check if Recents tab displayed");
    return recentTab.isDisplayed();
  }

  /**
   * Is favorites tab displayed boolean.
   *
   * @return the boolean
   */
  public boolean isFavoritesTabDisplayed() {
    logger.info("Check if Favorite tab displayed");
    return favoritesTab.isDisplayed();
  }

  /**
   * Select store.
   *
   * @param index the index
   */
  public void selectStore(int index) {
    logger.info("Click on Store's pin: " + index);
    pinsOnMapButton.get(index).click();
  }

  /** Location. */
  public <T extends BaseComponent> T location(Class<T> clazz) {
    logger.info("Click on store location button");
    mapLocationButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Gets pins count.
   *
   * @return the pins count
   */
  public int getPinsCount() {
    logger.info("Getting pins on map quantity");
    return pinsOnMapButton.size();
  }

  /**
   * Close t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T close(Class<T> clazz) {
    logger.info("Click <Close> button");
    closeButton.click();
    return SdkHelper.create(clazz);
  }

  public NewOrderView openDefault() {
    logger.info("Open default store");
    orderButton.click();
    return SdkHelper.create(NewOrderView.class);
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
  public NearbySelectCoffeeBarView search(String searchTxt) {
    logger.info("Searching for store: " + searchTxt);
    getSearchTextBox.click();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSearchTextBoxEdit).present().setTimeout(Duration.ofSeconds(3)));
    getSearchTextBoxEdit.sendKeys(searchTxt);
    SdkHelper.getSyncHelper().sleep(2000);
    AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
    SdkHelper.getSyncHelper().sleep(7000);
    return SdkHelper.create(NearbySelectCoffeeBarView.class);
  }
}
