package com.omahasteaks.utils.common;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.google.gson.JsonElement;
import com.logigear.control.base.imp.BaseControl;
import com.logigear.control.base.imp.Clickable;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.ComboBox;
import com.logigear.control.common.imp.TextBox;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.utils.helper.Logger;
import com.omahasteaks.utils.helper.ModalDialog;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Common {
	// ================================================================================
	// Public variable
	// ================================================================================
	public static RunningMode MODE = new RunningMode();
	public static ModalDialog modalDialog = new ModalDialog();
	public static String currentTitlePage;
	public static String currentUrlPage;
	private static int TRY_LIMIT = 10;
	private static String FREED_SCRIPT_ERROR = "Can't execute code from a freed script";

	   
	// ================================================================================
	// Action Hub
	// ================================================================================
	public enum ActionType {
		BUILT_IN("built-in"), JS("javascript"), ACTION("action");

		public String name;

		ActionType(String name) {
			this.name = name;
		}

		public static ActionType fromString(String parameterName) {
			if (parameterName != null) {
				for (ActionType objType : ActionType.values()) {
					if (parameterName.equalsIgnoreCase(objType.name)) {
						return objType;
					}
				}
			}
			return BUILT_IN;
		}
	}

	public enum HubPattern {
		DEFAULT, PATTERN1;
	}

	public static ActionType getClickTypeFromPattern(HubPattern pattern) {
		String keyName = RunningMode.getCurrentPlatform().toLowerCase() + "-" + DriverUtils.getDriverType();
		String patternName = pattern.toString().toLowerCase();

		JsonElement patternObject = Constants.CLICK_HUB.get(patternName);
		if (patternObject == null) {
			patternObject = Constants.CLICK_HUB.get("default");
			if (patternObject == null) {
				throw new NullPointerException("Pattern '" + patternName + "' and 'default' didn't exist");
			}
		}

		JsonElement actionObject = patternObject.getAsJsonObject().get(keyName);
		if (actionObject == null) {
			actionObject = patternObject.getAsJsonObject().get("other");
			if (actionObject == null) {
				throw new NullPointerException("Key '" + keyName + "' and 'other' didn't exist in pattern '" + patternName + "' and 'default'");
			}
		}

		return ActionType.fromString(actionObject.getAsString());
	}

	// ================================================================================
	// Utility
	// ================================================================================
	public static String captureScreenshot(String filename, String filepath) {
		String path = "";
		try {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DriverUtils.getWebDriver());
			path = System.getProperty("user.dir") + "/" + filepath + "/" + filename + ".png";
			ImageIO.write(screenshot.getImage(), "PNG", new File(path));
		} catch (Exception e) {
			System.out.println("An error occurred when capturing screen shot: " + e.getMessage());
		}
		return path;
	}

	public static String getRemoteInfo() {
		StringBuilder str = new StringBuilder();
		try {
			CommandExecutor ce = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCommandExecutor();
			URL url = ((HttpCommandExecutor) ce).getAddressOfRemoteServer();
			InetAddress host = InetAddress.getByName(url.getHost());
			Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
			str.append("<br>Execution Information:");
			str.append("<br>- Machine IP: <b>" + host.getHostAddress() + " - " + host.getHostName() + "</b>");
			str.append("<br>- Browser: <b>" + caps.getBrowserName().toUpperCase());
			str.append(" - " + caps.getVersion() + "</b>");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public static boolean isMacExecution() {
		String OS = System.getProperty("os.name").toLowerCase();
		return (OS.indexOf("mac") >= 0);
	}

	public static String convertImageToURI(String imagePath) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		BufferedImage img;
		File image = new File(imagePath);
		try {
			img = ImageIO.read(image);
			ByteArrayOutputStream convert = new ByteArrayOutputStream();
			ImageIO.write(img, "png", convert);
			String data = DatatypeConverter.printBase64Binary(convert.toByteArray());
			return data;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 1 && num <= 12) {
			month = months[num - 1];
		}
		return month;
	}

	public static String screenshotURI(String imagePath) {
		String randomPopUpId = "id" + UUID.randomUUID().toString();
		String randomButtonId = randomPopUpId + "button";
		String imageString = "data:image/png;base64," + convertImageToURI(imagePath);
		String htmlScript = "<script>$(document).ready(function(){$( \"#" + randomPopUpId + "\" ).dialog({ autoOpen: false });$( \"#" + randomPopUpId + "\" ).dialog({width:1000},{height:700});$( \"#" + randomButtonId + "\" ).click(function() {$( \"#" + randomPopUpId + "\" ).dialog( \"open\" );});});</script></br><img id=\"" + randomButtonId + "\" src=\"" + imageString + "\" style=\"border: 4px solid #f6f7fa;width: 150px;cursor: zoom-in;display: block;margin-top: 15px;\"/></br><div style=\"width: 50%; margin: 0 auto;\" id=\"" + randomPopUpId + "\" > <a href=\"#" + randomPopUpId + "\"  class=\"ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right\"></a><img style=\"width:800px;height:600;\"  src=\"" + imageString + "\"/></div>";
		return htmlScript;
	}

	public static String getNowTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getCSTTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getRandomString(String prefix) {
		return prefix.concat(new SimpleDateFormat("HHmmssSSS").format(new Date()));
	}

	@SuppressWarnings("deprecation")
	public static Date randomBirthday() {
		int curYear = new Date().getYear();
		long minEpoch = new Date(curYear - 70, 0, 1).getTime();
		Random rd = new Random();
		long rdResult = minEpoch + (Math.abs(rd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
		return new Date(rdResult);
	}

	public static Calendar randomNewBirthday() {
		GregorianCalendar gc = new GregorianCalendar();
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = randBetween(1900, curYear - 1);
		int month = randBetween(1, 12);
		int dayOfMonth = randBetween(1, gc.getActualMaximum(month));
		gc.set(year, month, dayOfMonth);
		return gc;
	}

	public static Calendar randomNewBirthday(boolean over21) {
		GregorianCalendar gc = new GregorianCalendar();
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int year;
		if (over21)
			year = randBetween(1900, curYear - 21);
		else
			year = randBetween(curYear - 20, curYear - 1);
		int month = randBetween(1, 12);
		int dayOfMonth = randBetween(1, gc.getActualMaximum(month));
		gc.set(year, month, dayOfMonth);
		return gc;
	}

	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	public static int randomYearOfFuture() {
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		Random rd = new Random();
		int yResult = curYear + rd.nextInt(10) + 1;
		return yResult;
	}

	public static int getCurrentYear() {
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		return curYear;
	}

	public static String randomPhoneNumber() {
		Random rand = new Random();
		int num1 = 6 * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
		int num2 = rand.nextInt(743);
		int num3 = rand.nextInt(10000);
		DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
		DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
		return df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
	}

	public static String generateRandomStringFromCandidateChars(String CANDIDATE_FULL_CHARS, int length) {
		String SALTCHARS = CANDIDATE_FULL_CHARS;
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	public static boolean isValidDayForCOP011() {
		Calendar calendar = Calendar.getInstance();
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		TimeZone fromTimeZone = calendar.getTimeZone();
		TimeZone toTimeZone = TimeZone.getTimeZone("CST");
		calendar.setTimeZone(fromTimeZone);
		calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
		if (fromTimeZone.inDaylightTime(calendar.getTime())) {
			calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
		}
		calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
		if (toTimeZone.inDaylightTime(calendar.getTime())) {
			calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
		}
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String resultTime = formatter.format(calendar.getTime()).toString();
		return ((dayOfWeek == 5 && resultTime.compareTo("09:00:00") > 0) || (dayOfWeek == 6 && resultTime.compareTo("09:00:00") < 0));
	}

	// ================================================================================
	// Page action
	// ================================================================================

	public static void setMobileViewMode() {
		DriverUtils.navigate(Constants.OMAHA_URL_MOBILE_MODE);
		Common.waitForPageLoad();
	}

	public static int getStartLoadingTimeout() {
		if (DriverUtils.getDriverType().equals(Constants.BROWSER_CHROME))
			return 0;
		return 3;
	}

	public static void switchTab(String url, boolean isURLFull) {
		ArrayList<String> tabs = new ArrayList<String>(DriverUtils.getWebDriver().getWindowHandles());
		for (int i = 1; i < tabs.size(); i++) {
			DriverUtils.switchTo(tabs.get(i));
			String fullURL = DriverUtils.getWebDriver().getCurrentUrl();
			if (!isURLFull && fullURL.toLowerCase().contains(url.toLowerCase())) {
				break;
			} else if (isURLFull && url.equals(fullURL)) {
				break;
			}
		}
	}

	public static void switchToMain(boolean doesCurrentBrowserClose) {
		ArrayList<String> tabs = new ArrayList<String>(DriverUtils.getWebDriver().getWindowHandles());
		DriverUtils.getWebDriver().close();
		DriverUtils.switchTo(tabs.get(0));
	}

	public static void enter(TextBox txtBox, String text) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start entering element {" + getLocator(txtBox) + "}");
		waitForDisplayed(txtBox);
		scrollElementToCenterScreen(txtBox);
		waitForEnabled(txtBox);
		// enter text
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		while (endTime <= timeout) {
			try {
				if (txtBox.getValue().equals(text))
					break;
			} catch (Exception e) {
			}
			try {
				tryEnter(txtBox, text);
			} catch (NoSuchElementException notFound) {
				throw notFound;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			endTime = System.currentTimeMillis() - startTime;
		}
		if (!txtBox.getValue().equals(text)) {
			throw new TimeoutException("Element {" + getLocator(txtBox) + "} text didn't receive correct value within " + Constants.SHORT_TIME + " seconds\n- Current value: " + txtBox.getValue());
		}
		System.out.println("[Debug] End entering element {" + getLocator(txtBox) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void clear(TextBox txtBox) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start clearing element {" + getLocator(txtBox) + "}");
		enter(txtBox, "");
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End clearing element {" + getLocator(txtBox) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void select(ComboBox cbb, String value) {
		select(cbb, value, true);
	}

	public static void select(ComboBox cbb, String value, boolean isCloseDialog) {
		select(cbb, value, ActionType.BUILT_IN, isCloseDialog);
	}

	public static void select(ComboBox cbb, String value, ActionType type, boolean isCloseDialog) {
		if (type.equals(ActionType.JS)) {
			selectByJs(cbb, value);
		} else {
			selectByBuiltIn(cbb, value, isCloseDialog);
		}
	}

	public static void selectByBuiltIn(ComboBox cbb, String value, boolean isCloseDialog) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start selecting element {" + getLocator(cbb) + "}");
		try {
			waitForDisplayed(cbb);
			scrollElementToCenterScreen(cbb);
			if (isCloseDialog)
				modalDialog.closeUnknownModalDialog();
			waitForClickable(cbb);
			if (!cbb.getSelected().equals(value)) {
				trySelect(cbb, value);
			}
		} catch (ElementNotVisibleException notInvisible) {
			throw notInvisible;
		} catch (TimeoutException timeOut) {
			throw timeOut;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End selecting element {" + getLocator(cbb) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void selectByJs(ComboBox cbb, String value) {
		System.out.println("[Debug] Start selecting by JS element {" + getLocator(cbb) + "}");
		waitForDisplayed(cbb);
		scrollElementToCenterScreen(cbb);
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SLEEP_TIME * 1000;
		while (endTime <= timeout) {
			try {
				if (cbb.getSelected().equals(value))
					break;
			} catch (Exception e) {
			}
			try {
				trySelectByJs(cbb, value);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			endTime = System.currentTimeMillis() - startTime;
		}
		System.out.println("[Debug] End selecting by JS element {" + getLocator(cbb) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void check(CheckBox chkBox) {
		check(chkBox, ActionType.BUILT_IN, true);
	}

	public static void check(CheckBox chkBox, boolean isCloseDialog) {
		check(chkBox, ActionType.BUILT_IN, isCloseDialog);
	}

	public static void check(CheckBox chkBox, ActionType type, boolean isCloseDialog) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start checking element {" + getLocator(chkBox) + "}");
		try {
			waitForDisplayed(chkBox);
			scrollElementToCenterScreen(chkBox);
			if (isCloseDialog)
				modalDialog.closeUnknownModalDialog();
			waitForEnabled(chkBox);
			if (!chkBox.isChecked()) {
				if (type.equals(ActionType.JS)) {
					tryCheckByJs(chkBox);
				} else {
					tryCheck(chkBox);
				}
				waitForChecked(chkBox, Constants.SLEEP_TIME);
			}
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (ElementNotVisibleException notInvisible) {
			throw notInvisible;
		} catch (TimeoutException timeOut) {
			throw timeOut;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End checking element {" + getLocator(chkBox) + "}".concat(setThousandsSeparatorInTime(endTime)));
		Common.waitForDOMChange();
	}

	public static void uncheck(CheckBox chkBox) {
		uncheck(chkBox, ActionType.BUILT_IN, true);
	}

	public static void uncheck(CheckBox chkBox, boolean isCloseDialog) {
		uncheck(chkBox, ActionType.BUILT_IN, isCloseDialog);
	}

	public static void uncheck(CheckBox chkBox, ActionType type, boolean isCloseDialog) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start unchecking element {" + getLocator(chkBox) + "}");
		try {
			waitForDisplayed(chkBox);
			scrollElementToCenterScreen(chkBox);
			if (isCloseDialog)
				modalDialog.closeUnknownModalDialog();
			waitForEnabled(chkBox);
			if (chkBox.isChecked()) {
				if (type.equals(ActionType.JS)) {
					tryUncheckByJs(chkBox);
				} else {
					tryUncheck(chkBox);
				}
				tryUncheck(chkBox);
				waitForUnchecked(chkBox, Constants.SLEEP_TIME);
			}
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (ElementNotVisibleException notInvisible) {
			throw notInvisible;
		} catch (TimeoutException timeOut) {
			throw timeOut;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End unchecking element {" + getLocator(chkBox) + "}".concat(setThousandsSeparatorInTime(endTime)));
		Common.waitForDOMChange();
	}

	public static void submitForm(BaseControl form) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start submitting form {" + getLocator(form) + "}");
		waitForDisplayed(form);
		modalDialog.closeUnknownModalDialog();
		try {
			updateStateOfPage();
			DriverUtils.execJavaScript("arguments[0].submit()", form.getElement());
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End submitting form {" + getLocator(form) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void click(Clickable button) {
		click(button, true);
	}

	public static void click(Clickable button, boolean isCloseDialog) {
		click(button, getClickTypeFromPattern(HubPattern.DEFAULT), isCloseDialog, true);
	}

	public static void click(Clickable button, boolean isCloseDialog, boolean isFocus) {
		click(button, getClickTypeFromPattern(HubPattern.DEFAULT), isCloseDialog, isFocus);
	}

	public static void click(Clickable button, ActionType type) {
		click(button, type, true, true);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 */
	public static void click(Clickable button, HubPattern pattern) {
		click(button, getClickTypeFromPattern(pattern), true, true);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 * @param isCloseDialog
	 *            <br>
	 *            * true -> close all unknown dialogs <br>
	 *            * false -> do nothing
	 */
	public static void click(Clickable button, HubPattern pattern, boolean isCloseDialog) {
		click(button, getClickTypeFromPattern(pattern), isCloseDialog, true);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 * @param isCloseDialog
	 *            <br>
	 *            * true -> close all unknown dialogs <br>
	 *            * false -> do nothing
	 * @param isFocus
	 *            <br>
	 *            * true -> focus before clicking <br>
	 *            * false -> do nothing
	 */
	public static void click(Clickable button, HubPattern pattern, boolean isCloseDialog, boolean isFocus) {
		click(button, getClickTypeFromPattern(pattern), isCloseDialog, isFocus);
	}

	public static void click(Clickable button, ActionType type, boolean isCloseDialog, boolean isFocus) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start clicking element {" + getLocator(button) + "} by " + type.name);
		try {
			waitForDisplayed(button);
			updateStateOfPage();
			scrollElementToCenterScreen(button);
			DriverUtils.getWebDriver().switchTo().defaultContent();
			DriverUtils.getWebDriver().switchTo().activeElement();
			switch (type) {
			case JS: {
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				waitForClickable(button);
				tryClickByJs(button);
				break;
			}
			case ACTION: {
				moveByAction(button);
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				waitForClickable(button);
				tryClickByAction(button, isFocus);
				break;
			}
			default: {
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				waitForClickable(button);
				tryClick(button, isFocus);
				break;
			}
			}
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (ElementNotVisibleException notInvisible) {
			throw notInvisible;
		} catch (TimeoutException timeOut) {
			throw timeOut;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End clicking element {" + getLocator(button) + "} by " + type.name.concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void click(WebElement button) {
		click(button, true);
	}

	public static void click(WebElement button, boolean isCloseDialog) {
		click(button, getClickTypeFromPattern(HubPattern.DEFAULT), isCloseDialog, true);
	}

	public static void click(WebElement button, boolean isCloseDialog, boolean isFocus) {
		click(button, getClickTypeFromPattern(HubPattern.DEFAULT), isCloseDialog, isFocus);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 */
	public static void click(WebElement button, HubPattern pattern) {
		click(button, getClickTypeFromPattern(pattern), true, true);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 * @param isCloseDialog
	 *            <br>
	 *            * true -> close all unknown dialogs <br>
	 *            * false -> do nothing
	 */
	public static void click(WebElement button, HubPattern pattern, boolean isCloseDialog) {
		click(button, getClickTypeFromPattern(pattern), isCloseDialog, true);
	}

	/**
	 * Click base on pattern
	 * 
	 * @param button
	 *            element
	 * @param pattern
	 *            (defined on click.json file) to determine how we click on the
	 *            element on each platform-browser <br>
	 *            * if pattern not found -> use "default" pattern <br>
	 *            * if platform-browser not found -> use "other" platform <br>
	 *            * if click type invalid -> use "built-in" click
	 * @param isCloseDialog
	 *            <br>
	 *            * true -> close all unknown dialogs <br>
	 *            * false -> do nothing
	 * @param isFocus
	 *            <br>
	 *            * true -> focus before clicking <br>
	 *            * false -> do nothing
	 */
	public static void click(WebElement button, HubPattern pattern, boolean isCloseDialog, boolean isFocus) {
		click(button, getClickTypeFromPattern(pattern), isCloseDialog, isFocus);
	}

	public static void click(WebElement button, ActionType type, boolean isCloseDialog, boolean isFocus) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start clicking {" + button.toString().split("->")[1] + "} by " + type.name);
		try {
			updateStateOfPage();
			scrollElementToCenterScreen(button);
			DriverUtils.delay(1);
			switch (type) {
			case JS: {
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				tryClickByJs(button);
				break;
			}
			case ACTION: {
				moveByAction(button);
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				tryClickByAction(button, isFocus);
				break;
			}
			default: {
				if (isCloseDialog)
					modalDialog.closeUnknownModalDialog();
				tryClick(button, isFocus);
				break;
			}
			}
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (ElementNotVisibleException notInvisible) {
			throw notInvisible;
		} catch (TimeoutException timeOut) {
			throw timeOut;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End clicking {" + button.toString().split("->")[1] + "} by " + type.name.concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void clickByAction(Clickable button) {
		new Actions(DriverUtils.getWebDriver()).click(button.getElement()).build().perform();
	}

	public static void clickByAction(WebElement button) {
		new Actions(DriverUtils.getWebDriver()).click(button).build().perform();
	}

	public static void moveByAction(Clickable button) {
		try {
			new Actions(DriverUtils.getWebDriver()).moveToElement(button.getElement()).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void moveByAction(WebElement button) {
		try {
			new Actions(DriverUtils.getWebDriver()).moveToElement(button).build().perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void focus(BaseControl baseControl) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start focusing element {" + getLocator(baseControl) + "}");
		try {
			baseControl.waitForVisibility();
			scrollElementToCenterScreen(baseControl);
			tryFocus(baseControl);
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End focusing element {" + getLocator(baseControl) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void moveTo(BaseControl baseControl) {
		long startTime = System.currentTimeMillis();
		System.out.println("[Debug] Start moving to element {" + getLocator(baseControl) + "}");
		try {
			waitForDisplayed(baseControl);
			scrollElementToCenterScreen(baseControl);
			modalDialog.closeUnknownModalDialog();
			tryMoveTo(baseControl);
		} catch (NoSuchElementException notFound) {
			System.out.println(notFound.getMessage());
		} catch (ElementNotVisibleException notInvisible) {
			System.out.println(notInvisible.getMessage());
		} catch (TimeoutException timeOut) {
			System.out.println(timeOut.getMessage());
		} catch (Exception e) {
		}
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] End moving to element {" + getLocator(baseControl) + "}".concat(setThousandsSeparatorInTime(endTime)));
	}

	public static void focus(WebElement control) {
		try {
			DriverUtils.execJavaScript("arguments[0].focus();", control);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getNumberFromText(String text) {
		StringBuilder tmpString = new StringBuilder("");
		String pattern = "\\d+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);
		while (m.find()) {
			tmpString = tmpString.append(text.substring(m.start(), m.end()));
		}
		return tmpString.toString();
	}

	public static void scrollDownToHandlePopupError() {
		try {
			DriverUtils.execJavaScript("window.scrollTo(0,25);");
			delay(0.5);
			DriverUtils.execJavaScript("window.scrollTo(0,50);");
			delay(0.5);
			DriverUtils.execJavaScript("window.scrollTo(0,75);");
			delay(0.5);
			DriverUtils.execJavaScript("window.scrollTo(0,100);");
			delay(0.5);
		} catch (Exception e) {
		}
	}

	public static void triggerTextBoxChangeEvent(BaseControl textBox) {
		try {
			DriverUtils.execJavaScript("var event = document.createEvent('Event');event.initEvent('change', false, true);arguments[0].dispatchEvent(event);", textBox.getElement());
		} catch (NoSuchElementException notFound) {
			throw notFound;
		} catch (Exception e) {
		}
	}

	public static String getCurrrentBrowser() {
		return DriverUtils.getDriverType();
	}

	public static boolean isSafari() {
		if (getCurrrentBrowser().equals(Constants.BROWSER_SAFARI))
			return true;
		return false;
	}

	public static void scrollElementToCenterScreen(BaseControl control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				control.scrollElementToCenterScreen();
				isLoop = false;
			} catch (Exception e) {
				if (tries == TRY_LIMIT)
					throw e;
				e.printStackTrace();
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void scrollElementToCenterScreen(WebElement control) {
		int tries = 0;
		boolean isLoop = true;
		String js = "Element.prototype.documentOffsetTop=function(){return this.offsetTop+(this.offsetParent?this.offsetParent.documentOffsetTop():0)};var top=arguments[0].documentOffsetTop()-window.innerHeight/2;window.scrollTo(0,top);";
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				DriverUtils.execJavaScript(js, control);
				isLoop = false;
			} catch (Exception e) {
				if (tries == TRY_LIMIT)
					throw e;
				e.printStackTrace();
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void moveToControl(BaseControl control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				control.scrollElementToCenterScreen();
				DriverUtils.moveToControl(control);
				isLoop = false;
			} catch (Exception e) {
				if (tries == TRY_LIMIT)
					throw e;
				e.printStackTrace();
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void deleteCookies() {
		try {
			waitForPageLoad();
			WebDriver driver = DriverUtils.getWebDriver();
			Set<Cookie> cookies = driver.manage().getCookies();
			driver.manage().deleteAllCookies();
			delay(Constants.SLEEP_TIME);
			if (!cookies.isEmpty()) {
				System.out.println("Cookies size: " + cookies.size());
				System.out.println("Cookie: " + cookies.toString());
				cookies.clear();
				System.out.println("Cookie cleared!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollToTop() {
		WebDriver driver = DriverUtils.getWebDriver();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,0);");
		delay(0.5);
		modalDialog.closeUnknownModalDialog();
	}

	public static void ignoreAlertPopup() {
		Alert alert = null;
		try {
			delay(1);
			alert = DriverUtils.getWebDriver().switchTo().alert();
			alert.dismiss();
			delay(1);
			alert.dismiss();
		} catch (Exception e) {
		}
	}

	public static String getTextAndCloseAlert() {
		String resultText = "";
		Alert alert = null;
		try {
			alert = DriverUtils.getWebDriver().switchTo().alert();
			resultText = alert.getText().toString();
		} catch (Exception e) {
		}
		try {
			alert.dismiss();
			delay(1);
			alert.dismiss();
		} catch (Exception e) {
		}
		return resultText;
	}

	public static String getText(BaseControl element) {
		waitForDisplayed(element);
		scrollElementToCenterScreen(element);
		if (RunningMode.getCurrentPlatform().equals(Constants.PLATFORM_MAC))
			return element.getAttribute("innerText").replace("\n", " ").replaceAll("\u00a0", " ").replaceAll("\\s+", " ").trim();
		return element.getText().replace("\n", " ").replaceAll("\\s+", " ").trim();
	}

	public static String getTitlePage() {
		try {
			return DriverUtils.getWebDriver().getTitle();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getCurrentUrl() {
		try {
			return DriverUtils.getWebDriver().getCurrentUrl();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void updateStateOfPage() {
		currentUrlPage = getCurrentUrl();
		currentTitlePage = getTitlePage();
	}

	public static double getPriceFromText(String text) {
		StringBuilder tmpString = new StringBuilder("");
		DecimalFormat format = new DecimalFormat();
		String pattern = "\\d+\\.\\d+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);
		while (m.find()) {
			tmpString = tmpString.append(text.substring(m.start(), m.end()));
		}
		format.setMinimumFractionDigits(2);
		Double price = Double.parseDouble(tmpString.toString());
		format.format(price);
		return price;
	}

	public static String getLocator(BaseControl control) {
		return control.getLocator().toString();
	}
	// ================================================================================
	// Wait actions
	// ================================================================================

	/**
	 * Wait for DOM change
	 */
	public static void waitForDOMChange(long timeout) {
		String jsStatus = "false";
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		System.out.print("[Debug] " + getTitlePage() + " - DOM is changing: [");
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		while (endTime < timeout * 1000 && !jsStatus.equals("true")) {
			delay(0.5);
			endTime = System.currentTimeMillis() - startTime;
			try {
				jsStatus = js.executeScript("return jQuery.active == 0").toString().trim();
				System.out.print(jsStatus.charAt(0));
			} catch (Exception e) {
				jsStatus = e.getMessage().split("\n")[0];
				break;
			}
		}
		jsStatus = (jsStatus.equals("true")) ? "complete" : jsStatus;
		System.out.print("] - " + jsStatus.concat(setThousandsSeparatorInTime(endTime)) + "\n");
		if (endTime > (timeout * 1000))
			Logger.info("Timeout to wait for DOM change");
	}

	public static void waitForDOMChange() {
		waitForDOMChange(Constants.SLEEP_TIME);
	}

	/**
	 * Wait for Page load
	 * 
	 * @jsStatus loading: The document is still loading.
	 * @jsStatus interactive: The document has finished loading and the document has
	 *           been parsed but sub-resources such as images, stylesheets and
	 *           frames are still loading.
	 * @jsStatus complete: The document and all sub-resources have finished loading.
	 *           The state indicates that the load event is about to fire.
	 */
	public static void waitForPageLoad(long timeout) {
		delay(getStartLoadingTimeout());
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		String jsStatus = "Begin";
		System.out.print("[Debug] " + DriverUtils.getWebDriver().switchTo().defaultContent().getTitle() + " is loading: [");
		while (endTime < timeout * 1000 && !jsStatus.equals("complete")) {
			delay(0.5);
			endTime = System.currentTimeMillis() - startTime;
			try {
				jsStatus = js.executeScript("return document.readyState").toString().trim();
				System.out.print(jsStatus.charAt(0));
			} catch (Exception e) {
				jsStatus = e.getMessage().split("\n")[0];
			}
		}
		System.out.print("] - " + jsStatus.concat(setThousandsSeparatorInTime(endTime)) + "\n");
		if (endTime > (timeout * 1000))
			Logger.info("Timeout to wait for page load");
		// throw exception if timeout
		// if (!jsStatus.equals("complete"))
		// {
		// throw new TimeoutException("Page was not load completely within " + timeout +
		// " seconds");
		// }
	}

	public static void waitForPageLoad() {
		waitForPageLoad(Constants.SHORT_TIME);
	}

	public static String getPageSource() {
		return DriverUtils.getWebDriver().getPageSource();
	}

	public static void delay(double timeInSecond) {
		DriverUtils.delay(timeInSecond);
	}

	// Wait for Title change
	public static void waitForTitleChange(String currentTitle, String curentUrl, long timeout) {
		String actualTitle = currentTitle;
		String actualUrl = curentUrl;
		String jsStatus = "";
		delay(getStartLoadingTimeout());
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis() - startTime;
		System.out.print("[Debug] " + currentTitle + " - " + curentUrl + " is loading: - [");
		// wait for title change
		while (actualTitle.equals(currentTitle) && actualUrl.equals(curentUrl) && endTime < (timeout * 500)) {
			delay(1);
			actualTitle = getTitlePage();
			actualUrl = getCurrentUrl();
			endTime = System.currentTimeMillis() - startTime;
		}
		// wait for page start loading
		long loadingTimeout = endTime + getStartLoadingTimeout();
		while (!jsStatus.equals("loading") && endTime < loadingTimeout) {
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
			try {
				jsStatus = DriverUtils.execJavaScript("return document.readyState").toString().trim();
			} catch (Exception e) {
				jsStatus = e.getMessage().split("\n")[0];
			}
			System.out.print(jsStatus.charAt(0));
		}
		// wait for page load completely
		long remainingTimeout = (timeout * 1000) - endTime;
		while (!jsStatus.equals("complete") && endTime < remainingTimeout) {
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
			try {
				jsStatus = DriverUtils.execJavaScript("return document.readyState").toString().trim();
			} catch (Exception e) {
				jsStatus = e.getMessage().split("\n")[0];
			}
			System.out.print(jsStatus.charAt(0));
		}
		System.out.print("] - " + jsStatus.concat(setThousandsSeparatorInTime(endTime)) + "\n");
		if (endTime > (timeout * 1000))
			Logger.info("Timeout to wait for title change");
		updateStateOfPage();
		// throw exception if timeout
		// if (actualTitle.equals(currentTitle) && actualUrl.equals(curentUrl) &&
		// !jsStatus.equals("complete"))
		// {
		// throw new TimeoutException("Page was not load completely within " + timeout +
		// " seconds");
		// }
	}

	public static void waitForTitleChange(long timeout) {
		waitForTitleChange(currentTitlePage, currentUrlPage, timeout);
	}

	public static void waitForTitleChange(String currentTitle) {
		waitForTitleChange(currentTitle, currentUrlPage, Constants.SHORT_TIME);
	}

	public static void waitForTitleChange() {
		waitForTitleChange(currentTitlePage, currentUrlPage, Constants.SHORT_TIME);
	}

	// Wait for Value change
	public static void waitForValueChange(BaseControl control, String currentValue) {
		long startTime = System.currentTimeMillis();
		String actual = currentValue;
		long endTime = System.currentTimeMillis() - startTime;
		long timeout = Constants.SHORT_TIME * 1000;
		while (actual.equals(currentValue) && endTime < timeout) {
			try {
				actual = control.getValue();
			} catch (NoSuchElementException notFound) {
				actual = "";
			} catch (StaleElementReferenceException staleEx) {
				staleEx.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			endTime = System.currentTimeMillis() - startTime;
		}
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} has value change".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (actual.equals(currentValue)) {
			// throw new TimeoutException("Element {" + getLocator(control) + "} value was
			// not changed within " + timeout + " seconds\n- Current value: " + actual);
		}
	}

	public static void waitForClickable(BaseControl button) {
		waitForClickable(button, Constants.SHORT_TIME);
	}

	public static void waitForClickable(BaseControl button, int timeout) {
		boolean isClickable = false;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		do {
			isClickable = button.isDisplayed() & button.isEnabled();
			if (isClickable) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(button) + "} clickable".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if ((button.isDisplayed() && button.isEnabled()) || (button.isDisplayed() && button.getTagName().equalsIgnoreCase("span")))
			return;
		else {
			throw new TimeoutException("Element {" + getLocator(button) + "} was not clickable within " + timeout + " seconds\n- Displayed: " + button.isDisplayed() + "\n- Enabled: " + button.isEnabled());
		}
	}

	// Wait for Displayed
	public static void waitForDisplayed(BaseControl control) {
		//waitForDisplayed(control, Constants.SHORT_TIME);
		waitForDisplayed(control, Constants.MID_TIME);
	}

	public static void waitForDisplayed(BaseControl control, int timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element displayed
		do {
			if (control.isDisplayed()) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} displayed".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (!control.isDisplayed()) {
			throw new TimeoutException("Element {" + getLocator(control) + "} was not displayed within " + timeout + " seconds");
		}
	}

	// Wait for Not Displayed
	public static void waitForNotDisplayed(BaseControl control) {
		waitForNotDisplayed(control, Constants.SHORT_TIME);
	}

	public static void waitForNotDisplayed(BaseControl control, int timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element not displayed
		do {
			if (!control.isDisplayed()) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} not displayed".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (control.isDisplayed()) {
			throw new TimeoutException("Element {" + getLocator(control) + "} was still displayed within " + timeout + " seconds");
		}
	}

	// Wait for Enable
	public static void waitForEnabled(BaseControl control) {
		waitForEnabled(control, Constants.SHORT_TIME);
	}

	public static void waitForEnabled(BaseControl control, int timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element enabled
		do {
			if (control.isEnabled()) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			delay(1);
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} enabled".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (!control.isEnabled()) {
			throw new TimeoutException("Element {" + getLocator(control) + "} was not enabled within " + timeout + " seconds");
		}
	}

	// Wait for Text
	public static void waitForText(BaseControl control, String text) {
		waitForText(control, text, Constants.SHORT_TIME);
	}

	public static void waitForText(BaseControl control, String text, long timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element text
		do {
			if (control.getText().equals(text)) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} has text is '" + text + "'".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (!control.getText().equals(text)) {
			throw new TimeoutException("Element {" + getLocator(control) + "} text didn't receive correct text '" + text + "' within " + timeout + " seconds\n- Current text: " + control.getText());
		}
	}

	public static void waitForTextContains(BaseControl control, String text) {
		waitForTextContains(control, text, Constants.SHORT_TIME);
	}

	public static void waitForTextContains(BaseControl control, String text, long timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element text
		do {
			if (control.getText().contains(text)) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} text contains '" + text + "'".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		// if (!control.getText().contains(text))
		// {
		// throw new TimeoutException("Element {" + getLocator(control) + "}
		// text didn't contains '" + text + "' within " + timeout + " seconds");
		// }
	}

	// Wait for Value
	public static void waitForValue(BaseControl control, String value) {
		waitForValue(control, value, Constants.SHORT_TIME);
	}

	public static void waitForValue(BaseControl control, String value, long timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element value
		do {
			if (control.getValue().equals(value)) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} has value is " + value.concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (!control.getValue().equals(value)) {
			throw new TimeoutException("Element {" + getLocator(control) + "} text didn't receive correct value within " + timeout + " seconds\n- Current value: " + control.getValue());
		}
	}

	// Wait for Checked
	public static void waitForChecked(CheckBox control) {
		waitForChecked(control, Constants.SHORT_TIME);
	}

	public static void waitForChecked(CheckBox control, long timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element checked
		do {
			if (control.isChecked()) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} checked".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (!control.isChecked()) {
			throw new TimeoutException("Element {" + getLocator(control) + "} text was not checked within " + timeout + " seconds");
		}
	}

	// Wait for Checked
	public static void waitForUnchecked(CheckBox control) {
		waitForUnchecked(control, Constants.SHORT_TIME);
	}

	public static void waitForUnchecked(CheckBox control, long timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element checked
		do {
			if (!control.isChecked()) {
				endTime = System.currentTimeMillis() - startTime;
				break;
			}
			endTime = System.currentTimeMillis() - startTime;
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} unchecked".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (control.isChecked()) {
			throw new TimeoutException("Element {" + getLocator(control) + "} text was not unchecked within " + timeout + " seconds");
		}
	}

	// Wait for Selected
	public static void waitForSelected(ComboBox control, String value) {
		waitForSelected(control, value, Constants.SHORT_TIME);
	}

	public static void waitForSelected(ComboBox control, String value, int timeout) {
		long startTime = System.currentTimeMillis();
		// wait for element item selected
		control.waitForSelectedOptionToBePresent(value, timeout);
		long endTime = System.currentTimeMillis() - startTime;
		System.out.println("[Debug] - Wait for element {" + getLocator(control) + "} has item '" + value + "' was selected".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		String selected;
		try {
			selected = control.getSelected();
		} catch (Exception e) {
			DriverUtils.delay(Constants.SLEEP_TIME);
			selected = control.getSelected();
		}

		if (!selected.equals(value)) {
			throw new TimeoutException("Element {" + getLocator(control) + "} with item '" + value + "' was not selected within " + timeout + " seconds\n- Current selected: " + control.getSelected());
		}
	}

	// ================================================================================
	// Handle exceptions when do an action
	// ================================================================================
	public static void tryFocus(BaseControl control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				control.focus();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (NoSuchElementException notFound) {
				if (tries == TRY_LIMIT)
					throw notFound;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClick(Clickable control) {
		tryClick(control, true);
	}

	public static void tryClick(Clickable control, boolean isFocus) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				if (isFocus)
					control.focus();
				delay(0.5);
				control.click();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClick result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClick result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClick result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClick result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (Exception e) {
				Logger.info("tryClick result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClick(WebElement control) {
		tryClick(control, true);
	}

	public static void tryClick(WebElement control, boolean isFocus) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				if (isFocus)
					focus(control);
				delay(0.5);
				control.click();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClick result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClick result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClick result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClick result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (Exception e) {
				Logger.info("tryClick result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClickByAction(Clickable control) {
		tryClickByAction(control, true);
	}

	public static void tryClickByAction(Clickable control, boolean isFocus) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				if (isFocus)
					control.focus();
				clickByAction(control);
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (MoveTargetOutOfBoundsException outOfBound) {
				if (tries == TRY_LIMIT)
					throw outOfBound;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(outOfBound.getMessage());
			} catch (Exception e) {
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClickByAction(WebElement control) {
		tryClickByAction(control, true);
	}

	public static void tryClickByAction(WebElement control, boolean isFocus) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				if (isFocus)
					focus(control);
				clickByAction(control);
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (MoveTargetOutOfBoundsException outOfBound) {
				if (tries == TRY_LIMIT)
					throw outOfBound;
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(outOfBound.getMessage());
			} catch (Exception e) {
				Logger.info("tryClickByAction result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClickByJs(Clickable control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.clickByJs();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (Exception e) {
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryClickByJs(WebElement control) {
		int tries = 0;
		boolean isLoop = true;
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				js.executeScript("arguments[0].click();", control);
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(invalid.getMessage());
			} catch (NoSuchElementException noElement) {
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(noElement.getMessage());
				noElement.printStackTrace();
				return;
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(staleEx.getMessage());
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(timeOut.getMessage());
			} catch (Exception e) {
				Logger.info("tryClickByJs result at time " + tries);
				Logger.info(e.getMessage());
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryCheck(CheckBox control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.check();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryUncheck(CheckBox control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.uncheck();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryCheckByJs(CheckBox control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.checkByJs();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryUncheckByJs(CheckBox control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.uncheckByJs();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void trySelect(ComboBox control, String value) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				scrollElementToCenterScreen(control);
				control.select(value);
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void trySelectByJs(ComboBox control, String value) {
		int tries = 0;
		boolean isLoop = true;
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		String selectedValue = "";
		try {
			selectedValue = (String) js.executeScript(String.format("var selectedOption = '';var ele = arguments[0];for (var i = 0; i < ele.options.length; i++) {if (ele.options[i].text === \"%s\") {selectedOption = ele.options[i].value;break;}};return selectedOption;", value), control.getElement());
		} catch (Exception ex) {
		}
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				js.executeScript(String.format("arguments[0].value=\"%s\";", selectedValue), control.getElement());
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryEnter(TextBox control, String text) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				control.setValue(text);
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void tryMoveTo(BaseControl control) {
		int tries = 0;
		boolean isLoop = true;
		while (isLoop && tries < TRY_LIMIT) {
			tries++;
			try {
				control.moveTo();
				isLoop = false;
			} catch (InvalidSelectorException invalid) {
				if (tries == TRY_LIMIT || !invalid.getMessage().contains(FREED_SCRIPT_ERROR)) {
					throw invalid;
				}
			} catch (StaleElementReferenceException staleEx) {
				if (tries == TRY_LIMIT)
					throw staleEx;
			} catch (TimeoutException timeOut) {
				if (tries == TRY_LIMIT)
					throw timeOut;
			} catch (Exception e) {
				throw e;
			}
			if (isLoop)
				delay(0.5);
		}
	}

	public static void goBack() {
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		js.executeScript("window.history.go(-1)");
		waitForPageLoad();
	}

	public static void goForward() {
		JavascriptExecutor js = (JavascriptExecutor) DriverUtils.getWebDriver();
		js.executeScript("window.history.go(1)");
		waitForPageLoad();
	}

	public static String setThousandsSeparatorInTime(long time) {
		return " - ".concat(String.format("%,d", time).concat(" miliseconds - ").concat(getcurrentThread()));
	}

	public static String getcurrentThread() {
		return Thread.currentThread().getStackTrace()[3].toString();
	}

	public static void triggerWindowActive() {
		WebDriver driver = DriverUtils.getWebDriver();
		String window = driver.getWindowHandle();
		((JavascriptExecutor) driver).executeScript("alert('triggerWindowActive')");
		driver.switchTo().alert().accept();
		driver.switchTo().window(window);
	}

	public static void triggerJustForYouModal() {
		System.out.println("[Debug] Start execute JavaScript to trigger Just For You Modal");
		if (MODE.getRunningMode().equals(Constants.PLATFORM_DESKTOP))
			DriverUtils.execJavaScript("var u = $('input#os_exit_modal').val(); os_modal(u, true, null, 'get', '0.85');");
		else
			DriverUtils.execJavaScript("os_modal('/servlet/OnlineShopping?dsp=7700', true, null, 'get')");
		System.out.println("[Debug] End execute JavaScript to trigger Just For You Modal");
	}

	public static String convertStateCodeToFullStateName(String stateCode) {
		switch (stateCode) {
		case "AK":
			return "Alaska";
		case "AL":
			return "Alabama";
		case "AZ":
			return "Arizona";
		case "DE":
			return "Delaware";
		case "HI":
			return "Hawaii";
		case "KY":
			return "Kentucky";
		case "ME":
			return "Maine";
		case "MS":
			return "Mississippi";
		case "MT":
			return "Montana";
		case "OK":
			return "Oklahoma";
		case "PR":
			return "Puerto Rico";
		case "SD":
			return "South Dakota";
		case "UT":
			return "Utah";
		case "VI":
			return "Virgin Islands";
		default:
			return "";
		}
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static Calendar getDateAfterNumberOfBusinessDays(Calendar startDate, int businessDays) {
		Calendar tmpDate = (Calendar) startDate.clone();
		while (businessDays > 0) {
			tmpDate.add(Calendar.DATE, 1);
			if (!(tmpDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || tmpDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
				businessDays--;
		}
		return tmpDate;
	}

	public static Calendar getDateBeforeNumberOfBusinessDays(Calendar endDate, int businessDays) {
		Calendar tmpDate = (Calendar) endDate.clone();
		while (businessDays > 0) {
			tmpDate.add(Calendar.DATE, -1);
			if (!(tmpDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || tmpDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
				businessDays--;
		}
		return tmpDate;
	}

	/**
	 * @return return "true" if "dateStart < date < dateEnd" else return "false"
	 */
	public static boolean isDateBetweenTwoDates(Calendar date, Calendar dateStart, Calendar dateEnd) {
		return (date.before(dateEnd) && date.after(dateStart));
	}

	public static boolean isDateBetweenTwoDates(Calendar date, Date dateStart, Date dateEnd) {
		return (date.before(dateToCalendar(dateEnd)) && date.after(dateToCalendar(dateStart)));
	}

	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static int getValueOfTheMONTH(String month) {
		switch (month) {
		case "Jan":
			return Calendar.JANUARY;
		case "Feb":
			return Calendar.FEBRUARY;
		case "Mar":
			return Calendar.MARCH;
		case "Apr":
			return Calendar.APRIL;
		case "May":
			return Calendar.MAY;
		case "Jun":
			return Calendar.JUNE;
		case "Jul":
			return Calendar.JULY;
		case "Aug":
			return Calendar.AUGUST;
		case "Sep":
			return Calendar.SEPTEMBER;
		case "Oct":
			return Calendar.OCTOBER;
		case "Nov":
			return Calendar.NOVEMBER;
		case "Dec":
			return Calendar.DECEMBER;
		default:
			return -1;
		}
	}

	public static void writeJsonToFile(String jsonFile, String... listString) {

		String json = "{";
		for (int i = 0; i < listString.length; i++) {
			if (i != 0)
				json = json + ",";
			json = json + listString[i].toString();
		}
		json = json + "}";
		BufferedWriter bufferedWriter = null;
		try {

			File myFile = new File(jsonFile);
			if (!myFile.exists()) {
				myFile.createNewFile();
			}

			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myFile), StandardCharsets.UTF_8));
			bufferedWriter.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception ex) {

			}
		}
	}

	public static ArrayList<String> getTraceroute() {
		ArrayList<String> hops = new ArrayList<String>();
		try {
			BufferedReader in;
			Runtime runtime = Runtime.getRuntime();
			String url = Common.getCurrentUrl();

			InetAddress address = InetAddress.getByName(new URL(url).getHost());
			String ip = address.getHostAddress();
			Process process = runtime.exec("tracert " + ip);

			in = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;

			hops.add("Tracing route to url: " + url);

			while ((line = in.readLine()) != null) {
				hops.add(line);
				System.out.println(line);
				// in.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		}
		return hops;
	}

	public static String getResponseCodeOfLink(String linkUrl) {
		URL url = null;
		if (linkUrl.contains("tel:"))
			return "200";
		try {

			linkUrl = linkUrl.trim().replaceAll(" ", "%20");
			url = new URL(linkUrl.replaceAll("http:", "https:"));
			HttpsURLConnection httpURLConnect = (HttpsURLConnection) url.openConnection();
			httpURLConnect.setInstanceFollowRedirects(true);
			httpURLConnect.setRequestProperty("User-Agent", "Chrome/75.0.3770.100");
			httpURLConnect.setConnectTimeout(0);
			httpURLConnect.connect();

			boolean redirect = false;
			int status = httpURLConnect.getResponseCode(); // No response. Throws exception
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
				redirect = true;
			}

			if (redirect == true) {
				String newUrl = httpURLConnect.getHeaderField("Location");
				url = new URL(newUrl.replaceAll("http:", "https:"));
				httpURLConnect = (HttpsURLConnection) url.openConnection();
				httpURLConnect.setInstanceFollowRedirects(true);
				httpURLConnect.setRequestProperty("User-Agent", "Chrome/75.0.3770.100");
				httpURLConnect.setConnectTimeout(0);
				httpURLConnect.connect();
			}
			return String.valueOf(httpURLConnect.getResponseCode());
		} catch (ProtocolException eProtocol) {
			return String.valueOf(HttpsURLConnection.HTTP_MOVED_TEMP);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * 
	 * @param isGreater
	 *            value should be as below <br>
	 *            1. true: Reward Card after generating which has value larger than
	 *            Total Price <br>
	 *            2. false: Reward Card after generating which has value smaller
	 *            than Total Price <br>
	 */
	public static String generateRandomRewardCardString(String totalPrice, boolean isGreater) {
		String code = Constants.REWARD_CARD;
		double rewardCardPrice;
		if (isGreater == true) {
			rewardCardPrice = Double.parseDouble(totalPrice) + Common.randBetween(1, 10);
		} else {
			rewardCardPrice = Double.parseDouble(totalPrice) - Common.randBetween(1, 10);
		}
		return code + String.valueOf(Math.round(rewardCardPrice));
	}

	public static void waitForElementExistedInDOM(BaseControl control, int timeout) {
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		long limitTime = timeout * 1000;
		// wait for element displayed
		do {
			endTime = System.currentTimeMillis() - startTime;
			if (control.getElements().size() > 0) {
				break;
			}
			delay(1);
		} while (endTime <= limitTime);
		System.out.println("[Debug] - Wait for element  {" + getLocator(control) + "} existed".concat(setThousandsSeparatorInTime(endTime)));
		// throw exception if timeout
		if (control.getElements().size() == 0) {
			throw new TimeoutException("Element {" + getLocator(control) + "} was not existed within " + timeout + " seconds");
		}
	}

	public static void waitForElementExistedInDOM(BaseControl control) {
		waitForElementExistedInDOM(control, Constants.SHORT_TIME);
	}

	public static String generateLogForLink(String url, String code) {
		return Common.getCSTTime("MM.dd.yyyy-HH.mm.ss") + ": Status code " + code + " - " + url + "\r\n";
	}

	// use for plug-in device
	public static boolean pingHost(String url, StringBuilder msg) {
		if (msg == null)
			msg = new StringBuilder();
		String host = url.contains("://") ? url.split("://")[1] : url;
		String command = "adb shell ping -t 10 -c 4 " + host;
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("errors") || line.contains("unknown")) {
					msg.append("Ping host " + host);
					msg.append(" - Error: " + line);
					return false;
				}
			}
		} catch (IOException e) {
			msg.append(" - Error: " + e.getMessage());
			return false;
		}
		return true;
	}

	static {
		disableSslVerification();
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	public static void switchTabByIndex(int index) {
		ArrayList<String> tabs = new ArrayList<String>(DriverUtils.getWebDriver().getWindowHandles());
		DriverUtils.switchTo(tabs.get(index - 1));
	}

	public static void openAndSwitchToNewTab() {
		DriverUtils.openNewTab();
		switchTabByIndex(2);
	}

	public static String randomNewDate(String format, int numberOfDateAfter) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numberOfDateAfter);
		date = c.getTime();
		return dateFormat.format(date);
	}

	public static WebElement getActiveElement() {
		WebElement activeEle = DriverUtils.getWebDriver().switchTo().activeElement();
		return activeEle;
	}

	public static void switchToMainWindow() {
		List<String> lstWindow = DriverUtils.getWindowHandles();
		String mainWindow = lstWindow.get(0);
		DriverUtils.getWebDriver().close();
		DriverUtils.switchTo(mainWindow);
	}
	
	public static void switchToWindow(String windowHanlde, boolean closeSubWindow) {
		if(!closeSubWindow) {
			DriverUtils.switchTo(windowHanlde);
			return;
		}
		List<String> lstWindow = DriverUtils.getWindowHandles();
		if (lstWindow.size() > 1) {
			for(String window:lstWindow) {
				if(!window.equals(windowHanlde)) {
				DriverUtils.switchTo(window);
				DriverUtils.getWebDriver().close();
				}
			}
		DriverUtils.switchTo(windowHanlde);
		}
	}
	public static boolean isGoldMembershipAvailable() {
		WebDriver driver = DriverUtils.getWebDriver();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String message = (String) jse.executeScript("return s.eVar11").toString();
		System.out.println(message);
		if (message.contains("Gold Membership Available"))
			return true;
		else
			return false;
	}

	public static Integer getMinValue(Integer[] array) {
		int min = array[0];
		for (int i = 1; i < array.length; i++)
			if (array[i] < min)
				min = array[i];
		return min;
	}

	public static void closeBrowser() {
		Logger.info("Close browser by ALT + F4");
		Robot robot;
		try {
			robot = new Robot();

			// press key Alt+F4
			robot.keyPress(KeyEvent.VK_ALT);
			robot.delay(100);
			robot.keyPress(KeyEvent.VK_F4);
			// relase key Alt+F4
			robot.delay(100);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.delay(100);
			robot.keyRelease(KeyEvent.VK_ALT);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static WebDriver navigateToOmastkSiteWithExistingBrowserProfile(String userDir) {
		Logger.info("-Create new webDriver based on the stored browser's profile" + "-    Navigate to omastk.com" + "-    Get all cookies");
		WebDriver driver = null;
		if (Common.getCurrrentBrowser().equals(Constants.BROWSER_CHROME)) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=" + userDir);
			options.addArguments("--disable-session-crashed-bubble");
			driver = new ChromeDriver(options);
		} else if (Common.getCurrrentBrowser().equals(Constants.BROWSER_FIREFOX)) {
			FirefoxOptions options = new FirefoxOptions();
			FirefoxProfile firefoxProfile = new FirefoxProfile(new File(userDir));
			options.setProfile(firefoxProfile);
			driver = new FirefoxDriver(options);
		}

		driver.manage().window().maximize();
 		driver.get(Constants.OMAHA_URL);
		return driver;
	}
	
	public static String getRewardSkuDescription(String value) {
		String rewardDesc = value;
		
		if (rewardDesc.contains("\\")) {
			rewardDesc = rewardDesc.split("\\)")[1].trim();
		} 
		
		if (rewardDesc.contains(")")) {
			rewardDesc = rewardDesc.substring(rewardDesc.indexOf(")") + 1);
		}
		
		int index = rewardDesc.indexOf(" ");
		if ((index == 0) || (index == 1)) {
			rewardDesc = rewardDesc.substring(rewardDesc.indexOf(" ") + 1);
		}
		
		index = rewardDesc.indexOf(".");
		if (index == 3) {
			rewardDesc = rewardDesc.substring(index + 1);
		}
		
		return rewardDesc.trim();
	}
}
