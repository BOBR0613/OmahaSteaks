package com.omahasteaks.tests.SLRGold;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.inject.Inject; 
import com.omahasteaks.page.MyAccountPage;
import com.omahasteaks.tests.SteakloverRewardsMembership.TestBase_SLR;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger; 

public class TC_GLD_001RenewModalIsShownIfWithinRenewalPeriod  extends TestBase_SLR  {
  
    @Inject
    MyAccountPage myAccountPage;
   
    @Test
    public void TC_GLD_001Renew_Modal_Is_Shown_If_Within_Renewal_Period() {
		initTestCaseData();
		signIn(account);
		Assert.assertTrue(myAccountPage.isRenewGoldMbrModalShown(),"Gold Membership Renewal modal is NOT shown when within the renewal period");
	}


	private void initTestCaseData() {
		Logger.tc("RECOGNIZED Gold account within renewal period - Renewal modal is displayed after signing into membership.");
		Logger.to("Verify the 'SLR Gold Renewal' link is displayed after signing into membership");
		account = Constants.LIST_ACCOUNTS.setAccountByEmail("slrgold01@omahasteaks.com");
		Constants.DB.updateGoldExpireDate(account.getEmail(), 20);
		Common.modalDialog.closeSavorDialog();
 	}
}
