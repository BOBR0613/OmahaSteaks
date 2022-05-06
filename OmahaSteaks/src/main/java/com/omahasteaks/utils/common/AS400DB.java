package com.omahasteaks.utils.common;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

import org.joda.time.LocalDateTime;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.ibm.as400.access.*;
import com.logigear.driver.DriverUtils;
import com.omahasteaks.utils.report.ExtentTestManager; 

public class AS400DB {

	private static AS400JDBCDataSource datasource=null;
	private static Connection connection=null;
	private static PreparedStatement stmt=null; 
	private static LocalDateTime today;
	private static BigDecimal dayval;
	private static BigDecimal timval;
	private static BigDecimal testdt;


	public AS400DB ( ) {
		datasource =  new AS400JDBCDataSource("SYSA", "BOBPROD", "ROSSE#2020");
		datasource.setLibraries("*LIBL"); 
		datasource.setNaming("system"); 
		today = LocalDateTime.now(); 
		testdt = new BigDecimal(1000000 + (today.getYear()-2000) * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth());
		try {
			connection = datasource.getConnection();
		} catch (SQLException e) { 
			e.printStackTrace();
		} 		
	}

	public AS400DB (String userName, String password) {
		datasource =  new AS400JDBCDataSource("SYSA", userName, password);
		datasource.setLibraries("*LIBL"); 
		datasource.setNaming("system"); 
		today = LocalDateTime.now(); 
		dayval = new BigDecimal(1000000 + (today.getYear()-2000) * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth());
		timval = new BigDecimal(today.getHourOfDay()*10000 + today.getSecondOfMinute()*100);
		try {
			connection = datasource.getConnection();
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
	}

	/********************************************************************************** 
	 * Process records from TMPRP                                                     * 
	 **********************************************************************************/
	public ResultSet getRepByName (String repname) {
		ResultSet rs=null;
		try {	    	
			stmt = connection.prepareStatement("select * from tmprp where rpphna=? ");
			stmt.setString(1, repname.toUpperCase());
			rs=stmt.executeQuery();  	 
		} catch (SQLException e) { e.printStackTrace(); }	
		return (rs);
	}

	/********************************************************************************** 
	 * Process buyer name & address records                                           * 
	 **********************************************************************************/
	public ResultSet getAddressByCuno (String cuno) {
		ResultSet rs=null;
		try {	    	
			stmt = connection.prepareStatement("select * from tmlna100 where nacuno=? ");
			stmt.setString(1, cuno);
			rs=stmt.executeQuery();  	 
		} catch (SQLException e) { e.printStackTrace(); }	
		return (rs);
	}
	
	/********************************************************************************** 
	 * Process buyer name & address records                                           * 
	 **********************************************************************************/
	public ResultSet getReceiverAddress (String cuno, String rcvrid) {
		ResultSet rs=null;
		try {	    	
			if (rcvrid.equalsIgnoreCase("")) {
			  stmt = connection.prepareStatement("select * from tmlna100 where nacuno=? ");
  			  stmt.setString(1, cuno);
			}
			else {
				  stmt = connection.prepareStatement("select * from tmlnb100 where nacuno=? and narcvr=? ");
	  			  stmt.setString(1, cuno);
	  			  stmt.setString(2, rcvrid);
			}
			rs=stmt.executeQuery();  	 
		} catch (SQLException e) { e.printStackTrace(); }	
		return (rs);
	}
	

	/********************************************************************************** 
	 * Get the member id from the email address                                       * 
	 **********************************************************************************/
	public int getMemberId(String mbrname) {
		ResultSet rs=null; 
		int rtnval=0;
		try {	    	
			stmt = connection.prepareStatement("select * from webdb2/wbmbrmst where mbrnam=?");
			stmt.setString(1, mbrname.toUpperCase());
			rs=stmt.executeQuery();  	 
			rs.next(); 
		} catch (SQLException e) { e.printStackTrace(); }	

		try {
			rtnval = rs.getInt("MBRID"); 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtnval;
	}
	

	/********************************************************************************** 
	 * update the gold membership information expiration date                         * 
	 **********************************************************************************/
	public void updateGoldExpireDate (String mbrname, int numdays) {
		today = LocalDateTime.now();
		int mbrid = getMemberId(mbrname.toUpperCase());
		BigDecimal id = BigDecimal.valueOf(mbrid);
		LocalDateTime expDate = today.plusDays(numdays);
		BigDecimal exdt = new BigDecimal(1000000 + (expDate.getYear()-2000) * 10000 + expDate.getMonthOfYear() * 100 + expDate.getDayOfMonth());
		LocalDateTime effectDate = expDate.plusDays(-365);
		BigDecimal efdt = new BigDecimal(1000000 + (effectDate.getYear()-2000) * 10000 + effectDate.getMonthOfYear() * 100 + effectDate.getDayOfMonth());

		try {	    	
			stmt = connection.prepareStatement("update webdb2/wbmbrsub set activeyn=?, expiredt=?, efdt=?, startdt=? where mbrid=?");
			stmt.setString(1, "Y");
			stmt.setBigDecimal(2, exdt);
			stmt.setBigDecimal(3, efdt);
			stmt.setBigDecimal(4, efdt);
			stmt.setBigDecimal(5, id);
			stmt.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }	
	}


	/********************************************************************************** 
	 * write results to sysA                                                          * 
	 **********************************************************************************/
	public void writeDbRecord(ExtentTest detailInfo, String hostName, Capabilities caps, ITestResult result, String status) {
		try {
			stmt = connection.prepareStatement("insert into OSIREPO/QAPTC "
					+ "(APP,URL,TESTDT,UDT,UTM,MACHINE,MODE,BROWSER,VERSION,PLATFORM,DURATION,TESTCASE,STATUS,ERRDESC,PROCESSED) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try { 
			today = LocalDateTime.now(); 
			dayval = new BigDecimal(1000000 + (today.getYear()-2000) * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth());
			timval = new BigDecimal(today.getHourOfDay()*10000 + today.getMinuteOfHour()*100 + today.getSecondOfMinute());
  			stmt.setString(1, "OMASTK");
			stmt.setString(2, Constants.OMAHA_URL);
			stmt.setBigDecimal(3, testdt); 
			stmt.setBigDecimal(4, dayval); 
			stmt.setBigDecimal(5, timval);
			stmt.setString(6, hostName);
			stmt.setString(7, DriverUtils.getRunningMode());
			stmt.setString(8, caps.getBrowserName());
			stmt.setString(9, caps.getVersion());
			stmt.setString(10, caps.getPlatform().name()+" "+caps.getPlatform().getMajorVersion()+"."+caps.getPlatform().getMinorVersion());
			stmt.setString(11, detailInfo.getModel().getRunDuration());
			if (result==null) stmt.setString(12, "completed regression tests");
			else stmt.setString(12, result.getMethod().getMethodName().toString());
			stmt.setString(13, status);			

			String msg="";
			if (status=="FAILED") {
				int maxLength = 249;
				Throwable t = result.getThrowable();
				if ((t != null) && ((msg = t.getMessage()) != null)) {
					msg = msg.trim();
					if (msg.length() > maxLength) {
						msg = msg.substring(0, maxLength);
					} else {
						// the length of the msg string object will be the current string length
					}
				} else {
					msg = "Exception Unknown!";
				}		      
			}
			stmt.setString(14, msg);
			stmt.setString(15, " ");
			stmt.executeUpdate();

		} catch (SQLException e) { 
			e.printStackTrace();
		}  
	}
	
	

	/********************************************************************************** 
	 * gets the country code for the given state                                      * 
	 **********************************************************************************/
	public String getCountry(String state) {
		ResultSet rs=null; 
		String rtnCountry="US";
		try {	    	
			stmt = connection.prepareStatement("select * from files/sypnm where nmkk='ST' and nmkv=?");
			stmt.setString(1, state.toUpperCase());
			rs=stmt.executeQuery();  	 
			rs.next();

		} catch (SQLException e) { e.printStackTrace(); }	

		try {
			rtnCountry = rs.getString("nmkv1").trim();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rtnCountry.trim();
	}
	

	/********************************************************************************** 
	 * get the email address for the customer number                                  * 
	 **********************************************************************************/
	public String getCustEmail(String cuno) {
		ResultSet rs=null; 
		String rtnEmail="";
		try {	    	
			stmt = connection.prepareStatement("select * from mirrordb/ollemail where cuno=?");
			stmt.setString(1, cuno);
			rs=stmt.executeQuery();  	 
			rs.next();

		} catch (SQLException e) { e.printStackTrace(); }	

		try {
			rtnEmail = rs.getString("emaddr").trim();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rtnEmail.trim();
	}
	

	/********************************************************************************** 
	 * get a current gift cert for use when redeeming points                          * 
	 **********************************************************************************/
	public static String getGCID(String gctype) {
		ResultSet rs=null; 
		String rtnId="";
		today = LocalDateTime.now(); 
		testdt = new BigDecimal(1000000 + (today.getYear()-2000) * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth());

		try {	    	
			if (gctype.equalsIgnoreCase("BOR"))
				stmt = connection.prepareStatement("SELECT * FROM MIRRORDB/GCPIS WHERE ISGCTP='BOR' AND ISX='640' "  + 
				                               "AND ISCLASS='P' AND ISSUBCLASS='P' AND ISEXDT>" + testdt + " AND ISAMT>45");
			else
				stmt = connection.prepareStatement("SELECT * FROM MIRRORDB/GCPIS inner join MIRRORDB/GCPSU on ISRQSID=SURQSID WHERE SUFORM='ARADIUS' AND ISGCTP='PPD' AND ISX='640' "  + 
                        "AND ISCLASS='P' AND ISSUBCLASS='P' AND ISEXDT>" + testdt + " AND ISAMT>55 ");
			rs=stmt.executeQuery();  	 
			rs.next();

		} catch (SQLException e) { e.printStackTrace(); }	

		try {
			rtnId = rs.getString("isgcid").trim(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return rtnId.trim();
	}

	public void sendHTMLReport() {
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
	   Constants.DB.writeDbRecord(detailInfo, HOST_NAME, caps, null, "COMPLETED");
 	}
		   
}
