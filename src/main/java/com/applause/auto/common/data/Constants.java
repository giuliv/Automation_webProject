package com.applause.auto.common.data;

import com.applause.auto.framework.SdkHelper;
import com.applause.auto.web.helpers.WebHelper;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {

  public static final int BOTTOM_BORDER_SIZE = 150;
  public static String BROWSER_NAME = System.getProperty("browser_name", "CHROME_WINDOWS");
  public static final Logger logger = LogManager.getLogger(MethodHandles.lookup().getClass());

  /** Test Groups */
  public static final class TestNGGroups {

    // Web
    public static final String DEBUG = "debug";
    public static final String NEW_WEB_CASES = "nwc";
    public static final String PLP = "plp";
    public static final String WEB_PROD_MONITORING = "web_prod_monitoring";
    public static final String DEMO_CHECKOUT = "demo-checkout";
    public static final String LOGIN = "login";
    public static final String GUEST_CHECKOUT = "guest-checkout";
    public static final String MINI_CART = "mini-cart";
    public static final String CART = "shopping-cart";
    public static final String MY_ACCOUNT = "my-account";
    public static final String EXISTING_USER_CHECKOUT = "existing-user-checkout";
    public static final String SUBSCRIPTIONS = "subscriptions";
    public static final String ACCOUNT_SETTINGS = "account-settings";
    public static final String STANDARD = "standard";
    public static final String EDGE_CASES = "EdgeCases";
    public static final String SEARCH = "search";
    public static final String FIND_STORE = "find-store";
    public static final String GIFT_CARDS = "gift-cards";
    public static final String COFFEE_FINDER = "coffee-finder";

    // Mobile
    public static final String ONBOARDING = "onboarding";
    public static final String PEETS_CARDS = "peets-cards";
    public static final String ORDER = "order";
    public static final String COMPANY_INFORMATION = "company-information";
    public static final String REGRESSION = "regression";
    public static final String PROD_MONITORING = "prod_monitoring";
    public static final String MONITORING = "monitoring";
    public static final String WEB_UI = "web-ui";
  }

  public enum TestEnvironment {
    uat("https://uat.aws.peets.com"),
    production("https://peets.com"),
    staging(
        "https://peets-coffee-staging.myshopify.com/?ab=0&_fd=0&_sc=1&key=032a0c34f50a8e6cd446cc43177f45f6bd829fb6ea48ef59fa7533c49306e7f9");

    private final String environment;

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
    public static final String GEAR_PAGE_URL =
        LANDING_PAGE_URL.split("\\?")[0] + "/collections/all-equipment";
    public static final String PLP_URL =
        LANDING_PAGE_URL.split("\\?")[0] + "/collections/all-coffee";
    public static final String PDP_URL =
        LANDING_PAGE_URL.split("\\?")[0] + "/products/major-dickasons-blend";
    public static final String SHOP_TEA_PAGE_URL = LANDING_PAGE_URL.concat("/mighty-leaf-tea");
    public static final String SHOP_EQUIPMENT_PAGE_URL = LANDING_PAGE_URL.concat("/equipment");
    public static final String SHOP_PEETS_CARD_PAGE_URL = LANDING_PAGE_URL.concat("/peets-card");
    public static final String SHOP_COFFEE_KCUPS_PAGE_URL =
        LANDING_PAGE_URL.concat("/coffee/k-cups");
    public static final String GIFT_CARDS_PAGE_URL =
        LANDING_PAGE_URL.split("\\?")[0].concat("/pages/gift-cards");

    public static final String WEB_USERNAME = "peets.automation01@applause.com";
    public static final String WEB_PASSWORD = "Pa55word!";
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

    public static final String VISA_CC_NUMBER = "4788250000028291";
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

    public static final String MASTER_CC_NUM = "5454545454545454";
    public static final String MASTER_CC_CODE = "111";
    public static final String MASTER_CC_MONTH = "12";
    public static final String MASTER_CC_YEAR = "2020";

    public static final String TOUR_SEARCH_TERMS = "Tour";
    public static final String WEDNES_ROAST_SEARCH = "Kona";

    public static final String PURCHASE_CONFIRMATION_TEXT = "Thank you for your purchase!";

    public static final String PAYPAL_EMAIL = "sraju@peets.com";
    public static final String PAYPAL_PASSWORD = "123456789";

    public static final String BIRTHDAY_MESSAGE_ANDROID =
        "Your birthday drink is on us text does not displayed";
    public static final String BIRTHDAY_MESSAGE_IOS =
        "Intended for users 13+ years old. Plus, get a birthday drink on us!";

    public static final String SUBSCRIBING_MESSAGE =
        "Thanks for subscribing!Your unique promo code will be sent shortly.";

    public static final List<String> CHECKOUT_PAGE_VALIDATION_ERRORS =
        Arrays.asList(
            "Enter a valid email",
            "Enter a first name",
            "Enter a last name",
            "Enter an address",
            "Enter a city",
            "Enter a ZIP / postal code",
            "Enter a valid phone number");

    public static final String PAYMENT_ERROR_MESSAGE =
        "Your payment details couldn’t be verified. Check your card details and try again.";
    public static final List<String> PAYMENT_PAGE_VALIDATION_ERRORS =
        Arrays.asList(
            "Enter a valid card number",
            "Enter your name exactly as it’s written on your card",
            "Enter a valid card expiry date",
            "Enter the CVV or security code on your card");
    public static final List<String> INVALID_CC_VALIDATION_ERRORS =
        Arrays.asList("Enter a valid card number");
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
    public static final String IOS_BUNDLE_ID = "com.peets.loyalty.uat";
    public static final String IOS_SETTINGS = "com.apple.Preferences";
    public static final String ANDROID_PACKAGE_ID = "com.wearehathway.peets.development";
  }

  public static final class MyAccountTestData {
    public static UserTestData CHECKOUT_ACCOUNT =
        SdkHelper.getEnvironmentHelper().isMobileIOS()
            ? new UserTestData("peets_order_beverages_ios@gmail.com", "P@ssword1!")
            : new UserTestData("peets.auto01@gmail.com", "p4ssword!");
    public static final String EMAIL = "mp+20@peets.com";
    public static final String EMAIL_HAS_FAVORITES = "peets+testhasfavorites3@gmail.com";
    // public static final String EMAIL_FAVORITES = "peets+testfavorites@gmail.com";
    public static final String EMAIL_CHANGE_PWD = "appautosvc+peetscoffee2@applause.com";
    public static final String EMAIL_PEETS_REWARDS = "peets+rewards@gmail.com";
    public static final String EMAIL_EDIT_PROFILE = "peets+testeditprofile@gmail.com";
    public static final String EMAIL_ORDERING = "peets2+testaccount@gmail.com";
    public static final String SAFARI_SHIPPING_EMAIL = "peets+safarishipping@qa.utest.com";
    public static final String SAFARI_BILLING_EMAIL = "peets+safaribilling@qa.utest.com";
    public static final String SAFARI_ACCOUNT_EMAIL = "peets+safariaccountsettings@qa.utest.com";
    public static final String MODIFY_ACCOUNT_EMAIL = "peets+modifyaccount1@qa.utest.com";
    public static final String UNUSED_EMAIL = "peets+unused@qa.utest.com";
    public static final String PASSWORD = "abcde1";
    public static final String ADDRESS_LINE_2 = "APT. 123";
    public static final String FIRST_NAME = "UTest";
    public static final String LAST_NAME = "QA Test";
    public static final String EDIT_EMAIL = "peets.auto01@gmail.com";
    public static final String EDIT_EMAIL_PWD = "p4ssword!";
  }

  public static class CheckoutUserTestData {
    public static String USERNAME = "app1@peets.com";
    public static String USERNAME_SAFARI = "app1@peets.com";
    public static String PASSWORD = "abcde1";
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
    public static final String SAVED_PAYMENT_HEADER = "payment methods";
    public static final String SAVED_CC_NAME = "Applause Test";
    public static final String CC_NUM = "5105105105105100";
    public static final String CC_EXP_DATE = "12/21";
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

  public static final class WebTestData {
    public static final String EMAIL = "peets.webautomation01@gmail.com";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Automation";
    public static final String ADDRESS = "133 Water St";
    public static final String CITY = "Brooklyn";
    public static final String PHONE = "646-759-4933";
    public static final String COUNTRY = "United States";
    public static final String PROVINCE = "New York";
    public static final String ZIP = "11201";

    public static final String CREDIT_CARD_NUMBER_1 = "5333";
    public static final String CREDIT_CARD_NUMBER_2 = "8663";
    public static final String CREDIT_CARD_NUMBER_3 = "0222";
    public static final String CREDIT_CARD_NUMBER_4 = "6031";
    public static final String CREDIT_CARD_NAME = "Test Automation";
    public static final String CREDIT_CARD_EXPIRATION_MONTH = "12";
    public static final String CREDIT_CARD_EXPIRATION_YEAR = "2021";
    public static final String CREDIT_CARD_CVV = "010";

    public static final String CREDIT_CARD =
        CREDIT_CARD_NUMBER_1 + CREDIT_CARD_NUMBER_2 + CREDIT_CARD_NUMBER_3 + CREDIT_CARD_NUMBER_4;

    public static final String INVALID_CREDIT_CARD = "1111111111111111";

    public static final String PEETS_CARD = "81001000001599";
    public static final String PEETS_CARD_NIP = "3746";

    public static final String PROMO_CODE_FREE_SHIPPING = "FREESHIP";
    public static final String PROMO_CODE_SUBSCRIPTION_30 = "NEWSUB30";

    public static final String SEARCH_COFFEE_AGED_SUMATRA = "Aged Sumatra";
    public static final String SEARCH_COFFEE_DECAF_MAJOR = "Decaf Major";
    public static final String SEARCH_COFFEE_JR_RESERVE_BLEND = "JR RESERVE BLEND";
    public static final String SEARCH_COFFEE_BRAZILIAN_K_CUP = "SINGLE ORIGIN BRAZIL K-CUP";
    public static final String NOT_EXIST_SEARCH_TERM = "qwert";

    public static final String EMPTY_SEARCH_RESULT_MESSAGE_TEMPLATE =
        "SORRY, WE COULDN’T FIND ANYTHING MATCHING \"%s\".";

    public static final String STORES_NO_RESULTS_MESSAGE =
        "Oh no! There are no Peet’s Coffeebars in your area. But don’t worry, we’re happy to ship freshly hand-roasted beans directly to you.";
    public static final String NOT_EXIST_EMAIL = "test.applause.231@mail.com";
    public static final String PASSWORD = "password123";
    public static final String FIFTEEN_DOLLARS = "$15";
    public static final String PEETS_CARDS_NAME = "Peet's Cards";
  }

  public enum SortType {
    LOW_TO_HIGH,
    HIGH_TO_LOW
  }

  public static final class OCR {
    public static final String OCR_IMAGES_COMMON_PATH = "src/main/resources/ocr-images/";

    public static final String PRIVACY_LINK_PATH = OCR_IMAGES_COMMON_PATH + "privacy-link.png";
    public static final String TERMS_LINK_PATH = OCR_IMAGES_COMMON_PATH + "terms-link.png";
  }

  public static final class CoffeeFinderData {
    public static final String COFFEE_FINDER_TITLE = "How do you prepare your coffee?";
    public static final String WE_HAVE_FOUND_YOUR_COFFEE = "WE'VE FOUND YOUR COFFEE!";
  }

  public enum MenuOptions {
    COFFEE,
    TEA,
    GEAR,
    SUBSCRIPTION
  }

  public enum MenuSubCategories {
    COFFEE_BEST_SELLERS("coffee-best-sellers"),
    COFFEE_BEANS("coffee-beans"),
    COFFEE_K_CUPS("k-cup-pods"),
    LIMITED_COFFEE("limited-releases"),
    TEA_BEST_SELLERS("tea-best-sellers"),
    SUBSCRIPTIONS_SINGLE_ORIGIN("single-origin"),
    SUBSCRIPTIONS_SIGNATURE_BLEND("signature-blend"),
    SUBSCRIPTIONS_SMALL_BATCHES("small-batch"),
    COFFEE_FINDER("coffee-finder");

    String subCategory;

    MenuSubCategories(String subCategory) {
      this.subCategory = subCategory;
    }

    public String getMenuSubCategories() {
      return subCategory;
    }
  }

  public enum Mail {
    Mail1("peets.automation02@applause.com"),
    Mail2("peets.automation03@applause.com"),
    Mail3("peets.automation04@applause.com"),
    Mail4("peets.automation05@applause.com"),
    Mail5("peets.automation06@applause.com"),
    Mail6("peets.automation07@applause.com");

    private final String value;

    Mail(String i) {
      value = i;
    }

    public String getValue() {
      return value;
    }

    public static Mail getRandomMail() {
      Random random = new Random();
      Mail data = Mail.values()[random.nextInt(Mail.values().length)];
      logger.info("Mail Selected: " + data.toString());
      return data;
    }
  }

  public enum NavigationArrow {
    NEXT,
    PREV
  }

  @Setter
  @Getter
  @AllArgsConstructor
  public static class UserTestData {
    private String username;
    private String password;
  }

  @AllArgsConstructor
  public enum MyAccountLeftMenuOption {
    DASHBOARD("Dashboard"),
    PEETNIK_REWARDS("Peetnik Rewards"),
    MY_SUBSCRIPTIONS("My Subscriptions"),
    ORDER_HISTORY("Order History"),
    PEETS_CARDS("Peet's Cards"),
    REFERRALS("Referrals"),
    SETTINGS("Settings");

    @Getter private String value;
  }
}
