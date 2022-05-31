package com.automation.utility;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Base {
	
	protected WebDriver driver;
	protected static ExtentReports extent;
	protected static ExtentTest logger;
	
	
	
	@BeforeSuite
	public static void InitializeReport() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/x-tent/" + TestConfigUtil.GenerateTimeStamp() + ".html",true);
		extent.addSystemInfo("Host Name", "Software Testing Material")
						 .addSystemInfo("Environment", "Automation Testing")
						 .addSystemInfo("User Name", System.getProperty("user.dir"));
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		
		
	}
	
	@AfterSuite
	public void GenerateReport() {
		extent.flush();
		extent.close();
	}

}
