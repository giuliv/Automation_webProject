package com.applause.auto.web.views;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.enums.Attribute;
import com.applause.auto.common.data.enums.GiftDuration;
import com.applause.auto.common.data.enums.GrindDropdown;
import com.applause.auto.common.data.enums.ShipEveryDropdown;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.MiniCart;
import com.applause.auto.web.components.MyReviewModalComponent;
import com.applause.auto.web.components.NotifyMeComponent;
import com.applause.auto.web.components.ProductStoryModalComponent;
import com.applause.auto.web.components.pdp.PdpStickyNavDetailsComponent;
import com.applause.auto.web.components.pdp.ProductDetailsCustomerReviewsComponent;
import com.applause.auto.web.components.pdp.ProductDetailsViewImageComponent;
import com.applause.auto.web.components.plp.PlpItemComponent;
import com.applause.auto.web.components.plp.PlpLearnMoreOverlappingComponent;
import com.applause.auto.web.components.plp.PlpSignInOverlappingComponent;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

  @Locate(css = "button#btnAddToBag", on = Platform.WEB)
  protected Button addToCartButton;

  @Locate(xpath = "//button[@id='btnAddToBag' and span[text()='Buy Now']]", on = Platform.WEB)
  protected Button buyNowButton;

  @Locate(id = "btnAddToBagText", on = Platform.WEB)
  private Button addToCartButtonText;

  @Locate(css = "button#productViewQuantityButton3", on = Platform.WEB)
  protected Button threeProductsButton;

  @Locate(css = "button#productViewQuantityButton2", on = Platform.WEB)
  private Button twoProductsButton;

  @Locate(css = "button.plus", on = Platform.WEB)
  private Button addOneMore;

  @Locate(xpath = "//div[@class='og-optin-row']//button[@class='og-optin-btn']", on = Platform.WEB)
  protected Button subscribeType;

  @Locate(css = "[test='regularEligible'] button.og-optin-btn span[style]", on = Platform.WEB)
  private Button subscribeTypePromoText;

  @Locate(css = "og-when[test='regularEligible'] p.og-shipping option", on = Platform.WEB)
  private LazyList<Text> subscriptionWeeks;

  @Locate(css = "og-when[test*='regular'] div.og-frequency-row", on = Platform.WEB)
  private TextBox subscriptionWeekBox;

  @Locate(
      xpath = "//div[@class='og-default-row']//button[@class='og-optout-btn']",
      on = Platform.WEB)
  protected Button oneTimePurchase;

  @Locate(css = "h3.pv-notify__title", on = Platform.WEB)
  private ContainerElement outOfStockNotifyMeSection;

  @Locate(css = "form#notifyForm", on = Platform.WEB)
  private Text outOfStockMessage;

  @Locate(id = "notifySubmit", on = Platform.WEB)
  private Button notifyMeButton;

  @Locate(css = "input[name='notify_email']", on = Platform.WEB)
  private TextBox notifyMeBox;

  @Locate(css = "#pvEssentials #productPrice .pv-price__original", on = Platform.WEB)
  private Text itemPrice;

  @Locate(css = "#pvEssentials #productPrice .js-price-original", on = Platform.WEB)
  private Text startingAtItemPrice;

  @Locate(css = "h1.pv-title", on = Platform.WEB)
  private Text itemTitle;

  @Locate(css = "#photos img", on = Platform.WEB)
  private Image itemImage;

  @Locate(css = "select#amount", on = Platform.WEB)
  private SelectList amountSelectList;

  @Locate(id = "productRecommendations", on = Platform.WEB)
  private Button recommendedForYou;

  //ONBOARDING ------------------------------------------
  @Locate(css = "li.form-item footer__newsletter-item", on = Platform.WEB)
  private Button newsletterButton;

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

  @Locate(xpath = "//div[@data-option-name='Grind']//div//li", on = Platform.WEB)
  //  @Locate(css = "select#grind", on = Platform.WEB)
  private LazyList<ContainerElement> grindDropdown;

  @Locate(
      xpath =
          "//div[@class='form-item']//div[@aria-label='Select grind']/../ul/li[@data-value='%s']",
      on = Platform.WEB)
  private Button grindOptionButton;

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

  //  @Locate(xpath = "//p[@class='og-shipping']/*", on = Platform.WEB) //Commented for now
  @Locate(
      javaScript =
          "return document.querySelector(\"p.og-shipping og-select-frequency\").shadowRoot.querySelector(\"og-select\").shadowRoot.querySelector(\"select\")",
      on = Platform.WEB)
  private SelectList shipEveryDropdown;

  @Locate(css = ".pv-header__reviews", on = Platform.WEB)
  protected Button ratingsSummaryButton;

  @Locate(css = ".bv_numReviews_text", on = Platform.WEB)
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
          "//section[@class='pv-description']//span[@class='pv-description-icon__title' and contains(text(),'%s')]",
      on = Platform.WEB)
  protected Text carouselDescriptionTitleText;

  @Locate(
      xpath =
          "//section[@class='pv-description']//span[@class='pv-description-icon__title' and contains(text(),'%s')]/../span[2]",
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

  @Locate(css = "button[id*='productViewQuantityButton']", on = Platform.WEB)
  private List<Button> quantityBoxes;

  @Locate(css = "button.upsell-btn", on = Platform.WEB)
  protected Text addToSubscribeOrderButton;

  @Locate(css = "select#gift-duration", on = Platform.WEB)
  private SelectList giftDurationDropdown;

  @Locate(css = ".pv-actions .og-sub-info", on = Platform.WEB)
  private Text coffeeShipsOncePerMonthText;

  @Locate(css = ".pv-qty", on = Platform.WEB)
  private ContainerElement quantitySection;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).present().setTimeout(Duration.ofSeconds(40)));
    //    WebHelper.clickButtonOverIFrame(newBannerIFrame, dismissBanner);
    logger.info("Product Details Page URL: " + WebHelper.getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Get product name")
  public String getProductName() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productName).visible());
    logger.info("[PDP] Product Name: " + productName.getText().toLowerCase().trim());

    return productName.getText().toLowerCase().trim();
  }

  public String getAddToExistingSubscriptionButtonText() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToSubscribeOrderButton).visible());
    logger.info(
        "[PDP] Add to Existing Subscription: "
            + addToSubscribeOrderButton.getText().toLowerCase().trim());

    return addToSubscribeOrderButton.getText().toLowerCase().trim();
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

  public boolean isProductStartAtPriceDisplayed() {
    return SdkHelper.getSyncHelper()
        .wait(Until.uiElement(startingAtItemPrice).visible())
        .isDisplayed();
  }

  @Step("Get item is available")
  public boolean isItemAvailable() {
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return outOfStockNotifyMeSection.isDisplayed();
  }

  public String getOutOfStockMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(outOfStockNotifyMeSection).visible());
    logger.info("Out of stock message: " + outOfStockNotifyMeSection.getText());
    return outOfStockNotifyMeSection.getText();
  }

  public String getNotifyMeMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(notifyMeButton).visible());
    logger.info("Notify Me message: " + notifyMeButton.getText());
    return notifyMeButton.getText();
  }

  public NotifyMeComponent setMailIntoNotifyMeBox(String txt) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(notifyMeBox).visible());
    logger.info("Notify Me text:" + txt);
    notifyMeBox.sendKeys(txt);
    notifyMeButton.click();
    return SdkHelper.create(NotifyMeComponent.class);
  }

  @Step("Get item is sampler")
  public boolean itemIsSampler() {
    logger.info(" -- checking if item is a sampler item");
    return itemTitle.getText().toLowerCase().trim().contains("sampler");
  }

  @Step("Get one time purchase link is available")
  public boolean isOneTimePurchaseLinkDisplayed() {
    logger.info("checking if one time purchase link is available");
    return WebHelper.getVisibility(oneTimePurchase.getWebElement());
  }

  @Step("Get grind selected")
  public String getGrindSelected() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected).visible());
    String text = "";
    text = grindSelected.getText().toLowerCase().trim();

    logger.info("[PDP] Grind Selected: " + text);

    if (WebHelper.isDesktop() && WebHelper.isSafari()) {
      text =
          grindListSelected
              .stream()
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

  public boolean isSubscriptionWeeksBoxDisplayed() {
    WebHelper.scrollToElement(subscriptionWeekBox);
    return subscriptionWeekBox.isDisplayed();
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
    SdkHelper.getSyncHelper().wait(Until.uiElement(oneTimePurchase).present());
    WebHelper.scrollToElement(oneTimePurchase);
    SdkHelper.getSyncHelper().wait(Until.uiElement(oneTimePurchase).visible());
    oneTimePurchase.click();
  }

  @Step("Click Add to minicart")
  public MiniCart clickAddToMiniCart() {
    logger.info("Adding to MiniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(2000); // Wait for action
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
      SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(CartPage.class);
  }

  @Step("Click on the Buy Now")
  public CheckOutPage clickAddBuyNow() {
    logger.info("Clicking on the Buy Now");
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToCartButton).clickable());
    if (!WebHelper.isDesktop()) {
      WebHelper.scrollToElement(addToCartButton);
      SdkHelper.getSyncHelper().sleep(2000); // Wait for action
    }

    addToCartButton.click();
    return SdkHelper.create(CheckOutPage.class);
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
    boolean isDisplayed = WebHelper.getVisibility(subscribeType.getWebElement());
    logger.info("Subscribe Type is displayed - [{}]", isDisplayed);
    return isDisplayed;
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
    Iterator var3 = grindDropdown.stream().iterator();

    while (var3.hasNext()) {
      ContainerElement optionElement = (ContainerElement) var3.next();
      if (option
          .getValue()
          .equalsIgnoreCase(
              optionElement.getAttributeValue(Attribute.DATA_VALUE.getValue()).trim())) {
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

  @Step("Select Gift Subscription Grind")
  public ProductDetailsPage selectGiftSubscriptionGrind(GrindDropdown option) {
    logger.info("Selecting [{}] Gift Subscription grind option", option.getValue());
    grindSelected.click();
    grindOptionButton.format(option.getValue()).initialize();
    WebHelper.jsClick(grindOptionButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait to be populated
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
    shipEveryDropdown.select(option.getValue());
    return this;
  }

  @Step("Get Selected Ship Every")
  public String getSelectedShipEvery() {
    String selected = WebHelper.cleanString(shipEveryDropdown.getSelectedOption().getText());
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
    return brewingMethods
        .stream()
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

  @Step("Scroll to Recommended For You Quiz Link")
  public boolean scrollDowntoRecommendedForYou(){
    WebHelper.scrollToPageBottom();
    WebHelper.scrollToElement(recommendedForYouQuizLink);
    return WebHelper.isDisplayed(recommendedForYouQuizLink);

  }

  //ONBOARDING ---------------------------------------------------
  @Step("Scroll to Newsletter button")
  public boolean scrollDownToNewsletterButton(){
    WebHelper.scrollToElement(newsletterButton);
    return WebHelper.isDisplayed(newsletterButton);
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

  public SoftAssert areQuantityBoxesDisplayed() {
    SoftAssert softAssert = new SoftAssert();
    logger.info("Review Quantity Boxes: " + quantityBoxes.size());
    softAssert.assertEquals(quantityBoxes.size(), 3, "Not all Quantity boxes are displayed");
    softAssert.assertTrue(quantityBoxes.get(0).isDisplayed(), "Quantity box 1 is not displayed");
    softAssert.assertTrue(quantityBoxes.get(1).isDisplayed(), "Quantity box 2 is not displayed");
    softAssert.assertTrue(quantityBoxes.get(2).isDisplayed(), "Quantity box 3 is not displayed");

    return softAssert;
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

    WebHelper.jsClick(quantityBox.getWebElement()); // Due to button being overlapped
    softAssert.assertFalse(quantityBoxSelected.isDisplayed(), "Quantity 2 should not be displayed");

    return softAssert;
  }

  public SoftAssert validateGiftQuantityElements() {
    SoftAssert softAssert = new SoftAssert();

    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity).visible());
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
    softAssert.assertFalse(quantityBoxSelected.isDisplayed(), "Quantity 2 is not selected");

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
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      for (int i = 0; i < 4; i++) {
        SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
      }
    } else {
      WebHelper.scrollToElement(flavorProfileSubtitle);
      WebHelper.scrollToPageBottom();
    }

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

  public void selectQuantityByIndex(int index) {
    logger.info("Selecting quantity by index: {}", index);
    quantityBox.format(index).initialize();
    WebHelper.scrollToElement(quantityBox);
    quantityBox.click();
  }

  public boolean isQuantitySelected(int index) {
    logger.info("Checking quantity with index {} is selected", index);
    quantityBoxSelected.format(index).initialize();
    return quantityBoxSelected.isDisplayed();
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

  @Step("Select Gift Duration value")
  public ProductDetailsPage selectGiftDuration(GiftDuration giftDuration) {
    logger.info("Selecting Gift Duration [{}]", giftDuration.getOption());
    giftDurationDropdown
        .getOptions()
        .stream()
        .filter(
            option ->
                option
                    .getAttributeValue(Attribute.VALUE.getValue())
                    .startsWith(giftDuration.getOption()))
        .findFirst()
        .orElseThrow(
            () ->
                new RuntimeException(
                    "Unable to find Gift Duration [" + giftDuration.getOption() + "]"))
        .click();
    return this;
  }

  @Step("Check if 'Buy Now' is Displayed")
  public boolean isBuyNowButtonDisplayed() {
    boolean isDisplayed =
        WebHelper.isDisplayed(addToCartButton)
            && addToCartButtonText.getText().equalsIgnoreCase("Buy Now");
    logger.info("Buy Now is displayed - [{}]", isDisplayed);
    return isDisplayed;
  }

  @Step("Click Buy now")
  public CheckOutPage clickBuyNow() {
    logger.info("Clicking on the 'Buy Now' button");
    WebHelper.waitForElementToAppear(buyNowButton);
    WebHelper.scrollToElement(buyNowButton);
    buyNowButton.click();
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Verify Coffee ships once per month text is displayed")
  public boolean isCoffeeShipsOncePerMonthTextDisplayed() {
    if (!WebHelper.isDisplayed(coffeeShipsOncePerMonthText)) {
      logger.debug("Coffee ships once per month text is not displayed");
      return false;
    }

    if (!coffeeShipsOncePerMonthText
        .getText()
        .equalsIgnoreCase(TestData.COFFEE_SHIPS_ONCE_PER_MONTH_MESSAGE)) {
      logger.debug(
          "Coffee ships once per month text is not correct. Expected [{}]. Actual [{}]",
          TestData.COFFEE_SHIPS_ONCE_PER_MONTH_MESSAGE,
          coffeeShipsOncePerMonthText.getText());
      return false;
    }

    return true;
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

  @Override
  @Step("Get one time purchase link is available")
  public boolean isOneTimePurchaseLinkDisplayed() {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      logger.info("Checking if one time purchase link is available on iOS");
      return oneTimePurchase.isDisplayed();
    } else {
      return super.isOneTimePurchaseLinkDisplayed();
    }
  }

  @Override
  @Step("Check if Subscribe Type is Displayed")
  public boolean isSubscribeTypeDisplayed() {
    if (SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      logger.info("Checking if Subscribe Type is displayed on iOS");
      boolean isDisplayed = WebHelper.isDisplayed(subscribeType);
      logger.info("Subscribe Type is displayed - [{}]", isDisplayed);
      return isDisplayed;
    } else {
      return super.isOneTimePurchaseLinkDisplayed();
    }
  }

  @Override
  public String getAddToExistingSubscriptionButtonText() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(addToSubscribeOrderButton).present());
    String addToSubscribeOrderText = addToSubscribeOrderButton.getText().toLowerCase().trim();
    logger.info("[PDP] Add to Existing Subscription: " + addToSubscribeOrderText);
    return addToSubscribeOrderText;
  }

  @Step("Click Buy now")
  public CheckOutPage clickBuyNow() {
    if (!SdkHelper.getEnvironmentHelper().isMobileIOS()) {
      return super.clickBuyNow();
    }

    logger.info("Clicking on the 'Buy Now' button iOS");
    WebHelper.scrollToElement(buyNowButton);
    WebHelper.jsClick(buyNowButton.getWebElement());
    SdkHelper.getSyncHelper().sleep(1000); // Wait for action
    return SdkHelper.create(CheckOutPage.class);
  }
}
