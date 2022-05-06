package com.omahasteaks.tests.SLRGold;

 
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.inject.Inject; 
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.tests.SteakloverRewardsMembership.TestBase_SLR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger; 

public class TC_GLD_002RenewModalIsNotShownIfNotWithinRenewalPeriod  extends TestBase_SLR  {
  
    @Inject
    MyAccountPage myAccountPage;
    
    @Test
    public void TC_GLD_002Renew_Modal_Is_Not_Shown_If_Not_Within_Renewal_Period() {
		initTestCaseData();
		signIn(account);
		Common.delay(30);
		Assert.assertFalse(myAccountPage.isRenewGoldMbrModalShown(),"Gold Membership modal is not shown after signing into account");
	}


	private void initTestCaseData() {
		Logger.tc("RECOGNIZED Gold account outside renewal period - Renewal modal is NOT displayed after signing into membership.");
		Logger.to("Verify the 'SLR Gold Renewal' link is NOT displayed after signing into membership");
		account = Constants.LIST_ACCOUNTS.setAccountByEmail("slrgold01@omahasteaks.com");
		Constants.DB.updateGoldExpireDate(account.getEmail(), 80);
		Common.modalDialog.closeSavorDialog();
 	}
}
