package commanFunction;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilites.PropertyFileUtil;


		
public class FunctionLibrary {
	public static WebDriver driver;
//method for statr browser
	public static WebDriver startBrowser()throws Throwable
	{
		if(PropertyFileUtil.getValueforKey("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(PropertyFileUtil.getValueforKey("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser value not matching");
		}
		return driver;
	}
	//method for launch url
	public static void openUrl(WebDriver driver)throws Throwable
	{
		driver.get(PropertyFileUtil.getValueforKey("Url"));
	}
	//method for waitForElement
	public static void waitForElement(WebDriver driver,String Locator_type,String Locator_Value,String wait)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(wait)));
		if(Locator_type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		if(Locator_type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
		if(Locator_type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
	}
//method for textboxes
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
	}
	//method for links,buttons,checkboxes,radiobuttons and images
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}
	//method for title validation
	public static void validateTitle(WebDriver driver,String Expected_Title)
	{
		String Actual_Title= driver.getTitle();
		try {
			Assert.assertEquals(Expected_Title, Actual_Title, "Title is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	//method for closing browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	//method for date formate
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
	}
	//method for mouse click
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//*[@id=\"mi_a_stock_items\"]/a"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("//*[@id=\"mi_a_stock_categories\"]/a"))).click().perform();
		Thread.sleep(3000);
	}
	//method for category table
	public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable
	{
		if(! driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-textbox"))).sendKeys(Exp_Data);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-button"))).click();
		Thread.sleep(3000);
		//capture data from table
 		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Category name is Not Matching");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	
	public static void dropDownAction(WebDriver driver, String Locator_Type, String Locator_Value, String Test_Data )
	{
		if(Locator_Type.equalsIgnoreCase("xpath")) {
	       int value = Integer.parseInt(Test_Data);
	       WebElement element = driver.findElement(By.xpath(Locator_Value));
	       Select select  = new Select(element);
	       select.selectByIndex(value);
		}
	       
		if(Locator_Type.equalsIgnoreCase("id")) {
		       int value = Integer.parseInt(Test_Data);
		       WebElement element = driver.findElement(By.id(Locator_Value));
		       Select select  = new Select(element);
		       select.selectByIndex(value);
			}
		if(Locator_Type.equalsIgnoreCase("name")) {
		       int value = Integer.parseInt(Test_Data);
		       WebElement element = driver.findElement(By.name(Locator_Value));
		       Select select  = new Select(element);
		       select.selectByIndex(value);
			}
	}
	
	//capture number
	public static void captureStockNumber(WebDriver driver, String Locator_Type, String Locator_Value ) throws Throwable {
		
		String Exp_Data = "";
		if(Locator_Type.equalsIgnoreCase("xpath")) {
			Exp_Data = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
			
		}
		if(Locator_Type.equalsIgnoreCase("id")) {
			Exp_Data = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			
		}
		if(Locator_Type.equalsIgnoreCase("name")) {
			Exp_Data = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Exp_Data);
		bw.flush();
		bw.close();
	}
	
	
	//validate stock items
	public static void stockItemTable(WebDriver driver) throws Throwable {
		
		FileReader fr = new FileReader("./CaptureData/capturedata.txt");
		BufferedReader br = new BufferedReader(fr);
	    String  Exp_Data =	br.readLine();
	    if(! driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-textbox"))).sendKeys(Exp_Data);
		Thread.sleep(3000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueforKey("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		try {
		Assert.assertEquals(Exp_Data, Act_Data,"Table Stock Number Not Match ");
		}catch(Throwable t) {
			System.out.println(t.getMessage());
		}
		
		
	}
	
	
}


		
		
		
		
		
		
		
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


