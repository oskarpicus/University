
; L1 P7 d) d)	Definiti o functie care interclaseaza fara pastrarea dublurilor doua liste liniare sortate

; mergeAux( L : list , K : list , ultim : atom )
; Functie auxiliara pentru interclasarea a doua liste liniare sortate
; L : prima lista liniara sortata de interclasat
; K : a doua lista liniara sortata de interclasat
; ultim : retine ultimul element adaugat in rezultat

(defun mergeAux(l k ultim)
    (cond
        ;;1
        ( (and (null l) (null k)) NIL )
        ;;2
        ( (and (null l) (not (= (car k) ultim))) 
              (cons (car k) (mergeAux l (cdr k) (car k)))
        )
        ;;3
        ( (and (null l) (= (car k) ultim))
                (mergeAux l (cdr k) ultim)
        )
        ;;4
        ( (and (null k) (not (= (car l) ultim)))
                (cons (car l) (mergeAux (cdr l) k (car l)))
        )
        ;;5
        ( (and (null k) (= (car l) ultim))
                (mergeAux (cdr l) k ultim)
        )
        ;;6
        ( (and (<= (car l) (car k)) (not (= (car l) ultim)))
                (cons (car l) (mergeAux (cdr l) k (car l)))
        )
        ;;7
        ( (and (<= (car l) (car k)) (= (car l) ultim))
                (mergeAux (cdr l) k ultim)
        )
        ;;8
        ( (and (< (car k) (car l)) (not (= (car k) ultim)))
                (cons (car k) (mergeAux l (cdr k) (car k)))
        )
        ;;9
        ( (and (< (car k) (car l)) (= (car k) ultim))
                (mergeAux l (cdr k) ultim)
        )
    )
)

; myMerge( L : list, K : list)
; Functie pentru interclasarea a doua liste liniare sortate
; L : prima lista liniara sortata de interclasat
; K : a doua lista liniara sortata de interclasat

(defun myMerge(l k)
    (cond
        ( (and (null l) (null k)) NIL )
        ( (null l) k )
        ( (null k) l )
        ( (<= (car l) (car k)) (cons (car l) (mergeAux (cdr l) k (car l))))
        (t (cons (car k) (mergeAux l (cdr k) (car k))))
    )
)

; Exemple de testare
; (myMerge '(1 2 3) '(4 5 6)) ==> (1 2 3 4 5 6)
; (myMerge '() '()) ==> NIL 
; (myMerge '() '(1)) ==> (1)
; (myMerge '(3) '()) ==> (3)
; (myMerge '(2 2 2) '(2 2 2)) ==> (2)
; (myMerge '(1 5 9) '(1 5 9)) ==> (1 5 9)
; (myMerge '(2 8 12 99) '(1 3 7 13 84 102)) ==> (1 2 3 7 8 12 13 84 99 102)
; (myMerge '(2 8 12 84 99) '(1 3 7 8 13 84 102)) ==> (1 2 3 7 8 12 13 84 99 102)
; (myMerge '(3 42) '(4 7 9 32)) ==> (3 4 7 9 32 42)
