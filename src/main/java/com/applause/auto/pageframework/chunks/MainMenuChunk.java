package com.applause.auto.pageframework.chunks;

import java.lang.invoke.MethodHandles;

import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.logger.LogController;
import com.applause.auto.framework.pageframework.web.AbstractPageChunk;
import com.applause.auto.framework.pageframework.web.ChunkFactory;
import com.applause.auto.framework.pageframework.web.PageFactory;
import com.applause.auto.framework.pageframework.web.WebElementLocator;
import com.applause.auto.framework.pageframework.web.factory.WebDesktopImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebPhoneImplementation;
import com.applause.auto.framework.pageframework.web.factory.WebTabletImplementation;
import com.applause.auto.framework.pageframework.webcontrols.Button;
import com.applause.auto.framework.pageframework.webcontrols.Text;
import com.applause.auto.pageframework.pages.LandingPage;
import com.applause.auto.pageframework.pages.PeetsCardProductPage;
import com.applause.auto.pageframework.pages.ShopEquipmentPage;
import com.applause.auto.pageframework.pages.ShopGiftSubscriptionsPage;
import com.applause.auto.pageframework.pages.ShopTeaPage;
import com.applause.auto.pageframework.testdata.TestConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;

@WebDesktopImplementation(MainMenuChunk.class)
@WebTabletImplementation(MainMenuChunk.class)
@WebPhoneImplementation(MainMenuChunk.class)
public class MainMenuChunk extends AbstractPageChunk {
	protected final static LogController LOGGER = new LogController(MethodHandles.lookup().getClass());

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MainMenuChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	@Override
	protected void waitUntilVisible() {
		syncHelper.waitForElementToAppear(getViewSignature());
	}

	/*
	 * Public actions
	 */

	/**
	 * Hover over main menu category
	 *
	 */
	public void hoverCategory(String category) {
		LOGGER.info("Hover a category in the main menu");
//		WebElement element = getMainMenuCategoryButton(category).getWebElement();
		WebElement element = getMainMenuCategoryButton().getWebElement();
		getMainMenuCategoryButton().hover();
//		Actions actions = new Actions(getDriver());
//		actions.moveToElement(element).build().perform();
//		getMainMenuCategoryButton(category).hover();
	}

	/**
	 * Click a Sub-Menu under a category
	 *
	 */
	public void clickCategorySubmenu(String category, String subMenu) {
		LOGGER.info(String.format("Accessing Sub-Menu '%s' under category '%s'", subMenu, category));
		hoverCategory(category);
		getCategorySubmenuButton(subMenu).click();
	}

	/**
	 * Click an option under a category
	 *
	 */
	public void clickCategoryOption(String category, String option) {
		LOGGER.info(String.format("Accessing option '%s' under category '%s'", option, category));
		hoverCategory(category);
		getCategoryOptionButton(option).click();
	}

	/**
	 * Access Sub-Menu Tea under Category Shop from main menu
	 *
	 */
	public ShopTeaPage accessShopTea() {
		LOGGER.info("Accessing Shop-Tea");
		clickCategorySubmenu(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP, TestConstants.TestMainMenu.NAV_SUBMENU_TEA);
		return PageFactory.create(ShopTeaPage.class);
	}

	/**
	 * Access Gift Subscriptions under Shop from Main Menu
	 *
	 * @return ShopGiftSubscriptionsPage
	 */
	public ShopGiftSubscriptionsPage accessShopGiftSubscriptions() {
		LOGGER.info("Accessing Shop Gift Subscriptions");
		hoverCategory(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP);
		getSubcategoryButton(TestConstants.TestMainMenu.NAV_SUBMENU_GIFT_SUBSCRIPTIONS).click();
		return PageFactory.create(ShopGiftSubscriptionsPage.class);
	}

	/**
	 * Access Sub-Menu Equipment under Category Shop from main menu
	 *
	 */
	public ShopEquipmentPage accessShopEquipment() {
		LOGGER.info("Accessing Shop-Equipment");
		clickCategorySubmenu(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP,
				TestConstants.TestMainMenu.NAV_SUBMENU_EQUIPMENT);
		return PageFactory.create(ShopEquipmentPage.class);
	}

	/**
	 * Click Peets Coffee logo
	 *
	 * @return LandingPage
	 */
	public LandingPage clickHeaderLogo() {
		LOGGER.info("Click Peets Coffee logo");
		getHeaderLogo().click();
		return PageFactory.create(LandingPage.class);
	}

	/**
	 * Access Sub-Menu Equipment under Category Shop from main menu
	 *
	 */
	public PeetsCardProductPage accessCardsByMail() {
		LOGGER.info("Accessing Shop-Equipment");
		clickCategorySubmenu(TestConstants.TestMainMenu.NAV_CATEGORY_SHOP,
				TestConstants.TestMainMenu.NAV_OPTION_CARDS_BY_MAIL);
		return PageFactory.create(PeetsCardProductPage.class);
	}

	/**
	 * Click Mini-Cart icon
	 *
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk clickMiniCart() {
		LOGGER.info("Click mini-cart icon");
		getHeaderMinicart().click();
		return ChunkFactory.create(MiniCartContainerChunk.class, this, "");
	}

	/*
	 * Protected Getters
	 */

	@WebElementLocator(webDesktop = ".page-header-container")
	protected Text getViewSignature() {
		return new Text(this, getLocator(this, "getViewSignature"));
	}

	@WebElementLocator(webDesktop = "#header-nav > nav > div > div.drop-holder > ul > li.wide.has-drop > a")
	protected Button getMainMenuCategoryButton() {
		return new Button(this, getLocator(this, "getMainMenuCategoryButton"));
	}

	@WebElementLocator(webDesktop = "//a[@class='drop-link' and contains(.,'%s')]")
	protected Button getCategorySubmenuButton(String subMenu) {
		return new Button(this, String.format(getLocator(this, "getCategorySubmenuButton"), subMenu));
	}

	@WebElementLocator(webDesktop = "//a[contains(.,'%s')]")
	protected Button getSubcategoryButton(String subMenu) {
		return new Button(this, String.format(getLocator(this, "getSubcategoryButton"), subMenu));
	}

	@WebElementLocator(webDesktop = ".logo")
	protected Button getHeaderLogo() {
		return new Button(this, getLocator(this, "getHeaderLogo"));
	}

	@WebElementLocator(webDesktop = "//div[@class='mobile-slide']//a[contains(.,'%s')]")
	protected Button getCategoryOptionButton(String option) {
		return new Button(this, String.format(getLocator(this, "getCategoryOptionButton"), option));
	}

	@WebElementLocator(webDesktop = "#top-cart-container")
	protected Button getHeaderMinicart() {
		return new Button(this, getLocator(this, "getHeaderMinicart"));
	}

}
