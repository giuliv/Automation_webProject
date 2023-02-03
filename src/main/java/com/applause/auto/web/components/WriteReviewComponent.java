package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;

public class WriteReviewComponent extends BaseComponent {

  @Locate(css = "form[id*=submitreview-product]", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Override
  public void afterInit() {
    super.afterInit();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).present());
  }

  @Step("Verify Write a review component is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer);
  }
}
