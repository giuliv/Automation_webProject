package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.mobile.views.CheckoutView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = RemoveFromOrderChunk.class, on = Platform.MOBILE_IOS)
public class RemoveFromOrderChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(id = "android:id/alertTitle", on = Platform.MOBILE_ANDROID)
  protected Text titleText;

  @Locate(id = "android:id/message", on = Platform.MOBILE_ANDROID)
  protected Text dialogMessageText;

  @Locate(id = "android:id/button2", on = Platform.MOBILE_ANDROID)
  protected Button cancelButton;

  @Locate(id = "android:id/button2", on = Platform.MOBILE_ANDROID)
  protected Button removeButton;

  /* -------- Actions -------- */

  /** Click dismiss button */
  public void afterInit() {
    getSyncHelper().wait(Until.uiElement(titleText).visible());
  }

  public boolean isTitleDisplayed() {
    return titleText.isDisplayed();
  }

  public String messageText() {
    return dialogMessageText.getText();
  }

  public boolean isCancelButtonDisplayed() {
    return cancelButton.isDisplayed();
  }

  public boolean isRemoveButtonDisplayed() {
    return removeButton.isDisplayed();
  }

  public CheckoutView remove() {
    return null;
  }
}
