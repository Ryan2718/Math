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
    
end
