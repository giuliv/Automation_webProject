package com.applause.auto.web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.web.views.CheckoutPage;
import com.applause.auto.web.views.CheckoutPlaceOrderPage;
import com.applause.auto.web.views.CheckoutShippingInfoPage;
import com.applause.auto.web.views.ShoppingCartPage;
import com.applause.auto.web.views.SignInPage;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = MiniCartContainerChunk.class, on = Platform.WEB)
public class MiniCartContainerChunk extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(css = "#minicart-container .product-name", on = Platform.WEB)
  protected List<Text> getMinicartItems;

  @Locate(css = "#minicart-container > div.block-subtitle > a.cart-link", on = Platform.WEB)
  protected Link getEditCartLink;

  @Locate(css = "a[title='Checkout']", on = Platform.WEB)
  protected Button getCheckoutButton;

  @Locate(
      xpath = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='remove']",
      on = Platform.WEB)
  protected Button getRemoveItemButton;

  @Locate(
      xpath = "//*[@id='cart-sidebar']//a[starts-with(@title,'%s')]/..//a[@class='re-add']",
      on = Platform.WEB)
  protected Button getReAddItemButton;

  /* -------- Actions -------- */

  /**
   * Click Checkout Button
   *
   * @return CheckoutPage
   */
  public CheckoutPage clickCheckout() {
    logger.info("Clicking Checkout Button");
    getCheckoutButton.click();
    return SdkHelper.create(CheckoutPage.class);
  }

  /**
   * Click Checkout Button with signed in user
   *
   * @return CheckoutPlaceOrderPage
   */
  public CheckoutPlaceOrderPage checkoutSignedInUser() {
    logger.info("Clicking Checkout Button");
    getCheckoutButton.click();
    return SdkHelper.create(CheckoutPlaceOrderPage.class);
  }

  /**
   * Click Checkout Button after user signed
   *
   * @return CheckoutShippingInfoPage
   */
  public CheckoutShippingInfoPage clickSignedInCheckout() {
    logger.info("Clicking Checkout Button");
    getCheckoutButton.click();
    return SdkHelper.create(CheckoutShippingInfoPage.class);
  }

  /**
   * Click Checkout Peets Card Button
   *
   * @return SignInPage
   */
  public SignInPage checkoutPeetsCard() {
    logger.info("Clicking Checkout Button on Peets Card Page");
    getCheckoutButton.click();
    return SdkHelper.create(SignInPage.class);
  }

  /**
   * Click Edit Cart link
   *
   * @return ShoppingCartPage
   */
  public ShoppingCartPage clickEditCart() {
    logger.info("Clicking Edit Button");
    getEditCartLink.click();
    return SdkHelper.create(ShoppingCartPage.class);
  }

  /**
   * Gets items.
   *
   * @return the items
   */
  public List<String> getItems() {
    logger.info("Obtaining items from mini-cart");
    return getMinicartItems.stream()
        .map(
            item -> {
              String result = item.getText();
              logger.info("Found item: " + result);
              return result.trim();
            })
        .collect(Collectors.toList());
  }

  /**
   * Remove mini cart container chunk.
   *
   * @param itemName the item name
   * @return the mini cart container chunk
   */
  public MiniCartContainerChunk remove(String itemName) {
    logger.info("Remove from cart item: " + itemName);
    getRemoveItemButton.initializeWithFormat(itemName);
    getRemoveItemButton.click();
    SdkHelper.getDriver().switchTo().alert().accept();
    getReAddItemButton.initializeWithFormat(itemName);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getReAddItemButton).present());
    return this;
  }

  /**
   * Gets re add button caption.
   *
   * @param itemName the item name
   * @return the re add button caption
   */
  public String getReAddButtonCaption(String itemName) {
    logger.info("Getting Re add button caption");
    getReAddItemButton.initializeWithFormat(itemName);
    return getReAddItemButton.getText().trim();
  }

  /**
   * Gets remove button caption.
   *
   * @param itemName the item name
   * @return the remove button caption
   */
  public String getRemoveButtonCaption(String itemName) {
    logger.info("Getting Remove button caption");
    getRemoveItemButton.initializeWithFormat(itemName);
    return getRemoveItemButton.getText().trim();
  }

  /**
   * Re add mini cart container chunk.
   *
   * @param itemName the item name
   * @return the mini cart container chunk
   */
  public MiniCartContainerChunk reAdd(String itemName) {
    logger.info("Clicking re-add button");
    getReAddItemButton.initializeWithFormat(itemName);
    getReAddItemButton.click();
    getRemoveItemButton.initializeWithFormat(itemName);
    SdkHelper.getSyncHelper().wait(Until.uiElement(getRemoveItemButton).present());
    return this;
  }
}
