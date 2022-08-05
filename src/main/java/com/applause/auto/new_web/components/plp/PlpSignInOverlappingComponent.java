package com.applause.auto.new_web.components.plp;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;
import org.testng.asserts.SoftAssert;

@Implementation(is = PlpSignInOverlappingComponent.class, on = Platform.WEB)
@Implementation(is = PlpSignInOverlappingComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class PlpSignInOverlappingComponent extends BaseComponent {

  @Locate(xpath = "//div[contains(@class,'signin__ContentWrapper')]", on = Platform.WEB)
  private Text signInWrapper;

  @Locate(xpath = "//form[starts-with(@class,'signin__StyledSignInForm')]", on = Platform.WEB)
  private Text signInForm;

  @Locate(xpath = "//div[starts-with(@class,'signin__StyledSocialSignInForm')]", on = Platform.WEB)
  private Text signInSocialMedia;

  @Locate(xpath = "//div[starts-with(@class, 'GradientBackground-')]/p/a", on = Platform.WEB)
  private Text signUpBanner;

  @Locate(xpath = "//iframe[@title='ShopRunner Modal Container']", on = Platform.WEB)
  private ContainerElement frame;

  @Locate(css = "iframe.zoid-component-frame", on = Platform.WEB)
  private ContainerElement innerFrame;

  @Step("Get ShopRunner SignIn Modal UI elements")
  public SoftAssert validateShopRunnerSignInModalUIElements() {
    logger.info("Switching to inner frame");
    WebHelper.switchToIFrame(frame);
    WebHelper.switchToIFrame(innerFrame);
    SoftAssert softAssert = new SoftAssert();
    softAssert.assertTrue(signInWrapper.isDisplayed(), "Sign In Heading Text is not displayed");
    softAssert.assertTrue(signInForm.isDisplayed(), "Sign In Form is not displayed");
    softAssert.assertTrue(
        signInSocialMedia.isDisplayed(), "Sign In Social Media Section is not displayed");
    softAssert.assertTrue(signUpBanner.isDisplayed(), "SignUp Banner is not displayed");
    logger.info("switching back to default content");
    SdkHelper.getDriver().switchTo().defaultContent();
    return softAssert;
  }
}
