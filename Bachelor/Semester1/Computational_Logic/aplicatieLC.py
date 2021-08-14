'''
Logica computationala
Picus Oskar Anton
Aplicatie metode de conversie, operatii aritmetice
'''
#Modulul principal
from teste import run_all_tests
from conversii import baza_intermediara,conversii_rapide,impartiri_succesive,substitutie
from operatii import *
from utile import transformare_in_int,numar_corect,list_to_string,comparatie
cifre={"0":0,"1":1,"2":2,"3":3,"4":4,"5":5,"6":6,"7":7,"8":8,"9":9,"A":10,"B":11,"C":12,"D":13,"E":14,"F":15
    ,"15":"F","14":"E","13":"D","12":"C","11":"B","10":"A"}
def meniu():
    '''
    Afiseaza meniul utilizatorului si primeste comenzi de la acesta
    Va executa functiile specifice pentru comanda aleasa
    '''
    print("Logica computationala")
    print("Picus Oskar Anton Grupa 216 Informatica Romana")
    print("Aplicatie metode de conversie, operatii aritmetice")
    print("Atentie - numerele in baza 16 se vor da folosind majuscule ! ")
    while True:
        print("Selectati functia dorita ")
        print("1 - Conversii intre baze")
        print("2 - Operatii aritmetice")
        print("0 - Iesirea din aplicatie")
        optiune = input()
        if optiune=="0":
            print("Sa aveti o zi frumoasa! ")
            return
        elif optiune=="2":
            meniu_operatii()
        elif optiune=="1":
            meniu_conversii()
        else:
            print("Comanda invalida")

def meniu_operatii():
    """
    Afiseaza meniul utilizatorului corespunzator categoriei alese (Operatii aritmetice) si primeste comenzi de la acesta
    Va executa functiile specifice pentru comanda aleasa
    :return: -
    """
    comenzi={"1":ui_adunare,"2":ui_scadere,"3":ui_inmultire,"4":ui_impartire}
    while True:
        print("Selectati functia dorita ")
        print("1 - Adunare")
        print("2 - Scadere")
        print("3 - Inmultire cu o cifra")
        print("4 - Impartire cu o cifra")
        print("0 - Revenire in meniul principal")
        optiune=input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi[optiune]()
        else:
            print("Comanda invalida! ")

def ui_adunare():
    """
    Primeste de la utilizator operanzii pentru adunare
    :return: -, afiseaza rezultatul adunarii a doua numere date in baze diferite intr-o alta baza
    """
    numar1,baza1,numar2,baza2,baza3=citire_pentru_operatii() #citirea datelor de intrare
    #se transforma din lista de string-uri in lista de int-uri
    numar1 = transformare_in_int(numar1)
    numar2 = transformare_in_int(numar2)
    if not validare(numar1,numar2,baza1,baza2,baza3): #se valideaza datele de intare
        print("Valoari invalide")
        ui_adunare()
    else:
        baza1=int(baza1)
        baza2=int(baza2)
        baza3=int(baza3)
        # conversii
        numar1=baza_intermediara(baza1,baza3,numar1)
        numar2=baza_intermediara(baza2,baza3,numar2)
        numar1 = transformare_in_int(numar1)
        numar2 = transformare_in_int(numar2)
        print(f"Rezultatul este {list_to_string(adunare(baza3,numar1,numar2))} ({baza3})")


def ui_scadere():
    """
    Primeste de la utilizator operanzii pentru scadere
    :return: -, afiseaza rezultatul scaderii a doua numere date in baze diferite intr-o alta baza
    """
    numar1,baza1,numar2,baza2,baza3 = citire_pentru_operatii() #citirea datelor de intrare
    # se transforma din lista de string-uri in lista de int-uri
    numar1 = transformare_in_int(numar1)
    numar2 = transformare_in_int(numar2)
    if not validare(numar1,numar2,baza1,baza2,baza3): #se valideaza datele de intare
        print("Valoari invalide")
        ui_scadere()
    else:
        baza1=int(baza1)
        baza2=int(baza2)
        baza3=int(baza3)
        # conversii
        numar1 = baza_intermediara(baza1,baza3,numar1)
        numar2 = baza_intermediara(baza2,baza3,numar2)
        if comparatie(numar1,numar2): #se verifica daca numar1>=numar2
            rezultat=scadere(baza3,numar1,numar2)
            if rezultat==[]: #daca rezultatul este 0
                rezultat=[0]
            rezultat=list_to_string(rezultat) #se converteste din lista in string
            print(f"Rezultatul este {rezultat} ({baza3})")
        else:
            print("Nu se poate face scaderea ")

