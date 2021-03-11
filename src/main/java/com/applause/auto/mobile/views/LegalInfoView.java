package com.applause.auto.mobile.views;

import java.time.Duration;

import org.openqa.selenium.ScreenOrientation;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

import io.appium.java_client.android.AndroidDriver;

@Implementation(is = AndroidLegalInfoView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = LegalInfoView.class, on = Platform.MOBILE_IOS)
public class LegalInfoView extends BaseComponent {

	/* -------- Elements -------- */

	@Locate(accessibilityId = "Peetnik Rewards Terms & Conditions", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/termsOfUse", on = Platform.MOBILE_ANDROID)
	protected Button peetnikRewardsTermsAndConditionsButton;

	@Locate(accessibilityId = "Privacy Policy", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/privacyPolicy", on = Platform.MOBILE_ANDROID)
	protected Button privacyPolicyButton;

	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"LEGAL INFO\"]/XCUIElementTypeStaticText", on = Platform.MOBILE_IOS)
	@Locate(id = "com.wearehathway.peets.development:id/title", on = Platform.MOBILE_ANDROID)
	protected Text headingText;

	// @Locate(accessibilityId = "button back", on = Platform.MOBILE_IOS) //Commented [15.01.2021]
	@Locate(xpath = "//XCUIElementTypeNavigationBar[@name=\"LEGAL INFO\"]/XCUIElementTypeButton", on = Platform.MOBILE_IOS)
	@Locate(accessibilityId = "Navigate up", on = Platform.MOBILE_ANDROID)
	protected Button navigateBackButton;

	@Locate(xpath = "//android.widget.Button[@text='allow cookies']", on = Platform.MOBILE_ANDROID)
	protected Button allowCookiesButton;

	/* -------- Actions -------- */

	public void afterInit() {
		getSyncHelper().wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(60)));
	}

	/**
	 * Peetnik rewards terms and conditions peetnik rewards terms and conditions view.
	 *
	 * @return the peetnik rewards terms and conditions view
	 */
	public PeetnikRewardsTermsAndConditionsView peetnikRewardsTermsAndConditions() {
		logger.info("Click on Peetnik Rewards Terms And Conditions");
		peetnikRewardsTermsAndConditionsButton.click();
		return this.create(PeetnikRewardsTermsAndConditionsView.class);
	}

	/**
	 * Privacy policy privacy policy view.
	 *
	 * @return the privacy policy view
	 */
	public PrivacyPolicyView privacyPolicy() {
		logger.info("Click on PPrivacy Policy");
		privacyPolicyButton.click();
		return this.create(PrivacyPolicyView.class);
	}

	/**
	 * Gets header.
	 *
	 * @return the header
	 */
	public String getHeader() {
		return headingText.getText();
	}

	/**
	 * Is back arrow displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isBackArrowDisplayed() {
		logger.info("Checking if navigate back button displayed");
		return navigateBackButton.isDisplayed();
	}

	/**
	 * Is peetnik rewards terms and conditions displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPeetnikRewardsTermsAndConditionsDisplayed() {
		logger.info("Checking Peetnik Rewards Terms And Conditions button displayed");
		return peetnikRewardsTermsAndConditionsButton.isDisplayed();
	}

	/**
	 * Is privacy policy displayed boolean.
	 *
	 * @return the boolean
	 */
	public boolean isPrivacyPolicyDisplayed() {
		logger.info("Checking if Privacy Policy button displayed");
		return privacyPolicyButton.isDisplayed();
	}
}

class AndroidLegalInfoView extends LegalInfoView {
	public void afterInit() {
		AndroidDriver androidDriver = ((AndroidDriver) getDriver());
		logger.info("Orientation: " + androidDriver.getOrientation());
		logger.info("Orientation: Forcing to PORTRAIT");
		androidDriver.rotate(ScreenOrientation.PORTRAIT);
		getSyncHelper().sleep(5000);
		logger.info("Orientation: " + androidDriver.getOrientation());
		try {
			allowCookiesButton.click();
		} catch (Throwable th) {
			logger.info("No cookies popup overlay found");
		}
		getSyncHelper().wait(Until.uiElement(headingText).present().setTimeout(Duration.ofSeconds(12)));
	}
}
