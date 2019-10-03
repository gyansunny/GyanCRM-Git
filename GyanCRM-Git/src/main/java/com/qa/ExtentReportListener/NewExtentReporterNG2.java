package com.qa.ExtentReportListener;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Date;

import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;
import org.testng.Assert;
import org.testng.IReporter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class NewExtentReporterNG2 implements IReporter{
	
	ExtentReports extent;
	ExtentTest logger;
	
	@BeforeSuite
	 public void startReport(){
	 //ExtentReports(String filePath,Boolean replaceExisting) 
	 //filepath - path of the file, in .htm or .html format - path where your report needs to generate. 
	 //replaceExisting - Setting to overwrite (TRUE) the existing file or append to it
	 //True (default): the file will be replaced with brand new markup, and all existing data will be lost. Use this option to create a brand new report
	 //False: existing data will remain, new tests will be appended to the existing report. If the the supplied path does not exist, a new file will be created.
	 extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/ExtentReport.html", true);
	 //extent.addSystemInfo("Environment","Environment Name")
	 extent
	                .addSystemInfo("Host Name", "IN-GYANPRAKASH2")
	                .addSystemInfo("Environment", "FreeCRMProject")
	                .addSystemInfo("User Name", "gyanprakash2");
	 //loading the external xml file (i.e., extent-config.xml) placed under the base directory
	 extent.loadConfig(new File(System.getProperty("user.dir")+ "\\extent-config.xml"));
	 }
	 
	 @Test
	 public void passTest(){
	 //extent.startTest("TestCaseName", "Description")
	 //TestCaseName – Name of the test
	 //Description – Description of the test
	 //Starting test
	 logger = extent.startTest("passTest");
	 Assert.assertTrue(true);
	 //To generate the log when the test case is passed
	 logger.log(LogStatus.PASS, "Test Case Passed is passTest");
	 }
	 
	 @Test
	 public void failTest(){
	 logger = extent.startTest("failTest");
	 Assert.assertTrue(false);
	 logger.log(LogStatus.PASS, "Test Case (failTest) Status is passed");
	 }
	 
	 @Test
	 public void skipTest(){
	 logger = extent.startTest("skipTest");
	 throw new SkipException("Skipping - This is not ready for testing ");
	 }
	 
	 @AfterMethod
	 public void getResult(ITestResult result){
	 if(result.getStatus() == ITestResult.FAILURE){
	 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getName());
	 logger.log(LogStatus.FAIL, "Test Case Failed is "+result.getThrowable());
	 }else if(result.getStatus() == ITestResult.SKIP){
	 logger.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());
	 }
	 // ending test
	 //endTest(logger) : It ends the current test and prepares to create HTML report
	 extent.endTest(logger);
	 }
	 
	 @AfterSuite
	 public void endReport(){
	 // writing everything to document
	 //flush() - to write or update test information to your report. 
	                extent.flush();
	                //Call close() at the very end of your session to clear all resources. 
	                //If any of your test ended abruptly causing any side-affects (not all logs sent to ExtentReports, information missing), this method will ensure that the test is still appended to the report with a warning message.
	                //You should call close() only once, at the very end (in @AfterSuite for example) as it closes the underlying stream. 
	                //Once this method is called, calling any Extent method will throw an error.
	                //close() - To close all the operation
	                extent.close();
	    }
	
	
	

}
