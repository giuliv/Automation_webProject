package com.applause.auto.test.new_web.my_account;

import com.applause.auto.common.data.Constants.MyAccountLeftMenuOption;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.*;
import com.applause.auto.new_web.views.my_account.MyAccountPage;
import com.applause.auto.new_web.views.my_account.MyAccountSettingsPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SettingsTests extends BaseTest {

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102938")
  public void myAccountSettingsElementsTest() {
    SoftAssert softAssert = new SoftAssert();

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("Verify Account information title is displayed");
    softAssert.assertTrue(
        settingsPage.isAccountInformationTitleDisplayed(),
        "Account information title isn't displayed");

    logger.info("Verify name and email is matched with user");
    softAssert.assertEquals(
        settingsPage.getName(), TestData.ANDREW_TESTER, "Name isn't matched with user name");
    softAssert.assertEquals(
        settingsPage.getEmail(), TestData.PEETS_USERNAME, "Email isn't matched with user email");

    logger.info("Verify phone number and password should display.");
    softAssert.assertTrue(settingsPage.isPhoneNumberDisplayed(), "Phone number isn't displayed");
    softAssert.assertTrue(settingsPage.isPasswordDisplayed(), "Password isn't displayed");
    softAssert.assertAll();
  }

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102939")
  public void myAccountSettingsEditPasswordTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Click on Create an account");
    CreateAccountPage createAccountPage = signInPage.clickOnCreateAccountButton();
    Assert.assertNotNull(createAccountPage, "Failed to navigate to the Create account page.");

    logger.info("3. Click on Create");
    String email = WebHelper.getRandomMail();
    MyAccountPage myAccountPage =
        createAccountPage.createAccount(
            WebTestData.FIRST_NAME,
            WebTestData.LAST_NAME,
            email,
            TestData.WEB_PASSWORD,
            TestData.WEB_PASSWORD);

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("5. Click on Edit password");
    ResetPasswordPage resetPasswordPage = settingsPage.clickEditPasswordLink();
    Assert.assertNotNull(resetPasswordPage, "Failed to navigate to the Reset password page.");

    logger.info("6. Enter email.");
    resetPasswordPage.enterEmail(email);

    logger.info("7. Click on Submit");
    PasswordRecoveryPage passwordRecoveryPage = resetPasswordPage.clickSubmitButton();
    Assert.assertTrue(
        passwordRecoveryPage.isSuccessfulMessageDisplayed(email),
        "Successful message isn't displayed");
  }

  @Test(
      groups = {TestNGGroups.MY_ACCOUNT},
      description = "11102940")
  public void myAccountSettingsEmailPreferencesTest() {

    logger.info("1. Navigate to Sign in page");
    SignInPage signInPage = navigateToSignInPage();
    Assert.assertNotNull(signInPage, "Failed to navigate to the Sign in page.");

    logger.info("2. Enter valid credentials.");
    signInPage.enterEmail(TestData.PEETS_USERNAME);
    signInPage.enterPassword(TestData.PEETS_PASSWORD);

    logger.info("3. Click on Sign in");
    MyAccountPage myAccountPage = signInPage.clickOnSignInButton();

    logger.info("4. Click on Settings");
    MyAccountSettingsPage settingsPage =
        myAccountPage.getLeftMenu().clickMenuOption(MyAccountLeftMenuOption.SETTINGS);

    logger.info("5. Click on Edit email preferences");
    MyAccountEmailPreferencesPage emailPreferencesPage =
        settingsPage.clickEditEmailPreferencesLink();
    Assert.assertNotNull(emailPreferencesPage, "Failed to navigate to the Email preferences page.");

    logger.info("6. Select a few checkboxes");
    emailPreferencesPage.selectRandomCheckboxes(3);

    logger.info("7. Click on Submit");
    emailPreferencesPage = emailPreferencesPage.clickSubmitButton();

    logger.info("Verify Success! Email settings updated. displays.");
    Assert.assertTrue(
        emailPreferencesPage.messageIsDisplayed("Success! Email settings updated."),
        "Message isn't displayed");

    logger.info("8. Click on Unsubscribe");
    emailPreferencesPage = emailPreferencesPage.clickUnsubscribeButton();

    logger.info("Verify Your email settings have been updated displays.");
    Assert.assertTrue(
        emailPreferencesPage.messageIsDisplayed("Your email settings have been updated."),
        "Message isn't displayed");
  }
}
