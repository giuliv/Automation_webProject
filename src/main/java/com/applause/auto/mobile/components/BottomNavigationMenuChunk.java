package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.PeetsCardsView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = BottomNavigationMenuChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = BottomNavigationMenuChunk.class, on = Platform.MOBILE_IOS)
public class BottomNavigationMenuChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Home\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[contains(@text,\"Home\") or contains(@content-desc,\"Home\")]/..",
      on = Platform.MOBILE_ANDROID)
  protected Button getHomeButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Peet's Card\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\" and (contains(@text,\"Peet's Card\") or contains(@content-desc,\"Peet's Card\"))]",
      on = Platform.MOBILE_ANDROID)
  protected Button getPeetsCardsButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Order\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@content-desc=\"Order, tab, 3 of 5\"]/android.widget.ImageView",
      on = Platform.MOBILE_ANDROID)
  protected Button getOrdersButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"Check In\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id=\"com.wearehathway.peets.development:id/bottom_navigation_container\"]/android.widget.TextView[contains(@text,\"Check In\") or contains(@content-desc,\"Check In\")]/..",
      on = Platform.MOBILE_ANDROID)
  protected Button getCheckInButton;

  @Locate(xpath = "//android.widget.Button[@text=\"DISMISS\"]", on = Platform.MOBILE_ANDROID)
  protected Button getDismissButton;

  @Locate(id = "com.wearehathway.peets.development:id/changeTextView", on = Platform.MOBILE_ANDROID)
  @Locate(id = "todo", on = Platform.MOBILE_IOS)
  protected Button getChangeStoreButton;

  @Locate(id = "Allow", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/allowButton", on = Platform.MOBILE_ANDROID)
  protected Button getAllowButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`name == 'Menu'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/menuButton\")",
      on = Platform.MOBILE_ANDROID)
  protected Button menuButton;

  /* -------- Actions -------- */

  /**
   * Peets cards peets cards view.
   *
   * @return the peets cards view
   */
  /*
   * Public actions
   */
  public PeetsCardsView peetsCards() {
    logger.info("Tap on Peets Cards");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getPeetsCardsButton).clickable().setTimeout(Duration.ofSeconds(50)));
    getPeetsCardsButton.click();
    return SdkHelper.create(PeetsCardsView.class);
  }

  /**
   * Home peets cards view.
   *
   * @return the peets cards view
   */
  public HomeView home() {
    logger.info("Tap on Home");
    getHomeButton.click();
    return SdkHelper.create(HomeView.class);
  }

  /**
   * Order Ahead view
   *
   * @param clazz
   * @param <T>
   * @return clazz
   */
  public <T extends BaseComponent> T order(Class<T> clazz) {
    logger.info("Tap on Order");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getOrdersButton).visible().setTimeout(Duration.ofSeconds(30)));
    getOrdersButton.click();
    return SdkHelper.create(clazz);
  }

  @Step("Tap on 'Menu' button")
  public <T extends BaseComponent> T tapMenu(Class<T> clazz) {
    logger.info("Tapping on 'Menu' button");
    menuButton.click();
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.create(FreeDeliveryModalChunk.class).dismissFreeDelivery();
    }
    return SdkHelper.create(clazz);
  }
}
