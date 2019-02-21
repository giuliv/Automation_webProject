package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.BaseHtmlElement;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.EditField;
import com.applause.auto.framework.pageframework.webcontrols.Image;
import com.applause.auto.framework.pageframework.webcontrols.Link;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.helpers.WebHelper;

@WebDesktopImplementation(MyAccountPeetsCardPage.class)
@WebTabletImplementation(MyAccountPeetsCardPage.class)
@WebPhoneImplementation(MyAccountPeetsCardPage.class)
public class MyAccountPeetsCardPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Buy Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardSectionDisplayed() {
		LOGGER.info("Verifying Buy Peets Card button is displayed");
		return getBuyPeetsCardSection().isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Description is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardDescriptionDisplayed() {
		LOGGER.info("Verifying Buy Peets Card Description is displayed");
		return getBuyCardDescriptionText().isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardLinkDisplayed() {
		LOGGER.info("Verifying Buy Peets Card link is displayed");
		return getBuyCardLink().isDisplayed();
	}

	/**
	 * Verify Buy Peet's Card Image is Displayed
	 *
	 * @return boolean
	 */
	public boolean isBuyPeetsCardImageDisplayed() {
		LOGGER.info("Verifying Buy Peets Card image is displayed");
		return getBuyCardImage().isDisplayed();
	}

	/**
	 * Verify Check Balance Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceSectionDisplayed() {
		LOGGER.info("Verifying Check Balance section is displayed");
		return getCheckBalanceSection().isDisplayed();
	}

	/**
	 * Verify Check Balance Card Number is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceCardNumberDisplayed() {
		LOGGER.info("Verifying Check Balance Card Number is displayed");
		return getCheckBalanceCardEditField().isDisplayed();
	}

	/**
	 * Verify Check Balance Pin Number is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalancePinNumberDisplayed() {
		LOGGER.info("Verifying Check Balance Pin Number is displayed");
		return getCheckBalancePinEditField().isDisplayed();
	}

	/**
	 * Verify Check Balance Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCheckBalanceButtonDisplayed() {
		LOGGER.info("Verifying Check Balance Button is displayed");
		return getCheckBalanceButton().isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Section is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardSectionDisplayed() {
		LOGGER.info("Verifying Register Peets Card button is displayed");
		return getRegisterPeetsCardSection().isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Description is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardDescriptionDisplayed() {
		LOGGER.info("Verifying Register Peets Card Description is displayed");
		return getRegisterCardDescriptionText().isDisplayed();
	}

	/**
	 * Verify Register Peet's Card Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isRegisterPeetsCardLinkDisplayed() {
		LOGGER.info("Verifying Register Peets Card link is displayed");
		return getRegisterCardLink().isDisplayed();
	}

	/**
	 * Verify FAQ Link is Displayed
	 *
	 * @return boolean
	 */
	public boolean isFAQLinkDisplayed() {
		LOGGER.info("Verifying FAQ link is displayed");
		return getFAQLink().isDisplayed();
	}

	// Protected getters
	@WebElementLocator(webDesktop = "div.col-main > div > div.page-title > h1")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "//a[contains(.,'Buy a Peet's card')]")
	protected BaseHtmlElement getBuyPeetsCardSection() {
		return new BaseHtmlElement(this, getLocator(this, "getBuyPeetsCardSection"));
	}

	@WebElementLocator(webDesktop = ".item-content-column .item-content-box")
	protected Text getBuyCardDescriptionText() {
		return new Text(this, getLocator(this, "getBuyCardDescriptionText"));
	}

	@WebElementLocator(webDesktop = ".item-content-column .item-link")
	protected Link getBuyCardLink() {
		return new Link(this, getLocator(this, "getBuyCardLink"));
	}

	@WebElementLocator(webDesktop = ".item-visual-column")
	protected Image getBuyCardImage() {
		return new Image(this, getLocator(this, "getBuyCardImage"));
	}

	@WebElementLocator(webDesktop = "//li[contains(.,'Check Your Balance')]")
	protected BaseHtmlElement getCheckBalanceSection() {
		return new BaseHtmlElement(this, getLocator(this, "getCheckBalanceSection"));
	}

	@WebElementLocator(webDesktop = "#card_number")
	protected EditField getCheckBalanceCardEditField() {
		return new EditField(this, getLocator(this, "getCheckBalanceCardEditField"));
	}

	@WebElementLocator(webDesktop = "#card_pin")
	protected EditField getCheckBalancePinEditField() {
		return new EditField(this, getLocator(this, "getCheckBalancePinEditField"));
	}

	@WebElementLocator(webDesktop = "#check-balance")
	protected Button getCheckBalanceButton() {
		return new Button(this, getLocator(this, "getCheckBalanceButton"));
	}

	@WebElementLocator(webDesktop = "//li[contains(.,\"Register a Peet's Card\")]")
	protected BaseHtmlElement getRegisterPeetsCardSection() {
		return new BaseHtmlElement(this, getLocator(this, "getRegisterPeetsCardSection"));
	}

	@WebElementLocator(webDesktop = ".quarter .item-content-box")
	protected Text getRegisterCardDescriptionText() {
		return new Text(this, getLocator(this, "getRegisterCardDescriptionText"));
	}

	@WebElementLocator(webDesktop = ".quarter .item-link")
	protected Link getRegisterCardLink() {
		return new Link(this, getLocator(this, "getRegisterCardLink"));
	}

	@WebElementLocator(webDesktop = ".buttons-set-link")
	protected Link getFAQLink() {
		return new Link(this, getLocator(this, "getFAQLink"));
	}

}
