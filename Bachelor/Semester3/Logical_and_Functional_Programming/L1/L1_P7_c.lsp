
; L1 P7 c) c)	Sa se inlocuiasca fiecare sublista a unei liste cu ultimul ei element. Prin sublista se intelege 
; element de pe primul nivel, care este lista.

; ultim ( L : list)
; Returneaza ultimul element de la orice nivel dintr-o lista
; L : lista din care se obtine ultimul element de la orice nivel

(defun ultim(l)
    (cond
        ( (and (atom (car l)) (equal (cdr l) NIL) )
                (car l)
        )
        ( (and (listp (car l)) (equal (cdr l) NIL) )
                (ultim (car l) )
        )
        (t (ultim (cdr l) ) )
    )
)

; Exemple de testare
; (ultim '(1 2 3)) ==> 3
; (ultim '()) ==> NIL
; (ultim '(1 2 (3 (4 (5 (6 (7 (((8)))))))))) ==> 8
; (ultim '(1 2 (3 (4 (5 (6 (7 (((8)))))))9))) ==> 9

; inlocuiesteUltim( L : list )
; Functie pentru inlocuirea fiecarei subliste cu ultimul ei element
; L : lista in care se inlocuieste fiecare sublista cu ul ei element

(defun inlocuiesteUltim(l)
    (cond
        ( (null l) NIL )
        ( (atom l) l )
        ( (atom (car l) ) (cons (car l) (inlocuiesteUltim (cdr l))) )
        (t (cons (ultim (car l)) (inlocuiesteUltim (cdr l))) )
    )
)

; Exemple de testare
; (inlocuiesteUltim '(a (b c) (d (e (f))))) ==> (a c f)
; (inlocuiesteUltim '(a (b c) (d ((e) f)))) ==> (a c f)
; (inlocuiesteUltim '(a (b (((c)))) ())) ==> (a c NIL)
; (inlocuiesteUltim '(a (b (((c)))) (d e (f (g (h (i)))))))) ==> (a c i)
; (inlocuiesteUltim '(a b c d e f g)) ==> (a b c d e f g)
; (inlocuiesteUltim '((((((((a))))))))) ==> (a)
; (inlocuiesteUltim '(a (((((((b)))))c d e)g) (i o u))) ==> (a g u)


