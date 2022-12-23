package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.FreeDeliveryModalChunk;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import java.time.Duration;

@Implementation(is = DashboardView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = IosDashboardView.class, on = Platform.MOBILE_IOS)
public class DashboardView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(accessibilityId = "Your Feed", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/yourFeedTextView",
      on = Platform.MOBILE_ANDROID)
  protected TextBox getSignature;

  @Locate(accessibilityId = "Store locator", on = Platform.MOBILE_IOS)
  @Locate(accessibilityId = "Stores button", on = Platform.MOBILE_ANDROID)
  protected Button locationButton;

  /* -------- Actions -------- */

  public void afterInit() {
    SdkHelper.create(FreeDeliveryModalChunk.class).dismissFreeDelivery();
    logger.info(">>>>>" + SdkHelper.getDriver().getPageSource());
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getSignature).present().setTimeout(Duration.ofSeconds(45)));
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
  public <T extends BaseComponent> T location(Class<T> clazz) {
    logger.info("Click on Location button");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(locationButton).present().setTimeout(Duration.ofSeconds(50)));
    SdkHelper.getDeviceControl().tapElementCenter(locationButton);
    return SdkHelper.create(clazz);
  }
}
