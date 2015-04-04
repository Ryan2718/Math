# Matrix
Ryan Forsyth

Code representing a matrix, implemented in various languages.

I think it's cool to compare doing the same task in different programming languages. Each language has its own advantages and disadvantages. In this repository, the common task is performing matrix operations.

## C

Positives
  * Efficient / Fast

Negatives
  * Have to deal with dynamic memory allocation manually
  * The pointer syntax is annoying. For example, `void matrix_print(Matrix *A)` declares that `A` is a pointer. Within the function however, `*A` would mean "get the value A points at" NOT "get A". The syntax within the function would suggest you are actually passing in a dereferenced pointer to the function
  * Lack of interactive environment. Requires compiling to test

## Common Lisp

Positives
  * Common Lisp is very multi-paradigm, with support for object-oriented, imperative, and functional programming. It is interesting to see all these styles implemented in the same language. Being dynamically typed, Common Lisp does have some similarites to scripting languages (compare the Common Lisp "add-imperative-mutate" method to the Python "add" method or Ruby "add!" method). The functional style of course has some similarities to Haskell (compare the Common Lisp "add-functional" method to the Haskell "add" function).
  * The REPL makes trying out code pretty easy

Negatives
  * I've found the REPL to be a bit lacking on details of an error. Sometimes it's hard to track down where an error occurs. Most errors aren't even found until runtime. Ocassionally, the REPL refuses to even load a file with an error, but usually it loads fine and the programmer is left thinking the code works until calling that one function with an error.
  * There's quite a few annoying syntax issues
    * Lots of Irritating Silly Parentheses (on the other hand, it's cool to think of a Lisp program as nothing but lists)
    * I have yet to find a way to do partial functional application as cleanly as in Haskell. Compare:
      Common Lisp (from "add-functional"):
      ```
      (mapcar (lambda (x y) (mapcar #'+ x y)) 
      ```
      Haskell (from "add"):
      ```
      zipWith (zipWith (+))
      ```
      Notice that the Common Lisp implementation requires a lambda expression or else the REPL declares that some function doesn't have enough arguments. The Common Lisp implementation also requires the `#'` before the function `+`, which I find awkward.

## Haskell

Positives
  * Haskell code is very concise - just 9 lines of code to do matrix multiplication (compare to Python's 14 or Java's 19)
  * Being compiled, Haskell catches many errors at compile-time
  * The interactive environment (ghci) makes trying out code much easier than in most compiled languages
  * Haskell is the only pure functional programming language I know of, which requires the programmer to completely leave the realm of imperative programming (or at least as much as possible)
  * Type inference - Haskell is statically typed, but one does not need to specify the types
  * Lazy evaluation means things like infinite lists are possible

Negatives
  * It is often difficult to determine how to implement an algorithm with pure functions. For instance, many mathematics algorithms are described procedurally. One has to rethink the algorithm in terms of functions as opposed to steps. Perhaps this is a skill that is picked up with further functional programming.
    
## Java

Positives
  * Large standard library
  * Static typing
  * Very good IDE - Eclipse

Negatives
  * Lack of interactive environment

## Python

Positives
  * Interactive environment
  * Optional and keyword arguments to functions
  * Multi-paradigm: procedural, object-oriented, functional
  * Large standard library plus additional libraries (i.e. SciPy, Natural Language Toolkit)

Negatives
  * Dynamic typing can lead to some runtime errors

## Ruby

Positives
  * Interactive environment

Negatives
  * Needing to put `end` after if-statements and loops

## My Favorites

Languages I feel most comfortable writing code in (i.e. what I would write a large project in):

1. Java
2. Python
3. Ruby
4. C
5. Haskell
6. Common Lisp

Languages I most enjoy writing code in (i.e. what I like experimenting with):

1. Haskell (Functional programming is really cool)
2. Common Lisp (Multi-paradigm, its use in AI)
3. Python (Executable pseudo-code, multi-paradigm)
4. Ruby (I rarely write Ruby code, but it's so similar to Python code...)
5. Java
6. C
