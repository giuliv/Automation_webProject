package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.mobile.components.RemoveFromOrderChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.helpers.sync.Until;
import java.time.Duration;
import java.util.stream.IntStream;
import com.applause.auto.framework.SdkHelper;
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

  @Locate(accessibilityId = "Delete", on = Platform.MOBILE_IOS)
  @Locate(id = "com.wearehathway.peets.development:id/delete", on = Platform.MOBILE_ANDROID)
  protected Button garbageButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`label == \"Update Order\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      id = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button updateOrderButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(id = "Add to Order", on = Platform.MOBILE_IOS)
  protected Button getAddToOrderButton;

  @Locate(
      xpath =
          "//android.widget.Button[@resource-id='com.wearehathway.peets.development:id/modifierButton' and @text='%s']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  protected Button sizeButton;

  @Locate(
      xpath =
          "//android.widget.TextView[starts-with(@text,'Milk Prep') or starts-with(@text,'Milk')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Milk Prep\" OR label == \"Milk\"`]",
      on = Platform.MOBILE_IOS)
  protected Button selectMilkPrepButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Syrups & Sauces')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label CONTAINS \"Syrups & Sauces\"`]",
      on = Platform.MOBILE_IOS)
  protected Button selectSyrupsAndSaucesButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/productCostTextView",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "\t\n"
              + "//XCUIElementTypeButton[@name=\"Add to Order\"]/../XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText[last()]",
      on = Platform.MOBILE_IOS)
  protected Text costText;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Shot Options')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Shot Options\"`]",
      on = Platform.MOBILE_IOS)
  protected Button selectShotOptionsButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Warming')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Warming\"`]",
      on = Platform.MOBILE_IOS)
  protected Button warmingButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Oatmeal Toppings')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Oatmeal Toppings\"`]",
      on = Platform.MOBILE_IOS)
  protected Button oatmealToppingsButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Add Cream Cheese')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Add Cream Cheese\"`]",
      on = Platform.MOBILE_IOS)
  protected Button addCreamCheeseButton;

  @Locate(
      xpath =
          "//android.widget.TextView[starts-with(@text,'Add Toppings') or starts-with(@text,'Toppings')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Add Toppings\"`]",
      on = Platform.MOBILE_IOS)
  protected Button selectToppingsButton;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Sweeteners')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label CONTAINS \"Sweeteners\"`]",
      on = Platform.MOBILE_IOS)
  protected Button selectSweetenersButton;

  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/quantity']//android.widget.ImageButton[@resource-id='com.wearehathway.peets.development:id/increaseQuantity']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Quantity\"]/../XCUIElementTypeButton[@name=\"+\"]",
      on = Platform.MOBILE_IOS)
  protected Button increaseQuantityButton;

  @Locate(
      xpath =
          "//android.widget.RelativeLayout[@resource-id='com.wearehathway.peets.development:id/quantity']//android.widget.TextView[@resource-id='com.wearehathway.peets.development:id/productQuantity']",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "//XCUIElementTypeStaticText[@name=\"Quantity\"]/following-sibling::XCUIElementTypeStaticText",
      on = Platform.MOBILE_IOS)
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
    getCategoryItem.format(category);
    SdkHelper.getDeviceControl().tapElementCenter(getCategoryItem);
    getCategoryItem.format(subCategory);
    SdkHelper.getDeviceControl().tapElementCenter(getCategoryItem);
    getSaveChangesButton.click();
    return SdkHelper.create(ProductDetailsView.class);
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
    return SdkHelper.create(clazz);
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
    return SdkHelper.create(clazz);
  }

  public String getSize() {
    return defaultSizeButton.getText();
  }

  public void selectSize(String size) {
    sizeButton.format(size);
    sizeButton.initialize();
    sizeButton.click();
  }

  public MilkPrepView selectMilkPrep() {
    MobileHelper.scrollElementIntoView(selectMilkPrepButton);
    SdkHelper.getDeviceControl().tapElementCenter(selectMilkPrepButton);
    return SdkHelper.create(MilkPrepView.class);
  }

  public ShotOptionsView selectShotOptions() {
    MobileHelper.scrollElementIntoView(selectShotOptionsButton);
    SdkHelper.getDeviceControl().tapElementCenter(selectShotOptionsButton);
    return SdkHelper.create(ShotOptionsView.class);
  }

  public SweetenersView selectSweeteners() {
    MobileHelper.scrollElementIntoView(selectSweetenersButton);
    selectSweetenersButton.click();
    return SdkHelper.create(SweetenersView.class);
  }

  public ToppingsView selectToppings() {
    int attempt = 5;
    if (selectToppingsButton.exists() && selectToppingsButton.isDisplayed()) {
      selectToppingsButton.click();
    } else {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
      SdkHelper.getSyncHelper().sleep(1000);
      while (attempt-- > 0
          && !(selectToppingsButton.exists() && selectToppingsButton.isDisplayed())) {
        MobileHelper.scrollDownCloseToMiddleAlgorithm();
        SdkHelper.getSyncHelper().sleep(1000);
      }
      selectToppingsButton.click();
    }
    return SdkHelper.create(ToppingsView.class);
  }

  public ProductDetailsView selectQuantity(String quantity) {
    MobileHelper.scrollElementIntoView(increaseQuantityButton);
    int attempts = 5;
    quantityText.initialize();
    while (!quantityText.getText().equals(quantity) && (attempts-- > 0)) {
      logger.info("Increasing quantity. Current: " + quantityText.getText());
      logger.info("Increasing quantity. Expected: " + quantity);
      logger.info(">>>DOM" + SdkHelper.getDriver().getPageSource());
      increaseQuantityButton.click();
      SdkHelper.getSyncHelper().sleep(1000);
    }
    return this;
  }

  public SyrupsAndSaucesView selectSyrups() {
    MobileHelper.scrollElementIntoView(selectSyrupsAndSaucesButton);
    SdkHelper.getDeviceControl().tapElementCenter(selectSyrupsAndSaucesButton);
    return SdkHelper.create(SyrupsAndSaucesView.class);
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

  public FoodWarmingView warming() {
    logger.info("Select Warming");
    warmingButton.click();
    return SdkHelper.create(FoodWarmingView.class);
  }

  public OatmealToppingsView selectOatmealToppings() {
    logger.info("Select Oatmeal Toppings");
    oatmealToppingsButton.click();
    return SdkHelper.create(OatmealToppingsView.class);
  }

  public AddCreamCheeseView selectAddCreamCheese() {
    logger.info("Select Add Cream Cheese");
    addCreamCheeseButton.click();
    return SdkHelper.create(AddCreamCheeseView.class);
  }

  public boolean isBackButtonDisplayed() {
    return getBackButton.isDisplayed();
  }

  public boolean isGarbageIconDisplayed() {
    return garbageButton.isDisplayed();
  }

  public boolean isUpdateOrderButtonDisplayed() {
    return updateOrderButton.isDisplayed();
  }

  public CheckoutView updateOrder() {
    updateOrderButton.click();
    return SdkHelper.create(CheckoutView.class);
  }

  public RemoveFromOrderChunk delete() {
    garbageButton.click();
    return SdkHelper.create(RemoveFromOrderChunk.class);
  }
}

