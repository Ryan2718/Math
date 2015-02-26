package math;

/** Static method for doing math.
 * @author Ryan Forsyth (02/15/2015)
 *
 */
public class MathMethods {

	/** Determine the greatest common divisor of two positive numbers.
	 * @param a The first number
	 * @param b The second number
	 * @return The greatest common divisors of the numbers
	 */
	public static long gcd(long a, long b) {
		/* See Wikipedia page "Greatest Common Divisor" - 
		 * "Using Euclid's Algorithm"
		 */
		if (a <= 0 || b <= 0) {
			throw new RuntimeException("Parameters must be positive!");
		} else if (a == b) {
			return a;
		} else if (a > b) {
			return gcd(a - b, b);
		} else { // b > a
			return gcd(a, b - a);
		}
	}
}
