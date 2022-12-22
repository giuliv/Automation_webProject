package com.applause.auto.test.new_web.ppd;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.WebTestData;
import com.applause.auto.common.data.dto.RecipientAddress;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.QuickViewComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.new_web.views.HomePage;
import com.applause.auto.new_web.views.PaymentsPage;
import com.applause.auto.new_web.views.ProductDetailsPage;
import com.applause.auto.new_web.views.SearchResultsPage;
import com.applause.auto.new_web.views.ShippingPage;
import com.applause.auto.test.new_web.BaseTest;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class PpdTestHelper extends BaseTest {

  public static void checkoutAndOrderValidationGiftSubscriptionPpd(
      ProductDetailsPage productDetailsPage, SoftAssert softAssert) {
    logger.info("2. Click buy now on PDP");
    CheckOutPage checkOutPage = productDetailsPage.clickAddBuyNow();

    logger.info("Verify Checkout page is displayed");
    softAssert.assertTrue(checkOutPage.isDisplayed(), "Checkout page is not displayed");

    logger.info("3. Enter the recipient data on checkout step = information");
    checkOutPage.enterRecipientInformation(Constants.WebTestData.FIRST_NAME, WebTestData.EMAIL);

    logger.info(
        "4. View and enter the 'Recipient Address' in place of  the 'Shipping Address' on checkout step = information");
    checkOutPage.setCheckOutData();

    logger.info("Verify the personalized message field is not required to enter");
    softAssert.assertTrue(
        checkOutPage.isContinueToShippingButtonEnable(),
        "The personalized message field is unexpected required to enter");

    logger.info("5. Enter a personalized message to the recipient on checkout step");
    checkOutPage.enterRecipientMessage(TestData.RECIPIENT_MESSAGE);

    logger.info("Verify the tip text (small ? Next to personalized message)");
    softAssert.assertTrue(
        checkOutPage.isRecipientMessageTipTextDisplayed(), "The tip text is not displayed");

    logger.info(
        "6. Continue to the shipping checkout step after entering the details in the information step");
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info(
        "7. Apply a subscription discount code on a prepaid subscription in any of the checkout steps prior to order confirmation");
    shippingPage = shippingPage.applyDiscountCode(TestData.FIRSTSUB_DISCOUNT_CODE);

    logger.info("Verify Discount codes have been updated to disallow discounts on prepaid subs");
    Assert.assertFalse(shippingPage.isDiscountDisplayed(), "Discount is unexpected displayed");

    logger.info("8. Click on return to cart from the information step");
    CartPage cartPage = shippingPage.clickOnReturnToInformation().clickOnReturnToCartButton();

    logger.info("Verify prepaid gift sub is in the cart/mini cart");
    softAssert.assertTrue(
        cartPage.isGiftExistsInYourCartMessageDisplayed(),
        "Prepaid gift sub is not in the cart/mini cart");

    logger.info("Verify prepaid gift can be removed from cart");
    softAssert.assertTrue(
        cartPage.isIncreaseQuantityButtonDisplayed(1), "Prepaid gift can't be removed from cart");

    logger.info("Verify can return to checkout flow by clicking checkout on mini cart or cart");
    softAssert.assertTrue(cartPage.checkOutButtonIsDisplayed(), "Checkout button is not displayed");
  }

  public static void checkNoItemsOtherThanPrepaidCanBeAddedToCart(
      ProductDetailsPage productDetailsPage, SoftAssert softAssert) {
    logger.info("Verify no items other than prepaid subs can be added to cart");
    SdkHelper.getSyncHelper().sleep(2000); // Wait for button text to update
    softAssert.assertNotEquals(
        productDetailsPage.getAddToCartButtonText(),
        TestData.ADD_TO_CART_BUTTON,
        "Add to cart button is unexpected displayed");

    logger.info("9. Click on peets coffee to return to the site");
    HomePage homePage = productDetailsPage.getHeader().clickLogoButton();

    logger.info("10. Open mini cart");
    MiniCart miniCart = homePage.getHeader().clickCartIcon();

    logger.info("Verify prepaid gift sub is in the cart/mini cart");
    softAssert.assertTrue(
        miniCart.isGiftExistsInYourCartMessageDisplayed(),
        "Prepaid gift sub is not in the cart/mini cart");

    logger.info("Verify prepaid gift can be removed from cart");
    softAssert.assertTrue(
        miniCart.isRemoveItemLinkDisplayed(),
        "Prepaid gift can't be removed from cart and remove button is not displayed");

    logger.info("Verify can return to checkout flow by clicking checkout on mini cart or cart");
    softAssert.assertTrue(miniCart.isCheckoutButtonDisplayed(), "Checkout button is not displayed");

    logger.info("11. Select free shipping on the shipping step of the checkout flow");
    CheckOutPage checkOutPage = miniCart.clickOnContinueToCheckOutButton(CheckOutPage.class);
    checkOutPage.enterRecipientInformation(Constants.WebTestData.FIRST_NAME, WebTestData.EMAIL);
    ShippingPage shippingPage = checkOutPage.clickContinueToShipping();

    logger.info(
        "Verify since this is a subscription the prepaid gifts should always be elgible for free shipping");
    softAssert.assertEquals(shippingPage.getShippingPrice(), 0.0, "The shipping price is not free");

    logger.info(
        "12. The 'Change' button next to the contact or ship to should bring the customer back to the information checkout step");
    checkOutPage = shippingPage.clickOnChangeShipTo();
    softAssert.assertTrue(
        checkOutPage.isDisplayed(),
        "Checkout page is not displayed after clicking on Change Ship to button");

    logger.info(
        "13. Update the contact and or ship to information in the shipping step of the checkout flow");
    checkOutPage.enterRecipientInformation(Constants.WebTestData.FIRST_NAME, WebTestData.EMAIL);
    checkOutPage.setCheckOutData(RecipientAddress.builder().build());

    logger.info("14. Change the billing address back to same as shipping address");
    PaymentsPage paymentsPage = checkOutPage.clickContinueToShipping().clickContinueToPayments();
    paymentsPage = paymentsPage.setUseBillingAddressSameAsShippingButton();

    logger.info(
        "Verify Credit card is the default selection and the customer should be able to enter their details here to buy the gift");
    softAssert.assertTrue(paymentsPage.isCreditCardMethodDisplayed(), "CC is not displayed");

    logger.info("Verify Validate there is no paypal subscription payment method for  prepaid subs");
    softAssert.assertFalse(
        paymentsPage.isPayPalMethodDisplayed(), "Paypal method is unexpected displayed");
  }

  public static void checkSubscriptionItemCanBeRemoved(
      ProductDetailsPage productDetailsPage, String cartPageUrl, SoftAssert softAssert) {
    SdkHelper.getSyncHelper().sleep(2000); // Wait for button text to update
    softAssert.assertNotEquals(
        productDetailsPage.getAddToCartButtonText(),
        TestData.ADD_TO_CART_BUTTON,
        "Add to cart button is unexpected displayed");

    logger.info(
        "17. Remove any items from the cart as soon as a prepaid item is added to the cart");
    CartPage cartPage = WebHelper.navigateToUrl(cartPageUrl, CartPage.class);
    String firstProductName = cartPage.getProductNameByIndex(1);
    cartPage.decreaseQuantity(0);

    logger.info(
        "Verify the system should remove all of the items in the cart and proceed to checkout with the prepaid item");
    softAssert.assertNotEquals(
        cartPage.getProductNameByIndex(1), firstProductName, "The item was not removed");
    CheckOutPage checkOutPage = cartPage.clickContinueToCheckOut();
    softAssert.assertTrue(checkOutPage.isDisplayed(), "Checkout page is not displayed");
  }

  public static void plpAndPdpPageValidationsTest(
      HomePage homePage, String productPartUrl, String productName, boolean isGrindDisplayed) {
    logger.info("1. Navigate to Home");
    homePage.closeInitialBannersAndModals();

    logger.info("2. Search for the product: {}", productPartUrl);
    SearchResultsPage searchResultsPage =
        homePage.getHeader().getSearchComponent().search(productPartUrl);
    QuickViewComponent quickViewComponent =
        searchResultsPage.clickOverQuickViewByProduct(productPartUrl);

    logger.info("3. Verify the correct product quickview displays");
    Assert.assertTrue(
        quickViewComponent.isGiftDurationDisplayed(), "Gift duration is not displayed");
    Assert.assertTrue(
        quickViewComponent.isGeneralQuantityBoxDisplayed(), "Quantity is not displayed");

    if (isGrindDisplayed) {
      Assert.assertTrue(quickViewComponent.isGrindDisplayed(), "Grind is not displayed");

      logger.info("4. Select the variant 1 from the quickview screen (Grind)");
      quickViewComponent.selectGrind(GrindDropdown.DRIP);

      logger.info("5. Verify dropdown provides the active variants for grinds");
      Assert.assertEquals(
          quickViewComponent.getSelectGrind(),
          GrindDropdown.DRIP.getValue(),
          "Grind element was not selected");

      logger.info("6. Dropdown provides the gift durations of 3,6,12 and can be updated");
      quickViewComponent.validateGiftQuantityElements();

      logger.info("7. Select the quantity from the Quick View");
      quickViewComponent.selectQuantityByIndex(2);
      Assert.assertTrue(quickViewComponent.isQuantitySelected(2), "Quantity was not selected");
    } else {
      Assert.assertFalse(quickViewComponent.isGrindDisplayed(), "Grind is unexpected displayed");

      logger.info("6. Dropdown provides the gift durations of 3,6,12 and can be updated");
      quickViewComponent.validateQuantityBoxSectionElements();

      logger.info("7. Select the quantity from the Quick View");
      quickViewComponent.clickQuantityMinusButton();
      Assert.assertEquals(
          quickViewComponent.getProductQuantitySelected(), 6, "Quantity was not selected");
    }

    logger.info("8. Click buy now from the quickview");
    String pageUrl = WebHelper.getCurrentUrl();
    CheckOutPage checkOutPage = quickViewComponent.clickBuyNow();

    logger.info(
        "9. The prepaid gift CTA is \"Buy now\" and when clicked is brought the checkout step = information");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on the Information page");

    logger.info("10. Click on view product from quickview");
    searchResultsPage = WebHelper.navigateToUrl(pageUrl, SearchResultsPage.class);
    quickViewComponent = searchResultsPage.clickOverQuickViewByProduct(productPartUrl);
    ProductDetailsPage productDetailsPage = quickViewComponent.clickViewProduct();

    logger.info("11. Verify view a prepaid gift PDP");
    Assert.assertEquals(
        productDetailsPage.getProductName(), productName, "Product details page is not opened");

    if (isGrindDisplayed) {
      logger.info("12. Update the grind from the PDP");
      productDetailsPage.selectGiftSubscriptionGrind(GrindDropdown.WHOLE_BEAN);

      logger.info("13. Grind variants are available to change on the product PDP");
      Assert.assertEquals(
          productDetailsPage.getGrindSelected().toLowerCase(),
          GrindDropdown.WHOLE_BEAN.getValue().toLowerCase(),
          "Grind wasn't selected");

      logger.info("14. Update the duration from the PDP");
      productDetailsPage.validateGiftQuantityElements();

      logger.info("15. Select the quantity from the PDP");
      productDetailsPage.selectQuantityByIndex(2);
      Assert.assertEquals(
          productDetailsPage.getProductQuantitySelected(), 2, "Quantity was not selected");
    } else {
      Assert.assertFalse(productDetailsPage.isGrindDisplayed(), "Grind is unexpected displayed");

      logger.info("6. Dropdown provides the gift durations of 3,6,12 and can be updated");
      productDetailsPage.clickQuantityMinusButton();
      productDetailsPage.validateQuantityBoxSectionElements();

      logger.info("7. Select the quantity from the Quick View");
      productDetailsPage.clickQuantityMinusButton();
      Assert.assertEquals(
          productDetailsPage.getProductQuantitySelected(), 6, "Quantity was not selected");
    }

    logger.info("16. Identify the cost of the prepaid gift");
    Assert.assertTrue(
        productDetailsPage.isProductStartAtPriceDisplayed(), "Product price is not displayed");

    logger.info("17. See the buy Now CTA on the PDP");
    Assert.assertEquals(
        productDetailsPage.getAddToCartButtonText(),
        TestData.BUY_NOW_BUTTON,
        "BUY NOW button is not named properly");

    logger.info("18. Click buy now on PDP");
    checkOutPage = productDetailsPage.clickAddBuyNow();

    logger.info("19. Verify when clicked is brought the checkout step = information");
    Assert.assertTrue(checkOutPage.isDisplayed(), "User isn't on the Information page");
  }
}
