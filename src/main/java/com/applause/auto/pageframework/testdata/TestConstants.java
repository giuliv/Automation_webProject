package com.applause.auto.pageframework.testdata;

public class TestConstants {

	/**
	 * Test Groups
	 */
	public static final class TestNGGroups {

		// Web
		public static final String DEBUG = "debug";
		public static final String LOGIN = "login";
		public static final String GUEST_CHECKOUT = "guest-checkout";
		public static final String MINI_CART = "mini-cart";
		public static final String CART = "shopping-cart";
		public static final String MY_ACCOUNT = "my-account";
		public static final String EXISTING_USER_CHECKOUT = "existing-user-checkout";
		public static final String SUBSCRIPTIONS = "subscriptions";

		// Mobile
		public static final String ONBOARDING = "onboarding";
	}

	/**
	 * Test Data for tests
	 */
	public static final class TestData {

		public static final String LANDING_PAGE_URL = "https://uat.aws.peets.com/";
		public static final String SHOP_TEA_PAGE_URL = "https://uat.aws.peets.com/mighty-leaf-tea";
		public static final String SHOP_EQUIPMENT_PAGE_URL = "https://uat.aws.peets.com/equipment";
		public static final String SHOP_PEETS_CARD_PAGE_URL = "https://uat.aws.peets.com/peets-card";
		public static final String SHOP_COFFEE_KCUPS_PAGE_URL = "https://uat.aws.peets.com/coffee/k-cups";

		public static final String USERNAME = "appautosvc+peetscoffeealternate@applause.com";
		public static final String USERNAME_SAFARI = "appautosvc+peetscoffeesafari@applause.com";
		public static final String PASSWORD = "password123";

		public static final String COFFEE_BRAND_NAME = "House Blend";
		public static final String GRIND = "Commercial Brewer";
		public static final String GRIND_2 = "Drip";

		public static final String TEA_NAME = "Chamomile Citrus Loose Leaf";

		public static final String EQUIPMENT_NAME = "Origin Mug with Bamboo";

		public static final String COFFEE_KCUP_NAME = "Colombia Luminosa";
		public static final String COFFEE_ARABIAN_MOCHA_JAVA_NAME = "Arabian Mocha-Java";
		public static final String COFFEE_KCUP_COUNT = "16 count";

		public static final String GIFT_MESSAGE = "This is a test message for the gift-message field";

		public static final String SHIPPING_METHOD_GROUND = "Ground";
		public static final String SHIPPING_METHOD_AIR_2ND_DAY = "2nd Day Air - Cont. U.S. (2-3 business days)";

		public static final String FIRST_NAME = "Applause";
		public static final String LAST_NAME = "QA Test";
		public static final String PHONE = "646-759-4933";
		public static final String ADDRESS = "133 Water St";
		public static final String ADDRESS_2 = "2266 Bath Ave";
		public static final String ZIP_CODE = "11201";
		public static final String ZIP_CODE_2 = "11214";
		public static final String CITY = "Brooklyn";
		public static final String STATE = "New York";
		public static final String EMAIL = "peets+%s@qa.utest.com";

		public static final String PEETS_CARD_NUMBER = "81001000000583";
		public static final String PEETS_CARD_PIN = "7038";
		public static final String PEETS_CARD_NUMBER_2 = "81001000000748";
		public static final String PEETS_CARD_PIN_2 = "1342";

		public static final String PEETS_CARD_LOWEST_AMOUNT = "1";
		public static final String PEETS_CARD_BUY_AMOUNT = "Card +$10.00";

		public static final String PEETS_CARD_NUMBER_CHROME_1 = "81001000000581";
		public static final String PEETS_CARD_PIN_CHROME_1 = "9967";
		public static final String PEETS_CARD_NUMBER_CHROME_2 = "81001000000582";
		public static final String PEETS_CARD_PIN_CHROME_2 = "3839";

		public static final String PEETS_CARD_NUMBER_SAFARI_1 = "81001000000747";
		public static final String PEETS_CARD_PIN_SAFARI_1 = "3396";
		public static final String PEETS_CARD_NUMBER_SAFARI_2 = "81001000000584";
		public static final String PEETS_CARD_PIN_SAFARI_2 = "8240";

		public static final String VISA_CC_NUMBER = "4111111111111111";
		public static final String VISA_CC_SECURITY_CODE = "111";
		public static final String VISA_CC_MONTH = "12";
		public static final String VISA_CC_YEAR = "2020";
		public static final String VISA_CC_NAME = "QA Test Applause Auto";
		public static final String VISA_CC_ZIP = "90404";

		public static final String TOUR_SEARCH_TERMS = "Tour";
		public static final String WEDNES_ROAST_SEARCH = "Kona";

		public static final String PURCHASE_CONFIRMATION_TEXT = "Thank you for your purchase!";

		public static final String PAYPAL_EMAIL = "sraju@peets.com";
		public static final String PAYPAL_PASSWORD = "123456789";

	}

	public static final class TestMainMenu {

		public static final String NAV_CATEGORY_SHOP = "Shop";

		public static final String NAV_SUBMENU_COFFEE = "Coffee";
		public static final String NAV_SUBMENU_TEA = "Tea";
		public static final String NAV_SUBMENU_EQUIPMENT = "Equipment";
		public static final String NAV_SUBMENU_GIFT_SUBSCRIPTIONS = "Gift Subscriptions";

		public static final String NAV_OPTION_CARDS_BY_MAIL = "Gift Cards by Mail";

	}

	public static final class MobileApp {

		public static final String IOS_BUNDLE_ID = "com.wearehathway.peets-dev";

	}

	public static final class MyAccountTestData {

		public static final String EMAIL = "peets+testaccount@qa.utest.com";
		public static final String SAFARI_SHIPPING_EMAIL = "peets+safarishipping@qa.utest.com";
		public static final String SAFARI_BILLING_EMAIL = "peets+safaribilling@qa.utest.com";
		public static final String SAFARI_ACCOUNT_EMAIL = "peets+safariaccountsettings@qa.utest.com";
		public static final String MODIFY_ACCOUNT_EMAIL = "peets+modifyaccount@qa.utest.com";
		public static final String UNUSED_EMAIL = "peets+unused@qa.utest.com";
		public static final String PASSWORD = "p@ssword123";
		public static final String ADDRESS_LINE_2 = "APT. 123";
		public static final String FIRST_NAME = "UTest";
		public static final String LAST_NAME = "QA Test";
	}

	public enum SubscriptionTerm {
		TWO_WEEKS("Every 2 Weeks", "2 weeks");

		public final String miniCartSpell;
		public final String fullCartSpell;

		SubscriptionTerm(String miniCartSpell, String fullCartSpell) {
			this.miniCartSpell = miniCartSpell;
			this.fullCartSpell = fullCartSpell;
		}
	}

}