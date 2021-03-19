package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.TestDataUtils;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.integrations.annotation.testidentification.ApplauseTestCaseId;
import com.applause.auto.mobile.components.AccountMenuMobileChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.AccountHistoryView;
import com.applause.auto.mobile.views.AuthenticationView;
import com.applause.auto.mobile.views.ChangePasswordView;
import com.applause.auto.mobile.views.CompleteAccountView;
import com.applause.auto.mobile.views.CreateAccountView;
import com.applause.auto.mobile.views.DashboardView;
import com.applause.auto.mobile.views.GeneralSettingsView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.PrivacyPolicyView;
import com.applause.auto.mobile.views.ProfileDetailsView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.mobile.views.TermsAndConditionsView;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAccountTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.WEB_UI},
      description = "625879")
  @ApplauseTestCaseId({"673972", "673971"})
  public void footerLinksTest() {
    logger.info("Launch the app and arrive at the first onboarding screen view");

    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");
    landingView.skipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("Scroll down and check the footer links");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 3);

    logger.info(
        "Make sure above the create account button is the copy and check-box: I agree to the Privacy Policy and Terms & Conditions");

    logger.info("Tap on Privacy Policy link");
    PrivacyPolicyView privacyPolicyView = createAccountView.privacyPolicy();

    logger.info("Make sure user is taken to Privacy Policy screen");
    Assert.assertNotNull(privacyPolicyView, "Privacy Policy does not displayed");

    logger.info("Tap at top left \"Peet's\" / close browser and open Peet's to return to the app");
    MobileHelper.activateApp();

    // after activate app is called on Android with app's package id - the landing view is loaded.
    // iOS loads previous state!
    landingView.createAccountNavigation();

    logger.info("Scroll down and check the footer links");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 3);

    logger.info("Tap on the Terms and Conditions link");
    TermsAndConditionsView termsAndConditionsView = createAccountView.termsAndConditions();

    logger.info("Make sure user is taken to Terms and Conditions screen");
    Assert.assertNotNull(termsAndConditionsView, "Terms And Conditions does not displayed");

    // logger.info("Tap at top left \"Peet's\" / close browser and open Peet's to return to the
    // app");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.MONITORING},
      description = "625883")
  @ApplauseTestCaseId({"674186", "674185"})
  public void signInEmailPasswordTest() {

    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    // TODO: add assert for "Make sure email field is displayed to user"

    // TODO: add assert for "Make sure password field is displayed to user"

    logger.info("Make sure show password button is displayed to user");
    Assert.assertTrue(
        signInView.isShowPasswordButtonDisplayed(), "Show password button is not displayed");

    logger.info("Make sure forgot password link is displayed to user");
    Assert.assertTrue(
        signInView.isForgotPasswordLinkDisplayed(), "Forgot password link is not displayed");

    logger.info("Make sure sign in button is disabled");
    Assert.assertFalse(signInView.isSignInButtonEnabled(), "Sign in button is not disabled");

    logger.info("Tap on Email Address field and enter valid email address");
    String username = MyAccountTestData.EMAIL;
    signInView.setUsername(username);

    logger.info("Enter valid password");
    signInView.setPassword(MyAccountTestData.PASSWORD);
    Assert.assertTrue(
        !signInView.getPassword().equals(TestData.PASSWORD), "Password does not hidden");

    logger.info("Tap on show password icon");
    signInView.showPassword();

    logger.info("Make sure password entered is displayed to user");
    Assert.assertEquals(
        signInView.getUnEncryptedPassword(), MyAccountTestData.PASSWORD, "Password does not shown");

    logger.info("Make sure password length requirement is displayed");
    Assert.assertTrue(
        signInView.isPasswordLengthRequirementDisplayed(),
        "Password length requirement is not displayed");

    logger.info("Make sure password contains numbers requirement is displayed");
    Assert.assertTrue(
        signInView.isPasswordContainsNumbersRequirementDisplayed(),
        "Password contains numbers requirement is not displayed");

    logger.info("Make sure password contains letters requirement is displayed");
    Assert.assertTrue(
        signInView.isPasswordContainsLettersRequirementDisplayed(),
        "Password contains letters requirement is not displayed");

    logger.info("Make sure sign in button is enabled");
    Assert.assertTrue(signInView.isSignInButtonEnabled(), "Sign in button is not enabled");

    logger.info("Tap Sign In button");
    DashboardView dashboardView = signInView.signIn();

    logger.info("User should see home/dashboard screen");
    Assert.assertNotNull(dashboardView, "Dashboard not displayed");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625925")
  @ApplauseTestCaseId({"674505", "674504"})
  public void accountSettingsEditProfileTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = this.create(LandingView.class);
    softAssert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");

    String username = MyAccountTestData.EDIT_EMAIL;
    signInView.setUsername(username);

    logger.info("Enter valid password");
    signInView.setPassword(MyAccountTestData.EDIT_EMAIL_PWD);

    logger.info("Tap Sign In button");
    DashboardView dashboardView = signInView.signIn();

    logger.info("Tap on ... at top right of home screen to view more screen");
    AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

    logger.info("Tap on Profile Details field/row");
    ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();

    logger.info(
        "Make sure user is taken to profile details screen:\n"
            + "* Header: Profile Details\n"
            + "* Back arrow at top left corner\n"
            + "* First name field\n"
            + "* Last name field\n"
            + "* Zip code (Optional) field\n"
            + "* Date of birth field (not editable)\n"
            + "* Phone number (Optional) field\n"
            + "* Email address field\n"
            + "* Change password link\n"
            + "* [Button] Save");
    softAssert.assertNotNull(profileDetailsView, "Profile details view does not passed validation");

    logger.info("Edit the fields that are editable");
    String firstNameOrig = profileDetailsView.getFirstname().trim();
    String lastNameOrig = profileDetailsView.getLastname().trim();
    String zipCodeOrig = profileDetailsView.getZipCode().trim();
    //    String emailOrig = profileDetailsView.getEmailAddress();
    logger.info("Firstname: " + firstNameOrig);
    logger.info("Lastname: " + lastNameOrig);
    logger.info("Zip: " + zipCodeOrig);

    String firstNameNew =
        firstNameOrig.matches("ApplauseUpdated$") ? "Applause" : "ApplauseUpdated";
    String lastNameNew = lastNameOrig.matches("QAUpdated$") ? "QA" : "QAUpdated";
    String zipCodeNew = zipCodeOrig.matches("11214$") ? "11215" : "11214";

    //    String emailNew = emailOrig.replace(".net", ".com");

    profileDetailsView.setFirstname(firstNameNew);
    profileDetailsView.setLastname(lastNameNew);
    profileDetailsView.setZipCode(zipCodeNew);
    //    profileDetailsView.setEmailAddress(emailNew);
    //    profileDetailsView.setConfirmEmailAddress(emailNew);

    logger.info("Tap Save button");
    accountMenuMobileChunk = profileDetailsView.save();

    logger.info("User should be directed back to the more screen");
    softAssert.assertNotNull(accountMenuMobileChunk, "User does not redirected to account menu");

    logger.info("Tap on Profile Details field again");
    profileDetailsView = accountMenuMobileChunk.profileDetails();

    logger.info("Make sure edits to profile are reflected correctly on profile details screen");
    String firstNameUpd = profileDetailsView.getFirstname();
    String lastNameUpd = profileDetailsView.getLastname();
    String zipCodeUpd = profileDetailsView.getZipCode();
    String emailUpd = profileDetailsView.getEmailAddress();
    softAssert.assertEquals(firstNameUpd, firstNameNew, "Firstname does not updated");
    softAssert.assertEquals(lastNameUpd, lastNameNew, "Lastname does not updated");
    softAssert.assertEquals(zipCodeUpd, zipCodeNew, "zipcode does not updated");
    //    softAssert.assertEquals(emailUpd, emailNew, "email does not updated");

    //    logger.info("Cleanup Restore original");
    //    profileDetailsView.setFirstname(firstNameOrig);
    //    profileDetailsView.setLastname(lastNameOrig);
    //    profileDetailsView.setZipCode(zipCodeOrig);
    //    //    profileDetailsView.setEmailAddress(emailOrig);
    //    //    profileDetailsView.setConfirmEmailAddress(emailOrig);
    profileDetailsView.save();

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625927")
  @ApplauseTestCaseId({"674507", "674506"})
  public void accountSettingsGeneralSettingsTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");
    signInView.setUsername(Constants.MyAccountTestData.EMAIL);

    logger.info("Enter valid password");
    signInView.setPassword(Constants.MyAccountTestData.PASSWORD);

    logger.info("Tap Sign In button");
    DashboardView dashboardView = signInView.signIn();

    logger.info("Tap on ... at top right of home screen to view more screen");
    AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

    logger.info(
        "Tap on ... at top right of home screen to view more screen\nTap on General Settings field/row");
    GeneralSettingsView generalSettingsView = accountMenuMobileChunk.generalSettings();

    logger.info(
        "Make sure user is taken to General Settings screen:\n"
            + "\n"
            + "* Header: General Settings\n"
            + "\n"
            + "* Notification Settings\n"
            + "\n"
            + "      o Promotional Emails\n"
            + "\n"
            + "      o Text: Receive offers, news, and more [Toggle off / on]\n"
            + "\n"
            + "      o Push Notifications\n"
            + "\n"
            + "      o Text: Receive alerts about offers, news, and more [Toggle off / on]\n"
            + "\n"
            + "* Privacy Settings\n"
            + "\n"
            + "      o Locations Services\n"
            + "\n"
            + "      o Text: Helps us locate your nearest Peet's [Toggle off / on]\n"
            + "\n");

    logger.info("Toggle Promotional Emails on");
    generalSettingsView.enablePromotionalEmails();

    logger.info("Promotional emails setting should turn on");
    Assert.assertTrue(
        generalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned on");

    logger.info("Toggle Promotional Emails off");
    generalSettingsView.disablePromotionalEmails();

    logger.info("Promotional emails setting should turn off");
    Assert.assertFalse(
        generalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned off");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625926")
  @ApplauseTestCaseId({"674511", "674510"})
  public void accountSettingsChangePasswordTest() {

    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = this.create(LandingView.class);
    // will fail as this view is shown randomly
    softAssert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");

    String VALID_USERNAME = MyAccountTestData.EMAIL_CHANGE_PWD;
    signInView.setUsername(VALID_USERNAME);

    logger.info("Enter valid password");
    String INITIAL_PASSWORD = MyAccountTestData.PASSWORD;
    String UPDATED_PASSWORD = "newPassword1";
    boolean isCleanUp = true;
    signInView.setPassword(INITIAL_PASSWORD);

    DashboardView dashboardView = null;
    try {
      logger.info("Tap Sign In button");
      dashboardView = signInView.signIn();
    } catch (Throwable throwable) {
      INITIAL_PASSWORD = "newPassword1";
      UPDATED_PASSWORD = MyAccountTestData.PASSWORD;
      signInView.dismissOkMessage();
      signInView.setPassword(INITIAL_PASSWORD);
      dashboardView = signInView.signIn();
      isCleanUp = false;
    }

    logger.info("Tap on ... at top right of home screen to view more screen");
    AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

    logger.info("Tap on Profile Details field/row");
    ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();

    logger.info("Tap on Change Password link");
    ChangePasswordView changePasswordView = profileDetailsView.changePassword();

    logger.info("Enter invalid current password");
    changePasswordView.setCurrentPassword("somewrongpassword1");

    logger.info("Enter new password");
    changePasswordView.setNewPassword(UPDATED_PASSWORD);

    logger.info("Tap Change Password button");
    changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

    logger.info(
        "Make sure user sees an error message: \"Operation failed, check your current password and try again\" and is not able to change password");
    softAssert.assertTrue(changePasswordView.verifyMessage(), "Error message was incorrect");
    changePasswordView = changePasswordView.dismissMessage(ChangePasswordView.class);

    logger.info("Tap show password icon");
    changePasswordView.showPassword();

    logger.info("Enter valid current password");
    changePasswordView.setCurrentPassword(INITIAL_PASSWORD);

    logger.info("Enter new password");
    changePasswordView.setNewPassword(UPDATED_PASSWORD);

    logger.info("Make sure password entered is displayed");
    softAssert.assertEquals(
        changePasswordView.getCurrentPasswordUnhide(),
        INITIAL_PASSWORD,
        "Show password button does not work");
    changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

    logger.info(
        "Make sure user sees success check mark and a UI alert:\n"
            + "\n"
            + "* Header: Change Password\n"
            + "\n"
            + "* Text: Your new password has been set [Okay]\n"
            + "\n");
    softAssert.assertEquals(
        changePasswordView.getMessage(),
        "Your new password has been set",
        "Wrong success password change message");

    logger.info("Tap okay to dismiss UI alert");
    changePasswordView = changePasswordView.dismissMessage(ChangePasswordView.class);

    logger.info("Tap back arrow");
    accountMenuMobileChunk = changePasswordView.goBack(AccountMenuMobileChunk.class);

    logger.info("Make sure user is directed to more screen");
    softAssert.assertNotNull(accountMenuMobileChunk, "User does not directed to more screen");

    logger.info("Scroll down and tap sign out button");
    accountMenuMobileChunk.signOut().isUserSignedOut();

    signInView = landingView.signIn();

    logger.info("Enter valid email address and old password");
    signInView.setUsername(VALID_USERNAME);
    signInView.setPassword(INITIAL_PASSWORD);

    logger.info("Tap Sign In button");
    signInView = signInView.signIn(SignInView.class);

    logger.info("Make sure user sees an error message and is not able to sign in");
    softAssert.assertEquals(
        signInView.getMessage(),
        "There was an error while trying to log in to your account. Please check all of the required fields and try submitting again.",
        "Error message not found");

    logger.info("Tap okay to dismiss UI alert");
    signInView.dismissMessage();

    logger.info("Enter new password");
    signInView.showPassword();
    signInView.setPassword(UPDATED_PASSWORD);

    logger.info("Tap Sign In button");
    dashboardView = signInView.signIn();

    logger.info("User should be able to sign in successfully with new password");
    softAssert.assertNotNull(dashboardView, "User does not logged in");

    // cleanup
    if (isCleanUp) {
      logger.info("Tap on ... at top right of home screen to view more screen");
      accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

      logger.info("Tap on Profile Details field/row");
      profileDetailsView = accountMenuMobileChunk.profileDetails();

      logger.info("Tap on Change Password link");
      changePasswordView = profileDetailsView.changePassword();

      logger.info("Enter current updated password");
      changePasswordView.setCurrentPassword(UPDATED_PASSWORD);

      logger.info("Enter initial password");
      changePasswordView.setNewPassword(INITIAL_PASSWORD);

      logger.info("Tap Change Password button");
      changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);
    }

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.MONITORING},
      description = "625880")
  @ApplauseTestCaseId({"674130", "674129"})
  public void createAccountEmailPassword() {

    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");
    long uniq = System.currentTimeMillis();

    landingView.skipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info(
        "VERIFY - User is taken to CREATE ACCOUNT screen:\n"
            + "\n"
            + "Verify the Header: CREATE ACCOUNT  ");

    logger.info("Tap on First Name field and enter valid first name");

    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Enter valid zip code / Skip this field");
    String zipCode = "11214";
    createAccountView.setZipCode(zipCode);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "May";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid ten digit phone number / Skip this field");
    String phone = TestDataUtils.PhoneNumberDataUtils.getRandomPhoneNumber();
    createAccountView.setPhoneNumber(phone);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);

    createAccountView.setEmailAddress(email);

    logger.info("Enter confirm email address");
    createAccountView.setConfirmEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Enter confirm password");
    createAccountView.setConfirmationPassword(password);

    logger.info("Tap on show password icon");
    createAccountView.showPassword();

    logger.info("Make sure password entered is displayed to user");
    Assert.assertEquals(
        createAccountView.getPassword().trim(), password, "Password does not displayed");

    logger.info("Tap on hide password icon");
    createAccountView.hidePassword();

    logger.info("Make sure password entered is hidden from user");
    Assert.assertNotEquals(
        createAccountView.getHiddenPassword(), password, "Password does not hidden");

    createAccountView.setPromo("TEST1966");

    logger.info(
        "At email opt in/out checkbox:\n"
            + "\n"
            + "(1) leave box checked\n"
            + "\n"
            + "OR\n"
            + "\n"
            + "(2) un-check it\n");
    logger.info(
        "Checkbox should be marked by default\n"
            + "\n"
            + "If user un-checks the box, make sure un-checking it removes the check mark from the box\n");
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(),
        "Email opt in checkbox does not marked by default");
    createAccountView.tapEmailOptIn();
    Assert.assertFalse(
        createAccountView.isEmailOptInChecked(), "Email opt in checkbox remains marked");
    createAccountView.tapEmailOptIn();
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(), "Email opt in checkbox does not marked");

    logger.info(
        "At Privacy Policy and Terms & Conditions\n"
            + "\n"
            + "(1) check box\n"
            + "\n"
            + "OR\n"
            + "\n"
            + "(2) un-check it\n");
    logger.info(
        "Checkbox should be unmarked by default\n"
            + "\n"
            + "Create account button should be grey and not activated if check box is not marked");
    Assert.assertFalse(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy and Terms and Conditions does not checked does not marked by default");

    Assert.assertFalse(
        createAccountView.isCreateAccountButtonEnabled(),
        "Create Account button does not disabled");

    logger.info("Tap on checkbox to agree to terms of service");
    createAccountView.checkPrivacyPolicyAndTermsAndConditions();

    logger.info(
        "Make sure checkbox is marked and create account button should be activated and turn gold");
    Assert.assertTrue(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy opt in checkbox does not marked");
    Assert.assertTrue(
        createAccountView.isCreateAccountButtonEnabled(), "Create Account button does not enabled");

    logger.info("Tap Create Account button");
    logger.info(
        "User sees a loading dial, then a check mark to indicate successful account creation");
    DashboardView dashboardView = createAccountView.createAccount();

    logger.info("User sees Peet's loading page briefly then user sees home/dashboard screen");
    Assert.assertNotNull(dashboardView, "Users dashboard does not displayed");

    logger.info("Swipe through dashboard feed");
    logger.info("User sees offer $2 OFF OA TEST in dashboard");
    Assert.assertTrue(
        dashboardView.lookUpOffer("$2 OFF OA TEST"), "User does not see 'offer $2 OFF OA TEST'");

    logger.info("Tap on ... at top right corner of home/dashboard screen");
    logger.info("Tap on Profile Details");
    ProfileDetailsView profileDetailsView = dashboardView.getAccountProfileMenu().profileDetails();

    logger.info(
        "All user info on profile details screen matches what was entered during create account process:\n"
            + "\n"
            + "First Name\n"
            + "Last Name\n"
            + "Zip Code (Optional)\n"
            + "Date of Birth > This field should be uneditable ** Text: Your birthday drink is on us\n"
            + "Phone Number (Optional) ** Text: Forgot your phone? Check in with this number.\n"
            + "Email Address\n"
            + "Change Password link\n"
            + "[Button] Save");
    String firstNameUpd = profileDetailsView.getFirstname();
    String lastNameUpd = profileDetailsView.getLastname();
    String zipCodeUpd = profileDetailsView.getZipCode();
    String dateOfBirthday = profileDetailsView.getDOB();
    String phoneUpd = profileDetailsView.getPhoneNumber();
    String emailUpd = profileDetailsView.getEmailAddress();
    Assert.assertEquals(firstNameUpd, firstname, "Firstname does not match");
    Assert.assertEquals(lastNameUpd, lastname, "Lastname does not match");
    Assert.assertEquals(zipCodeUpd, zipCode, "zipcode does not match");
    Assert.assertEquals(
        dateOfBirthday,
        String.format("%s %s, %s", dobMonth, dobDay, dobYear),
        "Date does not match");
    Assert.assertEquals(
        TestDataUtils.PhoneNumberDataUtils.getOnlyDigitsFromPhoneNumber(phoneUpd),
        TestDataUtils.PhoneNumberDataUtils.getOnlyDigitsFromPhoneNumber(phone),
        "Phone does not updated");
    Assert.assertEquals(emailUpd, email, "Email does not match");
    Assert.assertTrue(
        profileDetailsView.isChangePasswordLinkAvailable(),
        "Change password link does not available");
    Assert.assertTrue(profileDetailsView.isSaveButtonAvailable(), "Save button does not available");

    logger.info("Tap arrow at top left to return to more screen");
    AccountMenuMobileChunk accountMenuMobileChunk =
        profileDetailsView.goBack(AccountMenuMobileChunk.class);

    logger.info("Tap on General Settings");
    GeneralSettingsView generalSettingsView = accountMenuMobileChunk.generalSettings();

    logger.info(
        "Make sure promotional emails toggle reflects whatever selection user chose at step 14");
    Assert.assertTrue(
        generalSettingsView.isPromoEmailOptionChecked(), "Promo email does not checked");

    logger.info("Tap on back nav to return to more screen");
    accountMenuMobileChunk = generalSettingsView.goBack(AccountMenuMobileChunk.class);

    logger.info("Tap sign out button");
    AuthenticationView authenticationView = accountMenuMobileChunk.signOut();

    logger.info("User should be signed out successfully");
    Assert.assertNotNull(authenticationView.isUserSignedOut(), "User does not signed out");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "2980586")
  @ApplauseTestCaseId({"674182", "674181"})
  public void createAccountFieldValidation() {

    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");
    long uniq = System.currentTimeMillis();

    landingView.skipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info(
        "Fields:\n"
            + "\n"
            + "* First Name\n"
            + "\n"
            + "* Last Name\n"
            + "\n"
            + "* Zip Code (Optional)\n"
            + "\n"
            + "* Date of Birth\n"
            + "\n"
            + "      o Android Text: Your birthday drink is on us\n"
            + "\n"
            + "\n"
            + "      o iOS Text: Intended for users 13+ years old. Plus, get a birthday drink on us!\n"
            + "\n"
            + "* Phone Number (Optional)\n"
            + "\n"
            + "      o Text: Forgot your phone? Check in with this number.\n"
            + "\n"
            + "* Email Address"
            + "* Password with show/hide password icon\n"
            + "\n"
            + "      o Triggered Text: (x/_) At least 6 characters\n"
            + "\n"
            + "      o (x/_) At least 1 number\n"
            + "\n"
            + "      o (x/_) At least 1 letter\n"
            + "\n"
            + "Email opt in/out checkbox (checked by default)\n"
            + "\n"
            + "* Text: Yes, please send me emails with exclusive offers, rewards, news, and more.\n"
            + "\n"
            + "Privacy Policy/T&C checkbox (not checked by default)\n"
            + "\n"
            + "* Text: I agree to the Privacy Policy and Terms & Conditions\n"
            + "\n"
            + "[Button] Create Account (grey until all required fields are entered)");
    Assert.assertTrue(
        createAccountView.isFirstnameDisplayed(), "Firstname field does not displayed");
    Assert.assertTrue(createAccountView.isLastDisplayed(), "Lastname field does not displayed");
    Assert.assertTrue(createAccountView.isZipCodeDisplayed(), "Zip code field does not displayed");
    Assert.assertTrue(
        createAccountView.isDobTextDisplayed(), "Birthday drink text does not displayed");
    Assert.assertTrue(
        createAccountView.isEmailAddressDisplayed(), "Email address field does not displayed");
    Assert.assertTrue(
        createAccountView.isConfirmEmailAddressDisplayed(),
        "Email address field does not displayed");
    Assert.assertTrue(
        createAccountView.isPhoneNumberDisplayed(), "Phone number field does not displayed");
    Assert.assertTrue(createAccountView.isPasswordDisplayed(), "Password field does not displayed");
    Assert.assertTrue(
        createAccountView.isConfirmPasswordDisplayed(),
        "Confirm password field does not displayed");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 5);

    Assert.assertTrue(
        createAccountView.isPromocodeTextDisplayed(),
        "Promo code triggered text field does not displayed");
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(), "Email opt-in does not checked by default");
    Assert.assertFalse(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy field does not unchecked by default");
    Assert.assertTrue(
        createAccountView.isPasswordTextDisplayed(),
        "Password triggered text field does not displayed");

    MobileHelper.swipeWithCount(SwipeDirection.DOWN, 5);

    logger.info("Tap on First Name field and enter valid first name");

    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Enter valid zip code / Skip this field");
    String zipCode = "11214";
    createAccountView.setZipCode(zipCode);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "December";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid ten digit phone number / Skip this field");
    String phone = TestDataUtils.PhoneNumberDataUtils.getRandomPhoneNumber();
    createAccountView.setPhoneNumber(phone);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);
    createAccountView.setEmailAddress(email);
    createAccountView.setConfirmEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);
    createAccountView.setConfirmationPassword(password);

    logger.info("Tap on show password icon");
    createAccountView.showPassword();

    logger.info("Make sure password entered is displayed to user");
    Assert.assertEquals(
        createAccountView.getPassword().trim(), password, "Password does not displayed");

    logger.info("Tap on hide password icon");
    createAccountView.hidePassword();

    logger.info("Make sure password entered is hidden from user");
    Assert.assertNotEquals(
        createAccountView.getHiddenPassword(), password, "Password does not hidden");

    createAccountView.setPromo("");

    logger.info(
        "At email opt in/out checkbox:\n"
            + "\n"
            + "(1) leave box checked\n"
            + "\n"
            + "OR\n"
            + "\n"
            + "(2) un-check it\n");
    logger.info(
        "Checkbox should be marked by default\n"
            + "\n"
            + "If user un-checks the box, make sure un-checking it removes the check mark from the box\n");
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(),
        "Email opt in checkbox does not marked by default");
    createAccountView.tapEmailOptIn();
    Assert.assertFalse(
        createAccountView.isEmailOptInChecked(), "Email opt in checkbox does not marked");
    createAccountView.tapEmailOptIn();
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(), "Email opt in checkbox remains marked");

    logger.info(
        "At Privacy Policy and Terms & Conditions\n"
            + "\n"
            + "(1) check box\n"
            + "\n"
            + "OR\n"
            + "\n"
            + "(2) un-check it\n");
    logger.info(
        "Checkbox should be unmarked by default\n"
            + "\n"
            + "Create account button should be grey and not activated if check box is not marked");
    Assert.assertFalse(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy and Terms and Conditions does not checked does not marked by default");
    Assert.assertFalse(
        createAccountView.isCreateAccountButtonEnabled(),
        "Create Account button does not disabled");

    logger.info("Tap on checkbox to agree to terms of service");
    createAccountView.checkPrivacyPolicyAndTermsAndConditions();

    logger.info(
        "Make sure checkbox is marked and create account button should be activated and turn gold");
    Assert.assertTrue(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy opt in checkbox does not marked");
    Assert.assertTrue(
        createAccountView.isCreateAccountButtonEnabled(), "Create Account button does not enabled");

    logger.info("Tap Create Account button");
    DashboardView dashboardView = createAccountView.createAccount();

    logger.info(
        "User account should be created successfully:\n"
            + "\n"
            + "* User will see a loading dial, then a check mark to indicate successful account creation\n"
            + "\n"
            + "User should then see Peet's loading page briefly:\n"
            + "\n"
            + "* P-cup logo + Peet_s Coffee\n"
            + "\n"
            + "* Greeting [Good morning/afternoon/evening], <User_s first name>\n"
            + "\n"
            + "* Loading dial \n"
            + "\n"
            + "* Text: Loading your latest rewards_\n"
            + "\n"
            + "User should see home/dashboard screen\n");
    Assert.assertNotNull(dashboardView, "Users dashboard does not displayed");

    logger.info("Tap on ... at top right corner of home/dashboard screen");
    logger.info("Tap on Profile Details");
    ProfileDetailsView profileDetailsView = dashboardView.getAccountProfileMenu().profileDetails();

    logger.info(
        "Make sure all user info on account settings screen matches what was entered during sign up process");
    String firstNameUpd = profileDetailsView.getFirstname();
    String lastNameUpd = profileDetailsView.getLastname();
    String zipCodeUpd = profileDetailsView.getZipCode();
    String phoneUpd = profileDetailsView.getPhoneNumber();
    String emailUpd = profileDetailsView.getEmailAddress();
    String dob = profileDetailsView.getDOB();
    Assert.assertTrue(dob.matches("Dec.* 27, 2000"), "Unexpected DOB value: " + dob);
    Assert.assertEquals(firstNameUpd, firstname, "Firstname does not match");
    Assert.assertEquals(lastNameUpd, lastname, "Lastname does not match");
    Assert.assertEquals(zipCodeUpd, zipCode, "zipcode does not match");
    Assert.assertEquals(
        TestDataUtils.PhoneNumberDataUtils.getOnlyDigitsFromPhoneNumber(phoneUpd),
        TestDataUtils.PhoneNumberDataUtils.getOnlyDigitsFromPhoneNumber(phone),
        "Phone does not updated");
    Assert.assertEquals(emailUpd, email, "Email does not match");

    logger.info("Tap arrow at top left to return to more screen");
    AccountMenuMobileChunk accountMenuMobileChunk =
        profileDetailsView.goBack(AccountMenuMobileChunk.class);

    logger.info("Tap on General Settings");
    GeneralSettingsView generalSettingsView = accountMenuMobileChunk.generalSettings();

    logger.info(
        "Make sure promotional emails toggle reflects whatever selection user chose at step 11");
    Assert.assertTrue(
        generalSettingsView.isPromoEmailOptionChecked(), "Promo email does not checked");

    logger.info("Tap on back nav to return to more screen");
    accountMenuMobileChunk = generalSettingsView.goBack(AccountMenuMobileChunk.class);

    logger.info("Tap sign out button");
    AuthenticationView authenticationView = accountMenuMobileChunk.signOut();

    logger.info("User should be signed out successfully");
    Assert.assertTrue(authenticationView.isUserSignedOut(), "User does not signed out");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625929")
  @ApplauseTestCaseId({"674513", "674512"})
  public void accountSettingsAccountHistoryTest() {

    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = this.create(LandingView.class);
    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Earn Rewards.",
        "First screen text value is not correct");

    landingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");
    signInView.setUsername(MyAccountTestData.EMAIL);

    logger.info("Enter valid password");
    signInView.setPassword(MyAccountTestData.PASSWORD);

    logger.info("Tap Sign In button");
    DashboardView dashboardView = signInView.signIn();

    logger.info("Tap on ... at top right of home screen to view more screen");
    AccountMenuMobileChunk accountMenuMobileChunk = dashboardView.getAccountProfileMenu();

    logger.info("Tap on Account History field/row");
    AccountHistoryView accountHistoryView = accountMenuMobileChunk.accountHistory();

    logger.info(
        "Make sure user is taken to account history screen:\n"
            + "\n"
            + "* Header: Account History\n"
            + "\n"
            + "* Back arrow");
    Assert.assertNotNull(accountHistoryView, "User does not taken to account history screen");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625882")
  @ApplauseTestCaseId({"674184", "674183"})
  public void createAccountExistingWebUserTest() {
    logger.info("Launch the app and arrive at the first on boarding screen view");
    LandingView landingView = this.create(LandingView.class);

    CompleteAccountView completeAccountView =
        testHelper.signIn(
            landingView, TestData.USERNAME_625882, TestData.PASSWORD, CompleteAccountView.class);
    Assert.assertNotNull(completeAccountView, "Complete Account View does not displayed");
  }
}
