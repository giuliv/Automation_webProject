package com.applause.auto.pageframework.pages;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPage;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.chunks.MainMenuChunk;

@WebDesktopImplementation(LandingPage.class)
@WebTabletImplementation(LandingPage.class)
@WebPhoneImplementation(LandingPage.class)
public class LandingPage extends AbstractPage {

	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	@Override
	protected void waitUntilVisible() {
		dismissPopup();
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public Actions
	 */

	/**
	 * Taps the sign in button.
	 * 
	 * @return a Login
	 */
	public SignInPage clickSignInButton() {
		getSignInButton().click();
		LOGGER.info("Tap on Signin Button");
		return PageFactory.create(SignInPage.class);
	}

	/**
	 * Click Shop Coffee Button
	 *
	 * @return a Shop Coffee Page
	 */
	public ShopCoffeePage clickShopCoffeeButton() {
		LOGGER.info("Tap on Shop Coffee Button");
		getShopCoffeeButton().click();
		return PageFactory.create(ShopCoffeePage.class);
	}

	/**
	 * Click Shop Tea Button
	 *
	 * @return a Shop Tea Page
	 */
	public ShopTeaPage clickShopTeaButton() {
		LOGGER.info("Tap on Shop Tea Button");
		getShopTeaButton().click();
		return PageFactory.create(ShopTeaPage.class);
	}

	/**
	 * Click Shop Equipment Button
	 *
	 * @return a Shop Equipment Page
	 */
	public ShopEquipmentPage clickShopEquipmentButton() {
		LOGGER.info("Tap on Shop Equipment Button");
		getShopEquipmentButton().click();
		return PageFactory.create(ShopEquipmentPage.class);
	}

	/**
	 * Gets Main Menu
	 *
	 * @return MainMenuChunk
	 */
	public MainMenuChunk getMainMenu() {
		LOGGER.info("Getting Main Menu");
		return ChunkFactory.create(MainMenuChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = "header .logo")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = ".account-link")
	protected Button getSignInButton() {
		return new Button(this, getLocator(this, "getSignInButton"));
	}

	@WebElementLocator(webDesktop = ".close-button")
	protected Button getDismissPopupButton() {
		return new Button(this, getLocator(this, "getDismissPopupButton"));
	}

	@WebElementLocator(webDesktop = "ul.shop-list li:nth-child(1)")
	protected Button getShopCoffeeButton() {
		return new Button(this, getLocator(this, "getShopCoffeeButton"));
	}

	@WebElementLocator(webDesktop = "ul.shop-list li:nth-child(2)")
	protected Button getShopTeaButton() {
		return new Button(this, getLocator(this, "getShopTeaButton"));
	}

	@WebElementLocator(webDesktop = "ul.shop-list li:nth-child(3)")
	protected Button getShopEquipmentButton() {
		return new Button(this, getLocator(this, "getShopEquipmentButton"));
	}

	private void dismissPopup() {
		try {
			LOGGER.info("Attempting to dismiss popup");
			syncHelper.waitForElementToAppear(getLocator(this, "getDismissPopupButton"));
			getDismissPopupButton().click();
		} catch (Exception e) {
			LOGGER.info("Popup not found, moving on");
		}
	}
}
