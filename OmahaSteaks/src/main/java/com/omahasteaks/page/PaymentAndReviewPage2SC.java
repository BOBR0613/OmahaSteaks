package com.omahasteaks.page;

import java.util.Calendar;
import java.util.List;

import com.omahasteaks.data.enums.AddressFields;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.CustomerAddress;
import com.omahasteaks.data.objects.SKU;

public interface PaymentAndReviewPage2SC extends GeneralPage {

	/**
	 * verify Pay With Two Card link appears in Payment & Review page
	 */
	boolean isPayWithTwoCardsLinkDisplayed();

	/**
	 * click Pay With Two Card link appears in Payment & Review page
	 */
	void clickPayWithTwoCardsLink();

	/**
	 * click View All Shipping Options at Recipient section in Payment & Review page
	 */
	void clickViewAllShippingOptionsByRecipientNameLink(String recipient);

	/**
	 * click Save and Continue button at modal in Payment & Review page
	 */
	void clickSaveAndContinueButtonInModal();

	/**
	 * Click "edit" link to Edit the Gift Message. In Gift Message pop up modal,
	 * enter Edited Gift Message and click "Submit" button
	 */
	void editGiftMessage(String recipient, String message, String signature);

	/**
	 * select Shipping Method in Shipping and Delivery modal
	 */
	void selectShippingMethodInShippingAndDeliveryModal(String shippingMethod);

	/**
	 * Fill Billing Address information into all fields at Billing Address section
	 * in Payment & Review page
	 */
	void fillBillingAddress(CustomerAddress billingAddress);

	/**
	 * fill credit card number into credit card number text box in Payment & Review
	 * page
	 */
	void fillCreditCardNumber();

	void fillCreditCardNumber(String creditCardNumber, boolean isFutureDate);

	/**
	 * fill credit card number and date into credit card number and date text boxes
	 * in Payment & Review page
	 */
	void fillCreditCardNumber(String creditCardNumber, String date);

	/**
	 * fill amount of first credit card number in Payment & Review page
	 */
	void fillFirstCreditCardAmount(double amount);

	/**
	 * fill first credit card number in Payment & Review page
	 */
	void fillFirstCreditCardRandomAmount();

	/**
	 * fill amount of second credit card number in Payment & Review page
	 */
	void fillSecondCreditCardAmount(double amount);

	/**
	 * fill second credit card number in Payment & Review page
	 */
	void fillSecondCreditCardNumber();

	void fillSecondCreditCardNumber(String creditCardNumber, boolean isFutureDate);

	/**
	 * get first credit card number text in Payment & Review page
	 */
	double getFirstCreditCardAmount();

	/**
	 * get gift message text in Gift message text box in Greeting card section in
	 * Payment & Review page
	 */
	String getGiftMessage(String recipient);

	/**
	 * get Credit Card type in Payment & Review page
	 */
	String getCreditCardType();

	/**
	 * get amount of second Credit Card in Payment & Review page
	 */
	double getSecondCreditCardAmount();

	/**
	 * get shipping address of Recipient in Payment & Review page
	 */
	String[] getShippingAddress(String recipient);

	/**
	 * get Shipping Method and Delivery Date display in Payment & Review page
	 */
	String getShippingInfo(String recipient);

	/**
	 * get total price display in Payment & Review page
	 */
	String getTotalPrice();

	/**
	 * get the message wine in Recipient's cart is displayed in Payment and Review
	 * page
	 */
	String getWineInCartMesssage(String recipient);

	/**
	 * The gift wrap option is added as expected in Payment and Review page
	 */
	boolean isGiftWrapAdded(String recipient);

	/**
	 * The greeting card is added as expected in Payment and Review page
	 */
	boolean isGreetingCardAdded(String recipient, String cardName);

	/**
	 * verify SKU is existed in Payment and Review page
	 */
	boolean isSKUExisted(SKU sku);

	/**
	 * click Place Order button in Payment and Review page
	 */
	void placeOrder();

	void placeOrderIgnoreError();

	/**
	 * click "edit" link to Edit the Gifting option and click "No Thanks" button to
	 * remove Gift Wrap option
	 */
	void removeGiftingOption(String recipient);

	/**
	 * select Edit this address of Shipping Address section in Payment & Review page
	 */
	void selectEditShippingAddrLink(String recipient);

