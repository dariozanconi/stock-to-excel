package core;

import java.util.Arrays;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreator {
	
	/*
	 * The class ExcelCreator works with the Framework Apache POI.
	 * Got an instance of ExcelCreator with the parameter of Data (Stock values etc.) and the boolean array setColumn (which values to be saved)
	 * it returns with the method .createWorkbook a XSSFWorkbook (excel spreadsheet) with the corresponding data loaded
	 * 
	 * in this format for example:
	 * 
	 * DATE         OPEN    CLOSE,  ...   NAME        CURRENCY
	 * 13/04/2023   123.32  124.65, ...   Apple Inc.  USD
	 * 14/04/2023   125.87  126.62, ...
	 * ...
	 */
	
	Data data;
	Boolean[] setColumn; //settings of column
	
	public ExcelCreator(Data data, Boolean[] setColumn) {
		this.data = data;
		this.setColumn = setColumn;
	}
	
	public XSSFWorkbook createWorkbook() {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFCreationHelper createHelper = workbook.getCreationHelper();
		XSSFSheet sheet = workbook.createSheet(data.getName());
		
		//********CELL STYLES**********
		
		XSSFCellStyle headerStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);
		
		XSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
				
		XSSFCellStyle decimalStyle = workbook.createCellStyle();
		decimalStyle.setDataFormat(createHelper.createDataFormat().getFormat("#.##"));
		
		
		String[] labels = {"DATE", "OPEN", "HIGH", "LOW", "CLOSE", "ADJ. CLOSE"};
		List<List<Double>> valueLists = Arrays.asList(
			    data.getOpen(), data.getHigh(), data.getLow(), data.getClose(), data.getAdjClose()
			);
				
		// ********LABELS************
		// Setting the LABELS, starting with DATE, then OPEN, HIGH, LOW etc. 
		// according to the settings of the Boolean[] setColumns -> which column set or not
		
		// "index" permits to set the selected values each time "in the next column" (otherwise empty columns would be present
		// -> row.createCell(index++);
		
		int index = 0;  
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell = row.createCell(index++);
		cell.setCellValue("DATE");
		cell.setCellStyle(headerStyle);

		for (int j = 0; j < 5; j++) { 
			if (setColumn[j]) {                //-> setting control
				cell = row.createCell(index++);	
				cell.setCellValue(labels[j+1]);
			    cell.setCellStyle(headerStyle);
			}   
		}
		
		//...The remaining Labels of METADATA
		
		XSSFCell cellName = row.createCell(index+3);
		cellName.setCellValue("NAME");
		cellName.setCellStyle(headerStyle);

		XSSFCell cellCurrency = row.createCell(index+4);
		cellCurrency.setCellValue("CURRENCY");
		cellCurrency.setCellStyle(headerStyle);

		XSSFCell cellExchange = row.createCell(index+5);
		cellExchange.setCellValue("STOCK EXCHANGE");
		cellExchange.setCellStyle(headerStyle);
		
		//********VALUES**********
		//SETTING THE SELECTED VALUES		
		// First set the dates, then the selected values according to the settings of setColumn
		
		for (int i = 0; i< data.getDate().size(); i++) {
			
			row = sheet.createRow(i+1);
			index = 1;
			
			cell = row.createCell(0);
			cell.setCellValue(data.getDate().get(i));
			cell.setCellStyle(dateStyle);
			
			for (int c = 0; c < 5; c++) {
			    if (setColumn[c]) {               //-> setting control
			        cell = row.createCell(index++);			       
			        cell.setCellValue(valueLists.get(c).get(i));
			        cell.setCellStyle(decimalStyle);
			        
			    }			    			
			}
			
			//Only in the second row: set the metadata values
			
			if (i==0) {
				XSSFCell metaName = row.createCell(index+3);
				metaName.setCellValue(data.getName());

				XSSFCell metaCurrency = row.createCell(index+4);
				metaCurrency.setCellValue(data.getCurrency());

				XSSFCell metaExchange = row.createCell(index+5);
				metaExchange.setCellValue(data.getExchangeName());
			}
		}
		
		//Auto sizing of all columns
		for (int i = 0; i < 15; i++) {
		    sheet.autoSizeColumn(i);
		}
		
		return workbook;
		
		
	}
}
