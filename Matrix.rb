# Written: 02/06/2015

class Matrix
  attr_accessor :values
  attr_accessor :m
  attr_accessor :n
  
  def initialize(values)
    @values = values
    @m = values.length
    @n = values[0].length
  end
  
  def display
    for row in 0...@m
      for col in 0...@n
        print @values[row][col].to_s
        print " "
      end
      print "\n"
    end
    print "\n"
  end
  
  def copy
    newValues = []
    for row in 0...@m
      newEntries = []
      for col in 0...@n
        newEntries.push(@values[row][col])
      end
      newValues.push(newEntries)
    end
    return Matrix.new(newValues)
  end
  
  def transpose
    rows = []
    for col in 0...@n
      entries = []
      for row in 0...@m
        entries.push(@values[row][col])
      end
      rows.push(entries)
    end
    return Matrix.new(rows)
  end
  
  def add(b)
    a = copy
    return a.add!(b)
  end
  
  def add!(b)
    if @m != b.m || @n != b.n
      raise "These matrices cannot be added!"
    end
    for row in 0...@m
      for col in 0...@n
        @values[row][col] += b.values[row][col]
      end
    end
    return self
  end
  
  def scalarMultiply(c)
    a = copy
    return a.scalarMultiply!(c)
  end
  
  def scalarMultiply!(c)
    for row in 0...@m
      for col in 0...@n
        @values[row][col] *= c
      end
    end
    return self
  end
  
  def matrixMultiply(b)
    if @n != b.m
      raise "These matrices cannot be multiplied!"
    end
    values = []
    for row in 0...@m
      entries = []
      for col in 0...b.n
        # Applied Linear Algebra by Olver p.6
        entry = 0
        for k in 0...@n
          entry += @values[row][k] * b.values[k][col]
        end
        entries.push(entry)
      end
      values.push(entries)
    end
    return Matrix.new(values)
  end

  def determinant
    # Lay's textbook p. 165
    if @m != @n
      raise "Non-Square Matrix!"
    elsif @n == 1
      return @values[0][0]
    else
      det = 0
      for j in 1..@n
        w1 = (-1) ** (1 + j)
        w2 = @values[0][j - 1] # Account for zero-indexing
        w3 = subMatrix(1, j).determinant
        det += w1 * w2 * w3
      end
      return det
    end
  end
  
  def subMatrix(rowRemoved, colRemoved)
    # Account for zero-indexing
    rowRemoved -= 1
    colRemoved -= 1
    rows = []
    for row in 0...@m
      if row != rowRemoved
        entries = []
        for col in 0...@n
          if col != colRemoved
            entries.push(@values[row][col])
          end
        end
        rows.push(entries)
      end
    end 
    return Matrix.new(rows) 
  end
    
end
