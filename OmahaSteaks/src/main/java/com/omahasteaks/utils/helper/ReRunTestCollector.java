package com.omahasteaks.utils.helper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class ReRunTestCollector {
    @SuppressWarnings("deprecation")
    public static XmlSuite CreateSkipTestsSuite(List<ITestNGMethod> skipTests, List<ITestContext> listTestContext) {
	XmlSuite xmlSuite = null;
	try {
	    // Add Test Suite
	    xmlSuite = new XmlSuite();
	    xmlSuite.setName("Re-run tests");
	    xmlSuite.setThreadCount(skipTests.size());
	    xmlSuite.setParallel(ParallelMode.FALSE);
	    // Add Listeners
	    ArrayList<String> listeners = new ArrayList<String>();
	    listeners.add("org.uncommons.reportng.HTMLReporter");
	    listeners.add("com.omahasteaks.utils.config.TestListener");
	    xmlSuite.setListeners(listeners);
	    // Add Parameters

	    for (int i = 0; i < listTestContext.size(); i++) {
		ITestContext context = listTestContext.get(i);
		XmlTest current = context.getCurrentXmlTest();
		int reRunFailedTests = Integer.parseInt(current.getParameter("reRunFailedTests"));
		String failurePercentage = current.getParameter("failurePercentage");
		int numberOfTest = (int) Arrays.asList(context.getAllTestMethods()).stream().filter(x -> x.getXmlTest().equals(current)).count();
		int rerunTests = (int) skipTests.stream().filter(x -> x.getXmlTest().equals(current)).count();
		int percent = (int) ((rerunTests * 100.0f) / numberOfTest);

		if (reRunFailedTests < 1 || percent > Integer.parseInt(failurePercentage)) {
		    continue;
		}

		XmlTest xmlTest = new XmlTest(xmlSuite);

		xmlTest.setName("RE-RUN " + current.getName());
		xmlTest.addParameter("mode", current.getParameter("mode"));
		if (current.getParameter("site") != null)
		    xmlTest.addParameter("site", current.getParameter("site"));
		xmlTest.addParameter("browser", current.getParameter("browser"));
		// xmlTest.addParameter("autoLogBug", "false");
		xmlTest.addParameter("reRunFailedTests", String.valueOf(reRunFailedTests - 1));
		xmlTest.addParameter("failurePercentage", current.getParameter("failurePercentage"));

		// Add Test classes, Test methods
		List<XmlClass> classes = new ArrayList<XmlClass>();
		Map<ITestClass, XmlClass> map = new HashMap<ITestClass, XmlClass>();
		for (ITestNGMethod item : skipTests) {
		    if (current.equals(item.getXmlTest())) {
			XmlClass xmlClass;
			ITestClass testClass = item.getTestClass();
			if (map.containsKey(testClass))
			    xmlClass = map.get(testClass);
			else {
			    xmlClass = new XmlClass();
			    xmlClass.setName(item.getTestClass().getName());
			    map.put(testClass, xmlClass);
			    classes.add(xmlClass);
			}
			// xmlClass.getIncludedMethods().add(new XmlInclude(item.getMethodName()));
		    }
		}
		xmlTest.setClasses(classes);
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return xmlSuite;
    }

    public static void saveToFile(String filePath, String content) {
	try {
	    File file = new File(filePath);
	    File directory = new File(file.getParent());
	    if (!directory.exists())
		directory.mkdirs();
	    if (file.exists()) {
		file.delete();
	    }
	    FileWriter fileWriter = new FileWriter(file);
	    fileWriter.write(content);
	    fileWriter.flush();
	    fileWriter.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
}
