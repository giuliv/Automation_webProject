package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.components.BottomNavigationMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Point;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = DashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "Your Feed", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/yourFeedTextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(id = "button more", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/actionMore", on = Platform.MOBILE_ANDROID)
  protected Button getMoreScreenButton;

  /* -------- Actions -------- */

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
}
