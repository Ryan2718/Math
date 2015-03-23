import Matrix

testShow01 =
    show (Matrix [[1,2],[3,4]] 2 2) == "[1,2]\n[3,4]\n"
    
testShow02 =
    show (Matrix [[1.0, 2.0], [3.0, 4.0]] 2 2) == "[1.0,2.0]\n[3.0,4.0]\n"
    
testShow03 =
    show (Matrix [[1, 2, 3], [4, 5, 6.0]] 3 2) == "[1.0,2.0,3.0]\n[4.0,5.0,6.0]\n"
    
testEq01 =
    (Matrix [[1,2],[3,4]] 2 2) == (Matrix [[1,2], [3,4]] 2 2)
    
testEq02 =
    (Matrix [[1.0, 2.0], [3.0, 4.0]] 2 2) == (Matrix [[1, 2], [3, 4]] 2 2)
    
testScale01 =
    let a = Matrix [[1, 2], [3, 4], [5, 6]] 3 2
        a' = Matrix [[4, 8], [12, 16], [20, 24]] 3 2
    in scale 4 a == a'
    
testScale02 =
    let a = Matrix [[1, 2]] 1 2
        a' = Matrix [[0.5, 1]] 1 2
    in scale 0.5 a == a'
    
testAdd01 =
    let a = Matrix [[1,2], [3, 4], [5, 6]] 3 2
        b = Matrix [[1, 1], [1, 1], [1, 1]] 3 2
    in add a b == Just (Matrix [[2, 3], [4, 5], [6, 7]] 3 2)
    
testAdd02 =
    let a = Matrix [[1,2], [3, 4], [5, 6]] 3 2
        b = Matrix [[1, 2]] 1 2
    in add a b == Nothing
    
testAdd03 =
    let a = Matrix [[1, 2], [3, 4]] 2 2
        b = Matrix [[1, 2, 3], [4, 5, 6]] 2 3
    in add a b == Nothing

main = do
         if testShow01 then return () else putStrLn "Failed testShow01"
         if testShow02 then return () else putStrLn "Failed testShow02"
         if testShow03 then return () else putStrLn "Failed testShow03"
         if testEq01 then return () else putStrLn "Failed testEq01"
         if testEq02 then return () else putStrLn "Failed testEq02"
         if testScale01 then return () else putStrLn "Failed testScale01"
         if testScale02 then return () else putStrLn "Failed testScale02"
         if testAdd01 then return () else putStrLn "Failed testAdd01"
         if testAdd02 then return () else putStrLn "Failed testAdd02"
         if testAdd03 then return () else putStrLn "Failed testAdd03"
