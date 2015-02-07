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
  end
    
end
