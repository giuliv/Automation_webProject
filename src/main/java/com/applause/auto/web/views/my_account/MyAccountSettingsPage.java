package com.applause.auto.web.views.my_account;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Base;
import com.applause.auto.web.views.ResetPasswordPage;
import io.qameta.allure.Step;
import java.time.Duration;

@Implementation(is = MyAccountSettingsPage.class, on = Platform.WEB)
public class MyAccountSettingsPage extends Base {

  /* -------- Elements -------- */

  @Locate(css = ".ac-settings", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(css = ".ac-dashboard__section-heading", on = Platform.WEB)
  private Text getTitleText;

  @Locate(css = ".ac-settings__item:nth-child(1)", on = Platform.WEB)
  private Text getNameText;

  @Locate(css = ".ac-settings__item:nth-child(2)", on = Platform.WEB)
  private Text getEmailText;

  @Locate(css = ".ac-settings__item:nth-child(3)", on = Platform.WEB)
  private Text getPhoneNumberText;

  @Locate(css = ".ac-settings__item:nth-child(4)", on = Platform.WEB)
  private Text getPasswordText;

  @Locate(css = "a[href*=ResetPassword]", on = Platform.WEB)
  private Text getEditPasswordLink;

  @Locate(css = "a[href*=email-preferences]", on = Platform.WEB)
  private Text getEditEmailPreferencesLink;

  @Locate(css = "a.ac-settings__btn--address[href*='address-book']", on = Platform.WEB)
  private Link getEditStandardOrderAddressBookLink;

  @Locate(css = "a.ac-settings__btn--address[href*='subscriptions']", on = Platform.WEB)
  private Link getUpdateSubscriptionShippingAddressLink;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getViewSignature).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("My Account Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Verify Account information title is displayed")
  public boolean isAccountInformationTitleDisplayed() {
    return getTitleText.isDisplayed() && !getTitleText.getText().isEmpty();
  }

  @Step("Get Name value")
  public String getName() {
    String name = getNameText.getText().replace("Name:", "").trim();
    logger.info("Account name: {}", name);
    return name;
  }

  @Step("Get Email value")
  public String getEmail() {
    String email = getEmailText.getText().replace("Email:", "").trim();
    logger.info("Account email: {}", email);
    return email;
  }

  @Step("Verify Phone number is displayed")
  public boolean isPhoneNumberDisplayed() {
    return getPhoneNumberText.isDisplayed() && !getPhoneNumberText.getText().isEmpty();
  }

  @Step("Verify Password is displayed")
  public boolean isPasswordDisplayed() {
    return getPasswordText.isDisplayed() && !getPasswordText.getText().isEmpty();
  }

  @Step("Click 'Edit password' link")
  public ResetPasswordPage clickEditPasswordLink() {
    logger.info("Clicking 'Edit password' link");
    getEditPasswordLink.click();
    return SdkHelper.create(ResetPasswordPage.class);
  }

  @Step("Click 'Edit Email Preferences' link")
  public MyAccountEmailPreferencesPage clickEditEmailPreferencesLink() {
    logger.info("Clicking 'Edit Email Preferences' link");
    getEditEmailPreferencesLink.click();
    return SdkHelper.create(MyAccountEmailPreferencesPage.class);
  }

  @Step("Verify 'Edit password' link is displayed")
  public boolean isEditPasswordLinkDisplayed() {
    return WebHelper.isDisplayed(getEditPasswordLink);
  }

  @Step("Verify 'Edit Email Preferences' link is displayed")
  public boolean isEditEmailPreferencesLinkDisplayed() {
    return WebHelper.isDisplayed(getEditEmailPreferencesLink);
  }

  @Step("Verify 'UPDATE SUBSCRIPTION SHIPPING ADDRESS' link is displayed")
  public boolean isUpdateSubscriptionShippingAddressLinkDisplayed() {
    return WebHelper.isDisplayed(getUpdateSubscriptionShippingAddressLink);
  }

  @Step("Verify 'DIT STANDARD ORDER ADDRESS BOOK' link is displayed")
  public boolean isEditStandardOrderAddressBookLinkDisplayed() {
    return WebHelper.isDisplayed(getEditStandardOrderAddressBookLink);
  }
}
