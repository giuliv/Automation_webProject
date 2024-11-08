package com.applause.auto.common.data.enums;

import com.applause.auto.common.data.Constants;
import com.applause.auto.web.helpers.WebHelper;
import com.applause.auto.web.views.CommonWebPage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FooterOptions {

  // HELP CENTER
  CONTACT_US(
      "Help center",
      "Contact Us",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/contact-us",
      CommonWebPage.class),
  HELP_CENTER("Help center", "Help Center", "https://faq.peets.com/hc/en-us", CommonWebPage.class),
  SHIPPING_AND_RETURNS(
      "Help center",
      "Shipping & Returns",
      "https://faq.peets.com/hc/en-us/sections/115000833243-Shipping-and-Returns",
      CommonWebPage.class),

  // Company
  CAREERS(
      "Company",
      "Careers",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/careers",
      CommonWebPage.class),
  OUR_PEOPLE(
      "Company",
      "Our People",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/our-people",
      CommonWebPage.class),
  NEWSROOM(
      "Company",
      "Newsroom",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/newsroom",
      CommonWebPage.class),
  CODE_OF_ETHICS(
      "Company",
      "Code of Ethics",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/code-of-ethics",
      CommonWebPage.class),
  SUPPLY_CHAIN_TRANSPARENCY(
      "Company",
      "Supply Chain Transparency",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/supply-chain-transparency",
      CommonWebPage.class),

  // Gift cards
  SHOP_GIFTS_CARDS(
      "Gift cards",
      "Shop Gift Cards",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/gift-cards",
      CommonWebPage.class),

  // OFFERS
  CURRENT_OFFERS(
      "Offers",
      "Current Offers",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/current-offers",
      CommonWebPage.class),
  GET_FREE_COFFEE(
      "Offers",
      "GET FREE COFFEE",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/refer?traffic_source=site_footer",
      CommonWebPage.class),

  // PARTNER WITH PEET's
  FOODSERVICE_PROGRAM(
      "Partner with peet's",
      "Foodservice Program",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/pages/foodservice-program",
      CommonWebPage.class),
  BECOME_AFFILIATE(
      "Partner with peet's",
      "Become an affiliate",
      "https://signup.cj.com/member/signup/publisher/?cid=2346375#/branded",
      CommonWebPage.class),
  SUBMIT_SITE(
      "Partner with peet's",
      "Submit a Site",
      "https://peetscoffee.simmsonline.com/vexray/step1",
      CommonWebPage.class),

  // BLOG
  CUPPING_ROOM(
      "Blog",
      "The Cupping Room",
      Constants.TestEnvironment.valueOf(WebHelper.getTestEnvironment()).getEnvironment()
          + "/blogs/peets",
      CommonWebPage.class),

  // SOCIAL MEDIA
  INSTAGRAM("Social Media", "instagram", "www.instagram.com", CommonWebPage.class),
  TWITTER("Social Media", "twitter", "twitter.com", CommonWebPage.class),
  FACEBOOK("Social Media", "facebook", "facebook.com", CommonWebPage.class),
  YOUTUBE("Social Media", "youtube", "youtube.com", CommonWebPage.class),
  LINKEDIN("Social Media", "linkedin", "www.linkedin.com", CommonWebPage.class),

  // FOOTER END SUB LINKS
  PRIVACY_POLICY("End Sub link", "Privacy Policy", "/pages/privacy-policy", CommonWebPage.class),
  TERMS_OF_SERVICE(
      "End Sub link", "Terms of Service", "/pages/terms-and-conditions", CommonWebPage.class),
  DISCLAIMER_TEXT(
      "End Sub link",
      "Nespresso is a registered trademark of Société des Produits Nestlé S.A., and is not affiliated with Peet’s Coffee Inc. Compatible with most Nespresso Original machines.",
      "",
      CommonWebPage.class),
  COLOPHON_TEXT("End Sub link", "© 2023. PEET'S COFFEE", "", CommonWebPage.class);

  @Getter private String category;
  @Getter private String option;
  @Getter private String url;
  @Getter private Class clazz;

  public static List<FooterOptions> getHelpCenterOptions() {
    return getOptions("Help center");
  }

  public static List<FooterOptions> getCompanyOptions() {
    return getOptions("Company");
  }

  public static List<FooterOptions> getGiftsOptions() {
    return getOptions("Gift cards");
  }

  public static List<FooterOptions> getOffersOptions() {
    return getOptions("Offers");
  }

  public static List<FooterOptions> getPartnerWithPeetsOptions() {
    return getOptions("Partner with peet's");
  }

  public static List<FooterOptions> getBlogOptions() {
    return getOptions("Blog");
  }

  public static List<FooterOptions> getSocialMediaLinks() {
    return getOptions("Social Media");
  }

  public static List<FooterOptions> getEndSubLinks() {
    return getOptions("End Sub link");
  }

  private static List<FooterOptions> getOptions(String category) {
    return Arrays.stream(FooterOptions.values())
        .filter(v -> v.getCategory().equals(category))
        .collect(Collectors.toList());
  }
}
