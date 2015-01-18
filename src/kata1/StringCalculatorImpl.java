package kata1;

public class StringCalculatorImpl implements StringCalculator {
	public final String delimiter = ",";
	public final String newline = "\n";
	public final String customDelimiterPrefix = "//";
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
			return Integer.parseInt(numbers);
	}

	private int firstNumber(String numbers) {
		int delimiterIndex = numbers.indexOf(delimiter);

		return Integer.parseInt(numbers.substring(0, delimiterIndex));
	}

	private String remainingNumbers(String numbers) {
		int delimiterIndex = numbers.indexOf(delimiter);
		int delimiterLength = 1;

		return numbers.substring(delimiterIndex + delimiterLength);
	}

	private String standardizeDelimiters(String customDelimitedNumbers) {
		String standardDelimitedNumbers;
		if (customDelimitedNumbers.startsWith(customDelimiterPrefix)) {
			String customDelimiter = customDelimitedNumbers.substring(2, 3);
			String numbersOnly = customDelimitedNumbers.substring(customDelimitedNumbers.indexOf(newline) + 1);
			standardDelimitedNumbers = numbersOnly.replaceAll(customDelimiter, delimiter);
		} else
			standardDelimitedNumbers = customDelimitedNumbers.replaceAll(newline, delimiter);

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