class AndroidProductDetailsView extends ProductDetailsView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(120)));
  }

  @Override
  public ToppingsView selectToppings() {
    int attempt = 5;
    try {
      selectToppingsButton.click();
    } catch (NoSuchElementException nse) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
      SdkHelper.getSyncHelper().sleep(1000);
      while (attempt-- > 0 && !selectToppingsButton.exists()) {
        MobileHelper.scrollDownCloseToMiddleAlgorithm();
        SdkHelper.getSyncHelper().sleep(1000);
      }
      selectToppingsButton.click();
    }
    return SdkHelper.create(ToppingsView.class);
  }

  @Override
  public SweetenersView selectSweeteners() {
    if (selectSweetenersButton.exists() && selectSweetenersButton.isDisplayed()) {
      selectSweetenersButton.click();
    } else {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      selectSweetenersButton.click();
    }
    return SdkHelper.create(SweetenersView.class);
  }

  @Override
  public SyrupsAndSaucesView selectSyrups() {
    selectSyrupsAndSaucesButton.click();
    return SdkHelper.create(SyrupsAndSaucesView.class);
  }

  @Override
  public MilkPrepView selectMilkPrep() {
    if (selectMilkPrepButton.exists() && selectMilkPrepButton.isDisplayed()) {
      selectMilkPrepButton.click();
    } else {
      MobileHelper.scrollDownCloseToMiddleAlgorithm();
      selectMilkPrepButton.click();
    }
    return SdkHelper.create(MilkPrepView.class);
  }

  @Override
  public ShotOptionsView selectShotOptions() {
    selectShotOptionsButton.click();
    return SdkHelper.create(ShotOptionsView.class);
  }

  @Override
  public ProductDetailsView selectQuantity(String quantity) {
    SdkHelper.getDeviceControl().swipeAcrossScreenWithDirection(SwipeDirection.UP);
    SdkHelper.getSyncHelper().sleep(2000);
    int attempts = 3;
    while (!quantityText.getText().equals(quantity) && attempts-- > 0) {
      increaseQuantityButton.click();
    }
    return this;
  }
}
