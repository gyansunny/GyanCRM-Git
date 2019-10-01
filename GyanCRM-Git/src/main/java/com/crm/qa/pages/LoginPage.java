package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase {
	
	//PageFactory - Object Repository
	
	@FindBy(name="email")
	WebElement email;
	
	@FindBy(name="password")
	WebElement password;
	
	@FindBy(xpath="//*[@id='ui']/div/div/form/div/div[3]")
	WebElement Loginbtn;
	
	@FindBy(xpath="//*[@id='ui']/div/div/div[1]/a")
	WebElement ForgotYourPasswordlink;
	
	@FindBy(xpath="//*[@id='ui']/div/div/div[3]/a")
	WebElement SignUplink;
	
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean EmailFieldPresent(){
		return email.isDisplayed();
	}
	
	public boolean PasswordFieldPresent(){
		return password.isDisplayed();
	}
	
	public HomePage Login(String un, String pwd){
		email.sendKeys(un);
		password.sendKeys(pwd);
		Loginbtn.click();
		return new HomePage();
	}
	
	public String LoginPageTitle(){
		return driver.getTitle();
	}

}
