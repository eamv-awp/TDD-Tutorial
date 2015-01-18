package kata1;

import java.util.regex.Pattern;

public class StringCalculatorImpl implements StringCalculator {
	public final String delimiter = ",";
	public final String newline = "\n";
	public final String customDelimiterPrefix = "//";
	public final String customDelimiterStartWrapper = "[";
	public final String customDelimiterEndWrapper = "]";
	public final String negativeSign = "-";

	@Override
	public int add(String numbers) throws Exception {
		if (numbers.length() == 0)
			return 0;

		if (numbers.contains(newline)) { // NB: Custom-delimited input also contains newline
			String standardDelimitedNumbers = standardizeDelimiters(numbers);
			return add(standardDelimitedNumbers); // Re-run with standard-delimited numbers
		}

		if (numbers.contains(negativeSign)) { // Check for negative numbers
			throw new Exception("Negatives not allowed: " + listOfNegativeNumbers(numbers));
		}

		if (numbers.contains(delimiter)) // Recursively sum standard-delimited numbers
			return firstNumber(numbers) + add(remainingNumbers(numbers));
		else
			return firstNumber(numbers);
	}

	private int firstNumber(String numbers) {
		int delimiterIndex = numbers.indexOf(delimiter);
		if (delimiterIndex < 0)
			delimiterIndex = numbers.length();
		
		int number = Integer.parseInt(numbers.substring(0, delimiterIndex));
		
		if (number > 1000)
			number = 0;

		return number;
	}

	private String remainingNumbers(String numbers) {
		int delimiterIndex = numbers.indexOf(delimiter);
		int delimiterLength = 1;

		return numbers.substring(delimiterIndex + delimiterLength);
	}

	private String standardizeDelimiters(String customDelimitedNumbers) {
		String standardDelimitedNumbers;
		boolean hasMoreCustomDelimiters = false;
		if (customDelimitedNumbers.startsWith(customDelimiterPrefix)) {
			String customDelimiter;
			boolean containsWrappedDelimiter = customDelimitedNumbers.contains(customDelimiterStartWrapper) 
											   && customDelimitedNumbers.contains(customDelimiterStartWrapper);
			if (containsWrappedDelimiter) {
				int wrapperStartIndex = customDelimitedNumbers.indexOf(customDelimiterStartWrapper);
				int wrapperEndIndex = customDelimitedNumbers.indexOf(customDelimiterEndWrapper);
				customDelimiter = customDelimitedNumbers.substring(wrapperStartIndex + 1, wrapperEndIndex);
				
				if (customDelimitedNumbers.lastIndexOf(customDelimiterStartWrapper) != wrapperStartIndex)
					hasMoreCustomDelimiters = true;
			}
			else
				customDelimiter = customDelimitedNumbers.substring(2, 3);
			
			String numbersOnly = customDelimitedNumbers.substring(customDelimitedNumbers.indexOf(newline) + 1);
			standardDelimitedNumbers = numbersOnly.replaceAll(Pattern.quote(customDelimiter), delimiter);
			
			if (hasMoreCustomDelimiters) {
				int nextDelimiterIndex = customDelimitedNumbers.indexOf(customDelimiterEndWrapper) + 1;
				int firstNumberIndex = customDelimitedNumbers.indexOf(newline) + 1;
				String remainingDelimiters = customDelimitedNumbers.substring(nextDelimiterIndex, firstNumberIndex);
				
				String partiallyStandardDelimitedNumbers = customDelimiterPrefix + remainingDelimiters + standardDelimitedNumbers;
				
				return standardizeDelimiters(partiallyStandardDelimitedNumbers);
			}
		} else
			standardDelimitedNumbers = customDelimitedNumbers.replaceAll(Pattern.quote(newline), delimiter);

		return standardDelimitedNumbers;
	}

	private String listOfNegativeNumbers(String numbers) {
		int firstNegativeIndex = numbers.indexOf(negativeSign);
		int delimiterAfterFirstNegativeIndex = numbers.indexOf(delimiter, firstNegativeIndex);
		
		if (delimiterAfterFirstNegativeIndex < 0)
			return firstNegativeNumber(numbers);
		
		String numbersAfterFirstNegative = numbers.substring(delimiterAfterFirstNegativeIndex + 1);
		if (numbersAfterFirstNegative.contains(negativeSign))
			return firstNegativeNumber(numbers) + ", " + listOfNegativeNumbers(numbersAfterFirstNegative);
		else
			return firstNegativeNumber(numbers);
	}

	private String firstNegativeNumber(String numbers) {
		int firstNegativeIndex = numbers.indexOf(negativeSign);
		int delimiterAfterFirstNegativeIndex = numbers.indexOf(delimiter, firstNegativeIndex);
		if (delimiterAfterFirstNegativeIndex < 0)
			delimiterAfterFirstNegativeIndex = numbers.length();
		
		return numbers.substring(firstNegativeIndex, delimiterAfterFirstNegativeIndex);
	}

}
