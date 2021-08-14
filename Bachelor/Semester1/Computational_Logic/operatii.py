'''
Logica computationala
Picus Oskar Anton
Aplicatie metode de conversie, operatii aritmetice
'''
"""
Modul cu functiile pentru operatiile aritmetice:
adunare, scadere, inmultire cu o cifra, impartire cu o cifra
"""
from utile import litere_in_cifre,cifre_in_litere,eliminare_zerouri
cifre={"0":0,"1":1,"2":2,"3":3,"4":4,"5":5,"6":6,"7":7,"8":8,"9":9,"A":10,"B":11,"C":12,"D":13,"E":14,"F":15,
       "15":"F","14":"E","13":"D","12":"C","11":"B","10":"A","16":16}
def adunare(baza,numar1,numar2):
    """
    Aduna doua numere date intr-o baza de numeratie
    :param baza: int, din multimea [2,10] reunit cu {16}
    :param numar1: list
    :param numar2: list
    :return: suma dintre numar1 si numar2, calculata in baza "baza"
    """
    rezultat=[] #aici pastram rezultatul, initializam cu lista vida
    transport=0
    if baza==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar1=litere_in_cifre(numar1)
        numar2=litere_in_cifre(numar2)
    i = len(numar1) #lungimea primei liste
    j = len(numar2) #lungime a doua lista
    while i>0 and j>0: #parcurgem simultan listele
        i-=1
        j-=1
        rezultat.append((numar1[i]+numar2[j]+transport)%baza)
        # se adauga rezultatul adunarii cifrelor de pe aceeasi pozitie
        transport=(numar1[i]+numar2[j]+transport)//baza
        # in caz de depasire, se actualizeaza transportul cu catul impartirii intregi
    i-=1
    j-=1
    while j>=0:
        #adaugam cifrele lui numar2 daca nu s-au epuizat
        rezultat.append(( numar2 [j] + transport) % baza)
        transport = ( numar2 [j] + transport) // baza
        j-=1
    while i>=0:
        #adaugam cifrele lui numar1 daca nu s-au epuizat
        rezultat.append((numar1 [i] + transport) % baza)
        transport = (numar1 [i]+ transport) // baza
        i-=1
    if transport>0: #adaugam ultimul transport
        rezultat.append(transport)
    rezultat.reverse() #inversam elementele din lista
    if baza==16: #transformam cifrele hexa in litere
        rezultat=cifre_in_litere(rezultat)
    return rezultat

def scadere(baza,numar1,numar2):
    """
    Scade doua numere date intr-o baza de numeratie
    :param baza: int, din multimea [2,10] reunit cu {16}
    :param numar1: list
    :param numar2: list
    :return: rezultatul (list) scaderii dintre numar1 si numar 2 in baza "baza"
    """
    rezultat = [] #aici pastram rezultatul, initializam cu lista vida
    transport = 0
    if baza==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar1=litere_in_cifre(numar1)
        numar2=litere_in_cifre(numar2)
    i=len(numar1) #lungimea primei liste
    j=len(numar2) #lungime a doua lista
    while i>0 and j>0: #parcurgem simultan listele
        i-=1
        j-=1
        if numar1[i]-numar2[j]+transport>=0:
            rezultat.append(numar1[i]-numar2[j]+transport)
            # se adauga rezultatul scaderii cifrelor de pe aceeasi pozitie
            transport = 0
        else:
            rezultat.append(numar1[i]-numar2[j]+transport+baza)
            transport=-1
            #in caz de imprumut, se actualizeaza transportul
    i-=1
    j-=1
    while i>=0:
        # adaugam cifrele lui numar1 daca nu s-au epuizat
        if numar1[i]+transport>=0:
            rezultat.append(numar1[i]+transport)
            transport = 0
        else:
            rezultat.append(numar1[i]+transport+baza)
            transport=-1
        i-=1
    rezultat.reverse() #inversam elementele din lista
    if baza==16: #transformam cifrele hexa in litere
        rezultat=cifre_in_litere(rezultat)
    rezultat=eliminare_zerouri(rezultat) #eliminam zerourile din inceputul listei
    return rezultat

def inmultire(baza,numar,cifra):
    """
    Inmulteste un numar cu o cifra intr-o baza de numeratie
    :param baza: int, din multimea [2,10] reunit cu {16}
    :param numar: list
    :param cifra: int, 0<cifra<=9
    :return: rezultatul inmultirii lui numar la cifra in baza "baza"
    """
    transport=0
    rezultat=[] #aici pastram rezultatul, initializam cu lista vida
    if baza==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar=litere_in_cifre(numar)
        cifra=cifre[str(cifra)]
    i=len(numar) #lungime numar
    while i>0: #parcurgem lista numar
        i-=1
        rezultat.append((cifra*numar[i]+transport)%baza)
        transport=(cifra*numar[i]+transport)//baza
        #in caz de depasire, transportul se actualizeaza
    if transport>0: #adaugam ultimul transport
        rezultat.append(transport)
    rezultat.reverse()
    if baza==16: #transformam cifrele hexa in litere
        rezultat=cifre_in_litere(rezultat)
    return rezultat

def impartire(baza,numar,cifra):
    """
    Imparte un numar la o cifra intr-o baza de numeratie
    :param baza: int, 2<=baza<=10 sau baza=16
    :param numar: list
    :param cifra: int, 0<cifra<=9
    :return: rezultatul impartirii lui numar la cifra in baza "baza" sub forma [ cat, rest ]
    """
    transport=0
    rezultat=[] #aici pastram rezultatul, initializam cu lista vida
    if baza==16: #inlocuim cifrele hexa cu echivalentul lor numeric
        numar=litere_in_cifre(numar)
        if type(cifra)!=int:
            cifra=cifre[str(cifra)]
    i=0
    while i<len(numar): #parcurgem lista numar
        rezultat.append((transport*baza+numar[i])//cifra)
        transport=(transport*baza+numar[i])%cifra
        #se pastreaza restul impartirii cifrei curente la impartitor
        i+=1
    rezultat.append(transport) #restul impartirii
    if baza==16: #transformam cifrele hexa in litere
        rezultat=cifre_in_litere(rezultat)
    rezultat=eliminare_zerouri(rezultat) #eliminam zerourile din inceputul listei
    return rezultat