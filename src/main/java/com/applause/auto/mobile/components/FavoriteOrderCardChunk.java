package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = FavoriteOrderCardChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = FavoriteOrderCardChunk.class, on = Platform.MOBILE_IOS)
public class FavoriteOrderCardChunk extends BaseComponent {

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@value=\"ORDER:\" and @visible=\"true\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@text=\"ORDER:\" and @resource-id=\"com.wearehathway.peets.development:id/order_header\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text orderTitleText;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Unmark as favorite Order\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageView[@resource-id=\"com.wearehathway.peets.development:id/favoriteIcon\"]",
      on = Platform.MOBILE_ANDROID)
  protected Image favIconImage;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Reorder\" and @visible=\"true\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/reorderButton", on = Platform.MOBILE_ANDROID)
  protected Button reorderButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@value=\"MYVFAV\"]", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/favoriteOrderTitleTextView",
      on = Platform.MOBILE_ANDROID)
  protected Button myFAVHeaderText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@value=\"COFFEEBAR:\"]", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/order_location_header",
      on = Platform.MOBILE_ANDROID)
  protected Button coffeeBarText;

  @Step("Verify Order text displayed")
  public boolean isOrderTextDisplayed() {
    return MobileHelper.isElementDisplayed(orderTitleText, 5);
  }

  @Step("Verify If Favorites icon disabled")
  public boolean isFavIconDisabled() {
    return MobileHelper.isElementDisplayed(favIconImage, 5);
  }

  @Step("Verify Reorder button displayed")
  public boolean isReorderButtonDisplayed() {
    return MobileHelper.isElementDisplayed(reorderButton, 5);
  }

  @Step("Verify My FAV header displayed")
  public boolean isMyFAVHeaderDisplayed() {
    return MobileHelper.isElementDisplayed(myFAVHeaderText, 5);
  }

  @Step("Verify if coffee bar displayed")
  public boolean isCoffeeBarDisplayed() {
    return MobileHelper.isElementDisplayed(coffeeBarText, 5);
  }
}
