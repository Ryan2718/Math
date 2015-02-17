package math;

/** Class for a Fraction. This class is immutable.
 * @author Ryan Forsyth (02/15/2015)
 */
public class Fraction {
	
	/** The numerator of the fraction. */
	private final int NUMERATOR;
	
	/** The denominator of the fraction. */
	private final int DENOMINATOR;
	
	/** Constructor for a fraction. 
	 * Fraction objects are stored in simplified form.
	 * @param numerator The numerator
	 * @param denominator The denominator
	 */
	public Fraction(int numerator, int denominator) {
		if (denominator == 0) {
			throw new RuntimeException("Zero Denominator!");
		}
		
		int sign = 1;
		int a = numerator;
		int b = denominator;
		
		if (numerator < 0) {
			sign *= -1;
			a *= -1;
		}
		if (denominator < 0) {
			sign *= -1;
			b *= -1;
		}
		
		if (a != 0) {
			int gcd = MathMethods.gcd(a, b);

			a /= gcd;
			b /= gcd;
		}
		
		if (a == 0) {
			b = 1;
		}
		
		NUMERATOR = sign * a;
		DENOMINATOR = b;
	}
	
	/** Constructor for a fraction representation of an integer.
	 * @param numerator The integer
	 */
	public Fraction(int numerator) {
		this(numerator, 1);
	}
	
	
	/** Add another fraction to the current fraction.
	 * @param f The other fraction
	 * @return The sum of the fractions
	 */
	public Fraction add(Fraction f) {
		// a/b + c/d = (ad + bc)/(bd)
		int a = NUMERATOR;
		int b = DENOMINATOR;
		int c = f.NUMERATOR;
		int d = f.DENOMINATOR;
		
		return new Fraction(a * d + b * c, b * d);
	}
	
	/** Multiply another fraction with the current fraction.
	 * @param f The other fraction
	 * @return The product of the fractions
	 */
	public Fraction multiply(Fraction f) {
		// a/b * c/d = (ac)/(bd)
		int a = NUMERATOR;
		int b = DENOMINATOR;
		int c = f.NUMERATOR;
		int d = f.DENOMINATOR;
		
		return new Fraction(a * c, b * d);
	}
	
	/** Subtract another fraction from the current fraction.
	 * @param f The other fraction
	 * @return The difference of the fractions
	 */
	public Fraction subtract(Fraction f) {
		// a/b - c/d = (ad - bc)/(bd)
		int a = NUMERATOR;
		int b = DENOMINATOR;
		int c = f.NUMERATOR;
		int d = f.DENOMINATOR;
		
		return new Fraction(a * d - b * c, b * d);
	}
	
	/** Divide another fraction by this fraction.
	 * @param f The other fraction
	 * @return The quotient of the fractions
	 */
	public Fraction divide(Fraction f) {
		// (a/b) / (c/d) = (a/b)(d/c) = (ad)/(bc)
		int a = NUMERATOR;
		int b = DENOMINATOR;
		int c = f.NUMERATOR;
		int d = f.DENOMINATOR;
		
		return new Fraction(a * d, b * c);
	}
	
	/** Determine the reciprocal of the fraction.
	 * @return The reciprocal
	 */
	public Fraction reciprocal() {
		return new Fraction(DENOMINATOR, NUMERATOR);
	}
	
	/** Determine if the fraction is actually an integer.
	 * @return True if the fraction is an integer and false otherwise
	 */
	public boolean isInteger() {
		return (DENOMINATOR == 1) || (NUMERATOR == 0);
	}
	
	public boolean equals(Object o) {
		// Note that fractions are always stored in simplified form.
		if (o == this) {
			return true;
		} else if (o instanceof Fraction) {
			Fraction f = (Fraction) o;
			if ((NUMERATOR == f.NUMERATOR) && (DENOMINATOR == f.DENOMINATOR)) {
				return true;
			} else if ((NUMERATOR == f.NUMERATOR) && NUMERATOR == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		String string = "";
		string += NUMERATOR;
		if (!isInteger()) {
			string += "/" + DENOMINATOR;
		}
		return string;
	}
	
	/** Returns the full string representation of the fraction.
	 * @return The full string
	 */
	public String fullString() {
		return NUMERATOR + "/" + DENOMINATOR;
	}
}
