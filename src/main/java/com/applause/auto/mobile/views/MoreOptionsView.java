package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.NoAlertPresentException;

@Implementation(is = AndroidMoreOptionsView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = MoreOptionsView.class, on = Platform.MOBILE_IOS)
public class MoreOptionsView extends BaseComponent {

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`name == 'Profile Details' AND visible == 1`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/profileDetails\")",
      on = Platform.MOBILE_ANDROID)
  protected Button profileDetailsButton;

  @Locate(id = "Sign Out", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/logoutButton", on = Platform.MOBILE_ANDROID)
  protected Button getSignOutButton;

  @Locate(accessibilityId = "Terms and Privacy Policy", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/termsAndPrivacy",
      on = Platform.MOBILE_ANDROID)
  protected Button termsAndPrivacyPolicyButton;

  @Locate(xpath = "//XCUIElementTypeButton[@value='Log Out']", on = Platform.MOBILE_IOS)
  @Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
  protected Button getLogOutButton;

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeStaticText[`name == 'Personal Settings' AND visible == 1`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/generalSettings\")",
      on = Platform.MOBILE_ANDROID)
  protected Button personalSettingsButton;

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

  @Locate(
      xpath = "//XCUIElementTypeButton[@name='Close' and @visible='true']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
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
  @Locate(
      xpath =
          "//android.view.View[@text='facebook'] | //android.widget.LinearLayout[contains(@resource-id,'com.facebook.katana:id')]",
      on = Platform.MOBILE_ANDROID)
  protected Text facebookPage;

  @Locate(
      xpath =
          "//XCUIElementTypeButton[@name=\"Follow\"] | //XCUIElementTypeStaticText[@name=\"Instagram\" and @visible=\"true\"]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.Button[@class='android.widget.Button' and contains(@text, 'Follow')] | //android.webkit.WebView[@text='Login • Instagram']",
      on = Platform.MOBILE_ANDROID)
  protected Text instagramPage;

  @Locate(
      xpath = "//XCUIElementTypeStaticText[@name='The Original Craft Coffee.™ Since 1966.']",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.webkit.WebView[contains(@text, \"Peet's Coffee (@peetscoffee) / Twitter\")]",
      on = Platform.MOBILE_ANDROID)
  protected Text twitterPage;

  @Locate(xpath = "//XCUIElementTypeButton[@name='Done']", on = Platform.MOBILE_IOS)
  protected Button getDoneButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeNavigationBar/XCUIElementTypeStaticText",
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

  @Step("Navigate to the Profile details view.")
  public ProfileDetailsView profileDetails() {
    logger.info("Tapping on Profile Details button");
    profileDetailsButton.click();
    return SdkHelper.create(ProfileDetailsView.class);
  }

  @Step("Navigate to the Personal Settings")
  public PersonalSettingsView personalSettings() {
    logger.info("Taping on Personal Settings button");
    personalSettingsButton.click();
    return SdkHelper.create(PersonalSettingsView.class);
  }

  @Step("Sign out authentication view.")
  public LandingView signOut() {
    logger.info("Click on Sign Out button");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
    getSignOutButton.click();
    try {
      getDriver().switchTo().alert().accept();
    } catch (NoAlertPresentException noAlertPresentException) {
      logger.warn("No alert found, probably because TestObect cloud accept it");
    }
    return SdkHelper.create(LandingView.class);
  }

  /**
   * Click Payment Methods
   *
   * @return PaymentMethodsView
   */
  public PaymentMethodsView clickPaymentMethods() {
    logger.info("Click Payment Methods");
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(getPaymentMethodsButton).visible().setTimeout(Duration.ofSeconds(50)));
    getPaymentMethodsButton.click();
    SdkHelper.getSyncHelper().sleep(5000);
    return SdkHelper.create(PaymentMethodsView.class);
  }

  /**
   * Click Facebook Icon
   *
   * @return SocialMediaView
   */
  public MoreOptionsView clickFacebookIcon() {
    logger.info("Click Facebook");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 1);
    getFacebookIcon.click();
    SdkHelper.getSyncHelper().sleep(3000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  /**
   * Click Instagram Icon
   *
   * @return SocialMediaView
   */
  public MoreOptionsView clickInstagramIcon() {
    logger.info("Click Instagram");
    getInstagramIcon.click();
    SdkHelper.getSyncHelper().sleep(3000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  /**
   * Click Twitter Icon
   *
   * @return SocialMediaView
   */
  public MoreOptionsView clickTwitterIcon() {
    logger.info("Click Twitter");
    getTwitterIcon.click();
    SdkHelper.getSyncHelper().sleep(3000);
    return SdkHelper.create(MoreOptionsView.class);
  }

  /**
   * Is on facebook page boolean.
   *
   * @return the boolean
   */
  public Boolean isOnFacebookPage() {
    String s = facebookPage.getText();
    String e = "Peet's Coffee is on Facebook.";
    return s.equals(e);
  }

  /**
   * Is on instagram page boolean.
   *
   * @return the boolean
   */
  public Boolean isOnInstagramPage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(instagramPage).visible());
    return instagramPage.isDisplayed();
  }

  /**
   * Is on twitter page boolean.
   *
   * @return the boolean
   */
  public Boolean isOnTwitterPage() {
    String s = twitterPage.getText().trim();
    String e = "The Original Craft Coffee.™ Since 1966.";
    return s.equals(e);
  }

  /**
   * Click done button account menu mobile chunk.
   *
   * @return the account menu mobile chunk
   */
  public MoreOptionsView clickDoneButton() {
    logger.info("Clicking Done Button to go back");
    getDoneButton.click();
    return SdkHelper.create(MoreOptionsView.class);
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
    return SdkHelper.create(clazz);
  }

  /**
   * Account history account history view.
   *
   * @return the account history view
   */
  public AccountHistoryView accountHistory() {
    logger.info("Click Account History");
    getAccountHistoryButton.click();
    SdkHelper.getSyncHelper().sleep(15000);
    return SdkHelper.create(AccountHistoryView.class);
  }

  /**
   * Help & Feedback view.
   *
   * @return the help and feedback view
   */
  public HelpAndFeedbackView helpAndFeedback() {
    logger.info("Click Help & Feedback");
    getHelpAndFeedbackButton.click();
    return SdkHelper.create(HelpAndFeedbackView.class);
  }

  /** Click the Cross Button */
  public void clickCrossButton() {
    logger.info("Clicking the cross button");
    SdkHelper.getSyncHelper().sleep(5000);
    getCrossButton.click();
  }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    logger.info("Obtaining title");
    return titleText.getText();
  }

  /**
   * Is close button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isCloseButtonDisplayed() {
    logger.info("Checking if close button displayed");
    return getCrossButton.isDisplayed();
  }

  /**
   * Is account settings sub header displayed boolean.
   *
   * @return the boolean
   */
  public boolean isAccountSettingsSubHeaderDisplayed() {
    logger.info("Checking if account settings button displayed");
    return accountSettingsSubHeaderText.isDisplayed();
  }

  /**
   * Is profile details menu item displayed boolean.
   *
   * @return the boolean
   */
  public boolean isProfileDetailsMenuItemDisplayed() {
    logger.info("Checking if profile details menu item displayed");
    return profileDetailsButton.isDisplayed();
  }

  @Step("Check if personal settings menu item is displayed boolean.")
  public boolean isPersonalSettingsMenuItemDisplayed() {
    logger.info("Checking if personal settings menu item displayed");
    return personalSettingsButton.isDisplayed();
  }

  /**
   * Is payments methods menu item displayed boolean.
   *
   * @return the boolean
   */
  public boolean isPaymentsMethodsMenuItemDisplayed() {
    logger.info("Checking if payment methods menu item displayed");
    return getPaymentMethodsButton.isDisplayed();
  }

  /**
   * Is account history menu item displayed boolean.
   *
   * @return the boolean
   */
  public boolean isAccountHistoryMenuItemDisplayed() {
    logger.info("Checking if account history menu item displayed");
    return getAccountHistoryButton.isDisplayed();
  }

  /**
   * Is sub header peets coffee displayed boolean.
   *
   * @return the boolean
   */
  public boolean isSubHeaderPeetsCoffeeDisplayed() {
    logger.info("Checking if sub header Peets Coffee displayed");
    return peetsCoffeeSubHeaderText.isDisplayed();
  }

  /**
   * Is about us menu item displayed boolean.
   *
   * @return the boolean
   */
  public boolean isAboutUsMenuItemDisplayed() {
    logger.info("Checking if About Us menu item displayed");
    return aboutUsButton.isDisplayed();
  }

  /**
   * Is help and feedback boolean.
   *
   * @return the boolean
   */
  public boolean isHelpAndFeedback() {
    logger.info("Checking if Help and Feedback displayed");
    return getHelpAndFeedbackButton.isDisplayed();
  }

  /**
   * Is facebook icon displayed boolean.
   *
   * @return the boolean
   */
  public boolean isFacebookIconDisplayed() {
    logger.info("Checking if Facebook Icon displayed");
    return getFacebookIcon.isDisplayed();
  }

  /**
   * Is instagram icon displayed boolean.
   *
   * @return the boolean
   */
  public boolean isInstagramIconDisplayed() {
    logger.info("Checking if Instagram Icon displayed");
    return getInstagramIcon.isDisplayed();
  }

  /**
   * Is twitter icon displayed boolean.
   *
   * @return the boolean
   */
  public boolean isTwitterIconDisplayed() {
    logger.info("Checking if Twitter icon displayed");
    return getTwitterIcon.isDisplayed();
  }

  /**
   * Gets version.
   *
   * @return the version
   */
  public String getVersion() {
    logger.info("Obtaining version");
    return versionText.getText().replace("\n", " ");
  }

  /**
   * Is sign out button displayed boolean.
   *
   * @return the boolean
   */
  public boolean isSignOutButtonDisplayed() {
    logger.info("Checking if Sign Out button displayed");
    return getSignOutButton.isDisplayed();
  }

  public LegalInfoView termsAndPrivacyPolicy() {
    logger.info("Click on Terms and Privacy Policy");
    termsAndPrivacyPolicyButton.click();
    return SdkHelper.create(LegalInfoView.class);
  }
}

class AndroidMoreOptionsView extends MoreOptionsView {

  public LandingView signOut() {
    return signOut(LandingView.class);
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
    return SdkHelper.create(clazz);
  }

  @Override
  public MoreOptionsView clickDoneButton() {
    logger.info("Clicking Done Button to go back");
    MobileHelper.tapAndroidDeviceBackButton();
    return SdkHelper.create(MoreOptionsView.class);
  }

  @Override
  public Boolean isOnFacebookPage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(facebookPage).visible());
    return facebookPage.isDisplayed();
  }

  @Override
  public Boolean isOnTwitterPage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(twitterPage).visible());
    return twitterPage.isDisplayed();
  }

  @Override
  public void clickCrossButton() {
    logger.info("Clicking the cross button");
    SdkHelper.getSyncHelper().sleep(5000);
    SdkHelper.getDeviceControl().tapElementCenter(getCrossButton);
    SdkHelper.getSyncHelper().sleep(5000);
    SdkHelper.getDeviceControl().tapElementCenter(getCrossButton);
  }
}
