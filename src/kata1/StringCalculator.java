package kata1;

public interface StringCalculator {
	
	/**
	 * The method can take 0, 1 or 2 numbers, and will return their sum (for an empty string it will return 0) for example “” or “1” or “1,2”.
	 * 
	 * @param numbers is the String of numbers to sum
	 * @return the sum of the numbers
	 * @throws Exception 
	 */
	public int add(String numbers) throws Exception;

}
