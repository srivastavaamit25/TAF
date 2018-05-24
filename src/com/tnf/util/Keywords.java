package com.tnf.util;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;
import com.tnf.testreports.ReportGenerator;


//Parent class or Super Class

public class Keywords {


	//public static Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\src\\com\\tnf\\xls\\Data.xlsx");
	//public static Xls_Reader xls = new Xls_Reader("C:\\TAF_Automation\\xls\\Data.xlsx");
	public static Xls_Reader xls = new Xls_Reader("C:\\Selenium\\Framework\\FW\\TAF\\src\\com\\tnf\\xls\\Keywords.xls");
	static Keywords keywords=null;
	public Properties CONFIG=null;
	public Properties OR=null;
	public WebDriver driver= null;
	String StepName = null;
	String prospective = null;
	String Description = null;
	String errorUrl = null;
	public void executeKeywords(String testName, Map<String,String> data, List<List<String>> rowList, StringBuffer tr_testData) throws InterruptedException, AWTException, FileNotFoundException, StaleElementReferenceException
	{
		boolean resultFlag = true;
		System.out.println("Executing - "+testName);
		// find the keywords for the test
		String keyword=null;
		String objectKey=null;
		String dataColVal=null;
		String TestSiteurl=null;
		

		System.out.println("TestName :" + testName);
		int i = 0;
		for(int rNum=2;rNum<=xls.getRowCount("Test Steps");rNum++){

			String testStepsData = xls.getCellData("Test Steps", "TCID", rNum);
			if(testStepsData.contains(testName)&&xls.getCellData("Test Steps", "RunMode", rNum).equalsIgnoreCase("Y")){

				boolean unhandledAlertFlag = false;
				List<String> rowData = new ArrayList<String>();
				keyword=xls.getCellData("Test Steps", "Keyword", rNum);
				objectKey=xls.getCellData("Test Steps", "Object", rNum);
				dataColVal=xls.getCellData("Test Steps", "Data", rNum);
				TestSiteurl=xls.getCellData("Test Steps", "TestURL", rNum);
				StepName = xls.getCellData("Test Steps", "TCID", rNum);
				prospective = xls.getCellData("config", "Prospective",2);
				Description =xls.getCellData("Test Steps", "Desciption", rNum);

				String result=null;

				System.out.println("_________________________________________________________");
				System.out.println("Keyword : " + keyword);

				try {	 
					if(keyword.equals("openBrowser_navigate"))
						result=openBrowser_navigate(dataColVal);

					else if (keyword.equals("BrowserAuthenticationCredentials"))
						result=BrowserAuthenticationCredentials(data.get(dataColVal));
					else if (keyword.equals("BrowserAuthenticationLogin"))
						result=BrowserAuthenticationLogin();
					else if (keyword.equals("openBrowser_URL"))
						result=openBrowser_URL("",dataColVal,TestSiteurl);
					else if (keyword.equals("clickLink")){
						try { result=clickLink(objectKey,data.get(dataColVal));} 
						catch (Exception e) {result=clickLink(objectKey,data.get(dataColVal));}}
					else if (keyword.equals("RadioButton"))
						result=RadioButton(objectKey);
					else if (keyword.equals("clickButton"))
						result=clickButton(objectKey);
					else if(keyword.equals("input"))
						result=input(objectKey,data.get(dataColVal));
					else if(keyword.equals("verifyImage"))
						result=verifyImage(objectKey,data.get(dataColVal));
					else if(keyword.equals("verifyText"))
						result=verifyText(objectKey,data.get(dataColVal));
					else if(keyword.equals("select"))
						result=select(objectKey,data.get(dataColVal));
					else if (keyword.equals("CheckBox"))
						result=CheckBox(objectKey);
					else if (keyword.equals("WebGrid"))
						result=WebGrid(objectKey);
					else if (keyword.equals("verifyBreadCrumb"))
						result=verifyBreadCrumb(objectKey);
					else if (keyword.equals("Sync"))
						result=waitThread();
					else if (keyword.equals("clickLink_text"))
						result=clickLink_text(objectKey,data.get(dataColVal));
					else if (keyword.equals("verifyObject"))
						result=verifyObject(objectKey);
					else if (keyword.equals("popup"))
						result=popup();
					else if (keyword.equals("deletcookies"))
						result=deletcookies();
					else if (keyword.equals("closeBrowser"))
						result=closeBrowser();
					else if (keyword.equals("Robot_click"))
						result=Robot_click();
					else if (keyword.equals("excelinput"))
						result=excelinput("","",1,data.get(dataColVal));
					else if (keyword.equals("file_upload"))
						result=file_upload(data.get(dataColVal));
					else if (keyword.equals("openBrowser_AutoSignIn"))
						result=openBrowser_AutoSignIn("",dataColVal);
					else if (keyword.equals("ctrlSelect"))
						result=ctrlSelect(objectKey,data.get(dataColVal));
					else if (keyword.equals("switchTab"))
						result=switchTab(data.get(dataColVal));
					else if (keyword.equals("MouseHover"))
						result=MouseHover(objectKey,data.get(dataColVal));
					else if (keyword.equals("HandleAlert"))
						result=HandleAlert(data.get(dataColVal));
					else if (keyword.equals("linkDisabled"))
						result=linkDisabled(objectKey,data.get(dataColVal));
					else if (keyword.equals("linkEnabled"))
						result=linkEnabled(objectKey,data.get(dataColVal));
					else if(keyword.equals("refresh_page"))
						result=refresh_page();
					else if (keyword.equals("closeTab"))
						result=closeTab();
					else if (keyword.equals("AcceptAlert"))
						result=AcceptAlert();
					else if (keyword.equals("Remote_Login"))
						result=AcceptAlert();

				}catch(NoSuchElementException nsee) {
					//nsee.printStackTrace();
					result = "Fail";
				} catch (UnhandledAlertException uhae) {
					//throw new Exception(uhae.getMessage());
					//throw uhae;
					Alert alert = driver.switchTo().alert();
					alert.accept();
					result = "Fail";
					unhandledAlertFlag = true;
				}catch (NullPointerException e) {
					System.out.println("No such Keyword or Empty field");
				}catch(Exception e){
					result = "Fail";
				}

				try {
					System.out.println(result);
					rowData.add(xls.getCellData("Test Steps", "TCID", rNum));//Edited for HTML report 24/09
					rowData.add(xls.getCellData("Test Steps", "Desciption", rNum));//Edited for HTML report 24/09
					rowData.add(xls.getCellData("Test Steps", "ExpectedResult", rNum));
					rowData.add(result.equals("Pass")?"PASS":"FAIL");//Edited for HTML report 24/09
					if (result.equals("Fail")) {
						errorUrl = driver.getCurrentUrl();
						//System.out.println(errorUrl);
						rowData.add("<details><summary><b><FONT color=#8d110e face=#009900 size=3>Error Details </FONT></B></summary>RowNo. <b>"+rNum+"</b><br> Keyword: <b>"+keyword+"</b></br>Object:<b><font color='red'> "+objectKey+"</font></b><br><a href="+errorUrl+" target='_blank'>Error Page!</a></font></b></details>");
					}else {rowData.add("");}


				}catch(NoSuchElementException nsee) {
					//nsee.printStackTrace();
					result = "Fail";
				} catch (UnhandledAlertException uhae) {
					//throw new Exception(uhae.getMessage());
					//throw uhae;
					Alert alert = driver.switchTo().alert();
					alert.accept();
					result = "Fail";
					unhandledAlertFlag = true;
				}catch (NullPointerException e) {
					System.out.println("No such Keyword or Empty field");
				}catch(Exception e){
					result = "Fail";
				}

				if(!resultFlag || !result.equalsIgnoreCase("Pass")) {//Edited for HTML report 24/09
					resultFlag = false;//Edited for HTML report 24/09
				}//Edited for HTML report 24/09

				rowList.add(rowData);//Edited for HTML report 24/09

				if((result.equals("Fail")&&(prospective.equals("Debug")))){

					try {
						
						//Thread.sleep(3000);
						System.out.println();
						System.out.println("          *******|Debug Prospective|*******");
						System.out.println("--------------------------------------");
						System.out.println("@TCID :> "+StepName);
						System.out.println("--------------------------------------");
						System.out.println("@Step :> "+Description);
						System.out.println("---------------------------------------");
						File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						String fileName=testName+ "_" + i +".jpg";
						FileUtils.copyFile(scrFile, new File(ReportGenerator.reportGeneratePath + "\\screenshots\\"+fileName));
						System.out.println("@ScreenShot :> "+fileName);
						System.out.println("-------------------------------------");
						System.out.println("*****************************");
						Runtime.getRuntime().exec("C://softwares//Prospective//pros.exe");
						Thread.sleep(10000);
					} catch (IOException e) {
						System.out.println("***ERR***");
						e.printStackTrace();
					} catch(UnhandledAlertException uhae) {
						Alert alert = driver.switchTo().alert();
						alert.accept();
						System.out.println("==== Exception is handled ====");
						throw uhae;
					} catch(Exception e) {

					} if(unhandledAlertFlag) {
						throw new UnhandledAlertException("");
					}
					
				}else if((result.equals("Fail")&&(prospective.equals("TerminateUponFail")))){
					
					try {
						File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						String fileName=testName + "_" + i +".jpg";
						FileUtils.copyFile(scrFile, new File(ReportGenerator.reportGeneratePath + "\\screenshots\\"+fileName));
						System.out.println("Screen shot taken with name '"+fileName+"'");
						System.out.println("Breking loop");
						
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						driver.quit();
					}
				
				}else if(!result.equals("Pass")){
					System.out.println("####### Failed at Test Step "+StepName+"");
					// screenshot
					try {
						File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						String fileName=testName + "_" + i +".jpg";
						//FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+fileName));
						FileUtils.copyFile(scrFile, new File(ReportGenerator.reportGeneratePath + "\\screenshots\\"+fileName));
						System.out.println("Screen shot taken with name '"+fileName+"'");
					} catch (IOException e) {
						System.out.println("***ERR***");
						e.printStackTrace();
					} catch(UnhandledAlertException uhae) {
						//uhae.printStackTrace();
						Alert alert = driver.switchTo().alert();
						alert.accept();
						System.out.println("==== Exception is handled ====");
						throw uhae;
					} catch(Exception e) {

					} if(unhandledAlertFlag) {
						throw new UnhandledAlertException("");
					}
				}
				i++;
			}
		}


		if(resultFlag) {//Edited for HTML report 24/09
			tr_testData.append("PASS");//Edited for HTML report 24/09
		} else {//Edited for HTML report 24/09
			tr_testData.append("FAIL");//Edited for HTML report 24/09
		}//Edited for HTML report 24/09
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	

	public String BrowserAuthenticationCredentials(String data) throws InterruptedException{

		System.out.println("Executing Test Step '"+StepName+"'");
		String result = "Fail";

		Thread.sleep(10000);

		try {

			System.out.println(data);

			StringSelection abc=new StringSelection(data);

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(abc, null);

			Thread.sleep(3000);

			Robot r = new Robot(); 
			r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V); 
			r.keyRelease(KeyEvent.VK_CONTROL); 
			Thread.sleep(2000);
			r.keyPress(KeyEvent.VK_TAB); 
			r.keyRelease(KeyEvent.VK_TAB); 
			result = "Pass";
			//****************

			Thread.sleep(3000);
			result = "Pass";

			//Runtime.getRuntime().exec("C://softwares//AuthenticationUser//Authentication.exe");
			result = "Pass";
		} catch (Exception e) {
			result = "Fail";
		}

		return result;

	}
	public String BrowserAuthenticationLogin() throws InterruptedException{

		System.out.println("Executing Test Step '"+StepName+"'");
		String result = "Fail";

		Thread.sleep(10000);

		try {
			Thread.sleep(3000);
			Robot r = new Robot(); 
			r.keyPress(KeyEvent.VK_ENTER); 
			r.keyRelease(KeyEvent.VK_ENTER); 
			Thread.sleep(3000);
			//Runtime.getRuntime().exec("C://softwares//AuthenticationUser//Authentication.exe");
			Set<String> windows1 = driver.getWindowHandles();
			Iterator<String> window1 = windows1.iterator();
			driver.switchTo().window( window1.next() ); 	
			result = "Pass";
		} catch (Exception e) {
			result = "Fail";
		}
		System.out.println(result);
		return result;
	}

