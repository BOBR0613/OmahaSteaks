package com.omahasteaks.tests.DepartmentsPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.inject.Inject;
import com.omahasteaks.data.ListImageLink;
import com.omahasteaks.data.objects.ImageLink;
import com.omahasteaks.page.DepartmentsPage;
import com.omahasteaks.page.GeneralPage;
import com.omahasteaks.page.HomePage;
import com.omahasteaks.tests.TestBase_2SC;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;
import com.omahasteaks.utils.helper.Logger;

public class TC_DP_001_CheckAllLinksAndImagesInDepartmentPage extends TestBase_2SC {

    String linkStatus = "";
    int totalBrokenLink = 0;
    
    @Inject
    HomePage homePage;

    @Inject
    GeneralPage generalPage;

    @Inject
    DepartmentsPage departmentsPage;

    @Inject
    ListImageLink listImageLinkInDepartments;

    @Test
    public void TC_DP_001_Check_All_Links_And_Images_In_Department_Page() {

	Logger.tc("TC_DP_001 - Check all links and images in department page");
	Logger.to("TO_DP_01 Check all links in Department page");
	Logger.to("TO_DP_02 Check all images in Department page");
	Logger.to("TO_DP_03 In the header, drop down menu exists for each department");

	getAllLinksAndImages();

	verifyAllLinksAreValid();

	verifyAllImagesAreValid();

	logLink();
	
	assertEquals(totalBrokenLink, 0, "There is/are " + totalBrokenLink +" links/images broken");
    }

    public void getAllLinksAndImages() {

	List<String> lstDepartments = homePage.getDepartmentsName();

	for (int i = 0; i < lstDepartments.size(); i++) {
	    Logger.info("Get all links and images in " + "\"" + lstDepartments.get(i).toString() + "\"");

	    generalPage.goToDepartmentPage(lstDepartments.get(i).toString());

	    Common.modalDialog.closeModalDialog();

	    listImageLinkInDepartments.addAll(departmentsPage.getAllLinksAndImageBySection(lstDepartments.get(i).toString()));
	}

    }

    private void verifyAllLinksAreValid() {
    	Logger.verify("Check all links are valid");

    	for (ImageLink imgLink : listImageLinkInDepartments.getListImageLink()) {
    		if (!imgLink.getHref().equals("")) {
    			if (imgLink.getHref().equals(Constants.IGNORE_PRESS_LINK))
    				continue;
    			String code = Common.getResponseCodeOfLink(imgLink.getHref());
    			linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getHref(), code));
    			if (Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code.trim())) {
    				assertTrue(Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code.trim()));
    			}
    			else { 
    				if (!imgLink.getHref().contains("\"") && !imgLink.getHref().equalsIgnoreCase("https://osidev.omahasteaks.com/gifs/130x130/")) {
    					code = Common.getResponseCodeOfLink(imgLink.getHref());
    					linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getHref(), code));
    					if (Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code.trim()))
    						assertTrue(Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code));
    					else {
    						String departmentName = imgLink.getText().split("/")[0];
    						Logger.warning("In department : " + departmentName + "- \nLink: \"" + imgLink.getHref() + "\" is a broken link. \nStatus - code: " + code);
    						totalBrokenLink++;
    					} 
    				}
    			}
    		}
    	}
    }

    private void verifyAllImagesAreValid() {
    	Logger.verify("Check all images are valid");

    	for (ImageLink imgLink : listImageLinkInDepartments.getListImageLink()) {

    		if (!imgLink.getSrc().equals("")) {

    			String code = Common.getResponseCodeOfLink(imgLink.getSrc());
    			linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getSrc(), code));
    			if (Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code))
    				assertTrue(Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code));
    			else {
    				//Common.delay(15);
    				code = Common.getResponseCodeOfLink(imgLink.getSrc());
    				linkStatus = linkStatus.concat(Common.generateLogForLink(imgLink.getSrc(), code));
     				if (Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code))
    					assertTrue(Constants.LIST_VALID_HTTP_STATUS_CODE.contains(code));
    				else {
        				if (!imgLink.getSrc().equalsIgnoreCase("https://osidev.omahasteaks.com/gifs/130x130/")) {
    					   String departmentName = imgLink.getText().split("/")[0];
    					   Logger.warning("IN department : " + departmentName + "\nImage: \"" + imgLink.getSrc() + "\" is a broken image. \nStatus - code: " + code);
    				   	   totalBrokenLink++;
        				}
    				}

    			}

    		}
    	}
    }

    private void logLink() {
	Logger.logFile("DepartmentPage.txt", linkStatus.toString());
    }
}
