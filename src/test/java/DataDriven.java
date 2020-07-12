import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DataDriven {
	
   /*identify testcases colomns by scanning entire row
	once colomns is identified then scan entire testcase colomn to identify purchase testcase row
	after you grab purchase testcase row=pull all data of that row and feed into test
	*/
	public  ArrayList<String> getData(String TeastcaseName) throws IOException
	{
		ArrayList<String> a=new ArrayList<String>();
		FileInputStream fis=new FileInputStream("C:\\Users\\YUVA\\Documents\\demodata.xlsx");
      XSSFWorkbook workbook=new XSSFWorkbook(fis);
      
     int sheets= workbook.getNumberOfSheets();
     for(int i=0;i<sheets;i++)
     {
    	if(workbook.getSheetName(i).equalsIgnoreCase("testdata"))
    	{
         XSSFSheet sheet=workbook.getSheetAt(i);
         
         //identify testcases colomns by scanning entire row
       Iterator<Row> rows= sheet.iterator();//sheet is collection of rows
           Row firstrow =rows.next();
          Iterator<Cell> ce=firstrow.cellIterator();//row is collection of cells 
          int k=0;
          int colomn = 0;
        while(ce.hasNext())
        {
        	Cell value=ce.next();
        	if(value.getStringCellValue().equalsIgnoreCase("Testcases"))
        	{
        		colomn=k;
        	}
        	k++;
        }
    	System.out.println(colomn);
    	
    	//once colomns is identified then scan entire testcase colomn to identify purchase testcase row 
while(rows.hasNext())
{
	Row r=rows.next();
	if(r.getCell(colomn).getStringCellValue().equalsIgnoreCase(TeastcaseName))
	{
		//	after you grab purchase testcase row=pull all data of that row and feed into test
       Iterator<Cell>cv= r.cellIterator();
       while(cv.hasNext())
       {
    	   Cell c=cv.next();
    	   if(c.getCellTypeEnum()==CellType.STRING)
    	   {
    		   
    	  a.add(c .getStringCellValue());
    	   }
    	   else {
    		  a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
    		   
    	   }
       }
	}
	
}
    	

    	}
     }
     return a;
	}
	public static void main(String[] args) throws IOException, AWTException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		WebDriver driver =new ChromeDriver();
		//WebDriverWait w=new WebDriverWait(driver, 30);
		driver.get("https://www.demoblaze.com/");
		DataDriven dd=new DataDriven();
ArrayList data=dd.getData("Add profile");
driver.findElement(By.xpath("//a[@id='login2']")).click();

driver.findElement(By.id("loginusername")).sendKeys( data.get(1).toString());
//System.out.println(data.get(0));
System.out.println(data.get(1));
driver.findElement(By.id("loginpassword")).sendKeys(data.get(2).toString());
driver.findElement(By.xpath("//button[@onclick='logIn()']")).click();
JavascriptExecutor jse = (JavascriptExecutor) driver;
jse.executeScript("window.alert = function() {};"); 
jse.executeScript("window.confirm = function() {};");
driver.findElement(By.xpath("//div[@id='logInModal']//span[contains(text(),'×')]")).click();
System.out.println(data.get(2));
System.out.println(data.get(3));



driver.findElement(By.xpath("//a[@id='login2']")).click();

driver.findElement(By.id("loginusername")).sendKeys( data.get(1).toString());
//System.out.println(data.get(0));
System.out.println(data.get(1));
driver.findElement(By.id("loginpassword")).sendKeys(data.get(2).toString());
driver.findElement(By.xpath("//button[@onclick='logIn()']")).click();
JavascriptExecutor jse1 = (JavascriptExecutor) driver;
jse.executeScript("window.alert = function() {};"); 
jse.executeScript("window.confirm = function() {};");
driver.findElement(By.xpath("//div[@id='logInModal']//span[contains(text(),'×')]")).click();
System.out.println(data.get(2));
System.out.println(data.get(3));
	}

}
