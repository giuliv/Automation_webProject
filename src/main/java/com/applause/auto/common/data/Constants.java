package com.applause.auto.common.data;

import com.applause.auto.web.helpers.WebHelper;

public class Constants {

  /** Test Groups */
  public static final class TestNGGroups {

    // Web
    public static final String DEBUG = "debug";
    public static final String DEMO_CHECKOUT = "demo-checkout";
    public static final String LOGIN = "login";
    public static final String GUEST_CHECKOUT = "guest-checkout";
    public static final String MINI_CART = "mini-cart";
    public static final String CART = "shopping-cart";
    public static final String MY_ACCOUNT = "my-account";
    public static final String EXISTING_USER_CHECKOUT = "existing-user-checkout";
    public static final String SUBSCRIPTIONS = "subscriptions";
    public static final String ACCOUNT_SETTINGS = "account-settings";

    // Mobile
    public static final String ONBOARDING = "onboarding";
    public static final String PEETS_CARDS = "peets-cards";
    public static final String ORDER = "order";
  }

  public enum TestEnvironment {
    uat("https://uat.aws.peets.com"),
    production("https://peets.com");

    private String environment;

    TestEnvironment(String env) {
      this.environment = env;
    }

    public String getEnvironment() {
      return environment;
    }
  }

  /** Test Data for tests */
  public static final class TestData {
    public static final String LANDING_PAGE_URL =
        TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment();
    public static final String SHOP_TEA_PAGE_URL = LANDING_PAGE_URL.concat("/mighty-leaf-tea");
    public static final String SHOP_EQUIPMENT_PAGE_URL = LANDING_PAGE_URL.concat("/equipment");
    public static final String SHOP_PEETS_CARD_PAGE_URL = LANDING_PAGE_URL.concat("/peets-card");
    public static final String SHOP_COFFEE_KCUPS_PAGE_URL =
        LANDING_PAGE_URL.concat("/coffee/k-cups");

    public static final String USERNAME = "appautosvc+peetscoffeealternate@applause.com";
    public static final String USERNAME_625882 = "appautosvc+test625882@applause.com";
    public static final String USERNAME_SAFARI = "appautosvc+peetscoffeesafari@applause.com";
    public static final String PASSWORD = "password123";
    public static final String PEETS_USERNAME = "adavis@applausemail.com";
    public static final String PEETS_PASSWORD = "p@ssword123";

    public static final String COFFEE_BRAND_NAME = "Big Bang";
    public static final String GRIND = "Commercial Brewer";
    public static final String GRIND_2 = "Drip";

    public static final String TEA_NAME = "Organic Turmeric Ginger Tea Bags";
    public static final String TEA_COST_OVER_25_NAME = "Green Tea Tropical Tea Bags";

    public static final String EQUIPMENT_NAME = "Fellow Kettle, Silver";

    public static final String COFFEE_KCUP_NAME = "Decaf Especial K-Cup® Pack";
    public static final String COFFEE_ARABIAN_MOCHA_JAVA_NAME = "Arabian Mocha-Java";
    public static final String COFFEE_KCUP_COUNT = "10 count";

    public static final String GIFT_MESSAGE = "This is a test message for the gift-message field";

    public static final String SHIPPING_METHOD_GROUND = "Ground";
    public static final String SHIPPING_METHOD_AIR_2ND_DAY =
        "2nd Day Air - Cont. U.S. (2-3 business days)";
    public static final String SHIPPING_METHOD_SHOP_RUNNER = "Shoprunner FREE 2-Day Shipping";

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

    public static final String PEETS_CARD_LOWEST_AMOUNT = "1";
    public static final String PEETS_CARD_BUY_AMOUNT = "Card +$10.00";

    public static final String PEETS_CARD_NUMBER_CHROME = "81001000000748";
    public static final String PEETS_CARD_PIN_CHROME = "1342";

    public static final String PEETS_CARD_NUMBER_SAFARI = "81001000000747";
    public static final String PEETS_CARD_PIN_SAFARI = "3396";

