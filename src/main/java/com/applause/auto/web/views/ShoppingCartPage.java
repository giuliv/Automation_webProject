package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.DriverManager;
import com.applause.auto.util.control.BrowserControl;
import com.applause.auto.util.helper.EnvironmentHelper;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
import com.applause.auto.web.components.ShopRunnerChunk;
import com.applause.auto.web.helpers.WebHelper;

import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = ShoppingCartPage.class, on = Platform.WEB)
public class ShoppingCartPage extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "div.cart.display-single-price div.page-title h1", on = Platform.WEB)
  private Text getViewSignature;

  @Locate(xpath = "//div[@class='shoprunner-cart-header']//a[text()='sign in']", on = Platform.WEB)
  private Button getSignInShopRunnerButton;

  @Locate(css = "[value='update_qty']", on = Platform.WEB)
  private Text getUpdateCartButton;

  @Locate(css = "#subscription_interval", on = Platform.WEB)
  private Text getSubscriptionFrequencyText;

  @Locate(css = "#subscription-name-view > strong", on = Platform.WEB)
  private Text getSubscriptionNameText;

  @Locate(css = "#checkout-cart-please-wait", on = Platform.WEB)
  private ContainerElement getAddingToCartSpinner;

  @Locate(css = "#shopping-cart-messages", on = Platform.WEB)
  private Text getStatusMessageText;

  @Locate(xpath = "//select[@title='Grind']", on = Platform.WEB)
  private SelectList getGrindForItemSelectList;

  @Locate(xpath = "//select[@id='shipping_method']", on = Platform.WEB)
  private SelectList getShippingMethodSelectList;

  @Locate(xpath = "//h3[contains(.,'%s')]/../../..//input[@title='Qty']", on = Platform.WEB)
  private TextBox getQuantityForItemTextBox;

  @Locate(css = ".add-gift-message input", on = Platform.WEB)
  private Checkbox getOrderAsGiftCheckCheckbox;

  @Locate(css = "#gift-message-whole-message", on = Platform.WEB)
  private TextBox getGiftMessageText;

  @Locate(css = "#proceed-checkout", on = Platform.WEB)
  private Button getProceedToCheckoutButton;

  @Locate(
      css = "div#shopping-cart-actions-additional img[alt='Checkout with PayPal']",
      on = Platform.WEB)
  private Button getPaypalButton;

  @Locate(
      css = "#cart-table-standard > tbody > tr > td.a-center.product-cart-remove.last > a",
      on = Platform.WEB)
  private Button getRemoveItemButton;

  @Locate(css = "h3.product-name", on = Platform.WEB)
  private List<Text> getCartItemsText;

  @Locate(
      xpath = "//tr[td[contains(text(),'Estimated Shipping')]]//*[@class='price']",
      on = Platform.WEB)
  private Text getEstimatedShippingPriceText;

  @Locate(
      xpath = "//tr[th[contains(text(),'Product Discount')]]//*[@class='price']",
      on = Platform.WEB)
  private Text getProductDiscountPriceText;

  @Locate(xpath = "//tr[th[contains(text(),'Shipping Discount')]]", on = Platform.WEB)
  private Text getShippingDiscountPriceText;

  @Locate(css = "strong.total-price", on = Platform.WEB)
  private Text getOrderSummaryPriceText;

  @Locate(
      css =
          "body > div.wrapper > div > div.main-container.full-width > div > div > div.empty-cart-holder > div.page-title > h1",
      on = Platform.WEB)
  private Text getCartEmptyText;

  /* -------- Actions -------- */

  /**
   * Select Order is a Gift Checkbox
   *
   * @return CheckoutPage
   */
  public void selectOrderAsGift() {
    logger.info("Check the Order is a Gift");
    getOrderAsGiftCheckCheckbox.scrollToElement();
    if (!getOrderAsGiftCheckCheckbox.isChecked()) {
      SyncHelper.wait(Until.uiElement(getOrderAsGiftCheckCheckbox).present());
      getOrderAsGiftCheckCheckbox.click();
    }
  }

  /** Enter Gift Message */
  public void enterGiftMessage(String giftMessage) {
    logger.info("Enter a Gift Message");
    SyncHelper.wait(Until.uiElement(getGiftMessageText).present());
    getGiftMessageText.clearText();
    getGiftMessageText.sendKeys(giftMessage);
  }

  /**
   * Click Proceed to Checkout button
   *
   * @return CheckoutPage
   */
  public CheckoutPage clickProceedToCheckout() {
    logger.info("Click Proceed to Checkout button");
    getProceedToCheckoutButton.scrollToElement();

    // TODO: Investigate further why click is not working
    // getProceedToCheckoutButton.click();
    // SyncHelper.sleep(2000);
    WebHelper.jsClick(getProceedToCheckoutButton.getWebElement());
    return ComponentFactory.create(CheckoutPage.class);
  }

  /**
   * Click Proceed to Checkout button for a signed user
   *
   * @return CheckoutPlaceOrderPage
   */
  public CheckoutPlaceOrderPage checkoutSignedUser() {
    logger.info("Click Proceed to Checkout button");
    getProceedToCheckoutButton.click();
    SyncHelper.sleep(2000);
    WebHelper.jsClick(getProceedToCheckoutButton.getWebElement());
    return ComponentFactory.create(CheckoutPlaceOrderPage.class);
  }

  /**
   * Proceed to Shipping page via Checkout button for a signed user
   *
   * @return CheckoutShippingInfoPage
   */
  public CheckoutShippingInfoPage defineShippingSignedUser() {
    logger.info("Click Proceed to Checkout button");
    SyncHelper.sleep(10000);
    getProceedToCheckoutButton.click();
    SyncHelper.sleep(5000);
    try {
      getProceedToCheckoutButton.click();
    } catch (Exception ex) {
      logger.info("Button no longer present");
    }
    SyncHelper.sleep(2000);
    // WebHelper.jsClick(getProceedToCheckoutButton.getWebElement());
    return ComponentFactory.create(CheckoutShippingInfoPage.class);
  }

  /**
   * Click Pay with Paypal Button
   *
   * @return PaypalLoginPage
   */
  public PaypalLoginPage clickPayWithPaypal() {
    logger.info("Clicking Pay with Paypal");
    getPaypalButton.click();
    SyncHelper.sleep(5000);
    try {
      getPaypalButton.click();
    } catch (Exception ex) {
      logger.info("Already clicked Paypal button");
    }
    return ComponentFactory.create(PaypalLoginPage.class);
  }

  /**
   * Click Pay with Paypal Button for a signed user
   *
   * @return CheckoutPlaceOrderPage
   */
  public CheckoutPlaceOrderPage clickPayWithPaypalSignedUser() {
    logger.info("Clicking Pay with Paypal for Signed User");
    getPaypalButton.click();
    SyncHelper.sleep(5000); // Required due a change of focus after leaving gift-message
    if (getPaypalButton.exists()) {
      WebHelper.jsClick(getPaypalButton.getWebElement());
    }
    return ComponentFactory.create(CheckoutPlaceOrderPage.class);
  }

  /**
   * Click Pay with Paypal Button for a signed user
   *
   * @return CheckoutPlaceOrderPage
   */
  public CheckoutShippingInfoPage clickPayWithPaypalSignedUserLoggedIn() {
    logger.info("Clicking Pay with Paypal for Signed User");
    getPaypalButton.click();
    SyncHelper.sleep(5000); // Required due a change of focus after leaving gift-message
    if (getPaypalButton.exists()) {
      WebHelper.jsClick(getPaypalButton.getWebElement());
    }
    return ComponentFactory.create(CheckoutShippingInfoPage.class);
  }

  /**
   * Gets items.
   *
   * @return the items
   */
  public List<String> getItems() {
    logger.info("Obtaining items from mini-cart");
    return getCartItemsText
        .stream()
        .map(
            item -> {
              String _result = item.getText();
              logger.info("Found item: " + _result);
              return _result.trim();
            })
        .collect(Collectors.toList());
  }

  /**
   * Remove item shopping cart page.
   *
   * @param itemName the item name
   * @return the shopping cart page
   */
  public ShoppingCartPage removeItem(String itemName) {
    logger.info("Removing item: " + itemName);
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      getRemoveItemButton.initializeWithFormat(itemName);
      BrowserControl.jsClick(getRemoveItemButton);
    } else {
      getRemoveItemButton.click();
    }
    waitForAddingToCartSpinner();
    SyncHelper.sleep(5000);
    return this;
  }

  /** Click Add to Cart Button */
  public void waitForAddingToCartSpinner() {
    logger.info("Adding item to Shopping Cart...");
    SyncHelper.wait(Until.uiElement(getAddingToCartSpinner).present());
    SyncHelper.sleep(10000);
  }

  /**
   * Sets grind for item.
   *
   * @param itemName the item name
   * @param grind the grind 2
   * @return the grind for item
   */
  public ShoppingCartPage setGrindForItem(String itemName, String grind) {
    logger.info("Change grind value");
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      getGrindForItemSelectList.initializeWithFormat(itemName);
      getGrindForItemSelectList.getWebElement().sendKeys(grind + "\n");
    } else {
      getGrindForItemSelectList.select(grind);
    }
    waitForAddingToCartSpinner();
    return this;
  }

  /**
   * Gets status message.
   *
   * @return the status message
   */
  public String getStatusMessage() {
    SyncHelper.wait(Until.uiElement(getStatusMessageText).present());
    return getStatusMessageText.getText().replace("  ", " ");
  }

  /**
   * Sets quantity for item.
   *
   * @param itemName the item name
   * @param quantity the quantity
   * @return the quantity for item
   */
  public ShoppingCartPage setQuantityForItem(String itemName, int quantity) {
    logger.info("Change quantity value");
    getQuantityForItemTextBox.initializeWithFormat(itemName);
    getQuantityForItemTextBox.sendKeys("" + quantity);
    return this;
  }

  /**
   * Update cart shopping cart page.
   *
   * @return the shopping cart page
   */
  public ShoppingCartPage updateCart() {
    logger.info("Click Update cart button");
    getUpdateCartButton.click();
    waitForAddingToCartSpinner();
    return ComponentFactory.create(ShoppingCartPage.class);
  }

  /**
   * Select shipping method shopping cart page.
   *
   * @param method the method
   * @return the shopping cart page
   */
  public ShoppingCartPage selectShippingMethod(String method) {
    logger.info("Select shipping method: " + method);
    if (EnvironmentHelper.isSafari(DriverManager.getDriver())) {
      getShippingMethodSelectList.click();
      getShippingMethodSelectList.getWebElement().sendKeys(method + "\n");
    } else {
      getShippingMethodSelectList.select(method);
    }
    waitForAddingToCartSpinner();
    SyncHelper.sleep(5000);
    return ComponentFactory.create(ShoppingCartPage.class);
  }

  /**
   * Gets shipping method.
   *
   * @return the shipping method
   */
  public String getShippingMethod() {
    return getShippingMethodSelectList.getSelectedOption().getText();
  }

  /**
   * Gets estimated shipping price.
   *
   * @return the estimated shipping price
   */
  public String getEstimatedShippingPrice() {
    return getEstimatedShippingPriceText.getText();
  }

  /**
   * Gets order summary price.
   *
   * @return the order summary price
   */
  public String getOrderSummaryPrice() {
    return getOrderSummaryPriceText.getText();
  }

  /**
   * Gets subscription name.
   *
   * @return the subscription name
   */
  public String getSubscriptionName() {
    return getSubscriptionNameText.getText().trim();
  }

  /**
   * Gets subscription frequency.
   *
   * @return the subscription frequency
   */
  public String getSubscriptionFrequency() {
    return getSubscriptionFrequencyText.getText().trim();
  }

  /**
   * Is product discount price displayed boolean.
   *
   * @return the boolean
   */
  public boolean isProductDiscountPriceDisplayed() {
    return getProductDiscountPriceText.isDisplayed();
  }

  /**
   * Is shipping discount price displayed boolean.
   *
   * @return the boolean
   */
  public boolean isShippingDiscountPriceDisplayed() {
    return getShippingDiscountPriceText.isDisplayed();
  }

  /**
   * Sign in shop runner shop runner chunk.
   *
   * @return the shop runner chunk
   */
  public ShopRunnerChunk signInShopRunner() {
    logger.info("Click on Sign In shop runner");
    getSignInShopRunnerButton.click();
    return ComponentFactory.create(ShopRunnerChunk.class);
  }

  /**
   * Check if cart is empty
   *
   * @return Boolean
   */
  public Boolean isCartEmpty() {
    logger.info("Checking if Cart is empty");
    return getCartEmptyText.isDisplayed();
  }
}
