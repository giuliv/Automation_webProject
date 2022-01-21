package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import org.openqa.selenium.Point;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosDashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(accessibilityId = "Your Feed", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/yourFeedTextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  // @Locate(xpath = "(//*[@name=\"Settings\"])[last()]", on = Platform.MOBILE_IOS)
  // //Commented[15.01.2021]
  @Locate(accessibilityId = "Settings", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/actionMore", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`name == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  protected Button offerTitleText;

  @Locate(accessibilityId = "NO THANKS", on = Platform.MOBILE_ANDROID)
  protected Button dismissFreeDeliveryButton;

  // @Locate(
  // iOSClassChain = "**/XCUIElementTypeButton[`label == \"Store locator\"`]",
  // on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Store locator", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Stores button", on = Platform.MOBILE_ANDROID)
  protected Button locationButton;

  /* -------- Actions -------- */

  public void afterInit() {
    try {
      SdkHelper.getSyncHelper()
          .wait(
              Until.uiElement(dismissFreeDeliveryButton)
                  .present()
                  .setTimeout(Duration.ofSeconds(10)));
      dismissFreeDeliveryButton.click();
    } catch (Throwable throwable) {
      logger.info("No free delivery popup found");
    }
    logger.info(">>>>>" + SdkHelper.getDriver().getPageSource());
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }

  /**
   * Gets account profile menu.
   *
   * @return the account profile menu
   */
  public MoreOptionsView getAccountProfileMenu() {
    logger.info("Open account profile menu");
    getMoreScreenButton.initialize();
    Point elemCoord = getMoreScreenButton.getMobileElement().getCenter();
    AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    new TouchAction(driver).tap(PointOption.point(elemCoord.getX(), elemCoord.getY())).perform();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  /**
   * Gets bottom navigation menu.
   *
   * @return the bottom navigation menu
   */
  public BottomNavigationMenuChunk getBottomNavigationMenu() {
    return SdkHelper.create(BottomNavigationMenuChunk.class);
  }

  /**
   * Look up offer boolean.
   *
   * @param offerName the offer name
   * @return the boolean
   */
  public boolean lookUpOffer(String offerName) {
    int swipeLimit = 10;
    while ((swipeLimit-- != 0) && (!isOfferDisplayed(offerName))) {
      SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
    }
    return swipeLimit != 0;
  }

  private boolean isOfferDisplayed(String offerName) {
    logger.info("Checking if order displayed");
    try {
      offerTitleText.format(offerName);
      return offerTitleText.isDisplayed();
    } catch (Throwable th) {
      return false;
    }
  }

  /**
   * Location t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T location(Class<T> clazz) {
    logger.info("Click on Location button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(locationButton).clickable().setTimeout(Duration.ofSeconds(50)));
    locationButton.click();
    return SdkHelper.create(clazz);
  }
}

class IosDashboardView extends DashboardView {
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }

  @Override
  public MoreOptionsView getAccountProfileMenu() {
    logger.info("Open account profile menu");
    // int x = SdkHelper.getDriver().manage().window().getSize().width;
    // int y = SdkHelper.getDriver().manage().window().getSize().height;

    // AppiumDriver driver = (AppiumDriver) SdkHelper.getDriver();
    // if (!getMoreScreenButton.isDisplayed()) {
    // logger.info("Clicking settings icon from coordinates");
    // new TouchAction(driver).tap(PointOption.point((int) (x * 0.9), (int) (y *
    // 0.05))).perform();
    // } else {
    // logger.info("Clicking settings icon");
    // getMoreScreenButton.click();
    // }

    SdkHelper.getSyncHelper().sleep(8000);
    getMoreScreenButton.initialize();
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getMoreScreenButton).present().setTimeout(Duration.ofSeconds(45)));
    SdkHelper.getDeviceControl().tapElementCenter(getMoreScreenButton);

    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Override
  public <T extends BaseComponent> T location(Class<T> clazz) {
    logger.info("Click on Location button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(locationButton).present().setTimeout(Duration.ofSeconds(50)));
    SdkHelper.getDeviceControl().tapElementCenter(locationButton);
    return SdkHelper.create(clazz);
  }
}
