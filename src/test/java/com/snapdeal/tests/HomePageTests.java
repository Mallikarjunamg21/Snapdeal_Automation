package com.snapdeal.tests;

import com.snapdeal.page.HomePage;
import com.snapdeal.utils.Xls_Reader;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.snapdeal.base.TestBase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomePageTests extends TestBase
{

	HomePage homePage;

	public HomePageTests()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		homePage = new HomePage();
	}

	@Test
	public void ToCheckTitle() {
		System.out.println(driver.getTitle());
	}

	@Test(dataProvider = "searchItemsData")
	public void ToValidateSearchTextFeild(String SearchItem){
		WebElement search_txt= driver.findElement(By.xpath("//input[@name='keyword']"));
		 String placehold = search_txt.getAttribute("placeholder");
		System.out.println(placehold);
		String placeholder2 = "Search products & brands";
		Assert.assertEquals(placehold,placeholder2,"place holder matching as expected");
		search_txt.sendKeys(SearchItem);
		List<WebElement> keywords = driver.findElements(By.xpath("//a[@type='keyword']"));
		List<String> allKeys = new ArrayList<String>();
		List<String> counts = new ArrayList<String>();
		for (WebElement words:keywords)
		{
			String texts= words.getText();
			allKeys.add(texts);
		}
		for (String ask:allKeys) {
			if(ask.contains(SearchItem))
			{
				counts.add(ask);
			}else
			{
				System.out.println("No matching found");
			}
		}
		System.out.println("No of Matching Strings : "+counts.size());
	}

	@DataProvider(name = "searchItemsData")
	public String[] dataExcelExtract() {
		Xls_Reader excel;
		String[] dataList;
		excel = new Xls_Reader("C:\\Users\\Lenovo\\IdeaProjects\\Snapdeal_Automation\\src\\main\\java\\com\\snapdeal\\testData\\Snapdeal.xlsx");
		int rowCount = excel.getRowCount("SnapdealHome");
		System.out.println(rowCount);

		dataList = new String[rowCount - 1]; // Since you are skipping the first row in the loop
		for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
			dataList[rowNum - 2] = excel.getCellData("SnapdealHome", "SearchItems", rowNum);
		}

		return dataList;
	}

		@Test
		public void Test_newTab_togetOTP() throws AWTException, StaleElementReferenceException, InterruptedException {
		WebElement signIn = driver.findElement(By.xpath("//span[.='Sign In']"));
		WebElement register = driver.findElement(By.xpath("//span[.='Register']"));
			Actions mouse = new Actions(driver);
			mouse.moveToElement(signIn);
			mouse.moveToElement(register).click().build().perform();
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[id='signin_box']"))));
			//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iframeLogin")));
			WebElement frame =driver.findElement(By.xpath("//div[@id='iframeContainer']/iframe"));

			try {
				driver.switchTo().frame("iframeLogin");
				// Perform some action on the element (e.g., click, sendKeys, etc.)
				String frameUrl = frame.getAttribute("src");
				Robot k= new Robot();
				k.keyPress(KeyEvent.VK_CONTROL);
				k.keyPress(KeyEvent.VK_T);
				k.keyRelease(KeyEvent.VK_CONTROL);
				k.keyRelease(KeyEvent.VK_T);
				driver.navigate().to(frameUrl);

			} catch (StaleElementReferenceException e) {

				System.out.println("Hi retry");
			}
			//Thread.sleep(5000);
			WebElement username = driver.findElement(By.xpath("//input[@id='userName']"));
			username.sendKeys("mallikarjunamg21@outlook.com");
			WebElement cont = driver.findElement(By.xpath("//button[@id='checkUser']"));
			cont.click();
			driver.switchTo().defaultContent();
			Robot k= new Robot();
			k.keyPress(KeyEvent.VK_CONTROL);
			k.keyPress(KeyEvent.VK_T);
			k.keyRelease(KeyEvent.VK_CONTROL);
			k.keyRelease(KeyEvent.VK_T);
			driver.navigate().to("https//www.yahoomail.com");



		}

		@AfterMethod
		public void tearDown() {
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
			ImageIO.write(screenshot.getImage(), "jpg", new File("C:/Users/Lenovo/IdeaProjects/Snapdeal_Automation/Screenshots/"+Test.class+".jpg"));
					
			driver.close();
		}

	}
