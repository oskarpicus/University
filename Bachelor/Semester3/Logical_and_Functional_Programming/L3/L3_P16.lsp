
; L3 P16 - Definiti o functie care inverseaza o lista impreuna cu toate sublistele
; sale de pe orice nivel.

; inversareAux( l : list, col : list )
; Functie auxiliara pentru inversarea elementelor unei liste de la nivelul superficial
; l : lista ce se doreste a fi inversata la nivel superficial
; col : variabila colectoare ce retine rezultatul partial al inversarii

(defun inversareAux(l col)
    (cond
        ( (null l) col )
        ( t (inversareAux (cdr l) (cons (car l) col)))
    )
)

; inversare( l : list )
; Functie pentru inversarea unei liste de la nivelul superficial
; l : lista ce se doreste a fi inversata la nivel superficial

(defun inversare(l)
    (inversareAux l nil)
)

; Exemple de testare
; (inversare '(1 2 3)) ==> (3 2 1)
; (inversare '()) ==> NIL
; (inversare '(1 (2 3) 4)) ==> (4 (2 3) 1)
; (inversare '(1 (2 (3 (4 5))) 6)) ==> (6 (2 (3 (4 5))) 1)
; (inversare '( (1 2) (4) )) ==> ( (4) (1 2) )
; (inversare ' ( (1 (2 (3 4)) 5) (6 (7 8) 9 (((10)))))) ==> (inversare ' ( (1 (2 (3 4)) 5) (6 (7 8) 9 (((10))))))
; (inversare '((1 2))) ==> ((1 2))


; inv( l : s-expresie )
; Functie pentru inversarea unei liste, impreuna cu sublistele sale

(defun inv(l)
    (cond
        ( (atom l) l )
        ( t  (inversare (mapcar #'inv l)))
    )
)


; Exemple de testare
; (inv '(1 2 3)) ==> (3 2 1)
; (inv '(1 (2 3) 4 (5 6))) ==> ((6 5) 4 (3 2) 1)
; (inv '((1 2) (3 4))) ==> ((4 3) (2 1))
; (inv '(1 (2 (3 4) 5) 6)) ==> (6 (5 (4 3) 2) 1)
; (inv '(1 (2 (3 (4 (5 (6 7 8 9) 10) 12 13) 14)))) ==> (((14 (13 12 (10 (9 8 7 6) 5) 4) 3) 2) 1)
; (inv '(1 (2 (3 (4 (5 (6 7 8 9) 10) 12 13) 14) 15 16) 17)) ==> (17 (16 15 (14 (13 12 (10 (9 8 7 6) 5) 4) 3) 2) 1)
; (inv NIL) ==> NIL
; (inv '(1 (2 3) 4)) ==> (4 (3 2) 1)
; (inv '(1 (2 (3 (4 5))) 6)) ==> (6 (((5 4) 3) 2) 1)
; (inv '( (1 2) (4) )) ==> ( (4) (2 1) )
; (inv '((1 2))) ==> ((2 1))
