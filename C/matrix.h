#include <stdio.h>
#include <stdlib.h>
#ifndef MATRIX_H
#define TRUE 1
#define FALSE 0

typedef struct matrix {
  double **values; /* values of the matrix */
  int m, n; /* numRows, numCols, respectively */
} Matrix;

void matrix_print(Matrix *A);
int matrix_copy(Matrix *A, Matrix *B);
int matrix_equals(Matrix *A, Matrix *B);
int matrix_allocate(Matrix *A, int m, int n);
void matrix_free(Matrix *A);

#endif
