#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"

/* Print the matrix A */
void matrix_print(Matrix *A) {
  int row, col;
    
  for (row = 0; row < A->m; row++) {
    for (col = 0; col < A->n; col++) {
      printf("%2.1f", A->values[row][col]);
      if (col != A->n - 1) {
	printf(" ");
      }
    }
    printf("\n");
  }
  printf("\n");
}  

/* Copy the matrix A into B */
int  matrix_copy(Matrix *A, Matrix *B) {
  int row, col;
  
  if (A->m != B->m || A->n != B->n) {
    return FALSE;
  }

  for (row = 0; row < A->m; row++) {
    for (col = 0; col < A->n; col++) {
      B->values[row][col] = A->values[row][col];
    }
  }

  return TRUE;
    
}

/* Return TRUE if A and B are equal and FALSE otherwise */
int matrix_equals(Matrix *A, Matrix *B) {
  int row, col;

  if (A->m != B->m || A->n != B->n) {
    return FALSE;
  }

  for (row = 0; row < A->m; row++) {
    for (col = 0; col < A->n; col++) {
      if (A->values[row][col] != B->values[row][col]) {
	return FALSE;
      }
    }
  }
  
  return TRUE;
}

/* Allocate memory for the matrix A */
int matrix_allocate(Matrix *A, int m, int n) {
  int row;

  A->m = m;
  A->n = n;

  A->values = malloc (sizeof(double *) * A->m);
  if (A->values != NULL) {
    for (row = 0; row < A->m; row++) {
      A->values[row] = malloc (sizeof(double) * A->n);
      if (A->values[row] == NULL) {
	return FALSE;
      }
    }
    return TRUE;
  } else {
    return FALSE;
  }

} 

/* Free memory used by the matrix A */
void matrix_free(Matrix *A) {
  int row;

  for (row = 0; row < A->m; row++) {
    free(A->values[row]); /* Free the rows */
  }

  free(A->values); /* Free the whole matrix */
}
