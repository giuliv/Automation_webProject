package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;

@Implementation(is = ProductDetailsView.class, on = Platform.MOBILE_IOS)
@Implementation(is = AndroidProductDetailsView.class, on = Platform.MOBILE_ANDROID)
public class ProductDetailsView extends BaseComponent {

  /* -------- Elements -------- */

  @Locate(
      xpath = "//XCUIElementTypeStaticText[contains(@name,'Calories')]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/productCalories",
      on = Platform.MOBILE_ANDROID)
  protected Text getHeadingText;

  @Locate(xpath = "//XCUIElementTypeStaticText[@name=\"%s\"]", on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected ContainerElement getCategoryItem;

  @Locate(id = "Save Changes", on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/saveChangesButton",
      on = Platform.MOBILE_ANDROID)
  protected Button getSaveChangesButton;

  @Locate(id = "button back", on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(id = "Add to Order", on = Platform.MOBILE_IOS)
  protected Button getAddToOrderButton;

  @Locate(
      xpath =
          "//android.widget.Button[@resource-id='com.wearehathway.peets.development:id/modifierButton' and @text='%s']",
      on = Platform.MOBILE_ANDROID)
  protected Button sizeButton;

  @Locate(
      xpath =
          "//android.widget.TextView[starts-with(@text,'Milk Prep') or starts-with(@text,'Milk')]",
      on = Platform.MOBILE_ANDROID)
  protected Button selectMilkPrepButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Syrups & Sauces')]",
      on = Platform.MOBILE_ANDROID)
  protected Button selectSyrupsAndSaucesButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/productCostTextView",
      on = Platform.MOBILE_ANDROID)
  protected Text costText;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Shot Options')]",
      on = Platform.MOBILE_ANDROID)
  protected Button selectShotOptionsButton;

  @Locate(
      xpath =
          "//android.widget.TextView[starts-with(@text,'Add Toppings') or starts-with(@text,'Toppings')]",
      on = Platform.MOBILE_ANDROID)
  protected Button selectToppingsButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Sweeteners')]",
      on = Platform.MOBILE_ANDROID)
  protected Button selectSweetenersButton;

  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/quantity']//android.widget.ImageButton[@resource-id='com.wearehathway.peets.development:id/increaseQuantity']",
      on = Platform.MOBILE_ANDROID)
  protected Button increaseQuantityButton;

  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/quantity']//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productQuantity']",
      on = Platform.MOBILE_ANDROID)
  protected Text quantityText;

  @Locate(
      xpath =
          "//android.widget.Button[@resource-id='com.wearehathway.peets.development:id/modifierButton' and @selected='true']",
      on = Platform.MOBILE_ANDROID)
  protected Button defaultSizeButton;

  @Locate(
      xpath =
          "//android.widget.TextView[starts-with(@text,'%s') and @resource-id='com.wearehathway.peets.development:id/categoryNameTextView']/following-sibling::android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/categorySelectionsTextView']",
      on = Platform.MOBILE_ANDROID)
  protected Text modifiersText;

  /* -------- Actions -------- */

  /**
   * Get the text vaalue of the heading
   *
   * @return
   */
  public String getHeadingTextValue() {
    return getHeadingText.getText();
  }

  /**
   * Select modifiers product details view.
   *
   * @param category the category
   * @param subCategory the sub category
   * @return the product details view
   */
  public ProductDetailsView selectModifiers(String category, String subCategory) {
    logger.info("Select category: " + category + " | " + subCategory);
    getCategoryItem.initializeWithFormat(category);
    getDeviceControl().tapElementCenter(getCategoryItem);
    getCategoryItem.initializeWithFormat(subCategory);
    getDeviceControl().tapElementCenter(getCategoryItem);
    getSaveChangesButton.click();
    return this.create(ProductDetailsView.class);
  }

  /**
   * Navigate back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Navigate Back");
    getBackButton.click();
    if (!clazz.equals(SearchResultsView.class)) {
      getBackButton.click();
    }
    return this.create(clazz);
  }

  /**
   * Add to Order
   *
   * @param clazz
   * @param <T>
   * @return the t
   */
  public <T extends BaseComponent> T addToOrder(Class<T> clazz) {
    logger.info("Tap Add to Order");
    getAddToOrderButton.click();
    return this.create(clazz);
  }

  public String getSize() {
    return defaultSizeButton.getText();
  }

  public void selectSize(String size) {
    sizeButton.format(size);
    sizeButton.click();
  }

  public MilkPrepView selectMilkPrep() {
    selectMilkPrepButton.click();
    return this.create(MilkPrepView.class);
  }

  public ShotOptionsView selectShotOptions() {
    selectShotOptionsButton.click();
    return this.create(ShotOptionsView.class);
  }

  public SweetenersView selectSweeteners() {
    try {
      selectSweetenersButton.click();
    } catch (NoSuchElementException nse) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      selectSweetenersButton.click();
    }
    return this.create(SweetenersView.class);
  }

  public ToppingsView selectToppings() {
    try {
      selectToppingsButton.click();
    } catch (NoSuchElementException nse) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      selectToppingsButton.click();
    }
    return this.create(ToppingsView.class);
  }

  public void selectQuantity(String quantity) {
    getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    int attempts = 3;
    while (!quantityText.getText().equals(quantity) && attempts-- > 0) {
      increaseQuantityButton.click();
    }
  }

  public SyrupsAndSaucesView selectSyrups() {
    selectSyrupsAndSaucesButton.click();
    return this.create(SyrupsAndSaucesView.class);
  }

  public String getCost() {
    String result = costText.getText();
    logger.info("Cost: " + result);
    return result;
  }

  public String getModifies(String modifier) {
    MobileHelper.scrollUpCloseToMiddleAlgorithm();
    try {
      modifiersText.format(modifier).initialize();
    } catch (NoSuchElementException nse) {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      modifiersText.format(modifier).initialize();
    }
    String result = modifiersText.getText();
    logger.info("Modifiers for " + modifier + " found: " + result);
    return result;
  }
}

class AndroidProductDetailsView extends ProductDetailsView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(120)));
  }
}
