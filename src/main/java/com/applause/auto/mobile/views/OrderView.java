package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.OrderMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;

@Implementation(is = AndroidOrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderView.class, on = Platform.MOBILE_IOS)
public class OrderView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      iOSClassChain = "**/XCUIElementTypeOther[$name == 'Pickup'$][-1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/storePinImage\")",
      on = Platform.MOBILE_ANDROID)
  protected Button getLocateCoffeeBars;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name='ORDER']", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]/following-sibling::android.widget.TextView[@text='ORDER']|"
              + "//android.widget.TextView[@text='ORDER']",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeOther[$name == 'Pickup'$][-1]/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/storeTextView\")",
      on = Platform.MOBILE_ANDROID)
  protected Text storeName;

  @Locate(iOSClassChain = "**/XCUIElementTypeButton[`name == 'Back'`]", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator =
          "new UiSelector().resourceIdMatches(\".*id/toolbar\").childSelector(new UiSelector().className(\"android.widget.ImageButton\"))",
      on = Platform.MOBILE_ANDROID)
  protected Text backButton;

  @Locate(xpath = "//XCUIElementTypeButton[@name=\"No Thanks\"]", on = Platform.MOBILE_IOS)
  protected Text noThanksButton;

  /* -------- Actions -------- */
  @Override
  public void afterInit() {
    try {
      SdkHelper.getSyncHelper()
          .wait(Until.uiElement(noThanksButton).present().setTimeout(Duration.ofSeconds(10)));
      noThanksButton.click();
    } catch (Throwable nse) {

    }
  }

  public OrderMenuChunk getOrderMenuChunck() {
    return SdkHelper.create(OrderMenuChunk.class);
  }

  /**
   * Get the text value of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }

  /**
   * Is participating coffeebars displayed boolean.
   *
   * @return the boolean
   */
  public boolean isLocateCoffeeBarsDisplayed() {
    return getLocateCoffeeBars.isDisplayed();
  }

  /**
   * Tap to locate Coffeebars.
   *
   * @return the select coffee bar view by location
   */
  public <T extends BaseComponent> T locateCoffeebars(Class<T> clazz) {
    logger.info("Tap to locate Coffeebars");
    getLocateCoffeeBars.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Gets store name.
   *
   * @return the store name
   */
  public String getStoreName() {
    return storeName.getText();
  }

  public <T extends BaseComponent> T back(Class<T> clazz) {
    backButton.click();
    return SdkHelper.create(clazz);
  }
}

class AndroidOrderView extends OrderView {
  @Override
  public void afterInit() {}
}
