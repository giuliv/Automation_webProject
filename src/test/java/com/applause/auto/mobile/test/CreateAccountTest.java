package com.applause.auto.mobile.test;

import java.util.Random;

import com.applause.auto.pageframework.helpers.PeetsMobileHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.applause.auto.framework.pageframework.device.DeviceViewFactory;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.pageframework.chunks.AccountMenuMobileChunk;
import com.applause.auto.pageframework.helpers.MobileHelper;
import com.applause.auto.pageframework.testdata.TestConstants;
import com.applause.auto.pageframework.views.AccountHistoryView;
import com.applause.auto.pageframework.views.AuthenticationView;
import com.applause.auto.pageframework.views.ChangePasswordView;
import com.applause.auto.pageframework.views.CompleteAccountView;
import com.applause.auto.pageframework.views.CreateAccountView;
import com.applause.auto.pageframework.views.DashboardView;
import com.applause.auto.pageframework.views.GeneralSettingsView;
import com.applause.auto.pageframework.views.LandingView;
import com.applause.auto.pageframework.views.PeetsSettingsView;
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

		landingView.skipOnboarding();

		LOGGER.info("Tap Create Account");
		CreateAccountView createAccountView = landingView.createAccount();

		LOGGER.info("Scroll down and check the footer links");
		MobileHelper.scrollToBottom(3);

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

		landingView.skipOnboarding();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		String username = TestConstants.MyAccountTestData.EMAIL;
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.MyAccountTestData.FIRST_NAME);
		Assert.assertTrue(!signInView.getPassword().equals(TestConstants.TestData.PASSWORD),
				"Password does not hidden");

		LOGGER.info("Tap on show password icon");
		signInView.showPassword();

		LOGGER.info("Make sure password entered is displayed to user");
		Assert.assertEquals(signInView.getUnEncryptedPassword(), TestConstants.MyAccountTestData.PASSWORD,
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
		softAssert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOnboarding();

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
		softAssert.assertNotNull(profileDetailsView, "Profile details view does not passed validation");

		LOGGER.info("Edit the fields that are editable");
		String firstNameOrig = profileDetailsView.getFirstname();
		String lastNameOrig = profileDetailsView.getLastname();
		String zipCodeOrig = profileDetailsView.getZipCode();
		String emailOrig = profileDetailsView.getEmailAddress();

		String firstNameNew = "ApplauseUpdated";
		String lastNameNew = "QAUpdated";
		String zipCodeNew = "11214";

		String emailNew = emailOrig.replace(".com", ".net");

		profileDetailsView.setFirstname(firstNameNew);
		profileDetailsView.setLastname(lastNameNew);
		profileDetailsView.setZipCode(zipCodeNew);
		profileDetailsView.setEmailAddress(emailNew);
		profileDetailsView.setConfirmEmailAddress(emailNew);

		LOGGER.info("Tap Save button");
		accountMenuMobileChunk = profileDetailsView.save();

		LOGGER.info("User should be directed back to the more screen");
		softAssert.assertNotNull(accountMenuMobileChunk, "User does not redirected to account menu");

		LOGGER.info("Tap on Profile Details field again");
		profileDetailsView = accountMenuMobileChunk.profileDetails();

		LOGGER.info("Make sure edits to profile are reflected correctly on profile details screen");
		String firstNameUpd = profileDetailsView.getFirstname();
		String lastNameUpd = profileDetailsView.getLastname();
		String zipCodeUpd = profileDetailsView.getZipCode();
		String emailUpd = profileDetailsView.getEmailAddress();
		softAssert.assertEquals(firstNameUpd, firstNameNew, "Firstname does not updated");
		softAssert.assertEquals(lastNameUpd, lastNameNew, "Lastname does not updated");
		softAssert.assertEquals(zipCodeUpd, zipCodeNew, "zipcode does not updated");
		softAssert.assertEquals(emailUpd, emailNew, "email does not updated");

		LOGGER.info("Cleanup Restore original");
		profileDetailsView.setFirstname(firstNameOrig);
		profileDetailsView.setLastname(lastNameOrig);
		profileDetailsView.setZipCode(zipCodeOrig);
		profileDetailsView.setEmailAddress(emailOrig);
		profileDetailsView.setConfirmEmailAddress(emailOrig);
		profileDetailsView.save();

		softAssert.assertAll();

	}

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625927")
	public void accountSettingsGeneralSettingsTest() {

		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOnboarding();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		String username = "a+test625927@a.com";
		signInView.setUsername(username);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.TestData.PASSWORD);

		LOGGER.info("Tap Sign In button");
		DashboardView dashboardView = signInView.signIn();

		LOGGER.info("Tap on ... at top right of home screen to view more screen");
		AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

		LOGGER.info("Tap on ... at top right of home screen to view more screen\nTap on General Settings field/row");
		GeneralSettingsView generalSettingsView = accountMenuMobileChunk.generalSettings();

		LOGGER.info("Make sure user is taken to General Settings screen:\n" + "\n" + "* Header: General Settings\n"
				+ "\n" + "* Notification Settings\n" + "\n" + "      o Promotional Emails\n" + "\n"
				+ "      o Text: Receive offers, news, and more [Toggle off / on]\n" + "\n"
				+ "      o Push Notifications\n" + "\n"
				+ "      o Text: Receive alerts about offers, news, and more [Toggle off / on]\n" + "\n"
				+ "* Privacy Settings\n" + "\n" + "      o Locations Services\n" + "\n"
				+ "      o Text: Helps us locate your nearest Peet's [Toggle off / on]\n" + "\n");

		LOGGER.info("Toggle Promotional Emails on");
		generalSettingsView.enablePromotionalEmails();

		LOGGER.info("Promotional emails setting should turn on");
		Assert.assertTrue(generalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned on");

		LOGGER.info("Toggle Promotional Emails off");
		generalSettingsView.disablePromotionalEmails();

		LOGGER.info("Promotional emails setting should turn off");
		Assert.assertFalse(generalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned off");

	}

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625926")
	public void accountSettingsChangePasswordTest() {

		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		softAssert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOnboarding();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		String VALID_USERNAME = "a+625926@a.com";
		signInView.setUsername(VALID_USERNAME);

		LOGGER.info("Enter valid password");
		String INITIAL_PASSWORD = "Password1";
		signInView.setPassword(INITIAL_PASSWORD);

		LOGGER.info("Tap Sign In button");
		DashboardView dashboardView = signInView.signIn();

		LOGGER.info("Tap on ... at top right of home screen to view more screen");
		AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

		LOGGER.info("Tap on Profile Details field/row");
		ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();

		LOGGER.info("Tap on Change Password link");
		ChangePasswordView changePasswordView = profileDetailsView.changePassword();

		LOGGER.info("Enter invalid current password");
		changePasswordView.setCurrentPassword("somewrongpassword1");

		LOGGER.info("Enter new password");
		String UPDATED_PASSWORD = "newPassword1";
		changePasswordView.setNewPassword(UPDATED_PASSWORD);

		LOGGER.info("Tap Change Password button");
		changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

		LOGGER.info(
				"Make sure user sees an error message: \"Operation failed, check your current password and try again\" and is not able to change password");
		softAssert.assertTrue(changePasswordView.verifyMessage(), "Error message was incorrect");
		changePasswordView = changePasswordView.dismissMessage(ChangePasswordView.class);

		LOGGER.info("Tap show password icon");
		changePasswordView.showPassword();

		LOGGER.info("Enter valid current password");
		changePasswordView.setCurrentPassword(INITIAL_PASSWORD);

		LOGGER.info("Make sure password entered is displayed");
		softAssert.assertEquals(changePasswordView.getCurrentPasswordUnhide(), INITIAL_PASSWORD,
				"Show password button does not work");
		changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

		LOGGER.info("Make sure user sees success check mark and a UI alert:\n" + "\n" + "* Header: Change Password\n"
				+ "\n" + "* Text: Your new password has been set [Okay]\n" + "\n");
		softAssert.assertEquals(changePasswordView.getMessage(), "Your new password has been set",
				"Wrong success password change message");

		LOGGER.info("Tap okay to dismiss UI alert");
		profileDetailsView = changePasswordView.dismissMessage(ProfileDetailsView.class);

		LOGGER.info("Tap back arrow");
		accountMenuMobileChunk = profileDetailsView.goBack(AccountMenuMobileChunk.class);

		LOGGER.info("Make sure user is directed to more screen");
		softAssert.assertNotNull(accountMenuMobileChunk, "User does not directed to more screen");

		LOGGER.info("Scroll down and tap sign out button");
		AuthenticationView authenticationView = accountMenuMobileChunk.signOut();

		signInView = landingView.signIn();

		LOGGER.info("Enter valid email address and old password");
		signInView.setUsername(VALID_USERNAME);
		signInView.setPassword(INITIAL_PASSWORD);

		LOGGER.info("Tap Sign In button");
		signInView = signInView.signIn(SignInView.class);

		LOGGER.info("Make sure user sees an error message and is not able to sign in");
		softAssert.assertEquals(signInView.getMessage(),
				"The email and password you entered don't match. Please try again.", "Error message not found");

		LOGGER.info("Tap okay to dismiss UI alert");
		signInView.dismissMessage();

		LOGGER.info("Enter new password");
		signInView.showPassword();
		signInView.setPassword(UPDATED_PASSWORD);

		LOGGER.info("Tap Sign In button");
		dashboardView = signInView.signIn();

		LOGGER.info("User should be able to sign in successfully with new password");
		softAssert.assertNotNull(dashboardView, "User does not logged in");

		// cleanup
		LOGGER.info("Tap on ... at top right of home screen to view more screen");
		accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

		LOGGER.info("Tap on Profile Details field/row");
		profileDetailsView = accountMenuMobileChunk.profileDetails();

		LOGGER.info("Tap on Change Password link");
		changePasswordView = profileDetailsView.changePassword();

		LOGGER.info("Enter current updated password");
		changePasswordView.setCurrentPassword(UPDATED_PASSWORD);

		LOGGER.info("Enter initial password");
		changePasswordView.setNewPassword(INITIAL_PASSWORD);

		LOGGER.info("Tap Change Password button");
		changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

		softAssert.assertAll();

	}

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625880")
	public void createAccountEmailPassword() {

		LOGGER.info("Launch the app and arrive at the first onboarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");
		long uniq = System.currentTimeMillis();

		landingView.skipOnboarding();

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
		String dobYear = "2000";
		createAccountView.setDOB(dobDay, dobMonth, dobYear);

		LOGGER.info("Enter valid ten digit phone number / Skip this field");
		Random random = new Random();
		String phone = "2";
		for (int i = 0; i < 9; i++) {
			phone += "" + random.nextInt(9);
		}
		createAccountView.setPhoneNumber(phone);

		LOGGER.info("Enter valid email address");
		String email = String.format("a+%s@gmail.com", uniq);
		createAccountView.setEmailAddress(email);
		createAccountView.setConfirmEmailAddress(email);

		LOGGER.info("Enter valid password");
		String password = "Password1";
		createAccountView.setPassword(password);
		createAccountView.setConfirmationPassword(password);

		LOGGER.info("Tap on show password icon");
		createAccountView.showPassword();

		LOGGER.info("Make sure password entered is displayed to user");
		Assert.assertEquals(createAccountView.getPassword().trim(), password, "Password does not displayed");

		LOGGER.info("Tap on hide password icon");
		createAccountView.hidePassword();

		LOGGER.info("Make sure password entered is hidden from user");
		Assert.assertNotEquals(createAccountView.getHiddenPassword(), password, "Password does not hidden");

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
				+ "Create account button should be grey and not activated if check box is not marked\n");
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

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625929")
	public void accountSettingsAccountHistoryTest() {

		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);
		Assert.assertEquals(landingView.getHeadingTextValue(), "Earn Rewards.",
				"First screen text value is not correct");

		landingView.skipOnboarding();

		LOGGER.info("Tap Sign In");
		SignInView signInView = landingView.signIn();

		LOGGER.info("Tap on Email Address field and enter valid email address");
		signInView.setUsername(TestConstants.MyAccountTestData.EMAIL);

		LOGGER.info("Enter valid password");
		signInView.setPassword(TestConstants.MyAccountTestData.PASSWORD);

		LOGGER.info("Tap Sign In button");
		DashboardView dashboardView = signInView.signIn();

		LOGGER.info("Tap on ... at top right of home screen to view more screen");
		AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

		LOGGER.info("Tap on Account History field/row");
		AccountHistoryView accountHistoryView = accountMenuMobileChunk.accountHistory();

		LOGGER.info("Make sure user is taken to account history screen:\n" + "\n" + "* Header: Account History\n" + "\n"
				+ "* Back arrow");
		Assert.assertNotNull(accountHistoryView, "User does not taken to account history screen");

	}

	@Test(groups = { TestConstants.TestNGGroups.ONBOARDING }, description = "625882")
	public void createAccountExistingWebUserTest() {
		LOGGER.info("Launch the app and arrive at the first on boarding screen view");
		LandingView landingView = DeviceViewFactory.create(LandingView.class);

		CompleteAccountView completeAccountView = peetsMobileHelper.signIn(landingView,
				TestConstants.TestData.USERNAME_625882, TestConstants.TestData.PASSWORD, CompleteAccountView.class);
		Assert.assertNotNull(completeAccountView, "Complete Account View does not displayed");
	}

}
