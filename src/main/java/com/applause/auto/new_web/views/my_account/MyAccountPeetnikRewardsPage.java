package com.applause.auto.new_web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.views.Base;
import com.applause.auto.new_web.views.ContactUsPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = MyAccountPeetnikRewardsPage.class, on = Platform.WEB)
public class MyAccountPeetnikRewardsPage extends Base {

  /* -------- Elements -------- */

  @Locate(className = "ac-rewards", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(className = "ac-dashboard__section-heading", on = Platform.WEB)
  private Button titleText;

  @Locate(className = "ac-dashboard__section-header", on = Platform.WEB)
  private Button subtitleText;

  @Locate(css = ".rewards-settings ul > li:nth-child(1)", on = Platform.WEB)
  private Button userName;

  @Locate(css = ".rewards-settings ul > li:nth-child(2)", on = Platform.WEB)
  private Button userEmail;

  @Locate(css = ".rewards-settings__link", on = Platform.WEB)
  private Link customerExperienceTeamLink;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
  }

  /* -------- Actions -------- */

  @Step("Verify title is displayed")
  public boolean isTitleDisplayed() {
    return titleText.isDisplayed() && !titleText.getText().isEmpty();
  }

  @Step("Verify subtitle is displayed")
  public boolean isSubTitleDisplayed() {
    return subtitleText.isDisplayed() && !subtitleText.getText().isEmpty();
  }

  @Step("Get user name")
  public String getUserName() {
    return userName.getText().trim();
  }

  @Step("Get email")
  public String getEmail() {
    return userEmail.getText().trim();
  }

  @Step("Click 'Customer experience team' link")
  public ContactUsPage clickCustomerExperienceTeamLink() {
    logger.info("Clicking 'Customer experience team' link");
    customerExperienceTeamLink.click();
    return SdkHelper.create(ContactUsPage.class);
  }
}
