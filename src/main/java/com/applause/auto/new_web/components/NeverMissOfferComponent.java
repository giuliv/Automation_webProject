package com.applause.auto.new_web.components;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.SignUpPage;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

public class NeverMissOfferComponent extends BaseComponent {

  @Locate(css = ".footer__newsletter", on = Platform.WEB)
  protected ContainerElement mainContainer;

  @Locate(css = "h4.footer__newsletter-hdg", on = Platform.WEB)
  protected Text neverMissOfferHeader;

  @Locate(css = "p.footer__newsletter-copy", on = Platform.WEB)
  protected Text neverMissOfferDescription;

  @Locate(css = "li.form-item.footer__newsletter-item a", on = Platform.WEB)
  protected Button neverMissOfferSignUpButton;

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.waitForElementToAppear(mainContainer);
    WebHelper.scrollToElement(mainContainer);
  }

  @Step("Get never miss an offer title")
  public String getNeverMissOfferTitle() {
    logger.info("Getting never miss an offer title");
    SdkHelper.getSyncHelper().wait(Until.uiElement(neverMissOfferHeader).clickable());
    logger.info("------ got " + neverMissOfferHeader.getText().trim());
    return neverMissOfferHeader.getText().trim();
  }

  @Step("Get never miss an offer description")
  public String getNeverMissOfferDescription() {
    logger.info("Getting never miss an offer description");
    SdkHelper.getSyncHelper().wait(Until.uiElement(neverMissOfferDescription).clickable());
    logger.info("------ got " + neverMissOfferDescription.getText().trim());
    return neverMissOfferDescription.getText().trim();
  }

  @Step("Click sign up button in never miss offer section")
  public SignUpPage clickNeverMissOfferSignup() {
    logger.info("Clicking sign up button in never miss offer section");
    SdkHelper.getSyncHelper().wait(Until.uiElement(neverMissOfferSignUpButton).clickable()).click();
    return SdkHelper.create(SignUpPage.class);
  }
}
