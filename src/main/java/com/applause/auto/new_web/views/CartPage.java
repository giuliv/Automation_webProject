package com.applause.auto.new_web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import java.time.Duration;
import java.util.List;

@Implementation(is = CartPage.class, on = Platform.WEB)
@Implementation(is = CartPage.class, on = Platform.WEB_MOBILE_PHONE)
public class CartPage extends BaseComponent {

  @Locate(id = "your-shopping-cart", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private List<Text> productName;

  @Locate(css = "span.bag-item__option-name", on = Platform.WEB)
  private List<Text> grindSelected;

  @Locate(css = "input[name='quantity']", on = Platform.WEB)
  private List<Text> productQuantity;

  @Locate(css = ".bag-item__price--original", on = Platform.WEB)
  private List<Text> productPrice;

  @Locate(css = ".bag-item__photo img", on = Platform.WEB)
  private List<Image> productImage;

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private Button checkOutButton;

  @Locate(id = "bagContinue", on = Platform.WEB)
  private Button closeButton;

  @Locate(className = "bag-shipping__bar", on = Platform.WEB)
  private ContainerElement progressShippingBarElement;

  @Locate(id = "bagNoteBtn", on = Platform.WEB)
  private Button thisIsGiftButton;

  @Locate(id = "bagSubtotal", on = Platform.WEB)
  private Text subTotalValue;

  @Locate(id = "bagShipDate", on = Platform.WEB)
  private Text estimatedShipDate;

  @Locate(id = "bagRecommendations", on = Platform.WEB)
  private ContainerElement otherPurchasedContainer;

  @Locate(className = "increment__add", on = Platform.WEB)
  private List<Button> increaseQuantityButton;

  @Locate(className = "increment__subtr", on = Platform.WEB)
  private List<Button> decreaseQuantityButton;

  @Locate(css = "og-offer > og-when.og-offer button.og-optout-btn", on = Platform.WEB)
  private Button oneTimePurchaseButton;

  @Locate(css = "og-offer > og-when.og-offer button.og-optin-btn", on = Platform.WEB)
  private Button subscribeButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  public String getProductNameByIndex(int index) {
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(productName.get(index - 1))
                .visible()
                .setTimeout(Duration.ofSeconds(30)));
    logger.info(
        "[Cart] Product Name: " + productName.get(index - 1).getText().toLowerCase().trim());

    return productName.get(index - 1).getText().toLowerCase().trim();
  }

  public String getGrindSelectedByIndex(int index) {
    logger.info("Grind Size elements: " + grindSelected.size());
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected.get(index - 1)).visible());
    logger.info(
        "[Cart] Grind Selected: " + grindSelected.get(index - 1).getText().toLowerCase().trim());

    return grindSelected.get(index - 1).getText().toLowerCase().trim();
  }

  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index - 1)).present());
    logger.info(
        "[Cart] Product Quantity: " + productQuantity.get(index - 1).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index - 1).getAttributeValue("value"));
  }

  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }

  public String getProductPriceByIndex(int index) {
    logger.info("Product Size elements: " + productPrice.size());
    SdkHelper.getSyncHelper().waitUntil(driver -> !getProductPrice(index).isEmpty());
    return getProductPrice(index);
  }

  private String getProductPrice(int index) {
    ((LazyList<?>) productPrice).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(productPrice.get(index - 1)).visible());
    String price = productPrice.get(index - 1).getText().toLowerCase().split(" ")[0].trim();
    logger.info("[Cart] Price: " + price);
    return price;
  }

  public boolean productImageIsDisplayed(int index) {
    logger.info("Checking product image by index [{}] is displayed", index);
    if (productImage.size() < index) {
      logger.error("The product with index [{}] isn't displayed");
      return false;
    }

    return productImage.get(index - 1).isDisplayed();
  }

  public boolean progressShippingBarIsDisplayed() {
    return progressShippingBarElement.isDisplayed();
  }

  public boolean thisIsGiftIsDisplayed() {
    return thisIsGiftButton.isDisplayed();
  }

  public boolean subTotalIsDisplayed() {
    return subTotalValue.isDisplayed();
  }

  public boolean estimatedShipDateIsDisplayed() {
    return estimatedShipDate.isDisplayed();
  }

  public boolean checkOutButtonIsDisplayed() {
    return checkOutButton.isDisplayed();
  }

  public boolean otherPurchasedIsDisplayed() {
    return otherPurchasedContainer.exists();
  }

  /**
   * Click on the (+) icon for the product by index
   *
   * @param index
   * @return CartPage
   */
  public CartPage increaseQuantity(int index) {
    logger.info("Clicking on the (+) Quantity button by product index - [{}]", index);
    if (increaseQuantityButton.size() < index) {
      throw new RuntimeException("The product with index [{}] isn't displayed");
    }

    increaseQuantityButton.get(index).click();
    return this;
  }

  /**
   * Click on the (-) icon for the product by index
   *
   * @param index
   * @return CartPage
   */
  public CartPage decreaseQuantity(int index) {
    logger.info("Clicking on the (-) Quantity button by product index - [{}]", index);
    if (decreaseQuantityButton.size() < index) {
      throw new RuntimeException("The product with index [{}] isn't displayed");
    }

    decreaseQuantityButton.get(index).click();
    return this;
  }

  /**
   * Click on the 'ONE-TIME PURCHASE'
   *
   * @return CartPage
   */
  public CartPage clickOneTimePurchaseButton() {
    logger.info("Selecting One time purchase");
    oneTimePurchaseButton.click();
    return this;
  }

  /**
   * Click on the 'SUBSCRIBE & GET FREE SHIPPING' button
   *
   * @return CartPage
   */
  public CartPage clickSubscribeButton() {
    logger.info("Selecting SUBSCRIBE & GET FREE SHIPPING");
    subscribeButton.click();
    return this;
  }

  /** @return boolean */
  public boolean isOneTimePurchaseButtonEnabled() {
    return oneTimePurchaseButton.isEnabled();
  }

  /** @return boolean */
  public boolean isSubscribeButtonEnabled() {
    return subscribeButton.isEnabled();
  }
}
