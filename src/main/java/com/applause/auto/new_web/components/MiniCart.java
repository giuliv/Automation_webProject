package com.applause.auto.new_web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.CartPage;
import com.applause.auto.new_web.views.CheckOutPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.util.List;
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

  @Locate(css = "#sr_headerDiv a[onclick*='learn']", on = Platform.WEB)
  private Link learnMoreLink;

  @Locate(css = "#sr_headerDiv a[onclick*='sign']", on = Platform.WEB)
  private Link signInLink;

  @Locate(css = ".bag-recommendations__footer button[aria-label='Next']", on = Platform.WEB)
  private Button nextRecommendedItem;

  @Locate(css = ".bag-recommendations__footer button[aria-label='Previous']", on = Platform.WEB)
  private Button prevRecommendedItem;

  @Locate(css = "#bagRecommendations article", on = Platform.WEB)
  private List<ContainerElement> recommendedItemsList;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mainContainer).visible());
  }

  /* -------- Actions -------- */

  public boolean isProductNameDisplayedIndex() {
    return productNameOnlyItem.exists();
  }

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

  public String getGrindByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(grindSelected.get(index)).visible());
    logger.info(
        "[MiniCart] Grind Selected: " + grindSelected.get(index).getText().toLowerCase().trim());

    return grindSelected.get(index).getText().toLowerCase().trim();
  }

  public String getPriceByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(itemPrice.get(index)).visible());
    logger.info("[MiniCart] Item Price: " + itemPrice.get(index).getText().trim());

    return itemPrice.get(index).getText().trim();
  }

  public int getProductQuantityByIndex(int index) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(productQuantity.get(index)).present());
    logger.info(
        "[MiniCart] Product Quantity: " + productQuantity.get(index).getAttributeValue("value"));

    return Integer.parseInt(productQuantity.get(index).getAttributeValue("value"));
  }

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

  public String getEmptyMiniCartMessage() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(emptyMiniCartMessage).present());
    logger.info("[MiniCart] Empty MiniCart message: " + emptyMiniCartMessage.getText().trim());

    return emptyMiniCartMessage.getText().trim();
  }

  public void addOneMoreItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(addButton).clickable());
    addButton.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  public void removeOneItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(removeItem).clickable());
    removeItem.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

  public void removeItem() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(removeItemLink).clickable());
    removeItemLink.click();
    SdkHelper.getSyncHelper().sleep(2000); // Wait for action
  }

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

  public CheckOutPage clickContinueToCheckOut() {
    logger.info("Clicking CheckOut");
    SdkHelper.getSyncHelper().wait(Until.uiElement(checkOutButton).visible());
    checkOutButton.click();

    return SdkHelper.create(CheckOutPage.class);
  }

  public <V extends BaseComponent> V closeMiniCart(Class<V> expectedClass) {
    logger.info("Closing miniCart");
    SdkHelper.getSyncHelper().wait(Until.uiElement(closeButton).visible());
    closeButton.click();

    return SdkHelper.create(expectedClass);
  }

  /**
   * Click 'View Cart' button
   *
   * @return CartPage
   */
  public CartPage clickViewCartButton() {
    logger.info("Clicking 'View Cart' button");
    SdkHelper.getBrowserControl().jsClick(viewCartButton);
    return SdkHelper.create(CartPage.class);
  }

  public ShopRunnerComponent clickShopRunnerLinks(String link) {
    logger.info("Clicking link: " + link);
    if (link.equalsIgnoreCase("learn more")) {
      learnMoreLink.click();
    } else {
      signInLink.click();
    }
    return SdkHelper.create(ShopRunnerComponent.class);
  }

  /**
   * Click on 'Add to cart' button on 'Recommended for you' section
   *
   * @param index
   * @return QuickViewComponent
   */
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

  public long getRecommendedItemsVisible() {
    return recommendedForYouAddButton.stream().filter(x -> x.isDisplayed()).count();
  }
}
