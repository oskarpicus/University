
; L1 P7 a) Sa se scrie o functie care testeaza daca o
; lista este liniara.

; liniara( L : list)
; Verifica daca o lista este liniara
; L : lista de verificat liniaritatea

(defun liniara(l)
    (cond
        ( (null l) t )
        ( (atom l) NIL )
        ( (atom (car l)) (liniara (cdr l)) )
        ( (list l) NIL )
    )
)

; Exemple de testare
; (liniara '(1 2 3)) ==> T
; (liniara '() ) ==> T
; (liniara 'a ) ==> NIL
; (liniara '(1 (2) 3)) ==> NIL