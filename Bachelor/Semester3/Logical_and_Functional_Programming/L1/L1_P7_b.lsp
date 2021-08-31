
; L1 P7 b) b)	Definiti o functie care substituie prima aparitie a unui element intr-o lista data. 

; apare( L : list, e : element)
; Functie pentru cautarea unui element intr-o lista pe toate nivelele
; L : lista in care se cauta elementul
; e : elementul de cautat

(defun apare(l e)
    (cond
        ( (null l) NIL )
        ( (listp (car l)) 
                (or (apare (car l) e) (apare (cdr l) e ) )
        )
        ( (equal (car l) e) t )
        (t (apare (cdr l) e))
    )
)

; Exemple de testare
; (apare '(1 2 3) '2) ==> T
; (apare '(1 3 (4 (5 (6 (2) 4) 64) 42)) '2) ==> T
; (apare '(1 3 4) '2) ==> NIL
; (apare '() '2) ==> NIL
; (apare '((((())))) '2) ==> NIL
; (apare '(1 ((((9 (2))))) 3 (((2)))) '2) ==> T


; inlocuiesteAux( L : list, target : atom, e : atom, ok : boolean)
; Functie auxiliara pentru inlocuirea primei apartii a unui atom dintr-o lista cu un alt atom
; L : lista din care se inlocuieste
; target : atom de inlocuit
; e : atomul cu care se inlocuieste
; ok : indicator (NIL sau t) daca (nu) a fost gasit target in L

(defun inlocuiesteAux( l target e ok)
    (cond
        ( (null l) NIL )
        ( ok (cons (car l) (inlocuiesteAux (cdr l) target e ok) ) )
        ( (and (atom (car l) ) (not (equal (car l) target))) 
                 (cons (car l) (inlocuiesteAux (cdr l) target e ok) ) 
        )
        ( (and (atom (car l) ) (equal (car l) target) )
                 (cons e (inlocuiesteAux (cdr l) target e t) )
        )
        ( (listp l) 
            (cons (inlocuiesteAux (car l) target e ok) 
                (inlocuiesteAux (cdr l) target e (apare (car l) target) ))
        )
    )
)

; inlocuieste( L : list, target : atom, e : atom)
; Functie pentru inlocuirea primei apartii a unui atom dintr-o lista cu un alt atom
; L : lista din care se inlocuieste
; target : atom de inlocuit
; e : atomul cu care se inlocuieste

(defun inlocuieste(l target e)
    (inlocuiesteAux l target e NIL)
)

; Exemple de testare
; (inlocuieste '(1 2 3) '2 'X) ==> (1 X 3)
; (inlocuieste '(1 (2) 3) '2 'X) ==> (1 (X) 3)
; (inlocuieste '(1 3 4) '2 'X) ==> (1 3 4)
; (inlocuieste '(1 3 4 (5 (23 (4)))) '2 'X) ==> (1 3 4 (5 (23 (4))))
; (inlocuieste '(1 2 3 (4 (2))) '2 'X) ==> (1 X 3 (4 (2)))
; (inlocuieste '() '2 'X) ==> NIL
; (inlocuieste '(1 (3 (4 (5 (6 (2 2) ) 2) 1) 2) (4 2)) '2 'X) ==> (1 (3 (4 (5 (6 (X 2)) 2) 1) 2) (4 2))
; (inlocuieste '(1 (3 9) (1 (2) 2)) '2 'X) ==> (1 (3 9) (1 (X) 2))
; (inlocuieste '(2 2 2) '2 'X) ==> (X 2 2)
; (inlocuieste '((((((((1 (2))))))))) '2 'X) ==> ((((((((1 (X)))))))))
; (inlocuieste '((((((((1 (2)))))))2)) '2 'X) ==> ((((((((1 (X))))))) 2))
; (inlocuieste '(1 a 3) 'a 'X)) ==> (1 X 3)




