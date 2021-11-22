package com.applause.auto.new_web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.my_account.MyOrderItemComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = ReferralsPage.class, on = Platform.WEB)
@Implementation(is = ReferralsPage.class, on = Platform.WEB_MOBILE_PHONE)
public class ReferralsPage extends BaseComponent {

  @Locate(
      xpath = "//h2[text()='Referrals']",
      on = Platform.WEB)
  private Text baseElement;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(baseElement).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("Referrals page URL: " + getDriver().getCurrentUrl());
  }

  @Step("Check if 'Referrals' is Displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }
}
