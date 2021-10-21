package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.web.views.FAQPage;
import io.qameta.allure.Step;

@Implementation(is = ShipDateInfoComponent.class, on = Platform.WEB)
@Implementation(is = ShipDateInfoComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class ShipDateInfoComponent extends BaseComponent {

  @Locate(xpath = "//p/a", on = Platform.WEB)
  private Button faqButton;

  @Step("Click on FAQ link from tooltip")
  public FAQPage clickOnFaqButton() {
    logger.info("Clicking on FAQ link from tooltip");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    faqButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(FAQPage.class);
  }
}
