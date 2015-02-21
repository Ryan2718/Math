#include <stdio.h>
#include <stdlib.h>

typedef struct matrix {
    double **values; /* values of the matrix */
    int m, n; /* numRows, numCols, respectively */
} Matrix;


void matrix_print(Matrix A) {
    int row, col;
    
    for (row = 0; row < A.m; row++) {
        for (col = 0; col < A.n; col++) {
            printf("%2.1f", A.values[row][col]);
            if (col != A.n - 1) {
                printf(" ");
            }
        }
        printf("\n");
    }
    printf("\n");
}

Matrix matrix_copy(Matrix A) {
    int row, col;
    
    Matrix B;
    B.m = A.m;
    B.n = A.n;
    B.values = malloc(B.m * sizeof(double *));
    for (row = 0; row < A.m; row++) {
        B.values[row] = malloc(B.n * sizeof(double *));
        for (col = 0; col < A.n; col++) {
            B.values[row][col] = A.values[row][col];
        }
    }
    
    return B;
}
