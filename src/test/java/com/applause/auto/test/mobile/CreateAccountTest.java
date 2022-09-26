package com.applause.auto.test.mobile;

import com.applause.auto.common.data.Constants.MobileTestData;
import com.applause.auto.common.data.Constants.MyAccountTestData;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.dto.SignUpUserDto;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.components.ConfirmationPopup;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.mobile.views.AccountActivityView;
import com.applause.auto.mobile.views.ChangePasswordView;
import com.applause.auto.mobile.views.CreateAccountView;
import com.applause.auto.mobile.views.HomeView;
import com.applause.auto.mobile.views.LandingView;
import com.applause.auto.mobile.views.MoreOptionsView;
import com.applause.auto.mobile.views.OnboardingView;
import com.applause.auto.mobile.views.PersonalSettingsView;
import com.applause.auto.mobile.views.PrivacyPolicyView;
import com.applause.auto.mobile.views.ProfileDetailsView;
import com.applause.auto.mobile.views.SignInView;
import com.applause.auto.mobile.views.TermsAndConditionsView;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.test.mobile.helpers.TestHelper;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateAccountTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.WEB_UI},
      description = "625879")
  public void footerLinksTest() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = openAppAndSkipOnboarding();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("Scroll down and check the footer links");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 7);

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
    MobileHelper.swipeWithCount(SwipeDirection.UP, 7);

    logger.info("Tap on the Terms and Conditions link");
    TermsAndConditionsView termsAndConditionsView = createAccountView.termsAndConditions();

    logger.info("Make sure user is taken to Terms and Conditions screen");
    Assert.assertNotNull(termsAndConditionsView, "Terms And Conditions does not displayed");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.MONITORING},
      description = "625883")
  public void signInEmailPasswordTest() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = openApp();

    logger.info("Skip Onboarding");
    LandingView landingView = onboardingView.skipOnboarding();

    logger.info("Tap Sign In");
    SignInView signInView = landingView.signIn();

    logger.info("Make sure email field is displayed to user");
    Assert.assertTrue(signInView.isEmailFieldDisplayed(), "Email address field is not displayed");

    logger.info("Make sure password field is displayed to user");
    Assert.assertTrue(signInView.isPasswordFieldDisplayed(), "Password field is not displayed");

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
    signInView.setEmail(username);

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
    HomeView homeView =
        signInView
            .signIn(HomeView.class)
            .getReorderTooltipComponent()
            .closeReorderTooltipIfDisplayed(HomeView.class)
            .getCheckInTooltipComponent()
            .closeCheckInTooltipIfDisplayed(HomeView.class)
            .getPointsTurnIntoRewardsTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class);

    logger.info("User should see home/dashboard screen");
    Assert.assertNotNull(homeView, "Home view not displayed");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625925")
  public void accountSettingsEditProfileTest() {
    SoftAssert softAssert = new SoftAssert();
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(
            MyAccountTestData.EDIT_EMAIL, MyAccountTestData.EDIT_EMAIL_PWD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("Tap on ... at top right of home screen to view more screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("Tap on Profile Details field/row");
    ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();
    softAssert.assertNotNull(profileDetailsView, "Profile details view does not passed validation");

    logger.info("Verify - Header: PROFILE DETAILS is displayed");
    softAssert.assertTrue(
        profileDetailsView.isHeaderDisplayed(), "Header isn't displayed properly");

    logger.info("Verify - Back arrow at top left corner is displayed");
    softAssert.assertTrue(
        profileDetailsView.isBackButtonDisplayed(), "Back button isn't displayed");

    logger.info("Verify - First name field is displayed");
    softAssert.assertTrue(
        profileDetailsView.isFirstNameFieldDisplayed(), "First name field isn't displayed");

    logger.info("Verify - Last name field is displayed");
    softAssert.assertTrue(
        profileDetailsView.isLastNameFieldDisplayed(), "Last name field isn't displayed");

    logger.info("Verify - Date of birth field (not editable) is displayed");
    softAssert.assertTrue(
        profileDetailsView.isBirthdayFieldDisplayed(), "Birthday field isn't displayed");

    logger.info("Verify - Phone number field is displayed");
    softAssert.assertTrue(
        profileDetailsView.isPhoneNumberFieldDisplayed(), "Phone number field isn't displayed");

    logger.info("Verify - Email address field is displayed");
    softAssert.assertTrue(
        profileDetailsView.isEmailFieldDisplayed(), "Email field isn't displayed");

    logger.info("Verify - Change password link is displayed");
    softAssert.assertTrue(
        profileDetailsView.isChangePasswordLinkAvailable(), "Change Password link isn't displayed");

    logger.info("Edit the fields that are editable");
    String firstNameOrig = profileDetailsView.getFirstname().trim();
    String lastNameOrig = profileDetailsView.getLastname().trim();
    String zipCodeOrig = profileDetailsView.getZipCode().trim();
    String phoneNumberOrig = profileDetailsView.getPhoneNumber().trim();
    logger.info("Firstname: " + firstNameOrig);
    logger.info("Lastname: " + lastNameOrig);
    logger.info("Zip: " + zipCodeOrig);
    logger.info("Phone Number: " + phoneNumberOrig);

    String firstNameNew =
        firstNameOrig.matches("ApplauseUpdated$") ? "Applause" : "ApplauseUpdated";
    String lastNameNew = lastNameOrig.matches("QAUpdated$") ? "QA" : "QAUpdated";
    String zipCodeNew = zipCodeOrig.matches("11214$") ? "11215" : "11214";
    String phoneNumberNew = WebHelper.getRandomPhoneNumber();
    profileDetailsView.setFirstname(firstNameNew);
    profileDetailsView.setLastname(lastNameNew);
    profileDetailsView.setZipCode(zipCodeNew);
    profileDetailsView.setPhoneNumber(phoneNumberNew);

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
    String phoneNumberUpd = profileDetailsView.getPhoneNumber();
    softAssert.assertEquals(firstNameUpd, firstNameNew, "Firstname does not updated");
    softAssert.assertEquals(lastNameUpd, lastNameNew, "Lastname does not updated");
    softAssert.assertEquals(zipCodeUpd, zipCodeNew, "zipcode does not updated");
    softAssert.assertEquals(phoneNumberUpd, phoneNumberNew, "Phone number does not updated");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625927")
  public void accountSettingsGeneralSettingsTest() {
    SoftAssert softAssert = new SoftAssert();
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen to view more screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("STEP - Tap on Personal Settings field/row");
    PersonalSettingsView personalSettingsView = accountMenuMobileChunk.personalSettings();

    logger.info("STEP - Toggle Promotional Emails on");
    personalSettingsView.enablePromotionalEmails();

    logger.info("VERIFY - Promotional emails setting should turn on");
    Assert.assertTrue(
        personalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned on");

    logger.info("STEP - Toggle Promotional Emails off");
    personalSettingsView.disablePromotionalEmails();

    logger.info("VERIFY - Promotional emails setting should turn off");
    Assert.assertFalse(
        personalSettingsView.isPromoEmailOptionChecked(), "Promo emails does not turned off");

    logger.info("STEP - Toggle Location Services on");
    personalSettingsView.toggleLocationServicesOn();

    logger.info("VERIFY - Toggle should be on in app");
    softAssert.assertTrue(
        personalSettingsView.isLocationServicesChecked(), "Location isn't enabled");

    logger.info("STEP - Toggle Location Services off");
    personalSettingsView.toggleLocationServicesOff();

    logger.info("VERIFY - Toggle should be off in app");
    softAssert.assertFalse(
        personalSettingsView.isLocationServicesChecked(), "Location isn't disabled");

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625926")
  public void accountSettingsChangePasswordTest() {
    SoftAssert softAssert = new SoftAssert();
    SignInView signInView = TestHelper.openSignInView();
    HomeView homeView;

    logger.info("STEP - Tap on Email Address field and enter valid email address");
    String VALID_USERNAME = MyAccountTestData.EMAIL_CHANGE_PWD;
    signInView.setEmail(VALID_USERNAME);

    logger.info("STEP - Enter valid password");
    String INITIAL_PASSWORD = MyAccountTestData.PASSWORD;
    String UPDATED_PASSWORD = "newPassword1";
    boolean isCleanUp = true;
    signInView.setPassword(INITIAL_PASSWORD);

    try {
      logger.info("STEP - Tap Sign In button");
      homeView = signInView.signIn(HomeView.class);
    } catch (Throwable throwable) {
      INITIAL_PASSWORD = "newPassword1";
      UPDATED_PASSWORD = MyAccountTestData.PASSWORD;
      signInView.dismissOkMessage();
      signInView.setPassword(INITIAL_PASSWORD);
      homeView = signInView.signIn(HomeView.class);
      isCleanUp = false;
    }
    homeView
        .getReorderTooltipComponent()
        .closeReorderTooltipIfDisplayed(HomeView.class)
        .getCheckInTooltipComponent()
        .closeCheckInTooltipIfDisplayed(HomeView.class)
        .getPointsTurnIntoRewardsTooltipComponent()
        .closeTooltipIfDisplayed(HomeView.class);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen to view more screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("STEP - Tap on Profile Details field/row");
    ProfileDetailsView profileDetailsView = accountMenuMobileChunk.profileDetails();

    logger.info("STEP - Tap on Change Password link");
    ChangePasswordView changePasswordView = profileDetailsView.changePassword();

    logger.info("VERIFY - Header: CHANGE PASSWORD is displayed");
    softAssert.assertTrue(
        changePasswordView.isChangePasswordHeaderDisplayed(),
        "CHANGE PASSWORD header is not displayed");

    logger.info("VERIFY - Back arrow button is displayed");
    softAssert.assertTrue(
        changePasswordView.isBackArrowButtonDisplayed(), "Back arrow button is not displayed");

    logger.info("VERIFY - Current password field with show/hide password icon is displayed");
    softAssert.assertTrue(
        changePasswordView.isCurrentPasswordFieldDisplayed(), "Current filed isn't displayed");

    logger.info("VERIFY - New password field with show/hide password icon is displayed");
    softAssert.assertTrue(
        changePasswordView.isNewPasswordFieldDisplayed(), "New filed isn't displayed");

    logger.info("VERIFY - Change Password button is displayed");
    softAssert.assertTrue(
        changePasswordView.isChangePasswordButtonDisplayed(),
        "Change Password button isn't displayed");

    logger.info("STEP - Enter invalid current password");
    changePasswordView.setCurrentPassword("somewrongpassword1");

    logger.info("STEP - Enter new password");
    changePasswordView.setNewPassword(UPDATED_PASSWORD);

    logger.info("STEP - Tap Change Password button");
    changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

    logger.info(
        "VERIFY - User sees an error message: \"Operation failed, check your current password and try again\" and is not able to change password");
    softAssert.assertTrue(changePasswordView.verifyMessage(), "Error message was incorrect");
    changePasswordView = changePasswordView.dismissMessage(ChangePasswordView.class);

    logger.info("STEP - Tap show password icon");
    changePasswordView.showPassword();

    logger.info("STEP - Enter valid current password");
    changePasswordView.setCurrentPassword(INITIAL_PASSWORD);

    logger.info("STEP - Enter new password");
    changePasswordView.setNewPassword(UPDATED_PASSWORD);

    logger.info("VERIFY - Password entered is displayed");
    softAssert.assertEquals(
        changePasswordView.getCurrentPasswordUnhide(),
        INITIAL_PASSWORD,
        "Show password button does not work");
    changePasswordView = changePasswordView.changePassword(ChangePasswordView.class);

    logger.info(
        "VERIFY - User sees success check mark and a UI alert: Your new password has been set [Okay]");
    softAssert.assertEquals(
        changePasswordView.getMessage(),
        "Your new password has been set",
        "Wrong success password change message");

    logger.info("STEP - Tap okay to dismiss UI alert");
    changePasswordView = changePasswordView.dismissMessage(ChangePasswordView.class);

    logger.info("STEP - Tap back arrow");
    accountMenuMobileChunk = changePasswordView.goBack(MoreOptionsView.class);
    if (!accountMenuMobileChunk.isProfileDetailsMenuItemDisplayed()) {
      accountMenuMobileChunk =
          SdkHelper.create(ProfileDetailsView.class).goBack(MoreOptionsView.class);
    }

    logger.info("VERIFY - User is directed to more screen");
    softAssert.assertNotNull(accountMenuMobileChunk, "User does not directed to more screen");

    logger.info("STEP - Scroll down and tap sign out button");
    LandingView landingView = accountMenuMobileChunk.signOut();
    signInView = landingView.signIn();

    logger.info("STEP - Enter valid email address and old password");
    signInView.setEmail(VALID_USERNAME);
    signInView.setPassword(INITIAL_PASSWORD);

    logger.info("STEP - Tap Sign In button");
    signInView = signInView.signIn(SignInView.class);

    logger.info("VERIFY - User sees an error message and is not able to sign in");
    softAssert.assertEquals(
        signInView.getMessage(),
        "There was an error while trying to log in to your account. Please check all of the required fields and try submitting again.",
        "Error message not found");

    logger.info("STEP - Tap okay to dismiss UI alert");
    signInView.dismissMessage();

    logger.info("STEP - Enter new password");
    signInView.setPassword(UPDATED_PASSWORD);

    logger.info("STEP - Tap Sign In button");
    signInView.showPassword();
    homeView =
        signInView
            .signIn(HomeView.class)
            .getPointsTurnIntoRewardsTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class);

    logger.info("VERIFY - User should be able to sign in successfully with new password");
    softAssert.assertNotNull(homeView, "User does not logged in");

    // cleanup
    if (isCleanUp) {
      logger.info("STEP - Tap on ... at top right of home screen to view more screen");
      accountMenuMobileChunk =
          homeView
              .getReorderTooltipComponent()
              .closeReorderTooltipIfDisplayed(HomeView.class)
              .getAccountProfileMenu();

      logger.info("STEP - Tap on Profile Details field/row");
      profileDetailsView = accountMenuMobileChunk.profileDetails();

      logger.info("STEP - Tap on Change Password link");
      changePasswordView = profileDetailsView.changePassword();

      logger.info("STEP - Enter current updated password");
      changePasswordView.setCurrentPassword(UPDATED_PASSWORD);

      logger.info("STEP - Enter initial password");
      changePasswordView.setNewPassword(INITIAL_PASSWORD);

      logger.info("STEP - Tap Change Password button");
      changePasswordView.changePassword(ChangePasswordView.class);
    }

    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION, TestNGGroups.MONITORING},
      description = "625880")
  public void createAccountEmailPassword() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    OnboardingView onboardingView = openApp();

    logger.info("Skip Onboarding");
    LandingView landingView = onboardingView.skipOnboarding();
    long uniq = System.currentTimeMillis();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("VERIFY - User is taken to CREATE ACCOUNT screen");
    Assert.assertEquals(createAccountView.getTitle(), "CREATE ACCOUNT", "Page title is wrong");

    logger.info("Tap on First Name field and enter valid first name");
    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "May";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);
    createAccountView.setEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Tap on show password icon");
    createAccountView.tapOnShowHidePassword();

    logger.info("Make sure password entered is displayed to user");
    Assert.assertEquals(
        createAccountView.getPassword().trim(), password, "Password does not displayed");

    logger.info("Tap on hide password icon");
    createAccountView.tapOnShowHidePassword();

    if (!MobileHelper.isAndroid()) {
      logger.info("Make sure password entered is hidden from user");
      Assert.assertNotEquals(createAccountView.getPassword(), password, "Password does not hidden");
    }

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
        "Make sure checkbox is marked and SdkHelper.create account button should be activated and turn gold");
    Assert.assertTrue(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy opt in checkbox does not marked");
    Assert.assertTrue(
        createAccountView.isCreateAccountButtonEnabled(), "Create Account button does not enabled");

    logger.info("Tap Create Account button");
    logger.info(
        "User sees a loading dial, then a check mark to indicate successful account creation");
    HomeView homeView =
        createAccountView
            .tapOnCreateAccount()
            .getSwipeTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class)
            .getPointsTurnIntoRewardsTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class);

    logger.info("User sees Peet's loading page briefly then user sees home/dashboard screen");
    Assert.assertNotNull(homeView, "Users Home view does not displayed");

    /**
     * TODO
     *
     * <p>Need to check this step. Currently there is no offers
     *
     * <p>logger.info("Swipe through dashboard feed"); logger.info("User sees offer $2 OFF OA TEST
     * in dashboard"); Assert.assertTrue( dashboardView.lookUpOffer("$2 OFF OA TEST"), "User does
     * not see 'offer $2 OFF OA TEST'");
     */
    logger.info("Tap on More button");
    MoreOptionsView moreOptionsView = homeView.tapOnMoreButton();

    logger.info("Tap on Profile Details");
    ProfileDetailsView profileDetailsView = moreOptionsView.profileDetails();

    logger.info(
        "All user info on profile details screen matches what was entered during SdkHelper.create account process:\n"
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
    String dateOfBirthday = profileDetailsView.getDOB();
    String emailUpd = profileDetailsView.getEmailAddress();
    Assert.assertEquals(firstNameUpd, firstname, "Firstname does not match");
    Assert.assertEquals(lastNameUpd, lastname, "Lastname does not match");
    Assert.assertEquals(
        dateOfBirthday,
        String.format("%s %s, %s", dobMonth, dobDay, dobYear),
        "Date does not match");
    Assert.assertEquals(emailUpd, email, "Email does not match");
    Assert.assertTrue(
        profileDetailsView.isChangePasswordLinkAvailable(),
        "Change password link does not available");
    Assert.assertTrue(profileDetailsView.isSaveButtonAvailable(), "Save button does not available");

    logger.info("Tap arrow at top left to return to more screen");
    moreOptionsView = profileDetailsView.goBack(MoreOptionsView.class);

    logger.info("Tap on General Settings");
    PersonalSettingsView personalSettingsView = moreOptionsView.personalSettings();

    logger.info(
        "Make sure promotional emails toggle reflects whatever selection user chose at step 14");
    Assert.assertTrue(
        personalSettingsView.isPromoEmailOptionChecked(), "Promo email does not checked");

    logger.info("Tap on back nav to return to more screen");
    moreOptionsView = personalSettingsView.goBack(MoreOptionsView.class);

    logger.info("Tap sign out button");
    landingView = moreOptionsView.signOut();

    logger.info("User should be signed out successfully");
    Assert.assertTrue(landingView.isSignInButtonDisplayed(), "User does not signed out");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "2980586")
  public void createAccountFieldValidation() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = testHelper.navigateToLandingView();

    Assert.assertEquals(
        landingView.getHeadingTextValue(),
        "Peetnik Rewards",
        "First screen text value is not correct");
    long uniq = System.currentTimeMillis();

    logger.info("Tap Create Account");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info(
        "Fields:\n"
            + "\n"
            + "* First Name\n"
            + "\n"
            + "* Last Name\n"
            + "\n"
            + "* Date of Birth\n"
            + "\n"
            + "      o Android Text: Your birthday drink is on us\n"
            + "\n"
            + "      o iOS Text: Intended for users 13+ years old. Plus, get a birthday drink on us!\n"
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
    Assert.assertTrue(
        createAccountView.isDobTextDisplayed(), "Birthday drink text does not displayed");
    Assert.assertTrue(
        createAccountView.isEmailAddressDisplayed(), "Email address field does not displayed");
    Assert.assertTrue(createAccountView.isPasswordDisplayed(), "Password field does not displayed");
    MobileHelper.swipeWithCount(SwipeDirection.UP, 8);

    Assert.assertTrue(
        createAccountView.isPromocodeTextDisplayed(),
        "Promo code triggered text field does not displayed");
    Assert.assertTrue(
        createAccountView.isEmailOptInChecked(), "Email opt-in does not checked by default");
    Assert.assertFalse(
        createAccountView.isPrivacyPolicyAndTermsAndConditionsChecked(),
        "Privacy Policy field does not unchecked by default");

    MobileHelper.swipeWithCount(SwipeDirection.DOWN, 5);

    logger.info("Tap on First Name field and enter valid first name");

    String firstname = "Firstname";
    createAccountView.setFirstname(firstname);

    logger.info("Enter valid last name");
    String lastname = "Lastname";
    createAccountView.setLastname(lastname);

    logger.info("Scroll through and select birthday");
    String dobDay = "27";
    String dobMonth = "December";
    String dobYear = "2000";
    createAccountView.setDOB(dobDay, dobMonth, dobYear);

    logger.info("Enter valid email address");
    String email = String.format("a+%s@gmail.com", uniq);
    createAccountView.setEmailAddress(email);

    logger.info("Enter valid password");
    String password = "Password1";
    createAccountView.setPassword(password);

    logger.info("Tap on show password icon");
    createAccountView.tapOnShowHidePassword();

    logger.info("Make sure password entered is displayed to user");
    Assert.assertEquals(
        createAccountView.getPassword().trim(), password, "Password does not displayed");

    logger.info("Tap on hide password icon");
    createAccountView.tapOnShowHidePassword();

    // TODO for android we can get password value even after tap on the hide password icon
    if (!MobileHelper.isAndroid()) {
      logger.info("Make sure password entered is hidden from user");
      Assert.assertNotEquals(createAccountView.getPassword(), password, "Password does not hidden");
    }

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
    HomeView homeView = createAccountView.createAccount(HomeView.class);
    homeView = TestHelper.closeHomeViewTooltipIfDisplayed(homeView);

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
    Assert.assertNotNull(homeView, "Users homeview does not displayed");

    logger.info("Tap on ... at top right corner of home/dashboard screen");
    logger.info("Tap on Profile Details");
    ProfileDetailsView profileDetailsView = homeView.getAccountProfileMenu().profileDetails();

    logger.info(
        "Make sure all user info on account settings screen matches what was entered during sign up process");
    String firstNameUpd = profileDetailsView.getFirstname();
    String lastNameUpd = profileDetailsView.getLastname();
    String emailUpd = profileDetailsView.getEmailAddress();
    String dob = profileDetailsView.getDOB();
    Assert.assertTrue(dob.matches("Dec.* 27, 2000"), "Unexpected DOB value: " + dob);
    Assert.assertEquals(firstNameUpd, firstname, "Firstname does not match");
    Assert.assertEquals(lastNameUpd, lastname, "Lastname does not match");
    Assert.assertEquals(emailUpd, email, "Email does not match");

    logger.info("Tap arrow at top left to return to more screen");
    MoreOptionsView accountMenuMobileChunk = profileDetailsView.goBack(MoreOptionsView.class);

    logger.info("Tap on General Settings");
    PersonalSettingsView personalSettingsView = accountMenuMobileChunk.personalSettings();

    logger.info(
        "Make sure promotional emails toggle reflects whatever selection user chose at step 11");
    Assert.assertTrue(
        personalSettingsView.isPromoEmailOptionChecked(), "Promo email does not checked");

    logger.info("Tap on back nav to return to more screen");
    accountMenuMobileChunk = personalSettingsView.goBack(MoreOptionsView.class);

    logger.info("Tap sign out button");
    landingView = accountMenuMobileChunk.signOut();

    logger.info("User should be signed out successfully");
    Assert.assertTrue(landingView.isSignInButtonDisplayed(), "User does not signed out");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625882")
  public void createAccountExistingWebUserTest() {
    logger.info("Launch the app and arrive at the first onboarding screen view");
    LandingView landingView = openAppAndSkipOnboarding();

    logger.info("Tap Create Account button");
    CreateAccountView createAccountView = landingView.createAccount();

    logger.info("Enter required fields and use existing web email address and password");
    SignUpUserDto newUser =
        SignUpUserDto.builder().email(TestData.USERNAME_625882).password(TestData.PASSWORD).build();
    ConfirmationPopup confirmationPopup =
        testHelper.createNewAccount(createAccountView, newUser, ConfirmationPopup.class);

    logger.info("Verify that User should be served error message");
    Assert.assertEquals(
        confirmationPopup.getPopupMessage(),
        MobileTestData.ACCOUNT_ALREADY_EXIST_MESSAGE,
        "Error popup message is wrong");

    logger.info("Tap OK to dismiss error message");
    createAccountView = confirmationPopup.tapOnOkay(CreateAccountView.class);

    logger.info("Tap X to return to Peetnik Rewards authentication screen");
    landingView = createAccountView.close();

    logger.info("Tap Sign In button");
    SignInView signInView = landingView.signIn();

    logger.info("Tap on Email Address field and enter valid email address");
    signInView.setEmail(newUser.getEmail());

    logger.info("Enter valid password");
    signInView.setPassword(newUser.getPassword());

    logger.info("Tap Sign In button");
    HomeView homeView =
        signInView
            .signIn(HomeView.class)
            .getPointsTurnIntoRewardsTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class)
            .getPointsTurnIntoRewardsTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class)
            .getSwipeTooltipComponent()
            .closeTooltipIfDisplayed(HomeView.class);

    logger.info("Tap on More amd then Profile Details");
    ProfileDetailsView profileDetailsView = homeView.tapOnMoreButton().profileDetails();

    logger.info("Verify that All user info on profile details screen matches");
    String firstNameOrig = profileDetailsView.getFirstname().trim();
    String lastNameOrig = profileDetailsView.getLastname().trim();
    String emailUpd = profileDetailsView.getEmailAddress();
    String dob = profileDetailsView.getDOB();
    Assert.assertEquals(firstNameOrig, "Applause625882", "First name didn't match");
    Assert.assertEquals(lastNameOrig, "Test", "First name didn't match");
    Assert.assertTrue(dob.matches("Jan.* 25, 2000"), "Unexpected DOB value: " + dob);
    Assert.assertEquals(emailUpd, newUser.getEmail(), "Email does not match");

    logger.info("Navigate back");
    MoreOptionsView moreOptionsView = profileDetailsView.goBack(MoreOptionsView.class);

    logger.info("Tap sign out button");
    landingView = moreOptionsView.signOut();

    logger.info("User should be signed out successfully");
    Assert.assertTrue(landingView.isSignInButtonDisplayed(), "User does not signed out");
  }

  @Test(
      groups = {TestNGGroups.ONBOARDING, TestNGGroups.REGRESSION},
      description = "625929")
  public void accountSettingsAccountHistoryTest() {
    SoftAssert softAssert = new SoftAssert();
    HomeView homeView =
        TestHelper.skipOnboardingAndLogin(MyAccountTestData.EMAIL, MyAccountTestData.PASSWORD);
    Assert.assertNotNull(homeView, "Home View does not displayed");

    logger.info("STEP - Tap on ... at top right of home screen to view more screen");
    MoreOptionsView accountMenuMobileChunk = homeView.getAccountProfileMenu();

    logger.info("STEP - Tap on Account Activity field/row");
    AccountActivityView accountActivityView = accountMenuMobileChunk.accountActivity();

    logger.info("VERIFY - Make sure user is taken to account history screen");
    softAssert.assertNotNull(accountActivityView, "User does not taken to account history screen");

    logger.info("VERIFY - Back button is displayed");
    softAssert.assertTrue(
        accountActivityView.isBackButtonDisplayed(), "Back button is not displayed");

    logger.info("VERIFY - Back 'ACCOUNT ACTIVITY' header is displayed");
    softAssert.assertTrue(
        accountActivityView.isHeaderDisplayed(), "'ACCOUNT ACTIVITY' header is not displayed");

    softAssert.assertAll();
  }
}
