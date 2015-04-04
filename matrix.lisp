;;;; Matrix Operations
;;;; Ryan Forsyth
;;;; 04/03/2015

;;; The Matrix Class
(defclass matrix ()
  ((entries :accessor matrix-entries   ;; The entries of the matrix
	    :initarg :entries)
   (m :accessor matrix-m               ;; The number of rows in the matrix
      :initarg :m)
   (n :accessor matrix-n               ;; The number of columns in the matrix
      :initarg :n)))

;;; Constructor
(defun make-matrix (entries)
  (make-instance 'matrix 
		 :entries entries 
		 :m (length entries) 
		 :n (length (car entries))))

;;; Print the matrix
;;; Imperative
(defmethod print-matrix ((A matrix))
  (loop for i
	from 0
	upto (- (matrix-m A) 1)
	do
	(loop for j
	      from 0
	      upto (- (matrix-n A) 1)
	      do
	      (princ (nth j (nth i (matrix-entries A))))
	      (princ "  "))
	 (fresh-line)))

;;; Adds two matrices
;;; Imperative
;;; Mutates the first matrix
(defmethod add-imperative-mutate ((A matrix) (B matrix))
  (loop for i
	from 0
	upto (- (matrix-m A) 1)
	do
	(loop for j
	      from 0
	      upto (- (matrix-n A) 1)
	      do
	      (setf (nth j (nth i (matrix-entries A)))
		    (+ (nth j (nth i (matrix-entries A)))
		       (nth j (nth i (matrix-entries B))))))))

;;; Adds two matrices
;;; Functional
;;; Does NOT mutate either parameter matrix
(defmethod add-functional ((A matrix) (B matrix))
  (let ((entries (mapcar (lambda (x y) (mapcar #'+ x y)) 
		 (matrix-entries A)
		 (matrix-entries B))))
    (make-matrix entries)))
