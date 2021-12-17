package com.applause.auto.new_web.components.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = UnlockMyDiscountComponent.class, on = Platform.WEB)
@Implementation(is = UnlockMyDiscountComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class UnlockMyDiscountComponent extends BaseComponent {

  @Locate(xpath = "//div[@class='campaign']", on = Platform.WEB)
  private Button baseElement;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(baseElement).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Unlock My Discount Component URL: " + getDriver().getCurrentUrl());
  }

  @Step("Check if 'Unlock My Discount' is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }
}
