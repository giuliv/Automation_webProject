package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.framework.SdkHelper;
import java.time.Duration;

@Implementation(is = OrderConfirmationView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = OrderConfirmationView.class, on = Platform.MOBILE_IOS)
public class OrderConfirmationView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      id = "com.wearehathway.peets.development:id/pickUpOrderLayout",
      on = Platform.MOBILE_ANDROID)
  @Locate(id = "Your order will be ready between:", on = Platform.MOBILE_IOS)
  protected Text getHeadingText;

  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(120)));
  }
}
