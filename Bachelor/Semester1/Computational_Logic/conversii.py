'''
Logica computationala
Picus Oskar Anton
Aplicatie metode de conversie, operatii aritmetice
'''
""" Modul cu functii pentru conversia unui numar dintr-o baza de numeratie in alta """
from operatii import impartire
from utile import litere_in_cifre,cifre_in_litere,list_to_string,eliminare_zerouri,transformare_in_int
from copy import deepcopy
def impartiri_succesive(baza_sursa,baza_destinatie,numar):
    """
    Conversteste un numar dintr-o baza in alta folosind algoritmul de impartiri succesive
    :param baza_sursa: int, baza in care se vor realiza calculele
    :param baza_destinatie: int, baza in care va fi trecut numarul
    :param numar: list
    :return: numarul convertit in baza destinatie
    """
    rezultat=[] #aici pastram rezultatul, initializam cu lista vida
    if baza_sursa==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar=deepcopy(litere_in_cifre(numar))
    #prima imparire
    cat= impartire(baza_sursa,numar,baza_destinatie)
    rest=cat[-1]
    cat.pop() #sterge restul din lista
    if baza_sursa==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        cat=deepcopy(litere_in_cifre(cat))
    rezultat.append(rest) #adaugam restul in rezultat
    while cat!=[]: #impartim pana catul este lista vida (adica 0)
        cat=impartire(baza_sursa,cat,baza_destinatie) #impartim numerele
        rest = cat [-1]
        if baza_sursa==16: #inlocuim cifrele hexa cu echivalentul lor numeric
            cat=deepcopy(litere_in_cifre(cat))
        cat.pop() #sterge restul din lista (ultimul element)
        rezultat.append(rest) #adaugam restul in rezultat
    rezultat.reverse()
    if baza_destinatie==16: #transformam cifrele hexa in litere
        rezultat=cifre_in_litere(rezultat)
    return rezultat

def substitutie(baza_sursa,baza_destinatie,numar):
    """
    Converteste un numar dintr-o baza in alta folosind metoda substitutiei
    :param baza_sursa: int, baza in care se afla numarul initial
    :param baza_destinatie: int, baza in care se vor face calculele (baza 10)
    :param numar: list
    :return: numarul convertit in baza destinatie
    """
    suma=0 #aici se pastreaza rezultatul, se initializeaza cu 0
    if baza_sursa==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar = deepcopy(litere_in_cifre(numar))
    numar.reverse() #inversam elementele din lista
    for i in range(0,len(numar)): #parcurgem lista numar
        exp=baza_sursa**i #exponent
        suma+=numar[i]*exp #suma
    rezultat=list(str(suma)) #rezultat = lista de stringuri
    rezultat=transformare_in_int(rezultat) #rezultat = lista de cifre
    return rezultat

