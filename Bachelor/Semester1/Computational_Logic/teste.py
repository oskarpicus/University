'''
Logica computationala
Picus Oskar Anton
Aplicatie metode de conversie, operatii aritmetice
'''
"""
Modul cu functii de teste pentru fiecare functie a aplicatiei (exceptand functiile UI)
"""
from utile import *
from operatii import adunare,scadere,inmultire,impartire
from conversii import conversii_rapide,impartiri_succesive,baza_intermediara,substitutie
#Modulul operatii
def test_adunare():
    assert adunare(6,[3,4,2,1],[3,2,1])==[4,1,4,2]
    assert adunare(5,[4,2,1],[1,4,2,4])==[2,4,0,0]
    assert adunare(2,[1,0,1,1],[1,0,0,1])==[1,0,1,0,0]
    assert adunare(16,["D",9,7],["E","F",1])==[1,"C",8,8]
    assert adunare(10,[8,1,0,4],[7,5,8])==[8,8,6,2]

def test_scadere():
    assert scadere(6,[5,0,4,3],[4,1,5,1])==[4,5,2]
    assert scadere(10,[5,1,0,4],[7,6,2])==[4,3,4,2]
    assert scadere(8,[6,7,2,1,3],[4,5,0,4])==[6,2,5,0,7]
    assert scadere(16,["E","A",0,3,"F"],["B",5,2,"C"])==["D","E","B",1,3]
    assert scadere(2,[1,1,0,1,0],[1,1,0,1])==[1,1,0,1]

def test_inmultire():
    assert inmultire(10,[3,4,0,5],7)==[2,3,8,3,5]
    assert inmultire(4,[3,1,2,2],3)==[2,2,0,3,2]
    assert inmultire(6,[3,1,5],2)==[1,0,3,4]
    assert inmultire(16,["A",1,0,"B"],4)==[2,8,4,2,"C"]
    assert inmultire(16,[1,"A",3],"B")==[1,2,0,1]

def test_impartire():
    assert impartire(10,[6,1,0,4],4)==[1,5,2,6,0]
    assert impartire(10,[1,4],2)==[7,0]
    assert impartire(8,[4,5,7],2)==[2,2,7,1]
    assert impartire(4,[1,2,3,2],3)==[2,1,0,2]
    assert impartire(16,["A",3,"B",5],3)==[3,6,9,1,2]
    assert impartire(16,[8,"A","B","C",0],"E")==[9,"E",8,"D","A"]

#Modulul utile
def test_cifre_in_litere():
    assert cifre_in_litere([])==[]
    assert cifre_in_litere([1,2,3,4])==[1,2,3,4]
    assert cifre_in_litere([10,5,7,15])==["A",5,7,"F"]
    assert cifre_in_litere([10,11,12,13,14,15])==["A","B","C","D","E","F"]

def test_litere_in_cifre():
    assert litere_in_cifre([])==[]
    assert litere_in_cifre([1,2,3,4])==[1,2,3,4]
    assert litere_in_cifre(["A",6,"B",9,5])==[10,6,11,9,5]
    assert litere_in_cifre(["A","B","C","D","E","F","A"])==[10,11,12,13,14,15,10]
    assert litere_in_cifre([1,"A"])==[1,10]

def test_eliminare_zerouri():
    assert eliminare_zerouri([])==[]
    assert eliminare_zerouri([1,2,3,4])==[1,2,3,4]
    assert eliminare_zerouri([2,0,5,0,2,0,0])==[2,0,5,0,2,0,0]
    assert eliminare_zerouri([0,4,5,6,0,1])==[4,5,6,0,1]
    assert eliminare_zerouri([0,0,0,0,0,1])==[1]
    assert eliminare_zerouri([0,0,0,1,0,0,0])==[1,0,0,0]

def test_numar_corect():
    assert numar_corect([1,2,3,4],10)==True
    assert numar_corect([1,2,5],2)==False
    assert numar_corect([4,5,7,100],3)==False
    assert numar_corect(["A",4,"B",4],16)==True
    assert numar_corect(["G","H"],16)==False
    assert numar_corect(["E","A",0,3,"F"],16)==True

