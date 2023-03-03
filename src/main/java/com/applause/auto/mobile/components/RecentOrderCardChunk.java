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

@Implementation(is = RecentOrderCardChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = RecentOrderCardChunk.class, on = Platform.MOBILE_IOS)
public class RecentOrderCardChunk extends BaseComponent {

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[contains(@value,'MONDAY') or contains(@value,'TUESDAY') or contains(@value,'WEDNESDAY') or contains(@value,'THURSDAY') or contains(@value,'FRIDAY') or contains(@value,'SATURDAY') or contains(@value,'SUNDAYS')]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/dateTextView", on = Platform.MOBILE_ANDROID)
  protected Text dateHeaderText;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@value=\"Order Completed\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[@text=\"Order completed\" and @resource-id=\"com.wearehathway.peets.development:id/orderStatusTextView\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text orderCompletedText;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[(@label=\"PICKUP AT:\" or @label=\"DELIVERY ADDRESS:\")]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[(@text=\"PICKUP AT:\" or @text=\"DELIVERY ADDRESS:\") and @resource-id=\"com.wearehathway.peets.development:id/order_location_header\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text pickUpAtText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@value=\"ORDER:\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.TextView[@text=\"ORDER:\" and @resource-id=\"com.wearehathway.peets.development:id/order_header\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text orderTitleText;

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"Mark as favorite Order\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageView[@resource-id=\"com.wearehathway.peets.development:id/favoriteIcon\" and @checked=\"false\"]",
      on = Platform.MOBILE_ANDROID)
  protected Image favIconImage;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Reorder\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/reorderButton", on = Platform.MOBILE_ANDROID)
  protected Button reorderButton;

  @Step("Verify Date header displayed")
  public boolean isDateHeaderDisplayed() {
    return MobileHelper.isElementDisplayed(dateHeaderText, 5);
  }

  @Step("Verify Order Completed text displayed")
  public boolean isOrderCompletedTextDisplayed() {
    return MobileHelper.isElementDisplayed(orderCompletedText, 5);
  }

  @Step("Verify Pickup At text displayed")
  public boolean isPickupAtTextDisplayed() {
    return MobileHelper.isElementDisplayed(pickUpAtText, 5);
  }

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
}
