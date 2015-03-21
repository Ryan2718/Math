tests: tests.o matrix.o
	gcc -o tests tests.o matrix.o

tests.o: tests.c matrix.h
	gcc -c tests.c

matrix.o: matrix.c matrix.h
	gcc -c matrix.c
