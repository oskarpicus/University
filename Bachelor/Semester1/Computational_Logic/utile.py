'''
Logica computationala
Picus Oskar Anton
Aplicatie metode de conversie, operatii aritmetice
'''
"""
Modul cu functii ce ajuta la convertirea datelor de intrare intr-o forma mai usoara de prelucrat
"""
cifre={"0":0,"1":1,"2":2,"3":3,"4":4,"5":5,"6":6,"7":7,"8":8,"9":9,"A":10,"B":11,"C":12,"D":13,"E":14,"F":15,"15":"F","14":"E",
       "13":"D","12":"C","11":"B","10":"A"}

def litere_in_cifre(numar):
    """
    Transforma o lista ce contine caractere litera in cifra loc corespunzatoare in baza 16
    :param numar: list
    :return: lista numar modificata, fiecare litera fiind inlocuita cu cifra ei in baza 16
    """
    for n,i in enumerate(numar): #se parcurge lista
        if i=="A" or i=="B" or i=="C" or i=="D" or i=="E" or i=="F":
            numar [n] = cifre[i] #se inlocuieste fiecare cifra hexa mai mare ca 9 cu echivalentul ei numeric
    return numar

def cifre_in_litere(numar):
    """
    Transforma o lista ce contine cifre hexazecimale in litera corespunzatoare
    :param numar: list
    :return: lista numar modificata, fiecare cifra hexazecimala fiind inlocuita cu corespondentul ei (litera)
    """
    for n,i in enumerate(numar): #se parcurge lista
        if 10 <= i and i <= 15:
            numar [n] = cifre [str(i)] #se inlocuieste fiecare cifra hexa mai mare ca 9 cu litera ei corespunzatoare
    return numar

def eliminare_zerouri(numar):
    """
    Elimina zerourile astfel incat cifra cea mai semnificativa sa fie nenula
    :param numar: list
    :return: lista numar modificata, astfel incat cifra cea mai semnificativa sa fie nenula
    """
    ok=True #retine daca s-a sters o cifra 0 sau nu
    while ok==True:
        ok=False
        for i in numar: #parcurgem lista
            if i==0:
                numar.remove(i)
                ok=True
                break
            else:
                break
    return numar

def transformare_in_int(numar):
    """
    Transforma o lista de stringuri intr-una de int-uri (daca este posibil)
    :param numar: list
    :return: lista modificata
    """
    for n,i in enumerate(numar):
        try:
            numar[n]=int(numar[n])
        except:
            pass
    return numar

def numar_corect(numar,baza):
    """
    Verifica daca un numar intr-o baza este corect dat d.p.d.v. logic (nu contine alte cifre decat cele corecte)
    :param numar: list
    :param baza: int
    :return: True, daca este corect dat, False,in caz contrar
    """
    baza=int(baza)
    cifre=["A","B","C","D","E","F",0,1,2,3,4,5,6,7,8,9,"0","1","2","3","4","5","6","7","8","9"]
    for i in numar:
        if type(i)==int and baza<=10:
            if i>=baza:
                return False
        elif baza==16:
            if not i in cifre:
                return False
        if not i in cifre:
             return False
    return True

def list_to_string(x):
    """
    Transforma o lista intr-un string
    :param x: list
    :return: rezultatul convertirii listei x in string
    """
    rezultat=""
    for i in x:
        rezultat+=str(i)
    return rezultat

def comparatie(numar1,numar2):
    """
    Compara doua numere date in aceeasi baza de numeratie
    :param numar1: list
    :param numar2: list
    :return: True, daca numar1>=numar2, False, daca numar2<numar1
    """
    if len(numar1)>len(numar2):
        return True
    elif len(numar2)>len(numar1):
        return False
    else:
        for n,i in enumerate(numar1):
            if numar1[n]>numar2[n]:
                return True
            elif numar1[n]<numar2[n]:
                return False
    return True