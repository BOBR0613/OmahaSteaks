package com.omahasteaks.tests.Checkout_2Step.NegativeCases;
/*
 * package tests.Checkout_2Step.NegativeCases;
 * 
 * import static org.testng.Assert.assertEquals;
 * 
 * import org.testng.annotations.Test;
 * 
 * import com.google.inject.Inject;
 * 
 * import data.ListSKUs; import data.ENUM.Recipient; import data.objects.Account; import data.objects.SKU; import pages.AccountPage2SC; import pages.GeneralPage; import pages.ProductPage; import pages.SearchResultPage; import pages.ShoppingCartPage; import tests.TestBase_2SC; import utils.common.Common; import utils.common.Constants; import utils.common.Messages; import utils.helper.Logger;
 * 
 * public class TC_2SCON_009_AccountStepWithEmptyOrInvalidEmailAndPassword extends TestBase_2SC {
 * 
 * @Inject Account account;
 * 
 * @Inject ListSKUs myCart;
 * 
 * @Inject SKU mySku;
 * 
 * @Inject GeneralPage generalPage;
 * 
 * @Inject ShoppingCartPage shoppingCartPage;
 * 
 * @Inject SearchResultPage searchResultPage;
 * 
 * @Inject ProductPage productPage;
 * 
 * @Inject AccountPage2SC accountPage2SC;
 * 
 * @Test public void TC_2SCON_009_Account_Step_With_Empty_Or_Invalid_Email_And_Password() { initTestCaseData(); addFirstSKUToCart(mySku, myCart); createAccountWithEmptyEmailAndPassword(); verifyWarningOfEmailAddressFieldDisplaysproperly(); verifyWarningOfPasswordFieldDisplaysproperly(); createAccountWithOnly8LettersInPassword(); verifyWarningOfPasswordFieldDisplaysproperly(); createAccountWithOnly8NumbersInPassword(); verifyWarningOfPasswordFieldDisplaysproperly(); }
 * 
 * // ================================================================================ // Test Case Methods // ================================================================================ private void addFirstSKUToCart(SKU sku, ListSKUs lSku) { generalPage.search("AutoShip"); Logger.info("Add the first SKU and ship to Myself\n" + "- If Exclusive Offer (Upsell offer) appears, click \"No Thanks\"\n" + "- Click \"Checkout\""); searchResultPage.selectFirstItem(); productPage.addFirstSKUToCart(sku); productPage.selectExclusiveOffer(false); productPage.checkOut(); shoppingCartPage.checkOut(); }
 * 
 * private void createAccountWithEmptyEmailAndPassword() { Logger.info("In Account Page:\n" + " - Leave \"Email Address\" and \"Password\" empty\n" + " - Click \"Create Account & Checkout\" button"); account.setPassword(""); account.setEmail(""); accountPage2SC.createAccount(account); }
 * 
 * private void createAccountWithOnly8LettersInPassword() { Logger.info("\"In Account Page:\n" + " - Enter only 8 letters into Password textbox\n" + " - Click \"Create Account & Checkout\" button\""); account.setPassword(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_LETTER_CHARS, 8)); accountPage2SC.createAccount(account); }
 * 
 * private void createAccountWithOnly8NumbersInPassword() { Logger.info("\"In Account Page:\n" + " - Enter only 8 numbers into Password textbox\n" + " - Click \"Create Account & Checkout\" button\""); account.setPassword(Common.generateRandomStringFromCandidateChars(Constants.CANDIDATE_NUMBER_CHARS, 8)); accountPage2SC.createAccount(account); }
 * 
 * private void initTestCaseData() { Logger.info("TC_2SCON_009	Warning message with suitable information appears when filling blank or invalid value in email and password in Account page"); Logger.info("TO_2SCON_1	In Account step, warning message when leaving email address empty\n" + "TO_2SCON_2	In Account step, warning message when leaving password empty\n" + "TO_2SCON_3	In Account step, warning message when filling 8 letters\n" + "TO_2SCON_4	In Account step, warning message when filling 8 numbers"); mySku.setRecipient(Recipient.MYSELF); account.initRandomAccount(); }
 * 
 * private void verifyWarningOfEmailAddressFieldDisplaysproperly() { Logger.verify("Verify the warning of \"Email Address\" field displays properly"); assertEquals(accountPage2SC.getEmailAddressErrorMessage(), Messages.EMPTY_EMAIL_ERROR_MESSAGE); }
 * 
 * private void verifyWarningOfPasswordFieldDisplaysproperly() { Logger.verify("Verify the warning of \"Password\" field displays properly"); assertEquals(accountPage2SC.getPasswordErrorMessage(), Messages.INVALID_PASSWORD_ERROR_MESSAGE); } }
 */