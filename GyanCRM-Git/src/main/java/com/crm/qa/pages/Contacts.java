package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class Contacts extends TestBase{
	
	@FindBy(xpath="//*[@id='dashboard-toolbar']/div[1]")
	WebElement Contact_Label;
	
	@FindBy(xpath="//*[@id='dashboard-toolbar']/div[2]/div/a/button")
	WebElement Contact_Newbtn;
	
	@FindBy(xpath="//*[@id='ui']/div/div[2]/div[2]/div/div[2]/form/div[1]/div[1]/div/div/input")
	WebElement firstName;
	
	@FindBy(name = "last_name")
	WebElement lastName;
	
	@FindBy(xpath = "//*[@id='dashboard-toolbar']/div[2]/div/button[2]")
	WebElement Savebtn;
	
	public Contacts(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean Contact_Label(){
		return Contact_Label.isDisplayed();
	}
	
	public void ContactNewbtn(){
		Contact_Newbtn.click();
	}
	
	public void CreateNewContact(String ftName, String ltName){
		firstName.sendKeys(ftName);
		lastName.sendKeys(ltName);
		//Savebtn.click();
	}
	
	public void CreateNewContactSaveBtn(){
		Savebtn.click();
	}

}
