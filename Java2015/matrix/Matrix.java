package matrix;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import math.ComplexNumber;
import math.Fraction;

/* TODO:
 * basis for the range (pivot columns)
 * basis for the co-range (range of the transpose)
 * 
 * Make the palu method work correctly
 */

/** Class for a matrix. A matrix is a rectangular 2D array of numbers.
 * This class is immutable.
 * @author Ryan Forsyth (02/15/2015)
 */
public class Matrix {
	
	/** The spacing of the matrix when printed out. */
	private static final int SPACING = 5;
	
	/** The rows of the matrix. */
	private final ComplexNumber[][] ROWS;
	
	/** The number of rows. */
	private final int M;
	
	/** The number of columns. */
	private final int N;

	/** Constructor for a matrix with complex number entries.
	 * @param rows The rows of the matrix.
	 */
	public Matrix(ComplexNumber[][] rows) {
		ROWS = rows;
		M = rows.length;
		N = rows[0].length;
		for (int row = 0; row < M; row++) {
			if (rows[row].length != N) {
				throw new RuntimeException("Non-Rectangular Matrix!");
			}
		}
	}
	
	/** Constructor for a matrix with fraction entries.
	 * @param rows The rows of the matrix
	 */
	public Matrix(Fraction[][] rows) {
		ComplexNumber[][] complexRows = new ComplexNumber[rows.length][];
		for (int row = 0; row < rows.length; row++) {
			complexRows[row] = new ComplexNumber[rows[row].length];
			for (int col = 0; col < rows[row].length; col++) {
				complexRows[row][col] = new ComplexNumber(rows[row][col]);
			}
		}
		ROWS = complexRows;
		M = complexRows.length;
		N = complexRows[0].length;
		for (int row = 0; row < M; row++) {
			if (complexRows[row].length != N) {
				throw new RuntimeException("Non-Rectangular Matrix!");
			}
		}
	}
	
	/** Constructor for a matrix with integer entries.
	 * @param rows The rows of the matrix.
	 */
	public Matrix(int[][] rows) {
		ComplexNumber[][] complexRows = new ComplexNumber[rows.length][];
		for (int row = 0; row < rows.length; row++) {
			complexRows[row] = new ComplexNumber[rows[row].length];
			for (int col = 0; col < rows[row].length; col++) {
				Fraction real = new Fraction(rows[row][col]);
				Fraction imaginary = new Fraction(0);
				complexRows[row][col] = new ComplexNumber(real, imaginary);
			}
		}
		ROWS = complexRows;
		M = complexRows.length;
		N = complexRows[0].length;
		for (int row = 0; row < M; row++) {
			if (complexRows[row].length != N) {
				throw new RuntimeException("Non-Rectangular Matrix!");
			}
		}
	}
	
	/** Elementary row operation: Multiply a row by a scalar.
	 * @param row The row
	 * @param weight The scalar
	 * @return The resulting matrix
	 */
	public Matrix rowMultiply(int row, ComplexNumber weight) {
		// row *= weight
		Matrix m = copy();
		for (int col = 0; col < m.N; col++) {
			m.ROWS[row][col] = weight.multiply(m.ROWS[row][col]);
		}
		return m;
	}
	
	/** Elementary row operation: Multiply a row by a scalar.
	 * @param row The row
	 * @param weight The scalar
	 * @return The resulting matrix
	 */
	public Matrix rowMultiply(int row, Fraction weight) {
		return rowMultiply(row, new ComplexNumber(weight));
	}
	
	/** Elementary row operation: Multiply a row by a scalar.
	 * @param row The row
	 * @param weight The scalar
	 * @return The resulting matrix
	 */
	public Matrix rowMultiply(int row, int weight) {
		return rowMultiply(row, new ComplexNumber(new Fraction(weight)));
	}
	
	/** Elementary row operation: switch two rows.
	 * @param rowI One row
	 * @param rowJ The other row
	 * @return The resulting matrix
	 */
	public Matrix rowSwitch(int rowI, int rowJ) {
		Matrix m = copy();
		ComplexNumber[] temp = m.ROWS[rowI];
		m.ROWS[rowI] = m.ROWS[rowJ];
		m.ROWS[rowJ] = temp;
		return m;
	}
	
