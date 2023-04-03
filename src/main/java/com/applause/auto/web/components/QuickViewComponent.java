package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CheckOutPage;
import com.applause.auto.web.views.ProductDetailsPage;
import io.qameta.allure.Step;
import java.util.List;
import org.testng.asserts.SoftAssert;

@Implementation(is = QuickViewComponent.class, on = Platform.WEB)
@Implementation(is = QuickViewComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class QuickViewComponent extends BaseComponent {

  @Locate(css = "#modalQuickAdd .modal__inner--quick-add", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".modal__inner--quick-add [class*=title]", on = Platform.WEB)
  private Text productName;

  @Locate(css = ".modal__inner--quick-add .pv-price__original", on = Platform.WEB)
  private Text productPrice;

  @Locate(css = ".modal__inner--quick-add .pv-details", on = Platform.WEB)
  private Text productDetails;

  @Locate(css = ".pv-header__reviews", on = Platform.WEB)
  private ContainerElement starRatings;

  @Locate(css = ".bv_numReviews_component_container .bv_numReviews_text", on = Platform.WEB)
  private Text reviewCount;

  @Locate(css = "button.bv_button_buttonFull", on = Platform.WEB)
  private Button readReviewBox;

  @Locate(css = "div[data-message-id='quickGrind']", on = Platform.WEB)
  private ContainerElement grindSection;

  @Locate(css = "div.open li", on = Platform.WEB)
  private List<Text> grindOptions;

  @Locate(css = ".pv-qty", on = Platform.WEB)
  private ContainerElement quantitySection;

  @Locate(id = "productViewQuantityButton%s", on = Platform.WEB)
  private ContainerElement quantityBox;

  @Locate(css = "#productViewQuantityButton%s.is-selected", on = Platform.WEB)
  private ContainerElement quantityBoxSelected;

  @Locate(
      css = "#productViewQuantityButton%s span.pv-qty__button-text--discount",
      on = Platform.WEB)
  private Text quantityBoxText;

  @Locate(css = "#modalQuickAdd #quickBtnAddToBagText", on = Platform.WEB)
  private Button addToCartButton;

  @Locate(css = "#modalQuickAdd button.modal__close", on = Platform.WEB)
  private Button closeButton;

  @Locate(xpath = "//div[@id='srd_pd']//strong", on = Platform.WEB)
  private Text freeText;

  @Locate(xpath = "//div[@id='srd_pd']//span//a[contains(text(),'sign in')]", on = Platform.WEB)
  private Text signIn;

  @Locate(xpath = "//div[@id='srd_pd']//span//a[contains(text(),'learn more')]", on = Platform.WEB)
  private Text learnMore;

  @Locate(xpath = "//div[@id='srd_pd']//span//a[contains(text(),'sign in')]", on = Platform.WEB)
  private ContainerElement signInLink;

  @Locate(xpath = "//div[@id='srd_pd']//span//a[contains(text(),'learn more')]", on = Platform.WEB)
  private ContainerElement learnMoreLink;

  @Locate(xpath = "//div[@class='form-item']//ul//li", on = Platform.WEB)
  private List<Text> grindList;

  @Locate(xpath = "//div[@class='form-item']//div[@aria-label='Select grind']", on = Platform.WEB)
  private ContainerElement grindDropDown;

  @Locate(
      xpath =
          "//div[@class='form-item']//div[@aria-label='Select grind']/../ul/li[@data-value='%s']",
      on = Platform.WEB)
  private Button grindOptionButton;

  @Locate(xpath = "//span[@id='quantityText']", on = Platform.WEB)
  private Text quantity;

  @Locate(xpath = "//button[@id='decrement']", on = Platform.WEB)
  private Button minusButton;

  @Locate(xpath = "//button[@id='increment']", on = Platform.WEB)
  private Button plusButton;

  @Locate(css = "og-when[test='regularEligible'] p.og-shipping option", on = Platform.WEB)
  private LazyList<Text> subscriptionWeeks;

  @Locate(css = "og-when[test*='regular'] div.og-frequency-row", on = Platform.WEB)
  private TextBox subscriptionWeekBox;

  @Locate(css = "[test='regularEligible'] button.og-optin-btn", on = Platform.WEB)
  private Button subscribeType;

  @Locate(css = "button.og-optout-btn", on = Platform.WEB)
  private Button oneTimePurchase;

  @Locate(id = "gift-duration", on = Platform.WEB)
  private SelectList giftDurationDropdown;

  @Locate(css = "a.quick-add__cta", on = Platform.WEB)
  private Button viewProductButton;

  @Locate(
      css = "button[id*='productViewQuantityButton'].is-selected,#quantityText",
      on = Platform.WEB)
  private Text productQuantity;

  @Locate(id = "increment", on = Platform.WEB)
  protected Button incrementQuantityButton;

  @Locate(id = "decrement", on = Platform.WEB)
  private Button decrementQuantityButton;

  @Override
  public void afterInit() {
    logger.info("QuickView Init method");
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  @Step("Get UI elements")
  public SoftAssert validateMainUIElements() {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(productName.isDisplayed(), "Product name is not displayed");
    softAssert.assertTrue(productPrice.isDisplayed(), "Product price is not displayed");
    softAssert.assertTrue(productDetails.isDisplayed(), "Product details is not displayed");
    softAssert.assertTrue(starRatings.isDisplayed(), "Star Ratings is not displayed");
    softAssert.assertTrue(grindSection.isDisplayed(), "Grind Section is not displayed");
    softAssert.assertTrue(quantitySection.isDisplayed(), "Quantity Section is not displayed");
    softAssert.assertTrue(addToCartButton.isDisplayed(), "Add to Cart is not displayed");

    return softAssert;
  }

  public SoftAssert validateMainAndSecondaryUIElements() {
    SoftAssert softAssert = validateMainUIElements();
    softAssert.assertTrue(
        Integer.parseInt(reviewCount.getText().replaceAll("[^\\d.]", "")) >= 1,
        "Review count is not correct");

    if (WebHelper.isDesktop()) {
      logger.info("Hover Stars Review section [Only Desktop]");
      WebHelper.hoverByAction(starRatings);

      softAssert.assertTrue(
          readReviewBox.getAttributeValue("aria-expanded").equals("true"),
          "AutoDropDown Review module is not displayed");
    }

    return softAssert;
  }

  public boolean isGeneralQuantityBoxDisplayed() {
    return quantitySection.isDisplayed();
  }

  public SoftAssert validateQuantityElements() {
    SoftAssert softAssert = new SoftAssert();

    WebHelper.waitForVisibleOrPresent(quantitySection);
    quantityBoxSelected.format(1).initialize();
    softAssert.assertTrue(quantityBoxSelected.isDisplayed(), "Quantity 1 is not default value");

    quantityBox.format(2).initialize();
    quantityBoxText.format(2).initialize();
    quantityBox.click();
    quantityBoxSelected.format(2).initialize();
    softAssert.assertTrue(quantityBoxSelected.isDisplayed(), "Quantity 2 is not default value");
    softAssert.assertEquals(
        quantityBoxText.getText().replace("\n", ""),
        "SUBSCRIBE &GET 5% OFF",
        "Quantity 2 text does not matches");
    SdkHelper.getSyncHelper().sleep(1000); // Wait for change

    quantityBox.format(3).initialize();
    quantityBoxText.format(3).initialize();
    softAssert.assertEquals(
        quantityBoxText.getText().replace("\n", ""),
        "SUBSCRIBE &GET 10% OFF",
        "Quantity 3 text does not matches");
    if (WebHelper.isMobile()) {
      WebHelper.scrollToElement(quantityBox);
    }

    quantityBox.click();
    softAssert.assertFalse(quantityBoxSelected.isDisplayed(), "Quantity 2 should not be displayed");

    return softAssert;
  }

  public SoftAssert validateGiftQuantityElements() {
    SoftAssert softAssert = new SoftAssert();

    WebHelper.waitForVisibleOrPresent(quantitySection);
    quantityBoxSelected.format(1).initialize();
    softAssert.assertTrue(quantityBoxSelected.isDisplayed(), "Quantity 1 is not default value");

    quantityBox.format(2).initialize();
    quantityBox.click();
    quantityBoxSelected.format(2).initialize();
    softAssert.assertTrue(quantityBoxSelected.isDisplayed(), "Quantity 2 is not default value");
    SdkHelper.getSyncHelper().sleep(1000); // Wait for change

    quantityBox.format(3).initialize();
    quantityBox.click();
    quantityBoxSelected.format(3).initialize();
    softAssert.assertFalse(quantityBoxSelected.isDisplayed(), "Quantity 3 is not selected");

    return softAssert;
  }

  public SoftAssert validateQuantityBoxSectionElements() {
    SoftAssert softAssert = new SoftAssert();

    WebHelper.waitForVisibleOrPresent(quantitySection);
    softAssert.assertEquals(getProductQuantitySelected(), 3, "Quantity 3 is not default value");

    clickQuantityPlusButton();

    WebHelper.waitForVisibleOrPresent(quantitySection);
    softAssert.assertEquals(getProductQuantitySelected(), 6, "Quantity 6 is not selected");

    clickQuantityPlusButton();

    WebHelper.waitForVisibleOrPresent(quantitySection);
    softAssert.assertEquals(getProductQuantitySelected(), 9, "Quantity 9 is not selected");
    return softAssert;
  }

  public boolean isGrindDisplayed() {
    return WebHelper.exists(grindSection, 5);
  }

  public String getGrind() {
    logger.info("Grind value: [{}]", grindSection.getText());
    return grindSection.getText();
  }

  public QuickViewComponent selectGrindByIndex(int index) {
    logger.info("Selecting new Grind with index: [{}]", index);
    grindSection.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait to be populated

    grindOptions.get(index).click();
    return this;
  }

  public QuickViewComponent selectGrind(GrindDropdown grind) {
    logger.info("Selecting new Grind with name: [{}]", grind.getValue());
    if (getSelectGrind().equals(grind.getValue())) {
      logger.info("The Grind [{}] is already selected.", grind.getValue());
      return this;
    }

    grindDropDown.click();
    grindOptionButton.format(grind.getValue());
    WebHelper.jsClick(grindOptionButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait to be populated
    return this;
  }

  public String getSelectGrind() {
    String selectedGrind = grindDropDown.getAttributeValue(Attribute.DATA_VALUE.getValue());
    logger.info("Selected Grind: {}", selectedGrind);
    return selectedGrind;
  }

  public int getGrindOptions() {
    int total = grindOptions.size();
    logger.info("Total Grind options: [{}]", total);
    return total;
  }

  /**
   * Click on the 'Add to cart' button
   *
   * @return MiniCart
   */
  @Step("Click Add to cart")
  public MiniCart clickAddToCart() {
    logger.info("Clicking on the 'Add to cart' button");
    addToCartButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(MiniCart.class);
  }

  /** @return product name */
  @Step("Get product name")
  public String getProductName() {
    return productName.getText().trim();
  }

  public void closeQuickView() {
    logger.info("Closing quickView modal");
    closeButton.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).notPresent());
  }

  @Step("Verify ShopRunner UI elements")
  public SoftAssert validateShopRunnerUIElements() {
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertEquals(
        freeText.getText().trim().toLowerCase(),
        TestData.SHOP_RUNNER_FREE_TEXT.toLowerCase(),
        "FREE 2-Day Shipping & Free Returns Text is not displayed");
    softAssert.assertEquals(
        signInLink.getText().trim(), TestData.SHOP_RUNNER_SIGNIN, "Sign in Link is not displayed");
    softAssert.assertEquals(
        learnMoreLink.getText().trim(),
        TestData.SHOP_RUNNER_LEARN_MORE,
        "Learn more Link is not displayed");
    return softAssert;
  }

  @Step("Click Learn more Link")
  public PlpLearnMoreOverlappingComponent clickLearnMoreLink() {
    logger.info("Clicking on the 'Learn more' Link");
    learnMoreLink.click();
    return SdkHelper.create(PlpLearnMoreOverlappingComponent.class);
  }

  @Step("Click Sign In Link")
  public PlpSignInOverlappingComponent clickSignInLink() {
    logger.info("Clicking on the 'Sign In' Link");
    signInLink.click();

    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      SdkHelper.getSyncHelper().sleep(5000); // Extra wait needed on iOS
    }

    return SdkHelper.create(PlpSignInOverlappingComponent.class);
  }

  @Step("Select the option from GrindDropDown")
  public void selectOption(int index) {
    logger.info("Select the 'DropDown Grind' Option");
    grindDropDown.click();
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindList.get(index)).clickable()).click();
  }

  @Step("Return GrindDropDown Text")
  public String selectedOptionText() {
    logger.info("Select the 'DropDownGrind' Option Text");
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindDropDown).clickable());
    logger.info("Selected Dropdown option " + grindDropDown.getText().trim());
    return grindDropDown.getText().trim();
  }

  @Step("Return the Quantity Value")
  public String getQuantity() {
    logger.info("Get the 'Quantity' Text");
    SdkHelper.getSyncHelper().wait(Until.uiElement(quantity).visible());
    return quantity.getText().trim();
  }

  @Step("Click Minus Button")
  public void clickMinusButton() {
    logger.info("Clicking on the 'Minus' Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(minusButton).clickable()).click();
  }

  @Step("Click Plus Button")
  public void clickPLusButton() {
    logger.info("Clicking on the 'Plus' Button");
    SdkHelper.getSyncHelper().wait(Until.uiElement(plusButton).clickable());

    if (WebHelper.getDriverConfig().toLowerCase().contains("landscape")
        && SdkHelper.getEnvironmentHelper().isAndroid()) {
      logger.info("Android Landscape Click on Plus button");
      WebHelper.jsClick(plusButton.getWebElement());
    } else {
      plusButton.click();
    }
  }

  public boolean isSubscribeTypeAsDefault() {
    logger.info("Review if type, is default value");
    return subscribeType.getAttributeValue("slot").equalsIgnoreCase("default");
  }

  public boolean isSubscriptionWeeksBoxDisplayed() {
    WebHelper.scrollToElement(subscriptionWeekBox);
    return subscriptionWeekBox.isDisplayed();
  }

  public int subscriptionOptionsAvailable() {
    logger.info("Subscription options");
    return subscriptionWeeks.size();
  }

  @Step("Check if 'Add to Cart' is Displayed")
  public boolean isAddToCartButtonDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(addToCartButton);
    logger.info("Add to Cart is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  public String getAddToCartButtonText() {
    logger.info("Add to Cart is displayed - [{}]", addToCartButton.getText());
    return addToCartButton.getText();
  }

  public boolean areSubscribeOrOneTimePurchaseDisplayed() {
    return WebHelper.isDisplayed(oneTimePurchase) || WebHelper.isDisplayed(subscribeType);
  }

  @Step("Click Buy now")
  public CheckOutPage clickBuyNow() {
    logger.info("Clicking on the 'Buy Now' button");
    addToCartButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Verify Gift duration is displayed")
  public boolean isGiftDurationDisplayed() {
    return WebHelper.exists(giftDurationDropdown, 5);
  }

  @Step("Get Grind option")
  public String getGrindOption(int index) {
    if (grindOptions.size() <= index) {
      throw new IndexOutOfBoundsException(
          "Grind options doesn't contains element with index: " + index);
    }

    return grindOptions.get(index).getText();
  }

  @Step("Select quantity by index")
  public void selectQuantityByIndex(int index) {
    logger.info("Selecting quantity by index: {}", index);
    quantityBox.format(index).initialize();
    quantityBox.click();
  }

  @Step("Verify quantity is selected")
  public boolean isQuantitySelected(int index) {
    logger.info("Checking quantity with index {} is selected", index);
    quantityBoxSelected.format(index).initialize();
    return quantityBoxSelected.isDisplayed();
  }

  @Step("Click view product details button")
  public ProductDetailsPage clickViewProduct() {
    viewProductButton.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Click on (+) in Quantity")
  public QuickViewComponent clickQuantityPlusButton() {
    logger.info("Clicking on (+) in Quantity");
    WebHelper.scrollToElement(incrementQuantityButton);
    incrementQuantityButton.click();
    logger.info("Current quantity is - [{}]", getProductQuantitySelected());
    return this;
  }

  @Step("Get product quantity")
  public int getProductQuantitySelected() {
    productQuantity.initialize();
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity).visible());

    String quantity = "";
    if (productQuantity.getAttributeValue("data-quantity") != null) {
      quantity = productQuantity.getAttributeValue("data-quantity");
    } else {
      quantity = productQuantity.getText();
    }

    logger.info("[PDP] Product Quantity: " + quantity);
    return Integer.parseInt(quantity);
  }

  @Step("Click on (-) in Quantity")
  public QuickViewComponent clickQuantityMinusButton() {
    logger.info("Clicking on (-) in Quantity");
    decrementQuantityButton.click();
    logger.info("Current quantity is - [{}]", getProductQuantitySelected());
    return this;
  }
}
