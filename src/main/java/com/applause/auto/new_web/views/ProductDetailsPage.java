package com.applause.auto.new_web.views;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.common.data.enums.ShipEveryDropdown;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.MyReviewModalComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.LocatedBy;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

@Implementation(is = ProductDetailsPage.class, on = Platform.WEB)
@Implementation(is = ProductDetailsPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductDetailsPage extends Base {

  @Locate(css = "#pvEssentials, #gallery", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".pv-title", on = Platform.WEB)
  private Text productName;

  @Locate(css = "select#grind + div", on = Platform.WEB)
  private Text grindSelected;

  @Locate(css = "select#grind option", on = Platform.WEB)
  private List<Text> grindListSelected;

  @Locate(
      css = "button[id*='productViewQuantityButton'].is-selected,#quantityText",
      on = Platform.WEB)
  private Text productQuantity;

  @Locate(id = "quantityText", on = Platform.WEB)
  private Text productQuantityBox;

  @Locate(id = "btnAddToBag", on = Platform.WEB)
  private Button addToCartButton;

  @Locate(id = "productViewQuantityButton3", on = Platform.WEB)
  protected Button threeProductsButton;

  @Locate(id = "productViewQuantityButton2", on = Platform.WEB)
  private Button twoProductsButton;

  @Locate(css = "button.plus", on = Platform.WEB)
  private Button addOneMore;

  @Locate(css = "[test='regularEligible'] button.og-optin-btn", on = Platform.WEB)
  private Button subscribeType;

  @Locate(css = "button.og-optout-btn", on = Platform.WEB)
  private Button oneTimePurchase;

  @Locate(css = "form#notifyForm", on = Platform.WEB)
  private ContainerElement outOfStockNotifyMeSection;

  @Locate(css = "#pvEssentials #productPrice .pv-price__original", on = Platform.WEB)
  private Text itemPrice;

  @Locate(css = "#photos img", on = Platform.WEB)
  private Image itemImage;

  @Locate(css = "select#amount", on = Platform.WEB)
  private SelectList amountSelectList;

  @Locate(id = "productRecommendations", on = Platform.WEB)
  private Button recommendedForYou;

  @Locate(xpath = "//select[@id='grind']", on = Platform.WEB)
  private SelectList grindDropdown;

  @Locate(id = "increment", on = Platform.WEB)
  protected Button incrementQuantityButton;

  @Locate(css = ".btn-icon.js-qty-change.plus", on = Platform.WEB)
  protected Button incrementQuantityButtonFromBag;

  @Locate(id = "decrement", on = Platform.WEB)
  private Button decrementQuantityButton;

  @Locate(css = ".btn-icon.js-qty-change.minus", on = Platform.WEB)
  protected Button decrementQuantityButtonFromBag;

  @Locate(
      xpath =
          "//button[@class='og-optin-btn']//*[contains(@class, 'og-pdp-tooltip')]//*[contains(@class, 'og-tooltip-inner')]",
      on = Platform.WEB)
  protected Button subscribeInfoIcon;

  @Locate(
      xpath =
          "//button[@class='og-optin-btn']//*[contains(@class, 'og-pdp-tooltip-desktop')]//p[@class='og-tooltip-content']",
      on = Platform.WEB)
  protected Text subscribeInfoTooltip;

  @Locate(xpath = "//p[@class='og-shipping']/*", on = Platform.WEB)
  private SelectList shipEveryDropdown;

  @Locate(xpath = "//button[@id='ratings-summary']", on = Platform.WEB)
  protected Button ratingsSummaryButton;

  @Locate(
      xpath = "//button[@id='first-to-write' or contains(@class, 'bv-write-review')]",
      on = Platform.WEB)
  protected Button writeReviewButton;

  @Locate(xpath = "//a[contains(@class, 'pv-flavor-profile')]", on = Platform.WEB)
  @Locate(
      xpath =
          "//a[contains(@class, 'pv-flavor-profile') and not(@hide-mobile) and not(@hide-phone)]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button takeTheCoffeeQuizButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(40)));
    logger.info("Product Details Page URL: " + SdkHelper.getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Get product name")
  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[PDP] Product Name: " + productName.getText().toLowerCase().trim());

    return productName.getText().toLowerCase().trim();
  }

  @Step("Get item is available")
  public boolean isItemAvailable() {
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return outOfStockNotifyMeSection.isDisplayed();
  }

  @Step("Get one time purchase link is available")
  public boolean isOneTimePurchaseLinkDisplayed() {
    logger.info("checking if one time purchase link is available");
    return oneTimePurchase.isDisplayed();
  }

  @Step("Get grind selected")
  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    String text = "";
    try {
      text = grindSelected.getText().toLowerCase().trim();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
    logger.info("[PDP] Grind Selected: " + text);

    if (WebHelper.isSafari()) {
      text =
          grindListSelected.stream()
              .filter(x -> x.getWebElement().isSelected())
              .findFirst()
              .get()
              .getText()
              .toLowerCase()
              .trim();

      logger.info("[PDP] Grind Selected: " + text);
      return text;
    }

    return text;
  }

  @Step("Get product price")
  public String getProductPrice() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemPrice).visible());
    logger.info("[PDP] Product price: " + itemPrice.getText().trim());

    return itemPrice.getText().trim();
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

  @Step("Get product quantity from box")
  public int getProductQuantityFromBox() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantityBox).visible());

    logger.info("[PDP] Product Quantity: " + productQuantityBox.getText());
    return Integer.parseInt(productQuantityBox.getText());
  }

  @Step("Select subscribe")
  public void selectSubscribeType() {
    logger.info("Selecting Subscribe Order");
    SdkHelper.getSyncHelper().wait(Until.uiElement(subscribeType).visible());
    subscribeType.click();
  }

  @Step("Select single purchase only")
  public void selectOneTimePurchase() {
    logger.info("Selecting one-time purchase");
    SdkHelper.getSyncHelper().wait(Until.uiElement(oneTimePurchase).visible());
    oneTimePurchase.click();
  }

  @Step("Click Add to minicart")
  public MiniCart clickAddToMiniCart() {
    logger.info("Adding to MiniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(MiniCart.class);
  }

  @Step("Click Add to cart")
  public CartPage clickAddToCartPage() {
    logger.info("Adding to Cart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(CartPage.class);
  }

  @Step("Click Add more products")
  public void addMoreProducts(int totalProducts) {
    logger.info("[PDP] Total products: " + totalProducts);

    if (totalProducts > 3) {
      logger.info("Adding more than 3 products, clicking 3+ button");
      clickOnThreeProductsButton();

      SdkHelper.getSyncHelper().wait(Until.uiElement(addOneMore).present());
      if (!WebHelper.isDesktop()) {
        WebHelper.scrollToElement(addOneMore);
        SdkHelper.getSyncHelper().sleep(1000); // Wait for action
      }

      for (int i = 3; i < totalProducts; i++) {
        addOneMore.click();
        SdkHelper.getSyncHelper().sleep(1000); // Wait for action
      }
    } else if (totalProducts == 2) {
      if (twoProductsButton.exists()) {
        logger.info("Selecting 2 products button");
        SdkHelper.getSyncHelper().wait(Until.uiElement(twoProductsButton).clickable());
        twoProductsButton.click();
      } else {
        SdkHelper.getSyncHelper().wait(Until.uiElement(addOneMore).present());
        if (!WebHelper.isDesktop()) {
          WebHelper.scrollToElement(addOneMore);
          SdkHelper.getSyncHelper().sleep(1000); // Wait for action
        }

        logger.info("Adding 1 more product");
        addOneMore.click();
      }
    } else if (totalProducts == 3) {
      logger.info("Selecting 3 products button");
      SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
      WebHelper.jsClick(threeProductsButton.getWebElement());
    }
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  @Step("Verify expected image is displayed")
  public boolean isExpectedImageDisplayed(String imageSrc) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemImage).visible());
    String imageSrcset = itemImage.getAttributeValue(Attribute.SRCSET.getValue());
    if (!imageSrcset.contains(imageSrc)) {
      logger.info(
          "Wrong image is displayed. Expected src - [{}]. Actual src - [{}]",
          imageSrc,
          imageSrcset);
      return false;
    }

    return true;
  }

  @Step("Select amount")
  public ProductDetailsPage selectAmount(String amount) {
    logger.info("Selecting amount [{}]", amount);
    amountSelectList.select(amount);
    return this;
  }

  @Step("Check if Grind is Displayed")
  public boolean isGrindDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(grindSelected);
    logger.info("Grind is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Product Quantity is the Bag Picker Type")
  public boolean isItemBagQuantityType() {
    boolean isDisplayed = WebHelper.isDisplayed(threeProductsButton);
    logger.info("Bag Quantity Picker is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Product Quantity is Displayed")
  public boolean isProductQuantityDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(productQuantity);
    logger.info("Product Quantity is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if Subscribe Type is Displayed")
  public boolean isSubscribeTypeDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(subscribeType);
    logger.info("Subscribe Type is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'Add to Cart' is Displayed")
  public boolean isAddToCartButtonDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(addToCartButton);
    logger.info("Add to Cart is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Check if 'Recommended For You' is Displayed")
  public boolean isRecommendedForYouSectionDisplayed() {
    boolean isDisplayed = WebHelper.isDisplayed(recommendedForYou);
    logger.info("Recommended For You section is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Select Grind")
  public ProductDetailsPage selectGrind(GrindDropdown option) {
    logger.info("Selecting [{}] grind option", option.getValue());
    grindSelected.click();
    boolean valueFound = false;
    Iterator var3 = grindDropdown.getChildren(LocatedBy.xpath("//li")).iterator();

    while (var3.hasNext()) {
      ContainerElement optionElement = (ContainerElement) var3.next();
      if (option
          .getValue()
          .equalsIgnoreCase(optionElement.getAttributeValue(Attribute.ID.getValue()).trim())) {
        WebHelper.jsClick(optionElement.getWebElement());
        valueFound = true;
        break;
      }
    }

    if (!valueFound) {
      throw new IllegalStateException("Couldn't find option with name " + option.getValue());
    }
    return this;
  }

  @Step("Click on 3+ Quantity bag button")
  public ProductDetailsPage selectThreePlusQuantity() {
    logger.info("Clicking on the 3+ Quantity Bag");
    clickOnThreeProductsButton();
    return this;
  }

  @Step("Click on (+) in Quantity")
  public ProductDetailsPage incrementProductQuantity(int quantity) {
    IntStream.rangeClosed(1, quantity)
        .forEach(
            item -> {
              logger.info("Clicking on (+) in Quantity");
              incrementQuantityButton.click();
              logger.info("Current quantity is - [{}]", getProductQuantitySelected());
            });
    return this;
  }

  @Step("Click on (+) in Quantity Picker after Bag Click")
  public ProductDetailsPage incrementProductQuantityFromBag(int quantity) {
    IntStream.rangeClosed(1, quantity)
        .forEach(
            item -> {
              logger.info("Clicking on (+) in Quantity");
              incrementQuantityButtonFromBag.click();
              logger.info("Current quantity is - [{}]", getProductQuantityFromBox());
            });
    return this;
  }

  @Step("Click on (-) in Quantity")
  public ProductDetailsPage decrementProductQuantity(int quantity) {
    IntStream.rangeClosed(1, quantity)
        .forEach(
            item -> {
              logger.info("Clicking on (-) in Quantity");
              decrementQuantityButton.click();
              logger.info("Current quantity is - [{}]", getProductQuantitySelected());
            });
    return this;
  }

  @Step("Click on (-) in Quantity Picker after Bag Click")
  public ProductDetailsPage decrementProductQuantityFromBag(int quantity) {
    IntStream.rangeClosed(1, quantity)
        .forEach(
            item -> {
              logger.info("Clicking on (-) in Quantity");
              decrementQuantityButtonFromBag.click();
              logger.info("Current quantity is - [{}]", getProductQuantityFromBox());
            });
    return this;
  }

  @Step("Hover subscribe info icon")
  public String hoverOverSubscribeInfoIcon() {
    WebHelper.hoverByAction(subscribeInfoIcon);
    String tooltip = WebHelper.cleanString(subscribeInfoTooltip.getText());
    logger.info("Tooltip text - [{}]", tooltip);
    return tooltip;
  }

  @Step("Select Ship Every")
  public ProductDetailsPage selectShipEvery(ShipEveryDropdown option) {
    logger.info("Selecting Ship Every [{}]", option.getValue());

    Select dropdown = new Select(getShipEveryDropdown());
    dropdown.selectByVisibleText(option.getValue());
    return this;
  }

  @Step("Get Selected Ship Every")
  public String getSelectedShipEvery() {
    Select dropdown = new Select(getShipEveryDropdown());
    String selected = WebHelper.cleanString(dropdown.getFirstSelectedOption().getText());
    logger.info("Selected Ship Every - [{}]", selected);
    return selected;
  }

  @Step("Click on 'Stars' to add review")
  public ProductDetailsPage clickOnStars() {
    logger.info("Clicking on 'Stars'");
    ratingsSummaryButton.click();
    return this;
  }

  @Step("Click on 'Write Review' button")
  public MyReviewModalComponent clickWriteReview() {
    logger.info("Clicking on 'Write Review'");
    writeReviewButton.click();
    return SdkHelper.create(MyReviewModalComponent.class);
  }

  @Step("Scroll down and click on take the coffee quiz")
  public CoffeeFinderPage clickOnTakeTheCoffeeQuiz() {
    logger.info("Clicking on 'Take the coffee quiz'");
    takeTheCoffeeQuizButton.click();
    return SdkHelper.create(CoffeeFinderPage.class);
  }

  protected void clickOnThreeProductsButton() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
    threeProductsButton.click();
  }

  private WebElement getShipEveryDropdown() {
    return WebHelper.findShadowElementsBy(
            shipEveryDropdown.getWebElement(), By.cssSelector("og-select"))
        .stream()
        .map(i -> WebHelper.findShadowElementsBy(i, By.cssSelector("select")).get(0))
        .findFirst()
        .get();
  }
}