	/** Elementary row operation: add a multiple of one row to another.
	 * @param rowI The row being changed
	 * @param rowJ The row being multiplied by
	 * @param weight The weight
	 * @return The resulting matrix
	 */
	public Matrix rowAdd(int rowI, int rowJ, ComplexNumber weight) {
		// rowI += weight * rowJ
		Matrix m = copy();
		for (int col = 0; col < m.N; col++) {
			ComplexNumber term = weight.multiply(m.ROWS[rowJ][col]);
			m.ROWS[rowI][col] = m.ROWS[rowI][col].add(term);
		}
		return m;
	}
	
	/** Elementary row operation: add a multiple of one row to another.
	 * @param rowI The row being changed
	 * @param rowJ The row being multiplied by
	 * @param weight The weight
	 * @return The resulting matrix
	 */
	public Matrix rowAdd(int rowI, int rowJ, Fraction weight) {
		return rowAdd(rowI, rowJ, new ComplexNumber(weight));
	}
	
	/** Elementary row operation: add a multiple of one row to another.
	 * @param rowI The row being changed
	 * @param rowJ The row being multiplied by
	 * @param weight The weight
	 * @return The resulting matrix
	 */
	public Matrix rowAdd(int rowI, int rowJ, int weight) {
		return rowAdd(rowI, rowJ, new ComplexNumber(new Fraction(weight)));
	}
	
	/** Adds two matrices.
	 * @param b The other matrix
	 * @return The sum of the matrices
	 */
	public Matrix add(Matrix b) {
		Matrix a = copy();
		if (a.M != b.M || a.N != b.N) {
			throw new RuntimeException("These matrices cannot be added!");
		}
		for (int row = 0; row < a.M; row++) {
			for (int col = 0; col < a.N; col++) {
				a.ROWS[row][col] = a.ROWS[row][col].add(b.ROWS[row][col]);
			}
		}
		return a;
	}
	
	public Matrix multiply(Matrix b) {
		Matrix a = copy();
		if (a.N != b.M) {
			throw new RuntimeException("These matrices cannot be multiplied!");
		}
		ComplexNumber[][] rows = new ComplexNumber[a.M][];
		for (int row = 0; row < a.M; row++) {
			rows[row] = new ComplexNumber[b.N];
			for (int col = 0; col < b.N; col++) {
				// Applied Linear Algebra Olver p.6
				rows[row][col] = new ComplexNumber(new Fraction(0));
				for (int k = 0; k < a.N; k++) {
					ComplexNumber term = 
							a.ROWS[row][k].multiply(b.ROWS[k][col]);
					rows[row][col] = rows[row][col].add(term);
				}
			}
		}
		return new Matrix(rows);
	}
	
	/** Multiplies the matrix by the given complex number.
	 * @param cn The complex number
	 * @return The matrix multiplied by that number
	 */
	public Matrix multiply(ComplexNumber cn) {
		Matrix a = copy();
		for (int row = 0; row < a.M; row++) {
			for (int col = 0; col < a.N; col++) {
				a.ROWS[row][col] = a.ROWS[row][col].multiply(cn);
			}
		}
		return a;
	}
	
	/** Multiplies the matrix by the given fraction
	 * @param f The fraction
	 * @return The matrix multiplied by that number
	 */
	public Matrix multiply(Fraction f) {
		return multiply(new ComplexNumber(f));
	}
	
	/** Multiplies the matrix by the given integer
	 * @param i The integer
	 * @return The matrix multiplied by that integer
	 */
	public Matrix multiply(int i) {
		return multiply(new Fraction(i));
	}
	
	/** Augments two matrices.
	 * @param b The other matrix
	 * @return The augmented matrix
	 */
	public Matrix augment(Matrix b) {
		Matrix a = copy();
		if (a.M != b.M) {
			throw new RuntimeException("Thse matrices cannot be augmented!");
		}
		ComplexNumber[][] rows = new ComplexNumber[a.M][];
		for (int row = 0; row < a.M; row++) {
			rows[row] = new ComplexNumber[a.N + b.N];
			int newCol = 0;
			for (int col = 0; col < a.N; col++) {
				rows[row][newCol] = a.ROWS[row][col];
				newCol++;
			}
			for (int col = 0; col < b.N; col++) {
				rows[row][newCol] = b.ROWS[row][col];
				newCol++;
			}
		}
		return new Matrix(rows);
	}
	
