package com.omahasteaks.tests.Checkout_2Step.NegativeCases;

import static org.testng.Assert.assertEquals; 
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SideMenuItem;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SitemapPage;
import com.omahasteaks.page.UltimatePackagesPage; 
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCON_011_DropShipAndBurgerAndPackageItemsCannotBeDeliveredToCanada extends TestBase_2SC {
    @Inject
    GeneralPage generalPage;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    ListSKUs myCart;

    @Inject
    Item myItemWine, myItemBurger, mypackageItems, myItem;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage;

    @Inject
    ProductPage productPage;

    @Inject
    SearchResultPage searchResultPage;

    CustomerAddress shippingAddress;

    @Inject
    ShippingAddressPage2SC shippingAddressPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    SitemapPage sitemapPage;

    @Inject
    UltimatePackagesPage ultimatePackagesPage;

    @Test
    public void TC_2SCON_011_DropShip_And_Burger_And_packageItems_Cannot_Be_Delivered_To_Canada() {
	initTestCaseData();

	searchAndAddUltimatePackages();
	checkOutFromAddedToCart();

	searchAndAddWineItem(myItemWine);
	continueFromAddedToCart();

	searchAndAddBurgerItem(myItemBurger);
	continueFromAddedToCart();

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue(shippingAddress);

	verifyErrorMessageIsDisplay();

	verifyTheSKUsWhichCannotBeDeliveredToCanadaDisplayInShippingErrorSection();

	clickRemoveItemLink();

	cancelFromRemoveThisCart();

	verifyUserIsStayedAtShippingAddressPage();

	clickRemoveItemLink();

	confirmUpdateButtonFromRemoveThisCart();

	verifyYourCartIsCurrentlyEmptyIsDisplayed();

	searchAndAddWineItemSecondTime(myItemWine);
	continueFromAddedToCart();

	searchAndAddFoodItem(myItem);
	checkOutFromAddedToCart();

	checkOutFromShoppingCart();
    }
    
    // ============================================================================
    // Test Methods
    // ============================================================================

    private void cancelFromRemoveThisCart() {
	Logger.info("In 'Remove This Cart?' modal popup:" + "- Click \"CANCEL\"");
	shippingAddressPage.clickCancelButtonInRemoveThisCartModal();
    }

    private void checkOutFromAddedToCart() {
	Logger.info("In \"Added To Cart\" popup: " + "- Click \"CHECKOUT\"");
	generalPage.getAddedToCartSKUList(myCart);
	generalPage.checkOut();
    }

    private void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart page" + "- Click \"CHECKOUT\"");
	generalPage.goToMyCart();
	shoppingCartPage.checkOut();
    }

    private void clickRemoveItemLink() {
	Logger.info("In Shipping Address Page:" + "- Click \"Remove Items\" link");
	shippingAddressPage.clickRemoveItemsLink();
    }

    private void confirmUpdateButtonFromRemoveThisCart() {
	Logger.info("In 'Remove This Cart?' modal popup:" + "- Click \"CONFIRM UPDATE & CONTINUE\"");
	shippingAddressPage.clickConfirmUpdateButtonInRemoveThisCartModal();
    }

    private void continueFromAddedToCart() {
	Logger.info("In \"\"Added To Cart\"\" popup: " + "- Click \"CONTINUE SHOPPING\"");
	generalPage.getAddedToCartSKUList(myCart);
	generalPage.continueShopping();
    }

    private void fillShippingAddress(CustomerAddress address) {
	Logger.info("In Shipping Address Page:" + "- Fill valid Canada Address into Shipping Address section");
	shippingAddressPage.fillShippingAddress(address);
    }

    private void fillShippingAddressAndClickContinue(CustomerAddress address) {
	fillShippingAddress(address);
	Logger.info("Click  \"Continue \"");
	shippingAddressPage.clickContinueButton();
    }

    private void goToUltimatePackagesPage() {
	Logger.info("Go to Ultimate Packages page");
	String keyword = "Free Shipping package";
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
	    generalPage.search(keyword);
	else
	    generalPage.clickSideMenuItemLink(SideMenuItem.ULTIMATE_PACKAGES);
    }

    private void initTestCaseData() {

	Logger.tc("TC_2SCON_011 - DropShip and Burger and package Items cannot be delivered to Canada");
	Logger.to("TO_2SCON_34 - Canada Address - DropShip items (including Wine) cannot be delivered to Canada");
	Logger.to("TO_2SCON_35 - Canada Address - Burger items cannot be delivered to Canada");
	Logger.to("TO_2SCON_36 - Canada Address - Ultimate package items cannot be delivered to Canada");

	myCart.initEmpty();
	shippingAddress = lstAddresses.getRandomShippingAddressCanada();
	myItemWine.initRandom(Recipient.MYSELF, true);
	myItemBurger.setRecipient(Recipient.MYSELF);
	mypackageItems.setRecipient(Recipient.MYSELF);
	myItem.initRandom(Recipient.MYSELF, false);
	myItemBurger.init(SkuType.BURGERS, Recipient.MYSELF);
    }

    private void searchAndAddBurgerItem(Item item) {
	Logger.info("From Select Page, Search " + item.getId());
	generalPage.search(item.getId());

	Logger.info("In Search page\n" + "- Select the first Item and add it to Cart");
	generalPage.addFirstSKUToCart(item);

	Logger.info("In Exclusive Offer (Upsell offer) modal:" + "- Click \"No Thanks\"");
	productPage.selectExclusiveOffer(false);
    }

    private void searchAndAddFoodItem(Item item) {
	Logger.info("From Product Page, Search a Food Item with ID " + item.getId());
	generalPage.search(item.getId());
	generalPage.addFirstSKUToCart(item);
	productPage.selectExclusiveOffer(false);
    }

    private void searchAndAddUltimatePackages() {
	goToUltimatePackagesPage();
	//ultimatePackagesPage.clickFirstShopNowButton();
	generalPage.addFirstSKUToCart(mypackageItems);
	generalPage.selectExclusiveOffer(false);
    }

    private void searchAndAddWineItem(Item item) {
	Logger.info("From Homepage, Search a SKU (Wine) " + item.getId());
	generalPage.search(item.getName());

	Logger.info("In Search page\n" + "- Select the first Item and add it to Cart");
	generalPage.addFirstSKUToCart(item);
    }

    private void searchAndAddWineItemSecondTime(Item item) {
	Logger.info("From Shopping Cart page, Search a Wine " + item.getId());
	if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
	    generalPage.search(item.getName());
	else {
	    generalPage.goToHomePage();
	    generalPage.search(item.getName());
	}

	Logger.info("In Search page\n" + "- Add it to Cart");
	generalPage.addFirstSKUToCart(item);
    }

    private void verifyErrorMessageIsDisplay() {
	Logger.verify("In Shipping Address Page:" + "- The Warning message :\"Unfortunately we are unable to ship the following items to Canada.\"is displayed");
	assertEquals(shippingAddressPage.getShippingErrorMessage(), "Unfortunately we are unable to ship the following items to Canada.");
    }

    private void verifyTheSKUsWhichCannotBeDeliveredToCanadaDisplayInShippingErrorSection() {
	Logger.verify("In Shipping Address Page:" + "Verify The SKUs cannot be delivered to Canada Address are displayed in Shipping Error section");
	for (SKU sku : myCart.getList()) {
	    assertTrue(shippingAddressPage.isSKUExistedInShippingError(sku), " - ".concat(sku.getName()).concat(" does not display!"));
	}
    }

    private void verifyUserIsStayedAtShippingAddressPage() {
	Logger.verify("Verify user is stayed at Shipping Address Page");
	assertEquals(Common.getTitlePage().trim(), "Checkout | Shipping");
    }

    private void verifyYourCartIsCurrentlyEmptyIsDisplayed() {
	Logger.verify("In Shopping Cart page:" + "Verify that 'Your Cart Is Currently Empty' appears when removed all items");
	assertEquals(shoppingCartPage.getEmptyMessageText().trim(), Constants.EMPTY_MESSAGE);
    }
}
