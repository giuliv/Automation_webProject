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

  @Locate(id = "See Participating Coffeebars", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/storePinImage", on = Platform.MOBILE_ANDROID)
  protected Button getLocateCoffeeBars;

  @Locate(id = "Order", on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().textContains(\"Order\")",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

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
}
