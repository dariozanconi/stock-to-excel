
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import core.Data;
import core.ExcelCreator;

class ExcelCreatorTest {
	
	/*
	 * 
	 */

	@Test
    public void testWorkbookCreation() {

        ArrayList<Date> dates = new ArrayList<>(Arrays.asList(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()-12345678)));
        ArrayList<Double> open = new ArrayList<>(Arrays.asList(102.34, 104.23));
        ArrayList<Double> high = new ArrayList<>(Arrays.asList(103.34, 106.23));
        ArrayList<Double> low = new ArrayList<>(Arrays.asList(101.37, 102.67));
        ArrayList<Double> close = new ArrayList<>(Arrays.asList(102.73, 104.28));
        ArrayList<Double> adjClose = new ArrayList<>(Arrays.asList(102.73, 104.28));
        Boolean[] settings = {true, true, true, true, true};
        String name = "Visa Inc.";
        String currency = "USD";
        String exchangeName = "NYSE";
        String InstrumentType = "EQUITY";
        Double marketPrice = 103.12;

        Data data = new Data(dates, open, high, low, close, adjClose, name, currency, exchangeName, InstrumentType, marketPrice);
        ExcelCreator creator = new ExcelCreator(data, settings);
        XSSFWorkbook workbook = creator.createWorkbook();
        
        assertNotNull(workbook, "Workbook should not be null");
        
	}
}
