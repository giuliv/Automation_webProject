package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

@Implementation(is = YourReviewWasSubmittedComponent.class, on = Platform.WEB)
@Implementation(is = YourReviewWasSubmittedComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class YourReviewWasSubmittedComponent extends BaseComponent {

  @Locate(xpath = "//div[contains(@class, 'bv-mbox-injection-target')]//h1", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Check if 'Your Review Was Submitted' modal appear")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer);
  }
}