	/**
	 * Check "Same As My Address?" check box in Payment & Review page
	 */
	void selectSameAsMyAddress();

	/**
	 * get Estimated Delivery by shipping method in Shipping Delivery modal
	 */
	String getEstimatedDeliveryByShippingMethodInShippingDeliveryModal(String shippingMethod);

	/**
	 * get Greeting Card image by Recipient name
	 */
	String getGreetingCardImageByRecipientName(String recipient);

	/**
	 * click Edit Gift options link by Recipient name
	 */
	void clickEditGiftOptionLinkByRecipientName(String recipient);

	/**
	 * select gift option greeting card in modal
	 */
	void selectGiftOptionGreetingCardInModal();

	/**
	 * select second greeting card in modal
	 */
	String selectSecondGreetingCardInModal();

	/**
	 * get error message displays in Payment & Review page
	 */
	String getErrorMessage();

	/**
	 * get error message by Field displays in Payment & Review page
	 */
	String getErrorMessageByField(AddressFields addrField);

	/**
	 * select Edit Billing Address link in Billing Address section in Payment &
	 * Review page
	 */
	void selectEditBillingAddressLink();

	/**
	 * fill Billing Address on modal in Payment & Review page
	 */
	void fillBillingAddressInModal(CustomerAddress address);

	/**
	 * click update contact on modal in Payment & Review page
	 */
	void updateContactInModal();

	/**
	 * get error message displays at modal in Payment & Review page
	 */
	String getErrorMessageInModal();

	/**
	 * click Cancel button at modal in Payment & Review page
	 */
	void clickCancelButtonInModal();

	/**
	 * get error message at bottom displays in Payment & Review page
	 */
	String getErrorMessageAtBottom();

	/**
	 * click PayPal tab in Payment & Review page
	 */
	void clickPayPalTab();

	/**
	 * click check out with PayPal in Payment & Review page
	 */
	void clickCheckOutWithPayPal();

	/**
	 * verify PayPal tab displays in Payment & Review page
	 */
	boolean isPayPalTabDisplayed();

	/**
	 * convert Shipping Address in Payment & Review page for Mobile
	 */
	String[] convertShippingAddressForMobile(String[] shippingAddress);

	/**
	 * click remove 2nd card link in Payment & Review page
	 */
	void clickRemove2ndCardLink();

	/**
	 * verify remove 2nd card link displays in Payment & Review page
	 */
	boolean isRemove2ndCardLinkDisplayed();

	/**
	 * verify amount of first credit card displays in Payment & Review page
	 */
	boolean isFirstCreditCardAmountDisplayed();

	/**
	 * click Apply A Reward Card Gift Card Or Voucher link in Payment & Review page
	 */
	void clickApplyARewardCardGiftCardOrVoucherLink();

	/**
	 * select Edit Cart link by Recipient in Payment & Review page
	 */
	void selectLinkEditCartByRecipient(Recipient recipient);

	/**
	 * fill Email and Phone in Payment & Review page
	 */
	void fillEmailAndPhone(CustomerAddress billingAddress);

	/**
	 * get Extra Shipping Fee in Payment & Review page
	 */
	String getExtraShippingFee(String recipient);

	/**
	 * In Payment & Review page, get color of Shipping cost in Order total section
	 */
	String getColorOfShippingCostInOrderTotalsSection();

	/**
	 * In Payment & Review page, get recipient section title
	 */
	String getRecipientSectionTitle(String recipient);

	/**
	 * In Payment & Review page, verify "View All Shipping Options" link appears in
	 * My Cart
	 */
	boolean isViewAllShippingOptionsDisplayed(String recipient);

	/**
	 * In Payment & Review page, Shipping Options displays correctly
	 */
	boolean isShippingOptionsDisplayedCorrectly();

	/**
	 * In Payment & Review page, get Note of SKU display
	 */
	String getNoteOfSKU(SKU sku);

	/**
	 * In Payment & Review page, get Total Shipping Fee
	 */
	String getTotalShippingFee();

	/**
	 * In Payment & Review page,get shipping method list in Shipping And Delivery
	 * modal
	 */
	List<String> getShippingMethodsListInShippingAndDeliveryModal();

