package com.applause.auto.new_web.views;

import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.common.data.enums.ShipEveryDropdown;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.components.MiniCart;
import com.applause.auto.new_web.components.MyReviewModalComponent;
import com.applause.auto.new_web.components.ProductStoryModalComponent;
import com.applause.auto.new_web.components.pdp.PdpStickyNavDetailsComponent;
import com.applause.auto.new_web.components.pdp.ProductDetailsCustomerReviewsComponent;
import com.applause.auto.new_web.components.pdp.ProductDetailsViewImageComponent;
import com.applause.auto.new_web.components.plp.PlpItemComponent;
import com.applause.auto.new_web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.new_web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.LocatedBy;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

@Implementation(is = ProductDetailsPage.class, on = Platform.WEB)
@Implementation(is = ProductDetailsPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class ProductDetailsPage extends Base {

  @Locate(css = "#pvEssentials, #gallery", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(css = ".pv-title", on = Platform.WEB)
  private Text productName;

  @Locate(css = "section.pv-details", on = Platform.WEB)
  private Text productDescription;

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

  @Locate(css = "[test='regularEligible'] button.og-optin-btn span[style]", on = Platform.WEB)
  private Button subscribeTypePromoText;

  @Locate(css = "og-when[test='regularEligible'] p.og-shipping option", on = Platform.WEB)
  private LazyList<Text> subscriptionWeeks;

  @Locate(css = "button.og-optout-btn", on = Platform.WEB)
  private Button oneTimePurchase;

  @Locate(css = "form#notifyForm", on = Platform.WEB)
  private ContainerElement outOfStockNotifyMeSection;

  @Locate(css = "#pvEssentials #productPrice .pv-price__original", on = Platform.WEB)
  private Text itemPrice;

  @Locate(css = "h1.pv-title", on = Platform.WEB)
  private Text itemTitle;

  @Locate(css = "#photos img", on = Platform.WEB)
  private Image itemImage;

  @Locate(css = "select#amount", on = Platform.WEB)
  private SelectList amountSelectList;

  @Locate(id = "productRecommendations", on = Platform.WEB)
  private Button recommendedForYou;

  @Locate(xpath = "//div[@id='productRecommendationsWrapper']//article", on = Platform.WEB)
  @Locate(
      xpath = "//div[@id='productRecommendationsWrapper']//article[contains(@class,'selected')]",
      on = Platform.WEB_MOBILE)
  private List<PlpItemComponent> recommendedForYouItemList;

  @Locate(css = "#productRecommendations a.pv-flavor-profile__quiz-cta", on = Platform.WEB)
  @Locate(
      css = "#productRecommendations .phone-only a.pv-flavor-profile__quiz-cta",
      on = Platform.WEB_MOBILE)
  protected Link recommendedForYouQuizLink;

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

  @Locate(className = "bv_numReviews_text", on = Platform.WEB)
  protected Text ratingsReviewsNumber;

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

  @Locate(css = "#sr_productDetailDiv strong", on = Platform.WEB)
  protected Text freeDayShippingText;

  @Locate(xpath = "//div[@id='sr_productDetailDiv']//a[text()='learn more']", on = Platform.WEB)
  protected Link learnMoreLink;

  @Locate(xpath = "//div[@id='sr_productDetailDiv']//a[text()='sign in']", on = Platform.WEB)
  protected Link signInLink;

  @Locate(
      xpath =
          "//section[@class='pv-description']//span[@class='pv-description-icon__title' and text()='%s']",
      on = Platform.WEB)
  protected Text carouselDescriptionTitleText;

  @Locate(
      xpath =
          "//section[@class='pv-description']//span[@class='pv-description-icon__title' and text()='%s']/../span[2]",
      on = Platform.WEB)
  protected Text carouselDescriptionDescriptionText;

  @Locate(css = "section.pv-description", on = Platform.WEB)
  protected ContainerElement carouselDescriptionElement;

  @Locate(
      css = ".pv-description button[class*='flickity-prev-next-button'][aria-label='Next']",
      on = Platform.WEB)
  protected Button carouselDescriptionNextButton;

  @Locate(css = ".pv-flavor-profile__subtitle", on = Platform.WEB)
  protected Text flavorProfileSubtitle;

  @Locate(
      xpath = "//p[contains(text(),'%s')]/following-sibling::div[@class='progress-bar'][1]",
      on = Platform.WEB)
  protected ContainerElement flavorProfileRatingElement;

  @Locate(
      xpath =
          "//p[contains(text(),'%s')]/following-sibling::div[@class='progress-bar'][1]//p[text()='%s']",
      on = Platform.WEB)
  protected Text flavorProfileRatingLabelElement;

  @Locate(
      xpath = "//p[@class='pv-flavor-profile__attribute' and span[contains(text(),'%s')]]",
      on = Platform.WEB)
  protected ContainerElement flavorProfileAttributeElement;

  @Locate(css = "h3.pv-flavor-profile__quiz-title", on = Platform.WEB)
  protected Text flavorProfileQuizTitle;

  @Locate(css = ".pv-flavor-profile__quiz img", on = Platform.WEB)
  protected Text flavorProfileQuizImage;

  @Locate(css = ".pv-flavor-profile a.pv-flavor-profile__quiz-cta", on = Platform.WEB)
  protected Link flavorProfileQuizLink;

  @Locate(css = ".pv-story img", on = Platform.WEB)
  protected Image storyBannerImage;

  @Locate(css = "h2.pv-story__title", on = Platform.WEB)
  protected Text storyBannerTitle;

  @Locate(css = "p.pv-story__description", on = Platform.WEB)
  protected Text storyBannerDescription;

  @Locate(id = "productStoryModalTrigger", on = Platform.WEB)
  protected Text storyBannerReadMoreLink;

  @Locate(css = "button.pv-brew__button", on = Platform.WEB)
  protected List<Button> brewingMethods;

  @Locate(css = ".pv-brew__partitions.is-open .pv-brew__desc", on = Platform.WEB)
  protected Text activeBrewingMethodDescription;

  @Locate(css = ".pv-brew__partitions.is-open .pv-brew__summary-copy", on = Platform.WEB)
  protected Text activeBrewingMethodTime;

  @Locate(css = ".pv-brew__partitions.is-open .pv-brew__video-btn", on = Platform.WEB)
  @Locate(
      css = ".pv-brew__partitions.is-open .mobile-only .pv-brew__video-btn",
      on = Platform.WEB_MOBILE)
  protected Button activeBrewingMethodVideoPlayButton;

  @Locate(
      css = ".pv-brew__partitions.is-open .hide-mobile button[title='Pause the playback']",
      on = Platform.WEB)
  @Locate(
      css = ".pv-brew__partitions.is-open .mobile-only button[title='Pause the playback']",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button activeBrewingMethodVideoPauseButton;

  @Locate(css = ".pv-brew__partitions.is-open .hide-mobile video.pv-brew__video", on = Platform.WEB)
  @Locate(
      css = ".pv-brew__partitions.is-open .mobile-only video.pv-brew__video",
      on = Platform.WEB_MOBILE_PHONE)
  protected ContainerElement activeBrewingMethodVideo;

  @Locate(css = ".pv-brew__partitions.is-open a.pv-brew__link", on = Platform.WEB)
  protected Link activeBrewingMethodShopLink;

  @Locate(xpath = "//a[@class='pv-breadcrumbs__link' and text()='%s']", on = Platform.WEB)
  protected Link breadcrumbLink;

  @Locate(css = ".footer__newsletter a", on = Platform.WEB)
  protected Link neverMissSignUpButton;

  @Locate(css = "div#productFourSixty div[id*= post] > div", on = Platform.WEB)
  protected List<ContainerElement> peetsCoffeeImages;

  @Locate(css = "div.config__group--size", on = Platform.WEB)
  protected ContainerElement sizeDropdown;

  @Locate(id = "productViewQuantityButton%s", on = Platform.WEB)
  private ContainerElement quantityBox;

  @Locate(css = "#productViewQuantityButton%s.is-selected", on = Platform.WEB)
  private ContainerElement quantityBoxSelected;

  @Locate(
      css = "#productViewQuantityButton%s span.pv-qty__button-text--discount",
      on = Platform.WEB)
  private Text quantityBoxText;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(40)));
    logger.info("Product Details Page URL: " + WebHelper.getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Get product name")
  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[PDP] Product Name: " + productName.getText().toLowerCase().trim());

    return productName.getText().toLowerCase().trim();
  }

  public boolean isProductNameDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    return productName.isDisplayed();
  }

  public boolean isProductDescDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productDescription).visible());
    return productDescription.isDisplayed();
  }

  public boolean isProductPriceDisplayed() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemPrice).visible());
    return itemPrice.isDisplayed();
  }

  @Step("Get item is available")
  public boolean isItemAvailable() {
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return outOfStockNotifyMeSection.isDisplayed();
  }

  @Step("Get item is sampler")
  public boolean itemIsSampler() {
    logger.info(" -- checking if item is a sampler item");
    return itemTitle.getText().toLowerCase().trim().contains("sampler");
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

  public boolean isSubscribeTypeAsDefault() {
    logger.info("Review if type, is default value");
    return subscribeType.getAttributeValue("slot").equalsIgnoreCase("default");
  }

  public boolean isSubscribeToolTipDisplayed() {
    logger.info("Review if subscribe tooltip is displayed");
    return subscribeInfoIcon.isDisplayed();
  }

  public String getSubscribePromoText() {
    String text = subscribeTypePromoText.getText().toLowerCase().trim();
    logger.info("Subscribe promo text: " + text);
    return text;
  }

  public int subscriptionOptionsAvailable() {
    logger.info("Subscription options");
    return subscriptionWeeks.size();
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

    // Todo:Try/Catch added to prevent chromedriver issue[Temp fix]
    try {
      addToCartButton.click();
    } catch (WebDriverException e) {
      logger.info("Frame detached issue seen");
    }
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
    IntStream.rangeClosed(1, quantity).forEach(item -> clickQuantityPlusButton());
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
    IntStream.rangeClosed(1, quantity).forEach(item -> clickQuantityMinusButton());
    return this;
  }

  @Step("Click on (-) in Quantity")
  public ProductDetailsPage clickQuantityMinusButton() {
    logger.info("Clicking on (-) in Quantity");
    decrementQuantityButton.click();
    logger.info("Current quantity is - [{}]", getProductQuantitySelected());
    return this;
  }

  @Step("Click on (+) in Quantity")
  public ProductDetailsPage clickQuantityPlusButton() {
    logger.info("Clicking on (+) in Quantity");
    WebHelper.scrollToElement(incrementQuantityButton);
    incrementQuantityButton.click();
    logger.info("Current quantity is - [{}]", getProductQuantitySelected());
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

  public boolean isRatingStartsAndReviewsDisplayed() {
    logger.info("Validate rating stars and user reviews are displayed");
    return ratingsSummaryButton.isDisplayed() && ratingsReviewsNumber.isDisplayed();
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

  @Step("Verify 'FREE 2-Day Shipping & Free Returns' text is displayed")
  public boolean isFreeDayShippingTextDisplayed() {
    logger.info("Checking 'FREE 2-Day Shipping & Free Returns' text is displayed");
    return WebHelper.isDisplayed(freeDayShippingText);
  }

  @Step("Verify 'Learn More' link is displayed")
  public boolean isLearnMoreLinkDisplayed() {
    logger.info("Checking 'Learn More' link is displayed");
    return WebHelper.isDisplayed(learnMoreLink);
  }

  @Step("Verify 'Sign In' link is displayed")
  public boolean isSignInLinkDisplayed() {
    logger.info("Checking 'Sign In' link is displayed");
    return WebHelper.isDisplayed(signInLink);
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

  @Step("Verify carousel description title is displayed")
  public boolean isCarouselDescriptionTitleDisplayed(String titleText) {
    logger.info("Checking title: '{}' is displayed", titleText);
    WebHelper.scrollToPageBottom();
    WebHelper.waitForElementToAppear(carouselDescriptionElement, 15);
    WebHelper.scrollToElement(carouselDescriptionElement);
    carouselDescriptionDescriptionText.format(titleText).initialize();
    return WebHelper.isDisplayed(carouselDescriptionDescriptionText);
  }

  @Step("Get carousel description description by title")
  public String getCarouselDescription(String titleText) {
    logger.info("Getting description by title: '{}' is displayed", titleText);
    carouselDescriptionDescriptionText.format(titleText).initialize();
    return carouselDescriptionDescriptionText.getText().trim();
  }

  @Step("Get flavor profile subtitle")
  public String getFlavorProfileSubtitle() {
    String subtitle = flavorProfileSubtitle.getText().trim();
    logger.info("Flavor profile subtitle: {}", subtitle);
    return subtitle;
  }

  @Step("Verify Roast details rating is displayed properly")
  public boolean isFlavorProfileRoastDetailsRatingDisplayedProperly(
      String roastDetail, String lowerLabel, String higherLabel) {
    logger.info("Checking flavor profile roast details: {}", roastDetail);
    flavorProfileRatingElement.format(roastDetail).initialize();
    if (!WebHelper.isDisplayed(flavorProfileRatingElement)) {
      logger.error("Flavor profile roast detail rating '{}' isn't displayed", roastDetail);
      return false;
    }

    flavorProfileRatingLabelElement.format(roastDetail, lowerLabel).initialize();
    if (!WebHelper.isDisplayed(flavorProfileRatingLabelElement)) {
      logger.error("Flavor profile roast detail lower label '{}' isn't displayed", lowerLabel);
      return false;
    }

    flavorProfileRatingLabelElement.format(roastDetail, higherLabel).initialize();
    if (!WebHelper.isDisplayed(flavorProfileRatingLabelElement)) {
      logger.error("Flavor profile roast detail higher label '{}' isn't displayed", higherLabel);
      return false;
    }

    return true;
  }

  @Step("Verify Roast attribute is displayed")
  public boolean isFlavorProfileAttributeDisplayed(String attributeName) {
    logger.info("Checking flavor profile attribute: '{}' is displayed", attributeName);
    flavorProfileAttributeElement.format(attributeName).initialize();
    return WebHelper.isDisplayed(flavorProfileAttributeElement);
  }

  @Step("Verify flavor profile quiz title is displayed")
  public boolean isFlavorProfileQuizTitleDisplayed() {
    logger.info("Checking flavor profile quiz title is displayed");
    return WebHelper.isDisplayed(flavorProfileQuizTitle);
  }

  @Step("Verify flavor profile quiz image is displayed")
  public boolean isFlavorProfileQuizImageDisplayed() {
    logger.info("Checking flavor profile quiz image is displayed");
    return WebHelper.isDisplayed(flavorProfileQuizImage);
  }

  @Step("Click on the flavor profile quiz link")
  public CoffeeFinderPage clickFlavorProfileQuizLink() {
    logger.info("Clicking on the flavor profile quiz link");
    flavorProfileQuizLink.click();
    return SdkHelper.create(CoffeeFinderPage.class);
  }

  @Step("Verify Story section banner image is displayed")
  public boolean isStoryBannerDisplayed() {
    logger.info("Checking Story section banner image is displayed");
    WebHelper.scrollToElement(storyBannerImage);
    return WebHelper.isDisplayed(storyBannerImage);
  }

  @Step("Verify Story section banner title is displayed")
  public boolean isStoryTitleDisplayed() {
    logger.info("Checking Story section banner title is displayed");
    return WebHelper.isDisplayed(storyBannerTitle);
  }

  @Step("Verify Story section banner description is displayed")
  public boolean isStoryDescriptionDisplayed() {
    logger.info("Checking Story section banner description is displayed");
    return WebHelper.isDisplayed(storyBannerDescription);
  }

  @Step("Click on the Story Section 'Read more link'")
  public ProductStoryModalComponent clickStoryReadMoreLink() {
    logger.info("Clicking on the Story Section 'Read more link'");
    storyBannerReadMoreLink.click();
    return SdkHelper.create(ProductStoryModalComponent.class);
  }

  @Step("Get active brewing method")
  public String getActiveBrewingMethod() {
    return brewingMethods.stream()
        .filter(method -> method.getAttributeValue(Attribute.CLASS.getValue()).contains("active"))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("There are no active brewing methods"))
        .getText()
        .trim();
  }

  @Step("Get brewing method")
  public String getBrewingMethod(int index) {
    String brewingMethod = brewingMethods.get(index - 1).getText().trim();
    logger.info("Brewing method: '{}' by index: '{}'", brewingMethod, index);
    return brewingMethod;
  }

  @Step("Get active brewing method description")
  public String getActiveBrewingMethodDescription() {
    String brewingMethodDescription = activeBrewingMethodDescription.getText().trim();
    logger.info("Active brewing method description: '{}'", brewingMethodDescription);
    return brewingMethodDescription;
  }

  @Step("Get active brewing method time")
  public String getActiveBrewingMethodTime() {
    String brewingMethodTime = activeBrewingMethodTime.getText().trim();
    logger.info("Active brewing method time: '{}'", brewingMethodTime);
    return brewingMethodTime;
  }

  @Step("Verify the user is shown with all different brewing methods")
  public boolean areAllBrewingMethodsDifferent() {
    logger.info("Checking the user is shown with all different brewing methods");
    List<String> brewingMethodNames =
        brewingMethods.stream().map(Button::getText).collect(Collectors.toList());
    return brewingMethodNames.stream().anyMatch(brewingMethodNames.get(0)::equals);
  }

  @Step("Select Brewing methods by index")
  public ProductDetailsPage selectBrewingMethod(int index) {
    logger.info("Selecting Brewing methods by index: {}", index);
    WebHelper.scrollToElement(brewingMethods.get(index - 1));
    brewingMethods.get(index - 1).click();
    return this;
  }

  @Step("Click on Play button on Banner")
  public ProductDetailsPage clickBrewingMethodPlayVideoButton() {
    logger.info("Clicking on Play button on Banner");
    WebHelper.scrollToElement(activeBrewingMethodVideoPlayButton);
    activeBrewingMethodVideoPlayButton.click();
    return this;
  }

  @Step("Click on Pause button on Banner")
  public ProductDetailsPage clickBrewingMethodPauseVideoButton() {
    logger.info("Clicking on Pause button on Banner");
    activeBrewingMethodVideoPauseButton.click();
    return this;
  }

  @Step("Verify Brewing Method video is played")
  public boolean isBrewingMethodVideoPlayed() {
    logger.info("Checking Brewing Method video is played");
    return activeBrewingMethodVideo
        .getAttributeValue(Attribute.CLASS.getValue())
        .contains("is-playing");
  }

  @Step("Verify Brewing Method video pause button is displayed")
  public boolean isBrewingMethodPauseVideoButtonDisplayed() {
    logger.info("Checking Brewing Method video pause button is displayed");
    return WebHelper.isDisplayed(activeBrewingMethodVideoPauseButton);
  }

  @Step("Click on the Brewing Method shop link")
  public ProductDetailsPage clickBrewingMethodShopLink() {
    logger.info("Clicking on the Brewing Method shop link");
    activeBrewingMethodShopLink.click();
    return SdkHelper.create(ProductDetailsPage.class);
  }

  @Step("Verify breadCrumb is displayed")
  public boolean isBreadCrumbDisplayed(String name) {
    breadcrumbLink.format(name).initialize();
    logger.info("Checking breadcrumbs is displayed");
    return WebHelper.isDisplayed(breadcrumbLink);
  }

  public ProductListPage clickBreadCrumb(String name) {
    breadcrumbLink.format(name).initialize();
    logger.info("Clicking over breadcrumb link: [{}]", name);
    breadcrumbLink.click();

    return SdkHelper.create(ProductListPage.class);
  }

  public SoftAssert validateQuantityElements() {
    SoftAssert softAssert = new SoftAssert();

    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity).visible());
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
    quantityBox.click();
    softAssert.assertFalse(quantityBoxSelected.isDisplayed(), "Quantity 2 should not be displayed");

    return softAssert;
  }

  public ProductDetailsCustomerReviewsComponent getCustomerReviewsComponent() {
    return SdkHelper.create(ProductDetailsCustomerReviewsComponent.class);
  }

  public List<PlpItemComponent> getRecommendedForYouItemList() {
    return recommendedForYouItemList;
  }

  @Step("Get Number of displayed items")
  public int getDisplayedRecommendedForYouItemNumber() {
    return recommendedForYouItemList.size();
  }

  @Step("Click on the 'RECOMMENDED FOR YOU' quiz link")
  public CoffeeFinderPage clickRecommendedForYouQuizLink() {
    logger.info("Clicking on the 'RECOMMENDED FOR YOU' quiz link");
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(recommendedForYouQuizLink);
    recommendedForYouQuizLink.click();
    return SdkHelper.create(CoffeeFinderPage.class);
  }

  @Step("Verify Never miss offer 'Sign up' button is displayed")
  public boolean isNeverMissOfferSignUpButtonDisplayed() {
    WebHelper.scrollToPageBottom();
    return WebHelper.isDisplayed(neverMissSignUpButton);
  }

  @Step("Click on the Sign up button")
  public SignUpPage clickNeverMissOfferSignUpButton() {
    logger.info("Clicking on the Sign up button");
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(neverMissSignUpButton);
    neverMissSignUpButton.click();
    return SdkHelper.create(SignUpPage.class);
  }

  @Step("Verify Peets Coffee on instagram images are displayed")
  public boolean arePeetscoffeeOnInstagramImagesDisplayed() {
    WebHelper.scrollToPageBottom();
    return !peetsCoffeeImages.isEmpty();
  }

  @Step("Click on the Peets Сoffee on instagram image")
  public ProductDetailsViewImageComponent clickOnPeetscoffeeOnInstagramImage() {
    logger.info("Clicking on the Peets Сoffee on instagram image");
    WebHelper.scrollToElement(peetsCoffeeImages.get(1));
    WebHelper.jsClick(peetsCoffeeImages.get(1).getWebElement());
    return SdkHelper.create(ProductDetailsViewImageComponent.class);
  }

  @Step("Click on the carousel description next button")
  public void clickCarouselDescriptionNextButton() {
    logger.info("Clicking on the carousel description next button");
    WebHelper.jsClick(carouselDescriptionNextButton.getWebElement());
  }

  @Step("Scroll to Flavor profile section")
  public ProductDetailsPage scrollToFlavorProfile() {
    logger.info("Scrolling to Flavor profile section");
    WebHelper.scrollToElement(flavorProfileSubtitle);
    WebHelper.scrollToPageBottom();
    return this;
  }

  public PdpStickyNavDetailsComponent getPdpStickyDetailsComponent() {
    return SdkHelper.create(PdpStickyNavDetailsComponent.class);
  }

  @Step("Get background color for the top section")
  public String getBackgroundColorForTopSection() {
    String color = mainContainer.getWebElement().getCssValue(Attribute.BACKGROUND_COLOR.getValue());
    logger.info("Background color: {}", color);
    return color;
  }

  @Step("Verify Size dropdown isn't displayed")
  public boolean isSizeDropdownDisplayed() {
    return WebHelper.isDisplayed(sizeDropdown, 10);
  }

  @Step("Verify Quantity increment button is displayed")
  public boolean isIncrementQuantityButtonDisplayed() {
    return WebHelper.isDisplayed(incrementQuantityButton);
  }

  @Step("Verify Quantity decrement button is displayed")
  public boolean isDecrementQuantityButtonDisplayed() {
    return WebHelper.isDisplayed(decrementQuantityButton);
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

  @Locate(css = "#productRecommendations span.pv-description-icon__count-total", on = Platform.WEB)
  private Text recommendedForYouItemsNumber;

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
    WebHelper.scrollToPageBottom();
    WebHelper.clickOnElementAndScrollUpIfNeeded(ratingsSummaryButton, -90);
    return this;
  }

  @Override
  @Step("Get Number of displayed items")
  public int getDisplayedRecommendedForYouItemNumber() {
    return Integer.parseInt(recommendedForYouItemsNumber.getText().trim());
  }
}
