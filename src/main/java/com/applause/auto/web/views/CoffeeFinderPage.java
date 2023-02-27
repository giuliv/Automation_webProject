package com.applause.auto.web.views;

import com.applause.auto.common.data.enums.CoffeeFinderAnswers;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import com.applause.auto.web.components.AnswerItemComponent;
import com.applause.auto.web.helpers.WebHelper;
import io.qameta.allure.Step;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Point;

@Implementation(is = CoffeeFinderPage.class, on = Platform.WEB)
@Implementation(is = CoffeeFinderPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class CoffeeFinderPage extends Base {

  @Locate(xpath = "//div[contains(@class, 'answer-list')]", on = Platform.WEB)
  private ContainerElement signature;

  @Locate(
      xpath = "//div[contains(@class, 'header')]/div[contains(@class, '_title')]",
      on = Platform.WEB)
  private Text title;

  @Locate(xpath = "//div[.='just take me to my results']", on = Platform.WEB)
  protected Button takeMeToMyResultsButton;

  @Locate(xpath = "//div[@class='question-guidance-custom']", on = Platform.WEB)
  private Button didYouKnowButton;

  @Locate(
      xpath = "//div[contains(@class, 'answer-list')]/div[contains(@class, 'answer-item')]",
      on = Platform.WEB)
  private List<AnswerItemComponent> typeOfCoffeeList;

  @Locate(
      xpath = "//div[contains(@class, 'text-box') and contains(@class, 'value')]/p",
      on = Platform.WEB)
  private Text didYouKnowText;

  @Locate(xpath = "//a[contains(@class, 'brew-guides')]", on = Platform.WEB)
  private Button brewGuidesButton;

  @Locate(
      xpath =
          "//div[contains(@class, 'coffee-disk')]//*[contains(@transform, 'scale')]//*[@font-size][last()]",
      on = Platform.WEB)
  private List<Button> flavorsList;

  @Locate(xpath = "//div[contains(@class, 'selected-flavors')]/img", on = Platform.WEB)
  private Button selectedFlavorsInTheCircle;

  @Locate(
      xpath = "//div[contains(@class, 'answer-item') and contains(@class, 'selected')]",
      on = Platform.WEB)
  private List<Button> selectedFlavorsButton;

  @Locate(xpath = "//div[contains(@class, 'loadingA')]", on = Platform.WEB)
  protected Text animation;

  @Locate(xpath = "(//div[contains(@class, 'button--next')]/div)[1]", on = Platform.WEB)
  @Locate(
      xpath = "(//div[contains(@class, 'button--next')]/div)[2]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button nextQuestionButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(signature).present());
    logger.info("Current URL - [{}]", SdkHelper.getDriver().getCurrentUrl());
  }

  /**
   * Get page Title
   *
   * @return String
   */
  @Step("Get page Title")
  public String getTitle() {
    String text = WebHelper.cleanString(title.getText());
    logger.info("Page Title - [%s]", text);
    return text;
  }

  /**
   * Check if 'Did You Know' Button is displayed
   *
   * @return boolean
   */
  @Step("Check if 'Did You Know' Button is displayed")
  public boolean isDidYouKnowButtonDisplayed() {
    return WebHelper.isDisplayed(didYouKnowButton);
  }

  /**
   * Get Type Of Coffee List
   *
   * @return List<AnswerItemComponent>
   */
  public List<AnswerItemComponent> getTypeOfCoffeeList() {
    ((LazyList<?>) typeOfCoffeeList).initialize();
    return typeOfCoffeeList;
  }

  /**
   * Click on 'Did you know?' link
   *
   * @return CoffeeFinderPage
   */
  @Step("Click on 'Did you know?' link")
  public CoffeeFinderPage clickOnDidYouKnowButton() {
    logger.info("Clicking on 'Did you know?'");
    didYouKnowButton.click();
    return this;
  }

  /**
   * Check if 'Did You Know' Text is displayed
   *
   * @return boolean
   */
  @Step("Check if 'Did You Know' Text is displayed")
  public boolean isDidYouKnowTextDisplayed() {
    return WebHelper.isTextDisplayedAndNotEmpty(didYouKnowText);
  }

  /**
   * Click on 'Brew Guides' link
   *
   * @return BrewGuidesPage
   */
  @Step("Click on 'Brew Guides' link")
  public BrewGuidesPage clickOnBrewGuidesButton() {
    logger.info("Clicking on 'Brew Guides' Button");
    String windowHandle = SdkHelper.getDriver().getWindowHandle();
    brewGuidesButton.click();
    WebHelper.switchToNewTab(windowHandle);
    return SdkHelper.create(BrewGuidesPage.class);
  }

  /**
   * Select specific Answer
   *
   * @return BaseComponent
   */
  @Step("Select specific Answer")
  public <T extends BaseComponent> T selectAnswer(CoffeeFinderAnswers answer, Class<T> clazz) {
    return getTypeOfCoffeeList()
        .stream()
        .filter(item -> StringUtils.equalsIgnoreCase(item.getName(), answer.getValue()))
        .findFirst()
        .orElseThrow(
            () -> new RuntimeException("Unable to find Answer [" + answer.getValue() + "]"))
        .click(clazz);
  }

  /**
   * Get Flavors List
   *
   * @return List<Button>
   */
  public List<Button> getFlavorsList() {
    ((LazyList<?>) flavorsList).initialize();
    return flavorsList;
  }

  /**
   * Select specific Flavor on position
   *
   * @return CoffeeFinderPage
   */
  @Step("Select specific Flavor on position")
  public CoffeeFinderPage selectFlavor(int position) {
    logger.info("Selecting [{}] flower");
    Button flavor = getFlavorsList().get(position - 1);
    WebHelper.clickByAction(flavor);
    return this;
  }

  /**
   * Get Count Of Selected Flavors In The Circle
   *
   * @return int
   */
  @Step("Get Count Of Selected Flavors In The Circle")
  public int getCountOfSelectedFlavorsInTheCircle() {
    return SdkHelper.getQueryHelper().elementCount(selectedFlavorsInTheCircle.getLocator());
  }

  /**
   * Get Selected Flavors List
   *
   * @return List<Button>
   */
  public List<Button> getSelectedFlavorsList() {
    ((LazyList<?>) selectedFlavorsButton).initialize();
    return selectedFlavorsButton;
  }

  /**
   * Get Count Of Selected Flavors In The Circle
   *
   * @return CoffeeFinderPage
   */
  @Step("Get Count Of Selected Flavors In The Circle")
  public CoffeeFinderPage clickOnSelectedFlavorsButton(int position) {
    logger.info("Clicking On [{}] Selected Flavors");
    getSelectedFlavorsList().get(position - 1).click();
    WebHelper.waitForElementToDisappear(animation, 5);
    return SdkHelper.create(CoffeeFinderPage.class);
  }

  /**
   * Click on Next question.
   *
   * @return CoffeeFinderPage
   */
  @Step("Click on Next question.")
  public CoffeeFinderPage clickOnNextQuestionButton() {
    logger.info("Clicking On 'Next question' button");
    nextQuestionButton.click();
    WebHelper.waitForElementToDisappear(animation, 5);
    return SdkHelper.create(CoffeeFinderPage.class);
  }

  @Step("Check if Coffee Finder Page is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(signature);
  }

  @Step("Click on 'just take me to my results'.")
  public CoffeeFinderPage clickOnTakeMeToMyResults() {
    takeMeToMyResultsButton.click();
    return this;
  }
}

class CoffeeFinderPageMobile extends CoffeeFinderPage {

  @Locate(xpath = "(//div[contains(@class, 'button--next')]/div)[1]", on = Platform.WEB)
  private Button nextQuestionMobileButton;

  @Locate(
      css = "button .mobile-coffee-disk__selection-panel__answer__title",
      on = Platform.WEB_MOBILE_PHONE)
  private List<Button> optionsList;

  @Locate(
      css = "button.mobile-coffee-disk__selection-panel__select",
      on = Platform.WEB_MOBILE_PHONE)
  private Button selectButton;

  @Override
  @Step("Select specific Flavor on position")
  public CoffeeFinderPage selectFlavor(int position) {
    logger.info("Selecting [{}] flower");
    Button flavor = getFlavorsList().get(position - 1);
    WebHelper.scrollToElement(flavor);
    Point location = flavor.getWebElement().getLocation();
    //    WebHelper.clickOnCoordinatesWithNativeTap(location.getX(), location.getY());
    SdkHelper.getDeviceControl().tapScreenCoordinates(location.getX(), location.getY());
    SdkHelper.getDeviceControl()
        .tapElementCoordinates(
            flavor, flavor.getDimension().height / 2, flavor.getDimension().width / 2);
    optionsList.get(0).click();
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    selectButton.click();
    return this;
  }

  @Override
  @Step("Click on Next question.")
  public CoffeeFinderPage clickOnNextQuestionButton() {
    if (WebHelper.isDisplayed(nextQuestionButton)) {
      logger.info("Clicking On 'Next question' button");
      nextQuestionButton.click();
    } else {
      logger.info("Clicking On 'Next question' mobile button");
      nextQuestionMobileButton.click();
    }
    WebHelper.waitForElementToDisappear(animation, 5);
    return SdkHelper.create(CoffeeFinderPage.class);
  }
}
