package com.applause.auto.mobile.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import java.time.Duration;
import java.util.stream.IntStream;
import org.openqa.selenium.NoSuchElementException;

@Implementation(is = SubCategoryView.class, on = Platform.MOBILE_ANDROID)
@Implementation(is = SubCategoryView.class, on = Platform.MOBILE_IOS)
public class SubCategoryView extends BaseComponent {

  @Locate(
      iOSClassChain =
          "**/XCUIElementTypeNavigationBar[`visible = 1`]/XCUIElementTypeStaticText[`visible = 1`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().resourceIdMatches(\".*id/title\")",
      on = Platform.MOBILE_ANDROID)
  protected Text title;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name == 'Tap Here to Select Delivery'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      androidUIAutomator = "new UiSelector().text(\"Tap Here to Select Delivery\")",
      on = Platform.MOBILE_ANDROID)
  protected Button tapHereToSelectDeliveryButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeButton[`name == 'button back'`]",
      on = Platform.MOBILE_IOS)
  @Locate(
      xpath =
          "//android.widget.ImageButton[contains(@content-desc,\"Navigate up\") or contains(@content-desc,\"Nach oben\")]",
      on = Platform.MOBILE_ANDROID)
  protected Button backButton;

  @Locate(
      iOSClassChain = "**/XCUIElementTypeStaticText[`label == \"%s\"`]",
      on = Platform.MOBILE_IOS)
  @Locate(xpath = "//android.widget.TextView[@text=\"%s\"]", on = Platform.MOBILE_ANDROID)
  protected ContainerElement productItem;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(
            Until.uiElement(tapHereToSelectDeliveryButton)
                .present()
                .setTimeout(Duration.ofSeconds(10)));
  }

  /**
   * Get the text value of the heading
   *
   * @return String
   */
  public String getTitle() {
    String titleText = title.getText();
    logger.info("Title - [{}]", titleText);
    return titleText;
  }

  public <T extends BaseComponent> T navigateBack(Class<T> clazz) {
    logger.info("Navigate Back");
    backButton.click();
    return SdkHelper.create(clazz);
  }

  /**
   * Select product product details view.
   *
   * @param category the category
   * @return the product details view
   */
  public ProductDetailsView selectProduct(String category) {
    logger.info("Select product: " + category);
    int attempt = 5;
    productItem.format(category);
    try {
      productItem.initialize();
    } catch (NoSuchElementException nse) {
      IntStream.range(0, attempt)
          .forEach(
              i -> {
                MobileHelper.scrollUpCloseToMiddleAlgorithm();
              });
      /**
       * TODO
       *
       * <p>need a wait right after the swite to animation finished.
       */
      SdkHelper.getSyncHelper().sleep(1000);
      while (attempt-- > 0 && !productItem.exists()) {
        MobileHelper.scrollDownCloseToMiddleAlgorithm();
        SdkHelper.getSyncHelper().sleep(1000);
      }
    }
    SdkHelper.getSyncHelper().sleep(1000);
    SdkHelper.getDeviceControl().tapElementCenter(productItem);
    return SdkHelper.create(ProductDetailsView.class);
  }
}
