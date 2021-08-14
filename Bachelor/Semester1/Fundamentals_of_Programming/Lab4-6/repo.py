""" Modul ce se asigura de unicitatea elementelor si este responsabil de modificarea in orice fel a entitatii
care contine toate cheltuielile de la blocul de apartamente"""

from copy import deepcopy
def repo_adauga_cheltuiala(cheltuieli,c,undolist):
    """
    Functie ce adauga cheltuiala c in entitatea cheltuieli
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param c: o cheltuiala
    :param undolist: list
    :return: cheltuieli actualizata cu adaugarea lui c
    """
    if type(cheltuieli)==list:
        cheltuieli.append(c)
    else:
        cheltuieli ["nr_apartament"].append(get_nr_apartament(c))
        cheltuieli ["suma"].append(get_suma(c))
        cheltuieli ["tip"].append(get_tip(c))
        cheltuieli ["zi"].append(get_zi(c))
    undolist.append(deepcopy(cheltuieli))
    return cheltuieli

def repo_modifica_cheltuiala(cheltuieli,c1,c2,undolist):
    """
    Functie ce modifica entitatea cheltuieli, inlocuind cheltuiala c1 cu cheltuiala c2
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param c1: o cheltuiala
    :param c2: o cheltuiala
    :param undolist: list
    :return: cheltuieli actualizata cu inlocuirea lui c1 cu c2
    """
    if type(cheltuieli)==list:
        for n,i in enumerate(cheltuieli):
            if i==c1:
                cheltuieli [n] = c2
                break
    else:
        x=get_nr_apartament(c1)
        y=get_suma(c1)
        z=get_tip(c1)
        t=get_zi(c1)
        a=get_nr_apartament(c2)
        b=get_suma(c2)
        c=get_tip(c2)
        d=get_zi(c2)
        for n in range(0,len(cheltuieli ["nr_apartament"])):
            ch=[cheltuieli["nr_apartament"][n],cheltuieli["suma"][n],cheltuieli["tip"][n],cheltuieli["zi"][n]]
            if x==get_nr_apartament(ch) and y==get_suma(ch) and z==get_tip(ch) and t== get_zi(ch):
                cheltuieli ["nr_apartament"] [n] = a
                cheltuieli ["suma"] [n] = b
                cheltuieli ["tip"] [n] = c
                cheltuieli ["zi"] = d
                break
    undolist.append(deepcopy(cheltuieli))
    return cheltuieli

def repo_sterge_toate(cheltuieli,nr_apartament,undolist):
    """
    Functie ce sterge toate cheltuielile de la apartamentul nr_apartament din entitatea cheltuieli
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :param undolist: list
    :return: cheltuieli actualizata cu eliminarea cheltuielilor de la apartamentul nr_apartament
    """
    if type(cheltuieli)==list:
        ok = True
        while ok==True:
            ok = False
            for i in cheltuieli:
                if get_nr_apartament(i)==nr_apartament:
                    cheltuieli.remove(i)
                    ok = True
    else:
        ok = True
        while ok==True:
            ok = False
            for i in range(0,len(cheltuieli ["nr_apartament"])):
                if i < len(cheltuieli ["nr_apartament"]):
                    if cheltuieli ["nr_apartament"] [i]==nr_apartament:
                        cheltuieli ["nr_apartament"].remove(cheltuieli ["nr_apartament"] [i])
                        cheltuieli ["suma"].remove(cheltuieli ["suma"] [i])
                        cheltuieli ["tip"].remove(cheltuieli ["tip"] [i])
                        cheltuieli ["zi"].remove(cheltuieli ["zi"] [i])
                        ok = True
    undolist.append(deepcopy(cheltuieli))
    return cheltuieli

