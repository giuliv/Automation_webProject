package com.applause.auto.new_web.components;

import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.new_web.helpers.WebHelper;
import com.applause.auto.new_web.views.Base;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = FooterComponent.class, on = Platform.WEB)
@Implementation(is = FooterComponent.class, on = Platform.WEB_MOBILE_PHONE)
public class FooterComponent extends BaseComponent {

  @Locate(id = "footer", on = Platform.WEB)
  private Text maneContainer;

  @Locate(css = "i.footer__company-logo", on = Platform.WEB)
  private ContainerElement companyLogo;

  @Locate(css = "p.footer__company-est", on = Platform.WEB)
  private ContainerElement companyEst;

  @Locate(xpath = "//div[h4[normalize-space()='%s']]//a[normalize-space()='%s']", on = Platform.WEB)
  private ContainerElement footerOptionLink;

  @Override
  public void afterInit() {
    super.afterInit();
    WebHelper.scrollToPageBottom();
    SdkHelper.getSyncHelper().wait(Until.uiElement(maneContainer).present());
  }

  @Step("Verify company logo is displayed")
  public boolean isCompanyLogoDisplayed() {
    return WebHelper.isDisplayed(companyLogo);
  }

  @Step("Verify company est is displayed")
  public boolean isCompanyEstDisplayed() {
    return WebHelper.isDisplayed(companyEst);
  }

  @Step("Click on the footer option")
  public <T extends Base> T clickOption(FooterOptions option) {
    logger.info("Clicking on the footer option [{}]", option.getOption());
    footerOptionLink.format(option.getCategory(), option.getOption()).initialize();
    WebHelper.scrollToElement(footerOptionLink);
    SdkHelper.getSyncHelper().wait(Until.uiElement(footerOptionLink).clickable());
    footerOptionLink.click();
    return (T) SdkHelper.create(option.getClazz());
  }

  @Step("Verify footer option is displayed")
  public boolean isOptionDisplayed(FooterOptions option) {
    logger.info(
        "Checking footer option [{}] is displayed under category [{}]",
        option.getOption(),
        option.getCategory());
    try {
      footerOptionLink.format(option.getCategory(), option.getOption()).initialize();
      return WebHelper.isDisplayed(footerOptionLink);
    } catch (Exception e) {
      logger.error("Footer option [{}] isn't displayed", option.getOption());
      return false;
    }
  }
}
