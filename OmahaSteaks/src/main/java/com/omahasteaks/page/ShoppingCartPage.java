package com.omahasteaks.page;

import com.omahasteaks.data.ListSKUs;
import com.omahasteaks.data.enums.Recipient;
import com.omahasteaks.data.objects.Item;
import com.omahasteaks.data.objects.SKU;

public interface ShoppingCartPage extends GeneralPage {

    /**
     * In Shopping Cart page, add SKU in Cart Special section to Recipient's cart
     */
    void addInCartSpecial(Recipient recipient);

    /**
     * In Shopping Cart page, add SKU in Special Cart Bonus section to Recipient's
     * cart
     */
    void addSpecialCartBonus(Recipient recipient);

    /**
     * In Shopping Cart page, add first SKU in Special Cart Bonus section to
     * Recipient's cart
     */
    void addFirstSKUInSpecialCartBonus(Recipient recipient);

    /**
     * In Shopping Cart page, add the up sell SKU to Recipient's cart
     */
    void addTheUpsellSku(Recipient recipient);

    /**
     * In Shopping Cart page, click x button to close modal dialog
     */
    void closeModalDialog();

    /**
     * In Shopping Cart page, get added SKU id from modal
     */
    String getAddedSkuIdFromModal();

    /**
     * In Shopping Cart page, get added SKU name from modal
     */
    String getAddedSkuNameFromModal();

    /**
     * In Shopping Cart page, get Empty message text displayed when no have SKU to
     * cart
     */
    String getEmptyMessageText();

    /**
     * In Shopping Cart page, get quantity of SKU
     */
    int getQuantityOfSku(SKU sku);

    /**
     * In Shopping Cart page, verify Item adds to Recipient's cart
     */
    boolean isItemAddedToCart(Item item);

    boolean isOnShoppingCartPage();

    /**
     * In Shopping Cart page, verify SKU (SKU name) in Special Cart adds to
     * Recipient's cart
     */
    boolean isSkuInSpecialCartAdded(Recipient recipient, String skuName);

    /**
     * In Shopping Cart page, verify SKU (SKU ID) in Special Cart adds to
     * Recipient's cart
     */
    Boolean isSkuByIdAdded(Recipient recipient, String skuId);

    Boolean isSkuByIdAdded(Recipient recipient, String skuId, String deliveryFrequencyAutoShip);
    
    boolean isJoinSlrGoldButtonShown();

    /**
     * In Shopping Cart page, remove Item from cart
     */
    void removeItem(Item item);

    /**
     * 
     * In Shopping Cart page, remove Item from cart
     * 
     * @param item
     *            SKU want to remove from cart <br>
     * @param confirm
     *            If yes, SKU will be removed from cart. <br>
     *            If no, SKU will be retained in the shopping cart <br>
     */
    void removeItem(Item item, boolean confirm);

    /**
     * In Shopping Cart page, remove SKU from Recipient's cart by SKU ID
     */
    void removeSkuById(Recipient recipient, String skuId);

    void selectAutoShipOption(SKU sku, boolean isAutoShip, String deliveryFrequency);
    
    void clickContinue();

    boolean isCheckoutButtonDisplayed();
    
    /**
     * In Shopping Cart page, update quantity of SKU
     */
    void updateQuantityOfSku(SKU sku, int quantity);

    /**
     * In Shopping Cart page, wait for cart empty after removing SKU to cart
     */
    void waitForCartEmpty();

    /**
     * In Shopping Cart page, wait for Shopping Cart page displays
     */
    void waitForShoppingCartPageDisplays();

    /**
     * In Shopping Cart page, In My Cart, click on "Send to someone else" link".
     *
     * In "Send To Someone Else" pop up: select new Recipient and click "Add To
     * Cart" Button. In "Added To Cart" pop up, click "Close" button
     */
    void addSkuToSomeoneElse(SKU sku, Recipient recipient);

    /**
     * In shopping Cart, click on "Send to someone else" link. In "Send To Someone
     * Else" pop up:"select "Move To" radio button" and select "Someone Else" <br>
     * filling recipient's name in "Recipient's Name" text box Click \"MOVE TO
     * CART\" button
     */
    void selectMoveToAndSelectShipTo(SKU sku, Recipient recipient);

    /**
     * In shopping Cart, click on "Send to someone else" link. In "Send To Someone
     * Else" pop up:"select "Move To" radio button" and select "Someone Else" <br>
     * filling recipient's name in "Recipient's Name" text box Click \"MOVE TO
     * CART\" button
     */
    void moveSkuToSomeoneElse(SKU sku, Recipient recipient);

