package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;

@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_IOS)
public class RemoveFromOrderChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "android:id/alertTitle", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeAlert/**/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  protected Text titleText;

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  @Locate(accessibilityId = "Remove", on = Platform.MOBILE_IOS)
  protected Button removeButton;

  /* -------- Actions -------- */

  /** Click dismiss button */
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(titleText).visible());
  }

  /**
   * Remove checkout view.
   *
   * @return the checkout view
   */
  public CheckoutView remove() {
    removeButton.click();
    return SdkHelper.create(CheckoutView.class);
  }
}
