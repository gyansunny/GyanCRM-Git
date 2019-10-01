package com.crm.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.crm.qa.base.TestBase;

public class TestUtils extends TestBase{
	
	public static long PAGE_TIMEOUT_IN_SECONDS = 50;
	public static long IMPLICIT_TIMEOUT_IN_SECONDS = 50;
	
	public static String TestData_SheetPath = "C:/DATA/Data/Gyan-Personal/workspace/GyanCRM-Git"
			+ "/src/main/java/com/crm/qa/testdata/CRM_TestData.xlsx";
	public static String Screenshots_Path = "C:/DATA/Data/Gyan-Personal/workspace/GyanCRM-Git/Screenshots";
	
	static Workbook book;
	static org.apache.poi.ss.usermodel.Sheet sheet;
	
	public void switchToFrame(){
		driver.switchTo().frame("downloadFrame");
	}
	
	public static Object[][] getTestData(String sheetName){
		FileInputStream file = null;
		try{
			file = new FileInputStream(TestData_SheetPath);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
		try{
			book = WorkbookFactory.create(file);
		}  catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet = book.getSheet(sheetName);
		Object[][]data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i=0; i<sheet.getLastRowNum(); i++){
			for (int k=0; k<sheet.getRow(0).getLastCellNum(); k++){
				data[i][k] = sheet.getRow(i+1).getCell(k).toString();
			}
		}
		return data;
	}
	
	public static void takeScreenshotAtEndOfTest() throws IOException{
		File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//String CurrDir = System.getProperty("user.dir");
		FileUtils.copyFile(srcfile, new File (Screenshots_Path + "/Screenshot" + System.currentTimeMillis() + ".jpg"));
		}

}
