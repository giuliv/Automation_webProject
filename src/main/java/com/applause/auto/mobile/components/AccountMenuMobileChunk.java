package com.applause.auto.mobile.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.AccountHistoryView;
import com.applause.auto.mobile.views.AuthenticationView;
import com.applause.auto.mobile.views.GeneralSettingsView;
import com.applause.auto.mobile.views.HelpAndFeedbackView;
import com.applause.auto.mobile.views.PaymentMethodsView;
import com.applause.auto.mobile.views.ProfileDetailsView;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;

@Implementation(is = AndroidAccountMenuMobileChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountMenuMobileChunk.class, on = Platform.MOBILE_IOS)
public class AccountMenuMobileChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Profile Details\" and @visible='true']",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/profileDetails", on = Platform.MOBILE_ANDROID)
  protected Button getProfileDetailsButton;

  @Locate(id = "Sign Out", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/logoutButton", on = Platform.MOBILE_ANDROID)
  protected Button getSignOutButton;

  @Locate(xpath = "//XCUIElementTypeButton[@value='Log Out']", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getLogOutButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"General Settings\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/generalSettings",
      on = Platform.MOBILE_ANDROID)
  protected Button getGeneralSettingsButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Account History\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/accountActivity",
      on = Platform.MOBILE_ANDROID)
  protected Button getAccountHistoryButton;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Help & Feedback\"]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/helpFeedback", on = Platform.MOBILE_ANDROID)
  protected Button getHelpAndFeedbackButton;

  @Locate(id = "Payment Methods", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/paymentMethods", on = Platform.MOBILE_ANDROID)
  protected Button getPaymentMethodsButton;

  @Locate(id = "button cross", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
      on = Platform.MOBILE_ANDROID)
  protected Button getCrossButton;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"social facebook\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/facebook", on = Platform.MOBILE_ANDROID)
  protected Button getFacebookIcon;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"social instagram\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/instagram", on = Platform.MOBILE_ANDROID)
  protected Button getInstagramIcon;

  @Locate(
      xpath = "(//XCUIElementTypeButton[@name=\"social twitter\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/twitter", on = Platform.MOBILE_ANDROID)
  protected Button getTwitterIcon;

  @Locate(
      xpath = "(//XCUIElementTypeStaticText[@name=\"Peet's Coffee is on Facebook.\"])[1]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.view.View[@text='facebook']", on = Platform.MOBILE_ANDROID)
  protected Text facebookPage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Share your photos with\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.Button[@class='android.widget.Button' and contains(@text, 'Follow')]",
      on = Platform.MOBILE_ANDROID)
  protected Text instagramPage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Get the most out of Twitter\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.EditText[@class='android.widget.EditText' and contains(@text, 'https://mobile.twitter.com/PeetsCoffee')]",
      on = Platform.MOBILE_ANDROID)
  protected Text twitterPage;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Done']", on = Platform.MOBILE_IOS)
  protected Button getDoneButton;

  @Locate(
      xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
  protected Text titleText;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name=\"Account Settings\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text='Account Settings']",
      on = Platform.MOBILE_ANDROID)
  protected Text accountSettingsSubHeaderText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Peet's Coffee\"]", on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[@text=\"Peet's Coffee\"]",
      on = Platform.MOBILE_ANDROID)
  protected Text peetsCoffeeSubHeaderText;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@name,'Version')]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath = "//android.widget.TextView[contains(@text,'Version')]",
      on = Platform.MOBILE_ANDROID)
  protected Text versionText;

  @Locate(accessibilityId = "About Us", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"About Us\"]", on = Platform.MOBILE_ANDROID)
  protected Button aboutUsButton;

  /* -------- Actions -------- */

  /**
   * Profile details profile details view.
   *
   * @return the profile details view
   */
  public ProfileDetailsView profileDetails() {
    logger.info("Click on Profile Details button");
    getProfileDetailsButton.click();
    return ComponentFactory.create(ProfileDetailsView.class);
  }

  /**
   * General settings general settings view.
   *
   * @return the general settings view
   */
  public GeneralSettingsView generalSettings() {
    logger.info("Click on General Settings button");
    getGeneralSettingsButton.click();
    return ComponentFactory.create(GeneralSettingsView.class);
  }

  /**
   * Sign out authentication view.
   *
   * @return the authentication view
   */
  public AuthenticationView signOut() {
    logger.info("Click on Sign Out button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    getSignOutButton.click();
    DriverManager.getDriver().switchTo().alert().accept();
    return ComponentFactory.create(AuthenticationView.class);
  }

  /**
   * Click Payment Methods
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView clickPaymentMethods() {
    logger.info("Click Payment Methods");
    getPaymentMethodsButton.click();
    SyncHelper.sleep(5000);
    return ComponentFactory.create(PaymentMethodsView.class);
  }

  /**
   * Click Facebook Icon
   *
   * @return SocialMediaView
   */
  public AccountMenuMobileChunk clickFacebookIcon() {
    logger.info("Click Facebook");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 1);
    getFacebookIcon.click();
    SyncHelper.sleep(3000);
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  /**
   * Click Instagram Icon
   *
   * @return SocialMediaView
   */
  public AccountMenuMobileChunk clickInstagramIcon() {
    logger.info("Click Instagram");
    getInstagramIcon.click();
    SyncHelper.sleep(3000);
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  /**
   * Click Twitter Icon
   *
   * @return SocialMediaView
   */
  public AccountMenuMobileChunk clickTwitterIcon() {
    logger.info("Click Twitter");
    getTwitterIcon.click();
    SyncHelper.sleep(3000);
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  public Boolean isOnFacebookPage() {
    String s = facebookPage.getText();
    String e = "Peet's Coffee is on Facebook.";
    return s.equals(e);
  }

  public Boolean isOnInstagramPage() {
    String s = instagramPage.getText();
    String e = "Share your photos with";
    return s.equals(e);
  }

  public Boolean isOnTwitterPage() {
    String s = twitterPage.getText();
    String e = "Get the most out of Twitter";
    return s.equals(e);
  }

  public AccountMenuMobileChunk clickDoneButton() {
    logger.info("Clicking Done Button to go back");
    getDoneButton.click();
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  /**
   * Sign out t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T signOut(Class<T> clazz) {
    logger.info("Click on Sign Out button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    getSignOutButton.click();
    return ComponentFactory.create(clazz);
  }

  /**
   * Account history account history view.
   *
   * @return the account history view
   */
  public AccountHistoryView accountHistory() {
    logger.info("Click Account History");
    getAccountHistoryButton.click();
    return ComponentFactory.create(AccountHistoryView.class);
  }

  /**
   * Help & Feedback view.
   *
   * @return the help and feedback view
   */
  public HelpAndFeedbackView helpAndFeedback() {
    logger.info("Click Help & Feedback");
    getHelpAndFeedbackButton.click();
    return ComponentFactory.create(HelpAndFeedbackView.class);
  }

  /** Click the Cross Button */
  public void clickCrossButton() {
    logger.info("Clicking the cross button");
    getCrossButton.click();
  }

  public String getTitle() {
    logger.info("Obtaining title");
    return titleText.getText();
  }

  public boolean isCloseButtonDisplayed() {
    logger.info("Checking if close button displayed");
    return getCrossButton.isDisplayed();
  }

  public boolean isAccountSettingsSubHeaderDisplayed() {
    logger.info("Checking if account settings button displayed");
    return accountSettingsSubHeaderText.isDisplayed();
  }

  public boolean isProfileDetailsMenuItemDisplayed() {
    logger.info("Checking if profile details menu item displayed");
    return getProfileDetailsButton.isDisplayed();
  }

  public boolean isGeneralSettingsMenuItemDisplayed() {
    logger.info("Checking if general settings menu item displayed");
    return getGeneralSettingsButton.isDisplayed();
  }

  public boolean isPaymentsMethodsMenuItemDisplayed() {
    logger.info("Checking if payment methods menu item displayed");
    return getPaymentMethodsButton.isDisplayed();
  }

  public boolean isAccountHistoryMenuItemDisplayed() {
    logger.info("Checking if account history menu item displayed");
    return getAccountHistoryButton.isDisplayed();
  }

  public boolean isSubHeaderPeetsCoffeeDisplayed() {
    logger.info("Checking if sub header Peets Coffee displayed");
    return peetsCoffeeSubHeaderText.isDisplayed();
  }

  public boolean isAboutUsMenuItemDisplayed() {
    logger.info("Checking if About Us menu item displayed");
    return aboutUsButton.isDisplayed();
  }

  public boolean isHelpAndFeedback() {
    logger.info("Checking if Help and Feedback displayed");
    return getHelpAndFeedbackButton.isDisplayed();
  }

  public boolean isFacebookIconDisplayed() {
    logger.info("Checking if Facebook Icon displayed");
    return getFacebookIcon.isDisplayed();
  }

  public boolean isInstagramIconDisplayed() {
    logger.info("Checking if Instagram Icon displayed");
    return getInstagramIcon.isDisplayed();
  }

  public boolean isTwitterIconDisplayed() {
    logger.info("Checking if Twitter icon displayed");
    return getTwitterIcon.isDisplayed();
  }

  public String getVersion() {
    logger.info("Obtaining version");
    return versionText.getText().replace("\n", " ");
  }

  public boolean isSignOutButtonDisplayed() {
    logger.info("Checking if Sign Out button displayed");
    return getSignOutButton.isDisplayed();
  }
}

class AndroidAccountMenuMobileChunk extends AccountMenuMobileChunk {

  /* -------- Actions -------- */

  public AuthenticationView signOut() {
    logger.info("Click on Sign Out button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    getSignOutButton.click();
    getLogOutButton.click();
    return ComponentFactory.create(AuthenticationView.class);
  }

  /**
   * Sign out t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T signOut(Class<T> clazz) {
    logger.info("Click on Sign Out button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    getSignOutButton.click();
    getLogOutButton.click();
    return ComponentFactory.create(clazz);
  }

  @Override
  public AccountMenuMobileChunk clickDoneButton() {
    logger.info("Clicking Done Button to go back");
    MobileHelper.tapAndroidDeviceBackButton();
    return ComponentFactory.create(AccountMenuMobileChunk.class);
  }

  @Override
  public Boolean isOnFacebookPage() {
    SyncHelper.wait(Until.uiElement(facebookPage).visible());
    return facebookPage.isDisplayed();
  }

  @Override
  public Boolean isOnInstagramPage() {
    SyncHelper.wait(Until.uiElement(instagramPage).visible());
    return instagramPage.isDisplayed();
  }

  @Override
  public Boolean isOnTwitterPage() {
    SyncHelper.wait(Until.uiElement(twitterPage).visible());
    return twitterPage.isDisplayed();
  }
}
