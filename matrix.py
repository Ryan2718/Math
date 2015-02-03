from fractions import Fraction

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
