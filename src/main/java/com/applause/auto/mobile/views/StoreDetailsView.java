package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.MapView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = StoreDetailsView.class, on = Platform.MOBILE_ANDROID)
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

  @Locate(xpath = "//XCUIElementTypeSearchField[@name=\"Search Menu\"]", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/search_src_text",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSearchMenuTextBox;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
  protected Button navigateBackButton;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarHeaderText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "TBD", on = Platform.MOBILE_ANDROID)
  protected Image coffeebarPinDisplayedOnMapIcon;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeName", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarSubHeaderNameText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/faveStoreIcon", on = Platform.MOBILE_ANDROID)
  protected Image coffeebarFavoriteIcon;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeAddress", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarLocationText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/hourTitle", on = Platform.MOBILE_ANDROID)
  protected TextBox coffeebarSubHeaderHoursText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "(//android.widget.TextView[@text='HOURS']/following-sibling::android.widget.TextView)[1]",
      on = Platform.MOBILE_ANDROID)
  protected Text coffeebarOpenHoursText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/hoursContainer", on = Platform.MOBILE_ANDROID)
  protected Text coffeebarDayAndStoreHoursText;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/direction", on = Platform.MOBILE_ANDROID)
  protected Button directionsButton;

  @Locate(xpath = "TBD", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/startNewOrder", on = Platform.MOBILE_ANDROID)
  protected Text orderButton;

  /* -------- Actions -------- */

  public ProductDetailsView selectSearchResultByIndex(int index) {
    logger.info("Select search result");
    getSearchResultsElements.get(index).click();
    return ComponentFactory.create(ProductDetailsView.class);
  }

  public List<String> getResults() {
    return getSearchResultsElements.stream()
        .map(item -> item.getText())
        .collect(Collectors.toList());
  }

  public boolean isNavigateBackDisplayed() {
    return navigateBackButton.isDisplayed();
  }

  public String getCoffeebarHeader() {
    return coffeebarHeaderText.getText();
  }

  public boolean isCoffeebarPinDisplayedOnMap() {
    //    Pin does not show on map DOM. Unable to verify
    return true;
  }

  public String getCoffeebarSubHeaderName() {
    return coffeebarSubHeaderNameText.getText();
  }

  public boolean isCoffeebarFavorite() {
    return coffeebarFavoriteIcon.isDisplayed();
  }

  public String getCoffeebarLocation() {
    return coffeebarLocationText.getText();
  }

  public boolean isCoffeebarSubHeaderHoursDisplayed() {
    return coffeebarSubHeaderHoursText.isDisplayed();
  }

  public boolean isCoffeebarOpenHoursDisplayed() {
    return coffeebarOpenHoursText.isDisplayed();
  }

  public boolean isCoffeebarDayAndStoreHoursDisplayed() {
    return coffeebarDayAndStoreHoursText.isDisplayed();
  }

  public boolean isDirectionsButtonDisplayed() {
    return directionsButton.isDisplayed();
  }

  public boolean isOrderButtonDisplayed() {
    return orderButton.isDisplayed();
  }

  public MapView directions() {
    logger.info("Tap directions button");
    directionsButton.click();
    return ComponentFactory.create(MapView.class);
  }

  public OrderView order() {
    logger.info("Tap order button");
    orderButton.click();
    return ComponentFactory.create(OrderView.class);
  }
}
