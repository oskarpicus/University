
; L2 P4 Sa se converteasca un arbore de tipul (2) la un arbore de tipul (1).

; nrDescendenti( L2 : list, L3 : list )
; Functie pentru determinarea numarului de liste nevide dintre 2 liste date ca si parametru
; L2, L3 : listele care se doresc a fi verificate

(defun nrDescendenti(l2 l3)
    (cond
        ( (and (null l2) (null l3)) 0)
        ( (or (null l2) (null l3)) 1)
        (t 2)
    )
)

; Exemple de testare
; (nrDescendenti '() '()) ==> 0
; (nrDescendenti '(1) '()) ==> 1
; (nrDescendenti '() '(a (b) c)) ==> 1
; (nrDescendenti '(a (b) d (e (f))) '(1 (a) (3))) ==> 2


; convert( arb : list )
; Functie pentru converstia unui arbore de tipul (2) la un arbore de tipul (1)
; arb : arborele memorat sub forma unei liste in varianta (2)

(defun convert(arb)
    (cond
        ( (null arb) NIL)
        ( t (append (list (car arb)) (list (nrDescendenti (cadr arb) (caddr arb))) (convert (cadr arb)) (convert (caddr arb)))) 
    )
)

; Exemple de testare
; (convert '()) ==> NIL
; (convert '(a)) ==> (A 0)
; (convert '(a (b))) ==> (A 1 B 0)
; (convert '(a () (b))) ==> (A 1 B 0)
; (convert '(a (b) (c))) ==> (A 2 B 0 C 0)
; (convert '(a (b (c (d))))) ==> (A 1 B 1 C 1 D 0)
; (convert '(a () (b () (c () (d))))) ==> (A 1 B 1 C 1 D 0)
; (convert '(a () (b (c (e () (f))) (d () (g))))) ==> (A 1 B 2 C 1 E 1 F 0 D 1 G 0)
; (convert '(a (b) (c (d) (e)))) ==> (A 2 B 0 C 2 D 0 E 0)
; (convert '(a (b () (f)) (d (e)))) ==> (A 2 B 1 F 0 D 1 E 0)
; (convert '(a (b (c (i)) (f (g))) (d (e) (h)))) ==> (A 2 B 2 C 1 I 0 F 1 G 0 D 2 E 0 H 0)
; (convert '(a () (b (c () (d (e)))))) ==> (A 1 B 1 C 1 D 1 E 0)