class ProductDetailsPageMobile extends ProductDetailsPage {

  @Override
  protected void clickOnThreeProductsButton() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(threeProductsButton).clickable());
    WebHelper.clickOnElementAndScrollUpIfNeeded(threeProductsButton, -90);
  }

  @Override
  @Step("Click on (+) in Quantity")
  public ProductDetailsPage incrementProductQuantity(int quantity) {
    IntStream.rangeClosed(1, quantity)
        .forEach(
            item -> {
              logger.info("Clicking on (+) in Quantity");
              WebHelper.clickOnElementAndScrollUpIfNeeded(incrementQuantityButton, -90);
              logger.info("Current quantity is - [{}]", getProductQuantitySelected());
            });
    return this;
  }

  @Override
  @Step("Hover subscribe info icon")
  public String hoverOverSubscribeInfoIcon() {
    subscribeInfoIcon.click();
    String tooltip = WebHelper.cleanString(subscribeInfoTooltip.getText());
    logger.info("Tooltip text - [{}]", tooltip);
    return tooltip;
  }

  @Override
  @Step("Click on 'Stars' to add review")
  public ProductDetailsPage clickOnStars() {
    logger.info("Clicking on 'Stars'");
    WebHelper.clickOnElementAndScrollUpIfNeeded(ratingsSummaryButton, -90);
    return this;
  }
}
