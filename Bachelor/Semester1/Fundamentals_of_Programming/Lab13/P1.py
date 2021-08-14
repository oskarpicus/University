"""
1. Pentru listă de monede cu valorile a1,....,an, și o valoare S. Tipăriţi toate modalităţile de a
plăti suma S cu monedele disponibile. Tipăriți un mesaj dacă suma nu se poate plăti.
"""

def afisare(n,v,x,suma):
    """
    Functie pentru afisarea unei solutii corecte
    :param n: int, numar de monezi
    :param v: list, lista cu monezi
    :param x: list, solutie candidat
    :param suma: int, suma dep latit
    :return: -, afiseaza solutia daca este corecta
    """
    s=0
    for i in range(0,n):
        s+=v[i]*x[i]
    if s==suma:
        string=""
        for i in x:
            string+=str(i)+" "
        print(string)


def continuare(k,suma,v,x):
    """
    Functie pentru validarea unei solutii candidat
    :param k: int, pozitia curenta
    :param suma: int, suma de platit
    :param v: list, lista cu monezi
    :param x: list, solutie candidat
    :return: True, daca x poate fi inca considerata o solutie, False, altfel
    """
    s=0
    for i in range(0,k):
        s+=v[i]*x[i]
    if s<=suma:
        return True
    else:
        return False


def back(k,suma,n,v,x):
    """
    Functia de backtracking
    :param k: int, pozitia curenta
    :param suma: int, suma de platit
    :param n: int, numar de monezi
    :param v: list, lista cu monezi
    :param x: list, solutie candidat
    :return:
    """
    for val in range(0,suma//v[k]+1): #domeniul de cautare
        x[k]=val
        if continuare(k,suma,v,x)==True:
            if k+1==n:
                afisare(n,v,x,suma)
            else:
                back(k+1,suma,n,v,x)


suma=int(input("Dati suma de bani "))
n=int(input("Dati numar tipuri de monezi "))
print("Dati valori de monezi: ")
v=[]
x=[] #vector solutie
for i in range(0,n):
    v.append(int(input()))
    x.append(0)
if min(v)>suma:
    print("Suma nu poate fi platita ")
    exit()
string=""
for i in v:
    string+=f"Nr. monezi de {i} / "
print(string)
back(0,suma,n,v,x)

