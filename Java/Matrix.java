// Written: June 22,2014
// Updated June 27, 2014

public class Matrix {
	private final double[][] ENTRIES;
	private final int M, N;
	private static final NotRectangleException NOT_A_RECTANGLE =
			new NotRectangleException();
	private static final NotSquareException NOT_SQUARE =
			new NotSquareException();
	private static final NotSameSizeException NOT_SAME_SIZE = 
			new NotSameSizeException();
	private static final CannotMultiplyException CANT_MULTIPLY = 
			new CannotMultiplyException();
	private static final ZeroMatrixException ZERO_MATRIX = 
			new ZeroMatrixException();
			
	/*Constructors*/
	public Matrix(double[][] entries) throws NotRectangleException {
		ENTRIES = entries;
		M = entries.length;
		N = entries[0].length;
		int colNum;
		for (int index = 0; index < entries.length; index++) {
			colNum = entries[index].length;
			if (colNum != N) {
				throw NOT_A_RECTANGLE;
			}
		}
	}
	public Matrix(Matrix A) {
		ENTRIES = A.getEntries();
		M = A.getNumRows();
		N = A.getNumCols();
	}
	
	/*Getter Methods*/
	public double[][] getEntries() {
		return ENTRIES;
	}
	public int getNumRows() {
		return M;
	}
	public int getNumCols() {
		return N;
	}
	
	/*Special Methods*/
	public String toString() {
		String formattedMatrix = "";
		for (int row = 0; row < M; row++) {
			for (int col = 0; col < N; col++) {
				formattedMatrix += (ENTRIES[row][col] + "\t");
			}
			formattedMatrix += "\n";
		}
		return formattedMatrix;
	}
	
