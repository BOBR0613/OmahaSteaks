package com.omahasteaks.tests.RewardsCertificate;

import static org.testng.Assert.assertTrue;

import com.google.inject.Inject;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.data.enums.AddressType;
import com.omahasteaks.data.enums.LinksCustomerService;
import com.omahasteaks.data.enums.SearchType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.RewardSKU;
import com.omahasteaks.page.RewardGeneralPage;
import com.omahasteaks.page.RewardOrderReviewPage;
import com.omahasteaks.page.RewardShippingInfoPage;
import com.omahasteaks.page.RewardShippingOptionPage;
import com.omahasteaks.page.RewardShoppingCart;
import com.omahasteaks.tests.TestBase_RewardCollection;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TestBase_CR extends TestBase_RewardCollection {

	@Inject
	public RewardGeneralPage rewardGeneralPage;

	@Inject
	public RewardShoppingCart rewardShoppingCart;

	@Inject
	public RewardShippingInfoPage rewardShippingInfoPage;

	@Inject
	public RewardShippingOptionPage rewardShippingOptionPage;

	@Inject
	public RewardOrderReviewPage rewardOrderReviewPage;

	protected void searchSKUWithPoint(String point) {
		Logger.info("In Home page, search SKU which has " + point + " points");
		rewardGeneralPage.search(point, SearchType.POINT_VALUE);
	}

	protected void addFirstSKUToCart(RewardSKU sku) {
		Logger.info("Add the first SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectFirstItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
		  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addSecondSKUToCart(sku);
		}
	}
	
	protected void addSecondSKUToCart(RewardSKU sku) {
		Logger.info("Add the second SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectSecondItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addThirdSKUToCart(sku);
		}
	}

	protected void addThirdSKUToCart(RewardSKU sku) {
		Logger.info("Add the third SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectThirdItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
		  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addFourthSKUToCart(sku);
		}
	}
	
	protected void addFourthSKUToCart(RewardSKU sku) {
		Logger.info("Add the fourth SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectFourthItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
		  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addFifthSKUToCart(sku);
		}
	}
	
	protected void addFifthSKUToCart(RewardSKU sku) {
		Logger.info("Add the fifth SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectFifthItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
		  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addSixthSKUToCart(sku);
		}
	}
	
	protected void addSixthSKUToCart(RewardSKU sku) {
		Logger.info("Add the sixth SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectSixthItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
		  Logger.info("Item is out of stock. Retrying with next item.");
		  DriverUtils.getWebDriver().navigate().back();	
		  DriverUtils.getWebDriver().navigate().back();	
		  addNinthSKUToCart(sku);
		}
	}
	
	protected void addNinthSKUToCart(RewardSKU sku) {
		Logger.info("Add the ninth SKU and ship to " + sku.getRecipient() + " -Click \"Check out\" button");
		rewardGeneralPage.selectNinthItem();
		addSKUToCart(sku);
		if (rewardGeneralPage.isItemOutOfStockMsgDisplayed()) {
			Logger.warning("No items selected are in-stock.  Cannot finish test case.");
		}
	}

	protected void fillRewardNumberAndClickCheckOut(String rewardNumber) {
		Logger.info("In Shopping Cart page, fill " + rewardNumber + " into Reward Number textbox" + " - Click \"Checkout\" link");
		rewardShoppingCart.submitRewardNumber(rewardNumber);
		rewardShoppingCart.goToShippingInformationPage();
	}

	protected void fillResidentialAddressAndClickContinue(CustomerAddress address) {
		if (rewardGeneralPage.getCurrentTabName().equals(Constants.TITLE_SHIPPING_INFORMATION)) {
			fillShippingInformation(address);
			clickContinuelink();
		} else {
			clickContinuelink();
			clickContinuelink();
		}
	}

	protected void clickContinuelink() {
		Logger.info("Click \"Continue\" link");
		rewardGeneralPage.clickContinueLink();
	}

	protected void fillShippingInformation(CustomerAddress address) {
		Logger.info("In Shipping Information Page:" + "- Fill valid information into into all mandatory in Residential section.");
		rewardShippingInfoPage.selectAddressType(AddressType.RESIDENTIAL_ADDRESS);
		rewardShippingInfoPage.fillShippingInformation(address);
	}

	protected void verifyTitleOfShoppingBagTableDisplaysCorrectly(CustomerAddress address) {
		Logger.verify("Verify that title of 'Shopping bag' table for" + address.firstName + " " + address.lastName + "'s cart displays with correct information");
		assertTrue(rewardOrderReviewPage.isTitleOfShoppingBagTableDisplayedCorrectly(address.firstName + " " + address.lastName), "Title of 'Shopping bag' does not display correctly.");
	}

	protected void verifyMessagePleaseReviewYourOrderDisplays() {
		Logger.verify("In Order Review Page:" + "Verify that  'Please review your order information and click the \"Submit Order\" button at the bottom of the page." + "Your order will not be completed unless you click \"Submit Order.\" ' message is displayed");
		assertTrue(rewardOrderReviewPage.isPleaseReviewYourOrderMsgDisplayed(), "'Please Review Your Order' message does not display in 'Order review' page");
	}

	protected void deleteSKUFromCart(RewardSKU sku) {
		Logger.substep("In Shopping cart, click delete button for item " + sku.getId());
		rewardShoppingCart.deleteSKU(sku);
	}

	protected void fillDelayedArrivalDateAndClickContinueLink(String date) {
		fillDelayedArrivalDate(date);
		clickContinuelink();
	}

	protected void fillDelayedArrivalDate(String date) {
		if (!date.equals(Constants.DEFAULT_DATE)) {
			Logger.info("Fill " + date + " into delayed arrival date textbox");
			rewardShippingOptionPage.fillDelayedArrivalDate(date);
		}
	}

	protected void goToShoppingCartPage() {
		Logger.substep("Go to Shopping Cart page by clicking on \"Checkout\" link from catergory section");
		rewardGeneralPage.goToShoppingCartPage(true);
	}

	protected void addSKUToCart(RewardSKU sku) {
		Logger.substep("Add searched SKU to " + sku.getRecipient().getValue() + "");
		rewardGeneralPage.addSKUToCart(sku);
	}

	protected String goToShoppingCategoryPage(String orderCategory) {
		String nameCategory = rewardGeneralPage.getCategoryNameFromCategoriesSection(orderCategory);
		Logger.substep("Select " + nameCategory + " tab from Shopping Categories section");
		rewardGeneralPage.selectCategory(nameCategory);
		return nameCategory;
	}

	protected void searchAndAddSKUToCart(RewardSKU sku) {
		searchSKUWithPoint(Integer.toString(sku.getPoint()));
		addFirstSKUToCart(sku);
	}

	protected void searchAndAddSKU9ToCart(RewardSKU sku) {
		searchSKUWithPoint(Integer.toString(sku.getPoint()));
		addFirstSKUToCart(sku);
	}

	protected void selectLinkInCustomerService(LinksCustomerService nameLink) {
		Logger.info("Click " + nameLink.getValue() + " link in Customer Service page");
		rewardGeneralPage.selectLinkInCustomerServicePage(nameLink);
	}

	protected void goToCustomerServicePage() {
		Logger.info("Click Customer Service tab in menu");
		rewardGeneralPage.selectTabInTopMenu(Constants.CUSTOMER_SERVICE);
	}

	protected void goToEachSubLink(LinksCustomerService nameLink) {
		goToCustomerServicePage();
		selectLinkInCustomerService(nameLink);
	}

	protected void addSKUToCartAndGoToShoppingCart(RewardSKU sku) {
		searchAndAddSKUToCart(sku);
		goToShoppingCartPage();
	}

	protected void fillRewardNumberAndCheckOut() {
		goToShoppingCartPage();
		fillRewardNumberAndClickCheckOut(Constants.REWARDS_NUMBER_VALID);
	}

	protected void addSKUFromCategory(RewardSKU sku, String indexOfCategory) {
		goToShoppingCategoryPage(indexOfCategory);
	    rewardGeneralPage.selectFirstItem(); 
		addSKUToCart(sku);
	}
}