def test_transformare_in_int():
    assert transformare_in_int(["1","2","3"])==[1,2,3]
    assert transformare_in_int(["1","B","C","6"])==[1,"B","C",6]
    assert transformare_in_int(["B","B","B"])==["B","B","B"]

def test_list_to_string():
    assert list_to_string([1,2,3,4])=="1234"
    assert list_to_string([1,"A",5])=="1A5"

def test_comparatie():
    assert comparatie([1],[1])==True
    assert comparatie([1,"A","C"],[1,"A","F"])==False
    assert comparatie([9,9],[1,0,0])==False
    assert comparatie([1,0,0],[9,9])==True

#Modulul conversii
def test_impartiri_succesive():
    assert impartiri_succesive(10,8,[2,3,7])==[3,5,5]
    assert impartiri_succesive(10,16,[5,4,2])==[2,1,"E"]
    assert impartiri_succesive(7,8,[1,2,5,3])==[7,3,7]
    assert impartiri_succesive(9,3,[1,2,5,3])==[1,0,2,1,2,1,0]
    assert impartiri_succesive(10,2,[4,7,9])==[1,1,1,0,1,1,1,1,1]
    assert impartiri_succesive(16,10,[1,"A","C","F"])==[6,8,6,3]
    assert impartiri_succesive(16,2,[1,"A","C","F"])==[1,1,0,1,0,1,1,0,0,1,1,1,1]

def test_conversii_rapide():
    assert conversii_rapide(2,8,[1,0,1,0,1,1,0,1,0])==[5,3,2]
    assert conversii_rapide(2,16,[1,1,0,0,1,0,1,0,1,1,1,1])==["C","A","F"]
    assert conversii_rapide(2,16,[1,1,1,0,0,1,0,1,0,1,1,1,1])==[1,"C","A","F"]
    assert conversii_rapide(2,4,[1,1,0,0,0,0,0,1])==[3,0,0,1]
    assert conversii_rapide(2,16,[1,1,1,1])==["F"]
    assert conversii_rapide(8,2,[1,6,2,5,7,0])==[1,1,1,0,0,1,0,1,0,1,1,1,1,0,0,0]
    assert conversii_rapide(16,2,[2,"A",4,5,"B"])==[1,0,1,0,1,0,0,1,0,0,0,1,0,1,1,0,1,1]
    assert conversii_rapide(8,2,[1,6,5,7])==[1,1,1,0,1,0,1,1,1,1]
    assert conversii_rapide(16,2,["C","F",3,"A"])==[1,1,0,0,1,1,1,1,0,0,1,1,1,0,1,0]

def test_baza_intermediara():
    assert baza_intermediara(7,3,[2,5,1])==[1,1,2,2,2]
    assert baza_intermediara(10,2,[2,3,7])==[1,1,1,0,1,1,0,1]
    assert baza_intermediara(2,10,[1,0,1,1,0,1,1,1,0,0])==[7,3,2]
    assert baza_intermediara(4,7,[3,1,3])==[1,0,6]
    assert baza_intermediara(8,16,[1,6,5,7])==[3,"A","F"]
    assert baza_intermediara(16,8,["C","F",3,"A"])==[1,4,7,4,7,2]

def test_substitutie():
    assert substitutie(6,10,[2,1,3])==[8,1]
    assert substitutie(16,10,[2,"A"])==[4,2]
    assert substitutie(4,10,[3,1,3])==[5,5]
    assert substitutie(7,10,[2,5,1])==[1,3,4]

def run_all_tests():
    test_adunare()
    test_scadere()
    test_inmultire()
    test_cifre_in_litere()
    test_litere_in_cifre()
    test_eliminare_zerouri()
    test_impartire()
    test_impartiri_succesive()
    test_numar_corect()
    test_transformare_in_int()
    test_list_to_string()
    test_comparatie()
    test_conversii_rapide()
    test_substitutie()
    test_baza_intermediara()