package com.applause.auto.web.components;

import com.applause.auto.common.data.dto.MyReviewDto;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Checkbox;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import io.qameta.allure.Step;

@Implementation(is = MyReviewModalComponent.class, on = Platform.WEB)
@Implementation(is = MyReviewModalComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class MyReviewModalComponent extends BaseComponent {

  @Locate(css = "#bv-mbox-lightbox-list", on = Platform.WEB)
  private ContainerElement baseElement;

  @Locate(xpath = "//button[@name='Cancel']", on = Platform.WEB)
  private Button closeButton;

  @Locate(xpath = "//input[@id='bv-text-field-title']", on = Platform.WEB)
  private TextBox reviewTitleField;

  @Locate(xpath = "//textarea[@id='bv-textarea-field-reviewtext']", on = Platform.WEB)
  private TextBox reviewField;

  @Locate(xpath = "//input[@id='bv-text-field-usernickname']", on = Platform.WEB)
  private TextBox nicknameField;

  @Locate(
      xpath = "//input[@id='bv-email-field-hostedauthentication_authenticationemail']",
      on = Platform.WEB)
  private TextBox emailField;

  @Locate(xpath = "//input[@id='bv-checkbox-reviews-termsAndConditions']", on = Platform.WEB)
  private Checkbox iAgreeCheckBox;

  @Locate(xpath = "//button[@aria-label='Post Review']", on = Platform.WEB)
  private Button postReviewButton;

  @Locate(xpath = "//span[@id='bv-radio-rating-%s']", on = Platform.WEB)
  @Locate(
      xpath = "//span[./span[@id='bv-radio-rating-%s']]/span/span[1]",
      on = Platform.WEB_MOBILE_PHONE)
  protected Button ratingButton;

  @Override
  public void afterInit() {
    SdkHelper.getSyncHelper().wait(Until.uiElement(baseElement).visible());
  }

  @Step("Check if 'My Review' Modal is displayed")
  public boolean isDisplayed() {
    return WebHelper.isDisplayed(baseElement);
  }

  @Step("Close modal.")
  public <T extends BaseComponent> T close(Class<T> clazz) {
    logger.info("Clicking on (X) to close modal");
    closeButton.click();
    return SdkHelper.create(clazz);
  }

  @Step("Post Review")
  public YourReviewWasSubmittedComponent postReview(MyReviewDto reviewDto) {
    selectRating(reviewDto.getRating());

    logger.info("Typing [{}] review title", reviewDto.getReviewTitle());
    reviewTitleField.clearText();
    reviewTitleField.sendKeys(reviewDto.getReviewTitle());

    logger.info("Typing [{}] review", reviewDto.getReview());
    reviewField.clearText();
    reviewField.sendKeys(reviewDto.getReview());

    logger.info("Typing [{}] nickname", reviewDto.getNickName());
    nicknameField.clearText();
    nicknameField.sendKeys(reviewDto.getNickName());

    logger.info("Typing [{}] email", reviewDto.getEmail());
    emailField.clearText();
    emailField.sendKeys(reviewDto.getEmail());

    if (reviewDto.isIAgreeTermsAndConditions()) {
      logger.info("Selecting T&C checkbox");
      iAgreeCheckBox.click();
    }

    logger.info("Clicking on 'Post Review' button");
    postReviewButton.click();
    return SdkHelper.create(YourReviewWasSubmittedComponent.class);
  }

  protected void selectRating(int rating) {
    logger.info("Selecting [{}] rating", rating);
    ratingButton.format(rating).initialize();
    ratingButton.click();
  }
}

class MyReviewModalComponentMobile extends MyReviewModalComponent {

  @Override
  protected void selectRating(int rating) {
    logger.info("Selecting [{}] rating", rating);
    ratingButton.format(rating).initialize();
    WebHelper.jsClick(ratingButton.getWebElement());
  }
}