	public boolean equals(Matrix B) {
		final double EPSILON = 0.00000001;
		double entriesDifference;
		for (int row = 0; row < M; row++) {
			for (int col = 0; col < N; col++) {
				entriesDifference = ENTRIES[row][col] - 
				B.getEntries()[row][col];
				if (Math.abs(entriesDifference) >= EPSILON) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/*Methods for a single matrix */
	public boolean isSquare() {
		boolean square = (M == N);
		return square;
	}

	public Matrix scalarMultiple(double scalar) throws 
			NotRectangleException {
		double[][] entriesOfA = new double[M][N];
		for (int row = 0; row < M; row++) {
			for (int col=0; col < N; col++) {
				entriesOfA[row][col] = (ENTRIES[row][col])*scalar;
			}
		}
		Matrix A = new Matrix(entriesOfA);
		return A;
	}
	
	public Matrix transpose() throws NotRectangleException {
		double[][] entriesOfTranspose = new double[N][M];
		for (int row = 0; row < N; row++) {
			for (int col=0; col < M; col++) {
				entriesOfTranspose[row][col] = ENTRIES[col][row];
			}
		}
		Matrix transpose = new Matrix(entriesOfTranspose);
		return transpose;
	}
	
	public double determinant() 
			throws NotRectangleException, NotSquareException {
		double determinant = 0;
		double scalar1, scalar2, scalar, det;
		if (isSquare()) {
			if (M == 2) {
				determinant = 
						((ENTRIES[0][0])*
						(ENTRIES[1][1])) -
						((ENTRIES[0][1])*
						(ENTRIES[1][0]));
				return determinant;
			}
			Matrix B = removeRow(1);
			Matrix C;
			for (int col = 0; col < N; col++) {
				C = B.removeColumn(col);
				scalar1 = Math.pow(-1, 1+col);
				scalar2 = ENTRIES[1][col];
				scalar = scalar1*scalar2;
				det = C.determinant();
				determinant += scalar*det;
			}
			return determinant;
		}
		throw NOT_SQUARE;
	}
	
	public int pivotCol() throws ZeroMatrixException {
		for (int col = 0; col < N; col++) {
			for (int row = 0; row < M; row++) {
				if (ENTRIES[row][col] != 0) {
					return col;
				}
			}
		}
		throw ZERO_MATRIX;
	}
	
	public Matrix replaceRow(int rowToBeChanged, 
			int otherRow, double multiple) 
			throws NotRectangleException {
		double[][] entriesOfB = new double[M][N];
		entriesOfB = ENTRIES;
		double[] addedTerm = scaleSingleRow(otherRow, multiple);
		for (int col = 0; col < N; col++) {
			entriesOfB[rowToBeChanged][col] += addedTerm[col];
		}
		Matrix B = new Matrix(entriesOfB);
		return B;
	}
	
	public Matrix interchangeRows(int row1, int row2) 
		throws NotRectangleException {
		double[][] entriesOfSwitchedRow = new double[M][N];
		for (int row = 0; row < M; row++) {
			for (int col = 0; col < N; col++) {
				if (row == row1) {
					entriesOfSwitchedRow[row][col] = ENTRIES[row2][col];
				} else if (row == row2) {
					entriesOfSwitchedRow[row][col] = ENTRIES[row1][col];
				} else {
					entriesOfSwitchedRow[row][col] = ENTRIES[row][col];
				}
			}
		}
		Matrix switchedRow = new Matrix(entriesOfSwitchedRow);
		return switchedRow;
	}
	
	public Matrix scaleRow(int rowToBeScaled, double multiple)
		throws NotRectangleException {
		double[][] entriesOfB = new double [M][N];
		entriesOfB = ENTRIES;
		entriesOfB[rowToBeScaled] = scaleSingleRow(rowToBeScaled, multiple);
		Matrix B = new Matrix(entriesOfB);
		return B;
	}
	
	private Matrix removeColumn(int column) throws NotRectangleException {
		double[][] entriesOfB = new double[M][N-1];
		for (int row = 0; row < M; row ++) {
			for (int col = 0; col < column; col++) {
				entriesOfB[row][col] = ENTRIES[row][col];
			}
			for (int col = column; col < N-1; col++) {
				entriesOfB[row][col] = ENTRIES[row][col + 1];
			}
		}
		Matrix B = new Matrix(entriesOfB);
		return B;
	}
	
	private Matrix removeRow(int rowRemoved) throws NotRectangleException {
		double[][] entriesOfB = new double[M-1][N];
		for (int col = 0; col < N; col ++) {
			for (int row = 0; row < rowRemoved; row++) {
				entriesOfB[row][col] = ENTRIES[row][col];
			}
			for (int row = rowRemoved; row < M-1; row++) {
				entriesOfB[row][col] = ENTRIES[row + 1][col];
			}
		}
		Matrix B = new Matrix(entriesOfB);
		return B;
	}

	private double[] scaleSingleRow(int rowToBeScaled, double multiple) {
		double[] entriesOfScaledRow = new double[N];
		for (int col = 0; col < N; col++) {
			entriesOfScaledRow[col] = multiple*ENTRIES[rowToBeScaled][col];
		}
		return entriesOfScaledRow;
	}
	
	/*Static Methods */
	public static boolean sameSize(Matrix A, Matrix B) {
		boolean rowsMatch = (A.getNumRows() == B.getNumRows());
		boolean columnsMatch = (A.getNumCols() == B.getNumCols());
		boolean sizesMatch = rowsMatch && columnsMatch;
		return sizesMatch;
	}
	
	public static boolean canMultiply(Matrix A, Matrix B) {
		boolean canMultiply = (A.getNumCols() == B.getNumRows());
		return canMultiply;
	}
	
	public static Matrix add(Matrix A, Matrix B) 
			throws NotRectangleException, NotSameSizeException {

		double[][] entriesOfC = new double[A.getNumRows()][A.getNumCols()];
		if (sameSize(A,B)){
			for (int row = 0; row < A.getNumRows(); row++) {
				for (int col=0; col < A.getNumCols(); col++) {
					entriesOfC[row][col] = A.getEntries()[row][col] +
							B.getEntries()[row][col];
				}
			}
			Matrix C = new Matrix(entriesOfC);
			return C;
		}
		throw NOT_SAME_SIZE;
	}
	
	public static Matrix subtract(Matrix A, Matrix B) 
			throws NotRectangleException, NotSameSizeException {
		double[][] entriesOfC = new double[A.getNumRows()][A.getNumCols()];
		if (sameSize(A, B)){
			for (int row = 0; row < A.getNumRows(); row++) {
				for (int col=0; col < A.getNumCols(); col++) {
					entriesOfC[row][col] = A.getEntries()[row][col] -
							B.getEntries()[row][col];
				}
			}
			Matrix C = new Matrix(entriesOfC);
			return C;
		}
		throw NOT_SAME_SIZE;
	}
	
	public static Matrix multiplyEntries(Matrix A, Matrix B) 
			throws NotRectangleException, NotSameSizeException {
		double[][] entriesOfC = new double[A.getNumRows()][A.getNumCols()];
		if (sameSize(A,B)) {
			for (int row = 0; row < A.getNumRows(); row++) {
				for (int col=0; col < A.getNumCols(); col++) {
					entriesOfC[row][col] = A.getEntries()[row][col] *
							B.getEntries()[row][col];
				}
			}
			Matrix C = new Matrix(entriesOfC);
			return C;
		}
		throw NOT_SAME_SIZE;
	}
	
	public static Matrix divideEntries(Matrix A, Matrix B) 
			throws NotRectangleException, NotSameSizeException {
		double[][] entriesOfC = new double[A.getNumRows()][A.getNumCols()];
		if (sameSize(A,B)) {
			for (int row = 0; row < A.getNumRows(); row++) {
				for (int col=0; col < A.getNumCols(); col++) {
					entriesOfC[row][col] = A.getEntries()[row][col] /
							B.getEntries()[row][col];
				}
			}
			Matrix C = new Matrix(entriesOfC);
			return C;
		}
		throw NOT_SAME_SIZE;
	}
	
	public static Matrix multiply(Matrix A, Matrix B) 
			throws NotRectangleException, CannotMultiplyException {
		double[][] entriesOfC = new double[A.getNumRows()][B.getNumCols()];
		double f;
		double n = A.getNumCols();
		if (canMultiply(A,B)) {
			for (int row = 0; row < A.getNumRows(); row++) {
				for (int col=0; col < B.getNumCols(); col++) {
					f = 0;
					for (int index = 0; index < n; index++) {
						f += (A.getEntries()[row][index])*
								(B.getEntries()[index][col]);
					}
					entriesOfC[row][col] = f;		
				}
			}
			Matrix C = new Matrix(entriesOfC);
			return C;
		}
		throw CANT_MULTIPLY;
	}
	
}
