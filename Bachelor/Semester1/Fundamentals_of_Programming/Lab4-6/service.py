""" Modulul ce contine functiile care coordoneaza activitatea modelului, validatorului, repo-ului si domain-ului"""
from valid import valideaza_cheltuiala,existenta_cheltuiala,valideaza_numar_apartament,vid,valideaza_tip,valideaza_suma, valideaza_zi
from repo import *
from domain import *

def srv_adauga_cheltuiala(cheltuieli,nr_apartament,suma,tip,zi,undolist):
    """
    Functie care creaza o noua cheltuiala pe baza nr_apartament,suma,tip,zi, o valideaza si daca e valida, o adauga in repo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :param suma: int
    :param tip: string
    :param zi: int
    :param undolist: list
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    c=creeaza_c(int(nr_apartament),int(suma),tip,int(zi))
    try:
        assert valideaza_cheltuiala(c)
        repo_adauga_cheltuiala(cheltuieli,c,undolist)
        return True
    except:
        return False

def srv_modifica_cheltuiala(cheltuieli,nr_apartament,suma,tip,zi,nr_apartament1,suma1,tip1,zi1,undolist):
    """
    Functie care creaza doua cheltuieli pe baza datelor de intrare, le valideaza si daca sunt valide, iar prima se
    regaseste in cheltuieli, le adauga in repo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :param suma: int
    :param tip: string
    :param zi: int
    :param nr_apartament1: int
    :param suma1: int
    :param tip1: string
    :param zi1: int
    :param undolist: list
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    c1=creeaza_c(int(nr_apartament),int(suma),tip,int(zi))
    c2=creeaza_c(int(nr_apartament1),int(suma1),tip1,int(zi1))
    try:
        assert valideaza_cheltuiala(c1)
        assert valideaza_cheltuiala(c2)
        if not existenta_cheltuiala(cheltuieli,c1):
            raise NameError
        repo_modifica_cheltuiala(cheltuieli,c1,c2,undolist)
        return True
    except AssertionError:
        return False
    except NameError:
        return None

def srv_sterge_toate(cheltuieli,nr_apartament,undolist):
    """
    Functie care sterge toate cheltuielile de la un apartament. Verifica daca numarul de apartament este valid, iar
    in caz afirmativ, il returneaza lui repo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :param undolist: list
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if not vid(cheltuieli):
        if valideaza_numar_apartament(int(nr_apartament)):
            repo_sterge_toate(cheltuieli,int(nr_apartament),undolist)
            return True
        else:
            return False
    else:
        return None

def srv_sterge_consecutive(cheltuieli,inceput,final,undolist):
    """
    Functie care sterge cheltuielile de la apartamentele cuprinse in intervalul [inceput, final]. Verifica daca inceput
    si final sunt valide, iar in caz afirmativ, le returneaza la repo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param inceput: int
    :param final: int
    :param undolist: string
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if not vid(cheltuieli):
        if valideaza_numar_apartament(int(inceput)) and valideaza_numar_apartament(int(final)):
            repo_sterge_consecutive(cheltuieli,int(inceput),int(final),undolist)
            return True
        else:
            return False
    else:
        return None


def srv_sterge_dupa_tip(cheltuieli,tip,undolist):
    """
    Functie care sterge toate cheltuielile de un tip. Verifica daca tip este valid, iar in caz afirmativ, il
    returneaza la repo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: int
    :param undolist: list
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_tip(tip) and not vid(cheltuieli):
        repo_sterge_dupa_tip(cheltuieli,tip,undolist)
        return True
    else:
        return False

def srv_cheltuieli_suma_data(cheltuieli,suma):
    """
    Functie care construieste o lista cu apartamentele care au cheltuieli mai mari decat o suma data. Valideaza suma,
    iar daca este valida, o returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param suma: int
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_suma(int(suma)):
        return dm_cheltuieli_suma_data(cheltuieli,int(suma))
    else:
        return False


def srv_cheltuiala_tip (cheltuieli,tip):
    """
    Functie care construieste o lista cu cheltuielile de un anumit tip. Verifica daca parametrul tip este valid, iar
    daca este valid, il returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_tip(tip):
        return dm_cheltuiala_tip(cheltuieli,tip)
    else:
        return False

def srv_cheltuiala_zi_suma(cheltuieli,zi,suma):
    """
    Functie care construieste o lista cu cheltuielile efectuate inainte de o zi si mai mari decat o suma.
    Verifica daca parametrii sunt valizi, iar in caz afirmativ, ii returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param zi: int
    :param suma: int
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_suma(int(suma)) and valideaza_zi(int(zi)):
        return dm_cheltuiala_zi_suma(cheltuieli,int(zi),int(suma))
    else:
        return False

def srv_elimina_cheltuiala_mica(cheltuieli,suma):
    """
    Functie care elimina toate cheltuielile mai mici decat o suma data. Verifica daca suma este valida, iar in caz
    afirmativ, o returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param suma: int
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_suma(int(suma)):
        return dm_elimina_cheltuiala_mica(cheltuieli,int(suma))
    else:
        return False

def srv_elimina_cheltuiala_tip(cheltuieli,tip):
    """
    Functie care elimina toate cheltuielile de un anumit tip. Verifica daca tipul dat este valid, iar in caz afirmativ,
    il returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_tip(tip):
        return dm_elimina_cheltuiala_tip(cheltuieli,tip)
    else:
        return False

def srv_suma_dupa_tip(cheltuieli,tip):
    """
    Functie care calculeaza suma cheltuielilor de un anumit tip. Verifica daca tipul dat este valid, iar in caz
    afirmativ, il returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_tip(tip):
        return dm_suma_dupa_tip(cheltuieli,tip)
    else:
        return False

def srv_sortare_tip_cheltuiala(cheltuieli,tip):
    """
    Functie care construieste o lista cu apartamentele sortate dupa un tip de cheltuiala. Verifica daca tipul dat
    este valid, iar in caz afirmativ, il returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_tip(tip):
        return dm_sortare_tip_cheltuiala(cheltuieli,tip)
    else:
        return False

def srv_total_per_apartament(cheltuieli,nr_apartament):
    """
    Functie care calculeaza totalul de cheltuieli pentru un apartament dat. Verifica daca numarul de apartament dat
    este valid, iar in caz afirmativ, il returneaza la domain
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :return: True, datele de intrare nu sunt corect d.p.d.v. logic, False, in caz contrar
    """
    if valideaza_numar_apartament(int(nr_apartament)):
        return dm_total_per_apartament(cheltuieli,int(nr_apartament))
    else:
        return False

def srv_undo(cheltuieli,undolist):
    """
    Anuleaza ultima operatie facuta
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param undolist: list, reprezinta starile prin care a trecut entitatea cheltuieli
    :return: cheltuieli, actualizata
    """
    undolist.pop()
    if len(undolist)==0:
        cheltuieli = deepcopy(creeaza_cheltuieli())
    else:
        cheltuieli = deepcopy(undolist [-1])
    return deepcopy(cheltuieli)