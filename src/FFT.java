/**
 * Author: Hadas Ben Mordechai
 * 
 * The purpose of this class is to get a complex numbers array, 
 * and execute the FFT algorithm (or FFT_-1 algorithm) on it.
 */
public class FFT {

	private int direction; // The direction; FFT or FFT_-1
	private Complex[] omega; // Contains the relevant n-unit roots
	private Complex[] answer; // Contains the answer - the array after FFT algorithm

	/* Constructor:
	 * Get: arrInput - the original array, on which the FFT algorithm will be executed.
	 * 		omegaFormulaDirection - The direction: 1 for FFT and -1 for FFT_-1.
	 * */
	public FFT (Complex[] arrInput, int direction) {
		this.direction = direction;
		createOmega (arrInput.length);
		answer = runFFTalgorithm (arrInput, arrInput.length, 1);
	}

	// Initialization the relevant n-unit roots array:
	private void createOmega (int len) {
		omega = new Complex[len];
		for (int i = 0 ; i < omega.length; i++)
			omega[i] = new Complex(Math.cos(2*Math.PI*i/(len)), direction * Math.sin(2*Math.PI*i/(len)));
	}

	// Recursive function to calculate Fourier transform (using the unit roots):
	private Complex[] runFFTalgorithm (Complex[] c, int len, int pow) {

		if (len == 1)
			return c;

		// Divide the array into two arrays- even and odd:
		Complex[] even = new Complex[len / 2];
		Complex[] odd = new Complex[len / 2];
		for (int i = 0; i < len/2; i++) {
			even[i] = c[2 * i];
			odd[i] = c[2 * i + 1];
		}

		// Recursive, with half of the array length and twice in the omega location:
		Complex[] fEven = runFFTalgorithm (even, len / 2, 2 * pow);
		Complex[] fOdd = runFFTalgorithm (odd, len / 2, 2 * pow);

		// Combining the solutions:
		Complex[] union = new Complex[len];

		for (int i = 0; i < len / 2; i++) {
			Complex calc = omega[i * pow].multiply(fOdd[i]);
			union[i] = fEven[i].add(calc);
			union[i + (len / 2)] = fEven[i].sub(calc);
		}

		// Return the result - the union:
		return union;
	}

	public Complex[] getAnswer() {
		return answer;
	}

}
