package ProjectAssignment;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class Assignment {

	public String base_url = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;
	
	ExtentReports report;
	ExtentTest test;
	@BeforeClass
	public void report()
	{
		 report = new ExtentReports("user.dir"+ "extendreport.html");
		 test= report.startTest("extendreport");  
	}
	@BeforeTest
	public void setup() throws InterruptedException
	{
		System.out.println("Before Test Execution");
		
		driver = new ChromeDriver();
		driver.get(base_url);
		
		//maximize windows
		driver.manage().window().maximize();
		
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60)); //60 seconds
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000)); 
//		Thread.sleep(10000);
	}
	
	
	
	@Test(priority = 1,enabled = true)
	public void loginTestWithInValidUserName() throws InterruptedException
	{
	
		//find username and enter  wrong username "Admin1"
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin1");
		
		//find password and enter password "admin123"
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		
		//click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		String text = driver.findElement(By.xpath("(//p[@class='oxd-text oxd-text--p oxd-alert-content-text'])[1]")).getText();
				
		System.out.println("Error message : " + text);
		
		String message_expected = "Invalid credentials";
		
		Assert.assertTrue(text.contains(message_expected));
		
		test.log(LogStatus.PASS, "Invalid credentials !!!");
		Thread.sleep(1500);
	}
	
	
	@Test(priority = 2,enabled = true)
	public void loginTestWithInValidPassword() throws InterruptedException
	{ 
	
		//find username and enter   username "Admin1"
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		
		//find password and enter wrong password "admin123"
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("123");
		
		//click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		String text = driver.findElement(By.xpath("(//p[@class='oxd-text oxd-text--p oxd-alert-content-text'])[1]")).getText();
				
		System.out.println("Error message : " + text);
		
		String message_expected = "Invalid credentials";
		
		Assert.assertTrue(text.contains(message_expected));
		test.log(LogStatus.PASS, "Invalid credentials !!!");
		Thread.sleep(1500);
	}
	
	@Test(priority = 3,enabled = true)
	public void EmptyUsernameEmptyPassword () throws InterruptedException
	{ 
		driver.findElement(By.xpath("//input[@placeholder='Username']"));
		
		//find password and enter wrong password "admin123"
		driver.findElement(By.xpath("//input[@placeholder='Password']"));
		
		//click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		System.out.println("Both fields are empty");
		test.log(LogStatus.PASS, "Both fields are empty");
	}
	
	@Test(priority = 4,enabled = true)
	public void CaseSensitiveData () throws InterruptedException
	{ 
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("ADMIN");
		
		//find password and enter password "admin123"
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("ADMIN123");
		
		//click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		System.out.println("Both username and password should be case sensitive");
		test.log(LogStatus.PASS, "Both username and password should be case sensitive");
	}
	
	
	@Test(priority = 5,enabled = true)
	public void loginTestWithValidCredential() throws InterruptedException
	{
		//find username and enter username "Admin"
		driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("Admin");
		
		//find password and enter password "admin123"
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
		
		//click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		System.out.println("The user  logged in successfully.");
		test.log(LogStatus.PASS, "The user  logged in successfully. !!!");
		Logout_functionality();
	}
	
	public void Logout_functionality() throws InterruptedException
	{
		
		driver.findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000)); 
		
		List<WebElement> elementList = driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
		
		for(int i=0;i<elementList.size();i++)
		{
			Thread.sleep(1000);
			System.out.println(elementList.get(i).getText());	
		}
		elementList.get(3).click();
	}

	@Test(priority = 6,enabled = true)
	public void forgotPassword()
	{
		////p[.='Forgot your password? ']
		driver.findElement(By.xpath("//p[.='Forgot your password? ']")).click();
		System.out.println("The user has been redirected to the forgot password page");
		test.log(LogStatus.PASS, "The user has been redirected to the forgot password page");
	}
	
	
	@AfterTest
	public void teardown() throws InterruptedException
	{
		
		System.out.println("After Test Execution");
		Thread.sleep(2000);
		driver.close();
		driver.quit();
	}
	@AfterClass
	public void reportflush()
	{
		report.endTest(test);
		report.flush();
	}
}
