package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_019_PayPalButtonAndTabAreNotAppearedWhenWineIsAddedToCart extends TestBase_2SC {
    CustomerAddress shippingAddress;

    @Inject
    Item myItem, myItemWine;

    @Inject
    SKU myGiftCard;

    @Inject
    ListAddresses lstAddresses;

    @Inject
    GeneralPage generalPage;

    @Inject
    SearchResultPage searchResultPage;

    @Inject
    ShoppingCartPage shoppingCartPage;

    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;

    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;

    @Inject
    CategoryPage catergoryPage;

    @Inject
    ProductPage productPage;

    @Test
    public void TC_2SCOP_019_PayPal_Button_And_Tab_Are_Not_Appeared_When_Wine_Is_Added_To_Cart() {
	initTestCaseData();

	searchAndAddItem(myItemWine);

	searchAndAddItem(myItem);

	searchAndAddGiftCard(myGiftCard);

	verifyCheckOutWithPayPalIsNotDisplayedInShoppingCart();

	checkOutFromShoppingCart();

	fillShippingAddressAndClickContinue();

	verifyPayPalTabIsNotDisplayedInPaymentAndReview();

	goToMyCartAndRemoveWine();

	verifyCheckOutWithPayPalButtonIsDisplayedInShoppingCart();

	checkOutFromShoppingCart();

	verifyPayPalTabIsDisplayedInPaymentAndReview();
    }

    // ============================================================================
    // Test Methods
    // ============================================================================
    private void searchAndAddGiftCard(SKU sku) {
	Logger.info("In Searchpage: \n" + "- Search a SKU (Gift cards)\n");
	generalPage.search("Gift Card");

	Logger.info("- Select the \"Omaha Steaks Gift Card\"\n" + "- Click \"Buy Gift Card\" button");
	catergoryPage.clickBuyGiftCardButton();

	Logger.info("In Gift Card page:\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\n" + "- Click \"add to Cart\" button");
	catergoryPage.addGiftCard(sku);

	Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
	generalPage.checkOut();
    }

    private void goToMyCartAndRemoveWine() {
	goToMyCartByShoppingCartIcon();
	removeWineFromShoppingCart();
    }

    private void verifyPayPalTabIsDisplayedInPaymentAndReview() {
	Logger.verify("Verify in Payment & Review page, PayPal tab is appeared");
	assertTrue(paymentAndReviewPage2SC.isPayPalTabDisplayed());
    }

    private void verifyCheckOutWithPayPalButtonIsDisplayedInShoppingCart() {
	Logger.verify("Verify in Shopping Cart page, 'Check out with PayPal' button is appeared");
	assertTrue(shoppingCartPage.isCheckOutWithPayPalButtonDisplayed());
    }

    private void removeWineFromShoppingCart() {
	Logger.info("Remove Main SKU from Shopping Cart:\n" + "- Select added Wine\n" + "- Click \"Remove\" link\n" + "- Click \"Yes\" link");
	shoppingCartPage.removeItem(myItemWine);
    }

    private void goToMyCartByShoppingCartIcon() {
	Logger.info("In Payment & Review Page:\n" + "- Click \"Shopping Cart\" icon");
	generalPage.goToMyCart();
    }

    private void verifyPayPalTabIsNotDisplayedInPaymentAndReview() {
	Logger.verify("Verify in Payment & Review page, PayPal tab is not appeared");
	assertFalse(paymentAndReviewPage2SC.isPayPalTabDisplayed());
    }

    private void fillShippingAddressAndClickContinue() {
	Logger.info("In Shipping Address Page:\n" + "- Fill valid information\n" + "- Click  \"Continue \"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkOutFromShoppingCart() {
	Logger.info("In Shopping Cart page:\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void verifyCheckOutWithPayPalIsNotDisplayedInShoppingCart() {
	Logger.verify("Verify in Shopping Cart page, 'Check out with PayPal' button is not appeared");
	assertFalse(shoppingCartPage.isCheckOutWithPayPalButtonDisplayed());
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, Search a SKU:" + sku.getName() + "Click Add it to Cart" + "\"In \"\"Added To Cart\"\" popup: \n" + "- Click \"CONTINUE SHOPPING\"");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);
	generalPage.continueShopping();
    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_019 - PayPal button and tab aren't appeared when Wine items is added to Cart");
	Logger.to("TO_2SCOP_049 - In Shopping Cart page - PayPal button isn't appeared if Wine items are added to Cart");
	Logger.to("TO_2SCOP_050 - In Payment & Review page - PayPal tab isn't appeared if Wine items are added to Cart");
	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myItem.initRandom(Recipient.MYSELF, false);
	myItemWine.initRandom(Recipient.MYSELF, true);
	myGiftCard.setRecipient(Recipient.MYSELF);
	myGiftCard.setName("Omaha Steaks Gift Card");
	myGiftCard.setPrice(Common.randBetween(5, 500));
	myGiftCard.setQuantity(1);
    }

}
