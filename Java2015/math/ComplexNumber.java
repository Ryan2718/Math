package math;

/** Class for a complex number. This class is immutable.
 * @author Ryan Forsyth (02/15/2015)
 */
public class ComplexNumber {
	/** The real part */
	private final Fraction REAL;
	
	/** The imaginary part */
	private final Fraction IMAGINARY;
	
	/** Constructor for a complex number.
	 * @param real The real part
	 * @param imaginary The imaginary part
	 */
	public ComplexNumber(Fraction real, Fraction imaginary) {
		REAL = real;
		IMAGINARY = imaginary;
	}
	
	/** Constructor for a real number represented as a complex number.
	 * @param real The real number
	 */
	public ComplexNumber(Fraction real) {
		this(real, new Fraction(0));
	}
	
	/** Constructor for an integer represented as a complex number.
	 * @param num The integer
	 */
	public ComplexNumber(int num) {
		this(new Fraction(num));
	}
	
	/** Add a complex number to the current complex number.
	 * @param cn The other complex number
	 * @return The sum of the complex numbers
	 */
	public ComplexNumber add(ComplexNumber cn) {
		// (a + bi) + (c + di) = (a + c) + (b + d)i
		Fraction real = REAL.add(cn.REAL);
		Fraction imaginary = IMAGINARY.add(cn.IMAGINARY);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/** Subtract a complex number from the current complex number.
	 * @param cn The other complex number
	 * @return The difference of the complex numbers
	 */
	public ComplexNumber subtract(ComplexNumber cn) {
		// (a + bi) - (c + di) = (a + c) - (b + d)i
		Fraction real = REAL.subtract(cn.REAL);
		Fraction imaginary = IMAGINARY.subtract(cn.IMAGINARY);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/** Multiply a complex number with the current complex number.
	 * @param cn The other complex number
	 * @return The product of the complex numbers
	 */
	public ComplexNumber multiply(ComplexNumber cn) {
		/* (a + bi)(c + di) = ac + (ad + bc)i + bdi^2
		 * 				    = ac + (ad + bc)i - bd
		 * 					= (ac - bd) + (ad + bc)i
		 */
		Fraction a = REAL;
		Fraction b = IMAGINARY;
		Fraction c = cn.REAL;
		Fraction d = cn.IMAGINARY;
		
		Fraction real = (a.multiply(c)).subtract(b.multiply(d));
		Fraction imaginary = (a.multiply(d)).subtract(b.multiply(c));
		
		return new ComplexNumber(real, imaginary);
	}
	
	/** Divide a complex number by the current complex number.
	 * @param cn The other complex number
	 * @return The quotient of the complex numbers
	 */
	public ComplexNumber divide(ComplexNumber cn) {
		// See http://mathworld.wolfram.com/ComplexDivision.html
		Fraction a = REAL;
		Fraction b = IMAGINARY;
		Fraction c = cn.REAL;
		Fraction d = cn.IMAGINARY;
		
		Fraction r1 = a.multiply(c);
		Fraction r2 = b.multiply(d);
		
		Fraction i1 = b.multiply(c);
		Fraction i2 = a.multiply(d);
		
		Fraction d1 = c.multiply(c);
		Fraction d2 = d.multiply(d);
		Fraction denominator = d1.add(d2);
		
		Fraction real = r1.subtract(r2).divide(denominator);
		Fraction imaginary = i1.subtract(i2).divide(denominator);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/** Find the complex conjugate of the current complex number.
	 * @return The complex conjugate
	 */
	public ComplexNumber complexConjugate() {
		Fraction weight = new Fraction(-1, 0);
		return new ComplexNumber(REAL, weight.multiply(IMAGINARY));
	}
	
	/** Determine if the complex number is actually real.
	 * @return True if the real part is non-zero and the imaginary part is zero.
	 * False otherwise.
	 */
	public boolean isReal() {
		return (IMAGINARY.equals(new Fraction(0))) &&
				(!REAL.equals(new Fraction(0)));
	}
	
	/** Determine if the complex number is actually imaginary.
	 * @return True if the imaginary part is non-zero and the real part is zero. 
	 * False otherwise.
	 */
	public boolean isImaginary() {
		return (REAL.equals(new Fraction(0))) &&
				(!IMAGINARY.equals(new Fraction(0)));
	}
	
	/** Determine if the current number is 0.
	 * @return True if it is zero and false otherwise.
	 */
	public boolean isZero() {
		return (REAL.equals(new Fraction(0))) &&
				(IMAGINARY.equals(new Fraction(0)));
	}
	
	/** Determine if the current number has both real and imaginary parts.
	 * @return True if both the real and imaginary parts are nonzero. False
	 * otherwise.
	 */
	public boolean isComplex() {
		return (!REAL.equals(new Fraction(0))) &&
				(!IMAGINARY.equals(new Fraction(0)));
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof ComplexNumber) {
			ComplexNumber cn = (ComplexNumber) o;
			return (REAL.equals(cn.REAL)) && (IMAGINARY.equals(cn.IMAGINARY));
		} else {
			return false;
		}
	}
	
	public String toString() {
		String string = "";
		String realString = REAL.toString();
		String imaginaryString = isReal() ? "(" + IMAGINARY + ")i" : 
																IMAGINARY + "i";
		if (isReal()) {
			string = realString;
		} else if (isImaginary()) {
			string = imaginaryString;
		} else if (isComplex()) {
			string = realString + " + " + imaginaryString;
		} else if (isZero()) {
			string = "0";
		}
		return string;
	}
	
	/** Returns the full string representation of the complex number.
	 * @return The full string
	 */
	public String fullString() {
		return REAL + " + (" + IMAGINARY + ")i";
	}

}