	/** Splits the matrix.
	 * @param lastN The last N columns that should form its own matrix
	 * @return The two matrices resulting from the split
	 */
	public Matrix[] split(int lastN) {
		Matrix a = copy();
		int firstN = a.N - lastN;
		ComplexNumber[][] rows1 = new ComplexNumber[a.M][];
		ComplexNumber[][] rows2 = new ComplexNumber[a.M][];
		for (int row = 0; row < a.M; row++) {
			int oldCol = 0;
			
			rows1[row] = new ComplexNumber[firstN];
			for (int col = 0; col < firstN; col++) {
				rows1[row][col] = a.ROWS[row][oldCol];
				oldCol++;
			}
			
			rows2[row] = new ComplexNumber[lastN];
			for (int col = 0; col < lastN; col++) {
				rows2[row][col] = a.ROWS[row][oldCol];
				oldCol++;
			}
		}
		Matrix[] array = {new Matrix(rows1), new Matrix(rows2)};
		return array;
	}
	
	/** Finds the inverse of the matrix.
	 * @return The matrix's inverse
	 */
	public Matrix inverse() {
		Matrix a = copy();
		if (a.M != a.N) {
			throw new RuntimeException("This matrix is not square!");
		}
		Matrix i = identity(a.N);
		Matrix ai = a.augment(i);
		ai = ai.specialrref();
		Matrix[] split = ai.split(a.N);
		if (split[0].equals(i)) {
			return split[1];
		} else {
			throw new RuntimeException("This matrix is not invertible!");
		}
	}
	
	/** Finds the permuted LU factorization of the matrix. PA = LU
	 * @return P, A, L, U
	 */
	public Matrix[] palu() {
		HashMap<Integer, Integer> permutations = new HashMap<Integer,Integer>();
		Matrix m = copy();
		int pivotRow = 0;
		for (int col = 0; col < m.N; col++) {
			if (pivotRow < m.M) {
				int switchTo = m.M - 1;
				while (pivotRow != switchTo && 
						m.ROWS[pivotRow][col].equals(new ComplexNumber(0))) {
					m = m.rowSwitch(pivotRow, switchTo);
					permutations.put(pivotRow, switchTo);
					switchTo--;
				}
				if (!m.ROWS[pivotRow][col].equals(new ComplexNumber(0))) {
					// We got a non-zero pivot
					for (int lowerRow = pivotRow + 1; lowerRow < m.M;
																lowerRow++) {
						ComplexNumber factor1 = new ComplexNumber(-1);
						ComplexNumber factor2 = m.ROWS[lowerRow][col];
						ComplexNumber factor3 = m.ROWS[pivotRow][col];
						ComplexNumber factor4 = new ComplexNumber(1);
						ComplexNumber factor5 = factor1.multiply(factor2);
						ComplexNumber factor6 = factor4.divide(factor3);
						ComplexNumber weight = factor5.multiply(factor6);

						m = m.rowAdd(lowerRow, pivotRow, weight);
					}
					pivotRow++;
					/* Keep the same pivot row if we currently have a
					 * zero-pivot. Move on to see if there's a pivot in the
					 * next column.
					 */
				}	
			}
		}
		
		
		
		Matrix p = identity(m.M);
		for (Integer rowI : permutations.keySet()) {
			p.rowSwitch(rowI, permutations.get(rowI));
		}
		Matrix l = identity(m.M);
		Matrix u = p.multiply(copy());
		
		pivotRow = 0;
		for (int col = 0; col < u.N; col++) {
			if (pivotRow < u.M) {
				// Should not have to do any permutations
				if (!u.ROWS[pivotRow][col].equals(new ComplexNumber(0))) {
					// We got a non-zero pivot
					for (int lowerRow = pivotRow + 1; lowerRow < u.M;
																lowerRow++) {
						ComplexNumber factor1 = new ComplexNumber(-1);
						ComplexNumber factor2 = u.ROWS[lowerRow][col];
						ComplexNumber factor3 = u.ROWS[pivotRow][col];
						ComplexNumber factor4 = new ComplexNumber(1);
						ComplexNumber factor5 = factor1.multiply(factor2);
						ComplexNumber factor6 = factor4.divide(factor3);
						ComplexNumber weight = factor5.multiply(factor6);

						u = u.rowAdd(lowerRow, pivotRow, weight);
						l = l.rowAdd(lowerRow, pivotRow, weight);
					}
					pivotRow++;
					/* Keep the same pivot row if we currently have a
					 * zero-pivot. Move on to see if there's a pivot in the
					 * next column.
					 */
				}	
			}
		}
		
		l = l.inverse();
		Matrix[] palu = {p, this, l, u};
		return palu;
	}
	
