import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import core.Data;
import core.Parser;

class ParserTest {

	@Test
	void testValidSymbol() {
		 Parser parser = new Parser();
	     Data data = parser.getData("AAPL", "1d","1d");
	     
	     assertNotNull(data, "Data should not be null for valid symbol");
	     assertFalse(data.getDate().isEmpty(), "Date list should not be empty");
	     assertFalse(data.getClose().isEmpty(), "Close list should not be empty");
	     assertFalse(data.getOpen().isEmpty(), "Open list should not be empty");
	     assertFalse(data.getHigh().isEmpty(), "High list should not be empty");
	     
	     assertEquals("Apple Inc.", data.getName());

	}
	
	@Test
	public void testInvalidSymbol() {
	     Parser parser = new Parser();
	     Data data = parser.getData("INVALIDSYMBOL123", "1y","1mo");

	     assertNull(data, "Data should be null for an invalid symbol");
	    }

}
