package driverFactory;

import org.openqa.selenium.WebDriver;

import commanFunction.FunctionLibrary;
import utilites.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	String TestCases = "MasterTestCases";
	String inputpath = "./FileInput/ERP_Stock.xlsx";
	String Outputpath = "./FileOutPut/HybridResults.xlsx";

	public void startTest() throws Throwable {
		String Module_Status = "";
		// create object for Excelfile util class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		// iterate all rows in testcases sheet
		for (int i = 1; i <= xl.rowCount(TestCases); i++) {
			String Execution_Status = xl.getCellData(TestCases, i, 2);
			if (Execution_Status.equalsIgnoreCase("Y")) {
				// store correspoding sheet
				String TCModule = xl.getCellData(TestCases, i, 1);
				// iterate all rows in TCModule sheet
				for (int j = 1; j <= xl.rowCount(TCModule); j++) {
					// read all cells from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if (Object_Type.equalsIgnoreCase("startBrowser")) {
							driver = FunctionLibrary.startBrowser();
						}
						if (Object_Type.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl(driver);
						}
						if (Object_Type.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						}
						if (Object_Type.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
						}
						if (Object_Type.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						}
						if (Object_Type.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(driver, Test_Data);
						}
						if (Object_Type.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser(driver);
						}
						if (Object_Type.equalsIgnoreCase("mouseClick")) {
							FunctionLibrary.mouseClick(driver);
						}
						if (Object_Type.equalsIgnoreCase("categoryTable")) {
							FunctionLibrary.categoryTable(driver, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("captureStockNumber")) {
							FunctionLibrary.captureStockNumber(driver, Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("stockItemTable")) {
							FunctionLibrary.stockItemTable(driver);
						}
						
						
						// write as pass into status cell in TCMOdule
						xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
						Module_Status = "True";

					} catch (Exception e) {
						System.out.println(e.getMessage());
						// write a s Fail in status in TCModule sheet'
						xl.setCellData(TCModule, j, 5, "Fail", Outputpath);
						Module_Status = "False";
					}
					if (Module_Status.equalsIgnoreCase("True")) {
						// write a s pass into Testcases sheet
						xl.setCellData(TestCases, i, 3, "Pass", Outputpath);
					} else {
						// write a s Fail into Testcases sheet
						xl.setCellData(TestCases, i, 3, "Fail", Outputpath);
					}

				}
			} else {
				// write as blocked into test cases sheet
				xl.setCellData(TestCases, i, 3, "Blocked", Outputpath);
			}

		}
	}
}
