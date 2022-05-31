package com.automation.tests;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.dataProviders.BookFlight_DP;
import com.automation.dataProviders.BookFlight_DP2;
import com.automation.pages.HomePage;
import com.automation.pages.PaymentPage;
import com.automation.pages.PurchaseSummaryPage;
import com.automation.pages.SearchResultsPage;
import com.automation.utility.Base;
import com.automation.utility.TestConfigUtil;
import com.relevantcodes.extentreports.LogStatus;

public class BookFlight extends Base{


	@BeforeMethod
	public void Initialize() throws IOException {
		System.setProperty("Webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/test/java/com/automation/resources/chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println(driver);
		driver.get(TestConfigUtil.GetConfigValue("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	
	@Test(dataProvider = "DP_Bookflight", dataProviderClass = BookFlight_DP.class)
	public void Test_BookFlight_1(String Tc_id, String Description, String Iteration, 
								  String Departure, String Destination, 
								  String Choose_By, String Choose_Value, String Name, 
								  String Address, String City, String State, String ZipCode,
								  String CardType, String CreditCardNumber, String Month, 
								  String Year, String NameOnCard) throws InterruptedException {
		
		logger = extent.startTest("Book Flight 1");
		HomePage homePage = new HomePage(driver);
		homePage.SearchFlights(Departure, Destination);
		Thread.sleep(2000);

		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
		searchResultsPage.SelectFlight(Choose_By, Choose_Value);
		Thread.sleep(2000);
		
		PaymentPage paymentPage = new PaymentPage(driver);
		Map<String, String> map = paymentPage.GetTicketInformation();

		paymentPage.PurchaseFlight( Name, Address,  City,  State,  ZipCode,
				   CardType,  CreditCardNumber,  Month, Year,  NameOnCard);
		Thread.sleep(2000);
		
		PurchaseSummaryPage purchaseSummaryPage = new PurchaseSummaryPage(driver);
		Map<String, String> purchaseSummaryMap = purchaseSummaryPage.GetPurchaseSummaryInformation(); 
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("amount")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("cardNumber")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("expiration")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("authcode")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("date")));
		
		purchaseSummaryPage.NavigateToHomePage();
		Thread.sleep(2000);
		
		Assert.assertEquals(true, true);
		logger.log(LogStatus.PASS, "PASS");
	}

	@Test(dataProvider = "DP_Bookflight2", dataProviderClass = BookFlight_DP2.class)
	public void Test_BookFlight_2(String Tc_id, String Description, String Iteration, 
								  String Departure, String Destination, 
								  String Choose_By, String Choose_Value, String Name, 
								  String Address, String City, String State, String ZipCode,
								  String CardType, String CreditCardNumber, String Month, 
								  String Year, String NameOnCard) throws InterruptedException {
		
		logger = extent.startTest("Book Flight 2");
		HomePage homePage = new HomePage(driver);
		homePage.SearchFlights(Departure, Destination);
		Thread.sleep(2000);

		SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
		searchResultsPage.SelectFlight(Choose_By, Choose_Value);
		Thread.sleep(2000);
		
		PaymentPage paymentPage = new PaymentPage(driver);
		Map<String, String> map = paymentPage.GetTicketInformation();
		
		paymentPage.PurchaseFlight( Name, Address,  City,  State,  ZipCode,
				   CardType,  CreditCardNumber,  Month, Year,  NameOnCard);
		Thread.sleep(2000);
		
		PurchaseSummaryPage purchaseSummaryPage = new PurchaseSummaryPage(driver);
		Map<String, String> purchaseSummaryMap = purchaseSummaryPage.GetPurchaseSummaryInformation(); 
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("amount")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("cardNumber")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("expiration")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("authcode")));
		logger.log(LogStatus.PASS, String.valueOf(purchaseSummaryMap.get("date")));
		
		purchaseSummaryPage.NavigateToHomePage();
		Thread.sleep(2000);
		
		Assert.assertEquals(true, true);
		logger.log(LogStatus.PASS, "PASS");
	}


	@AfterMethod
	public void CloseBrowser(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			String screenshotPath = TestConfigUtil.getScreenshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		extent.endTest(logger);
		driver.close();
	}
	
	@AfterSuite
	public void GenerateReport() {
		extent.flush();
		extent.close();
	}
	
}
