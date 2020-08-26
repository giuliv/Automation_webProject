package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

import org.openqa.selenium.Point;

import java.time.Duration;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosDashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Your Feed", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/yourFeedTextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Settings\"]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/actionMore", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  protected Button offerTitleText;

  @Locate(accessibilityId = "NO THANKS", on = Platform.MOBILE_ANDROID)
  protected Button dismissFreeDeliveryButton;

  /* -------- Actions -------- */

  public void afterInit() {
    try {
      dismissFreeDeliveryButton.click();
    } catch (Throwable throwable) {
      logger.info("No free delivery popup found");
    }
    SyncHelper.wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }

  /**
   * Gets account profile menu.
   *
   * @return the account profile menu
   */
  public AccountMenuMobileChunk getAccountProfileMenu() {
    logger.info("Open account profile menu");
    Point elemCoord = getMoreScreenButton.getMobileElement().getCenter();
    AppiumDriver driver = (AppiumDriver) DriverManager.getDriver();
    new TouchAction(driver).tap(PointOption.point(elemCoord.getX(), elemCoord.getY())).perform();
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  /**
   * Gets bottom navigation menu.
   *
   * @return the bottom navigation menu
   */
  public BottomNavigationMenuChunk getBottomNavigationMenu() {
    return ComponentFactory.create(BottomNavigationMenuChunk.class);
  }

  public boolean lookUpOffer(String offerName) {
    int swipeLimit = 10;
    while ((swipeLimit-- != 0) && (!isOfferDisplayed(offerName))) {
      DeviceControl.swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
    }
    return swipeLimit != 0;
  }

  private boolean isOfferDisplayed(String offerName) {
    try {
      offerTitleText.format(offerName);
      return offerTitleText.isDisplayed();
    } catch (Throwable th) {
      return false;
    }
  }
}

class IosDashboardView extends DashboardView {
  public void afterInit() {
    SyncHelper.wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }
}
