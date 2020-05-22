package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.crm.qa.utils.TestUtils;
import com.crm.qa.utils.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static WebEventListener eventListener;
	public static EventFiringWebDriver eventfiringwebdriver;
	
	//Test Base Class for instantiating browser
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("C:\\Users\\gyanprakash2\\git\\GyanCRM-Git\\GyanCRM-Git\\src\\main\\java\\com"
					+ "\\crm\\qa\\config\\config.properties");
			//Adding File Path
			prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void initialization(){
		String BrowserName = prop.getProperty("browser");
		if (BrowserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\DATA\\Data\\Gyan-Personal\\Selenium_Artifacts\\chromedriver83.exe");
			driver = new ChromeDriver();
		} else if (BrowserName.equals("internetexplorer")) {
			System.setProperty("webdriver.ie.driver", "C:\\DATA\\Data\\Gyan-Personal\\Selenium_Artifacts\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
		
		eventfiringwebdriver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		eventfiringwebdriver.register(eventListener);
		driver = eventfiringwebdriver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
	}
	
	

}
