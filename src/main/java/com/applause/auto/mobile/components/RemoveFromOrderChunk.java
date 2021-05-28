package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;

@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_IOS)
public class RemoveFromOrderChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "android:id/alertTitle", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeAlert/**/XCUIElementTypeStaticText[1]",
      on = Platform.MOBILE_IOS)
  protected Text titleText;

  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeAlert/**/XCUIElementTypeStaticText[2]",
      on = Platform.MOBILE_IOS)
  protected Text dialogMessageText;

  @Locate(id = "android:id/button2", on = Platform.MOBILE_ANDROID)
  @Locate(accessibilityId = "Cancel", on = Platform.MOBILE_IOS)
  protected Button cancelButton;

  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  @Locate(accessibilityId = "Remove", on = Platform.MOBILE_IOS)
  protected Button removeButton;

  /* -------- Actions -------- */

  /** Click dismiss button */
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(titleText).visible());
  }

  /**
   * Is title displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTitleDisplayed() {
    return titleText.isDisplayed();
  }

  /**
   * Message text string.
   *
   * @return the string
   */
  public String messageText() {
    return dialogMessageText.getText();
  }

  /**
   * Is cancel button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCancelButtonDisplayed() {
    return cancelButton.isDisplayed();
  }

  /**
   * Is remove button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isRemoveButtonDisplayed() {
    return removeButton.isDisplayed();
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
