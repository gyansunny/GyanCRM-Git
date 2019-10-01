package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase{
	
	@FindBy(xpath="//*[@id='top-header-menu']/div[2]/span[1]")
	WebElement User_text_at_top;
	
	@FindBy(xpath="//*[@id='main-nav']/a[3]/span")
	WebElement Contacts_Link;
	
	@FindBy(xpath="//*[@id='main-nav']/a[5]/span")
	WebElement Deals_Link;
	
	@FindBy(xpath="//*[@id='main-nav']/a[6]/span")
	WebElement Tasks_Link;
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}
	
	public String GetHomePageTitle(){
		return driver.getTitle();
	}
	
	public boolean User_text_at_top(){
		return User_text_at_top.isDisplayed();
	}
	
	public Contacts ClickContactsLink(){
		Contacts_Link.click();
		return new Contacts();
	}
	
	public Deals ClickDealsLink(){
		Deals_Link.click();
		return new Deals();
	}
	
	public Tasks ClickTasksLink(){
		Tasks_Link.click();
		return new Tasks();
	}

}
