package com.crm.qa.testcases;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.Contacts;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.utils.TestUtils;

public class HomePageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	TestUtils testUtil;
	Contacts contacts;
	
	public HomePageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();
		testUtil = new TestUtils();
		contacts = new Contacts();
		homePage = loginPage.Login(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test (priority=1)
	public void VerifyHomepageTitleTest(){
		String HomepageTitle = homePage.GetHomePageTitle();
		Assert.assertEquals(HomepageTitle, "Cogmento CRM", "Homepage title doesn't matches");
		
	}
	
	@Test(priority=2)
	public void VerifyUserNameAtTop(){
		testUtil.switchToFrame();
		Assert.assertTrue(homePage.User_text_at_top());
	}
	
	@Test(priority=3)
	public void VerifyContactsLinkClick(){
		contacts = homePage.ClickContactsLink();
		//testUtil.switchToFrame();
		//Assert.assertEquals(contacts.Contact_Info(), "Gyan Prakash");
	}
	
	@AfterMethod
	public void tearDown(){
		driver.close();
		driver.quit();
	}

}