	/**
	 * In Payment & Review page, select shipping method in Shipping and Delivery
	 * modal
	 */
	String selectRandomShippingMethodInShippingAndDeliveryModal();

	String selectRandomShippingMethodInShippingAndDeliveryModal(List<String> shippingMethodsList);

	/**
	 * In Payment & Review page, get the cost of shipping method in Shipping and
	 * Delivery modal
	 */
	String getShippingMethodCostInShippingDeliveryModal(String shippingMethod);

	/**
	 * In Payment & Review page, get tax amount in Order Total
	 */
	String getTaxAmountInOrderTotal();

	/**
	 * In Payment & Review page, verify Pay With credit card section displays
	 */
	boolean isPayWithCreditCardSectionDisplayed();

	/**
	 * In Payment & Review page, verify reward card displays
	 */
	boolean isRewardCardDisplayed(String rewardCardCode);

	/**
	 * In Payment & Review page, remove the previously entered reward card
	 */
	void removeRewardCard(String rewardCardCode);

	/**
	 * In Payment & Review page, get selected Shipping method in Recipient section
	 */
	String getSelectedShippingMethod(String recipent);

	/**
	 * In Payment & Review page, get promotion message displays in Recipient section
	 */
	String getPromotionMessage(String recipient);

	/**
	 * In Payment & Review page, select random shipping method in Recipient section
	 */
	String selectRandomShippingMethod(String recipient);

	/**
	 * In Payment & Review page, get shipping method list in Recipient section
	 */
	List<String> getShippingMethodsList(String recipient);

	/**
	 * In Payment & Review page, select shipping method in Recipient section
	 */
	void selectShippingMethod(String recipient, String shippingMethod);

	/**
	 * In Payment & Review page, verify shipping method displays in Recipient
	 * section
	 */
	boolean isShippingMethodDisplayed(String recipient, String shippingMethod);

	/**
	 * In Payment & Review page, verify Promotion message displays in Shipping and
	 * Delivery popup
	 */
	boolean isPromotionsMsgDisplayedInShippingAndDeliveryPopup(List<String> lstShippingOptions);

	/**
	 * In Payment & Review page, click details link in Recipient section
	 */
	void clickDetailsLink(String recipient);

	/**
	 * In Payment & Review page, verify details pop up displays
	 */
	boolean isDetailsPopupDisplayed();

	/**
	 * In Payment & Review page, get Incentive Offer Terms and Condition message
	 * display
	 */
	String getIncentiveOfferTermsAndConditionsMessage();

	/**
	 * In Payment & Review page, get Checked Shipping method in Recipient section
	 */
	boolean getCheckedShippingMethod(String recipent, String shippingMethod);

	/**
	 * In Payment & Review page, We are sorry modal displays when filling invalid
	 * reward card code or expired reward card code
	 */
	boolean isWeAreSorryModalDisplayed();

	/**
	 * In Payment & Review page, verify remaining card balance label displays
	 */
	boolean isLabelRemainingCardBalanceDisplayed();

	/**
	 * In Payment & Review page, get promotion message after updating shipping
	 * method in Recipient section
	 */
	String getPromotionMsgAfterUpdatingShippingMethod(String recipent);

	/**
	 * In Payment & Review page, verify shipping method in Shipping and Delivery pop
	 * up selects
	 */
	boolean isShippingMethodInShippingAndDeliveryPopupSelected(String shippingMethod);

	/**
	 * In Payment & Review page, verify title of Shipping and Delivery displays
	 * correctly
	 */
	boolean isTitleOfShippingAndDeliveryDisplayedCorrectly();

	/**
	 * In Payment & Review page, verify shipping method displays on pop up
	 */
	boolean isShippingMethodDisplaysOnPopup(String shippingMethod);

	/**
	 * In Payment & Review page, get shipping cost by shipping method in Recipient
	 * section
	 */
	String getShippingCostByShippingMethod(String recipient, String shippingMethod);

	/**
	 * In Payment & Review page, get shipping method by day of week
	 */
	String getShippingMethodByDayOfWeek();

	Calendar getDeliveryDate(String recipient);

	/**
	 * In Payment & Review page, get promotion text of shipping method
	 */
	String getPromotionTextOfShippingMethod(String shippingMethod);
}
