package com.omahasteaks.tests.Checkout_2Step.PositiveCases;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListAddresses;
import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.enums.SkuType;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;
import com.omahasteaks.page.CategoryPage;
import com.omahasteaks.page.ConfirmationPage2SC;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.PaymentAndReviewPage2SC;
import com.omahasteaks.page.ProductPage;
import com.omahasteaks.page.SearchResultPage;
import com.omahasteaks.page.ShippingAddressPage2SC;
import com.omahasteaks.page.ShoppingCartPage;
import com.omahasteaks.page.SignInPage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SCOP_017_PayWithTwoCardsLinkIsNotAppearedIfIncludingWineInCart extends TestBase_2SC {
    CustomerAddress billingAddress, shippingAddress;
    @Inject
    ListSKUs myCart;
    @Inject
    Item myItem, myWine, myGiftCard;
    @Inject
    ListAddresses lstAddresses;
    @Inject
    GeneralPage generalPage;
    @Inject
    SignInPage signInPage;
    @Inject
    SearchResultPage searchResultPage;
    @Inject
    ShoppingCartPage shoppingCartPage;
    @Inject
    ProductPage productPage;
    @Inject
    ShippingAddressPage2SC shippingAddressPage2SC;
    @Inject
    PaymentAndReviewPage2SC paymentAndReviewPage2SC;
    @Inject
    ConfirmationPage2SC confirmationPage2SC;
    @Inject
    CategoryPage catergoryPage;

    @Test
    public void TC_2SCOP_017_Pay_With_Two_Cards_Link_Is_Not_Appeared_If_Including_Wine_In_Cart() {
	initTestCaseData();

	searchAndAddItem(myWine);

	continueShoppingFromAddedToCart();

	searchAndAddItem(myItem);

	checkOutFromAddedToCart();

	checkoutFromShoppingCart();

	fillShippingAddress();

	verifyPayWithTwoCardsLinkIsNotDisplayed();

  	goToShoppingCartThenRemoveWineItem();

	checkoutFromShoppingCart();

	verifyPayWithTwoCardsLinkDisplaysAgain();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================
    private void goToShoppingCartThenRemoveWineItem() {
	generalPage.goToMyCart();
	// Remove Wine
	shoppingCartPage.removeItem(myWine);
    }

    /************
    private void addGiftCardItem() {
	paymentAndReviewPage2SC.goToHomePage();
	Logger.info("In Homepage: " + "    - Search a SKU( Gift Card )  ( eg. Omaha Steaks Gift Card) " + "    - Click \"Search\" button");
	generalPage.search("Gift Card");
	
	Logger.info("In Gift Card page:\r\n" + "- In the first Item, input amount into \"Enter Amount\" textbox\r\n" + "- Click \"add to Cart\" button");
	catergoryPage.addGiftCard(myGiftCard);
    }
    ************/

    /***********
    private void removeItemAndContinueToPaymentAndReviewPage() {
	Logger.info("In Payment & Review page\r\n" + "- Click  \"Cart\" icon to go to Shopping Cart page");
	generalPage.goToMyCart();

	Logger.info("In Shopping Cart page:\r\n" + "- Remove the Food item\r\n" + "- Click \"Check out\"");
	shoppingCartPage.removeItem(myItem);
	shoppingCartPage.checkOut();
    }
    ************/
    
    private void verifyPayWithTwoCardsLinkIsNotDisplayed() {
	Logger.verify("In Payment & Review page\r\n" + "- Verify that \"Pay with two cards\" link is not appeared");
	assertEquals(paymentAndReviewPage2SC.isPayWithTwoCardsLinkDisplayed(), false);
    }

    private void verifyPayWithTwoCardsLinkDisplaysAgain() {
	Logger.verify("In Payment & Review page\r\n" + "- Verify that \"Pay with two cards\" link appears again");
	assertEquals(paymentAndReviewPage2SC.isPayWithTwoCardsLinkDisplayed(), true);
    }

    private void fillShippingAddress() {
	Logger.info("In Shipping Address Page: Fill valid information into Shipping Address section\r\n" + "- Click  \"Continue\"");
	shippingAddressPage2SC.fillShippingAddress(shippingAddress);
	shippingAddressPage2SC.clickContinueButton();
    }

    private void checkoutFromShoppingCart() {
	Logger.info("In Shopping Cart page\n" + "- Click \"CHECKOUT\"");
	shoppingCartPage.checkOut();
    }

    private void checkOutFromAddedToCart() {
	Logger.info("In \"Added To Cart\" popup: \n" + "- Click \"CHECKOUT\"");
	generalPage.checkOut();
    }

    private void continueShoppingFromAddedToCart() {
	Logger.info("In Added To Cart model:\r\n" + "- Click \"Continue Shopping\"");
	productPage.continueShopping();
    }

    private void searchAndAddItem(SKU sku) {
	Logger.info("From Homepage, Search and Add" + sku.getName() + "- Click Add to Cart");
	generalPage.search(Common.getNumberFromText(sku.getId()));
	productPage.addSKUToCart(sku, false);

    }

    private void initTestCaseData() {
	Logger.tc("TC_2SCOP_017 - Pay With Two Cards Link Is Not Appeared If Including Wine In Cart");
	Logger.to("TO_2SCOP_44 - Pay with two cards' link should not appear in the Payment & Review page if Wine items are added in Cart");
	Logger.to("TO_2SCOP_45 - Pay with two cards' link should not appear in the Payment & Review page if Wine + Food items are added in Cart");
	Logger.to("TO_2SCOP_46 - Pay with two cards' link should not appear in the Payment & Review page if Wine + Gift Card items are added in Cart");

	shippingAddress = lstAddresses.getDefaultShippingAddress();
	myCart.initEmpty();
	myItem.init(SkuType.ITEM,Recipient.MYSELF);
	myGiftCard.setRecipient(Recipient.MYSELF);
	myGiftCard.setPrice(Common.randBetween(5, 500));
	myWine.init(SkuType.WINE,Recipient.MYSELF);
    }
}
