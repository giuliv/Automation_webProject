package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CartPage;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.CommonWebPage;
import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

@Implementation(is = MiniCart.class, on = Platform.WEB)
@Implementation(is = MiniCart.class, on = Platform.WEB_MOBILE_PHONE)
public class MiniCart extends BaseComponent {

  @Locate(id = "bagForm", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private List<Text> productName;

  @Locate(css = "h4.bag-item__title", on = Platform.WEB)
  private Text productNameOnlyItem;

  @Locate(css = "span.bag-item__option-name ", on = Platform.WEB)
  private List<Text> grindSelected;

  @Locate(css = ".bag-item__action--qty input[name='quantity']", on = Platform.WEB)
  private List<Text> productQuantity;

  @Locate(css = ".bag-item__header span[data-unit-price]", on = Platform.WEB)
  private List<Text> itemPrice;

  @Locate(xpath = "//span[contains(., 'Plus get up to')]", on = Platform.WEB)
  private List<Text> itemDiscount;

  @Locate(css = "span.og-tooltip-inner", on = Platform.WEB)
  private List<Text> itemDiscountHint;

  @Locate(css = "a[href*='checkout']", on = Platform.WEB)
  private Button checkOutButton;

  @Locate(id = "bagContinue", on = Platform.WEB)
  private Button closeButton;

  @Locate(css = "button[data-action='add']", on = Platform.WEB)
  private Button addButton;

  @Locate(css = "button[data-action='remove']", on = Platform.WEB)
  private Button removeItem;

  @Locate(css = "a[title='Remove item']", on = Platform.WEB)
  private Link removeItemLink;

  @Locate(css = "#bagShipping .bag-shipping__bar", on = Platform.WEB)
  private Image progressBar;

  @Locate(css = "div.bag-shipping__fill", on = Platform.WEB)
  private Image progressBarFill;

  @Locate(id = "bagRecommendations", on = Platform.WEB)
  private ContainerElement recommendationSection;

  @Locate(id = "bagSubtotal", on = Platform.WEB)
  private Text subTotal;

  @Locate(id = "bagEstimateShip", on = Platform.WEB)
  private Text estimatedShipDate;

  @Locate(css = ".bag__footer a[href*='cart']", on = Platform.WEB)
  private Button viewCartButton;

  @Locate(css = "#bagEmpty h3", on = Platform.WEB)
  private Text emptyMiniCartMessage;

  @Locate(css = "#bagRecommendationsWrapper button", on = Platform.WEB)
  private List<Button> recommendedForYouAddButton;

  @Locate(css = "div.srd_msg strong", on = Platform.WEB)
  private Text shopRunnerMessage;

  @Locate(css = "#closeIconContainer", on = Platform.WEB)
  protected Button closeSpecialOfferButton;

  protected static final String specialOfferFrameCSS = "#attentive_creative";

  @Locate(css = specialOfferFrameCSS, on = Platform.WEB)
  protected ContainerElement specialOfferFrame;

  @Locate(css = "#sr_headerDiv a[onclick*='learn']", on = Platform.WEB)
  private Link learnMoreLink;

  @Locate(css = "#sr_headerDiv a[onclick*='sign']", on = Platform.WEB)
  private Link signInLink;

  @Locate(css = ".bag-recommendations__footer button[aria-label='Next']", on = Platform.WEB)
  private Button nextRecommendedItem;

  @Locate(css = ".bag-recommendations__footer button[aria-label='Previous']", on = Platform.WEB)
  private Button prevRecommendedItem;

  @Locate(css = "#bagRecommendations article h3", on = Platform.WEB)
  private List<ContainerElement> recommendedItemsNameList;

  @Locate(css = "#bagItems og-offer >og-when.og-offer button.og-optout-btn", on = Platform.WEB)
  private Button oneTimePurchaseButton;

  @Locate(css = "#bagItems og-offer > og-when.og-offer button.og-optin-btn", on = Platform.WEB)
  private Button subscribeButton;

  @Locate(xpath = "//table//p[@class='og-shipping']/*", on = Platform.WEB)
  private SelectList shipEveryDropdown;

  @Locate(css = ".bag-shipping__text", on = Platform.WEB)
  private Text shippingAwayMessage;

  @Locate(css = "i.icon--shipped", on = Platform.WEB)
  private Image truckIcon;

  @Locate(css = ".bag__touts a img ", on = Platform.WEB)
  private LazyList<Image> shopabbleItems;

  @Locate(css = ".bag__touts a p", on = Platform.WEB)
  private LazyList<Text> shopabbleTitles;

  @Locate(css = "#bagEstimateShip i", on = Platform.WEB)
  private Button estimatedToolTip;

  @Locate(css = "#bagEstimateShip p.tooltip__copy", on = Platform.WEB)
  private Text estimatedToolTipText;

  @Locate(css = "#bagEstimateShip p.tooltip__copy a", on = Platform.WEB)
  private Link estimatedToolTipFAQLink;

  @Locate(css = ".bag__has-prepaid", on = Platform.WEB)
  private Text giftExistsInYourCartTextMessage;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  @Step("Product name is displayed")
  public boolean isProductNameDisplayedIndex() {
    return productNameOnlyItem.exists();
  }

  @Step("Get product name")
  public String getProductNameByIndex(int index) {
    if (WebHelper.isSafari()) {
      SdkHelper.getSyncHelper().wait(Until.uiElement(productName.get(index)).present());
    } else {
      SdkHelper.getSyncHelper().wait(Until.uiElement(productName.get(index)).visible());
    }

    logger.info(
        "[MiniCart] Product Name: " + productName.get(index).getText().toLowerCase().trim());

    return productName.get(index).getText().toLowerCase().trim();
  }

  @Step("Get grind")
  public String getGrindByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected.get(index)).visible());
    String grind = grindSelected.get(index).getText().toLowerCase().trim();
    logger.info("[MiniCart] Grind Selected: " + grind);
    return grind;
  }

  @Step("Get price")
  public String getPriceByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemPrice.get(index)).visible());
    logger.info("[MiniCart] Item Price: " + itemPrice.get(index).getText().trim());

    return itemPrice.get(index).getText().trim();
  }

  @Step("Get discount")
  public String getDiscountByIndex(int index, String price) {
    double discountOverThirty = 0.05;
    double discountOverFifty = 0.10;
    double multiplier = 0;
    double tempPrice = Double.parseDouble(price.replace("$", ""));

    if (itemDiscount.get(index).exists()) {
      logger.info("Discount exists, checking discount type...");

      if (Float.parseFloat(subTotal.getText().replace("$", "")) > 50) {
        logger.info("- Subtotal is over 50, applying 10%");
        multiplier = discountOverFifty;
      } else if (Float.parseFloat(subTotal.getText().replace("$", "")) > 30) {
        logger.info("- Subtotal is over 30, applying 5%");
        multiplier = discountOverThirty;
      }
    }
    tempPrice = (tempPrice - tempPrice * multiplier);
    BigDecimal bd = new BigDecimal(Double.toString(tempPrice));
    bd = bd.setScale(2, RoundingMode.HALF_UP);
    return "$" + bd.doubleValue();
  }

  @Step("Get product quantity")
  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index)).present());
    logger.info(
        "[MiniCart] Product Quantity: " + productQuantity.get(index).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index).getAttributeValue("value"));
  }

  @Step("Get progress bar quantity")
  public float getProgressBarQuantity() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(progressBar).present());
    float progress =
        Float.parseFloat(
            progressBarFill
                .getAttributeValue("style")
                .replace("width:", "")
                .replace("%", "")
                .replace(";", ""));
    logger.info("[MiniCart] Progress Bar: " + progress);

    return progress;
  }

  @Step("Get empty minicart message")
  public String getEmptyMiniCartMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(emptyMiniCartMessage).present());
    logger.info("[MiniCart] Empty MiniCart message: " + emptyMiniCartMessage.getText().trim());

    return emptyMiniCartMessage.getText().trim();
  }

  @Step("Add an item")
  public void addOneMoreItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(addButton).clickable());
    addButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  @Step("Remove an item")
  public void removeOneItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(removeItem).clickable());
    removeItem.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  @Step("Remove item")
  public void removeItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(removeItemLink).clickable());
    removeItemLink.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  @Step("Validate minicart elements")
  public SoftAssert validateGeneralMiniCartElements() {
    SoftAssert softAssert = new SoftAssert();

    softAssert.assertTrue(removeItemLink.isDisplayed(), "Remove item button is not displayed");
    softAssert.assertTrue(progressBar.isDisplayed(), "ProgressBar is not displayed");
    softAssert.assertTrue(
        recommendationSection.isDisplayed(), "Recommendation Section is not displayed");
    softAssert.assertTrue(subTotal.isDisplayed(), "SubTotal is not displayed");
    softAssert.assertTrue(estimatedShipDate.isDisplayed(), "Estimated Ship Date is not displayed");
    softAssert.assertTrue(checkOutButton.isDisplayed(), "CheckOut is not displayed");
    softAssert.assertTrue(viewCartButton.isDisplayed(), "View Cart is not displayed");

    return softAssert;
  }

  @Step("Verify Remove link is displayed")
  public boolean isRemoveItemLinkDisplayed() {
    return WebHelper.isDisplayed(removeItemLink, 10);
  }

  @Step("Click continue to checkout")
  public CheckOutPage clickContinueToCheckOut() {
    return clickOnContinueToCheckOutButton(CheckOutPage.class);
  }

  @Step("Click continue to checkout")
  public <T extends BaseComponent> T clickOnContinueToCheckOutButton(Class<T> clazz) {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(checkOutButton).visible().setTimeout(Duration.ofSeconds(40)));
    checkOutButton.click();
    return SdkHelper.create(clazz);
  }

  @Step("Close miniCart")
  public <V extends BaseComponent> V closeMiniCart(Class<V> expectedClass) {
    logger.info("Closing miniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).visible());
    closeButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action

    return SdkHelper.create(expectedClass);
  }

  /**
   * Click 'View Cart' button
   *
   * @return CartPage
   */
  @Step("Click view cart")
  public CartPage clickViewCartButton() {
    logger.info("Clicking 'View Cart' button");
    SdkHelper.getBrowserControl().jsClick(viewCartButton);
    SdkHelper.getSyncHelper().sleep(2000); // Wait for item to be added

    return SdkHelper.create(CartPage.class);
  }

  @Step("Click on shopRunner links")
  public ShopRunnerComponent clickShopRunnerLinks(String link) {
    logger.info("Clicking link: " + link);
    if (link.equalsIgnoreCase("learn more")) {
      learnMoreLink.click();
    } else {
      signInLink.click();
    }
    return SdkHelper.create(ShopRunnerComponent.class);
  }

  @Step("Get shoprunner message")
  public String getShopRunnerMessage() {
    logger.info("Reading ShopRunner message");
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopRunnerMessage).visible());
    logger.info("-- Message found, is: " + shopRunnerMessage.getText().trim());
    return shopRunnerMessage.getText().trim().toLowerCase();
  }

  /**
   * Click on 'Add to cart' button on 'Recommended for you' section
   *
   * @param index
   * @return QuickViewComponent
   */
  @Step("Click on recommended for you")
  public QuickViewComponent clickOnRecommendedForYouAddButtonByIndex(int index) {
    logger.info("Click on recommended 'Add to cart' button with index: " + index);
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(recommendedForYouAddButton.get(index)).visible());

    if (WebHelper.isSafari() && !WebHelper.isMobile()) {
      logger.info("Safari Desktop");
      WebHelper.jsClick(recommendedForYouAddButton.get(index).getWebElement());
    } else {
      recommendedForYouAddButton.get(index).click();
    }

    return SdkHelper.create(QuickViewComponent.class);
  }

  @Step("Click arrow")
  public void clickNavigationArrow(Constants.NavigationArrow navigationArrow) {
    logger.info("Click Arrow: " + navigationArrow.name());
    WebHelper.scrollToElement(nextRecommendedItem);

    if (navigationArrow.equals(Constants.NavigationArrow.NEXT)) {
      nextRecommendedItem.click();
    } else {
      prevRecommendedItem.click();
    }
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
  }

  @Step("Get recommended items")
  public long getRecommendedItemsVisible() {
    return recommendedForYouAddButton.stream().filter(x -> x.isDisplayed()).count();
  }

  @Step("Get recommended item name")
  public String getRecommendedItemNameByIndex(int index) {
    logger.info("Recommended Item: " + recommendedItemsNameList.get(index).getText());
    return recommendedItemsNameList.get(index).getText();
  }

  /**
   * Click on the 'ONE-TIME PURCHASE'
   *
   * @return CartPage
   */
  @Step("Click one time purchase")
  public MiniCart clickOneTimePurchaseButton() {
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
  public MiniCart clickSubscribeButton() {
    logger.info("Selecting SUBSCRIBE & GET FREE SHIPPING");
    subscribeButton.click();
    return this;
  }

  /** @return boolean */
  @Step("Get one time purchase")
  public boolean isOneTimePurchaseButtonEnabled() {
    return oneTimePurchaseButton.isEnabled();
  }

  /** @return boolean */
  @Step("Get subscribe")
  public boolean isSubscribeButtonEnabled() {
    return subscribeButton.isEnabled();
  }

  @Step("Get Selected Ship Every")
  public String getSelectedShipEvery() {
    Select dropdown = new Select(getShipEveryDropdown());
    String selected = WebHelper.cleanString(dropdown.getFirstSelectedOption().getText());
    logger.info("Selected Ship Every - [{}]", selected);
    return selected;
  }

  private WebElement getShipEveryDropdown() {
    return WebHelper.findShadowElementsBy(
            shipEveryDropdown.getWebElement(), By.cssSelector("og-select"))
        .stream()
        .map(i -> WebHelper.findShadowElementsBy(i, By.cssSelector("select")).get(0))
        .findFirst()
        .get();
  }

  public boolean isShipEveryDropdownDisplayed() {
    return WebHelper.isDisplayed(shipEveryDropdown, 5);
  }

  public String getShippingAwayMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(shippingAwayMessage).visible());
    String message = shippingAwayMessage.getText();
    logger.info("Shipping away message: {}", message);

    return message;
  }

  public boolean isTruckIconDisplayed() {
    return truckIcon.isDisplayed();
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
    shopabbleTitles.get(index).click();

    return SdkHelper.create(clazz);
  }

  public boolean isShopabbleItemDisplayed(int index) {
    logger.info("Reviewing shopabble items");
    SdkHelper.getSyncHelper().sleep(1000); // Wait for items
    SdkHelper.getSyncHelper().wait(Until.uiElement(shopabbleTitles.get(index)).present());
    return shopabbleTitles.get(index).isDisplayed() && shopabbleItems.get(index).isDisplayed();
  }

  @Step("Get estimated ship date")
  public boolean estimatedShipDateIsDisplayed() {
    return estimatedShipDate.isDisplayed();
  }

  @Step("Get estimated ship date")
  public String getEstimatedShipDate() {
    logger.info("Estimated Ship Text: " + estimatedShipDate.getText());
    return estimatedShipDate.getText();
  }

  public void openEstimatedTooltip() {
    logger.info("Click over estimated date tooltip");
    estimatedToolTip.click();
  }

  public String getEstimatedDateTooltipText() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(estimatedToolTipText).visible());
    String message = estimatedToolTipText.getText();
    logger.info("Estimated Date Tooltip text {}", message);

    return message;
  }

  public CommonWebPage openEstimatedFAQLink() {
    logger.info("Click over estimated FAQ link");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    SdkHelper.getSyncHelper().wait(Until.uiElement(estimatedToolTipFAQLink).clickable());
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      estimatedToolTipFAQLink.goToURL(); // Workaround, since click was not working on element
    } else {
      estimatedToolTipFAQLink.click();
      WebHelper.switchToNewTab(windowHandle);
    }

    return SdkHelper.create(CommonWebPage.class);
  }

  @Step("Verify A gift exists in your cart message is displayed")
  public boolean isGiftExistsInYourCartMessageDisplayed() {
    if (!WebHelper.isDisplayed(giftExistsInYourCartTextMessage)) {
      logger.debug("A gift exists in your cart message is not displayed in mini cart");
      return false;
    }

    if (!giftExistsInYourCartTextMessage
        .getText()
        .equalsIgnoreCase(TestData.GIFT_EXIST_IN_YOUR_CART_MESSAGE)) {
      logger.debug(
          "A gift exists in your cart message is wrong in mini cart. Expected [{}]. Actual [{}]",
          TestData.GIFT_EXIST_IN_YOUR_CART_MESSAGE,
          giftExistsInYourCartTextMessage.getText());
      return false;
    }

    return true;
  }

  @Step("Verify Remove item button is displayed")
  public boolean isRemoveItemButtonDisplayed() {
    return WebHelper.isDisplayed(removeItem);
  }

  @Step("Verify Checkout item button is displayed")
  public boolean isCheckoutButtonDisplayed() {
    return WebHelper.isDisplayed(checkOutButton);
  }

  @Step("Click Learn More Link")
  public PlpLearnMoreOverlappingComponent clickLearnMoreLink() {
    logger.info("Clicking on the 'Learn More' Link");
    WebHelper.scrollToElement(learnMoreLink);
    learnMoreLink.click();
    return SdkHelper.create(PlpLearnMoreOverlappingComponent.class);
  }

  @Step("Click Sign In Link")
  public PlpSignInOverlappingComponent clickSignInLink() {
    logger.info("Clicking on the 'Sign In' Link");
    signInLink.click();
    return SdkHelper.create(PlpSignInOverlappingComponent.class);
  }
}