    /**
     * In shopping Cart, click on "Send to someone else" link. In "Send To Someone
     * Else" pop up:"select "Add To" radio button" and select "Someone Else" <br>
     * filling recipient's name in "Recipient's Name" text box Click \"ADD TO CART\"
     * button
     */
    void selectAddToAndSelectShipTo(SKU sku, Recipient recipient);

    /**
     * In Shopping Cart page, click remove to remove SKU from cart by SKU ID
     * 
     * @param isClickYesButton
     *            If yes, SKU will be removed from cart. <br>
     *            If no, SKU will be retained in the shopping cart <br>
     */
    void removeSkuById(SKU sku, boolean isClickYesButton);

    /**
     * In Shopping Cart page, In "Send To" section: select "Someone Else" and fill
     * "Recipient's Name" text box Click "Add To Cart" button
     * 
     */
    void sendCartTo(Recipient fromCart, Recipient toCart);

    /**
     * In Shopping Cart page, get error message displayed
     */
    String getErrorMessageOnPage();

    /**
     * In Shopping Cart page, uncheck All Item in Special Cart Bonus and select
     * Recipient then click add to Recipient's cart
     */
    void uncheckAllItemAndAddSpecialCartBonus(Recipient recipient);

    /**
     * In Shopping Cart page, get error message displayed In Special Cart Bonus
     */
    String getErrorMessageInSpecialCartBonus();

    /**
     * In Shopping Cart page, get SKU name in Special Cart Bonus added to
     * Recipient's cart
     */
    String getSkuNameInSpecialCartBonus(Recipient recipient);

    /**
     * In Shopping Cart page, get list SKU added to recipient's cart Recipient's
     * cart
     */
    void getAddedToCartSKUListInShoppingCartPage(ListSKUs addedToCartSKUsList, Recipient recipient);

    /**
     * In Shopping Cart page, get Cart Shipment title by Recipient
     */
    String getCartShipmentTitleByRecipient(Recipient recipient, int shpmnt);

    /**
     * In Shopping Cart page, get added SKU name from added to cart
     */
    String getAddedSkuNameFromAddedToCart();

    /**
     * In Shopping Cart page, click Check Out With PayPal to check out from Shopping
     * cart page
     */
    void clickCheckOutWithPayPal();

    /**
     * In Shopping Cart page, verify Check Out With PayPal button displays
     */
    boolean isCheckOutWithPayPalButtonDisplayed();

    /**
     * In Shopping Cart page, click Create Your Own Trio link
     */
    void clickCreateYourOwnTrioLink();

    /**
     * In Shopping Cart page, add SKU in Create Your Own Trio modal
     */
    void addCreateYourOwnTrioItem(SKU sku);

    /**
     * In Shopping Cart page, select SKU In Create Your Own Trio modal
     */
    void selectRandomItemsInCreateYourOwnTrioModal(int numberOfItems);

    /**
     * In Shopping Cart page, select Edit in SKU need to edit
     */
    void selectEditSku(SKU sku);

    /**
     * In Shopping Cart page, verify Sub Items displays in cart
     */
    boolean isSubItemsDisplayed(SKU sku);

    /**
     * In Shopping Cart page, click Enter Gift Card Or Voucher link to fill Gift
     * Card or Voucher code
     */
    void clickEnterGiftCardOrVoucherLink();

    /**
     * In Shopping Cart page, get value of Items Sub total displayed
     */
    String getValueOfItemSubtotal();

    /**
     * In Shopping Cart page, verify Reward Card code display after filling Reward
     * Card code successful
     */
    boolean isRewardCardDisplayed(String rewardCardCode);

    /**
     * In Shopping Cart page, remove Reward Card code when clicking remove link in
     * Reward Card code
     */
    void removeRewardCard(String rewardCardCode);

    /**
     * In Shopping Cart page, get Remaining Card Balance value displayed In Pop up
     */
    String getRemainingCardBalanceInPopup();

    /**
     * In Shopping Cart page, get Amount Applied value displayed In Pop up
     */
    String getAmountAppliedInPopup();

    /**
     * In Shopping Cart page, verify Gift Card are added for recipient
     */
    boolean isGiftCardByNameAdded(String recipient, String itemName);

    /**
     * In Shopping Cart page, verify Label Additional Saving displays
     */
    boolean isLabelAdditionalSavingsDisplayed();

