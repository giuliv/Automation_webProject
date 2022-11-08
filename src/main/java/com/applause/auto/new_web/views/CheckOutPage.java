package com.applause.auto.new_web.views;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.SelectList;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.pageobjectmodel.factory.LazyList;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Implementation(is = CheckOutPage.class, on = Platform.WEB)
@Implementation(is = CheckOutPageMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class CheckOutPage extends Base {

  @Locate(css = "div[data-step='contact_information']", on = Platform.WEB)
  private ContainerElement mainContainer;

  @Locate(id = "checkout_email", on = Platform.WEB)
  private TextBox mail;

  @Locate(id = "checkout_shipping_address_first_name", on = Platform.WEB)
  private TextBox firstName;

  @Locate(id = "checkout_shipping_address_last_name", on = Platform.WEB)
  private TextBox lastName;

  @Locate(id = "checkout_shipping_address_address1", on = Platform.WEB)
  private TextBox address;

  @Locate(id = "checkout_shipping_address_address2", on = Platform.WEB)
  private TextBox extraDetailsBox;

  @Locate(id = "checkout_shipping_address_city", on = Platform.WEB)
  private TextBox city;

  @Locate(id = "checkout_shipping_address_phone", on = Platform.WEB)
  private TextBox phone;

  @Locate(css = "select#checkout_shipping_address_country", on = Platform.WEB)
  private SelectList country;

  @Locate(css = "select#checkout_shipping_address_province", on = Platform.WEB)
  private SelectList province;

  @Locate(id = "checkout_shipping_address_zip", on = Platform.WEB)
  private TextBox zip;

  @Locate(id = "continue_button", on = Platform.WEB)
  protected Button continueToShipping;

  @Locate(css = "p[class*='logged-in']", on = Platform.WEB)
  private Text existingUser;

  @Locate(css = "select[data-address-selector]", on = Platform.WEB)
  private SelectList existingAddress;

  @Locate(id = "btn-proceed-address", on = Platform.WEB)
  private Button proceedButton;

  @Locate(xpath = "//p[contains(@id, 'error-for')]", on = Platform.WEB)
  private List<Text> errorMessagesList;

  @Locate(xpath = "//a[contains(@class, 'previous')]", on = Platform.WEB)
  protected Button returnToCartButton;

  @Locate(id = "checkout_reduction_code", on = Platform.WEB)
  protected TextBox discountCodeField;

  @Locate(xpath = "//button[contains(@class, 'field')]", on = Platform.WEB)
  protected Button applyCodeButton;

  @Locate(id = "error-for-reduction_code", on = Platform.WEB)
  private Text discountCodeError;

  @Locate(xpath = "//label[@for='checkout_remember_me']", on = Platform.WEB)
  private Checkbox saveThisInformationForNextTime;

  @Locate(css = ".section--contact-information", on = Platform.WEB)
  private ContainerElement contactInformationSection;

  @Locate(css = ".section--contact-information a[href*='login']", on = Platform.WEB)
  private Link loginLink;

  @Locate(css = ".section--contact-information a[href*='logout']", on = Platform.WEB)
  private Link logOutLink;

  @Locate(id = "order-summary", on = Platform.WEB)
  private ContainerElement orderSummarySection;

  @Locate(css = ".product__description__name", on = Platform.WEB)
  private LazyList<Text> productsNames;

  @Locate(
      xpath = "//div[@class='logged-in-customer-information']//span[contains(text(),'%s')]",
      on = Platform.WEB)
  private Text customerEmailAddress;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper()
        .wait(Until.uiElement(mainContainer).visible().setTimeout(Duration.ofSeconds(40)));
    logger.info("CheckOut Page URL: " + getDriver().getCurrentUrl());
  }

  /* -------- Actions -------- */

  @Step("Set checkout data")
  public void setCheckOutData() {
    logger.info("Setting CheckOut data...");
    typeEmail(Constants.WebTestData.EMAIL);

    firstName.sendKeys(Constants.WebTestData.FIRST_NAME);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    lastName.sendKeys(Constants.WebTestData.LAST_NAME);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    typeAddress(Constants.WebTestData.ADDRESS);

    extraDetailsBox.sendKeys(Constants.WebTestData.EXTRA_INFO);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    city.sendKeys(Constants.WebTestData.CITY);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    phone.sendKeys(Constants.WebTestData.PHONE);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    country.select(Constants.WebTestData.COUNTRY);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    province.select(Constants.WebTestData.PROVINCE);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action

    zip.clearText();
    zip.sendKeys(Constants.WebTestData.ZIP);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action
  }

  @Step("Set checkout data as existing user")
  public void setCheckOutDataAsExistingUser() {
    logger.info("Setting CheckOut data...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(address).visible());

    if (existingAddress.exists()) {
      logger.info("Using existing saved address");

      existingAddress.click();
      SdkHelper.getSyncHelper().sleep(1000); // Wait for action

      existingAddress.getOptions().get(0).click();
    } else {
      typeAddress(Constants.WebTestData.ADDRESS);

      city.sendKeys(Constants.WebTestData.CITY);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      phone.sendKeys(Constants.WebTestData.PHONE);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      country.select(Constants.WebTestData.COUNTRY);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      province.select(Constants.WebTestData.PROVINCE);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action

      zip.sendKeys(Constants.WebTestData.ZIP);
      SdkHelper.getSyncHelper().sleep(500); // Wait for action
    }
  }

  @Step("Type user Email")
  public void typeEmail(String email) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(mail).visible());
    logger.info("Typing [{}] email", email);
    mail.clearText();
    mail.sendKeys(email);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action
  }

  @Step("Type user Email")
  public void typeAddress(String userAddress) {
    SdkHelper.getSyncHelper().wait(Until.uiElement(address).visible());
    logger.info("Typing [{}] address", userAddress);
    address.clearText();
    address.sendKeys(userAddress);
    SdkHelper.getSyncHelper().sleep(500); // Wait for action
  }

  @Step("Review existing user mail")
  public String isExistingUserMailCorrect() {
    logger.info("Reviewing existing user...");
    SdkHelper.getSyncHelper().wait(Until.uiElement(existingUser).visible());

    return existingUser.getText();
  }

  @Step("Click continue to shipping")
  public ShippingPage clickContinueToShipping() {
    logger.info("Clicking Continue to Shipping");
    SdkHelper.getSyncHelper().wait(Until.uiElement(continueToShipping).visible());
    continueToShipping.click();

    return SdkHelper.create(ShippingPage.class);
  }

  @Step("Click continue button")
  public <T extends BaseComponent> T clickOnContinueButton(Class<T> clazz) {
    logger.info("Clicking on 'Continue' button");
    continueToShipping.click();
    return SdkHelper.create(clazz);
  }

  /** @return List<String> */
  @Step("Get list of error messages")
  public List<String> getListOfErrorMessages() {
    ((LazyList<?>) errorMessagesList).initialize();
    return errorMessagesList.stream()
        .map(error -> WebHelper.cleanString(error.getText()))
        .collect(Collectors.toList());
  }

  /**
   * Click on 'Return to cart' button
   *
   * @return CartPage
   */
  @Step("Click return to cart button")
  public CartPage clickOnReturnToCartButton() {
    logger.info("Clicking on 'Return to cart' button");
    returnToCartButton.click();
    return SdkHelper.create(CartPage.class);
  }

  /**
   * Apply Discount Code
   *
   * @param code
   * @return CheckOutPage
   */
  @Step("Apply discount code")
  public CheckOutPage applyDiscountCode(String code) {
    logger.info("Typing [{}] Discount Code", code);
    discountCodeField.clearText();
    discountCodeField.sendKeys(code);

    logger.info("Clicking on 'Apply' button");
    applyCodeButton.click();
    return SdkHelper.create(CheckOutPage.class);
  }

  /**
   * Get Discount Code Error
   *
   * @return String
   */
  @Step("Get discount code error")
  public String getDiscountCodeError() {
    String error = WebHelper.cleanString(discountCodeError.getText());
    logger.info("Discount Code Error - [{}]", error);
    return error;
  }

  /**
   * Click on 'Save This Information For The Next Time'
   *
   * @return CheckOutPage
   */
  @Step("Click on Save this information for next time")
  public CheckOutPage clickOnSaveThisInformationForTheNextTime() {
    logger.info("Clicking on 'Save this information for next time' checkbox");
    saveThisInformationForNextTime.click();
    return SdkHelper.create(CheckOutPage.class);
  }

  @Step("Get container")
  /** @return boolean */
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(mainContainer);
  }

  /**
   * Verify Contact Information section is displayed
   *
   * @return boolean
   */
  public boolean isContactInformationSectionDisplayed() {
    logger.info("Checking Contact Information section is displayed");
    return WebHelper.isDisplayed(contactInformationSection);
  }

  /**
   * Verify customer email address is displayed
   *
   * @return boolean
   */
  public boolean isCustomerEmailAddressDisplayed(String emailAddress) {
    logger.info("Checking customer email address [{}] is displayed", emailAddress);
    customerEmailAddress.format(emailAddress);
    return WebHelper.isDisplayed(customerEmailAddress);
  }

  /**
   * Click on the Log in link
   *
   * @return SignInPage
   */
  public SignInPage clickLogin() {
    logger.info("Clicking on the Log in link");
    loginLink.click();
    return SdkHelper.create(SignInPage.class);
  }

  /**
   * Click on the Logout in link
   *
   * @return HomePage
   */
  public HomePage clickLogout() {
    logger.info("Clicking on the Logout in link");
    logOutLink.click();
    return SdkHelper.create(HomePage.class);
  }

  /** @return products names */
  public List<String> getProductsNames() {
    WebHelper.waitForElementToAppear(orderSummarySection, 10);
    productsNames.initialize();
    return productsNames.stream().map(Text::getText).collect(Collectors.toList());
  }

  /**
   * Verify user is logged in
   *
   * @return boolean
   */
  public boolean isUserLoggedIn() {
    logger.info("Checking user is logged in");
    return WebHelper.isDisplayed(logOutLink, 10);
  }
}

class CheckOutPageMobile extends CheckOutPage {

  @Locate(xpath = "//button[contains(@class, 'order-summary-toggle')]", on = Platform.WEB)
  private Button showOrderSummaryButton;

  @Override
  @Step("Apply discount code")
  public CheckOutPage applyDiscountCode(String code) {
    logger.info("Clicking on 'Show Order Summary' button");
    showOrderSummaryButton.click();

    logger.info("Typing [{}] Discount Code", code);
    discountCodeField.click();
    discountCodeField.clearText();
    discountCodeField.sendKeys(code);

    logger.info("Clicking on 'Apply' button");
    applyCodeButton.click();
    return SdkHelper.create(CheckOutPage.class);
  }
}
