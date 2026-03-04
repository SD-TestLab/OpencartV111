package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders 
{
	//DataProvider
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";	//Taking excel file from testdata
		
		ExcelUtility xlutil=new ExcelUtility(path);	//creating an object for xlutility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1", 1);
		
		String logindata [][]=new String[totalrows][totalcols];	//created for two dimension array which can store
		
		for(int i=1;i<=totalrows;i++)	//1	//read the data from x1 storing in two deminsional array
		{
			for(int j=0; j<totalcols; j++)	//0  i is rows j is col
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
		}
		return logindata;	//returning two dimension array
	}
}
