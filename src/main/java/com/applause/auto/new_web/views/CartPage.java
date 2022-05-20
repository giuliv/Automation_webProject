package com.applause.auto.new_web.views;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.NeverMissAnOfferChunk;
import com.applause.auto.web.components.OtherPurchasedItemChunk;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.WebDriverException;
import org.testng.asserts.SoftAssert;

@Implementation(is = CartPage.class, on = Platform.WEB)
@Implementation(is = CartPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class CartPage extends BaseComponent {

  @Locate(css = "#your-shopping-cart, #bagForm", on = Platform.WEB)
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
  protected Button thisIsGiftButton;

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

  @Locate(css = "#bagNoteInput", on = Platform.WEB)
  private TextBox addPersonalMessageField;

  @Locate(xpath = "//*[@id='bagRecommendationsWrapper']//article", on = Platform.WEB)
  private List<OtherPurchasedItemChunk> listOfOtherPurchased;

  @Locate(className = "bag__empty-heading", on = Platform.WEB)
  private Text emptyMessage;

  @Locate(css = ".bag__touts a img ", on = Platform.WEB)
  private LazyList<Image> shopabbleItems;

  @Locate(css = ".bag__touts a p", on = Platform.WEB)
  private LazyList<Text> shopabbleTitles;

  @Getter @Locate public NeverMissAnOfferChunk neverMissAnOfferChunk;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(30)));
  }

  /* -------- Actions -------- */

  @Step("Get product name")
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

  @Step("Get grind selected")
  public String getGrindSelectedByIndex(int index) {
    logger.info("Grind Size elements: " + grindSelected.size());
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected.get(index - 1)).visible());
    logger.info(
        "[Cart] Grind Selected: " + grindSelected.get(index - 1).getText().toLowerCase().trim());

    return grindSelected.get(index - 1).getText().toLowerCase().trim();
  }

  @Step("Get product quantity")
  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index - 1)).present());
    logger.info(
        "[Cart] Product Quantity: " + productQuantity.get(index - 1).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index - 1).getAttributeValue("value"));
  }

  @Step("Click continue to checkout")
  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Get product price")
  public String getProductPriceByIndex(int index) {
    logger.info("Product Size elements: " + productPrice.size());
    SdkHelper.getSyncHelper().waitUntil(driver -> !getProductPrice(index).isEmpty());
    return getProductPrice(index);
  }

  @Step("Get product price")
  private String getProductPrice(int index) {
    ((LazyList<?>) productPrice).initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(productPrice.get(index - 1)).visible());
    String price = productPrice.get(index - 1).getText().toLowerCase().split(" ")[0].trim();
    logger.info("[Cart] Price: " + price);
    return price;
  }

  @Step("Get product image")
  public boolean productImageIsDisplayed(int index) {
    logger.info("Checking product image by index [{}] is displayed", index);
    if (productImage.size() < index) {
      logger.error("The product with index [{}] isn't displayed");
      return false;
    }

    return productImage.get(index - 1).isDisplayed();
  }

  @Step("Get progress shipping bar")
  public boolean progressShippingBarIsDisplayed() {
    return progressShippingBarElement.isDisplayed();
  }

  @Step("Get This is gift")
  public boolean thisIsGiftIsDisplayed() {
    return thisIsGiftButton.isDisplayed();
  }

  /**
   * Click on 'This is a gift' button
   *
   * @return CartPage
   */
  @Step("Click This is gift")
  public CartPage clickOnThisIsGift() {
    logger.info("Clicking on 'This is a gift' button");
    String attribute = thisIsGiftButton.getAttributeValue(Attribute.ARIA_EXPANDED.getValue());
    thisIsGiftButton.click();
    SdkHelper.getSyncHelper()
        .waitUntil(
            driver ->
                !attribute.equals(
                    thisIsGiftButton.getAttributeValue(Attribute.ARIA_EXPANDED.getValue())));
    return this;
  }

  @Step("Get subtotal")
  public boolean subTotalIsDisplayed() {
    return subTotalValue.isDisplayed();
  }

  @Step("Get estimated ship date")
  public boolean estimatedShipDateIsDisplayed() {
    return estimatedShipDate.isDisplayed();
  }

  @Step("Get checkout")
  public boolean checkOutButtonIsDisplayed() {
    return checkOutButton.isDisplayed();
  }

  @Step("Get other purchased")
  public boolean otherPurchasedIsDisplayed() {
    return otherPurchasedContainer.exists();
  }

  /**
   * Click on the (+) icon for the product by index
   *
   * @param index
   * @return CartPage
   */
  @Step("Increase quantity")
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
  @Step("Decrease quantity")
  public CartPage decreaseQuantity(int index) {
    logger.info("Clicking on the (-) Quantity button by product index - [{}]", index);
    if (decreaseQuantityButton.size() < index) {
      throw new RuntimeException("The product with index [{}] isn't displayed");
    }

    SdkHelper.getSyncHelper().wait(Until.uiElement(decreaseQuantityButton.get(index)).visible());
    decreaseQuantityButton.get(index).click();
    return this;
  }

  /**
   * Click on the 'ONE-TIME PURCHASE'
   *
   * @return CartPage
   */
  @Step("Click one time purchase")
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
  @Step("Click subscribe")
  public CartPage clickSubscribeButton() {
    logger.info("Selecting SUBSCRIBE & GET FREE SHIPPING");
    subscribeButton.click();
    return this;
  }

  /** @return boolean */
  @Step("Get one time purchase button")
  public boolean isOneTimePurchaseButtonEnabled() {
    return oneTimePurchaseButton.isEnabled();
  }

  /** @return boolean */
  @Step("Get subscribe button")
  public boolean isSubscribeButtonEnabled() {
    return subscribeButton.isEnabled();
  }

  /** @return boolean */
  @Step("Get Add personal message field")
  public boolean isAddPersonalMessageFieldDisplayed() {
    return WebHelper.isDisplayed(addPersonalMessageField);
  }

  /** @return String */
  @Step("Get Add personal message")
  public String getAddPersonalMessageFieldText() {
    String message = addPersonalMessageField.getCurrentText();
    logger.info("Text from 'Add Personal Message' field - [{}]", message);
    return message;
  }

  /**
   * Type Personal Message
   *
   * @param message
   * @return CartPage
   */
  @Step("Type personal message")
  public CartPage typePersonalMessage(String message) {
    logger.info("Typing [{}] into the 'Add Personal Message' field");
    addPersonalMessageField.clearText();
    addPersonalMessageField.sendKeys(message);
    return this;
  }

  /**
   * @param position
   * @return OtherPurchasedItemChunk
   */
  @Step("Get list of other purchased")
  public OtherPurchasedItemChunk getPurchasedItemOnPosition(int position) {
    ((LazyList<?>) listOfOtherPurchased).initialize();
    return listOfOtherPurchased.get(position - 1);
  }

  /**
   * Check if Cart page is displayed
   *
   * @return boolean
   */
  @Step("Get checkout button")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer) && WebHelper.isDisplayed(checkOutButton);
  }

  @Step("Get Empty Cart message")
  public String getEmptyCartMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(emptyMessage).visible());
    String message = emptyMessage.getText();
    logger.info("Text from 'Empty Cart' field - [{}]", message);
    return message;
  }

  @Step("Get Total shopabble items")
  public int getTotalShopabbleItems() {
    int total = shopabbleItems.size();
    logger.info("Total shopabble items - [{}]", total);
    return total;
  }

  public <T extends BaseComponent> T openShopabbleItemsByIndex(Class<T> clazz, int index) {
    logger.info("Opening shoppable item: " + index);
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopabbleTitles.get(index)).present());
    try {
      shopabbleTitles.get(index).click();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }

    return SdkHelper.create(clazz);
  }

  public boolean isShopabbleItemDisplayed(int index) {
    logger.info("Reviewing shopabble items");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopabbleTitles.get(index)).present());
    return shopabbleTitles.get(index).isDisplayed() && shopabbleItems.get(index).isDisplayed();
  }
}

class CartPageMobile extends CartPage {

  @Override
  public CartPage clickOnThisIsGift() {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      logger.info("Clicking on 'This is a gift' button");
      String attribute = thisIsGiftButton.getAttributeValue(Attribute.ARIA_EXPANDED.getValue());
      SdkHelper.getBrowserControl().jsClick(thisIsGiftButton);
      SdkHelper.getSyncHelper()
          .waitUntil(
              driver ->
                  !attribute.equals(
                      thisIsGiftButton.getAttributeValue(Attribute.ARIA_EXPANDED.getValue())));
      return this;
    } else {
      return super.clickOnThisIsGift();
    }
  }
}
