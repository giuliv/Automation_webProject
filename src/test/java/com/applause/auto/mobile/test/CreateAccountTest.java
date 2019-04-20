package com.applause.auto.mobile.test;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AuthenticationView;
import com.applause.auto.pageframework.views.CreateAccountView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.GeneralSettingsView;
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
		String username = TestConstants.MyAccountTestData.EMAIL;
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.MyAccountTestData.PASSWORD);

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

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625880")
	public void createAccountEmailPassword() {
		long uniq = System.currentTimeMillis();

		LOGGER.info("Launch the app and arrive at the first onboarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOffer();

		LOGGER.info("Tap Create Account");
		CreateAccountView createAccountView = landingView.createAccount();

		LOGGER.info("Fields:\n" + "\n" + "* First Name\n" + "\n" + "* Last Name\n" + "\n" + "* Zip Code (Optional)\n"
				+ "\n" + "* Date of Birth\n" + "\n" + "      o Text: Your birthday drink is on us\n" + "\n"
				+ "* Phone Number (Optional)\n" + "\n" + "      o Text: Forgot your phone? Check in with this number.\n"
				+ "\n" + "* Email Address" + "* Password with show/hide password icon\n" + "\n"
				+ "      o Triggered Text: (x/_) At least 6 characters\n" + "\n" + "      o (x/_) At least 1 number\n"
				+ "\n" + "      o (x/_) At least 1 letter\n" + "\n" + "Email opt in/out checkbox (checked by default)\n"
				+ "\n" + "* Text: Yes, please send me emails with exclusive offers, rewards, news, and more.\n" + "\n"
				+ "Privacy Policy/T&C checkbox (not checked by default)\n" + "\n"
				+ "* Text: I agree to the Privacy Policy and Terms & Conditions\n" + "\n"
				+ "[Button] Create Account (grey until all required fields are entered)");

		LOGGER.info("Tap on First Name field and enter valid first name");

		String firstname = "Firstname";
		createAccountView.setFirstname(firstname);

		LOGGER.info("Enter valid last name");
		String lastname = "Lastname";
		createAccountView.setLastname(lastname);

		LOGGER.info("Enter valid zip code / Skip this field");
		String zipCode = "11214";
		createAccountView.setZipCode(zipCode);

		LOGGER.info("Scroll through and select birthday");
		String dobDay = "27";
		String dobMonth = "December";
		String dobYear = "1990";
		createAccountView.setDOB(dobDay, dobMonth, dobYear);

		LOGGER.info("Enter valid ten digit phone number / Skip this field");
		Random random = new Random();
		String phone = "2";
		for (int i = 0; i < 9; i++) {
			phone += "" + random.nextInt(9);
		}
		createAccountView.setPhoneNumber(phone);

		LOGGER.info("Enter valid email address");
		String email = String.format("a+%s@a.com", uniq);
		createAccountView.setEmailAddress(email);
		createAccountView.setConfirmEmailAddress(email);

		LOGGER.info("Enter valid password");
		String password = "Password1";
		createAccountView.setPassword(password);
		createAccountView.setConfirmationPassword(password);

		LOGGER.info("Tap on show password icon");
		createAccountView.showPassword();

		LOGGER.info("Make sure password entered is displayed to user");
		Assert.assertEquals(createAccountView.getHiddenPassword(), password, "Password does not displayed");

		LOGGER.info("Tap on hide password icon");
		createAccountView.hidePassword();

		LOGGER.info("Make sure password entered is hidden from user");
		Assert.assertNotEquals(createAccountView.getPassword(), password, "Password does not hidden");

		createAccountView.setPromo("");

		LOGGER.info("At email opt in/out checkbox:\n" + "\n" + "(1) leave box checked\n" + "\n" + "OR\n" + "\n"
				+ "(2) un-check it\n");
		LOGGER.info("Checkbox should be marked by default\n" + "\n"
				+ "If user un-checks the box, make sure un-checking it removes the check mark from the box\n");
		Assert.assertTrue(createAccountView.isEmailOptInChecked(), "Email opt in checkbox does not marked by default");
		createAccountView.tapEmailOptIn();
		Assert.assertFalse(createAccountView.isEmailOptInChecked(), "Email opt in checkbox does not marked");
		createAccountView.tapEmailOptIn();
		Assert.assertTrue(createAccountView.isEmailOptInChecked(), "Email opt in checkbox remains marked");

		LOGGER.info("At Privacy Policy and Terms & Conditions\n" + "\n" + "(1) check box\n" + "\n" + "OR\n" + "\n"
				+ "(2) un-check it\n");
		LOGGER.info("Checkbox should be unmarked by default\n" + "\n"
				+ "Create account button should be grey and not activated if check box is not marked\n"
				+ driver.getPageSource());
		Assert.assertFalse(createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
				"Privacy Policy and Terms and Conditions does not checked does not marked by default");
		Assert.assertFalse(createAccountView.isCreateAccountButtonEnabled(), "Create Account button does not disabled");

		LOGGER.info("Tap on checkbox to agree to terms of service");
		createAccountView.checkPrivacyPolicyAndTermsAndConditions();

		LOGGER.info("Make sure checkbox is marked and create account button should be activated and turn gold");
		Assert.assertTrue(createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
				"Privacy Policy opt in checkbox does not marked");
		Assert.assertTrue(createAccountView.isCreateAccountButtonEnabled(), "Create Account button does not enabled");

		LOGGER.info("Tap Create Account button");
		DashboardView dashboardView = createAccountView.createAccount();

		LOGGER.info("User account should be created successfully:\n" + "\n"
				+ "* User will see a loading dial, then a check mark to indicate successful account creation\n" + "\n"
				+ "User should then see Peet's loading page briefly:\n" + "\n" + "* P-cup logo + Peet_s Coffee\n" + "\n"
				+ "* Greeting [Good morning/afternoon/evening], <User_s first name>\n" + "\n" + "* Loading dial \n"
				+ "\n" + "* Text: Loading your latest rewards_\n" + "\n" + "User should see home/dashboard screen\n");
		Assert.assertNotNull(dashboardView, "Users dashboard does not displayed");

		LOGGER.info("Tap on ... at top right corner of home/dashboard screen");
		LOGGER.info("Tap on Profile Details");
		ProfileDetailsView profileDetailsView = dashboardView.getAccountProfileMenu().profileDetails();

		LOGGER.info(
				"Make sure all user info on account settings screen matches what was entered during sign up process");
		String firstNameUpd = profileDetailsView.getFirstname();
		String lastNameUpd = profileDetailsView.getLastname();
		String zipCodeUpd = profileDetailsView.getZipCode();
		String phoneUpd = profileDetailsView.getPhoneNumber();
		String emailUpd = profileDetailsView.getEmailAddress();
		Assert.assertEquals(firstNameUpd, firstname, "Firstname does not match");
		Assert.assertEquals(lastNameUpd, lastname, "Lastname does not match");
		Assert.assertEquals(zipCodeUpd, zipCode, "zipcode does not match");
		Assert.assertEquals(phoneUpd.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""),
				phone.replace("(", "").replace(")", "").replace("-", "").replace(" ", ""), "Phone does not updated");
		Assert.assertEquals(emailUpd, email, "Email does not match");

		LOGGER.info("Tap arrow at top left to return to more screen");
		AccountMenuMobileChunk accountMenuMobileChunk = profileDetailsView.goBack(AccountMenuMobileChunk.class);

		LOGGER.info("Tap on General Settings");
		GeneralSettingsView generalSettingsView = accountMenuMobileChunk.generalSettings();

		LOGGER.info("Make sure promotional emails toggle reflects whatever selection user chose at step 11");
		Assert.assertTrue(generalSettingsView.isPromoEmailOptionChecked(), "Promo email does not checked");

		LOGGER.info("Tap on back nav to return to more screen");
		accountMenuMobileChunk = generalSettingsView.goBack(AccountMenuMobileChunk.class);

		LOGGER.info("Tap sign out button");
		AuthenticationView authenticationView = accountMenuMobileChunk.signOut();

		LOGGER.info("User should be signed out successfully");
		Assert.assertNotNull(authenticationView, "User does not signed out");
	}
}
