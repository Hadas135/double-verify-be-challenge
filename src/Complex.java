
public class Complex {

	private double real;
	private double imag;

	public Complex (double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex add (Complex c) {
		return new Complex (real + c.real, imag + c.imag);
	}

	public Complex sub(Complex c) {
		return new Complex(real - c.real, imag - c.imag);
	}

	public Complex multiply (Complex c) {
		return new Complex (real * c.real - imag * c.imag, real * c.imag + imag * c.real);
	}

	public String toString() {
		return String.format("%8.4f  + %8.4fi", real, imag);
	}

	public double getReal() {
		return (float)(real);
	}

}