def repo_sterge_consecutive(cheltuieli,inceput,final,undolist):
    """
    Functie care sterge cheltuielile apartamentelor aflate in intervalul [inceput,final] din entitatea cheltuieli
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param inceput: int, un numar de apartament
    :param final: int, un numar de apartament
    :param undolist: list
    :return: entitatea cheltuieli actualizata cu stergerea mentionata
    """
    if type(cheltuieli)==list:
        ok = True
        while ok==True:
            ok = False
            for i in cheltuieli:
                if get_nr_apartament(i) >= inceput and get_nr_apartament(i) <= final:
                    cheltuieli.remove(i)
                    ok = True
    else:
        ok = True
        while ok==True:
            ok = False
            for i in range(0,len(cheltuieli ["nr_apartament"])):
                if i < len(cheltuieli ["nr_apartament"]):
                    if cheltuieli ["nr_apartament"] [i] >= inceput and final >= cheltuieli ["nr_apartament"] [i]:
                        cheltuieli ["nr_apartament"].remove(cheltuieli ["nr_apartament"] [i])
                        cheltuieli ["suma"].remove(cheltuieli ["suma"] [i])
                        cheltuieli ["tip"].remove(cheltuieli ["tip"] [i])
                        cheltuieli ["zi"].remove(cheltuieli ["zi"] [i])
                        ok = True
        undolist.append(deepcopy(cheltuieli))
        return cheltuieli

def repo_sterge_dupa_tip(cheltuieli,tip,undolist):
    """
    Functie care sterge cheltuielile de tipul tip din entitatea cheltuieli
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string, un tip de cheltuiala
    :param undolist: list
    :return: entitatea cheltuieli actualizata cu stergerea mentionata
    """
    if type(cheltuieli)==list:
        ok = True
        while ok==True:
            ok = False
            for i in cheltuieli:
                if get_tip(i)==tip:
                    cheltuieli.remove(i)
                    ok = True
    else:
        ok = True
        while ok==True:
            ok = False
            for i in range(0,len(cheltuieli ["nr_apartament"])):
                if i < len(cheltuieli ["nr_apartament"]):
                    if cheltuieli ["tip"] [i]==tip:
                        cheltuieli ["nr_apartament"].remove(cheltuieli ["nr_apartament"] [i])
                        cheltuieli ["suma"].remove(cheltuieli ["suma"] [i])
                        cheltuieli ["tip"].remove(cheltuieli ["tip"] [i])
                        cheltuieli ["zi"].remove(cheltuieli ["zi"] [i])
                        ok = True
    undolist.append(deepcopy(cheltuieli))
    return cheltuieli


def get_nr_apartament(c):
    """
    Functie care returneaza numarul de apartament al unei cheltuieli
    :param c: o cheltuiala
    :return: numarul de apartament
    """
    return c[0]

def get_suma(c):
    """
    Functie care returneaza suma al unei cheltuieli
    :param c: o cheltuiala
    :return: suma
    """
    return c[1]

def get_tip(c):
    """
    Functie care returneaza tipul unei cheltuieli
    :param c: o cheltuiala
    :return: tip
    """
    return c[2]

def get_zi(c):
    """
    Functie care returneaza ziua unei cheltuieli
    :param c: o cheltuiala
    :return: zi
    """
    return c[3]

def setter_nr_apartament(c,nr_apartament):
    """
    Functie care seteaza numarul de apartament al unei cheltuieli la nr_apartament
    :param c: o cheltuiala
    :param nr_apartament: int, un numar de apartamente
    :return: -, cheltuiala are numarul de apartament egal cu paramentrul nr_apartament
    """
    c[0]=nr_apartament
    return c

def setter_suma(c,suma):
    """
    Functie care seteaza suma unei cheltuieli la suma
    :param c: o cheltuiala
    :param suma: int, suma unei cheltuieli
    :return: -, cheltuiala are suma egala cu paramentru suma
    """
    c[1]=suma
    return c

def setter_tip(c,tip):
    """
    Functie care seteaza tipul unei cheltuieli la tip
    :param c: o cheltuiala
    :param tip: string, tipul unei cheltuieli
    :return: -, cheltuiala are tipul egal cu parametrul tip
    """
    c[2]=tip
    return c

def setter_zi(c,zi):
    """
    Functie care seteaza ziua unei cheltuieli la zi
    :param c: o cheltuiala
    :param zi: int, ziua efectuarii unei cheltuieli
    :return: -, cheltuiala are ziua egala cu parametrul zi
    """
    c[3]=zi
    return c
