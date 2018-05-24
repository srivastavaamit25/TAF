package com.tnf.testreports;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.tnf.util.Keywords;

public class ReportGenerator {
	Keywords key = new Keywords();
	//Date and Time
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date date = new Date();
	//For Time Diff
	DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
	Date startTime = new Date();
	String Time1 = timeFormat.format(startTime);
	private String folderPath = System.getProperty("user.dir")+"\\src\\com\\tnf\\testreports\\";
	public static String reportGeneratePath = "C:\\TAF_Automation\\Reports";

	//Methods//////////////////////
	
	public void createFolder(String folderName) {
		File file = new File(reportGeneratePath + "\\" + folderName);
		if(!file.isDirectory()) {
			boolean folderCreated = file.mkdir();
			System.out.println("FolderCreated : " + folderCreated);
		
		}
	}
	
	public StringBuffer getData(String filePath) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(folderPath + filePath));
			String str;
			while ((str = br.readLine()) != null)
				buffer.append(str);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return buffer;
	}
	
	public void writeFile(String path, String data) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			bw.write(data);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
					bw = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (fw != null) {
					fw.close();
					fw = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void generateTestSteps(List<List<String>> dataList, String tcid, String rowNum) {

		List<String> columnNames = new ArrayList<String>();
		columnNames.add("TCID");
		columnNames.add("Description");
		columnNames.add("ExpectedResult");
		columnNames.add("Result");
		columnNames.add("ErrorReason");

		StringBuffer fileData = new StringBuffer();
		fileData.append(getData("header.txt"));//Time
		fileData.append(TimeDiffbtwnTC(columnNames));
		fileData.append(prepareColumnNameData(columnNames));
		int i=0;
		for(List<String> rowData : dataList) {
			fileData.append(addTestStepRow(rowData, tcid, i));
			i++;
		}

		fileData.append(getData("bottom.txt"));

		writeFile(reportGeneratePath + "\\" + tcid + "\\" + tcid + rowNum +".html", fileData.toString());
	}
	
	public void generateTCIDPage(String tcid, List<String> columnNames, List<List<String>> testDataList) {

		StringBuffer fileData = new StringBuffer();
		fileData.append(getData("header.txt"));
		fileData.append(TimeDiffbtwnTC(columnNames));
		columnNames.add(0, "TCTD");
		columnNames.add("Result");
		fileData.append(prepareColumnNameData(columnNames));
		for(List<String> rowData : testDataList) {
			fileData.append(addTCIDRow(tcid, rowData));
		}

		fileData.append(getData("bottom.txt"));

		writeFile(reportGeneratePath + "\\" + tcid + "\\index.html", fileData.toString());
	}

	public void generateIndexPage(List<List<String>> indexDataList) {
		/* Prepare Column Names for Index.html */
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("TCID");
		columnNames.add("Description");
		columnNames.add("Result");


		StringBuffer fileData = new StringBuffer();
		fileData.append(getData("header.txt"));
		fileData.append(Date(columnNames));
		fileData.append(prepareColumnNameData(columnNames));
		for(List<String> rowData : indexDataList) {
			fileData.append(addIndexRow(rowData));
		}

		fileData.append(getData("bottom.txt"));
		writeFile(reportGeneratePath + "\\" + "index.html", fileData.toString());
	}
	
	public String Date(List<String> columnNames) {

//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		Date date = new Date();
		Date endTime = new Date();
		String Time2 = timeFormat.format(endTime);


		System.out.println("End Time "+ Time2);
		System.out.println("Start Time "+ Time1);

		System.out.println("********");
		String[] parts2 = Time2.split(":");
		int ETH = Integer.parseInt(parts2[0]);
		int ETM = Integer.parseInt(parts2[1]);
		int ETS = Integer.parseInt(parts2[2]);

		System.out.println("********");

		String[] parts1 = Time1.split(":");
		int STH = Integer.parseInt(parts1[0]);
		int STM = Integer.parseInt(parts1[1]);
		int STS = Integer.parseInt(parts1[2]);

		int HrsDiff = Math.abs(ETH-STH);
		int MinDiff = Math.abs(ETM-STM);
		int SecDiff = Math.abs(ETS-STS);

		System.out.println("TimeTaken :"+HrsDiff+":"+MinDiff+":"+SecDiff);

		StringBuffer columnNameBuffer = new StringBuffer();
		//***************Date Column
		columnNameBuffer
		.append("<TR><TH align='left' width='10%'><B>Execution Date & Time:  </b></th> <th align='left'>"+ dateFormat.format(date)+"</TH></TR>");

		//*************** Time Diff Column
		columnNameBuffer
		.append("<TR><TH align='left' width='10%'><B>Total Execution Time :  </b></th> <th align='left'>"+ HrsDiff+":"+MinDiff+":"+SecDiff+"</TH></TR>"
				+ "</TABLE><Table Border Width=100%><border=1 color=red cellPadding=3 cellSpacing=2 width='100%'><tr>" );
		//***************** Table Column
		columnNameBuffer.append("<TABLE border=1 cellPadding=0 cellSpacing=0 width='100%'>"
				+ "<TR><th colSpan=1 bgcolor = #F8F8F8 vAlign=top align=center valign='center'><B><FONT color=#8d110e face=#009900 size=3><br>Test Case Details </FONT></B></td></TR><tr><td><Table Border Width=100%>");
		return columnNameBuffer.toString();

	}
	
	public String TimeDiffbtwnTC(List<String> columnNames) {

//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		Date date = new Date();
		
		//For Time Diff
		DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
		Date startTime = new Date();
		String Time1 = timeFormat.format(startTime);
		
		Date endTime = new Date();
		String Time2 = timeFormat.format(endTime);


		System.out.println("End Time "+ Time2);
		System.out.println("Start Time "+ Time1);

		System.out.println("********");
		String[] parts2 = Time2.split(":");
		int ETH = Integer.parseInt(parts2[0]);
		int ETM = Integer.parseInt(parts2[1]);
		int ETS = Integer.parseInt(parts2[2]);

		System.out.println("********");

		String[] parts1 = Time1.split(":");
		int STH = Integer.parseInt(parts1[0]);
		int STM = Integer.parseInt(parts1[1]);
		int STS = Integer.parseInt(parts1[2]);

		int HrsDiff = Math.abs(ETH-STH);
		int MinDiff = Math.abs(ETM-STM);
		int SecDiff = Math.abs(ETS-STS);

		System.out.println("TimeTaken :"+HrsDiff+":"+MinDiff+":"+SecDiff);

		StringBuffer columnNameBuffer = new StringBuffer();
		//***************Date Column
		columnNameBuffer
		.append("<TR><TH align='left' width='10%'><B>Execution Date & Time:  </b></th> <th align='left'>"+ dateFormat.format(date)+"</TH></TR>");

		//*************** Time Diff Column
		columnNameBuffer
		.append(/*"<TR><TH align='left' width='10%'><B>Total Execution Time :  </b></th>"
				+ " <th align='left'>"+ HrsDiff+":"+MinDiff+":"+SecDiff+"</TH></TR>"+*/
				 "</TABLE><Table Border Width=100%><border=1 color=red cellPadding=3 cellSpacing=2 width='100%'><tr>" );
		//***************** Table Column
		columnNameBuffer.append("<TABLE border=1 cellPadding=0 cellSpacing=0 width='100%'>"
				+ "<TR><th colSpan=1 bgcolor = #F8F8F8 vAlign=top align=center valign='center'><B><FONT color=#8d110e face=#009900 size=3><br>Test Case Details </FONT></B></td></TR><tr><td><Table Border Width=100%>");
		return columnNameBuffer.toString();

	}
	
	public String prepareColumnNameData(List<String> rowData) {
		StringBuffer columnNameBuffer = new StringBuffer();
		columnNameBuffer.append("<tr>");
		for (String columnData : rowData) {
			columnNameBuffer
			.append("<th BGCOLOR=#00248F width=17%><font color=white face=Verdana size=2>"
					+ columnData + "</font></th>");
		}
		columnNameBuffer.append("</tr>");
		return columnNameBuffer.toString();
	}

	public String prepareColumnNameData(Set<String> rowData) {
		StringBuffer columnNameBuffer = new StringBuffer();
		columnNameBuffer.append("<tr>");
		for (String columnData : rowData) {
			columnNameBuffer
			.append("<<th BGCOLOR=#00248F width=17%><font color=white face=Verdana size=2>"
					+ columnData + "</font></th>");
		}
		columnNameBuffer.append("</tr>");
		return columnNameBuffer.toString();
	}

	public String addIndexRow(List<String> rowData) {
		StringBuffer indexRowData = new StringBuffer();
		indexRowData.append("<tr bgcolor=#D3D3D3>");
		indexRowData.append("<td Align=Left><a href='" + ".\\" + rowData.get(0) + "\\index.html'>" + rowData.get(0) + "</a></td>");
		indexRowData.append("<td Align=Left>" + rowData.get(1) + "</td>");
		indexRowData.append("<td Align=Left>" + rowData.get(2) + "</td>");
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}

	public String addTCIDRow(String tcid, List<String> rowData) {
		StringBuffer indexRowData = new StringBuffer();
		indexRowData.append("<tr bgcolor=#D3D3D3>");
		indexRowData.append("<td Align=Left><a href='" + ".\\" +  tcid + rowData.get(0) + ".html'>" + tcid + "</a></td>");
		for(int i=0; i<rowData.size(); i++) {
			indexRowData.append("<td Align=Left>" + rowData.get(i) + "</td>");
		}
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}

	public String addTestStepRow(List<String> rowData, String tcid, int rowNo) {
		//System.out.println("Sai =======> "+ rowData);
		StringBuffer indexRowData = new StringBuffer();
		//indexRowData.append("<tr bgcolor=#D3D3D3>");		
		indexRowData.append("<tr bgcolor=#D3D3D3>");

		for(int i=0; i<rowData.size(); i++) {
			String link = rowData.get(i);
			//System.out.println(link);
			if (link.equals("FAIL")) {								//C:\TAF_Automation\Screen Shots+Filename
				//indexRowData.append("<td Align=Left><b><a href='http://www.facebook.com'target='_blank'>"+rowData.get(i)+"</a></b></td>");
				indexRowData.append("<td Align=Left><a href='" + "..\\ScreenShots\\" +  tcid + "_" + rowNo + ".jpg'target='_blank'> FAIL </a></td>");
			}else
			{
				indexRowData.append("<td Align=Left>" + rowData.get(i) + "</td>");
			}
		}
		indexRowData.append("</tr>");
		return indexRowData.toString();
	}
}
