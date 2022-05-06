package com.omahasteaks.utils.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import com.aventstack.extentreports.Status;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.report.ExtentTestManager;

public class Logger {
	private static String methodName;
	private static String clasName;
	private static List<String> currentLogs = new ArrayList<String>();
	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Logger.class);
	private static int step=0;

	/**
	 * Log message into report as an info
	 */
	public static void info(String message) {
		saveLog(message);
		log.info(message);
		Reporter.log("<b>INFO: </b>" + convertMessage(message));
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + convertMessage(message) + "</span>");
	}

	public static void precondition(String message) {
		saveLog(message);
		log.info(message);
		Reporter.log("<b>PRECONDITION: </b>" + convertMessage(message));
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + convertMessage(message) + "</span>");
	}

	public static void reset() {
		Logger.step = 0;	
	}

	public static void step(String message) {
		String stepnum = "&nbsp;&nbsp;" + ++Logger.step + ".&nbsp;";
		if (Logger.step==1) {
			saveLog("STEPS-");
			log.info("STEPS--");
			Reporter.log("<b>STEPS:</b>");  // goes to output.html
			ExtentTestManager.getTest().log(Status.INFO, "<b>STEPS:</b>");  // goes to ReportResults.html
		} 
		saveLog(message);
		log.info(message);
		Reporter.log("<b>" + stepnum + convertMessage(message) + "</b>");
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word><b>" + stepnum + convertMessage(message) + "</b></span>");
	}

	public static void substep(String message) {
		String nbsp = "&nbsp;&nbsp;&nbsp;&nbsp;"; 
		saveLog(nbsp + message);
		log.info(nbsp + message);
		Reporter.log(nbsp + convertMessage(message));
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + nbsp + convertMessage(nbsp + message) + "</span>");
	}

	public static void debug(String message) {
		saveLog(message);
		log.info(message);
		Reporter.log("<b>INFO: </b>" + convertMessage(message));
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + convertMessage(message) + "</span>");
	}



	/**
	 * Log bug into report as a hyperlink
	 */
	public static void bug(String bugId, String bugLink) {
		String bugInfo = String.format("The bug: %s", bugId);
		log.error(bugInfo);
		saveLog(bugInfo);
		String msg = "<b><a target=\"_blank\" href=\"" + bugLink + "\" style=\"color:#DF0101;font-size:14px;word-break:break-word;\">" + bugInfo + "</a></b>";
		Reporter.log(msg);
		ExtentTestManager.getTest().log(Status.WARNING, msg);
	}

	/**
	 * Log message into report as a warning
	 */
	public static void warning(String message) {
		log.info("WARNING: " + message);
		saveLog("WARNING: " + message);
		message = "<b style=\"color: darkorange;word-break:break-word;\"><i>WARNING: </i>" + convertMessage(message) + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.WARNING, message);
	}

	/**
	 * Log message into report as a verify point
	 */
	public static void verify(String message) {
		log.info("VERIFY POINT: " + message);
		saveLog("VERIFY POINT: " + message);
		message = "<b style=\"color: blue;word-break:break-word;\"><i style=\"color: #00af00\">VERIFY POINT: </i>" + convertMessage(message) + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}

	/**
	 * Log message into report as a test case name
	 */
	public static void tc(String message) {
		log.info("TEST CASE: " + message);
		saveLog("TEST CASE: " + message);
		message = "<b style=\"color: #900C3F;word-break:break-word;\"><i style=\"color: #900C3F\">TEST CASE: </i>" + convertMessage(message) + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}

	/**
	 * Log message into report as a test objective
	 */
	public static void to(String message) {
		log.info("TEST OBJECTIVE: " + message);
		saveLog("TEST OBJECTIVE: " + message);
		message = "<b style=\"color: #F800DF;word-break:break-word;\"><i style=\"color: #F800DF\">TEST OBJECTIVE: </i>" + convertMessage(message) + "</b>";
		Reporter.log(message);
		ExtentTestManager.getTest().log(Status.INFO, message);
	}

	/**
	 * Save message into report
	 */
	private static void saveLog(String message) {
		String currentMethod = Thread.currentThread().getStackTrace()[3].getMethodName();
		String currentClass = Thread.currentThread().getStackTrace()[3].getClassName();
		if (!currentMethod.equals(methodName) || (currentMethod.equals(methodName) && !currentClass.equals(clasName))) {
			currentLogs.clear();
		}
		methodName = currentMethod;
		clasName = currentClass;
		currentLogs.add(message);
	}

	/**
	 * Handle HTML character
	 */
	private static String convertMessage(String message) {
		return message.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	/**
	 * Return the current log
	 */
	public static List<String> getCurrentLogs() {
		return currentLogs;
	}

	/**
	 * Save message into logfile
	 */
	public static void logFile(String fileName, String content) {
		String outputFolder = "test-output" + System.getProperty("file.separator") + "Log".concat("-").concat(Common.getNowTime("MM.dd.yyyy-HH.mm.ss"));
		String path = System.getProperty("user.dir") + System.getProperty("file.separator") + outputFolder;
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}

			File myFile = new File(path + System.getProperty("file.separator") + fileName);
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(myFile), StandardCharsets.UTF_8));
			bufferedWriter.write("All times are Central Time\r\n");
			bufferedWriter.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (Exception ex) {

			}
		}
		String message = "Log has been saved into " + path;
		saveLog(message);
		log.info(message);
		Reporter.log("<b>INFO: </b>" + convertMessage(message));
		ExtentTestManager.getTest().log(Status.INFO, "<span style=word-break:break-word>" + convertMessage(message) + "</span>");

	}
}
