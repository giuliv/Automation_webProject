package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.ContainerElement;
import com.applause.auto.pageobjectmodel.elements.Image;
import com.applause.auto.pageobjectmodel.elements.Link;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.elements.TextBox;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountPeetsCardPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MyAccountPeetsCardPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MyAccountPeetsCardPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountPeetsCardPage extends BaseComponent {

	// Public actions

	/**
	 * Verify Buy Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardSectionDisplayed() {
		logger.info("Verifying Buy Peets Card button is displayed");
		return getBuyPeetsCardSection.isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Description is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardDescriptionDisplayed() {
		logger.info("Verifying Buy Peets Card Description is displayed");
		return getBuyCardDescriptionText.isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardLinkDisplayed() {
		logger.info("Verifying Buy Peets Card link is displayed");
		return getBuyCardLink.isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Image is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardImageDisplayed() {
		logger.info("Verifying Buy Peets Card image is displayed");
		return getBuyCardImage.isDisplayed();
	}

	/**
	 * Verify Check Balance Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceSectionDisplayed() {
		logger.info("Verifying Check Balance section is displayed");
		return getCheckBalanceSection.isDisplayed();
	}

	/**
	 * Verify Check Balance Card Number is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceCardNumberDisplayed() {
		logger.info("Verifying Check Balance Card Number is displayed");
		return getCheckBalanceCardTextBox.isDisplayed();
	}

	/**
	 * Verify Check Balance Pin Number is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalancePinNumberDisplayed() {
		logger.info("Verifying Check Balance Pin Number is displayed");
		return getCheckBalancePinTextBox.isDisplayed();
	}

	/**
	 * Verify Check Balance Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceButtonDisplayed() {
		logger.info("Verifying Check Balance Button is displayed");
		return getCheckBalanceButton.isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardSectionDisplayed() {
		logger.info("Verifying Register Peets Card button is displayed");
		return getRegisterPeetsCardSection.isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Description is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardDescriptionDisplayed() {
		logger.info("Verifying Register Peets Card Description is displayed");
		return getRegisterCardDescriptionText.isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardLinkDisplayed() {
		logger.info("Verifying Register Peets Card link is displayed");
		return getRegisterCardLink.isDisplayed();
	}

	/**
	 * Verify FAQ Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isFAQLinkDisplayed() {
		logger.info("Verifying FAQ link is displayed");
		return getFAQLink.isDisplayed();
	}

	// Protected getters
	@Locate(jQuery = "div.col-main > div > div.page-title > h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(xpath = "//*[contains(.,\"Buy a Peet's card\")]", on = Platform.WEB_DESKTOP)
	protected ContainerElement getBuyPeetsCardSection;

	@Locate(jQuery = ".item-content-column .item-content-box", on = Platform.WEB_DESKTOP)
	protected Text getBuyCardDescriptionText;

	@Locate(jQuery = ".item-content-column .item-link", on = Platform.WEB_DESKTOP)
	protected Link getBuyCardLink;

	@Locate(jQuery = ".item-visual-column", on = Platform.WEB_DESKTOP)
	protected Image getBuyCardImage;

	@Locate(xpath = "//li[contains(.,'Check Your Balance')]", on = Platform.WEB_DESKTOP)
	protected ContainerElement getCheckBalanceSection;

	@Locate(jQuery = "#card_number", on = Platform.WEB_DESKTOP)
	protected TextBox getCheckBalanceCardTextBox;

	@Locate(jQuery = "#card_pin", on = Platform.WEB_DESKTOP)
	protected TextBox getCheckBalancePinTextBox;

	@Locate(jQuery = "#check-balance", on = Platform.WEB_DESKTOP)
	protected Button getCheckBalanceButton;

	@Locate(xpath = "//li[contains(.,\"Register a Peet's Card\")]", on = Platform.WEB_DESKTOP)
	protected ContainerElement getRegisterPeetsCardSection;

	@Locate(jQuery = ".quarter .item-content-box", on = Platform.WEB_DESKTOP)
	protected Text getRegisterCardDescriptionText;

	@Locate(jQuery = ".quarter .item-link", on = Platform.WEB_DESKTOP)
	protected Link getRegisterCardLink;

	@Locate(jQuery = ".buttons-set-link", on = Platform.WEB_DESKTOP)
	protected Link getFAQLink;

}
