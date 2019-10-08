import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {

		// Unit tests:
		AdditionAndMultiplication unitTest = new AdditionAndMultiplication();
		int[] num1 = {9, 9, 9};
		int[] num2 = {9, 9, 9};

		// sum
		System.out.println(Arrays.toString(unitTest.sum(num1, num2)));
		// multiply
		System.out.println(Arrays.toString(unitTest.multiply(num1, num2)));

		// Start the server:
		Server test = new Server();
		try {
			test.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
