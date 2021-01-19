package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.helper.sync.Until;

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
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button sizeButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button selectMilkPrepButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button selectSyrupsAndSaucesButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button selectShotOptionsButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button selectToppingsButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button selectSweetenersButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Button increaseQuantityButton;

  @Locate(
      xpath = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  protected Text quantityText;

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
    return null;
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
    selectSweetenersButton.click();
    return this.create(SweetenersView.class);
  }

  public ToppingsView selectToppings() {
    selectToppingsButton.click();
    return this.create(ToppingsView.class);
  }

  public void selectQuantity(String quantity) {
    while (!quantityText.equals(quantity)) {
      increaseQuantityButton.click();
    }
  }

  public SyrupsAndSaucesView selectSyrups() {
    selectSyrupsAndSaucesButton.click();
    return this.create(SyrupsAndSaucesView.class);
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
