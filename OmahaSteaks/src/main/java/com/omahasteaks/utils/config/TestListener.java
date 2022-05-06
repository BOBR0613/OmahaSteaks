package com.omahasteaks.utils.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer; 
import java.net.UnknownHostException;   
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentTest;  
import com.logigear.driver.DriverUtils;
import com.omahasteaks.utils.common.Common;
import com.omahasteaks.utils.common.Constants; 
import com.omahasteaks.utils.helper.Logger;
import com.omahasteaks.utils.helper.ReRunTestCollector;
import com.omahasteaks.utils.report.ExtentManager;
import com.omahasteaks.utils.report.ExtentTestManager;

public class TestListener implements ITestListener {
    private static Map<String, ExtentTest> testSuite = new HashMap<String, ExtentTest>();
    private static List<ITestNGMethod> skippedMethods = new ArrayList<ITestNGMethod>();
    private static List<ITestContext> listTestContext = new ArrayList<ITestContext>();
    static String workingDir = System.getProperty("user.dir"); 

    private static String FAILED = "FAILED";
    private static String PASSED = "PASSED";
    private static String COMPLETED = "COMPLETED";
    private Integer numTests = 0;
    
    public void onTestFailure(ITestResult result) {
    String HOST_NAME="UNKNOWN";
	// Get screenshot
	String screenshotFileName = UUID.randomUUID().toString();
	String path = DriverUtils.captureScreenshot(screenshotFileName, ExtentTestManager.getScreenshotFolder());
	Common.captureScreenshot("full_" + screenshotFileName, ExtentTestManager.getScreenshotFolder());
	String script = Common.screenshotURI(path);
	Reporter.log(script);

	// Handle for ExtentReports
	String executionInfo = Common.getRemoteInfo();
	try {
	    ExtentTest detailFailed = ExtentTestManager.getTest();
	    recheckURLConnection(detailFailed, result.getMethod());
	    
	    if (result.getThrowable().toString().contains("Assert")) {
		detailFailed.fail("<b>This test case check failed.</b>" + executionInfo);
		detailFailed.fail(result.getThrowable());
	    } else {
		detailFailed.error("<b>This test case failed by error.</b>" + executionInfo);
		detailFailed.error(result.getThrowable());
	    }
	    detailFailed.addScreenCaptureFromPath("screenshots\\" + screenshotFileName + ".png");
	    detailFailed.addScreenCaptureFromPath("screenshots\\full_" + screenshotFileName + ".png");
	    
	    Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
	    try {
			HOST_NAME = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	Constants.DB.writeDbRecord(detailFailed, HOST_NAME, caps, result, FAILED);
	    
	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    private void recheckURLConnection(ExtentTest test, ITestNGMethod method) {
	StringBuilder msg = new StringBuilder();
	String Url = DriverUtils.getWebDriver().getCurrentUrl();

	if (!DriverUtils.isUrlStable(Url, msg)) {
	    skippedMethods.add(method);
	    test.warning("<b>Failed at middle of test case: </b>" + msg);
	} else if (Common.MODE.getCurrentPlatform().equals(Constants.PLATFORM_ANDROID) || Common.MODE.getCurrentPlatform().equals(Constants.PLATFORM_TABLET)) {
	    if (!Common.pingHost("www.google.com", msg)) {
		skippedMethods.add(method);
		test.warning("<b>Failed at middle of test case: </b>" + msg);
	    }
	} 
    }

    public void onTestStart(ITestResult result) {
 	ExtentTestManager.startTest(result.getMethod().getMethodName(), "", testSuite.get(result.getTestContext().getName()));
	ExtentTestManager.getTest().assignCategory(result.getTestContext().getName());
	Logger.info(String.format("TEST CASE: %s.%s", result.getTestClass().getName(), result.getName()).replace("_", "_ "));
	// Handle to Skip test case
	ITestContext context = result.getTestContext();
	Object skip = context.getAttribute("isSkipped");
	Throwable throwable = (Throwable) context.getAttribute("message");
	System.out.println("<<<<<<<<<<<Executing test " + ++numTests + "  " + result.getMethod().getMethodName()+">>>>>>>>>>>>>>>");
	
	if (skip != null && (boolean) skip) {
	    skippedMethods.add(result.getMethod());
	    throw new SkipException(throwable.getMessage());
	}
    }

    public void onTestSuccess(ITestResult result) {
	    String HOST_NAME="UNKNOWN";
		ExtentTest detailInfo = ExtentTestManager.getTest();
		String executionInfo = Common.getRemoteInfo();
		detailInfo.pass("Test passed." + executionInfo);
		System.out.println("Duration--" + detailInfo.getModel().getRunDuration());
	
	    Capabilities caps = ((RemoteWebDriver) DriverUtils.getWebDriver()).getCapabilities();
	    try {
			HOST_NAME = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   Constants.DB.writeDbRecord(detailInfo, HOST_NAME, caps, result, PASSED);
    }

    public void onTestSkipped(ITestResult result) {
    //	ExtentTest detailSkip = ExtentTestManager.getTest();
    //	detailSkip.skip("<b>This test case skipped by: </b>");
    //	detailSkip.skip(result.getThrowable().getMessage());
    }
    

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
    }

    public void onStart(ITestContext context) {
	if (!ExtentTestManager.isTestExisted(context.getName())) {
	    ExtentTest tmpSuite = ExtentTestManager.startTest(context.getName(), "", null);
	    testSuite.put(context.getName(), tmpSuite);
	}
	System.setProperty("org.uncommons.reportng.escape-output", "false");
    }

    public void onFinish(ITestContext context) {
	try {
	    ExtentManager.getReporter().flush();
	    listTestContext.add(context);

	    // Support parallel
	    if (listTestContext.size() == context.getSuite().getXmlSuite().getTests().size()) {
		if (skippedMethods.size() > 0) {
		    XmlSuite suite = ReRunTestCollector.CreateSkipTestsSuite(skippedMethods, listTestContext);
		    if (suite != null && suite.getTests().size() > 0) {

			ReRunTestCollector.saveToFile(ExtentTestManager.getOutputFullPath() + System.getProperty("file.separator") + "RerunTestCases.xml", suite.toXml());
			String xmlFile = ExtentTestManager.getOutputFolder() + System.getProperty("file.separator") + "RerunTestCases.xml";
            System.out.println("xmlFile:::" + xmlFile);
			System.out.println("--------------------------------RE-RUN TESTS--------------------------------");
			// Need to set variable environment for M2_HOME
			// Set variable environment for MAC
			if (Common.isMacExecution()) {
			    String scriptContent = "export M2_HOME=" + Constants.MAVEN_HOME_MAC + " \nexport M2=$M2_HOME/bin \nexport PATH=$M2:$PATH \ncd " + workingDir + "\nmvn clean install -DTESTNG_XML_FILE=" + "\"" + xmlFile + "\"";
			    try {
				System.out.println("Running...");
				Writer output = new BufferedWriter(new FileWriter("/tmp/rerun.sh"));
				output.write(scriptContent);
				output.close();
				Process p = Runtime.getRuntime().exec("sh /tmp/rerun.sh");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
				    System.out.println(line);
				}
				//int exitVal = p.waitFor();
			    } catch (IOException ex) {
			    }
			} else {
			    InvocationRequest request = new DefaultInvocationRequest();
			    List<String> goals = new ArrayList<String>();
			    goals.add("clean");
			    goals.add("install");
			    goals.add("-DTESTNG_XML_FILE=" + "\"" + xmlFile + "\"");
			    request.setGoals(goals);
			    Invoker invoker = new DefaultInvoker();
			    invoker.execute(request);
			}
		    }
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
    
 }