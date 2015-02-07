import static org.junit.Assert.*;
import org.junit.Test;

public class MatrixTester {
	
	@Test(expected = NotRectangleException.class)
	public void testRaggedMatrix() throws NotRectangleException {
		double[][] entriesOfA = {{1,2}, {4,5,6}, {7,8,9}};
		new Matrix(entriesOfA);
	}
	
	@Test
	public void testToString1() throws NotRectangleException {
		double[][] entriesOfA = {{1,2,3}, {4,5,6}, {7,8,9}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfA);
		assertTrue((A.toString()).equals(B.toString()));
	}
	
	@Test
	public void testToString2() throws NotRectangleException {
		double[][] entriesOfA = {{1,2}, {3, 4}};
		Matrix A = new Matrix(entriesOfA);
		String output = "1.0\t2.0\t\n3.0\t4.0\t\n";
		assertTrue((A.toString()).equals(output));
	}

	@Test
	public void testEquals1() throws NotRectangleException {
		double[][] entriesOfA = {{1,2,3}, {4,5,6}, {7,8,9}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfA);
		assertTrue(A.equals(B));
	}
	
	@Test
	public void testEquals2() throws NotRectangleException {
		//do divideEntries and see if equals still works
		//it may not because of roundoff errors.
		
		double[][] entriesOfA = {{4,7,3}, {2, 5, 6}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = A.scalarMultiple(0.5);
		double[][] entriesOfC = {{2, 3.5, 1.5}, {1, 2.5, 3}};
		Matrix C = new Matrix(entriesOfC);
		assertTrue(B.equals(C));
	}
	
	@Test
	public void testEquals3() 
			throws NotRectangleException, NotSameSizeException {
		double[][] entriesOfA = {{1.5, 3.2, 4.77}};
		double[][] entriesOfB = {{1.4, 3.1, 4.67}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfB);
		Matrix C = Matrix.subtract(A,B);
		double[][] entriesOfD = {{0.1, 0.1, 0.1}};
		Matrix D = new Matrix(entriesOfD);
		assertTrue(D.equals(C));
	}
	
	@Test
	public void testIsSquare1() throws NotRectangleException {
		double[][] entriesOfA = {{1,2,3}, {4,5,6}, {7, 8, 9}};
		Matrix A = new Matrix(entriesOfA);
		assertTrue(A.isSquare());
	}
	
	@Test
	public void testIsSquare2() throws NotRectangleException {
		double[][] entriesOfA = {{1,2,3}, {4,5,6}};
		Matrix A = new Matrix(entriesOfA);
		assertFalse(A.isSquare());
	}
	
	@Test
	public void testScalarMultiple() throws NotRectangleException {
		double[][] entriesOfA = { {1,2,3}, {4,5,6}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = A.scalarMultiple(5);
		double[][] entriesOfC = {{5,10,15}, {20,25,30}};
		Matrix C = new Matrix(entriesOfC);
		assertTrue(B.equals(C));
	}
	
	@Test
	public void testTranspose() throws NotRectangleException {
		//Linear Algebra Textbook: Page 99, Example 8
		double[][] entriesOfA = {{-5,2}, {1,-3}, {0,4}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = A.transpose();
		double[][] entriesOfC = {{-5,1,0}, {2,-3,4}};
		Matrix C = new Matrix(entriesOfC);
		assertTrue(B.equals(C));
	}
	
	@Test(expected = NotSquareException.class)
	public void testNotSquareMatrix()
			throws NotRectangleException, NotSquareException {
		double[][] entriesOfA = {{1,2}, {3,4}, {5,6}};
		Matrix A = new Matrix(entriesOfA);
		A.determinant();
	}
	
	@Test
	public void testDeterminant1() 
			throws NotRectangleException, NotSquareException {
		//Linear Algebra Textbook: Page 165, Example 1
		double[][] entriesOfA = {{1,5,0}, {2,4,-1}, {0,-2,0}};
		Matrix A = new Matrix(entriesOfA);
		double det = A.determinant();
		assertTrue(det == -2);
	}
	
	@Test
	public void testDeterminant2() 
			throws NotRectangleException, NotSquareException {
		//Linear Algebra Textbook: Page 168, Exercise 13
		double[][] entriesOfA = {{4,0,-7,3,-5}, {0,0,2,0,0}, {7,3,-6,4,-8},
				{5,0,5,2,-3},{0,0,9,-1,2}};
		Matrix A = new Matrix(entriesOfA);
		double det = A.determinant();
		assertTrue(det == 6);
	}
	
	@Test(expected = ZeroMatrixException.class)
	public void testZeroMatrix() 
			throws NotRectangleException, ZeroMatrixException {
		double[][] entriesOfA = {{0,0,0}, {0,0,0}};
		Matrix A = new Matrix(entriesOfA);
		A.pivotCol();
	}
	
	@Test
	public void pivotCol()
			throws NotRectangleException, ZeroMatrixException {
		//Linear Algebra Textbook: Page 15, Example 3
		double[][] entriesOfA = {{0,3,-6,6,4,-5}, {3,-7,8,-5,8,9}, 
				{3,-9,12,-9,6,15}};
		Matrix A = new Matrix(entriesOfA);
		int pivotCol = A.pivotCol();
		assertTrue(pivotCol == 0);
	}
	
	@Test(expected = NotSameSizeException.class)
	public void testNotSameSize() 
			throws NotRectangleException, NotSameSizeException {
		double[][] entriesOfA = {{2,3,4}, {1,-5,10}};
		double[][] entriesOfB = {{4,3,6},{1,-2,3}, {7,8,9}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfB);
		Matrix.add(A, B);
	}
	
	@Test(expected = CannotMultiplyException.class)
	public void testCannotMultiply() 
			throws NotRectangleException, CannotMultiplyException {
		double[][] entriesOfA = {{2,3,4}, {1,-5,10}};
		double[][] entriesOfB = {{4,3,6},{1,-2,3}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfB);
		Matrix.multiply(A, B);
	}
	
	@Test
	public void testMultiply() 
			throws NotRectangleException, CannotMultiplyException {
		//Linear Algebra Textbook: Page 95, Example 3
		double[][] entriesOfA = {{2,3}, {1,-5}};
		double[][] entriesOfB = {{4,3,6},{1,-2,3}};
		Matrix A = new Matrix(entriesOfA);
		Matrix B = new Matrix(entriesOfB);
		Matrix C = Matrix.multiply(A, B);
		double[][] entriesOfD = {{11,0,21}, {-1,13,-9}};
		Matrix D = new Matrix(entriesOfD);
		assertTrue(C.equals(D));
	}
	

}
