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
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import org.openqa.selenium.Point;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosDashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Your Feed", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/yourFeedTextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(xpath = "(//*[@name=\"Settings\"])[last()]", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/actionMore", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text='%s']", on = Platform.MOBILE_ANDROID)
  protected Button offerTitleText;

  @Locate(accessibilityId = "NO THANKS", on = Platform.MOBILE_ANDROID)
  protected Button dismissFreeDeliveryButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Store locator\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Stores button", on = Platform.MOBILE_ANDROID)
  protected Button locationButton;

  /* -------- Actions -------- */

  public void afterInit() {
    try {
      dismissFreeDeliveryButton.click();
    } catch (Throwable throwable) {
      logger.info("No free delivery popup found");
    }
    logger.info(">>>>>" + getDriver().getPageSource());
    getSyncHelper()
        .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }

  /**
   * Gets account profile menu.
   *
   * @return the account profile menu
   */
  public AccountMenuMobileChunk getAccountProfileMenu() {
    logger.info("Open account profile menu\n" + getDriver().getPageSource());
    getMoreScreenButton.initialize();
    Point elemCoord = getMoreScreenButton.getMobileElement().getCenter();
    AppiumDriver driver = (AppiumDriver) getDriver();
    new TouchAction(driver).tap(PointOption.point(elemCoord.getX(), elemCoord.getY())).perform();
    getSyncHelper().sleep(5000);
    return this.create(AccountMenuMobileChunk.class);
  }

  /**
   * Gets bottom navigation menu.
   *
   * @return the bottom navigation menu
   */
  public BottomNavigationMenuChunk getBottomNavigationMenu() {
    return this.create(BottomNavigationMenuChunk.class);
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
      getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.LEFT);
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
    locationButton.click();
    return this.create(clazz);
  }
}

class IosDashboardView extends DashboardView {
  public void afterInit() {
    getSyncHelper()
        .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
  }

  public AccountMenuMobileChunk getAccountProfileMenu() {
    logger.info("Open account profile menu\n" + getDriver().getPageSource());
    int x = getDriver().manage().window().getSize().width;
    int y = getDriver().manage().window().getSize().height;
    AppiumDriver driver = (AppiumDriver) getDriver();
    if (!getMoreScreenButton.isDisplayed()) {
      new TouchAction(driver).tap(PointOption.point((int) (x * 0.9), (int) (y * 0.05))).perform();
    } else {
      getMoreScreenButton.click();
    }
    getSyncHelper().sleep(5000);
    return this.create(AccountMenuMobileChunk.class);
  }
}