	/** Puts the matrix into row echelon form.
	 * @return row echelon form of the matrix
	 */
	public Matrix ref() {
		Matrix m = copy();
		int pivotRow = 0;
		for (int col = 0; col < m.N; col++) {
			if (pivotRow < m.M) {
				int switchTo = m.M - 1;
				while (pivotRow != switchTo && 
						m.ROWS[pivotRow][col].equals(new ComplexNumber(0))) {
					m = m.rowSwitch(pivotRow, switchTo);
					switchTo--;
				}
				if (!m.ROWS[pivotRow][col].equals(new ComplexNumber(0))) {
					// We got a non-zero pivot
					for (int lowerRow = pivotRow + 1; lowerRow < m.M;
																lowerRow++) {
						ComplexNumber factor1 = new ComplexNumber(-1);
						ComplexNumber factor2 = m.ROWS[lowerRow][col];
						ComplexNumber factor3 = m.ROWS[pivotRow][col];
						ComplexNumber factor4 = new ComplexNumber(1);
						ComplexNumber factor5 = factor1.multiply(factor2);
						ComplexNumber factor6 = factor4.divide(factor3);
						ComplexNumber weight = factor5.multiply(factor6);

						m = m.rowAdd(lowerRow, pivotRow, weight);
					}
					pivotRow++;
					/* Keep the same pivot row if we currently have a
					 * zero-pivot. Move on to see if there's a pivot in the
					 * next column.
					 */
				}	
			}
		}
		return m;
	}
	
	/** Puts the matrix into reduced row echelon form.
	 * @return reduced row echelon form of the matrix
	 */
	public Matrix rref() {
		Matrix m = ref();
		int pivotRow = m.M - 1;
		ArrayList<Integer> pivotColumns = m.pivotColumns();
		Collections.reverse(pivotColumns);
		for (int i = 0; i < pivotColumns.size(); i++) {
			int pivotCol = pivotColumns.get(i);
			while (pivotRow >= 0 && m.ROWS[pivotRow][pivotCol].equals(new ComplexNumber(0))) {
				pivotRow--;
			}
			for (int upperRow = pivotRow - 1; upperRow > -1; upperRow--) {
				ComplexNumber factor1 = new ComplexNumber(-1);
				ComplexNumber factor2 = m.ROWS[upperRow][pivotCol];
				ComplexNumber factor3 = m.ROWS[pivotRow][pivotCol];
				ComplexNumber factor4 = new ComplexNumber(1);
				ComplexNumber factor5 = factor1.multiply(factor2);
				ComplexNumber factor6 = factor4.divide(factor3);
				ComplexNumber weight = factor5.multiply(factor6);

				m = m.rowAdd(upperRow, pivotRow, weight);
			}
			pivotRow--;
		}
		return m;
	}
	
	public Matrix specialrref() {
		Matrix m = rref();
		ArrayList<Integer> pivotColumns = pivotColumns();
		int i = 0;
		for (int row = 0; row < rank(); row++) {
			ComplexNumber numerator = new ComplexNumber(1);
			ComplexNumber denominator = m.ROWS[row][pivotColumns.get(i)];
			ComplexNumber weight = numerator.divide(denominator);
			m = m.rowMultiply(row, weight);
			i++;
		}
		return m;
	}
	
	public ArrayList<Integer> pivotColumns() {
		ArrayList<Integer> pivotColumns = new ArrayList<Integer>();
		Matrix m = ref();
		int col = 0;
		for (int row = 0; row < m.M; row++) {
			while (col < m.N && m.ROWS[row][col].equals(new ComplexNumber(0))) {
				col++;
			}
			if (col < m.N) {
				pivotColumns.add(col);
			}
		}
		return pivotColumns;
	}
	
	/** Determines the rank of the matrix. 
	 * @return The rank (i.e. the number of pivot columns)
	 */
	public int rank() {
		return pivotColumns().size();
	}
	
	/** Creates an n x n identity matrix.
	 * @param n The size of the matrix
	 * @return The n x n identity
	 */
	public static Matrix identity(int n) {
		ComplexNumber[][] rows = new ComplexNumber[n][];
		for (int row = 0; row < n; row++) {
			rows[row] = new ComplexNumber[n];
			for (int col = 0; col < n; col++) {
				int term = (row == col) ? 1 : 0;
				rows[row][col] = new ComplexNumber(term);
			}
		}
		return new Matrix(rows);
	}
	
