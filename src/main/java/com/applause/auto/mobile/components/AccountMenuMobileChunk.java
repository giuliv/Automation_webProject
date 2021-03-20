package com.applause.auto.mobile.components;

import java.time.Duration;

import org.openqa.selenium.NoAlertPresentException;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.*;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

@Implementation(is = AndroidAccountMenuMobileChunk.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = AccountMenuMobileChunk.class, on = Platform.MOBILE_IOS)
public class AccountMenuMobileChunk extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Profile Details\" and @visible='true']", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/profileDetails", on = Platform.MOBILE_ANDROID)
	protected Button getProfileDetailsButton;

	@Locate(id = "Sign Out", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/logoutButton", on = Platform.MOBILE_ANDROID)
	protected Button getSignOutButton;

	@Locate(accessibilityId = "Terms and Privacy Policy", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/termsAndPrivacy", on = Platform.MOBILE_ANDROID)
	protected Button termsAndPrivacyPolicyButton;

	@Locate(xpath = "//XCUIElementTypeButton[@value='Log Out']", on = Platform.MOBILE_IOS)
	@Locate(id = "android:id/button1", on = Platform.MOBILE_ANDROID)
	protected Button getLogOutButton;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"General Settings\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/generalSettings", on = Platform.MOBILE_ANDROID)
	protected Button getGeneralSettingsButton;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Account History\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/accountActivity", on = Platform.MOBILE_ANDROID)
	protected Button getAccountHistoryButton;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Help & Feedback\"]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/helpFeedback", on = Platform.MOBILE_ANDROID)
	protected Button getHelpAndFeedbackButton;

	@Locate(id = "Payment Methods", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/paymentMethods", on = Platform.MOBILE_ANDROID)
	protected Button getPaymentMethodsButton;

	@Locate(xpath = "//XCUIElementTypeButton[@name='Close' and @visible='true']", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]", on = Platform.MOBILE_ANDROID)
	protected Button getCrossButton;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"social facebook\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/facebook", on = Platform.MOBILE_ANDROID)
	protected Button getFacebookIcon;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"social instagram\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/instagram", on = Platform.MOBILE_ANDROID)
	protected Button getInstagramIcon;

	@Locate(xpath = "(//XCUIElementTypeButton[@name=\"social twitter\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/twitter", on = Platform.MOBILE_ANDROID)
	protected Button getTwitterIcon;

	@Locate(xpath = "(//XCUIElementTypeStaticText[@name=\"Peet's Coffee is on Facebook.\"])[1]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.view.View[@text='facebook'] | //android.widget.LinearLayout[contains(@resource-id,'com.facebook.katana:id')]", on = Platform.MOBILE_ANDROID)
	protected Text facebookPage;

	@Locate(xpath = "//XCUIElementTypeButton[@name=\"Follow\"] | //XCUIElementTypeStaticText[@name=\"Instagram\" and @visible=\"true\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.Button[@class='android.widget.Button' and contains(@text, 'Follow')] | //android.webkit.WebView[@text='Login • Instagram']", on = Platform.MOBILE_ANDROID)
	protected Text instagramPage;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name='The Original Craft Coffee.™ Since 1966.']", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.webkit.WebView[contains(@text, \"Peet's Coffee (@peetscoffee) / Twitter\")]", on = Platform.MOBILE_ANDROID)
	protected Text twitterPage;

	@Locate(xpath = "//XCUIElementTypeButton[@name='Done']", on = Platform.MOBILE_IOS)
	protected Button getDoneButton;

	@Locate(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeStaticText", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
	protected Text titleText;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Account Settings\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text='Account Settings']", on = Platform.MOBILE_ANDROID)
	protected Text accountSettingsSubHeaderText;

	@Locate(xpath = "//XCUIElementTypeStaticText[@name=\"Peet's Coffee\"]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[@text=\"Peet's Coffee\"]", on = Platform.MOBILE_ANDROID)
	protected Text peetsCoffeeSubHeaderText;

	@Locate(xpath = "//XCUIElementTypeStaticText[contains(@name,'Version')]", on = Platform.MOBILE_IOS)
	@Locate(xpath = "//android.widget.TextView[contains(@text,'Version')]", on = Platform.MOBILE_ANDROID)
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
		return this.create(ProfileDetailsView.class);
	}

	/**
	 * General settings general settings view.
	 *
	 * @return the general settings view
	 */
	public GeneralSettingsView generalSettings() {
		logger.info("Click on General Settings button");
		getGeneralSettingsButton.click();
		return this.create(GeneralSettingsView.class);
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
		try {
			getDriver().switchTo().alert().accept();
		} catch (NoAlertPresentException noAlertPresentException) {
			logger.warn("No alert found, probably because TestObect cloud accept it");
		}
		return this.create(AuthenticationView.class);
	}

	/**
	 * Click Payment Methods
	 *
	 * @return PaymentMethodsView
	 */
	public PaymentMethodsView clickPaymentMethods() {
		logger.info("Click Payment Methods");
		getSyncHelper().wait(Until.uiElement(getPaymentMethodsButton).visible().setTimeout(Duration.ofSeconds(50)));
		getPaymentMethodsButton.click();
		getSyncHelper().sleep(5000);
		return this.create(PaymentMethodsView.class);
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
		getSyncHelper().sleep(3000);
		return this.create(AccountMenuMobileChunk.class);
	}

	/**
	 * Click Instagram Icon
	 *
	 * @return SocialMediaView
	 */
	public AccountMenuMobileChunk clickInstagramIcon() {
		logger.info("Click Instagram");
		getInstagramIcon.click();
		getSyncHelper().sleep(3000);
		return this.create(AccountMenuMobileChunk.class);
	}

	/**
	 * Click Twitter Icon
	 *
	 * @return SocialMediaView
	 */
	public AccountMenuMobileChunk clickTwitterIcon() {
		logger.info("Click Twitter");
		getTwitterIcon.click();
		getSyncHelper().sleep(3000);
		return this.create(AccountMenuMobileChunk.class);
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
		getSyncHelper().wait(Until.uiElement(instagramPage).visible());
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
	public AccountMenuMobileChunk clickDoneButton() {
		logger.info("Clicking Done Button to go back");
		getDoneButton.click();
		return this.create(AccountMenuMobileChunk.class);
	}

	/**
	 * Sign out t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T signOut(Class<T> clazz) {
		logger.info("Click on Sign Out button");
		MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
		getSignOutButton.click();
		return this.create(clazz);
	}

	/**
	 * Account history account history view.
	 *
	 * @return the account history view
	 */
	public AccountHistoryView accountHistory() {
		logger.info("Click Account History");
		getAccountHistoryButton.click();
		getSyncHelper().sleep(15000);
		return this.create(AccountHistoryView.class);
	}

	/**
	 * Help & Feedback view.
	 *
	 * @return the help and feedback view
	 */
	public HelpAndFeedbackView helpAndFeedback() {
		logger.info("Click Help & Feedback");
		getHelpAndFeedbackButton.click();
		return this.create(HelpAndFeedbackView.class);
	}

	/** Click the Cross Button */
	public void clickCrossButton() {
		logger.info("Clicking the cross button");
		getSyncHelper().sleep(5000);
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
		return getProfileDetailsButton.isDisplayed();
	}

	/**
	 * Is general settings menu item displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isGeneralSettingsMenuItemDisplayed() {
		logger.info("Checking if general settings menu item displayed");
		return getGeneralSettingsButton.isDisplayed();
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
		return this.create(LegalInfoView.class);
	}
}

class AndroidAccountMenuMobileChunk extends AccountMenuMobileChunk {

	/* -------- Actions -------- */

	public AuthenticationView signOut() {
		return signOut(AuthenticationView.class);
	}

	/**
	 * Sign out t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T signOut(Class<T> clazz) {
		logger.info("Click on Sign Out button");
		MobileHelper.swipeWithCount(SwipeDirection.UP, 5);
		getSignOutButton.click();
		getLogOutButton.click();
		return this.create(clazz);
	}

	@Override
	public AccountMenuMobileChunk clickDoneButton() {
		logger.info("Clicking Done Button to go back");
		MobileHelper.tapAndroidDeviceBackButton();
		return this.create(AccountMenuMobileChunk.class);
	}

	@Override
	public Boolean isOnFacebookPage() {
		getSyncHelper().wait(Until.uiElement(facebookPage).visible());
		return facebookPage.isDisplayed();
	}

	@Override
	public Boolean isOnTwitterPage() {
		getSyncHelper().wait(Until.uiElement(twitterPage).visible());
		return twitterPage.isDisplayed();
	}

	@Override
	public void clickCrossButton() {
		logger.info("Clicking the cross button");
		getSyncHelper().sleep(5000);
		getDeviceControl().tapElementCenter(getCrossButton);
		getSyncHelper().sleep(5000);
		getDeviceControl().tapElementCenter(getCrossButton);
	}

}
