package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginPageTest extends TestBase{
	LoginPage loginPage;
	HomePage homePage;
	
	public LoginPageTest(){
		super();
	}

	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();
	}
	
	@Test (priority=1)
	public void TitleTest(){
		String title = loginPage.LoginPageTitle();
		Assert.assertEquals(title, "CRM");
	}
	
	@Test (priority=2)
	public void EmailFieldPresentTest(){
		boolean flag = loginPage.EmailFieldPresent();
		Assert.assertTrue(flag);
	}
	
	@Test (priority=3)
	public void LoginTest(){
		homePage = loginPage.Login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	
	@AfterMethod
	public void tearDown(){
		driver.close();
		driver.quit();
	}
	
	
}
