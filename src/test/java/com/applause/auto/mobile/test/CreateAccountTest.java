package com.applause.auto.mobile.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.CreateAccountView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.PrivacyPolicyView;
import com.applause.auto.pageframework.views.TermsAndConditionsView;

public class CreateAccountTest extends BaseTest {

	private LogController LOGGER = new LogController(CreateAccountTest.class);

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625879")
	public void footerLinksTest() {

		LOGGER.info("Launch the app and arrive at the first onboarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOffer();

		LOGGER.info("Tap Create Account");
		CreateAccountView createAccountView = landingView.createAccount();

		LOGGER.info("Scroll down and check the footer links");
		MobileHelper.scrollDown(4);

		LOGGER.info(
				"Make sure above the create account button is the copy and check-box: I agree to the Privacy Policy and Terms & Conditions");

		LOGGER.info("Tap on Privacy Policy link");
		PrivacyPolicyView privacyPolicyView = createAccountView.privacyPolicy();

		LOGGER.info("Make sure user is taken to Privacy Policy screen");
		Assert.assertNotNull(privacyPolicyView, "Privacy Policy does not displayed");

		LOGGER.info("Tap at top left \"Peet's\" / close browser and open Peet's to return to the app");
		MobileHelper.activateApp();

		LOGGER.info("Tap on the Terms and Conditions link");
		TermsAndConditionsView termsAndConditionsView = createAccountView.termsAndConditions();

		LOGGER.info("Make sure user is taken to Terms and Conditions screen");
		Assert.assertNotNull(termsAndConditionsView, "Terms And Conditions does not displayed");

		LOGGER.info("Tap at top left \"Peet's\" / close browser and open Peet's to return to the app");

	}
}
