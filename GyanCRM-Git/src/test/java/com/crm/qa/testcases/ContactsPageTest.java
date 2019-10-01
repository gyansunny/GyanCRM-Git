package com.crm.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.Contacts;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.utils.TestUtils;

public class ContactsPageTest extends TestBase{

	LoginPage loginPage;
	HomePage homePage;
	TestUtils testUtil;
	Contacts contacts;
	
	String sheetName = "Contacts";
	
	public ContactsPageTest(){
		super();
	}
	
	@BeforeMethod
	public void setUp(){
		initialization();
		loginPage = new LoginPage();
		testUtil = new TestUtils();
		contacts = new Contacts();
		homePage = loginPage.Login(prop.getProperty("username"), prop.getProperty("password"));
		homePage.ClickContactsLink();
		}
	
	@Test(priority=1)
	public void VerifyContactsLabelTest(){
		Assert.assertTrue(contacts.Contact_Label(), "Page not found as expected");
	}
	
	@Test(priority=2)
	public void ContactsNewBtnTest(){
		contacts.ContactNewbtn();
		}
	
	@DataProvider
	public Object[][] getCRMTestData(){
		Object data[][] = TestUtils.getTestData(sheetName);
		return data;
	}
	
	@Test(priority=3, dataProvider = "getCRMTestData")
	public void ValidateNewContactsCreation(String FirstName, String LastName){
		contacts.ContactNewbtn();
		//contacts.CreateNewContact("Gyan", "Prakash");
		contacts.CreateNewContact(FirstName, LastName);
		contacts.CreateNewContactSaveBtn();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@AfterMethod
	public void tearDown(){
		driver.close();
		driver.quit();
	}
}