    public static final String VISA_CC_NUMBER = "4788 2500 0002 8291";
    public static final String VISA_CC_SECURITY_CODE = "111";
    public static final String VISA_CC_MONTH = "12";
    public static final String VISA_CC_YEAR = "2020";
    public static final String VISA_CC_NAME = "QA Test Applause Auto";
    public static final String VISA_CC_ZIP = "66666";

    public static final String AMEX_CC_NUM = "378282246310005";
    public static final String AMEX_CC_CODE = "2222";
    public static final String AMEX_CC_MONTH = "12";
    public static final String AMEX_CC_YEAR = "2020";

    public static final String DISCOVERY_CC_NUM = "6011000995500000";
    public static final String DISCOVERY_CC_CODE = "111";
    public static final String DISCOVERY_CC_MONTH = "12";
    public static final String DISCOVERY_CC_YEAR = "2020";
    public static final String DISCOVERY_CC_ZIP = "11111";


    public static final String MASTER_CC_NUM = "5405222222222226";
    public static final String MASTER_CC_CODE = "111";
    public static final String MASTER_CC_MONTH = "12";
    public static final String MASTER_CC_YEAR = "2020";

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
    public static final String ANDROID_PACKAGE_ID = "com.wearehathway.peets.development";
  }

  public static final class MyAccountTestData {
    public static final String EMAIL = "peets+testaccount@gmail.com";
    public static final String EMAIL_PEETS_REWARDS = "peets+rewards@gmail.com";
    public static final String EMAIL_EDIT_PROFILE = "peets+testeditprofile@gmail.com";
    public static final String EMAIL_ORDERING = "peets2+testaccount@gmail.com";
    public static final String SAFARI_SHIPPING_EMAIL = "peets+safarishipping@qa.utest.com";
    public static final String SAFARI_BILLING_EMAIL = "peets+safaribilling@qa.utest.com";
    public static final String SAFARI_ACCOUNT_EMAIL = "peets+safariaccountsettings@qa.utest.com";
    public static final String MODIFY_ACCOUNT_EMAIL = "peets+modifyaccount1@qa.utest.com";
    public static final String UNUSED_EMAIL = "peets+unused@qa.utest.com";
    public static final String PASSWORD = "p@ssword123";
    public static final String ADDRESS_LINE_2 = "APT. 123";
    public static final String FIRST_NAME = "UTest";
    public static final String LAST_NAME = "QA Test";
  }

  public static final class CheckoutUserTestData {
    public static final String USERNAME = "app1@peets.com";
    public static final String USERNAME_SAFARI = "app1@peets.com";
    public static final String PASSWORD = "abcde1";
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

  public static final class MobileTestData {
    public static final String PEETS_CARD_HEADER = "Your Peet's Card";
    public static final String SAVED_PAYMENT_HEADER = "PAYMENT METHODS";
    public static final String SAVED_CC_NAME = "Applause Test";
    public static final String CC_NUM = "5105105105105100";
    public static final String CC_EXP_DATE = "12/20";
    public static final String CC_MODIFIED_EXP_DATE = "12/22";
    public static final String CC_CVV = "111";
    public static final String CC_ZIP = "66666";
    public static final String CC_VISA_NAME = "VISA TEST";
    public static final String CC_AMEX_NAME = "AMEX TEST";
    public static final String CC_MASTER_NAME = "MASTER TEST";
    public static final String CC_DISCO_NAME = "DISCO TEST";
    public static final String TRANSFER_ERROR = "One last thing";
    public static final String TRANSFER_PROCESS_ERROR =
        "Please check your card number and pin code and try again";

    public static final String INVALID_PEETS_CC_NUM_1 = "12341234123412";
    public static final String INVALID_PEETS_CC_PIN_1 = "9967";
    public static final String INVALID_PEETS_CC_PIN_2 = "1111";
    public static final String VALID_PEETS_CC_NUM_1 = "81001000000581";
    public static final String VALID_PEETS_CC_NUM_2 = "81001000000584";
  }

  public static final class OCR {
    public static final String OCR_IMAGES_COMMON_PATH = "src/main/resources/ocr-images/";

    public static final String PRIVACY_LINK_PATH = OCR_IMAGES_COMMON_PATH + "privacy-link.png";
    public static final String TERMS_LINK_PATH = OCR_IMAGES_COMMON_PATH + "terms-link.png";
  }
}
