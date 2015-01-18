package kata1;

import static org.junit.Assert.*;

import org.junit.Test;

public class Kata1TDD {

	@Test
	public void testEmptyStringReturns0() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(0, calculator.add(""));
	}
	
	@Test
	public void testStringWith1NumberReturnsSameNumber() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(5, calculator.add("5"));
	}

	@Test
	public void testStringWith2CommaSeparatedNumbersReturnsSumOfNumbers() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(11, calculator.add("3,8"));
	}

	@Test
	public void testStringWithUnknownAmountOfCommaSeparatedNumbersReturnsSumOfNumbers() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(51, calculator.add("3,8,15,21,4"));
	}

	@Test
	public void testStringWithUnknownAmountOfCommaOrNewlineSeparatedNumbersReturnsSumOfNumbers() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(25, calculator.add("2,10\n7,6"));
	}

	@Test
	public void testStringWithUnknownAmountOfCustomDelimiterSeparatedNumbersReturnsSumOfNumbers() throws Exception {
		StringCalculator calculator = new StringCalculatorImpl();
		
		assertEquals(3, calculator.add("//;\n1;2"));
	}

	@Test
	public void testStringWithNegativeNumberThrowsException() {
		StringCalculator calculator = new StringCalculatorImpl();
		
		try {
			calculator.add("-4");
			fail("Exception should have been thrown.");
		}
		catch (Exception e) {
			assertTrue(e.getMessage().contains("Negatives not allowed"));
			assertTrue(e.getMessage().contains("-4"));
		}
	}

	@Test
	public void testStringWithNegativeNumbersThrowsException() {
		StringCalculator calculator = new StringCalculatorImpl();
		
		try {
			calculator.add("//;\n3;-4;34;-57;9");
			fail("Exception should have been thrown.");
		}
		catch (Exception e) {
			assertTrue(e.getMessage().contains("Negatives not allowed:"));
			assertTrue(e.getMessage().contains("-4"));
			assertTrue(e.getMessage().contains("-57"));
		}
	}

}
