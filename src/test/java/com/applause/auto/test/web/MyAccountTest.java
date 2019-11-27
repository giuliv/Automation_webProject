package com.applause.auto.test.web;

import com.applause.auto.common.data.Constants;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.web.components.MiniCartContainerChunk;
import com.applause.auto.web.components.VerifyYourAddressDetailsChunk;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.AddBillingAddressPage;
import com.applause.auto.web.views.AddShippingAddressPage;
import com.applause.auto.web.views.AddressBookPage;
import com.applause.auto.web.views.AssociateNewCardPage;
import com.applause.auto.web.views.CheckoutConfirmationPage;
import com.applause.auto.web.views.CheckoutPage;
import com.applause.auto.web.views.CheckoutPaymentMethodPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.CoffeeProductPage;
import com.applause.auto.web.views.EditAccountInformationPage;
import com.applause.auto.web.views.EditBillingAddressPage;
import com.applause.auto.web.views.EditPaymentMethodPage;
import com.applause.auto.web.views.EditShippingAddressPage;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.MyAccountManageSubscriptionPage;
import com.applause.auto.web.views.MyAccountMyOrdersPage;
import com.applause.auto.web.views.MyAccountMySuscriptionsPage;
import com.applause.auto.web.views.MyAccountOrderDetailPage;
import com.applause.auto.web.views.MyAccountPage;
import com.applause.auto.web.views.MyAccountPeetsCardPage;
import com.applause.auto.web.views.PaymentMethodsPage;
import com.applause.auto.web.views.ShopCoffeePage;
import com.applause.auto.web.views.SignInPage;
import com.applause.auto.web.views.SignUpPage;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyAccountTest extends BaseTest {

  private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());
  private WebHelper webHelper = new WebHelper();

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133894")
  public void myAccountDashboard() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Verify My Subscriptions Section");
    Assert.assertTrue(
        myAccountPage.isMySubscriptionsCardDisplayed(), "My Subscription section is not displayed");
    Assert.assertTrue(
        myAccountPage.isManageSubscriptionsLinkDisplayed(),
        "Subscription Manage link is not displayed");
    Assert.assertTrue(
        myAccountPage.isEditSubscriptionsButtonDisplayed(),
        "Subscription Edit button is not displayed");

    logger.info("4. Verify Order History Section");
    Assert.assertTrue(
        myAccountPage.isOrderHistoryCardDisplayed(), "Order History section is not displayed");
    Assert.assertTrue(
        myAccountPage.isViewAllOrdersLinkDisplayed(), "View All Orders link is not displayed");
    Assert.assertTrue(myAccountPage.isOrderRatingDisplayed(), "Order Rating is not displayed");

    logger.info("5. Verify Payment Section");
    Assert.assertTrue(myAccountPage.isPaymentCardDisplayed(), "Payment section is not displayed");
    Assert.assertTrue(
        myAccountPage.isPaymentManageCardsLinkDisplayed(),
        "Payment Manage Cards link is not displayed");
    Assert.assertTrue(
        myAccountPage.isPaymentCardImageDisplayed(), "Payment Card image is not displayed");

    logger.info("6. Verify Peets Card Section");
    Assert.assertTrue(
        myAccountPage.isPeetsCardsCardDisplayed(), "Peets Card section is not displayed");
    Assert.assertTrue(
        myAccountPage.isPeetsCardManageCardsLinkDisplayed(),
        "Peets Card Manage Cards link is not displayed");

    logger.info("7. Verify Settings Card Section");
    Assert.assertTrue(myAccountPage.isSettingsCardDisplayed(), "Settings section is not displayed");
    Assert.assertTrue(
        myAccountPage.isGoToAddressBookLinkDisplayed(), "Go To Address Book link is not displayed");

    logger.info("8. Verify Billing Address Section");
    Assert.assertTrue(
        myAccountPage.isBillingAddressDisplayed(), "Billing Address section is not displayed");
    Assert.assertTrue(
        myAccountPage.isBillingAddressEditLinkDisplayed(),
        "Billing Address Edit link is not displayed");

    logger.info("9. Verify Shipping Address Section");
    Assert.assertTrue(
        myAccountPage.isShippingAddressDisplayed(), "Shipping Address section is not displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133897")
  public void myAccountPaymentMethod() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Select Payment");
    PaymentMethodsPage paymentMethodsPage = myAccountPage.clickPayment();
    Assert.assertNotNull(paymentMethodsPage, "Payment Methods page did not display");

    logger.info("4. Verify Payment Method Elements");
    Assert.assertTrue(paymentMethodsPage.isCreditCardDisplayed(), "Credit Card is not displayed");
    Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
    Assert.assertTrue(
        paymentMethodsPage.isAddCreditCardButtonDisplayed(),
        "Add Credit Card button is not displayed");
    Assert.assertTrue(
        paymentMethodsPage.isAddPaypalAccountButtonDisplayed(),
        "Add Paypal Account button is not displayed");
    Assert.assertTrue(
        paymentMethodsPage.isAddPeetsCardButtonDisplayed(),
        "Add Peets Card button is not displayed");

    logger.info("5. Edit Saved Payment Method");
    EditPaymentMethodPage editPaymentMethodPage = paymentMethodsPage.clickEditCreditCard();
    String updatedName = editPaymentMethodPage.enterNameOnCard(Constants.TestData.LAST_NAME);
    paymentMethodsPage = editPaymentMethodPage.clickSavePaymentMethod();
    Assert.assertEquals(
        paymentMethodsPage.getNameOnCreditCard(),
        updatedName,
        "Name on Credit Card was not updated");

    logger.info("6. Delete Payment Method");
    paymentMethodsPage.clickDeletePeetsCard();
    Assert.assertTrue(paymentMethodsPage.didPeetsCardDelete(), "Peets Card did not delete");

    logger.info("7. Add New Payment Method");
    AssociateNewCardPage associateNewCardPage = paymentMethodsPage.addPeetsCard();
    String alternatePeetsCardNumber =
        (EnvironmentHelper.isSafari(DriverManager.getDriver()))
            ? Constants.TestData.PEETS_CARD_NUMBER_SAFARI_2
            : Constants.TestData.PEETS_CARD_NUMBER_CHROME_2;
    associateNewCardPage.enterCardNumber(alternatePeetsCardNumber);
    String alternatePeetsCardPin =
        (EnvironmentHelper.isSafari(DriverManager.getDriver()))
            ? Constants.TestData.PEETS_CARD_PIN_SAFARI_2
            : Constants.TestData.PEETS_CARD_PIN_CHROME_2;
    associateNewCardPage.enterPinNumber(alternatePeetsCardPin);
    paymentMethodsPage = associateNewCardPage.clickAssociateCard();

    logger.info("8. Verify New Payment Method");
    Assert.assertTrue(paymentMethodsPage.isPeetsCardDisplayed(), "Peets Card is not displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133898")
  public void myAccountBillingAddress() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    signInPage.enterEmailByBrowser(
        Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.SAFARI_BILLING_EMAIL);
    signInPage.enterPassword(Constants.MyAccountTestData.PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickonSignInButton();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Edit Billing Address");
    EditBillingAddressPage editBillingAddressPage = myAccountPage.clickEditBillingAddress();
    Assert.assertNotNull(editBillingAddressPage, "Edit Billing Address page is not displayed");
    String address =
        editBillingAddressPage.enterAddress(
            Constants.TestData.ADDRESS, Constants.MyAccountTestData.ADDRESS_LINE_2);
    AddressBookPage addressBookPage = editBillingAddressPage.clickSaveAddress();

    logger.info("4. Verify Billing Address Change");
    Assert.assertTrue(
        addressBookPage.isAddressSavedTextDisplayed(), "Address Saved text is not displayed");
    Assert.assertTrue(addressBookPage.getBillingAddress().contains(address));

    logger.info("5. Delete Billing Address");
    addressBookPage.deleteBillingAddress();
    Assert.assertTrue(addressBookPage.isBillingAddressDeleted(), "Billing Address was not deleted");

    logger.info("6. Add New Billing Address");
    AddBillingAddressPage addBillingAddressPage = addressBookPage.clickAddNewBillingAddress();
    addBillingAddressPage.enterAddressLine1(Constants.TestData.ADDRESS);
    addBillingAddressPage.enterZipCode(Constants.TestData.ZIP_CODE);
    addBillingAddressPage.selectState(Constants.TestData.STATE);
    addBillingAddressPage.enterCity(Constants.TestData.CITY);
    addBillingAddressPage.enterPhoneNumber(Constants.TestData.PHONE);
    addressBookPage = addBillingAddressPage.clickSaveAddress();

    logger.info("7. Verify Address is Added");
    Assert.assertTrue(addressBookPage.getBillingAddress().contains(Constants.TestData.ADDRESS));
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133899")
  public void myAccountShippingAddress() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    signInPage.enterEmailByBrowser(
        Constants.MyAccountTestData.EMAIL, Constants.MyAccountTestData.SAFARI_SHIPPING_EMAIL);
    signInPage.enterPassword(Constants.MyAccountTestData.PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickonSignInButton();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Edit Shipping Address");
    EditShippingAddressPage editShippingAddressPage = myAccountPage.clickEditShippingAddress();
    Assert.assertNotNull(editShippingAddressPage, "Edit Shipping Address page is not displayed");
    String address =
        editShippingAddressPage.enterAddress(
            Constants.TestData.ADDRESS, Constants.MyAccountTestData.ADDRESS_LINE_2);
    AddressBookPage addressBookPage = editShippingAddressPage.clickSaveAddress();

    logger.info("4. Verify Shipping Address Change");
    Assert.assertTrue(
        addressBookPage.isAddressSavedTextDisplayed(), "Address Saved text is not displayed");
    Assert.assertTrue(addressBookPage.getShippingAddress().contains(address));

    logger.info(" 5. Delete Shipping Address");
    addressBookPage.deleteShippingAddress();
    Assert.assertTrue(
        addressBookPage.isShippingAddressDeleted(), "Shipping Address was not deleted");

    logger.info("6. Add New Shipping Address");
    AddShippingAddressPage addShippingAddressPage = addressBookPage.clickAddNewShippingAddress();
    addShippingAddressPage.enterAddressLine1(Constants.TestData.ADDRESS);
    addShippingAddressPage.enterZipCode(Constants.TestData.ZIP_CODE);
    addShippingAddressPage.selectState(Constants.TestData.STATE);
    addShippingAddressPage.enterCity(Constants.TestData.CITY);
    addShippingAddressPage.enterPhoneNumber(Constants.TestData.PHONE);
    addressBookPage = addShippingAddressPage.clickSaveAddress();

    logger.info("7. Verify Address was Added");
    Assert.assertTrue(addressBookPage.getShippingAddress().contains(Constants.TestData.ADDRESS));
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133901")
  public void myAccountPeetsCards() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage =
        signInPage.userLogin(Constants.TestData.PEETS_USERNAME, Constants.TestData.PEETS_PASSWORD);
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Select 'Peets Cards' from Dashboard navigation bar");
    MyAccountPeetsCardPage peetsCardsPage = myAccountPage.clickPeetsCardsTab();
    Assert.assertNotNull(peetsCardsPage, "Peets Cards page did not display");

    logger.info("4. Verify Peets Cards sections, Buy Peet's Card");
    Assert.assertTrue(
        peetsCardsPage.isBuyPeetsCardSectionDisplayed(),
        "Buy Peet's Card Section is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isBuyPeetsCardDescriptionDisplayed(),
        "Buy Peet's Card Description is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isBuyPeetsCardLinkDisplayed(), "Buy Peet's Card Link is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isBuyPeetsCardImageDisplayed(), "Buy Peet's Card Image is not displayed");

    logger.info("5. Verify Peets Cards sections, Check Balance");
    Assert.assertTrue(
        peetsCardsPage.isCheckBalanceSectionDisplayed(), "Check Balance Section is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isCheckBalanceCardNumberDisplayed(),
        "Check Balance Card Number is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isCheckBalancePinNumberDisplayed(),
        "Check Balance Pin Number is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isCheckBalanceButtonDisplayed(), "Check Balance Button is not displayed");

    logger.info("6. Verify Peets Cards sections, Register Peet's Card");
    Assert.assertTrue(
        peetsCardsPage.isRegisterPeetsCardSectionDisplayed(),
        "Register Peet's Card Section is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isRegisterPeetsCardDescriptionDisplayed(),
        "Register Peet's Card Description is not displayed");
    Assert.assertTrue(
        peetsCardsPage.isRegisterPeetsCardLinkDisplayed(),
        "Register Peet's Card Link is not displayed");

    logger.info("6. Verify Peets Cards sections, FAQ");
    Assert.assertTrue(peetsCardsPage.isFAQLinkDisplayed(), "FAQ Link is not displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133895")
  public void myAccountMyOrdersTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Select 'My Orders' from Dashboard navigation bar");
    MyAccountMyOrdersPage myOrdersPage = myAccountPage.clickMyOrdersTab();
    Assert.assertNotNull(myOrdersPage, "My Orders page did not display");

    logger.info("4. Verify My Orders section, First Order");
    Assert.assertTrue(
        myOrdersPage.isOrdersPlacedSectionDisplayed(), "Orders Placed Section is not displayed");
    Assert.assertTrue(myOrdersPage.isOrdersDateDisplayed(), "Order's Date is not displayed");
    Assert.assertTrue(myOrdersPage.isOrdersNumberDisplayed(), "Order's Number is not displayed");
    Assert.assertTrue(myOrdersPage.isOrdersItemDisplayed(), "Order's Item is not displayed");
    Assert.assertTrue(myOrdersPage.isOrdersTotalDisplayed(), "Order's Total is not displayed");
    Assert.assertTrue(myOrdersPage.isOrdersStatusDisplayed(), "Order's Status is not displayed");
    Assert.assertTrue(myOrdersPage.isViewButtonDisplayed(), "Order's View button is not displayed");
    Assert.assertTrue(
        myOrdersPage.isReorderButtonDisplayed(), "Order's View button is not displayed");

    logger.info("5. Access first Oder and Verify Detail");
    MyAccountOrderDetailPage ordersDetailPage = myOrdersPage.clickOrderNumber();
    Assert.assertTrue(
        ordersDetailPage.isProductDisplayed(), "Product on Order Detail is not displayed");
    Assert.assertTrue(
        ordersDetailPage.isReorderButtonDisplayed(),
        "Reorder button on Order Detail is not displayed");
    Assert.assertTrue(
        ordersDetailPage.isShippingMethodDisplayed(),
        "Shipping Method on Order Detail is not displayed");
    Assert.assertTrue(
        ordersDetailPage.isPaymentMethodDisplayed(),
        "Payment Method on Order Detail is not displayed");

    logger.info("6. Return to My Orders page");
    myOrdersPage = ordersDetailPage.clickBackToMyOrders();
    Assert.assertNotNull(myOrdersPage, "My Orders page did not display");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133900")
  public void myAccountSettings() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    signInPage.enterEmailByBrowser(
        Constants.MyAccountTestData.MODIFY_ACCOUNT_EMAIL,
        Constants.MyAccountTestData.SAFARI_ACCOUNT_EMAIL);
    signInPage.enterPassword(Constants.MyAccountTestData.PASSWORD);
    MyAccountPage myAccountPage = signInPage.clickonSignInButton();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. Select Settings");
    EditAccountInformationPage editAccountInformationPage = myAccountPage.clickSettings();
    Assert.assertNotNull(
        editAccountInformationPage, "Edit Account Information page is not displayed");

    logger.info("4. Edit Information");
    editAccountInformationPage.enterFirstName(Constants.MyAccountTestData.FIRST_NAME);
    String email = String.format(Constants.TestData.EMAIL, WebHelper.returnTimestamp());
    editAccountInformationPage.enterEmail(email);
    myAccountPage = editAccountInformationPage.clickSave();

    logger.info("5. Verify Information Changed");
    Assert.assertTrue(
        myAccountPage.getCustomerName().contains(Constants.MyAccountTestData.FIRST_NAME));
    Assert.assertTrue(myAccountPage.getCustomerEmail().contains(email));

    logger.info("6. Click Edit Contact Information");
    editAccountInformationPage = myAccountPage.clickEditContactInformation();

    logger.info("7. Revert Data");
    editAccountInformationPage.enterEmailByBrowser(
        Constants.MyAccountTestData.MODIFY_ACCOUNT_EMAIL,
        Constants.MyAccountTestData.SAFARI_ACCOUNT_EMAIL);
    editAccountInformationPage.enterFirstName(Constants.TestData.FIRST_NAME);
    editAccountInformationPage.clickSave();
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "133896")
  public void myAccountSubscriptionsTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Log In");
    SignInPage signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPage = signInPage.mainUserLogin();
    Assert.assertNotNull(myAccountPage, "Account Dashboard did not display");

    logger.info("3. My Subscriptions");
    MyAccountMySuscriptionsPage mySuscriptionsPage = myAccountPage.clickMySuscriptions();
    Assert.assertTrue(
        mySuscriptionsPage.isSubscriptionNameDisplayed(), "Subscription Name is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isNextShipmentDateDisplayed(), "Next Shipment Date is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isSubscriptionFrequencyDateDisplayed(),
        "Subscription Frequency Date is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isManageSubscriptionButtonDisplayed(),
        "Manage Subscription button is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isShippingAddressDisplayed(), "Shipping Address is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isShippingMethodDisplayed(), "Shipping Method is not displayed");
    Assert.assertTrue(
        mySuscriptionsPage.isSubscribedProductDisplayed(), "Subscribed Product is not displayed");

    logger.info("5. My Subscriptions Details");
    MyAccountManageSubscriptionPage suscriptionsDetailPage =
        mySuscriptionsPage.clickManageSubscription();
    Assert.assertTrue(
        suscriptionsDetailPage.isPauseCancelSubscriptionButtonDisplayed(),
        "Pause/Cancel Subscription button is not displayed");
    Assert.assertTrue(
        suscriptionsDetailPage.isManageShipmentButtonDisplayed(),
        "PManage Shipment button is not displayed");
    Assert.assertTrue(
        suscriptionsDetailPage.isBillingAddressDisplayed(), "Billing Address is not displayed");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "627698")
  public void myAccountCreateNewAccountTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Click Sign In");
    SignInPage signInPage = landing.clickSignInButton();

    logger.info("3. Click Create An Account");
    SignUpPage signUpPage = signInPage.clickonCreateAccountButton();

    logger.info("4. Fill out new account information");
    logger.info("5. Click Submit");
    MyAccountPage myAccountPage = signUpPage.submitSignUpInfo(MyAccountPage.class);

    logger.info("Verify user is logged in");
    Assert.assertNotNull(myAccountPage, "User does not signed in");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "627699")
  public void myAccountCreateNewAccountAfterGuestCheckoutTest() {
    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();
    Assert.assertNotNull(landing, "Failed to navigate to the landing page.");

    logger.info("2. Select a coffee from grid view and add to cart");
    ShopCoffeePage shopCoffeePage = landing.clickShopCoffeeButton();
    CoffeeProductPage coffeeProductPage =
        shopCoffeePage.clickProductName(Constants.TestData.COFFEE_BRAND_NAME);
    coffeeProductPage.selectAGrind(Constants.TestData.GRIND);
    MiniCartContainerChunk miniCartContainer = coffeeProductPage.clickAddToCart();

    logger.info("3. Select 'Proceed to Checkout'");
    CheckoutPage checkoutPage = miniCartContainer.clickCheckout();
    CheckoutShippingInfoPage shippingInfoPage = checkoutPage.clickContinueAsGuest();

    logger.info("4. Complete Contact Information");
    VerifyYourAddressDetailsChunk verifyAddressChunk =
        shippingInfoPage.continueAfterFillingRequiredContactInfo();
    shippingInfoPage = verifyAddressChunk.clickEnteredAddressButton();

    logger.info("5. Select ground shipping");
    CheckoutPaymentMethodPage paymentMethodPage =
        shippingInfoPage.setShippingMethod(Constants.TestData.SHIPPING_METHOD_GROUND);

    logger.info("6. Use credit card for payment");
    CheckoutPlaceOrderPage placeOrderPage =
        paymentMethodPage.continueAfterFillingRequiredBillingInfoLoggedIn();

    logger.info("7. Click 'Place Order'");
    CheckoutConfirmationPage confirmationPage = placeOrderPage.placeOrder();

    logger.info("Verify Confirmation page is displayed");
    Assert.assertTrue(
        confirmationPage.getConfirmationMessage().contains("THANK YOU FOR YOUR PURCHASE!"),
        "Order was not placed");

    logger.info("Order Placed: " + confirmationPage.getOrderNumber());

    logger.info(
        "On Order confirmation page, create a new account for new customers. Enter email address, password, confirm password. ");
    confirmationPage.enterPassword(Constants.MyAccountTestData.PASSWORD);
    confirmationPage.enterConfirmPassword(Constants.MyAccountTestData.PASSWORD);

    logger.info("Select Create Account");
    MyAccountPage myAccountPage = confirmationPage.createAccount();

    logger.info("Verify account was created");
    Assert.assertNotNull(myAccountPage, "My Account page not found, account does not created");
  }

  @Test(
      groups = {Constants.TestNGGroups.MY_ACCOUNT},
      description = "627700")
  public void myAccountChangePasswordTest() {

    logger.info("1. Navigate to landing page");
    Landing landing = navigateToLanding();

    logger.info("2. Click Sign In");
    SignInPage signInPage = landing.clickSignInButton();

    logger.info("3. Click Create An Account");
    SignUpPage signUpPage = signInPage.clickonCreateAccountButton();

    logger.info("4. Fill out new account information");
    logger.info("5. Click Submit");
    MyAccountPage myAccountPage = signUpPage.submitSignUpInfo(MyAccountPage.class);
    String newAccountEmail = signUpPage.email;

    logger.info("Verify user is logged in");
    Assert.assertNotNull(myAccountPage, "User does not signed in");

    logger.info("6. From My Dashboard, click Settings");
    EditAccountInformationPage editAccountInformationPage = myAccountPage.clickSettings();

    logger.info("7. Enter current password. Select checkbox for Change Password.");
    editAccountInformationPage.enterCurrentPassword(Constants.TestData.PASSWORD);
    editAccountInformationPage.changeCurrentPassword();

    logger.info("8. Enter new password, Confirm password, Click Save");
    editAccountInformationPage.enterNewPassword("new" + Constants.MyAccountTestData.PASSWORD);
    editAccountInformationPage.enterConfirmPassword("new" + Constants.MyAccountTestData.PASSWORD);
    myAccountPage = editAccountInformationPage.clickSave();

    logger.info("9. Log Out");
    landing = myAccountPage.getAccountMenu().signOut();

    logger.info("10. Log back in using new password");
    signInPage = landing.clickSignInButton();
    MyAccountPage myAccountPageNew =
        signInPage.userLogin(newAccountEmail, "new" + Constants.MyAccountTestData.PASSWORD);

    logger.info("User should be able to log in");
    Assert.assertNotNull(myAccountPageNew, "Use was not able to log in using new password");
  }
}
