package com.applause.auto.web.components;

import com.applause.auto.common.data.Constants;
import com.applause.auto.data.enums.Platform;
import com.applause.auto.framework.pageframework.UIData;
import com.applause.auto.framework.pageframework.util.webDrivers.BrowserType;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.util.helper.QueryHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.Landing;
import com.applause.auto.web.views.PeetsCardProductPage;
import com.applause.auto.web.views.ShopEquipmentPage;
import com.applause.auto.web.views.ShopGiftSubscriptionsPage;
import com.applause.auto.web.views.ShopTeaPage;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Implementation(is = MainMenuChunk.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MainMenuChunk.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MainMenuChunk.class, on = Platform.WEB_MOBILE_PHONE)
public class MainMenuChunk extends BaseComponent {

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            the selector of the chunk
	 */
	public MainMenuChunk(UIData parent, String selector) {
		super(parent, selector);
	}

	/*
	 * Public actions
	 */

	/**
	 * Hover over main menu category
	 *
	 */
	public void hoverCategory(String category) {
		logger.info("Hover a category in the main menu");
		WebElement element = getMainMenuCategoryButton(category).getWebElement();
		Actions actions = new Actions(getDriver());
		actions.moveToElement(element).build().perform();
	}

	/**
	 * Click a Sub-Menu under a category
	 *
	 */
	public void clickCategorySubmenu(String category, String subMenu) {
		logger.info(String.format("Accessing Sub-Menu '%s' under category '%s'", subMenu, category));
		hoverCategory(category);
		getCategorySubmenuButton(subMenu).click();
	}

	/**
	 * Click an option under a category
	 *
	 */
	public void clickCategoryOption(String category, String option) {
		logger.info(String.format("Accessing option '%s' under category '%s'", option, category));
		hoverCategory(category);
		getCategoryOptionButton(option).click();
	}

	/**
	 * Click category option t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @param category
	 *            the category
	 * @param column
	 *            the column
	 * @param option
	 *            the option
	 * @return the t
	 */
	public <T extends BaseComponent> T clickCategoryOption(Class<T> clazz, String category, String column,
			String option) {
		logger.info(String.format("Accessing category [%s] subcategory [%s] option [%s]", category, column, option));
		if (env.getBrowserType() != BrowserType.SAFARI) {
			hoverCategory(category);
			getCategoryColumnOptionButton(column, option).click();
		} else {
			getDriver().get(getCategoryColumnOptionButton(column, option).getAttributeValue("href"));
		}
		return ComponentFactory.create(clazz);
	}

	/**
	 * Access Sub-Menu Tea under Category Shop from main menu
	 *
	 */
	public ShopTeaPage accessShopTea() {
		logger.info("Accessing Shop-Tea");
		clickCategorySubmenu(Constants.TestMainMenu.NAV_CATEGORY_SHOP, Constants.TestMainMenu.NAV_SUBMENU_TEA);
		return ComponentFactory.create(ShopTeaPage.class);
	}

	/**
	 * Access Gift Subscriptions under Shop from Main Menu
	 *
	 * @return ShopGiftSubscriptionsPage
	 */
	public ShopGiftSubscriptionsPage accessShopGiftSubscriptions() {
		logger.info("Accessing Shop Gift Subscriptions");
		hoverCategory(Constants.TestMainMenu.NAV_CATEGORY_SHOP);
		getSubcategoryButton(Constants.TestMainMenu.NAV_SUBMENU_GIFT_SUBSCRIPTIONS).click();
		return ComponentFactory.create(ShopGiftSubscriptionsPage.class);
	}

	/**
	 * Access Sub-Menu Equipment under Category Shop from main menu
	 *
	 */
	public ShopEquipmentPage accessShopEquipment() {
		logger.info("Accessing Shop-Equipment");
		clickCategorySubmenu(Constants.TestMainMenu.NAV_CATEGORY_SHOP,
				Constants.TestMainMenu.NAV_SUBMENU_EQUIPMENT);
		return ComponentFactory.create(ShopEquipmentPage.class);
	}

	/**
	 * Click Peets Coffee logo
	 *
	 * @return Landing
	 */
	public Landing clickHeaderLogo() {
		logger.info("Click Peets Coffee logo");
		getHeaderLogo.click();
		return ComponentFactory.create(Landing.class);
	}

	/**
	 * Access Sub-Menu Equipment under Category Shop from main menu
	 *
	 */
	public PeetsCardProductPage accessCardsByMail() {
		logger.info("Accessing Shop-Equipment");
		clickCategorySubmenu(Constants.TestMainMenu.NAV_CATEGORY_SHOP,
				Constants.TestMainMenu.NAV_OPTION_CARDS_BY_MAIL);
		return ComponentFactory.create(PeetsCardProductPage.class);
	}

	/**
	 * Click Mini-Cart icon
	 *
	 * @return MiniCartContainerChunk
	 */
	public MiniCartContainerChunk clickMiniCart() {
		logger.info("Click mini-cart icon");
		getHeaderMinicart.click();
		return ComponentFactory.create(MiniCartContainerChunk.class, this, "");
	}

	/**
	 * Close mini cart t.
	 *
	 * @param <T>
	 *            the type parameter
	 * @param clazz
	 *            the clazz
	 * @return the t
	 */
	public <T extends BaseComponent> T closeMiniCart(Class<T> clazz) {
		logger.info("Click mini-cart icon");
		getHeaderMinicart.click();
		return ComponentFactory.create(clazz);
	}

	/*
	 * Protected Getters
	 */

	@Locate(jQuery = ".page-header-container", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(xpath = "//li[contains(.,'%s')]", on = Platform.WEB_DESKTOP)
	protected Button getMainMenuCategoryButton;

	@Locate(xpath = "//a[@class='drop-link' and contains(.,'%s')]", on = Platform.WEB_DESKTOP)
	protected Button getCategorySubmenuButton;

	@Locate(xpath = "//a[contains(.,'%s')]", on = Platform.WEB_DESKTOP)
	protected Button getSubcategoryButton;

	@Locate(jQuery = ".logo", on = Platform.WEB_DESKTOP)
	protected Button getHeaderLogo;

	@Locate(xpath = "//div[@class='mobile-slide']//a[contains(.,'%s')]", on = Platform.WEB_DESKTOP)
	protected Button getCategoryOptionButton;

	@Locate(xpath = "//li[a[@class='drop-link' and contains(text(),'%s')]]//a[contains(text(),'%s')]", on = Platform.WEB_DESKTOP)
	protected Button getCategoryColumnOptionButton;

	@Locate(jQuery = "#top-cart-container", on = Platform.WEB_DESKTOP)
	protected Button getHeaderMinicart;

	@Locate(jQuery = "span.count", on = Platform.WEB_DESKTOP)
	protected Button getCartItemsText;

	public String getCartItemsCount() {
		logger.info("Reading GrtCArtItemsCount");
		if (!QueryHelper.doesElementExist(getCartItemsText.getAbsoluteSelector())) {
			return "0";
		} else {
			return getCartItemsText.getCurrentText();
		}
	}
}
