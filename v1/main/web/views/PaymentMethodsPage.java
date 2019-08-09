package com.applause.auto.web.views;

import java.lang.invoke.MethodHandles;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.web.helpers.WebHelper;

@WebDesktopImplementation(PaymentMethodsPage.class)
@WebTabletImplementation(PaymentMethodsPage.class)
@WebPhoneImplementation(PaymentMethodsPage.class)
public class PaymentMethodsPage extends AbstractPage {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		WebHelper.waitForDocument();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	// Public actions

	/**
	 * Verify Add Credit Card Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddCreditCardButtonDisplayed() {
		LOGGER.info("Verifying Add Credit Card button is displayed");
		return getAddACreditCardButton().isDisplayed();
	}

	/**
	 * Verify Add Paypal Account Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddPaypalAccountButtonDisplayed() {
		LOGGER.info("Verifying Add Paypal Account button is displayed");
		return getAddAPaypalAccountButton().isDisplayed();
	}

	/**
	 * Verify Add Peets Card Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isAddPeetsCardButtonDisplayed() {
		LOGGER.info("Verifying Add Peets Card button is displayed");
		return getAddPeetsCardButton().isDisplayed();
	}

	/**
	 * Verify Credit Card is Displayed
	 *
	 * @return boolean
	 */
	public boolean isCreditCardDisplayed() {
		LOGGER.info("Verifying Credit Card is displayed");
		return getCreditCardBlock().isDisplayed();
	}

	/**
	 * Verify Peets Card is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPeetsCardDisplayed() {
		LOGGER.info("Verifying Peets Card is displayed");
		return getPeetsCardBlock().isDisplayed();
	}

	/**
	 * Click Edit Credit Card
	 *
	 * @return EditPaymentMethodPage
	 */
	public EditPaymentMethodPage clickEditCreditCard() {
		LOGGER.info("Clicking Edit Credit Card");
		getEditCreditCardButton().click();
		return PageFactory.create(EditPaymentMethodPage.class);
	}

	/**
	 * Get Name on Credit Card
	 *
	 * @return String
	 */
	public String getNameOnCreditCard() {
		LOGGER.info("Getting Name on Credit Card");
		return getNameOnCreditCardText().getStringValue();
	}

	/**
	 * Click Delete Credit Card
	 */
	public void clickDeletePeetsCard() {
		LOGGER.info("Clicking Delete Credit Card");
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		jse.executeScript("scroll(0,250)", "");
		getDeletePeetsCardButton().click();
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}

	/**
	 * Verify Peets Card Deletion Text
	 *
	 * @return boolean
	 */
	public boolean didPeetsCardDelete() {
		LOGGER.info("Verifying Peets Card was removed");
		return getSuccessfulDeleteText().isDisplayed();
	}

	/**
	 * Click Add New Peets Card
	 *
	 * @return AssociateNewCardPage
	 */
	public AssociateNewCardPage addPeetsCard() {
		LOGGER.info("Clicking Add New Peets Card");
		getAddPeetsCardButton().click();
		return PageFactory.create(AssociateNewCardPage.class);
	}

	// Protected getters
	@WebElementLocator(webDesktop = "ol.payment-cards-list")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "ol.payment-cards-list")
	protected Text getCreditCardBlock() {
		return new Text(this, getLocator(this, "getCreditCardBlock"));
	}

	@WebElementLocator(webDesktop = "div.main-section li.peets-account-block:nth-child(1) p:nth-child(4)")
	protected Text getNameOnCreditCardText() {
		return new Text(this, getLocator(this, "getNameOnCreditCardText"));
	}

	@WebElementLocator(webDesktop = "div.main-section li.peets-account-block:nth-child(2) a.link-remove")
	protected Button getDeletePeetsCardButton() {
		return new Button(this, getLocator(this, "getDeletePeetsCardButton"));
	}

	@WebElementLocator(webDesktop = "div.main-section li.peets-account-block:nth-child(2) div.highlighted")
	protected Text getPeetsCardBlock() {
		return new Text(this, getLocator(this, "getPeetsCardBlock"));
	}

	@WebElementLocator(webDesktop = "//a[text()='Add a credit card']")
	protected Button getAddACreditCardButton() {
		return new Button(this, getLocator(this, "getAddACreditCardButton"));
	}

	@WebElementLocator(webDesktop = "//a[text()='Add a PayPal account']")
	protected Button getAddAPaypalAccountButton() {
		return new Button(this, getLocator(this, "getAddAPaypalAccountButton"));
	}

	@WebElementLocator(webDesktop = "div.col-main > div > div.payment-card-holder.account-container > div.title > div > a:nth-child(3)")
	protected Button getAddPeetsCardButton() {
		return new Button(this, getLocator(this, "getAddPeetsCardButton"));
	}

	@WebElementLocator(webDesktop = "div.payment-card-holder.account-container > div.main-section > ol > li:nth-child(1) > div.actions-col > ul > li:nth-child(1) > a")
	protected Button getEditCreditCardButton() {
		return new Button(this, getLocator(this, "getEditCreditCardButton"));
	}

	@WebElementLocator(webDesktop = "div.main-container.col2-left-layout > div > div.col-main > div > div.payment-card-holder.account-container > ul > li > ul > li")
	protected Text getSuccessfulDeleteText() {
		return new Text(this, getLocator(this, "getSuccessfulDeleteText"));
	}
}
