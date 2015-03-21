#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"
#define FAILURE -1
#define SUCCESS 0

int test01() {
  Matrix A;
  Matrix B;
  int result;

  result = matrix_allocate(&A, 3, 2);
  if (result == TRUE) {
    A.values[0][0] = 1;
    A.values[0][1] = 2;
    A.values[1][0] = 3;
    A.values[1][1] = 4;
    A.values[2][0] = 5;
    A.values[2][1] = 6;

    result = matrix_allocate(&B, 3, 2);
    if (result == TRUE) {
      result = matrix_copy(&A, &B);
      if (result == TRUE) {
	      if (matrix_equals(&A, &B) == FALSE) {
	        printf("A != B\n");
	        return FAILURE;
	      }
      } else {
	      printf("A and B have different dimensions.\n");
	      return FAILURE;
      }
    } else {
      printf("Memory not allocated for B.\n");
      return FAILURE;
    }
    matrix_free(&B);
    
  } else {
    printf("Memory not allocated for A.\n");
    return FAILURE;
  }
  matrix_free(&A);

  return SUCCESS;
}

int main() {
  int status = SUCCESS;

  if (test01() == FAILURE) status = FAILURE;

  if (status == SUCCESS) printf("Success!\n");

  return status;
}