def ui_inmultire():
    """
    Primeste de la utilizator numarul si cifra pentru a fi inmultite, ambele in baze diferite si baza de lucru
    :return: -, afiseaza rezultatul inmultirii celor doua numere in baza de lucru
    """
    print("Aveti voie sa inmultiti doar cu o cifra! ")
    b16=["A","B","C","D","E","F","0","1","2","3","4","5","6","7","8","9"]
    #citirea datelor de intrare
    numar = transformare_in_int(list(input("Dati numarul ")))
    baza1 = input("Dati baza ")
    cifra = input("Dati cifra ")
    #validarea datelor de intrare
    if not validare(numar,[],baza1,baza1,baza1) or (baza1=="16" and not cifra in b16) or (baza1!="16" and not valideaza_tip_numeric(cifra)):
        print("Valoari invalide")
        ui_inmultire()
    else:
        baza1 = int(baza1)
        if baza1==16:
            print(f"Rezultatul este {list_to_string(inmultire(baza1,numar,cifra))} ({baza1})")
        else:
            print(f"Rezultatul este {list_to_string(inmultire(baza1,numar,int(cifra)))} ({baza1})")

def ui_impartire():
    """
    Primeste de la utilizator numarul si cifra pentru a fi impartite, ambele in baze diferite si baza de lucru
    :return: -, afiseaza rezultatul impartirii celor doua numere in baza de lucru
    """
    print("Aveti voie sa impartiti doar cu o cifra! ")
    b16 = ["A","B","C","D","E","F","0","1","2","3","4","5","6","7","8","9"]
    #citirea datelor de intrare
    numar=transformare_in_int(list(input("Dati numarul ")))
    baza1=input("Dati baza ")
    cifra=input("Dati cifra ")
    #validarea datelor de intrare
    if not validare(numar,[],baza1,baza1,baza1) or (baza1=="16" and not cifra in b16 ) or (baza1!="16" and not valideaza_tip_numeric(cifra)) or cifra=="0":
        print("Valoari invalide")
        ui_impartire()
    else:
        baza1 = int(baza1)
        if baza1!=16:
            rezultat=impartire(baza1,numar,int(cifra))
        else:
            rezultat = impartire(baza1,numar,cifra)
        rest=rezultat[-1] #restul impartirii
        rezultat.pop() #se sterge restul din rezultat
        if rezultat==[]: #daca rezultatul (catul) este 0
            rezultat=[0]
        rezultat=list_to_string(rezultat)
        print(f"Rezultatul este {rezultat} ({baza1}) rest {rest} ({baza1})")

def meniu_conversii():
    """
    Primeste de la utilizator un numar si baza in care se afla si afiseaza rezultatul convertirii sale intr-o alta baza
    :return: -, afiseaza conversia lui numar din baza_sursa in baza_destinatie
    """
    puteri=[2,4,8,16] #bazele puteri ale lui 2
    #citirea datelor de intrare
    numar=input("Dati numarul pentru a fi convertit ")
    baza_sursa=input("Dati baza numarului dat ")
    baza_destinatie=input("Dati baza in care sa fie convertit numarul ")
    numar=transformare_in_int(numar) #conversie din lista de string-uri in lista de int-uri
    #validarea datelor de intrare
    if not validare(numar,[1],baza_sursa,baza_destinatie,0):
        print("Valori invalide ")
        meniu_conversii()
    else:
        #conversiile datelor de intrare
        numar=transformare_in_int(list(str(numar)))
        baza_sursa=int(baza_sursa)
        baza_destinatie=int(baza_destinatie)
        #deciderea celei mai bune metode de conversie
        if baza_destinatie==10:
            rezultat=substitutie(baza_sursa,baza_destinatie,numar)
        elif baza_sursa==10:
            rezultat=impartiri_succesive(baza_sursa,baza_destinatie,numar)
        elif baza_sursa in puteri and baza_destinatie in puteri and (baza_sursa==2 or baza_destinatie==2):
            rezultat=conversii_rapide(baza_sursa,baza_destinatie,numar)
        else:
            rezultat=baza_intermediara(baza_sursa,baza_destinatie,numar)

        print(f"Rezultatul este: {list_to_string(rezultat)} ({baza_destinatie})")


def valideaza_tip_numeric(x):
    """
    Verifica daca un string poate fi convertit la int
    :param x: string
    :return: True, daca x este un numar, False, in caz contrar
    """
    try:
        x=int(x)
        return True
    except ValueError:
        return False

def validare(numar1,numar2,baza1,baza2,baza3):
    """
    Valideaza operanzii operatiei alese si bazele in care acestia au fost dati d.p.d.v. logic
    :param numar1: list
    :param numar2: list
    :param baza1: string
    :param baza2: string
    :return: True, daca datele sunt corecte, False, in caz contrar
    """
    if not valideaza_tip_numeric(baza1) or not valideaza_tip_numeric(baza2) or not valideaza_tip_numeric(baza3):
        return False
    elif numar_corect(numar1,int(baza1))==False or numar_corect(numar2,int(baza2))==False:
        return False
    return True

def citire_pentru_operatii():
    """
    Citirea de la tastatura a operanzilor operatiei alese si bazele de lucru
    :return: lista cu datele operatiei
    """
    print("Dati cele 2 numere ")
    numar1 = list(input("Dati primul numar "))
    baza1 = input("Dati baza primului numar ")
    numar2 = list(input("Dati al doilea numar "))
    baza2 = input("Dati baza celui de-al doilea numar ")
    baza3 = input("Dati baza in care se va efectua operatia ")
    return numar1,baza1,numar2,baza2,baza3

run_all_tests()
meniu()
