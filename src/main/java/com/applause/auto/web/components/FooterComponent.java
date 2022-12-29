package com.applause.auto.web.components;

import com.applause.auto.common.data.enums.FooterOptions;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.helpers.sync.Until;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Base;
import com.applause.auto.web.views.CommonWebPage;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import io.qameta.allure.Step;

@Implementation(is = FooterComponent.class, on = Platform.WEB)
@Implementation(is = FooterComponentMobile.class, on = Platform.WEB_MOBILE_PHONE)
public class FooterComponent extends BaseComponent {

  @Locate(id = "footer", on = Platform.WEB)
  private ContainerElement maneContainer;

  @Locate(css = "i.footer__company-logo", on = Platform.WEB)
  private ContainerElement companyLogo;

  @Locate(css = "p.footer__company-est", on = Platform.WEB)
  private ContainerElement companyEst;

  @Locate(
      xpath = "//div[h4[normalize-space()=\"%s\"]]//a[normalize-space()=\"%s\"]",
      on = Platform.WEB)
  protected ContainerElement footerOptionLink;

  @Locate(xpath = "//a[.//img[contains(@srcset,'app-store')]]", on = Platform.WEB)
  private Link appStoreLink;

  @Locate(xpath = "//a[.//img[contains(@srcset,'google-play')]]", on = Platform.WEB)
  private Link googlePlayLink;

  @Locate(css = "a[href*=\"%s\"]", on = Platform.WEB)
  private Link socialMediaLink;

  @Locate(xpath = "//div[@class='footer__sub']//li[contains(*,\"%s\")]/a", on = Platform.WEB)
  private Link endSubLink;

  @Locate(xpath = "//*[contains(text(),\"%s\")]", on = Platform.WEB)
  private Text endSubText;

  @Locate(xpath = "//p[@class='footer__sub-disclaimer']", on = Platform.WEB)
  private Text endPageDescrption;

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

  @Step("Click 'App store' link")
  public CommonWebPage clickAppStore() {
    logger.info("Clicking on the 'App Store' link");
    SdkHelper.getSyncHelper().wait(Until.uiElement(appStoreLink).present());
    WebHelper.scrollToElement(appStoreLink);
    SdkHelper.getSyncHelper().sleep(1000); // Wait for scroll ends

    WebHelper.jsClick(appStoreLink.getWebElement());
    return SdkHelper.create(CommonWebPage.class);
  }

  @Step("Click 'Google play' link")
  public CommonWebPage clickGooglePlay() {
    logger.info("Clicking on the 'Google play' link");
    WebHelper.scrollToElement(googlePlayLink);
    WebHelper.jsClick(googlePlayLink.getWebElement());
    return SdkHelper.create(CommonWebPage.class);
  }

  @Step("Click on the footer social media link")
  public <T extends Base> T clickSocialMediaLink(FooterOptions option) {
    logger.info("Clicking on the footer social media link [{}]", option.getOption());
    socialMediaLink.format(option.getOption()).initialize();
    WebHelper.scrollToElement(socialMediaLink);
    SdkHelper.getSyncHelper().wait(Until.uiElement(socialMediaLink).clickable());

    socialMediaLink.click();
    if (option.equals(option.getOption().contains("youtube"))) {
      SdkHelper.getSyncHelper().sleep(3000); // Extra wait
    }
    return (T) SdkHelper.create(option.getClazz());
  }

  @Step("Verify footer social media link is displayed")
  public boolean isSocialMediaLinkDisplayed(FooterOptions option) {
    logger.info("Checking social media link [{}] is displayed", option.getOption());
    try {
      socialMediaLink.format(option.getOption()).initialize();
      return WebHelper.isDisplayed(socialMediaLink);
    } catch (Exception e) {
      logger.error("Footer social media link [{}] isn't displayed", option.getOption());
      return false;
    }
  }

  @Step("Click on the footer end sub link")
  public <T extends Base> T clickEndSubLink(FooterOptions option) {
    logger.info("Clicking on the footer end sub link [{}]", option.getOption());
    endSubLink.format(option.getOption()).initialize();
    WebHelper.scrollToPageBottom();
    SdkHelper.getSyncHelper().wait(Until.uiElement(endSubLink).clickable());

    endSubLink.click();
    return (T) SdkHelper.create(option.getClazz());
  }

  @Step("Verify footer end sub link is displayed")
  public boolean isEndSubLinkDisplayed(FooterOptions option) {
    logger.info("Checking end sub link [{}] is displayed", option.getOption());
    try {
      endSubLink.format(option.getOption()).initialize();
      return WebHelper.isDisplayed(endSubLink);
    } catch (Exception e) {
      logger.error("Footer end sub link [{}] isn't displayed", option.getOption());
      return false;
    }
  }

  @Step("Verify footer end text is displayed")
  public boolean isFooterEndTextDisplayed(FooterOptions option) {
    logger.info("Checking footer end text [{}] is displayed", option.getOption());
    try {
      endSubText.format(option.getOption()).initialize();
      return WebHelper.isDisplayed(endSubText);
    } catch (Exception e) {
      logger.error("Footer end sub link [{}] isn't displayed", option.getOption());
      return false;
    }
  }

  @Step("Get of the End of the Page Description")
  public String getEndOfThePageDescription() {
    WebHelper.scrollToElement(endPageDescrption);
    SdkHelper.getSyncHelper().wait(Until.uiElement(endPageDescrption).visible());
    logger.info(
        "End Of the Page Description [{}] is displayed", endPageDescrption.getText().trim());
    return endPageDescrption.getText().trim();
  }
}

class FooterComponentMobile extends FooterComponent {

  @Override
  @Step("Click on the footer option")
  public <T extends Base> T clickOption(FooterOptions option) {
    logger.info("Clicking on the footer option [{}]", option.getOption());
    footerOptionLink.format(option.getCategory(), option.getOption()).initialize();
    WebHelper.scrollToElement(footerOptionLink);

    try {
      // Footer links might be covered by Help popup
      SdkHelper.getSyncHelper().wait(Until.uiElement(footerOptionLink).clickable());
      footerOptionLink.click();
    } catch (Exception e) {
      logger.error(e.getMessage());
      WebHelper.jsClick(footerOptionLink.getWebElement());
    }
    return (T) SdkHelper.create(option.getClazz());
  }
}