def conversii_rapide(baza_sursa,baza_destinatie,numar):
    """
    Conversteste un numar dintr-o baza in alta baza folosind conversiile rapide (din baza 2 in 4/8/16 sau invers)
    :param baza_sursa: int, numar egal cu 2,4,8 sau 16
    :param baza_destinatie: int, numar egal cu 2,4,8 sau 16
    :param numar: list
    :return: numarul convertit in baza destinatie
    """
    if baza_sursa==2:
        #conversia din baza 2 in baza 4/8/16
        rezultat=[] #aici se pastreaza rezultatul, se initializeaza cu lista vida
        # se decide la ce putere fata de 2 este baza_destinatie
        if baza_destinatie==4:
            k=2
        elif baza_destinatie==8:
            k=3
        else:
            k=4
        # dictionare cu cifrele in bazele 4,8 si 16 in binar
        cifre4={"00":0,"01":1,"10":2,"11":3}
        cifre8={"000":0,"001":1,"010":2,"011":3,"100":4,"101":5,"110":6,"111":7}
        cifre16={"0000":0,"0001":1,"0010":2,"0011":3,"0100":4,"0101":5,"0110":6,"0111":7,"1000":8,"1001":9,"1010":"A","1011":"B","1100":"C",
                 "1101":"D","1110":"E","1111":"F"}
        numar.reverse() #se inverseaza elementele listei numar
        #completare cu zerouri
        while len(numar)%k!=0:
            numar.append(0)
        for i in range(0,len(numar)//k): #se parcurge cate k cifre in lista
            cifra=numar[k*i:k*(i+1)] #o cifra in baza 2^k
            cifra.reverse()
            cifra = list_to_string(cifra) #conversie la tipul string
            # se decide ce cifra sa fie adaugata
            if k==2:
                cifra=cifre4[cifra]
            elif k==3:
                cifra=cifre8[cifra]
            else:
                cifra=cifre16[cifra]
            rezultat.append(cifra)
        rezultat.reverse()
        return eliminare_zerouri(rezultat) #se elimina zerourile de la inceputul listei
    else:
        #conversie din baza 4/8/16 in baza 2
        # dictionare cu cifrele in bazele 4,8 si 16 in binar
        cifre4={"0":"00","1":"01","2":"10","3":"11"}
        cifre8={"0":"000","1":"001","2":"010","3":"011","4":"100","5":"101","6":"110","7":"111"}
        cifre16={"0":"0000","1":"0001","2":"0010","3":"0011","4":"0100","5":"0101","6":"0110","7":"0111","8":"1000","9":"1001","A":"1010"
                 ,"B":"1011","C":"1100","D":"1101","E":"1110","F":"1111"}
        rezultat=[] #aici se pastreaza rezultatul, se initializeaza cu lista vida
        for i in numar: #se parcurge lista numar
            # se adauga in rezultat cifra corespunzatoare
            if baza_sursa==4:
                rezultat.append(cifre4[str(i)])
            elif baza_sursa==8:
                rezultat.append(cifre8[str(i)])
            else:
                rezultat.append(cifre16[str(i)])
        rezultat=list_to_string(rezultat) #se converteste la tipul string
        rezultat=list(rezultat) #se converteste in lista
        rezultat=transformare_in_int(rezultat) #se converteste fiecare element la tipul int (daca se poate)
        return eliminare_zerouri(rezultat) #se elimina zerourile de la inceputul listei

def baza_intermediara(baza_sursa,baza_destinatie,numar):
    """
    Conversteste un numar dintr-o baza de numeratie in alta folosind o baza intermediara (de obicei, baza 10)
    :param baza_sursa: int
    :param baza_destinatie: int
    :param numar: list
    :return: numarul convertit din baza_sursa in baza_destinatie
    """
    baze=[10,16,8,4,2] #baze des folosite
    if not baza_sursa in baze or not baza_destinatie in baze: #daca sunt baze diferite de cele din lista
        #se converteste folosind baza intermediara baza 10
        numar=substitutie(baza_sursa,10,numar)
        numar=impartiri_succesive(10,baza_destinatie,numar)
    elif baza_sursa==16 and (not baza_destinatie in baze or baza_destinatie==10):
        #conversia din baza 16 in baze diferite de 2,4,8
        numar = impartiri_succesive(baza_sursa,baza_destinatie,numar)
    elif baza_sursa==10 and baza_destinatie==2:
        #conversia din baza 10 in baza 2 folosind baza 8 drept baza intermediara
        numar=impartiri_succesive(10,8,numar)
        numar=conversii_rapide(8,2,numar)
    elif baza_sursa==2 and baza_destinatie==10:
        #conversia din baza 2 in baza 10 folosind baza 8 drept baza intermediara
        numar=conversii_rapide(2,8,numar)
        numar=substitutie(8,10,numar)
    elif baza_destinatie!=2 and baza_sursa!=2:
        #conversia din alte baze puteri ale lui 2, folosind baza 2 drept baza intermediara
        numar=conversii_rapide(baza_sursa,2,numar)
        numar=conversii_rapide(2,baza_destinatie,numar)
    return numar