	public String closeBrowser()
	{
		String result = "Fail";
		try
		{
			driver.quit();	
			result = "Pass";
			System.out.println("");
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			driver.quit();
			return "Fail";
		}
		catch(Exception e)
		{
			return "Fail";
		}

		return result;
	}

	public String excelinput(String sheetname,String colname,int rownum,String data)
	{
		String result = "Fail";
		try
		{
			xls.setCellData(sheetname, colname,rownum,data);
			result = "Pass";
		}
		catch(Exception e)
		{
			return "Fail";
		}

		return result;
	}

	public String openBrowser_navigate(String browserType){

		System.out.println("Executing Test Step '"+StepName+"'");
		String result = "Fail";
		try
		{

			String Browertype = xls.getCellData("config", "Browertype", 2);
			//Condition to pick browserType
			if(Browertype.equals("Firefox")){
				driver = new FirefoxDriver();
				result = "Pass";
			}else if(Browertype.equals("IE")){
				System.setProperty("webdriver.ie.driver", "C:\\softwares\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				result = "Pass";
			}else if(Browertype.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver.exe");
				driver = new ChromeDriver();
				result = "Pass";
			}else if(Browertype.equals("Safari")){
				//System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver.exe");
				driver = new SafariDriver();
				result = "Pass";
			}

			String URL = xls.getCellData("config", "URL", 2);
			String Test = xls.getCellData("config", "Test", 2);
			String Live = xls.getCellData("config", "Live", 2);
			String QA = xls.getCellData("config", "QA", 2);
			String Window = xls.getCellData("config", "WindowSize", 2);



			if(Window.equals("Maximise")){
				driver.manage().window().maximize();
				System.out.println("Window Maximised");
			}
			else if(Window.equals("Right to Screen")){
				driver.manage().window().setPosition(new Point(955,0));
				driver.manage().window().setSize(new Dimension(965,1037));
				System.out.println("Window is set to Right of the Screen");
			}
			else if(Window.equals("Left to Screen")){
				driver.manage().window().setPosition(new Point(0,0));
				driver.manage().window().setSize(new Dimension(965,1037));
				System.out.println("Window is set to Left of the Screen");
			}
			else {
				driver.manage().window().maximize();
				System.out.println("Please select Window Position from 'Config' File");
			}				


			if(URL.equals("QA")){
				driver.get(QA);
				result = "Pass";
			}else if(URL.equals("Test")){
				driver.get(Test);
				result = "Pass";
			}else if(URL.equals("Live")){
				driver.get(Live);
				result = "Pass";
			}
		}
		catch(Exception e)
		{
			return "Fail";
		}

		return result;

	}

	public String openBrowser_URL(String browserType,String  data,String TestUrl){

		System.out.println("Executing Test Step '"+StepName+"'");
		String result = "Fail";
		try
		{

			String Browertype = xls.getCellData("config", "Browertype", 2);
			String URL = xls.getCellData("config", "URL", 2);


			result = "Pass";
			//Condition to pick browserType
			if(Browertype.equals("Firefox")){
				driver = new FirefoxDriver();
				result = "Pass";
			}else if(Browertype.equals("IE")){
				System.setProperty("webdriver.ie.driver", "C:\\softwares\\IEDriverServer.exe");
				result = "Pass";
			}else if(Browertype.equals("Chrome")){
				System.setProperty("webdriver.chrome.driver", "C:\\softwares\\chromedriver.exe");
				driver = new ChromeDriver();
				result = "Pass";
			}

			if (URL.equals("QA")) {
				driver.get(data);
			}else if (URL.equals("Test")) {
				driver.get(TestUrl);
			}


			String Window = (xls.getCellData("config","WindowSize", 2));

			if(Window.equals("Maximise")){
				driver.manage().window().maximize();
				System.out.println("Window Maximised");
			}
			else if(Window.equals("Right to Screen")){
				driver.manage().window().setPosition(new Point(955,0));
				driver.manage().window().setSize(new Dimension(965,1037));
				System.out.println("Window is set to Right of the Screen");
			}
			else if(Window.equals("Left to Screen")){
				driver.manage().window().setPosition(new Point(0,0));
				driver.manage().window().setSize(new Dimension(965,1037));
				System.out.println("Window is set to Left of the Screen");
			}
			else {
				driver.manage().window().maximize();
				System.out.println("Please select Window Position from 'Config' File");
			}
		}
		catch(Exception e)
		{
			return "Fail";
		}


		return result;

	}

	public String ctrlSelect(String xpathKey,String data) {
		String result = "Fail";

		System.out.println("Executing click");
		try{
			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				driver.findElement(By.xpath("(//a[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]")).click();
				result = "Pass";
			}
			else if((xpathKey.contains("text"))&&((xpathKey.contains(">"))))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
				result = "Pass";
			}
			else if(xpathKey.contains("id")&&(data!=null))
			{
				driver.findElement(By.xpath("//*[@id='"+data+"']")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("id"))
			{
				driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("name"))
			{
				driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("value"))
			{
				driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']")).click();
				result = "Pass";
			}

			else if(xpathKey.contains("class"))
			{
				driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).click();
				result = "Pass";
			}

			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("//"))
			{
				driver.findElement(By.xpath(xpathKey)).click();
				result = "Pass";
			}
			else if(xpathKey.contains("link"))
			{
				driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']")).click();
				result = "Pass";
			}
		}catch(NoSuchElementException e){
			return "Fail";
		}
		catch(Exception e){
			return "Fail";
		}
		return result;	
	}

	public String openBrowser_AutoSignIn(String browserType,String  data)
	{
		String result = "Fail";
		System.out.println("Executing AutoSignIn");
		try
		{
			driver.get(data);
			driver.manage().window().maximize();
			result = "Pass";
		}
		catch(NoSuchElementException e)
		{
			return "Fail";
		}

		return result;
	}

	public String refresh_page(){
		Popup_TFO();Popup_CRC();
		String result = "Fail";
		try{
			System.out.println("Executing refresh Page");

			//Actions actionObject = new Actions(driver);
			//actionObject.keyDown(Keys.CONTROL).sendKeys(Keys.F5).perform();
			((JavascriptExecutor)driver).executeScript("document.location.reload()");

			result = "Pass";

		}catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}
		catch(NoSuchElementException e)
		{
			return "Fail";
		}

		if(result=="Pass"){
			System.out.println("");
			//return result;
		}else if(result=="Fail"){
			System.out.println("");
			//return result;
		}

		return result;
	}
	public String navigate(String URLKey)
	{
		String result = "Fail";
		System.out.println("Executing navigate");
		try{
			driver.get(CONFIG.getProperty(URLKey));
			result = "Pass";
		}catch(NoSuchElementException e){
			return "Fail";
		}
		return result;
	}

	public String Robot_click() throws AWTException, InterruptedException
	{
		String result = "Fail";
		try
		{
			Robot robot = new Robot();//Comment RobotClass When Running on Chrome
			robot.delay(500);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			/*driver.switchTo().defaultContent();
        Thread.sleep(2000);*/
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(3000);
			result = "Pass";
		}catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch(Exception e)
		{
			return "Fail";	
		}
		return result;

	}

	public String clickLink(String xpathKey,String data)
	{

		Popup_TFO();Popup_CRC();
		WebElement element = null;
		JavascriptLibrary jsLib = new JavascriptLibrary(); 
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing click at '"+xpathKey+"'");
		try{
			String[] xpathKeyx1 =  xpathKey.split(">");

			//*********KeyWord Contains ':'**********//
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKeyx2[0]+"')])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@id='"+xpathKeyx2[0]+"'])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@name='"+xpathKeyx2[0]+"'])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@class='"+xpathKeyx2[0]+"'])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@link='"+xpathKeyx2[0]+"'])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@title='"+xpathKeyx2[0]+"'])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.contains("href"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKeyx2 =  xpathKeyx1[1].split(":");

				element = driver.findElement(By.xpath("(//*[contains(@href,'"+xpathKeyx2[0]+"')])["+xpathKeyx2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}			
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}				//String x = id>"xyz"--> x1=id & x2=xyx
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{														//xyz
				element = driver.findElement(By.xpath("//*[@id='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");

				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@class='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKeyx1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@link='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("href"))&&((xpathKey.contains(">"))))
			{
				driver.findElement(By.xpath("//*[contains(href='"+xpathKeyx1[1]+"')]")).click();
				result = "Pass";
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@title='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("alt"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@alt='"+xpathKeyx1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			//********************END*******************
			//***********Keyword Contains 'Data'*****************
			else if(xpathKey.equals("id")&&(data!=null))
			{
				element = driver.findElement(By.xpath("//*[@id='"+data+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if(xpathKey.equals("text")&&(data!=null))
			{
				element = driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");			
				}
				result = "Pass";
			}
			Thread.sleep(2000);
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";
		}catch(Exception e){
			System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";

		}
		/*if(result=="Pass"){
			System.out.println("Clicked on Link '["+xpathKeyx+"]'");*/	
		//return result;}

		return result;	
	}

	public String clickLink_text(String xpathKey,String data){
		Popup_TFO();Popup_CRC();
		String result = "Fail";
		System.out.println("Executing click at '"+xpathKey+"'");
		try{
			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				driver.findElement(By.xpath("(//a[contains(text(),'"+data+"')])["+xpathKey2[1]+"]")).click();
				result = "Pass";
			}
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//a[contains(text(),'"+data+"')]")).click();
				result = "Pass";
			}			
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			return "Fail";

		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch(Exception e){
			return "Fail";

		}
		return result;	
	}
	public String clickButton(String xpathKey){
		Popup_TFO();
		//Popup_CRC();


		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor)driver;
		JavascriptLibrary jsLib = new JavascriptLibrary(); 
		String result = "Fail";
		System.out.println("Executing clickButton at '"+xpathKey+"'");
		try{
			String[] xpathKey1 =  xpathKey.split(">");

			//*********KeyWord Contains ':'**********//
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@class='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@link='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@title='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.contains("href"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[contains(@href,'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}			
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{

				element = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}

			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}

			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("href"))&&((xpathKey.contains(">"))))
			{
				driver.findElement(By.xpath("//*[contains(href='"+xpathKey1[1]+"')]")).click();
				result = "Pass";
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@title='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("alt"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			else if((xpathKey.startsWith("value"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");				
					result = "Pass";	
				}
			}
			//********************END*******************
			//***********Keyword Contains 'Data'*****************

			Thread.sleep(2000);
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Unable to click Button '["+xpathKey+"]'");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to click Button '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			System.out.println("Unable to click Button '["+xpathKey+"]'");
			return "Fail";
		}
		catch(Exception e){
			System.out.println("Unable to click Button '["+xpathKey+"]'");
			return "Fail";

		}
		/*if(result=="Pass"){
			System.out.println("Clicked on Link '["+xpathKey+"]'");
			//return result;
			}*/
		return result;	
	}




	public String linkDisabled(String xpathKey,String data)
	{
		Popup_TFO();Popup_CRC();
		WebElement textlink = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing click at '"+xpathKey+"'");
		try{
			String[] xpathKey1 =  xpathKey.split(">");

			//*********KeyWord Contains ':'**********//
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				textlink = driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@name ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@class ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@link ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@title ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}		
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				textlink = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@title='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("alt"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			//********************END*******************
			//***********Keyword Contains 'Data'*****************
			else if(xpathKey.equals("id")&&(data!=null))
			{
				textlink =driver.findElement(By.xpath("//*[@id='"+data+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if(xpathKey.equals("text"))
			{
				textlink =driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;color: white");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Fail";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}
			}
			Thread.sleep(2000);
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch(StaleElementReferenceException SES){
			//	System.out.println("Unable to click Link '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}
		try{
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "");
		}catch(Exception e){

		}
		return result;	
	}
	public String linkEnabled(String xpathKey,String data){

		Popup_TFO();Popup_CRC();
		WebElement textlink = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing click at '"+xpathKey+"'");
		try{
			String[] xpathKey1 =  xpathKey.split(">");

			//*********KeyWord Contains ':'**********//
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@name ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@class ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}
			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@link ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				textlink = driver.findElement(By.xpath("(//*[@title ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}		
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				textlink = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@title='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if((xpathKey.startsWith("alt"))&&((xpathKey.contains(">"))))
			{
				textlink = driver.findElement(By.xpath("//*[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			//********************END*******************
			//***********Keyword Contains 'Data'*****************
			else if(xpathKey.equals("id")&&(data!=null))
			{
				textlink =driver.findElement(By.xpath("//*[@id='"+data+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}

			}
			else if(xpathKey.equals("text"))
			{
				textlink =driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "background: purple; border: 4px  red; color white;");
				String isDisabled = textlink.getAttribute("class");
				if (isDisabled==null || !isDisabled.equals("link_disabled")){
					System.out.println("View link: Enabled");
					result = "Pass";
				}else{
					System.out.println("View link: Disabled");
					result = "Fail";
				}
			}
			Thread.sleep(2000);
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch(NoSuchElementException e){
			return "Fail";

		}
		catch(Exception e){
			return "Fail";

		}

		try{
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", textlink, "");
		}catch(Exception e){

		}
		return result;	

	}
	public String input(String xpathKey,String data) throws InterruptedException{


		Popup_TFO();Popup_CRC();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing input at '"+xpathKey+"'");
		try{//Contains : parsing

			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{

				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@class='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			//******************************contains >******************************//
			else if((xpathKey.contains("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);	
				result = "Pass";
			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{

				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			else if((xpathKey.contains("id"))&&((xpathKey.contains(">"))))
			{														//xyz
				element = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));//id>xyz
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			else if((xpathKey.contains("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@class ='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				element.clear();
				element.sendKeys(data);
				result = "Pass";
			}
			Thread.sleep(3000);
		}catch(NoSuchElementException e){
			System.out.println("Cannot input '"+data+"' in to Field '["+xpathKey+"]'");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Cannot input '"+data+"' in to Field '["+xpathKey+"]'");
			return "Fail";
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Cannot input '"+data+"' in to Field '["+xpathKey+"]'");
			return "Fail";
		}catch(Exception e){
			System.out.println("Cannot input '"+data+"' in to Field '["+xpathKey+"]'");
			return "Fail";

		}

		/*if(result=="Pass"){
			System.out.println("input '"+data+"' in to Field '["+xpathKey+"]'");
			//return result;
		}else if(result=="Fail"){
			System.out.println("Cannot input '"+data+"' in to Field '["+xpathKey+"]'");
			//return result;
		}*/

		return result;			
	}


	public  String CheckBox(String xpathKey)
	{
		Popup_TFO();Popup_CRC();
		JavascriptLibrary jsLib = new JavascriptLibrary(); 
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing clickBox at '"+xpathKey+"'");
		try{
			String[] xpathKey1 =  xpathKey.split(">");

			//*********KeyWord Contains ':'**********//
			if((xpathKey.startsWith("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{

				String[] xpathKey2 =  xpathKey1[1].split(":");

				element =  driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element =  driver.findElement(By.xpath("(//*[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@class='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("type"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element =  driver.findElement(By.xpath("(//*[@type ='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("value"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@value='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name ='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{

				element = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}

			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("type"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@type='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("value"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			//********************END*******************
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Unable to Check the Checkbox at '["+xpathKey+"]'");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to Check the Checkbox at '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			System.out.println("Unable to Check the Checkbox at '["+xpathKey+"]'");
			return "Fail";
		}catch(Exception e){
			System.out.println("Unable to Check the Checkbox at '["+xpathKey+"]'");
			return "Fail";
		}

		return result;	
	}

	public  String RadioButton(String xpathKey)
	{
		Popup_TFO();Popup_CRC();
		JavascriptLibrary jsLib = new JavascriptLibrary(); 
		System.out.println("Executing RadioButton at '"+xpathKey+"'");
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		String[] xpathKey1 =  xpathKey.split(">");
		try{//Contains split :
			if((xpathKey.startsWith("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue;color: black");
				element.click();
				result = "Pass";
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element =driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element =driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("value"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element =driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			//Contains > Parse
			else if((xpathKey.contains("value"))&&((xpathKey.contains(">"))))
			{
				element =driver.findElement(By.xpath("//*[@value='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if(xpathKey.startsWith("//"))
			{
				element =driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				element =driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				element =driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				element =driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  red;color: black");
				try {
					element.click();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"click", "0,0");	
					result = "Pass";
				}
			}
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Unable to Check the Radio Button at '["+xpathKey+"]'");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to Check the Radio Button at '["+xpathKey+"]'");
			return "Fail";
		}catch(NoSuchElementException e){
			System.out.println("Unable to Check the Radio Button at '["+xpathKey+"]'");
			return "Fail";
		}
		catch(Exception e){
			System.out.println("Unable to Check the Radio Button at '["+xpathKey+"]'");
			return "Fail";
		}


		/*if(result=="Pass"){
			System.out.println("Clicked the Radio Buttton at '["+xpathKey+"]'");
			//return result;
		}else if(result=="Fail"){
			System.out.println("Unable to Check the Radio Button at '["+xpathKey+"]'");
			//return result;
		}*/

		return result;	
	}



	public  String WebGrid(String xpathKey)
	{
		String result = "Fail";
		try {
			String[] xpathKey1 =  xpathKey.split(">");

			if(xpathKey.contains("class"))
			{
				if(driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']")).isDisplayed())
				{

				}
				result = "Pass";
			}
			else if(xpathKey.contains("id"))
			{
				if(driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']")).isDisplayed())
				{

				}
			}
			result = "Pass";
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch (NoSuchElementException e) {
			return "Fail";
		}
		catch(Exception e){
			return "Fail";

		}

		return result;	
	}

	//*************************************Verify Text old code*************************************
	/*

	String[] xpathKey1 =  xpathKey.split(">");
	if(xpathKey.contains(">"))
	{
		String appnText=driver.findElement(By.xpath(xpathKey1[0])).getText();
		if(data!=null)
		{
			if(appnText.contains(data))
			{

			}	
		}
		else
		{
			if(appnText.contains(xpathKey1[1]))
			{

			}	
		}
		result = "Pass";
	}

	else if(driver.findElement(By.xpath(xpathKey)).isDisplayed())
	{
		result = "Pass";
	}

	else if(driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]")).isDisplayed())
	{

	}

	 */


	/*
	WebElement element = driver.findElement(By.xpath(xpathKey));
	String txt = element.getAttribute("textContent");
	System.out.println(txt);
	String title1 = "Add to cart";
	System.out.println(title1);
	if(txt.contains(title1)){
		System.out.println("Pass");
	}else{
		System.out.println("Fail");
		}
	 */


	//*************************************Verify Text old code Ends*************************************	
	//modified on 18/06/2014 (Sai Rajput)
	public String verifyText(String xpathKey,String data)
	{
		Popup_TFO();Popup_CRC();
		WebElement element = null;
		JavascriptExecutor js =  (JavascriptExecutor) driver;

		String result = "Fail";
		System.out.println("Executing verifyText at '"+xpathKey+"'");
		try {
			String[] xpathKey1 =  xpathKey.split(">");
			//js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
			//*********KeyWord Contains ':'**********//

			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/
			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();
				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				element = driver.findElement(By.xpath("(//*[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@class='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@link='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");

				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(":")))) 
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				element = driver.findElement(By.xpath("(//*[@title='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();
				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			//************END*****************//
			//*********KeyWord Contains ">" **********//

			else if(xpathKey.startsWith("//"))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/		

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();
				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("link"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			/*___________________________________________________________________________________________________*/

			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@title='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  skyblue; border: 2px  purple;color: black");
				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();
				if(txt.contains(data)||txt1.contains(data)||txt2.contains(data))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}else if(data.contains(txt)||data.contains(txt1)||data.contains(txt2))
				{
					System.out.println("Text '"+data+"' Present");
					result = "Pass";
				}
				else
				{
					System.out.println("Text '"+data+"' NOT Present");
					result = "Fail";
				}
			}
			//********************END*******************
			Thread.sleep(2000);
		} catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Text '"+data+"' NOT Present");
			return "Fail";
		}catch (NoSuchElementException e) {
			System.out.println("Text '"+data+"' NOT Present");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Text '"+data+"' NOT Present");
			return "Fail";
		}catch(Exception e){
			System.out.println("Text '"+data+"' NOT Present");
			return "Fail";

		}

		try{
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}catch(Exception e){

		}
		return result;		

	}

	public String verifyContent(String xpathKey,String data)
	{
		String result = "Fail";
		try {


			if(driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]")).isDisplayed())
			{
				result = "Pass";
			}

		} catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}
		catch (NoSuchElementException e) {
			return "Fail";
		}
		catch(Exception e){
			return "Fail";

		}
		System.out.println("Executing verifyText");

		return result;					
	}
	/*	
   public String verifyImage(String xpathKey){

         try {

        	if(driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).isDisplayed())
        	{

        	}
              } catch (NoSuchElementException e) {
        	  //return "Fail - can't see element-"+xpathKey;
            	  return "Fail";
          }
		System.out.println("Executing verifyText");

		return "Pass";					
	}

	 *
	 *
	 *
	 *OLD Code
		    	if(xpathKey.contains("alt"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@alt='"+xpathKey+"']")).isDisplayed())
				   	{

				   	}
		    	}
		    	else if(xpathKey.contains("id"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@id='"+xpathKey+"']")).isDisplayed())
				   	{

				   	}	
		    	}
		    	else if(xpathKey.contains("class"))
		    	{
		    		if(driver.findElement(By.xpath("//*[@class='"+xpathKey+"']")).isDisplayed())
				   	{

				   	}	
		    	}



	 *
	 *
	 *
	 *
	 *
	 *
	 */
	//modified on 17/06/2014 (Sai Rajput)
	public String verifyImage(String xpathKey,String data){

		Popup_TFO();Popup_CRC();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String result = "Fail";
		System.out.println("Executing Verify Image at '"+xpathKey+"'");

		try {
			String[] xpathKey1 =  xpathKey.split(">");
			/*__________________________________________________________________________________________________________________*/		

			if(((xpathKey.contains("//")))&&(data.contains(".")))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				//String src = element.getAttribute("src");
				String Imgsrc = ((JavascriptExecutor)driver).executeScript("return arguments[0].attributes['src'].value;", element).toString();
				if(Imgsrc.contains(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/	

			else if((xpathKey.contains("alt"))&&((data.contains("."))))
			{
				element = driver.findElement(By.xpath("//img[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				//String src = element.getAttribute("src");
				String Imgsrc = ((JavascriptExecutor)driver).executeScript("return arguments[0].attributes['src'].value;", element).toString();
				if(Imgsrc.contains(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/		

			else if((xpathKey.contains("img"))&&((data.contains("."))))
			{
				element = driver.findElement(By.xpath("//img[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = ((JavascriptExecutor)driver).executeScript("return arguments[0].attributes['src'].value;", element).toString();
				//String Imgsrc = element.getAttribute("src");
				if(Imgsrc.contains(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/	


			else if((xpathKey.contains("id"))&&((data.contains("."))))
			{
				element = driver.findElement(By.xpath("//img[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = ((JavascriptExecutor)driver).executeScript("return arguments[0].attributes['src'].value;", element).toString();
				if(Imgsrc.contains(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/		

			else if((xpathKey.contains("class"))&&((data.contains("."))))
			{
				element = driver.findElement(By.xpath("//img[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = ((JavascriptExecutor)driver).executeScript("return arguments[0].attributes['src'].value;", element).toString();
				if(Imgsrc.contains(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}

			//*************************Contains '>'******************************************************

			else if((xpathKey.contains("//")))
			{
				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = element.getAttribute("alt");
				if(Imgsrc.equals(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/	

			else if((xpathKey.contains("alt"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//img[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = element.getAttribute("alt");
				if(Imgsrc.equals(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}

			/*__________________________________________________________________________________________________________________*/		

			else if((xpathKey.contains("img"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//img[@alt='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = element.getAttribute("alt");
				if(Imgsrc.equals(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}

			/*__________________________________________________________________________________________________________________*/		

			else if((xpathKey.contains("id"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//img[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = element.getAttribute("alt");
				if(Imgsrc.equals(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}
				else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/

			else if((xpathKey.contains("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//img[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: skyblue ; border: 4px  red;");
				String Imgsrc = element.getAttribute("alt");
				if(Imgsrc.equals(data)){
					System.out.println("Image '"+data+"' Present");
					result = "Pass";
				}else{
					result = "Fail";
				}
			}
			/*__________________________________________________________________________________________________________________*/				
		} catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Image '"+data+"' Not Present");
			return "Fail";
		}catch (NoSuchElementException e) {
			//return "Fail - can't see element-"+xpathKey;
			System.out.println("Image '"+data+"' Not Present");
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Image '"+data+"' Not Present");
			return "Fail";
		}catch(Exception e){
			System.out.println("Image '"+data+"' Not Present");
			return "Fail";
		}

		try{
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}catch(Exception e){

		}
		return result;					
	}

	//new Select(driver.findElement(By.xpath("//select[@id='cardType']"))).selectByVisibleText("Visa");

	public String select(String xpathKey,String data){

		Popup_TFO();Popup_CRC();
		System.out.println("Executin Select at '"+xpathKey+"'");
		WebElement element = null;
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		String result = "Fail";
		try {

			String[] xpathKey1 =  xpathKey.split(">");

			if(xpathKey.contains("name"))
			{
				try
				{
					element = (driver.findElement(By.xpath("//value[@name='"+xpathKey1[1]+"']")));
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: rose ; border: 4px  red;");
					new Select(element).selectByVisibleText(data);
				}
				catch(NoSuchElementException e)
				{
					try
					{
						String[] xpathKey2 =  xpathKey1[1].split(":");
						if(xpathKey.contains("name")&&xpathKey.contains(":"))
						{
							element =driver.findElement(By.xpath("(//select[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"));
							js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: rose ; border: 4px  red;");
							new Select(driver.findElement(By.xpath("(//select[@name='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"))).selectByVisibleText(data);
						}
						else
						{
							new Select(driver.findElement(By.xpath("//select[@name='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
						}
					}
					catch(NoSuchElementException ae)
					{
						return "Fail";
					}
				}
				result = "Pass";
			} 

			else if(xpathKey.contains("class"))
			{
				new Select(driver.findElement(By.xpath("//select[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
				result = "Pass";
			}
			else if(xpathKey.contains("value"))
			{
				new Select(driver.findElement(By.xpath("//value[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
				result = "Pass";
			}
			else if(xpathKey.contains("select")||xpathKey.contains("id"))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");

				if((xpathKey.contains("select")||xpathKey.contains("id"))&&(xpathKey.contains(":")))
				{
					new Select(driver.findElement(By.xpath("(//*[@id='"+xpathKey2[0]+"'])["+xpathKey2[1]+"]"))).selectByVisibleText(data);
					result = "Pass";
				}
				else
				{
					new Select(driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"))).selectByVisibleText(data);
					result = "Pass";
				}
			}
			else if(xpathKey.contains("text"))
			{
				driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]")).click();
				result = "Pass";
			}
		}
		catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Can not Select  '["+data+"]' From the List");
			return "Fail";
		}
		catch (NoSuchElementException e){
			System.out.println("Can not Select  '["+data+"]' From the List");
			return "Pass";
		}catch(StaleElementReferenceException SES){
			System.out.println("Can not Select  '["+data+"]' From the List");
			return "Fail";
		}
		catch(Exception e){
			System.out.println("Can not Select  '["+data+"]' From the List");
			return "Fail";
		}
		return result;					
	}


	public String verifyObject(String xpathKey){

		Popup_CRC();popup();

		String result = "Fail";
		JavascriptLibrary jsLib = new JavascriptLibrary(); 
		System.out.println("Executing VerifyObject at '"+xpathKey+"'");
		WebElement element = null;
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		try {
			String[] xpathKey1 =  xpathKey.split(">");



			/*******************************************************************************************/
			if(xpathKey.startsWith("//"))
			{

				element = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.isDisplayed();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"isDisplayed", "0,0");	
					result = "Pass";
				}
			}

			else if((xpathKey.startsWith("text"))&&((xpathKey.contains(">")))){

				element = driver.findElement(By.xpath("//*[contains(text(),'"+xpathKey1[1]+"')]"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}

				String txt2 = element.getAttribute("text");
				String txt = element.getAttribute("textContent");
				String txt1 = element.getText();

				if(txt.contains(xpathKey1[1])||txt1.contains(xpathKey1[1])||txt2.contains(xpathKey1[1])){
					//System.out.println("Pass");
					result = "Pass";
				}else if(xpathKey1[1].contains(txt)||xpathKey1[1].contains(txt1)||xpathKey1[1].contains(txt2)){
					//System.out.println("Pass");
					result = "Pass";

				}
			}
			else if(xpathKey.contains("checked"))
			{
				if(driver.findElement(By.xpath("//*[@checked='"+xpathKey+"']")).isEnabled())
				{
					result = "Pass";
				}
			}
			else if(xpathKey.contains("active"))
			{
				element = driver.findElement(By.xpath("h4//[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lime; color: black");
				try {
					element.isDisplayed();
					result = "Pass";
				} catch (Exception e) {
					jsLib.callEmbeddedSelenium(driver,"triggerMouseEventAt", element,"isDisplayed", "0,0");	
					result = "Pass";
				}
			}

			else if((xpathKey.startsWith("class"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}

			else if((xpathKey.startsWith("name"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}

			else if((xpathKey.startsWith("type"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@type='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}

			else if((xpathKey.startsWith("id"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@id ='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}

			else if((xpathKey.startsWith("alt"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@alt ='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}
			else if((xpathKey.startsWith("title"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@title ='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}
			else if((xpathKey.startsWith("value"))&&((xpathKey.contains(">"))))
			{
				element = driver.findElement(By.xpath("//*[@value ='"+xpathKey1[1]+"']"));
				try{
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background:  lavender; border: 2px  red;color: red");

				}catch(Exception e){

				}
				element.isDisplayed();
				result = "Pass";
			}



		}catch (NoSuchElementException e) {
			System.out.println("Object at ["+xpathKey+"] is not Present");
			return "Fail";
		}catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Object at ["+xpathKey+"] is not Present");
			return "Fail";

		}catch(StaleElementReferenceException SES){
			System.out.println("Object at ["+xpathKey+"] is not Present");
			return "Fail";
		}
		catch(Exception e){
			System.out.println("Object at ["+xpathKey+"] is not Present");
			return "Fail";
		}

		try{
			Thread.sleep(200);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}catch(Exception e){

		}
		/* try {

       if((result.equals("Fail")&&(prospective == "Debug"))){
			Runtime.getRuntime().exec("C://Users//rajpuths//Desktop//Auto_It//Prospective//pros.exe");
			System.out.println("Debug Prospective");
		}
		}catch (IOException e) {
			e.getMessage();
			//e.printStackTrace();	
			}*/
		return result;					
	}

	public String switchTab(String title)
	{
		try{

			Popup_TFO();Popup_CRC();
			String result = "Fail";
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> window = windows.iterator();
			while( window.hasNext() ) {

				driver.switchTo().window( window.next() );

				try
				{
					if(driver.switchTo().window( window.next()).getTitle().equals(title));
					result = "Pass";

				}catch (UnhandledAlertException e)
				{
					Alert alert = driver.switchTo().alert();
					alert.accept();
					System.out.println("Failed to Switch to '"+title+"' Tab");
					return "Fail";
				}
				catch(Exception e)
				{
					System.out.println("Failed to Switch to '"+title+"' Tab");
					return "Fail";
				}
			}

			if(result=="Pass"){
				System.out.println("Switched to Tab "+title);	    	
			}
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to switch to Tab "+title);
			return "Fail";
		}catch (UnhandledAlertException e){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Unable to switch to Tab "+title);
			return "Fail";
		}catch(NoSuchElementException e){
			System.out.println("Unable to switch to Tab "+title);
			return "Fail";
		}catch(Exception e){
			System.out.println("Unable to switch to Tab "+title);
			return "Fail";

		}
		return "Pass";

	} 

	public String AcceptAlert(){
		String result = "Fail";

		try {
			driver.switchTo().alert().accept();
			result = "Pass";
		} catch (Exception e) {
			driver.switchTo().alert().accept();
			result = "Pass";
		}

		return result;
	}

	public String HandleAlert(String title)
	{
		String result = "Fail";

		try{
			Set<String> windows = driver.getWindowHandles();
			Iterator<String> window = windows.iterator();
			while( window.hasNext() ) {

				driver.switchTo().window( window.next() );
				if(driver.switchTo().window( window.next()).getTitle().equals(title));
				driver.close();
				Set<String> windows1 = driver.getWindowHandles();
				Iterator<String> window1 = windows1.iterator();
				driver.switchTo().window( window1.next() ); 	
				result = "Pass";
			}
		}catch(Exception e)
		{
			result = "Fail";
		}
		return result;
	}

	public String closeTab()
	{
		String result = "Fail";

		try{
			driver.close();
			Set<String> windows1 = driver.getWindowHandles();
			Iterator<String> window1 = windows1.iterator();
			driver.switchTo().window( window1.next() ); 	
			result = "Pass";
		}catch(Exception e){
			return "Fail";

		}
		return result;
	}

	//Closing desired Tab

	/*public String tabHandle(String xpathKey,String data){
		String result = "Fail";

		if(((xpathKey.contains("//")))&&(data.contains(".")))
		{	


		}
		return result;

	}
	 */

	/*public String HandleAlert(String title) {
		try {
			WebDriver myDriver = driver.switchTo().window(title);
			if(myDriver != null) {
				myDriver.close();
			}
		} catch(Exception e) {
			return "Fail";
		}
		return "Pass";

	}
	 */

	public String MouseHover(String xpathKey,String data)
	{
		Popup_TFO();Popup_CRC();
		JavascriptExecutor js =  (JavascriptExecutor) driver;
		String result = "Fail";
		Actions builder = new Actions(driver);
		System.out.println("Executing MouseHover");
		try{
			String[] xpathKey1 =  xpathKey.split(">");
			if((xpathKey.contains("text"))&&((xpathKey.contains(":"))))
			{
				String[] xpathKey2 =  xpathKey1[1].split(":");
				WebElement tagElement = driver.findElement(By.xpath("(//*[contains(text(),'"+xpathKey2[0]+"')])["+xpathKey2[1]+"]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(5000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	

				result = "Pass";
			}

			else if(xpathKey.contains("//"))
			{
				WebElement tagElement = driver.findElement(By.xpath(xpathKey));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}
			else if(xpathKey.contains("id")&&(data!=null))
			{
				WebElement tagElement = driver.findElement(By.xpath("//*[@id='"+data+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}
			else if(xpathKey.contains("id"))
			{
				WebElement tagElement = driver.findElement(By.xpath("//*[@id='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}
			else if(xpathKey.contains("name"))
			{
				WebElement tagElement = driver.findElement(By.xpath("//*[@name='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}

			else if(xpathKey.contains("class"))
			{
				WebElement tagElement =driver.findElement(By.xpath("//*[@class='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}

			else if(xpathKey.contains("text"))
			{
				Actions builder1 = new Actions(driver);
				WebElement tagElement =driver.findElement(By.xpath("//*[contains(text(),'"+data+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				builder1.moveToElement(tagElement).build();
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}
			else if(xpathKey.contains("link"))
			{
				WebElement tagElement = driver.findElement(By.xpath("//*[@link='"+xpathKey1[1]+"']"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}
			else if((xpathKey.contains("text"))&&((xpathKey.contains(">"))))
			{
				WebElement tagElement = driver.findElement(By.xpath("//a[contains(text(),'"+xpathKey1[1]+"')]"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "background: lavender ; border: 2px  red;color: black");
				Thread.sleep(2000);
				Thread.sleep(2000);
				builder.moveToElement(tagElement).build().perform();   
				Thread.sleep(2000);
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", tagElement, "");	
				result = "Pass";
			}

		}catch (NoSuchElementException e){
			System.out.println("Unable to Hover Mouse at the Location ['"+xpathKey+"']");
			return "Fail";
		}
		catch (UnhandledAlertException e){
			System.out.println("Unable to Hover Mouse at the Location ['"+xpathKey+"']");
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}catch(StaleElementReferenceException SES){
			System.out.println("Unable to Hover Mouse at the Location ['"+xpathKey+"']");
			return "Fail";
		}

		catch(Exception e){
			System.out.println("Unable to Hover Mouse at the Location ['"+xpathKey+"']");
			return "Fail";
		}

		return result;					
	}


	public String file_upload (String data) throws FileNotFoundException
	{
		String result = "Fail";
		try
		{
			StringSelection abc=new StringSelection(data);

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(abc, null);

			Thread.sleep(3000);

			Robot r = new Robot(); 

			r.keyPress(KeyEvent.VK_ENTER); 
			r.keyRelease(KeyEvent.VK_ENTER); 
			r.keyPress(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V); 
			r.keyRelease(KeyEvent.VK_CONTROL); 
			r.keyPress(KeyEvent.VK_ENTER); 
			r.keyRelease(KeyEvent.VK_ENTER); 
			result = "Pass";
		}catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}
		catch(Exception e)
		{
			return "Fail";	
		}
		return result;

	}

	public String verifyBreadCrumb(String xpathKey){
		Popup_TFO();Popup_CRC();
		WebElement element = null;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			element = driver.findElement(By.xpath(xpathKey));
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "background: lavender ; border: 2px  red;color: black");
			if(driver.findElement(By.xpath(xpathKey)).isDisplayed()){

				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");	
			}

		} catch (NoSuchElementException e) {
			//return "Fail - can't see element-"+xpathKey;
			return "Fail";
		}catch (UnhandledAlertException e)
		{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Fail";
		}
		catch(Exception e){
			return "Fail";
		}
		System.out.println("Executing verifyBreadCrumb");
		return "Pass";					
	}

	public String waitThread() throws InterruptedException
	{
		//  driver.manage().timeouts().implicitlyWait(data, TimeUnit.SECONDS);
		Thread.sleep(5000);
		return "Pass";	  
	}

	/**************************Application dependent****************************/
	public String checkMail(String mailName){
		String result = "Fail";
		System.out.println("Executing checkMail");
		try{
			driver.findElement(By.linkText(mailName)).click();
			result = "Pass";
		}catch(Exception e){
			return "Fail-Mail not found";
		}
		return result;							
	}

	//******Singleton function*******//
	public static Keywords getKeywordsInstance(){
		if(keywords == null){
			keywords = new Keywords();
		}


		return keywords;
	}

	public  String deletcookies()
	{
		try
		{
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
		}
		catch(Exception e)
		{
			return "Fail";
		}

		return "Pass";
	}

	public  String popup()
	{
		Popup_TFO();Popup_CRC();

		try {
			//  assertTrue(isElementPresent(By.id("//*[@id='banner-proceed-button']")));

			if(driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed())
			{
				driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
			}


		} catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return "Pass";

		}catch (NoSuchElementException e) {
			// System.out.println("No popup Exists");
		}
		return "Pass";
	}

	public  String handle()
	{
		/*if(driver.findElement(By.xpath("//p[contains(text(),'"+xpathKey+"')]")).isDisplayed())
		{

		}*/

		//driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();

		if(driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed())
		{
			driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
		}
		return "Pass";

	}


	public String Popup_TFO(){

		{
			try {
				//  assertTrue(isElementPresent(By.id("//*[@id='banner-proceed-button']")));
				if(driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed())
				{
					driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
				}

			} catch (UnhandledAlertException e) {
				driver.switchTo().alert().accept();
				return "Pass";
			}catch (NoSuchElementException e) {
				// System.out.println("No popup Exists");
			}
			return "Pass";
		}
	}
	public String Popup_CRC(){

		{
			try {

				if(driver.findElement(By.xpath("//input[@name='Close this message']")).isDisplayed())
				{
					driver.findElement(By.xpath("//input[@name='Close this message']")).click();
				}
			} catch (UnhandledAlertException e) {
				driver.switchTo().alert().accept();
				return "Pass";
			}catch (NoSuchElementException e) {
			}
			return "Pass";
		}
	}

	/*try {driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).isDisplayed();       			
      	  driver.findElement(By.xpath("//*[@id='banner-proceed-button']")).click();
      	  Thread.sleep(1000);
        	}catch (NoSuchElementException | InterruptedException e) {}

       try{ driver.findElement(By.xpath("//input[@name='Close this message']")).isDisplayed();
      	  driver.findElement(By.xpath("//input[@name='Close this message']")).click();
      	  Thread.sleep(1000);
        }catch (NoSuchElementException | InterruptedException e) {}

           return "Pass";*/


	public  boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (UnhandledAlertException e) {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}







	public void highlightElement(WebElement element) {
		// for (int i = 0; i < 2; i++) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px  yellow;");
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		//}
	}







}
