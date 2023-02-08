package com.applause.auto.test.web.shipping;

import com.applause.auto.common.data.Constants.TestData;
import com.applause.auto.common.data.Constants.TestNGGroups;
import com.applause.auto.test.web.BaseTest;
import org.testng.annotations.Test;

public class ShippingDiscountsTests extends BaseTest {

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.SHIPPING_DISCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127074")
  public void oneLbKCupItemCheckoutFlow() {
    ShippingTestHelper.checkoutProcess(
        navigateToHome(),
        TestData.MAJOR_DICKASONS_BLEND_KCUP_PODS,
        1,
        TestData.SHIPPING_METHOD_GROUND);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.SHIPPING_DISCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127075")
  public void lessThanFiveLbMultipleItemsCheckoutFlow() {
    ShippingTestHelper.checkoutProcess(
        navigateToHome(),
        TestData.DECAF_SUMATRA_URL_PARAMETER,
        3,
        TestData.SHIPPING_METHOD_GROUND_SUREPOST);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.SHIPPING_DISCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127076")
  public void betweenFiveLbToNineLbTeaMerchItemsCheckoutFlow() {
    ShippingTestHelper.checkoutProcess(
        navigateToHome(), TestData.DECAF_SUMATRA_URL_PARAMETER, 6, TestData.SHIPPING_METHOD_GROUND);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.SHIPPING_DISCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127077")
  public void moreThanNineLbKCupItemCheckoutFlow() {
    ShippingTestHelper.checkoutProcess(
        navigateToHome(),
        TestData.MAJOR_DICKASONS_BLEND_KCUP_PODS,
        9,
        TestData.SHIPPING_METHOD_GROUND);
  }

  @Test(
      groups = {
        TestNGGroups.FRONT_END_REGRESSION,
        TestNGGroups.SHIPPING_DISCOUNT,
        TestNGGroups.FE_PROD_REGRESSION
      },
      description = "11127078")
  public void threeDayShippingItemCheckoutFlow() {
    ShippingTestHelper.checkoutProcess(
        navigateToHome(),
        TestData.RISTRETTO_ESPRESSO_CAPSULES_URL_PARAMETER,
        1,
        TestData.SHIPPING_METHOD_THREE_DAY_SELECT);
  }
}
