package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.data.enums.SwipeDirection;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.components.RemoveFromOrderChunk;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;

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

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name == 'button back'`]",
      on = Platform.MOBILE_IOS)
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
      id = "com.wearehathway.peets.development:id/productCostTextView",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      xpath =
          "\t\n"
              + "//XCUIElementTypeButton[@name=\"Add to Order\"]/../XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeStaticText[last()]",
      on = Platform.MOBILE_IOS)
  protected Text costText;

  @Locate(
      xpath = "//android.widget.TextView[starts-with(@text,'Warming')]",
      on = Platform.MOBILE_ANDROID)
  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"Warming\"`]",
      on = Platform.MOBILE_IOS)
  protected Button warmingButton;

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
   * Navigate back t.
   *
   * @param <T> the type parameter
   * @param clazz the clazz
   * @return the t
   */
  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Navigate Back");
    getBackButton.click();
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

  public String getCost() {
    String result = costText.getText();
    logger.info("Cost: " + result);
    return result;
  }

  public FoodWarmingView warming() {
    logger.info("Select Warming");
    warmingButton.click();
    return SdkHelper.create(FoodWarmingView.class);
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