    /**
     * In Shopping Cart page, verify We Are Sorry modal displays when filling Reward
     * Card code successful
     */
    boolean isWeAreSorryModalDisplayed();

    /**
     * In Shopping Cart page, click Check Out button
     */
    void checkOut();

    /**
     * In Shopping Cart page, verify The Upsell SKU displays
     */
    boolean isTheUpsellSkuDisplayed();

    /**
     * In Shopping Cart page, verify Treat Yourself module displays
     */
    boolean isTreatYourselfModuleDisplayed();

    /**
     * In Shopping Cart page, add SKU in Treat Yourself module to cart
     */
    void addTreatYourself();
    
    /**
     * In Shopping Cart page, Check if minimum threshold message is shown.
     */
	boolean isMinThresholdMessageShown();

    /**
     * In Shopping Cart page, get SKU ID of SKU in Treat Yourself module
     */
    String getSkuID_TreatYourself();

    /**
     * In Shopping Cart page, verify Send To Someone Else link displays
     */
    boolean isLinkSendToSomeOneElseDisplayed(SKU sku);

    /**
     * In Shopping Cart page, verify Myself Cart section displays at the top of
     * Shopping Cart section
     */
    boolean isMyselfCartSectionAddedAtTheTopOfShoppingCart();

    /**
     * In Shopping Cart page, verify Cart Shipment display in Recipient's Cart
     */
    boolean isCartShipmentDisplayed(Recipient recipient);
    
    /**
     * Click Steaklover Reward option link displays in Steaklover Rewards section
     */
    void clickSteakloverRewardOption(String option);
    
    /**
     * Verify Steaklover Reward option link displays in Steaklover Rewards section
     */
    boolean isSteakloverRewardOptionDisplayed(String option);
    
    /**
     * Verify Select Steaklover Rewards pop up displays after clicking Redeem Point link
     */
    boolean isSelectSteakloverRewardsModalDisplayed();
    
    /**
     * Get Your Balance point in Steaklover Rewards section
     */
    String getYourBalancePoint();
    
    /**
     * add SKU in Select Steaklover Rewards pop up to cart
     */
    void addSelectSteakloverRewardsSKU(Recipient recipient);
    
    /**
     * get SKU name in Select Steaklover Rewards pop up
     */
    String getSKUNameInSelectSteakloverRewardsModal();
    
    /**
     * get SKU points in Select Steaklover Rewards pop up
     */
    String getSKUPointInSelectSteakloverRewardsModal();
    
    /**
     * verify Steaklover Reward pop up displays
     */
    boolean isSteakloverRewardsModalDisplayed();
    
    /**
     * get SKU name in Steaklover Rewards pop up
     */
    public String getAddedSKUNameInSteakloverRewardsModal();
    
    /**
     * get SKU points in Steaklover Rewards pop up
     */
    String getSKUPointInSteakloverRewardsModal();
    
    /**
     * Get premise text of Cart summary section
     */
    public boolean isTheFreeShippingMessageDisplayed(Recipient recipientName);
    
    /**
     * Verify the monthly free food is displayed when logging in by Steaklover rewards Gold account
     */
    public Boolean isFreeFoodAddedByName(Recipient recipient, String nameOfSKU);
    public Boolean isFreeFoodAddedByMembership(Recipient recipient);
    
    /**
     * Get message displays in Steaklover Rewards section
     */
    String getMessageInSteakloveRewardsSection();
    
    public String getTheQuantityOfFreeFood(SKU sku);
    
    public Double getPriceOfFreeFood(SKU sku);
    
    public boolean isRemoveLinkInFreeFoodDisplayed(Recipient recipient, String skuName);
    
    public boolean isQuantityComboBoxDisplayed(Recipient recipient, String skuName);
    
    void selectFirstSKUInSelectSteakloverRewards();
    
    String getAvailablePoints();
    
    boolean isFreeFoodDisableAfterSelectedOneSKU();
    
    void clickAddToCartInSelectYourSlrRewardsModal();
    
    void joinSlrGoldMembershipToCart();
    
    boolean joinedSlrGold();
    
    boolean isChooseYourRewardsHighlighted();
    
    void selectRecipientInSeletecSteakloverRewardsModal(Recipient recipient);

    public void removeFreeFood(SKU sku);
    
    public boolean isSignInLinkDisplayed();

	boolean isSLROptionDisplayed(String string);
}
