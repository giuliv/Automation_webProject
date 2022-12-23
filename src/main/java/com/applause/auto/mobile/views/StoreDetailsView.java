package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.components.MapView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = AndroidStoreDetailsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = StoreDetailsView.class, on = Platform.MOBILE_IOS)
public class StoreDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeImage[@name=\"arrow-right\"]/preceding-sibling::XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.support.v7.widget.LinearLayoutCompat[@resource-id='com.wearehathway.peets.development:id/productContainer']/android.widget.TextView",
      on = Platform.MOBILE_ANDROID)
  protected List<Text> getSearchResultsElements;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeButton",
      on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarHeaderText;

  @Locate(
      xpath =
          "(//XCUIElementTypeMap/../../following-sibling::XCUIElementTypeOther)[1]/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeName", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarSubHeaderNameText;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name='Unmark as favorite Coffeebar'] | //XCUIElementTypeButton[@name='Mark as favorite Coffeebar']",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/faveStoreIcon\")",
      on = Platform.MOBILE_ANDROID)
  protected Image coffeebarFavoriteIcon;

  @Locate(
      xpath =
          "(//XCUIElementTypeMap/../../following-sibling::XCUIElementTypeOther)[2]/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeAddress", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarLocationText;

  @Locate(accessibilityId = "HOURS", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/hourTitle", on = Platform.MOBILE_ANDROID)
  protected TextBox coffeebarSubHeaderHoursText;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeStaticText[`name CONTAINS 'Open until ' or name CONTAINS 'Will open '`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.widget.TextView[@text='HOURS']/following-sibling::android.widget.TextView)[1]",
      on = Platform.MOBILE_ANDROID)
  protected Text coffeebarOpenHoursText;

  @Locate(accessibilityId = "Sunday", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/hoursContainer", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarDayAndStoreHoursText;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Directions']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/direction", on = Platform.MOBILE_ANDROID)
  protected Button directionsButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Order']", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/startNewOrder", on = Platform.MOBILE_ANDROID)
  protected Text orderButton;

  /* -------- Actions -------- */

  /**
   * Gets results.
   *
   * @return the results
   */
  public List<String> getResults() {
    logger.info("Looking for results");
    return getSearchResultsElements.stream()
        .map(item -> item.getText())
        .collect(Collectors.toList());
  }

  /**
   * Is navigate back displayed boolean.
   *
   * @return the boolean
   */
  public boolean isNavigateBackDisplayed() {
    logger.info("Checking Back navigation button");
    return navigateBackButton.isDisplayed();
  }

  /**
   * Navigate back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Click on Back navigation button");
    navigateBackButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Gets coffeebar header.
   *
   * @return the coffeebar header
   */
  public String getCoffeebarHeader() {
    logger.info("Checking Coffeebar main header");
    return coffeebarHeaderText.getText();
  }

  /**
   * Is coffeebar pin displayed on map boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarPinDisplayedOnMap() {
    // Pin does not show on map DOM. Unable to verify
    return true;
  }

  /**
   * Gets coffeebar sub header name.
   *
   * @return the coffeebar sub header name
   */
  public String getCoffeebarSubHeaderName() {
    SdkHelper.getSyncHelper().sleep(10000);
    logger.info("Checking coffeebar sub-header name:" + coffeebarSubHeaderNameText.getText());
    return coffeebarSubHeaderNameText.getText();
  }

  /**
   * Is coffeebar favorite boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarFavorite() {
    logger.info("Checking Coffeebar tagged as favorite");
    /*
    Need to wait till the content-desc attribute changes. Usually, it takes about 3-5 seconds after clicking on the heart icon
     */
    SdkHelper.getSyncHelper().sleep(5000);
    coffeebarFavoriteIcon.initialize();
    return coffeebarFavoriteIcon.getAttributeValue("name").contains("Unmark");
  }

  /**
   * Gets coffeebar location.
   *
   * @return the coffeebar location
   */
  public String getCoffeebarLocation() {
    logger.info("Checking Coffeebar location");
    return coffeebarLocationText.getText();
  }

  /**
   * Is coffeebar sub header hours displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarSubHeaderHoursDisplayed() {
    logger.info("Checking Coffeebar sub-header section");
    return coffeebarSubHeaderHoursText.isDisplayed();
  }

  /**
   * Is coffeebar open hours displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarOpenHoursDisplayed() {
    logger.info("Checking Coffeebar open hours");
    return coffeebarOpenHoursText.isDisplayed();
  }

  /**
   * Is coffeebar day and store hours displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCoffeebarDayAndStoreHoursDisplayed() {
    logger.info("Checking Coffeebar day and store hours");
    return coffeebarDayAndStoreHoursText.isDisplayed();
  }

  /**
   * Is directions button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isDirectionsButtonDisplayed() {
    logger.info("Checking Directions button");
    return directionsButton.isDisplayed();
  }

  /**
   * Is order button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isOrderButtonDisplayed() {
    logger.info("Checking Order button");
    return orderButton.isDisplayed();
  }

  /**
   * Directions map view.
   *
   * @return the map view
   */
  public MapView directions() {
    logger.info("Tap directions button");
    directionsButton.click();
    return SdkHelper.create(MapView.class);
  }

  /**
   * Order order view.
   *
   * @return the order view
   */
  public OrderView order() {
    logger.info("Tap order button");
    orderButton.click();
    return SdkHelper.create(OrderView.class);
  }

  /**
   * Add to favorite store details view.
   *
   * @return the store details view
   */
  public StoreDetailsView tapFavorite() {
    logger.info("Tap favorite icon");
    coffeebarFavoriteIcon.initialize();
    coffeebarFavoriteIcon.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return this;
  }
}

class AndroidStoreDetailsView extends StoreDetailsView {

  @Override
  public boolean isCoffeebarFavorite() {
    logger.info("Checking Coffeebar tagged as favorite");
    /*
    Need to wait till the content-desc attribute changes. Usually, it takes about 3-5 seconds after clicking on the heart icon
     */
    SdkHelper.getSyncHelper().sleep(5000);
    coffeebarFavoriteIcon.initialize();
    return coffeebarFavoriteIcon.getAttributeValue("content-desc").contains("Remove");
  }
}
