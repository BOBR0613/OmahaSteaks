package com.omahasteaks.utils.helper;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

public class SkipTestCollector {
    public static XmlSuite CreateSkipTestsSuite(List<ITestNGMethod> skipTests, String SuiteName, String browser, String mode) {
	XmlSuite xmlSuite = null;
	/********************
	try {
	    // Add Test Suite
	    xmlSuite = new XmlSuite();
	    xmlSuite.setName("Skip Test Cases In " + SuiteName);
	    xmlSuite.setThreadCount(skipTests.size());
	    xmlSuite.setParallel(ParallelMode.TESTS);
	    // Add Listeners
	    ArrayList<String> listeners = new ArrayList<String>();
	    listeners.add("org.uncommons.reportng.HTMLReporter");
	    listeners.add("com.omahasteaks.utils.config.TestListener");
	    xmlSuite.setListeners(listeners);
	    // Add Parameters
	    XmlTest xmlTest = new XmlTest(xmlSuite);
	    xmlTest.setName(SuiteName);
	    xmlTest.addParameter("mode", mode);
	    xmlTest.addParameter("browser", browser);
	    // Add Test classes, Test methods
	    List<XmlClass> classes = new ArrayList<XmlClass>();
	    Map<ITestClass, XmlClass> map = new HashMap<ITestClass, XmlClass>();
	    for (ITestNGMethod item : skipTests) {
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
		xmlClass.getIncludedMethods().add(new XmlInclude(item.getMethodName()));
	    }
	    xmlTest.setClasses(classes);
	} catch (Exception ex) {
	    System.out.println("Getting ERROR: " + ex.getMessage());
	    ex.printStackTrace();
	}
    *************/
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
	    System.out.println("Getting ERROR: " + ex.getMessage());
	    ex.printStackTrace();
	}
    }
}
