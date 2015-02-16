# Written: 01/31/2015

from fractions import Fraction
from numbers import Number

class Matrix():
    """
    Defines a Matrix
    Ryan Forsyth
    2015
    """
    
    # Redefining Built-in Functions    
    
    def __init__(self, values):
        """
        Constructor for a matrix object.
        Pass in arguments as a 2D list.
        """
        self.values = values
        self.m = len(self.values) # num_rows
        self.n = len(self.values[0]) # num_cols
    
    def __repr__(self):
        return str(self)
        
    def __str__(self):
        """
        String representation of the matrix.
        """
        string = ""
        for row in range(self.m):
            for col in range(self.n):
                value = self.values[row][col]
                valueString = str(value)
                string += takeNspaces(valueString, 5) + "  "
            string += "\n"
        return string
        
    def __eq__(self, other):
        """
        Equals Method
        """
        for row in range(self.m):
            for col in range(self.n):
                if self.values[row][col] != other.values[row][col]:
                    return False
        return True
    
    def __getitem__(self, num):
        """
        Indexes the matrix.
        """
        return self.values[num]
    
    # Copying a Matrix
    
    def copy(self):
        """
        A deep copy of the matrix.
        """
        newValues = []
        for row in range(self.m):
            newEntries = []
            for col in range(self.n):
                newEntries.append(self.values[row][col])
            newValues.append(newEntries)
        return Matrix(newValues)
    
    # Operator overloading
    
    def __add__(self, other):
        """
        Add two matrices
        """
        return self.add(other)
    
    def __mul__(self, other):
        """
        Multiply two matrices or multiply a matrix by a scalar.
        """
        if isinstance(other, self.__class__):
            return self.matrixMultiply(other)
        elif isinstance(other, Number):
            return self.scalarMultiply(other)
    
    def __rmul__(self, other):
        """
        Multiply a matrix by a scalar.
        """
        if isinstance(other, Number):
            return self.scalarMultiply(other)
    
    # Identity
    
    def identity(n):
        """
        The n x n identity matrix.
        """
        values = []
        for row in range(n):
            entries = []
            for col in range(n):
                if row == col:
                    entries.append(1)
                else:
                    entries.append(0)
            values.append(entries)
        return Matrix(values)
    
    # Elementary Matrices
    
    def rowSwitchE(row1, row2, m):
        """
        Elementary matrix for switching rows.
        """
        I = Matrix.identity(m)
        return I.rowSwitch(row1, row2)
    
    def rowMultiplyE(row, weight, m):
        """
        Elementary matrix for multiplying a row by a scalar.
        """
        I = Matrix.identity(m)
        return I.rowMultiply(row, weight)
    
    def rowAddE(row1, row2, weight, m):
        """
        Elementary matrix for adding a multiple of another row to a row.
        """
        I = Matrix.identity(m)
        return I.rowAdd(row1, row2, weight)
    
    # Elementary Row Operations
    
    def rowSwitch(self, row1, row2):
        """
        Elementary Row Operation
        Permute the matrix by switching row1 with row2
        """
        A = self.copy()
        row1 = row1 - 1 # zero-indexed
        row2 = row2 - 1 # zero-indexed
        for col in range(A.n):
            temp = A.values[row1][col]
            A.values[row1][col] = A.values[row2][col]
            A.values[row2][col] = temp
        return A
    
    def rowMultiply(self, row , weight):
        """
        Elementary Row Operation
        Multiply row by weight
        """
        A = self.copy()
        row = row - 1 # zero-indexed
        for col in range(A.n):
            A.values[row][col] = weight * A.values[row][col]
        return A

    def rowAdd(self, row1, row2, weight):
        """
        Elementary Row Operation
        Add weight * row2 to row1
        """
        A = self.copy()
        row1 = row1 - 1 # zero-indexed
        row2 = row2 - 1 # zero-indexed
        for col in range(A.n):
            A.values[row1][col] = (A.values[row1][col] + 
                                    weight * A.values[row2][col])
        return A
    
    # Gaussian Elimination
    
    def ref(self, logger=False):
        """
        Put the matrix into row echelon form
        """
        A = self.copy()
        pivotRow = 0
        for col in range(A.n): # For every column
            if pivotRow < A.m and A.values[pivotRow][col] == 0: # Short-circuit the operator
                switchTo = A.m                
                while A.values[A.m - 1][col] != 0 and pivotRow + 1 != switchTo:
                    A = A.rowSwitch(pivotRow + 1, switchTo)
                    switchTo -= 1
                    if logger:
                        print(A)
            if pivotRow < A.m and A.values[pivotRow][col] != 0:
                for lowerRow in range(pivotRow + 1, A.m):
                    weight = -1 * A.values[lowerRow][col] * Fraction(1, A.values[pivotRow][col])
                    A = A.rowAdd(lowerRow + 1, pivotRow + 1, weight)
                    if logger:
                        print(A)
                pivotRow += 1
        return A
    
    def rref(self, logger=False):
        """
        Put the matrix into reduced row echelon form
        """
        A = self.ref(logger)
        pivotRow = A.m - 1
        pivotCols = A.pivotCols() # Start from the right hand side
        pivotCols.reverse()
        for col in pivotCols: # For every pivot column
            col = col - 1 # Go back to zero indexing
            while pivotRow >= 0 and A.values[pivotRow][col] == 0: # Short-circuit the operator
                pivotRow -= 1 # Go up to the next row
            for upperRow in range(pivotRow - 1, -1, -1):
                weight = -1 * A.values[upperRow][col] * Fraction(1, A.values[pivotRow][col])
                A = A.rowAdd(upperRow + 1, pivotRow + 1, weight)
                if logger:
                    print(A)
            pivotRow -= 1
        return A
    
    def specialrref(self, logger=False):
        """
        Put the matrix into reduced row echelon form with 1s along the diagonal
        """
        A = self.rref(logger)
        pivotCols = A.pivotCols()
        col = 0
        for row in range(A.rank()):
            weight = Fraction(1, A.values[row][pivotCols[col] - 1]) # Go back to zero-indexing
            A = A.rowMultiply(row + 1, weight)
            if logger:
                print(A)
            col += 1
        return A
    
    def pivotCols(self):
        """
        Determine where the pivot columns are.
        """
        pivotCols = []
        A = self.ref() # Pivots will be first non-zero entry in each row
        col = 0
        for row in range(A.m):
            while col < A.n and A.values[row][col] == 0: # short-circuit the operator
                col += 1
            if col < A.n:
                pivotCols.append(col + 1) # zero-indexed
        return pivotCols
     
    def rank(self):
        """
        The rank of the matrix.
        """
        return len(self.pivotCols())
        
    # The inverse
        
    def inverse(self):
        """
        The inverse of the current matrix.
        """
        A = self.copy()
        if A.m != A.n:
            raise MatrixException("Non-Square Matrix!")
        I = Matrix.identity(A.n)
        AI = A.augment(I)
        AI = AI.specialrref()
        split = AI.split(A.n)
        if (split[0] == I):
            return split[1]
        else:
            raise MatrixException("Not invertible!")
    
    # The transpose
    
    def transpose(self):
        """
        The transpose of the current matrix.
        A m x n
        => A* n x m
        """
        A = self.copy()
        rows = []
        for col in range(A.n):
            entries = []
            for row in range(A.m):
                entries.append(A.values[row][col])
            rows.append(entries)
        return Matrix(rows)
    
    # The determinant
    
    def determinant(self):
        """
        See Lay's textbook p. 165
        The determinant of the current matrix.
        """
        A = self.copy()
        if A.m != A.n:
            raise MatrixException("Non-Square Matrix!")
        elif A.n == 1:
            return A.values[0][0]
        else:
            det = 0
            for j in range(1, A.n + 1):
                w1 = (-1) ** (1 + j)
                w2 = A.values[0][j - 1] # account for zero-indexing
                w3 = A.subMatrix(1, j).determinant()
                det +=  w1 * w2 * w3
            return det
            
    def subMatrix(self, rowRemoved, colRemoved):
        """
        Remove the row and column from this matrix
        """
        # Get back to zero-indexing
        rowRemoved -= 1
        colRemoved -= 1
        A = self.copy()
        rows = []
        for row in range(A.m):
            if row != rowRemoved:
                entries = []
                for col in range(A.n):
                    if col != colRemoved:
                        entries.append(A.values[row][col])
                rows.append(entries)
        return Matrix(rows)
        
    # Matrix Operations
        
    def add(self, B):
        """
        Adds two matrices
        """
        A = self.copy()
        if A.m != B.m or A.n != B.n:
            raise MatrixException("These matrices cannot be added!")
        for row in range(A.m):
            for col in range(A.n):
                A.values[row][col] += B.values[row][col]
        return A
    
    def scalarMultiply(self, c):
        """
        Multiplies a matrix by a scalar
        """
        A = self.copy()
        for row in range(A.m):
            for col in range(A.n):
                A.values[row][col] *= c
        return A
    
    def matrixMultiply(self, B):
        """
        Multiplies two matrices
        A m x n
        B n x r
        => C m x r
        """
        A = self.copy()
        if A.n != B.m:
            raise MatrixException("These matrices cannot be multiplied!")
        values = []
        for row in range(A.m):
            entries = []
            for col in range(B.n):
                # Applied Linear Algebra by Olver p.6
                entry = 0
                for k in range(A.n):
                    entry += A.values[row][k] * B.values[k][col]
                entries.append(entry)
            values.append(entries)
        return Matrix(values)
                
        
    # Adding columns, splitting columns
    
    def augment(self, b):
        """
        Augments the matrix by adding columns.
        """
        A = self.copy()
        if not A.m == b.m:
            raise MatrixException("The matrices do not have the same number of rows")
        rows = []
        for row in range(A.m): # for every row of self
            newRow = A.values[row]
            for col in range(b.n):
                newRow.append(b.values[row][col])
            rows.append(newRow)
        A.n += b.n # increase the number of columns
        A.values = rows
        return A
    
    def split(self, lastN):
        """
        Splits the matrix into two matrices.
        One m x (n - N)
        One m x N
        """
        A = self.copy()
        rows1 = []
        rows2 = []
        for row in range(A.m):
            newRow1 = []
            for col in range(A.n - lastN):
                newRow1.append(A.values[row][col])
            rows1.append(newRow1)
            
            newRow2 = []
            for col in range(A.n - lastN, A.n):
                newRow2.append(A.values[row][col])
            rows2.append(newRow2)
        return (Matrix(rows1), Matrix(rows2))
    
        
class MatrixException(Exception):
    pass

# Stand - Alone Function

def takeNspaces(string, N):
    """ 
    Makes sure a string takes up N spaces.
    """
    l = len(string)
    s = N - l # The number of blank spaces needed
    newString = ""
    for i in range(s):
        newString += " "
    newString += string
    return newString
