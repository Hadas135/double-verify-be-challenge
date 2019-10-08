
/**
 * Author: Hadas Ben Mordechai
 * 
 * The mainly implementation class.
 * This class realizes the functionality needed to connect and multiply numbers, 
 * using the FFT algorithm.
 */

public class AdditionAndMultiplication {
	/**
	 * The function that triggers the addition operation.
	 * The function takes two long numbers ('firstNum' and 'secondNum'), 
	 * splits them into an int array and calls the method that summarizes them, 
	 * then converts the int array back to a long number and returns it.
	 */
	public long add (long firstNum, long secondNum) {
		int[] calculation = sum (splitIntoArray(firstNum), splitIntoArray(secondNum));
		return HornerRule(calculation);
	}

	/**
	 * The function that triggers the multiplication operation.
	 * The function takes two long numbers ('firstNum' and 'secondNum'), 
	 * splits them into an int array and calls the method that multiplies them, 
	 * then converts the int array back to a long number and returns it.
	 */
	public long mult (long firstNum, long secondNum) {
		int[] calculation = multiply (splitIntoArray(firstNum), splitIntoArray(secondNum));
		return HornerRule(calculation);
	}

	// Addition of two long numbers:
	public int[] sum (int[] firstNum, int[] secondNum) {

		// Compare the size of the arrays, before adding:
		if (firstNum.length > secondNum.length)
			secondNum = increaseArray (secondNum, secondNum.length, firstNum.length);
		else if (firstNum.length < secondNum.length)
			firstNum = increaseArray (firstNum, firstNum.length, secondNum.length);

		// The adding:
		int arrLength = firstNum.length;
		int[] sumArr = new int[arrLength];
		int digit, rest = 0;

		for (int i = arrLength - 1; i >= 0; i--) {
			digit = firstNum[i] + secondNum[i] + rest;
			rest = 0;

			if (digit > 9) {
				rest = digit / 10;
				digit = digit % 10;
			}

			sumArr[i] = digit;
		}

		// If the numbers requires increasing the number of digits:
		if (rest != 0) {
			sumArr = increaseArray (sumArr, arrLength, arrLength + 1);
			sumArr[0] = rest;
		}

		return sumArr;
	}

	// Multiplication of two long numbers, using FFT:
	public int[] multiply (int[] firstNum, int[] secondNum) {

		// Preparing the array for FFT algorithm, which requires a number of digits which is a power of 2:
		int numsLength = firstNum.length;
		if (secondNum.length > numsLength)
			numsLength = secondNum.length;

		int check = isPowerOfTwo(numsLength);
		if (check != -1) // Increase the array
			numsLength = (int) Math.pow(2, check);

		// Adding "unnecessary zeros":
		firstNum = increaseArray (firstNum, firstNum.length, numsLength * 2);
		secondNum = increaseArray (secondNum, secondNum.length, numsLength * 2);

		// Multiply, using FFT algorithm:
		int[] multiplyArr = multiplicationUsingFFT (firstNum, secondNum);
		return multiplyArr;
	}

	// Increase 'arrayToIncrease' array from size 'oldSize' to size 'newSize', by adding "unnecessary zeros":
	private int[] increaseArray (int [] arrayToIncrease, int oldSize, int newSize) {
		int[] newArr = new int [newSize];
		for (int i = newSize - 1, j = oldSize - 1; j >= 0; i--, j--)
			newArr[i] = arrayToIncrease[j];
		return newArr;
	}

	/** Check if the size is power of two (for FFT algorithm).
	 * If so, returns -1.
	 * Otherwise, returns the necessary power of 2 that we will need.
	 */
	private int isPowerOfTwo (int num) {
		if (num == 0)
			return 0;

		int ceil = (int)(Math.ceil((Math.log(num) / Math.log(2))));
		int floor =  (int)(Math.floor(((Math.log(num) / Math.log(2)))));

		if (ceil != floor)
			return ceil;
		else
			return -1;
	}

	// Multiply, using FFT algorithm:
	private int[] multiplicationUsingFFT (int[] firstCoefficientsArray, int[] secondCoefficientsArray) {

		int i, j;
		int len = firstCoefficientsArray.length;

		// Convert the values from real numbers to composite numbers with imaginary = 0:
		Complex[] firstNumAsComplex = new Complex[len];
		Complex[] secondNumAsComplex = new Complex[len];
		for (i  = 0, j = len - 1; i < len; i++, j--) {
			firstNumAsComplex[i] = new Complex(firstCoefficientsArray[j], 0);
			secondNumAsComplex[i] = new Complex(secondCoefficientsArray[j], 0);
		}

		// Run FFT algorithm on the coefficients arrays:
		FFT firstFFT = new FFT(firstNumAsComplex, 1);
		FFT secondFFT = new FFT(secondNumAsComplex, 1);

		// Point to point multiplication:
		Complex[] multByPoints = new Complex[len];
		for (i = 0; i < multByPoints.length; i++)
			multByPoints[i] = firstFFT.getAnswer()[i].multiply(secondFFT.getAnswer()[i]);

		// Run FFT_-1 algorithm to get the new coefficient array:
		FFT reverseFFT = new FFT(multByPoints, -1);
		Complex[] reverseCoefficientsArr = reverseFFT.getAnswer();
		len = reverseCoefficientsArr.length;

		// Convert the values from composite numbers back to real numbers:
		int[] finalAnswer = new int[len];
		for (i = 0, j = len - 1; i < len; i++, j--)
			finalAnswer[i] = (int) (reverseCoefficientsArr[j].getReal() / len);

		// Convert the values to the required output format:
		finalAnswer = removeZeros(arrangeFFTarray(finalAnswer));

		return finalAnswer;
	}

	// Conversion of the resulting array ('arr') from the FFT algorithm to a long array:
	private int[] arrangeFFTarray (int[] arr) {
		int[] answer = new int[arr.length];
		int rest = 0;
		for(int i = arr.length - 1; i >= 0; i--) {
			arr[i] += rest;
			answer[i] = arr[i] % 10;
			rest = arr[i] / 10;
		}
		return answer;
	}

	// Delete "unnecessary zeros" from 'arr':
	private int[] removeZeros (int[] arr) {
		int i, j;
		for (i = 0; i < arr.length && arr[i] == 0; i++);

		int[] answer = new int[arr.length - i];
		for (j = 0; i < arr.length; i++, j++)
			answer[j] = arr[i];

		return answer;
	}

	// The number 'coefficientsArray' at base 10, according to Horner rule:
	private long HornerRule (int[] coefficientsArray) {
		long num = 0;
		int i = 0;
		while (i < coefficientsArray.length) {
			num = num * 10 + coefficientsArray[i];
			i++;
		}
		return num;
	}

	// Split a number 'num' into array (each digit is a cell in the array):
	private int[] splitIntoArray (long num) {
		long number = num;
		int len;
		for (len = 0; number > 0; len++)
			number = number / 10;

		int[] digitsArr = new int[len];
		for (int i = len - 1; i >= 0; i--) {
			digitsArr[i] = (int)(num % 10);
			num = num / 10;
		}
		return digitsArr;
	}

}



