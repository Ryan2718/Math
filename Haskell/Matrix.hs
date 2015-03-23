module Matrix where

import Data.List

data Matrix a = Matrix { entries :: [[a]]
                     , m :: Int
                     , n :: Int
                     }

instance (Show a) => Show (Matrix a) where
    show (Matrix values _ _) = concat $ map (++ "\n") $ map (show) values
    
instance (Eq a) => Eq (Matrix a) where
    Matrix entries1 m1 n1 == Matrix entries2 m2 n2 =
        (entries1 == entries2) && (m1 == m2) && (n1 == n2)


-- Scale a matrix by a given number
scale :: (Num a) => a -> Matrix a -> Matrix a
scale c (Matrix values m' n') = let newEntries = map (map (*c)) values in
                                   Matrix newEntries m' n'

-- Add two matrices
add :: (Num a) => Matrix a -> Matrix a -> Maybe (Matrix a)
add (Matrix entries1 m' n') (Matrix entries2 m'' n'') =
    if (m' == m'') && (n' == n'')
    then let newEntries = zipWith (zipWith (+)) entries1 entries2 in
             Just $ Matrix newEntries m' n'
    else Nothing