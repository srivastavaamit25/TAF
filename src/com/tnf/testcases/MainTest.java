package com.tnf.testcases;
import java.awt.AWTException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.tnf.testreports.ReportGenerator;
import com.tnf.util.Keywords;
import com.tnf.util.TestUtil;

//Child class of Keywords or Subclass of Keywords
public class MainTest /*<--child*//*parent-->*/ extends Keywords {
	ReportGenerator reportGenerator = new ReportGenerator();
	//********************************************//
	@Test
	public void applicationTest() throws InterruptedException, AWTException, FileNotFoundException {

		//driver.manage().deleteAllCookies();
		int rowCount = xls.getRowCount("Test Cases");
		System.out.println("--->"+rowCount);
		List<List<String>> indexDataList = new ArrayList<List<String>>();
		//list of list 
		try {
			//Looping Test Cases Sheet
			for (int i = 1; i <= rowCount; i++) {//i=475
				//Sheet name, column, Row
				String tcid = xls.getCellData("Test Cases", 0, i + 1);
				String desc = xls.getCellData("Test Cases", 1, i + 1);
				String runMode = xls.getCellData("Test Cases", 2, i + 1);
				
				if("Y".equalsIgnoreCase(runMode)) {
					List<String> indexData = new ArrayList<String>();
					indexData.add(tcid);
					indexData.add(desc);
					
					/*Create folder */
					reportGenerator.createFolder(tcid);
					List<List<String>> testDataList = new ArrayList<List<String>>();
					List<Map<String, String>> dataList = TestUtil.getData(tcid, Keywords.xls);//(01 HomePage, Data sheet path)
					List<String> columnNames = new ArrayList<String>();   //(a, b)(

					for(String name : dataList.get(0).keySet()) {
						if("RowNum".equalsIgnoreCase(name)) {//RowNum=RowNum
							columnNames.add(0, name);
						} else {
							columnNames.add(name);
						}
					}
					boolean resultFlag = true;

					//Looping Tests
					for (Map<String, String> data : dataList) {

						if (!TestUtil.isTestCaseExecutable(tcid, Keywords.xls))
							//throw new SkipException("Skipping the test as Runmode is NO");
							break;
						if(data.get("RunMode").equalsIgnoreCase("N")){
							//throw new SkipException("Skipping the test as data set Runmode is NO");
							continue;
						}else if(!data.get("RunMode").equalsIgnoreCase("Y")){
							break;
						}
						
						List<String> testData = new ArrayList<String>();
						for(String name : columnNames) {
							testData.add(data.get(name));
						}

						List<List<String>> rowList = new ArrayList<List<String>>();
						StringBuffer tr_testData = new StringBuffer();
						Keywords k = Keywords.getKeywordsInstance();
						k.executeKeywords(tcid, data, rowList, tr_testData);
						testData.add(tr_testData.toString());
						testDataList.add(testData);

						reportGenerator.generateTestSteps(rowList, tcid, data.get("RowNum"));

						if(!resultFlag || !"PASS".equals(tr_testData.toString())) {
							resultFlag = false;
						}
					}
					reportGenerator.generateTCIDPage(tcid, columnNames, testDataList);
					if(resultFlag) {
						indexData.add("PASS");
					} else {
						indexData.add("FAIL");
					}
					indexDataList.add(indexData);
				}
			}
		} finally {
			reportGenerator.generateIndexPage(indexDataList);
		}
	}
}
