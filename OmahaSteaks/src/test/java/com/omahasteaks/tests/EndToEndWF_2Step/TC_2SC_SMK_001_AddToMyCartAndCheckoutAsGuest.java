package com.omahasteaks.tests.EndToEndWF_2Step;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.utils.helper.Logger;

public class TC_2SC_SMK_001_AddToMyCartAndCheckoutAsGuest extends TestBase_SMK {
    @Test
    public void TC_2SC_SMK_001_Add_To_My_Cart_And_Checkout_As_Guest() {
	Logger.tc("TC_2SC_SMK_001 Add To MyCart And Checkout As Guest");

	Logger.to("TO01	Verify Guest - New customer can add SKU to Myself cart and then complete Checkout");

	initTestCaseData();

	searchAndAddItem(myItem, true);

	goToMyCart();

	String specialSkuId = addAndGetSpecialCartBonusId();
	goToMyCart();
 
	verifyMainSkuIsAddedSuccessfully();

	verifySpecialSkuIsAddedSuccessfully(specialSkuId);
	int cartNumber = myCart.size();

	verifyNumberOfCartIncreasedBy1(cartNumber);

	removeSpecialSku(specialSkuId);

	myCart.removeSkuByID(specialSkuId);

	verifySpecialSkuIsRemovedAndNumberOfCartReducesBy(cartNumber, specialSkuId);
 
	checkOutFromShoppingCartPage();

	fillShippingAddressAndContinueCheckout(shippingAddress);

	verifyShippingAddressesDisplayCorrectly(Recipient.MYSELF, shippingAddress);

	fillCreditCardNumberAndPlaceMyOrder();
 
	verifyBillingAddressAndThankYouMessageDisplays();

	verifyOrderNumberInCorrectFormat();
    }

    // ================================================================================
    // Test Case Methods
    // ================================================================================

    private String addAndGetSpecialCartBonusId() {
	Logger.info("In Shopping Cart page, if \"Special Cart Bonus Savings\" appears:\n" + " - Keep default values of checkboxes.\n" + " - In dropdown, select \"Myself\"\n" + " - Click \"ADD TO CART\"");
	shoppingCartPage.addSpecialCartBonus(Recipient.MYSELF);
	String specialSkuId = shoppingCartPage.getAddedSkuIdFromModal();
	shoppingCartPage.closeModalDialog();
	return specialSkuId;
    }

    private void removeSpecialSku(String specialSkuId) {
	Logger.info(" If Special Cart Bonus Savings are added to My Cart, select it then:\n" + " - Click \"Remove\" link\n" + " - Click \"Yes\" link");
	shoppingCartPage.removeSkuById(Recipient.MYSELF, specialSkuId);
    }

    private void verifyNumberOfCartIncreasedBy1(int cartNumber) {
	assertEquals(generalPage.getCartNumber(), cartNumber + 1, "The number of cart item is not increased as expected");
    }

    private void verifySpecialSkuIsAddedSuccessfully(String specialSkuId) {
	assertTrue(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, specialSkuId), "The sku is not displayed as expected");
    }

    private void verifySpecialSkuIsRemovedAndNumberOfCartReducesBy(int cartNumber, String specialSkuId) {
	Logger.verify("Verify that:\n" + " - SKU from Special Cart Bonus are removed \n" + " - Cart count (number above \"My Cart\" icon) decreases correctly");
	assertFalse(shoppingCartPage.isSkuByIdAdded(Recipient.MYSELF, specialSkuId), "The SKU from Special Cart is not deleted as expected");
	assertEquals(generalPage.getCartNumber(), cartNumber, "The number of cart item is not displayed as expected");
    }
}
