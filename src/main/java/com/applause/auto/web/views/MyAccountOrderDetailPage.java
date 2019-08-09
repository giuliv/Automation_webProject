package com.applause.auto.web.views;

import com.applause.auto.data.enums.Platform;
import com.applause.auto.pageobjectmodel.annotation.Implementation;
import com.applause.auto.pageobjectmodel.annotation.Locate;
import com.applause.auto.pageobjectmodel.base.BaseComponent;
import com.applause.auto.pageobjectmodel.elements.Button;
import com.applause.auto.pageobjectmodel.elements.Text;
import com.applause.auto.pageobjectmodel.factory.ComponentFactory;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;

@Implementation(is = MyAccountOrderDetailPage.class, on = Platform.WEB_DESKTOP)
@Implementation(is = MyAccountOrderDetailPage.class, on = Platform.WEB_MOBILE_TABLET)
@Implementation(is = MyAccountOrderDetailPage.class, on = Platform.WEB_MOBILE_PHONE)
public class MyAccountOrderDetailPage extends BaseComponent {

	// Public actions

	/**
	 * Verify Order's Detail, product is Displayed
	 *
	 * @return boolean
	 */
	public boolean isProductDisplayed() {
		logger.info("Verifying Order's Detail, Product is displayed");
		return getDetailsProductText.isDisplayed();
	}

	/**
	 * Verify Order's Detail, Reorder Button is Displayed
	 *
	 * @return boolean
	 */
	public boolean isReorderButtonDisplayed() {
		logger.info("Verifying Order's Detail, Reorder Button is displayed");
		return getDetailsReorderButton.isDisplayed();
	}

	/**
	 * Verify Order's Detail, Shipping Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isShippingMethodDisplayed() {
		logger.info("Verifying Order's Detail, Shipping Method is displayed");
		return getDetailsShippingMethodText.isDisplayed();
	}

	/**
	 * Verify Order's Detail, Payment Method is Displayed
	 *
	 * @return boolean
	 */
	public boolean isPaymentMethodDisplayed() {
		logger.info("Verifying Order's Detail, Payment Method is displayed");
		return getDetailsPaymentMethodText.isDisplayed();
	}

	/**
	 * Click Back to My Orders
	 *
	 * @return MyAccountMyOrdersPage
	 */
	public MyAccountMyOrdersPage clickBackToMyOrders() {
		logger.info("Clicking back to my orders button");
		getBackToMyOrdersButton.focus();
		getBackToMyOrdersButton.click();
		return ComponentFactory.create(MyAccountMyOrdersPage.class);
	}

	// Protected getters
	@Locate(jQuery = "body > div.wrapper > div > div.main-container.col2-left-layout > div > div.col-main > div > div.page-title > h1", on = Platform.WEB_DESKTOP)
	protected Text getViewSignature;

	@Locate(jQuery = "h3.product-name", on = Platform.WEB_DESKTOP)
	protected Text getDetailsProductText;

	@Locate(jQuery = "a.button.link-cart", on = Platform.WEB_DESKTOP)
	protected Button getDetailsReorderButton;

	@Locate(jQuery = "div.wrapper-items > div.content-info.left > div:nth-child(2) > div > div:nth-child(2) > p", on = Platform.WEB_DESKTOP)
	protected Text getDetailsShippingMethodText;

	@Locate(jQuery = "div.wrapper-items > div.content-info.left > div:nth-child(3) > div > div:nth-child(2) > div:nth-child(2) > p", on = Platform.WEB_DESKTOP)
	protected Text getDetailsPaymentMethodText;

	@Locate(jQuery = "a.back-link", on = Platform.WEB_DESKTOP)
	protected Button getBackToMyOrdersButton;

}