	/** Creates a deep copy of the current matrix.
	 * @return The deep copy
	 */
	public Matrix copy() {
		ComplexNumber[][] rows = new ComplexNumber[M][];
		for (int row = 0; row < M; row++) {
			rows[row] = new ComplexNumber[N];
			for (int col = 0; col < N; col++) {
				rows[row][col] = ROWS[row][col];
			}
		}
		return new Matrix(rows);
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			for (int row = 0; row < M; row++) {
				for (int col = 0; col < N; col++) {
					if (!ROWS[row][col].equals(m.ROWS[row][col])) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		String string = "";
		for (int row = 0; row < M; row++) {
			for (int col = 0; col < N; col++) {
				String entry = ROWS[row][col].toString();
				int numSpaces = SPACING - entry.length();
				for (int i = 0; i < numSpaces; i++) {
						string += " ";
				}
				string += entry;
			}
			string += "\n";
		}
		string += "\n";
		return string;
	}
	
	/** Prints our the matrix. */
	public void print() {
		System.out.println(toString());
	}
	
	/** Finds the elementary matrix for switching two rows.
	 * @param row1 One of the rows
	 * @param row2 The other row
	 * @param m The number of rows in A
	 * @return The elementary matrix such that multiplying it by A will be the
	 * same as switching the two specified rows of A.
	 */
	public static Matrix rowSwitchE(int row1, int row2, int m) {
		return identity(m).rowSwitch(row1, row2);
	}
	
	/** Finds the elementary matrix for scaling a row.
	 * @param row The row to be scaled
	 * @param weight The weight
	 * @param m The number of rows in A
	 * @return The elementary matrix such that multiplying it by A will be the
	 * same as scaling the specified row of A by the given weight.
	 */
	public static Matrix rowMultiplyE(int row, ComplexNumber weight, int m) {
		return identity(m).rowMultiply(row, weight);
	}
	
	/** Finds the elementary matrix for adding a multiple of one row to
	 * another.
	 * @param row1 The row being added to
	 * @param row2 The other row
	 * @param weight The weight of the other row
	 * @param m The number of rows in A
	 * @return The elementary matrix such that multiplying it by A will be the
	 * same as adding a multiple of row2 of A weighted by weight to row1 of A.
	 */
	public static Matrix rowAddE(int row1, int row2, ComplexNumber weight, 
								 int m) {
		return identity(m).rowAdd(row1, row2, weight);
	}
	
	/** Finds the transpose of the current matrix.
	 * @return The transpose
	 */
	public Matrix transpose() {
		Matrix a = copy();
		ComplexNumber[][] values = new ComplexNumber[a.N][];
		for (int col = 0; col < a.N; col++) {
			values[col] = new ComplexNumber[a.M];
			for (int row = 0; row < a.M; row++) {
				values[col][row] = a.ROWS[row][col]; 	
			}
		}
		return new Matrix(values);
	}
	
	/** Finds the determinant of the matrix.
	 * @return The determinant
	 */
	public ComplexNumber determinant() {
		// See Lay's textbook (p.165)
		if (M != N) {
			throw new RuntimeException("Non-Square Matrix!");
		} else if (N == 1) {
			return ROWS[0][0];
		} else {
			ComplexNumber det = new ComplexNumber(0);
			for (int j = 1; j <= N; j++) {
				ComplexNumber w1 = new ComplexNumber((int) Math.pow(-1, 1 + j));
				ComplexNumber w2 = ROWS[0][j - 1]; // Account for zero-indexing
				ComplexNumber w3 = subMatrix(0, j - 1).determinant();
				ComplexNumber term = w1.multiply(w2).multiply(w3);
				det = det.add(term);
			}
			return det;
		}
	}
	
	/** Creates a sub-matrix by removing a row and column of the current
	 * matrix.
	 * @param rowRemoved The row to be removed.
	 * @param colRemoved The column to be removed.
	 * @return The sub-matrix
	 */
	public Matrix subMatrix(int rowRemoved, int colRemoved) {
		ComplexNumber[][] values = new ComplexNumber[M - 1][];
		int newRow = 0;
		for (int row = 0; row < M; row++) {
			if (row != rowRemoved) {
				values[newRow] = new ComplexNumber[N - 1];
				int newCol = 0;
				for (int col = 0; col < N; col++) {
					if (col != colRemoved) {
						values[newRow][newCol] = ROWS[row][col];
						newCol++;
					}
				}
				newRow++;
			}
		}
		return new Matrix(values);
	}
	
}
