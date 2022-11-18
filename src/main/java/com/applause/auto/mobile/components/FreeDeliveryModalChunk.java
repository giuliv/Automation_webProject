package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import java.time.Duration;

public class FreeDeliveryModalChunk extends BaseComponent {

  @Locate(
      xpath = "//XCUIElementTypeButton[@name=\"No Thanks\" or @name=\"Dismiss\"]",
      on = Platform.MOBILE_IOS)
  protected Button dismissFreeDeliveryButton;

  public void dismissFreeDelivery() {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      try {
        logger.info("Click 'No Thanks'/'Dismiss' popUp, if displayed");
        SdkHelper.getSyncHelper()
            .wait(
                Until.uiElement(dismissFreeDeliveryButton)
                    .present()
                    .setTimeout(Duration.ofSeconds(10)));
        dismissFreeDeliveryButton.click();
      } catch (Throwable throwable) {
        logger.info("No Free Delivery popup found");
      }
    }
  }
}
