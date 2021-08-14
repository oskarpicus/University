""" Modul pentru validarea datelor de intrare in conformitate cu logica problemei """

def valideaza_numar_apartament(nr_apartament):
    """
    Verifica daca un numar de apartament este valid
    :param nr_apartament: int
    :return: True, daca este valid, False, in caz contrar
    """
    if nr_apartament<=0:
        return False
    else:
        return True

def valideaza_suma(suma):
    """
    Verifica daca o suma este valida
    :param suma: int
    :return: True, daca este valida, False, in caz contrar
    """
    if suma<=0:
        return False
    else:
        return True

def valideaza_tip(tip):
    """
    Verifica daca un tip de cheltuiala este unul valid (este unul dintre cele predefinite)
    :param tip: string
    :return: True, daca este valid, False, in caz contrar
    """
    tipuri = ["apa","canal","incalzire","gaz","altele"]
    if tip in tipuri:
        return True
    else:
        return False

def valideaza_zi(zi):
    """
    Verifica daca o zi este valida
    :param zi: int
    :return: True, daca este valida, False, in caz contrar
    """
    if 0<zi and zi<=31:
        return True
    else:
        return False

def valideaza_cheltuiala(c):
    """
    Verifica daca o cheltuiala este valida in conformitate cu datele problemei
    :param c: o cheltuiala
    :return: True, daca este valida, False, in caz contrar
    """
    try:
        assert valideaza_numar_apartament(c[0])
        assert valideaza_suma(c[1])
        assert valideaza_tip(c[2])
        assert valideaza_zi(c[3])
        return True
    except AssertionError:
        return False

def existenta_cheltuiala(cheltuieli,c):
    """
    Verifica daca o cheltuiala se afla in entitatea ce contine toate cheltuielile unui bloc de apartamente
    :param cheltuieli: entitate ce contine toate cheltuielile unui bloc de apartamente
    :param c: o cheltuiala
    :return: True, daca se afla, False, in caz contrar
    """
    if type(cheltuieli)==list:
        if c in cheltuieli:
            return True
        else:
            return False
    else:
        x,y,z,t = c
        x = int(x)
        y = int(y)
        t = int(t)
        for n in range(0,len(cheltuieli ["nr_apartament"])):
            if x==cheltuieli ["nr_apartament"] [n] and y==cheltuieli ["suma"] [n] and z==cheltuieli ["tip"] [n] and t== \
                    cheltuieli ["zi"] [n]:
                return True
        return False

def vid(cheltuieli):
    """
    Verifica daca entitatea ce contine toate cheltuielile unui bloc de apartamente este vida
    :param cheltuieli: entitate ce contine toate cheltuielile unui bloc de apartamente
    :return: True, daca este vida, False, in caz contrar
    """
    if type(cheltuieli)==list:
        if cheltuieli==[]:
            return True
    elif type(cheltuieli)==dict and cheltuieli["nr_apartament"]==[]:
        return True
    return False