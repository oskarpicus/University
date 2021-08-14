""" Modul ce contine algoritmica necesara rezolvarii problemelor """
from model import *
from repo import get_suma,get_zi,get_tip,get_nr_apartament,repo_adauga_cheltuiala

def dm_cheltuieli_suma_data(cheltuieli,suma):
    """
    Functie care construieste o lista cu apartamentele care au cheltuieli mai mari decat o suma data
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param suma: int
    :return: lista cu apartamentele care au cheltuieli mai mari decat parametrul suma
    """
    rezultat=[]
    apartamente=lista_cu_apartamente(cheltuieli)
    for i in apartamente:
        if total_cheltuieli_per_apartament(cheltuieli,i)>suma:
            rezultat.append(i)
    return rezultat

def dm_cheltuiala_tip(cheltuieli,tip):
    """
    Functie care construieste o lista cu cheltuielile de un anumit tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: lista cu cheltuielile care au tipul egal cu parametrul tip
    """
    rezultat=[]
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_tip(i)==tip:
            rezultat.append(i)
    return rezultat

def dm_cheltuiala_zi_suma(cheltuieli,zi,suma):
    """
    Functie care construieste o lista cu cheltuielile efectuate inainte de o zi si mai mari decat o suma.
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param zi: int
    :param suma: int
    :return: lista cu cheltuielile efectuate inainte de zi si mai mare decat suma
    """
    rezultat=[]
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_zi(i)<zi and get_suma(i)>suma:
            rezultat.append(i)
    return rezultat

def total_cheltuieli_per_apartament(cheltuieli,nr_apartament):
    """
    Functie care calculeaza suma tuturor cheltuielilor unui apartament
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :return: suma tuturor cheltuielilor apartamentului nr_apartament
    """
    suma=0
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_nr_apartament(i)==nr_apartament:
            suma+=get_suma(i)
    return suma

def lista_cu_apartamente(cheltuieli):
    """
    Functie care construieste o lista cu toate apartamentele care au cheltuieli
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: lista cu toate apartamentele care au cheltuieli
    """
    rezultat=[]
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if not get_nr_apartament(i) in rezultat:
            rezultat.append(get_nr_apartament(i))
    return rezultat

def dm_elimina_cheltuiala_tip(cheltuieli,tip):
    """
    Functie care elimina toate cheltuielile de un anumit tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: rezultatul eliminarii tuturor cheltuielilor de tipul tip
    """
    rezultat=creeaza_cheltuieli()
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_tip(i)!=tip:
            nr_apartament = get_nr_apartament(i)
            s = get_suma(i)
            tip = get_tip(i)
            zi = get_zi(i)
            c = creeaza_c(nr_apartament,s,tip,zi)
            repo_adauga_cheltuiala(rezultat,c,[])
    return rezultat

def dm_elimina_cheltuiala_mica(cheltuieli,suma):
    """
    Functie care elimina toate cheltuielile mai mici decat o suma data
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param suma: int
    :return: rezultatul eliminarii tuturor cheltuielilor mai mici decat parametrul suma
    """
    rezultat = creeaza_cheltuieli()
    ch = create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_suma(i) >= suma:
            nr_apartament = get_nr_apartament(i)
            s = get_suma(i)
            tip = get_tip(i)
            zi = get_zi(i)
            c = creeaza_c(nr_apartament,s,tip,zi)
            repo_adauga_cheltuiala(rezultat,c,[])
    return rezultat

def dm_suma_dupa_tip(cheltuieli,tip):
    """
    Functie care calculeaza suma cheltuielilor de un anumit tip.
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: suma cheltuielilor de tipul tip
    """
    s=0
    ch=create_lista_cheltuieli(cheltuieli)
    for i in ch:
        if get_tip(i)==tip:
            s+=get_suma(i)
    return s

def dm_sortare_tip_cheltuiala(cheltuieli,tip):
    """
    Functie care construieste o lista cu apartamentele sortate dupa un tip de cheltuiala.
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param tip: string
    :return: lista cu apartamentele sortate dupa tipul tip
    """
    rezultat = []
    temporar = []
    lista=create_lista_cheltuieli(cheltuieli)
    for i in lista:
        if get_tip(i)==tip:
            rezultat.append(i)
        else:
            temporar.append(i)
    rezultat += temporar
    return rezultat

def dm_total_per_apartament(cheltuieli,nr_apartament):
    """
    Functie care calculeaza totalul de cheltuieli pentru un apartament dat.
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :param nr_apartament: int
    :return: suma cheltuielilor apartamentului dat
    """
    suma = 0
    lista=create_lista_cheltuieli(cheltuieli)
    for i in lista:
        if get_nr_apartament(i)==nr_apartament:
            suma += get_suma(i)
    return suma