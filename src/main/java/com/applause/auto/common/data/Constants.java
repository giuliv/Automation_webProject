package com.applause.auto.common.data;

import com.applause.auto.common.data.dto.CreditCardDto;
import com.applause.auto.common.data.dto.ShareViaEmailDto;
import com.applause.auto.framework.SdkHelper;
import com.applause.auto.mobile.helpers.MobileHelper;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.my_account.MyAccountOrderHistoryPage;
import com.applause.auto.web.views.my_account.MyAccountPage;
import com.applause.auto.web.views.my_account.MyAccountPeetnikRewardsPage;
import com.applause.auto.web.views.my_account.MyAccountSettingsPage;
import com.applause.auto.web.views.my_account.MyCardsPage;
import com.applause.auto.web.views.my_account.ReferralsPage;
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
    public static final String FRONT_END_REGRESSION = "web_regression";
    public static final String FE_PROD_REGRESSION = "fe_prod_regression";
    public static final String TO_BE_RENAMED = "TBD";
    public static final String PLP = "plp";
    public static final String WEB_PROD_MONITORING = "web_prod_monitoring";
    public static final String CART = "shopping-cart";
    public static final String MY_ACCOUNT = "my-account";
    public static final String SETTINGS = "settings";
    public static final String EXISTING_USER_CHECKOUT = "existing-user-checkout";
    public static final String SUBSCRIPTIONS = "subscriptions";
    public static final String ACCOUNT_SETTINGS = "account-settings";
    public static final String EDGE_CASES = "EdgeCases";
    public static final String SEARCH = "search";
    public static final String FIND_STORE = "find-store";
    public static final String GIFT_CARDS = "gift-cards";
    public static final String COFFEE_FINDER = "coffee-finder";
    public static final String DASHBOARD = "dashboard";
    public static final String PDP = "pdp";
    public static final String SMOKE = "smoke";
    public static final String SANITY = "sanity";
    public static final String MENU = "menu";
    public static final String IDENTITY = "identity";
    public static final String CHECKOUT = "checkout";
    public static final String HOME_PAGE = "home-page";
    public static final String WEB_CART = "cart";
    public static final String PRODUCTS = "products";
    public static final String PPD = "ppd";
    public static final String VIRTUAL_BUNDLE = "virtual-bundle";
    public static final String SHIPPING_DISCOUNT = "shipping-discount";

    // Mobile
    public static final String ONBOARDING = "onboarding";
    public static final String PEETS_CARDS = "peets-cards";
    public static final String ORDER = "order";
    public static final String COMPANY_INFORMATION = "company-information";
    public static final String REGRESSION = "regression";
    public static final String REWARDS_BANNER = "rewards_banner";
    public static final String PROD_MONITORING = "prod_monitoring";
    public static final String MONITORING = "monitoring";
    public static final String WEB_UI = "web-ui";
  }

  public enum TestEnvironment {
    uat("https://uat.aws.peets.com"),
    production("https://peets.com"),
    staging("https://peets-coffee-staging.myshopify.com");

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
    public static final String STAGING_KEY_URL_PARAMETER =
        "/?ab=0&_fd=0&_sc=1&key=032a0c34f50a8e6cd446cc43177f45f6bd829fb6ea48ef59fa7533c49306e7f9";
    public static final String BASE_URL =
        TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment();

    public static final String LANDING_PAGE_URL =
        WebHelper.isProdEnv() ? BASE_URL : BASE_URL + STAGING_KEY_URL_PARAMETER;
    public static final String GEAR_PAGE_URL = BASE_URL + "/collections/all-equipment";
    public static final String PLP_URL = BASE_URL + "/collections/all-coffee";

    public static final String PDP_URL = BASE_URL + "/products/major-dickasons-blend";
    public static final String GENERAL_PDP_URL = BASE_URL + "/products/";

    public static final String HOODIE = "peets-coffee-hoodie";
    public static final String GIFT_CARDS_PAGE_URL = BASE_URL.concat("/pages/gift-cards");
    public static final String STORE_LOCATOR_PAGE_URL = BASE_URL.concat("/pages/store-locator");
    public static final String SUBSCRIPTIONS_URL = "/pages/subscriptions";
    public static final String SUBSCRIPTION_SO_URL =
        BASE_URL.concat("/products/single-origin-series-subscription");
    public static final String SUBSCRIPTION_SB_URL =
        BASE_URL.concat("/products/signature-blend-series-subscription");
    public static final String SUBSCRIPTION_SMB_URL =
        BASE_URL.concat("/products/small-batch-series-subscription");
    public static final String ORDER_PEETS_URL = "order.peets.com";
    public static final String BLOG_PEETS_URL_PARAMETER = "/blogs/peets";
    public static final String COFFEEBAR_MENU_URL = BASE_URL.concat("/pages/menu");
    public static final String STORE_LOCATOR_URL = "pages/store-locator";
    public static final String REWARDS_URL = "pages/peetnik-rewards";
    public static final String RECENT_ORDERS_URL = "/account#/orders";
    public static final String REGISTER_CARD_URL = "/account#/peets-cards/register";
    public static final String BUY_CARD_URL = "/gift-cards";
    public static final String EMAIL_SIGNUP_URL = "pages/email-signup";
    public static final String LOGIN_PAGE_URL =
        WebHelper.isProdEnv()
            ? "https://account.peets.com/login"
            : "https://account-qa.peets.com/login";
    public static final String REGISTRATION_PAGE_URL =
        WebHelper.isProdEnv()
            ? "https://account.peets.com/Registration?target=checkout"
            : "https://account-qa.peets.com/Registration";
    public static final String COFFEE_BEST_SELLERS_URL =
        BASE_URL + "/collections/coffee-best-sellers";
    public static final String COFFEE_DARK_ROAST_URL = BASE_URL + "/collections/dark-roast";
    public static final String TEA_PLP_URL = BASE_URL + "/collections/all-tea";
    public static final String TEA_BEST_SELLERS_URL = BASE_URL + "/collections/tea-best-sellers";
    public static final String COFFEE_WITH_REVIEWS_URL_PARAMETER =
        "major-dickasons-blend-kcup-pods";
    public static final String BUNDLE_PRODUCT_URL_PARAMETER = "morning-kickstart-bundle";
    public static final String FRENCH_ROAST_URL_PARAMETER = "french-roast";
    public static final String MAJOR_DICKASONS_BLEND_KCUP_PODS = "major-dickasons-blend-kcup-pods";
    public static final String DECAF_SPECIAL_KCUP_PODS = "decaf-especial-kcup-pods";
    public static final String RISTRETTO_ESPRESSO_CAPSULES_URL_PARAMETER =
        "ristretto-espresso-capsules";
    public static final String DECAF_SUMATRA_URL_PARAMETER = "decaf-sumatra";
    public static final String AGED_SUMATRA_URL_PARAMETER = "aged-sumatra";
    public static final String SHIPPING_METHOD_GROUND = "Ground";
    public static final String SHIPPING_METHOD_GROUND_SUREPOST = "Ground-Surepost";
    public static final String SHIPPING_METHOD_THREE_DAY_SELECT = "3 Day Select";

    /** Test Data for tests */
    public static final String WEB_USERNAME_SUBSCRIPTION =
        "peets.subscribe.automation@applause.com";

    public static final String WEB_PASSWORD = "Pa55word!";
    public static final String USERNAME = "appautosvc+peetscoffeealternate@applause.com";
    public static final String USERNAME_625882 = "appautosvc+test625882@applause.com";
    public static final String USERNAME_SAFARI = "appautosvc+peetscoffeesafari@applause.com";
    public static final String PASSWORD = "password123";
    public static final String PASSWORD_BAD_FORMAT = "pass";
    public static final String PEETS_FORGOT_PASSWORD_USERNAME = "peetfp01.awkv01hh@mailosaur.io";
    public static final String PEETS_FORGOT_PASSWORD_USERNAME_PROD =
        "production.peetfp01.awkv01hh@mailosaur.io";
    public static final String PEETS_USERNAME = "adavis@applausemail.com";
    public static final String PEETS_PASSWORD = "p@ssword123";
    public static final String FROM_THE_COFFEEBAR = "FROM THE COFFEEBAR";
    public static final String FEATURED_MENU = "OUR FEATURED MENU";
    public static final String TEA_NAME = "Organic Turmeric Ginger";
    public static final String EQUIPMENT_NAME = "Fellow Kettle";
    public static final String TEST_EQUIPMENT = "TEST PEET’S PAPER FILTERS #4";
    public static final String EQUIPMENT_NAME_OOO = "T-SAC TEA FILTERS";
    public static final String PODS_OOO = "DECAF HOUSE BLEND K-CUP® PODS";
    public static final String BUNDLE_PRODUCT_NAME = "MORNING KICKSTART BUNDLE";

    public static final String UNRECOGNIZED_USERNAME_AND_PASSWORD_MESSAGE =
        "Unrecognized username and password combination";
    public static final String SHOP_RUNNER_FREE_TEXT = "Free 2-Day Shipping & Free Returns";
    public static final String SHOP_RUNNER_SIGNIN = "sign in";
    public static final String SHOP_RUNNER_LEARN_MORE = "learn more";
    public static final String PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION =
        "luminosa-breakfast-blend-gift-subscription";
    public static final String PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION =
        "luminosa-breakfast-k-cup-gift-subscription";
    public static final String GIFT_EXIST_IN_YOUR_CART_MESSAGE =
        "A gift exists in your cart. Please proceed to checkout now.";
    public static final String COFFEE_SHIPS_ONCE_PER_MONTH_MESSAGE = "Coffee ships once per month";
    public static final String PROMO_CODE_NEW_SUB_30 = "NewSub30";
    public static final String PRODUCT_LUMINOSA_BREAKFAST_BLEND_GIFT_SUBSCRIPTION_NAME =
        "luminosa breakfast blend gift subscription";
    public static final String PRODUCT_LUMINOSA_BREAKFAST_BLEND_K_CUP_PODS_GIFT_SUBSCRIPTION_NAME =
        "luminosa breakfast blend k-cup® pods gift subscription";
    public static final String BUY_NOW_BUTTON = "BUY NOW";
    public static final String ADD_TO_CART_BUTTON = "ADD TO CART";
    public static final String RECIPIENT_MESSAGE = "Applause Recipient Message";
    public static final String FIRSTSUB_DISCOUNT_CODE = "FIRSTSUB";
    public static final String USER_EMAIL_WITH_PREPAID_SUBSCRIPTIONS =
        "peets.applause.ppd.subscription@gmail.com";
    public static final String USER_EMAIL_WITH_PREPAID_AND_REGULAR_SUBSCRIPTIONS =
        "peets.applause.ppd.and.regular.subscription@gmail.com";
    public static final String USER_PASSWORD_WITH_PREPAID_SUBSCRIPTIONS = "Applause123@";
    public static final String COLLECTION_ALL_PART_URL = "collections/all";
    public static final List<String> SUBSCRIPTION_QUANTITY_OPTIONS =
        Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20");
    public static final List<String> SUBSCRIPTION_FREQUENCY_OPTIONS =
        Arrays.asList("1 week", "2 weeks", "3 weeks", "4 weeks", "5 weeks", "6 weeks", "8 weeks");

    public static final String FIRST_NAME = "Applause";
    public static final String LAST_NAME = "QA Test";
    public static final String PHONE = "646-759-4933";
    public static final String ADDRESS = "133 Water St";
    public static final String ADDRESS_2 = "2266 Bath Ave";
    public static final String ZIP_CODE = "11201";
    public static final String ZIP_CODE_2 = "11214";
    public static final String QA_LAB_ZIP = "78717";
    public static final String CITY = "Brooklyn";
    public static final String STATE = "New York";
    public static final String EMAIL = "peets+%s@qa.utest.com";

    public static final CreditCardDto VISA_CC =
        new CreditCardDto(
            WebTestData.CREDIT_CARD_NUMBER_1
                + WebTestData.CREDIT_CARD_NUMBER_2
                + WebTestData.CREDIT_CARD_NUMBER_3
                + WebTestData.CREDIT_CARD_NUMBER_4,
            WebTestData.CREDIT_CARD_NAME,
            WebTestData.CREDIT_CARD_EXPIRATION_MONTH,
            WebTestData.CREDIT_CARD_EXPIRATION_YEAR,
            WebTestData.CREDIT_CARD_CVV);

    public static final String VISA_CC_NUMBER = "4788250000028291";
    public static final String VISA_CC_SECURITY_CODE = "111";
    public static final String VISA_CC_NAME = "QA Test Applause Auto";
    public static final String VISA_CC_ZIP = "66666";
    public static final String VISA_CC_EXP_DATE = "12/28";
    public static final String VISA_NUMBER = "4111111111111111";
    public static final String VISA_SECURITY_CODE = "111";
    public static final String VISA_ZIP = "66666";
    public static final String VISA_EXP_DATE = "12/26";
    public static final String VISA_NAME = "Visa Card";
    public static final String SECONDARY_PAYPAL_EMAIL = "omstest@peets.com";
    public static final String SECONDARY_PAYPAL_PASSWORD = "Peets1966!";
    public static final String BIRTHDAY_MESSAGE_IOS =
        "Intended for users 13+ years old. Plus, get 25 bonus points on your birthday.";

    public static final String SUBSCRIBING_MESSAGE =
        "Thanks for subscribing!Your unique promo code will be sent shortly.";
    public static final String PERSONAL_MESSAGE = "Add a personal message";

    public static final String SIGN_UP_SUCCESS_MESSAGE = "Check your texts";

    public static final List<String> CHECKOUT_PAGE_VALIDATION_ERRORS =
        Arrays.asList(
            "Enter a valid email",
            "Enter a first name",
            "Enter a last name",
            "Enter an address",
            "Enter a city",
            "Enter a ZIP code",
            "Enter a phone number to use this delivery method");

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
    public static final String ANDREW_TESTER = "Andrew Tester";
    public static final String USER_EMAIL_WITH_SUBSCRIPTIONS =
        "test_automation_1637180308245@gmail.com";
    public static final String HIDDEN_CREDIT_CARD_NUMBER_TEMPLATE = "**** **** **** %s";
    public static final String RANDOM_EXPIRATION_DATE =
        String.format("12/%s", WebHelper.getRandomValueWithinRange(10, 40));

    public static final String END_OF_PAGE_DESCRIPTION =
        "*Nespresso is a registered trademark of Société des Produits Nestlé S.A., and is not affiliated with Peet’s Coffee Inc. Compatible with most Nespresso Original machines.";
    public static final String ALL_COFFEE_HOVER = "All Coffee";
    public static final String ALL_EQUIPMENT = "All Equipment";
    public static final String ALL_TEA_HOVER = "All Tea";
    public static final String TEA_JASMINE = "jasmine-downy-pearls";
    public static final String K_CUP = "SINGLE ORIGIN NICARAGUA K-CUP® PODS";
    public static final String COFFEE_HOVER_FINDER = "coffee-finder";
    public static final String TEA_HOVER_FINDER = "tea-finde";
    public static final String TEA_HOVER_MIGHTY_LEAF = "mighty-leaf";

    public static final String ALL_TEA_HEADER = "Mighty Leaf Tea";
    public static final String COFFEE_BEST_SELLERS_HEADER = "COFFEE BEST SELLERS";
    public static final String DARK_ROAST_HEADER = "DARK ROAST";
    public static final String TEA_BEST_SELLERS_HEADER = "Tea Best Sellers";

    public static final String GRIND_FIRST_TEXT_COFFEE = "Whole Bean";
    public static final String GRIND_FIRST_TEXT = "4OZ LOOSE LEAF";
    public static final String GRIND_NEXT_TEXT = "1 LB";

    public static final String PDP_HAND_ROAST_TO_ORDER = "Hand Roasted";
    public static final String PDP_SEALED_FOR_FRESHNESS = "Sealed for Freshness";
    public static final String PDP_SEALED_FOR_FRESHNESS_DESCRIPTION =
        "Sealed immediately after roast";
    public static final String PDP_DELIVERED_FRESH_TO_YOU = "Delivered Fresh To You";
    public static final String PDP_DELIVERED_FRESH_TO_YOU_DESCRIPTION =
        "Roasts and ships the same day";
    public static final String REPORTED_BUTTON = "Reported";
    public static final String MOBILE_NUMBER = "202-555-0196";

    public static final String PROD_URL = "http://www.peets.com";
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
    public static final String EMAIL = "jyothipeets+97@gmail.com";
    public static final String EMAIL_HAS_FAVORITES = "peets+testhasfavorites3@gmail.com";
    // public static final String EMAIL_FAVORITES = "peets+testfavorites@gmail.com";
    public static final String EMAIL_REWARDS_197_POINTS = "Jyothipeets+101@gmail.com";
    public static final String EMAIL_REWARDS_47_POINTS = "Jyothipeets+99@gmail.com";
    public static final String EMAIL_REWARDS_288_POINTS = "Jyothipeets+80@gmail.com";

    public static final String EMAIL_CHANGE_PWD =
        MobileHelper.isAndroid()
            ? "appautosvc+peetscoffee2@applause.com"
            : "appautosvc+peetscoffee2ios@applause.com";
    public static final String USER_WITH_PEETNIK_REWARDS =
        WebHelper.isProdEnv()
            ? "vaibhavk8999+400@gmail.com"
            : "peets_order_beverages_ios@gmail.com";
    public static final String PASSWORD_FOR_USER__WITH_PEETNIK_REWARDS =
        WebHelper.isProdEnv() ? "peets123" : "P@ssword1!";
    public static final String PASSWORD = "Peets123";
    public static final String REORDER_EMAIL = "peets.automation.reorder@applause.com";
    public static final String EDIT_EMAIL = "peets.auto01@gmail.com";
    public static final String EDIT_EMAIL_PWD = "p4ssword!";
    public static final String EDIT_EMAIL_USERNAME = "WELCOME BACK,APPLAUSE";
    public static final String ALL_COFFEE_HEADER = "ALL COFFEE";
    public static final String GEAR_HEADER = "GEAR";
    public static final String BESTSELLER_HEADER = "COFFEE BEST SELLERS";
    public static final String DARK_ROAST_HEADER = "DARK ROAST";
    public static final String TEA_HEADER = "MIGHTY LEAF TEA";
    public static final String BESTSELLER_TEA_HEADER = "TEA BEST SELLERS";
    public static final String UPDATED_PAYMENT_DATA_SUCCESSFULLY_ALERT =
        "Updated payment method successfully";

    public static final String REFERRALS_TITLE_HEADER = "Referrals";
    public static final List<String> REFERRALS_STATS =
        Arrays.asList("times shared", "possible rewards", "friends referred", "rewards earned");

    public static final ShareViaEmailDto SHARE_VIA_EMAIL_DTO =
        ShareViaEmailDto.builder()
            .email(Constants.Mail.getRandomMail().getValue())
            .subject("Applause Subject")
            .note("Applause automation note")
            .build();

    public static final String PROD_ACCOUNT_EMAIL = "applause.test.peets.prod_1@applausemail.com";
    public static final String PROD_ACCOUNT_PASSWORD = "Applause123@";
  }

  public static class CheckoutUserTestData {
    public static String USERNAME = "App1@peets.com";
    public static String USERNAME_SAFARI = "App1@peets.com";
    public static String PASSWORD = "abcde1";
  }

  public static class CheckoutPageMiscData {
    public static final String CHECKOUT_PAGE_URL_PART = "/checkouts/";
  }

  public static class CartPageMiscData {
    public static final String CART_PAGE_URL_PART = "/cart";
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

  public static final class DashboardTestData {
    public static final String RECENT_ORDERS_HEADER = "Recent Orders";
    public static final String MY_SUBSCRIPTION_HEADER = "My Subscriptions";

    public static final List<String> MY_ORDERS_TABLE_TITLES =
        Arrays.asList("order #", "date ordered", "order status", "total");
  }

  public static final class MobileTestData {
    public static final String PEETS_CARD_HEADER = "Your Peet's Card";
    public static final String SAVED_PAYMENT_HEADER = "payment methods";
    public static final String TRANSFER_PROCESS_ERROR =
        "Please check your card number and pin code and try again";

    public static final String INVALID_PEETS_CC_NUM_1 = "12341234123412";
    public static final String INVALID_PEETS_CC_PIN_1 = "9967";
    public static final String INVALID_PEETS_CC_PIN_2 = "1111";
    public static final String VALID_PEETS_CC_NUM_1 = "81001000000581";
    public static final String VALID_PEETS_CC_NUM_2 = "81001000000584";

    public static final String ACCOUNT_ALREADY_EXIST_MESSAGE =
        "Account already exists. If you have a Peets.com account, use those credentials to sign in.";

    public static final List<Integer> RED_COLORS =
        Arrays.asList(99, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201);

    public static final String ALLOW_LOCATION_SERVICE_MESSAGE =
        "Allow location services to help you find nearby Peet's Coffeebars and to place delivery orders";
  }

  public static final class WebTestData {
    public static final String EMAIL = "peets.webautomation01@gmail.com";
    public static final String RECOVER_EMAIL = "peets.automation16@applause.com";
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "Automation";
    public static final String ADDRESS = "133 Water St";
    public static final String EXTRA_INFO = "Ste 1";
    public static final String CITY = "Brooklyn";
    public static final String PHONE = "646-759-4933";
    public static final String COUNTRY = "United States";
    public static final String PROVINCE = "New York";
    public static final String ZIP = "11201";
    public static final String PERSONALIZED_MESSAGE = "Test message";

    public static final String CREDIT_CARD_NUMBER_1 = "5333";
    public static final String CREDIT_CARD_NUMBER_2 = "8663";
    public static final String CREDIT_CARD_NUMBER_3 = "0222";
    public static final String CREDIT_CARD_NUMBER_4 = "6031";
    public static final String CREDIT_CARD_NAME = "Test Automation";
    public static final String CREDIT_CARD_EXPIRATION_MONTH = "12";
    public static final String CREDIT_CARD_EXPIRATION_YEAR = "2024";
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
    public static final String SEARCH_COFFEE_YOSEMITE_DOS_SIERRAS_ORGANIC =
        "YOSEMITE DOS SIERRAS ORGANIC";

    public static final String EMPTY_SEARCH_RESULT_MESSAGE_TEMPLATE =
        "SORRY, WE COULDN’T FIND ANYTHING MATCHING \"%s\".";

    public static final String STORES_NO_RESULTS_MESSAGE =
        "Oh no! There are no Peet’s Coffeebars in your area. But don’t worry, we’re happy to ship freshly hand-roasted beans directly to you.";
    public static final String NOT_EXIST_EMAIL = "test.applause.231@mail.com";
    public static final String PASSWORD = "password123";
    public static final String FIFTEEN_DOLLARS = "$15";
    public static final String PEETS_CARDS_NAME = "Peet's Cards";
    public static final String EMPTY_CART_MESSAGE =
        "Your cart is empty. Do you need any beans or tea?";
    public static final List<String> PLP_SHOPABBLE_ITEMS =
        Arrays.asList("ALL COFFEE", "MIGHTY LEAF TEA", "GEAR");
    public static final String HOME_DELIVERY_TITLE = "FREE HOME DELIVERY";
    public static final String DARK_ROAST_BREADCRUMBS = "ALL COFFEE / DARK ROAST";
    public static final String FAQ_MESSAGE =
        "Estimated ship date based on items in your cart. Ship dates subject to change. Visit our FAQ page to learn more.";
    public static final String SHIP_ON_MESSAGE = "Ships on or before";

    public static final String PDP_SECTION_GREEN_COLOR = "rgba(31, 59, 39, 1)";
    public static final String PDP_SECTION_ANNIVERSARY_PRODUCT_COLOR = "rgba(32, 26, 23, 1)";
    public static final List<String> AMENITIES_LIST =
        Arrays.asList(
            "Contactless Payments",
            "Accepts Peet's Cards",
            "Participates in Peetnik Rewards",
            "Order Ahead",
            "Warm Breakfast",
            "Free Wi-Fi",
            "Pick Up In Store",
            "Delivery");

    public static final String INVALID_EMAIL = "webautomation01";
  }

  @AllArgsConstructor
  public enum SortType {
    RECOMMENDED("Recommended", "Sort: Recommended", "Sort: Recommended"),
    PRICE_LOW_TO_HIGH("Price: Low to High", "Price: Low to High", "Price: Low to High"),
    PRICE_HIGH_TO_LOW("Price: High to Low", "Price: High to Low", "Price: High to Low"),
    NAME_A_Z("Name (A - Z)", "Name: A - Z", "Name (A - Z)"),
    NAME_Z_A("Name (Z - A)", "Name: Z - A", "Name (A - Z)"),
    NEWEST("Newest", "Newest", "Newest");
    @Getter String name;
    @Getter String mobileName;
    @Getter String prodMobileName;

    public String getOptionName() {
      if (WebHelper.getTestEnvironment().equalsIgnoreCase("production") && !WebHelper.isDesktop()) {
        return getProdMobileName();
      }
      return WebHelper.isMobile() ? getMobileName() : getName();
    }
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

  public static final class PDPTestData {
    public static final String SUBSCRIBE_INFO_TOOLTIP =
        "Subscribe and get free shipping on every order. Plus, get 5% off orders of $30+ and 10% off orders of $50+. Pause, skip, or cancel anytime. To learn more, visit our";
  }

  public static class HomepageCarouselData {
    public static String CAROUSEL_LINK = "products/guatemala-huehuetenango";
  }

  public static class HomepagePromoTileData {
    public static String COFFEE_PROMO_LINK = "/collections/all-coffee";
    public static String COFFEE_PROMO_DESCRIPTION =
        "Hand roasted on demand and delivered fresh to your door.";
    public static String TEA_PROMO_LINK = "/collections/all-tea";
    public static String TEA_PROMO_DESCRIPTION =
        "Savor Mighty Leaf’s tea collection: Different from the start.";
  }

  public static class HomepageBestSellersData {
    public static String BEST_SELLERS_LINK = "/collections/coffee-best-sellers";
  }

  public static class HomepageNeverMissOffer {
    public static String OFFER_TITLE = "NEVER MISS AN OFFER";
    public static String OFFER_DESCRIPTION =
        "Sign up for our newsletter and receive 10% off + free shipping on your first order.";
    public static String OFFER_URL_PART = "/pages/email-signup";
  }

  public static class HomepageCovidResponse {
    public static String HEADER = "How We are Responding to COVID-19";
    public static String DESCRIPTION =
        "The health and safety of our employees and customers is important to all of us at Peet's Coffee.";
    public static String LEARN_MORE_URL_PART = "/pages/covid-19";
    public static String STORE_HOURS_TEXT = "View Store Hours";
    public static String STORE_HOURS_LINK = "https://order.peets.com";
  }

  public static class HomepageCoffeeRevolution {
    public static String HEADER = "OUR COFFEE REVOLUTION";
    public static String DESCRIPTION =
        "When Alfred Peet opened the doors to his first coffeebar in 1966, he forever changed the expectations of American coffee drinkers.";
    public static String LEARN_MORE_URL_PART = "/pages/timeline";
  }

  public static class BundleIds {
    public static String SAFARI = "com.apple.mobilesafari";
    public static String APP_STORE = "com.apple.AppStore";
  }

  public static class HomepageSubscriptionsModule {
    public static String HEADER = "30% OFF FOR NEW SUBSCRIBERS";
    public static String DESCRIPTION =
        "A Peet's subscription gives you free shipping and access to discounts on all of your coffee purchases. Plus, new subscribers get 30% off their first order using code NEWSUB30.";
    public static String COPY_BUTTON_CLICKED_TEXT = "copied";
    public static String DISCOUNT_CODE = "NEWSUB30";
  }

  public static class HomepageCoffeeBar {
    public static String ORDER_NOW_URL = "https://pickup.peets.com/";
    public static String SEASONAL_URL_PART = "/collections/seasonal";
    public static String FIND_COFFEE_BAR = "/pages/store-locator";
  }

  public static class MiniCartMiscTestData {
    public static String SHOPRUNNER_MESSAGE = "free 2-day shipping & free returns";
  }

  public enum MenuOptions {
    COFFEE,
    TEA,
    GEAR,
    SUBSCRIPTION,
    COFFEE_BARS,
    LEARN,
    PEETNIK_REWARDS,
    FREE_HOME_DELIVERY,
    OFFERS
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
    COFFEE_FINDER("coffee-finder"),
    FIND_COFFEEBAR("/pages/store-locator"),
    VIEW_MENU("/pages/menu"),
    PEETNIK_REWARDS("peetnik-rewards"),
    ORDER_FROM_A_COFFEE_BAR("order.peets.com"),
    OUR_COFFEE_REVOLUTION("pages/timeline"),
    COMMITMENT_TO_CRAFT("commitment-to-craft"),
    SOURCING_WITH_IMPACT("impact"),
    BREW_GUIDES("brew-guides"),
    THE_CUPPING_ROOM("/blogs/peets");

    String subCategory;

    MenuSubCategories(String subCategory) {
      this.subCategory = subCategory;
    }

    public String getMenuSubCategories() {
      return subCategory;
    }
  }

  public enum Mail {
    // We have created following accounts, if not displayed below, that means are corrupted
    // [peets.automation01 - peets.automation35
    Mail1("peets.automation17@applause.com"),
    Mail2("peets.automation18@applause.com"),
    Mail3("peets.automation19@applause.com"),
    Mail4("peets.automation20@applause.com"),
    Mail5("peets.automation21@applause.com"),
    Mail6("peets.automation22@applause.com"),
    Mail7("peets.automation23@applause.com"),
    Mail8("peets.automation24@applause.com"),
    Mail9("peets.automation25@applause.com"),
    Mail10("peets.automation26@applause.com"),
    Mail11("peets.automation27@applause.com"),
    Mail12("peets.automation28@applause.com"),
    Mail13("peets.automation29@applause.com"),
    Mail14("peets.automation30@applause.com"),
    Mail15("peets.automation31@applause.com"),
    Mail16("peets.automation32@applause.com"),
    Mail17("peets.automation33@applause.com"),
    Mail18("peets.automation34@applause.com"),
    Mail19("peets.automation35@applause.com");

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

  public enum StandardCoffeeInventory {
    Coffee1("french-roast"),
    Coffee2("french-roast-half-caf"),
    Coffee3("aged-sumatra");

    private final String value;

    StandardCoffeeInventory(String i) {
      value = i;
    }

    public String getValue() {
      return value;
    }

    public static StandardCoffeeInventory getRandomCoffee() {
      Random random = new Random();
      StandardCoffeeInventory data =
          StandardCoffeeInventory.values()[random.nextInt(StandardCoffeeInventory.values().length)];
      logger.info("Coffee Selected: " + data.toString());
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
    DASHBOARD("Dashboard", null),
    PEETNIK_REWARDS("Peetnik Rewards", MyAccountPeetnikRewardsPage.class),
    MY_SUBSCRIPTIONS("My Subscriptions", MyAccountPage.class),
    ORDER_HISTORY("Order History", MyAccountOrderHistoryPage.class),
    PEETS_CARDS("Peet's Cards", MyCardsPage.class),
    REFERRALS("Referrals", ReferralsPage.class),
    SETTINGS("Settings", MyAccountSettingsPage.class);

    @Getter private String value;
    @Getter private Class clazz;
  }

  @Getter
  @AllArgsConstructor
  public enum Touts {
    START_AN_ORDER("Start an Order", 1, "Select a location to order online or use the Peet’s app."),
    PLACE_ORDER_AND_PAY(
        "Place Order and Pay", 2, "Tap the Order button and select your beverages and food."),
    PICK_UP_IN_STORE("Pick Up In Store", 3, "Pick up your order at the Order Ahead Pickup area.");

    @Getter private String title;
    @Getter private int sequenceNumber;
    @Getter private String description;
  }
}
