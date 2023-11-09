package utilites;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil 
{
	Workbook Wb;
	//Constructor for reading Excel path
	
	public ExcelFileUtil(String Excelpath)throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		Wb = WorkbookFactory.create(fi);
		
	}
	
	//Counting no.of rows in a sheet
	public int rowCount(String sheetname)
	{
		return Wb.getSheet(sheetname).getLastRowNum();
		
	}
	
	//Get cell data from sheet
	public String getCellData(String sheetname,int row, int column)
	{
		String data = "";
		if(Wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata=(int) Wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
			
		}
		else
		{
			data = Wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	
	}
	
	//method for writing
	
		public void setCellData(String sheetname, int row, int column, String status, String WriteExcel)throws Throwable
		{
			//get sheet from wb
		Sheet ws = Wb.getSheet(sheetname);
		
		// get row from sheet
		
		Row rowNum = ws.getRow(row);
		
		//create cell
		Cell cell = rowNum.createCell(column);
		cell.setCellValue(status);
		
		if(status.equalsIgnoreCase("pass"))
		{
			CellStyle style = Wb.createCellStyle();
			Font font = Wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
			}
		
		else if(status.equalsIgnoreCase("fail"))
		{
			CellStyle style = Wb.createCellStyle();
			Font font = Wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = Wb.createCellStyle();
			Font font = Wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
		
	
		FileOutputStream fo = new FileOutputStream(WriteExcel);
				Wb.write(fo);
		
			
			
		}
		


	
	}
	
	
	
	


