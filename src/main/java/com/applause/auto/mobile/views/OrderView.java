package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.components.OrderMenuChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;

@Implementation(is = OrderView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderView.class, on = Platform.MOBILE_IOS)
public class OrderView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath =
          "//XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storePinImage", on = Platform.MOBILE_ANDROID)
  protected Button getLocateCoffeeBars;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name='ORDER']", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]/following-sibling::android.widget.TextView[@text='ORDER']|"
              + "//android.widget.TextView[@text='ORDER']",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Change\"]/preceding-sibling::XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storeTextView", on = Platform.MOBILE_ANDROID)
  protected Text storeName;

  /* -------- Actions -------- */

  public OrderMenuChunk getOrderMenuChunck() {
    return ComponentFactory.create(OrderMenuChunk.class);
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
    return ComponentFactory.create(clazz);
  }

  public String getStoreName() {
    return storeName.getText();
  }
}
