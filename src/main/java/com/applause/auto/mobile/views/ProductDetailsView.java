package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.control.DeviceControl;
import com.applause.auto.util.helper.SyncHelper;
import com.applause.auto.util.helper.sync.Until;
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
      xpath = "//android.widget.ImageButton[@content-desc='Navigate up']",
      on = Platform.MOBILE_ANDROID)
  protected Button getBackButton;

  @Locate(
      id = "com.wearehathway.peets.development:id/addOrUpdateProductButton",
      on = Platform.MOBILE_ANDROID)
  @Locate(id = "Add to Order", on = Platform.MOBILE_IOS)
  protected Button getAddToOrderButton;

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
    DeviceControl.tapElementCenter(getCategoryItem);
    getCategoryItem.initializeWithFormat(subCategory);
    DeviceControl.tapElementCenter(getCategoryItem);
    getSaveChangesButton.click();
    return ComponentFactory.create(ProductDetailsView.class);
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
    return ComponentFactory.create(clazz);
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
    return ComponentFactory.create(clazz);
  }
}

class AndroidProductDetailsView extends ProductDetailsView {

  /* -------- Lifecycle Methods -------- */

  @Override
  public void afterInit() {
    SyncHelper.wait(Until.uiElement(getHeadingText).present().setTimeout(Duration.ofSeconds(120)));
  }
}
