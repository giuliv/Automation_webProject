package com.applause.auto.mobile.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.CreateAccountView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.PrivacyPolicyView;
import com.applause.auto.pageframework.views.ProfileDetailsView;
import com.applause.auto.pageframework.views.SignInView;
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

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625883")
	public void signInEmailPasswordTest() {

		LOGGER.info("Launch the app and arrive at the first onboarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOffer();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		String username = TestConstants.TestData.USERNAME;
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.TestData.PASSWORD);
		Assert.assertTrue(!signInView.getPassword().equals(TestConstants.TestData.PASSWORD),
				"Password does not hidden");

		LOGGER.info("Tap on show password icon");
		signInView.showPassword();

		LOGGER.info("Make sure password entered is displayed to user");
		Assert.assertEquals(signInView.getUnEncryptedPassword(), TestConstants.TestData.PASSWORD,
				"Password does not shown");

		LOGGER.info("Tap Sign In button");
		DashboardView dashboardView = signInView.signIn();

		LOGGER.info("User should see home/dashboard screen");
		Assert.assertNotNull(dashboardView, "Dashboard not displayed");

	}

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625925")
	public void accountSettingsEditProfileTest() {

		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOffer();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		String username = TestConstants.TestData.USERNAME;
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.TestData.PASSWORD);

		LOGGER.info("Tap Sign In button");
		DashboardView dashboardView = signInView.signIn();

		LOGGER.info("Tap on ... at top right of home screen to view more screen");
		AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

		LOGGER.info("Tap on Profile Details field/row");
		ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();

		LOGGER.info("Make sure user is taken to profile details screen:\n" + "* Header: Profile Details\n"
				+ "* Back arrow at top left corner\n" + "* First name field\n" + "* Last name field\n"
				+ "* Zip code (Optional) field\n" + "* Date of birth field (not editable)\n"
				+ "* Phone number (Optional) field\n" + "* Email address field\n" + "* Change password link\n"
				+ "* [Button] Save");
		Assert.assertNotNull(profileDetailsView, "Profile details view does not passed validation");

		LOGGER.info("Edit the fields that are editable");
		String firstNameOrig = profileDetailsView.getFirstname();
		String lastNameOrig = profileDetailsView.getLastname();
		String zipCodeOrig = profileDetailsView.getZipCode();
		String phoneOrig = profileDetailsView.getPhoneNumber();
		String emailOrig = profileDetailsView.getEmailAddress();

		String firstNameNew = firstNameOrig.replaceFirst("A", "AA");
		String lastNameNew = lastNameOrig.replaceFirst("A", "AA");
		String zipCodeNew = "11214";
		String phoneNew = "2345678901";
		String emailNew = emailOrig.replace(".com", ".net");

		profileDetailsView.setFirstname(firstNameNew);
		profileDetailsView.setLastname(lastNameNew);
		profileDetailsView.setZipCode(zipCodeNew);
		profileDetailsView.setPhoneNumber(phoneNew);
		profileDetailsView.setEmailAddress(emailNew);
		profileDetailsView.setConfirmEmailAddress(emailNew);

		LOGGER.info("Tap Save button");
		accountMenuMobileChunk = profileDetailsView.save();

		LOGGER.info("User should be directed back to the more screen");
		Assert.assertNotNull(accountMenuMobileChunk, "User does not redirected to account menu");

		LOGGER.info("Tap on Profile Details field again");
		profileDetailsView = accountMenuMobileChunk.profileDetails();

		LOGGER.info("Make sure edits to profile are reflected correctly on profile details screen");
		String firstNameUpd = profileDetailsView.getFirstname();
		String lastNameUpd = profileDetailsView.getLastname();
		String zipCodeUpd = profileDetailsView.getZipCode();
		String phoneUpd = profileDetailsView.getPhoneNumber();
		String emailUpd = profileDetailsView.getEmailAddress();
		Assert.assertEquals(firstNameUpd, firstNameNew, "Firstname does not updated");
		Assert.assertEquals(lastNameUpd, lastNameNew, "Lastname does not updated");
		Assert.assertEquals(zipCodeUpd, zipCodeNew, "zipcode does not updated");
		Assert.assertEquals(phoneUpd.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""),
				phoneNew.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""), "Phone does not updated");
		Assert.assertEquals(emailUpd, emailNew, "email does not updated");

		LOGGER.info("Cleanup Restore original");
		profileDetailsView.setFirstname(firstNameOrig);
		profileDetailsView.setLastname(lastNameOrig);
		profileDetailsView.setZipCode(zipCodeOrig);
		profileDetailsView.setPhoneNumber(phoneOrig);
		profileDetailsView.setEmailAddress(emailOrig);
		profileDetailsView.setConfirmEmailAddress(emailOrig);
		accountMenuMobileChunk = profileDetailsView.save();

	}
}
