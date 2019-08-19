package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = OrderAheadView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderAheadView.class, on = Platform.MOBILE_IOS)
public class OrderAheadView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "See Participating Coffeebars", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/seeCoffeebarsButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getParticipatingCoffeebarsButton;

  @Locate(id = "Order Ahead.", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().textContains(\"Order Ahead\")",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(id = "Bypass the line and proceed to great coffee.", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//*[@resource-id='com.wearehathway.peets.development:id/topContainer']/android.widget.TextView[2]",
      on = Platform.MOBILE_ANDROID)
  protected Text getSubHeaderText;

  @Locate(id = "Get Started", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/getStartedButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getGetStartedButton;

  @Locate(
      xpath =
          "//XCUIElementTypeOther[1]/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/skipTextView", on = Platform.MOBILE_ANDROID)
  protected Button getSkipButton;

  /* -------- Actions -------- */

  /**
   * Press Get Started button
   *
   * @return
   */
  public AuthenticationView clickGetStartedButton() {
    logger.info("Pressing Get Started button and expected to land at Peetnik Rewards auth screen");
    getGetStartedButton.click();
    return ComponentFactory.create(AuthenticationView.class);
  }

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }

  /**
   * Gets sub header text value.
   *
   * @return the sub header text value
   */
  public String getSubHeaderTextValue() {
    return getSubHeaderText.getText();
  }

  /**
   * Is participating coffeebars displayed boolean.
   *
   * @return the boolean
   */
  public boolean isParticipatingCoffeebarsDisplayed() {
    return getParticipatingCoffeebarsButton.isDisplayed();
  }

  /**
   * Participating coffeebars select coffee bar view.
   *
   * @return the select coffee bar view
   */
  public SelectCoffeeBarView participatingCoffeebars() {
    logger.info("Tap See Participating Coffeebars");
    getParticipatingCoffeebarsButton.click();
    return ComponentFactory.create(SelectCoffeeBarView.class);
  }
}
