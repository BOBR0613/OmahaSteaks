package com.omahasteaks.utils.helper;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Frame;
import com.logigear.driver.DriverUtils;
import com.logigear.locator.Interface;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants;

public class ModalDialog {
	private Interface interfaces = new Interface(this.getClass(), Constants.LOCATOR_PATH);
	// private Button btnCloseModal = new Button(interfaces.get("btnCloseModal"));
	private Element modalDialog = new Element(interfaces.get("modalDialog"));
	private Element titleCandidate = new Element(interfaces.get("titleCandidate"));
	private List<String> lstTitles;

	public ModalDialog() {
		loadWellknownDialogLocaltor();
	}

	private void loadWellknownDialogLocaltor() {
		lstTitles = new ArrayList<String>();
		String jsonPath = "src/test/resources/data/ModalDialog.json";
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(jsonPath));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray titles = (JSONArray) jsonObject.get("expectedTitles");
			for (int i = 0; i < titles.size(); i++)
				lstTitles.add(titles.get(i).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close the modal dialog if it is not our expected dialog. The expected dialog
	 * is specified by the title or main information inside dialog.
	 */
	public void closeUnknownModalDialog() {
		closeAppBanner();
		if (isModalDialogAppeared()) {
			String txtTitle = detectDialogTitle();
			if (lstTitles.contains(txtTitle) || txtTitle.startsWith("Create Your Own Trio for"))
				return;
			closeModalDialog();
		}
	}
	
	public void closeSavorDialog() {
		Common.waitForDOMChange();
		Frame iFrameSavor = new Frame(interfaces.get("iFrameSavor"));
 		if (iFrameSavor.isEnabled()) System.out.println("iFrameSavor is enabled");
		else System.out.println("iFrameSavor is NOT enabled");
		if (iFrameSavor.isDisplayed()) System.out.println("iFrameSavor is displayed");
		else System.out.println("iFrameSavor is NOT displayed");
        if (!iFrameSavor.isDisplayed()) return;
        
		Element btnCloseSavor = new Element(interfaces.get("btnCloseSavor"));
		
 			if (iFrameSavor.getElements().size() > 0) {
 				iFrameSavor.switchTo();
				Common.waitForElementExistedInDOM(btnCloseSavor);
				btnCloseSavor.clickByJs();
				iFrameSavor.switchToMainDocument();
			}
 	}

	public void closeModalDialog() {
		try {
			DriverUtils.execJavaScript("os_modal_close();");
			Common.waitForDOMChange();
		} catch (Exception e) {
			System.out.println("Getting error when closing modal dialog: " + e.getMessage());
		}
	}

	/**
	 * Check if the modal dialog appears on screen.
	 * 
	 * @return
	 */
	public boolean isModalDialogAppeared() {
		modalDialog.waitForDisplay(1);
		return modalDialog.isDisplayed();
	}

	/**
	 * we detect the title of some special dialog which we should not close it
	 * automatically.
	 * 
	 * @return
	 */
	public String detectDialogTitle() {
		// "Added to Cart" dialog
		// "Exclusive Offer"
		// 'Confirm This Cart Update' dialog and "Create Your Own Trio for ..." dialog
		// "Send To Someone Else" dialog or 'Added To Cart' (2) dialog
		for (String locator : titleCandidate.getLocatorAsArray()) {
			String strInnerText = getCandidateInnerText(locator);
			if (strInnerText.isEmpty() == false)
				return strInnerText;
		}
		return "";
	}

	/**
	 * 
	 * @param xpath
	 * @return
	 */
	private String getCandidateInnerText(String xpath) {
		Element elemTitle = new Element(By.xpath(xpath));
		if (elemTitle.isDisplayed()) {
			String txtTitle = elemTitle.getText().trim();
			if (txtTitle.isEmpty() == false)
				return txtTitle;
		}
		return "";
	}

	/** Close the App Banner on Mobile if it displays */
	public void closeAppBanner() {
		Frame iFrameAppBanner = new Frame(interfaces.get("iFrameAppBanner"));
		Element btnCloseAppBanner = new Element(interfaces.get("btnCloseAppBanner"));
		if (Common.MODE.getRunningMode().equals(Constants.PLATFORM_MOBILE))
		{
			if (iFrameAppBanner.getElements().size() > 0) {
				iFrameAppBanner.switchTo();
				Common.waitForElementExistedInDOM(btnCloseAppBanner);
				btnCloseAppBanner.clickByJs();
				iFrameAppBanner.switchToMainDocument();
			}
		}
		
	}
}
