"""
Modulul ce contine functiile pentru interfata cu utilizatorul
"""
from service import *
from model import creeaza_cheltuieli
undolist=[] #lista ce va contine toate starile prin care trece entitatea cheltuieli

def meniu_nou():
    global undolist
    cheltuieli = creeaza_cheltuieli()
    while True:
        print("Alegeti functia dorita. Intre comenzi se va pune virgula! ")
        print("add - Adaugare cheltuiala (se da numarul apartament, suma, tipul, ziua) ")
        print("delete - Stergere toate cheltuielile de la un apartament (se da numarul de apartament) ")
        print("rapoarte - Tipărește suma totală pentru un tip de cheltuială (se da tipul) ")
        print("cauta - Tipărește cheltuielile de un anumit tip de la toate apartamentele (se da tipul) ")
        print("print - Afisarea cheltuielilor ")
        print("exit - Iesire din aplicatie")
        comenzi={ "add":srv_adauga_cheltuiala,"delete":srv_sterge_toate,"rapoarte":srv_suma_dupa_tip,"cauta":srv_cheltuiala_tip}
        l=input()
        lista=l.split(",")
        n=0
        while n<len(lista):
            copie=deepcopy(cheltuieli)
            comanda=lista[n].split(" ")
            if comanda[0]=="exit":
                return
            elif comanda[0]=="add":
                if not tip_numeric(comanda[1]) or not tip_numeric(comanda[2]) or not tip_numeric(comanda[4]):
                    print("Valori invalide! ")
                else:
                    comenzi[comanda[0]](cheltuieli,comanda[1],comanda[2],comanda[3],comanda[4],undolist)
                    if copie==cheltuieli:
                        print("Valorile nu sunt valide! ")
                    else:
                        print("cheltuiala a fost adaugata cu succes !")
            elif comanda[0]=="delete":
                comenzi[comanda[0]](cheltuieli,comanda[1],undolist)
                if copie==cheltuieli:
                    print("Valori invalide! ")
                else:
                    print("cheltuielile au fost sterse cu succes !")
            elif comanda[0]=="print":
                afisare(cheltuieli)
            else:
                if comenzi [comanda [0]](cheltuieli,comanda [1])==False:
                    print("Valorile nu sunt valide! ")
                else:
                    print(comenzi [comanda [0]](cheltuieli,comanda [1]))
            n+=1


def meniu():
    """Afiseaza meniul utilizatorului (categoriile) si primeste comenzi de la acesta"""
    comenzi = {"1": meniu_adaugare,"2": meniu_stergere,"3": meniu_cautari,"4": meniu_rapoarte,"5": meniu_filtru,
               "6": undo,"7": afisare}
    cheltuieli=creeaza_cheltuieli()
    while True:
        print("Selectati categoria dorita: ")
        print("1-Adaugare")
        print("2-Stergere")
        print("3-Cautari")
        print("4-Rapoarte")
        print("5-Filtru")
        print("6-Undo")
        print("7-Afisare")
        print("0-Inchidere aplicatie")
        optiune = input()
        if optiune=="0":
            print("Sa aveti o zi minunata!")
            return
        elif optiune=="6":
            cheltuieli=undo(cheltuieli)
        elif optiune in comenzi:
            comenzi [optiune](cheltuieli)
        else:
            print("Comanda invalida!")

def meniu_adaugare(cheltuieli):
    """Afiseaza meniul utilizatorului specific categoriei Adaugare si primeste comenzi de la acesta"""
    comenzi = {"1": ui_adaugare_cheltuiala,"2": ui_modificare_cheltuiala}
    while True:
        print("Selectati functia dorita: ")
        print("1-Adaugare cheltuiala pentru un apartament")
        print("2-Modificare cheltuiala")
        print("0-Alegere categorie diferita")
        optiune = input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi [optiune](cheltuieli)
        else:
            print("Comanda invalida")

def ui_adaugare_cheltuiala(cheltuieli):
    """
    Primeste input-ul necesar functiei Adaugare_cheltuiala si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    global undolist
    nr_apartament=citire_numar_apartament()
    suma=citire_suma()
    tip=citire_tip()
    zi=citire_zi()
    if tip_numeric(nr_apartament) and tip_numeric(suma) and tip_numeric(zi):
        if not srv_adauga_cheltuiala(cheltuieli,nr_apartament,suma,tip,zi,undolist):
            print("Valori invalide! ")
        else:
            print("Cheltuiala a fost adaugata cu succes! ")
    else:
        print("Nu ati dat valori valide ")
        ui_adaugare_cheltuiala(cheltuieli)

def ui_modificare_cheltuiala(cheltuieli):
    """
    Primeste input-ul necesar functiei Modificare_cheltuiala si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    global undolist
    print("Dati datele cheltuielii de modificat ")
    nr_apartament = citire_numar_apartament()
    suma = citire_suma()
    tip = citire_tip()
    zi = citire_zi()
    print("Dati datele cheltuielii modificate ")
    nr_apartament1 = citire_numar_apartament()
    suma1 = citire_suma()
    tip1 = citire_tip()
    zi1 = citire_zi()
    if tip_numeric(nr_apartament) and tip_numeric(suma) and tip_numeric(zi) and tip_numeric(nr_apartament1) and tip_numeric(suma1) and tip_numeric(zi1):
        if srv_modifica_cheltuiala(cheltuieli,nr_apartament,suma,tip,zi,nr_apartament1,suma1,tip1,zi1,undolist)==None:
            print("Cheltuiala de modificat nu exista ! ")
        elif srv_modifica_cheltuiala(cheltuieli,nr_apartament,suma,tip,zi,nr_apartament1,suma1,tip1,zi1,undolist)==False:
            print("Valori invalide! ")
        else:
            print("Cheltuiala a fost modificata cu succes ! ")
    else:
        print("Nu ati dat valori valide ")
        ui_modificare_cheltuiala(cheltuieli)

def meniu_stergere(cheltuieli):
    """Afiseaza meniul utilizatorului specific categoriei Stergere si primeste comenzi de la acesta"""
    comenzi = {"1": ui_sterge_toate_cheltuielile,"2": ui_sterge_consecutive,"3": ui_sterge_dupa_tip}
    while True:
        print("Selectati functia dorita: ")
        print("1-Șterge toate cheltuielile de la un apartament")
        print("2-Șterge cheltuielile de la apartamente consecutive")
        print("3-Șterge cheltuielile de un anumit tip de la toate apartamentele")
        print("0-Alegere categorie diferita")
        optiune = input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi [optiune](cheltuieli)
        else:
            print("Comanda invalida")

def ui_sterge_toate_cheltuielile(cheltuieli):
    """
    Primeste input-ul necesar functiei Sterge_toate_cheltuielile si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    global undolist
    nr_apartament=citire_numar_apartament()
    if tip_numeric(nr_apartament):
        if srv_sterge_toate(cheltuieli,nr_apartament,undolist)==True:
            print("Cheltuielile au fost sterse cu succes ! ")
        elif srv_sterge_toate(cheltuieli,nr_apartament,undolist)==None:
            print("Lista de cheltuieli este vida! ")
        else:
            print("Valori invalide! ")
    else:
        print("Nu ati dat valori valide ")
        ui_sterge_toate_cheltuielile(cheltuieli)

def ui_sterge_consecutive(cheltuieli):
    """
    Primeste input-ul necesar functiei Sterge_consecutive si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    global undolist
    print("Inceput ")
    inceput=citire_numar_apartament()
    print("Final ")
    final=citire_numar_apartament()
    if tip_numeric(inceput) and tip_numeric(final):
        if srv_sterge_consecutive(cheltuieli,inceput,final,undolist)==True:
            print("Cheltuielile au fost sterse cu succes! ")
        elif srv_sterge_consecutive(cheltuieli,inceput,final,undolist)==None:
            print("Nu exista nicio cheltuiala salvata! ")
        else:
            print("Valori invalide! ")
    else:
        print("Nu ati dat valori valide ")
        ui_sterge_consecutive(cheltuieli)

def ui_sterge_dupa_tip(cheltuieli):
    """
    Primeste input-ul necesar functiei Sterge_dupa_tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    global undolist
    tip=citire_tip()
    if srv_sterge_dupa_tip(cheltuieli,tip,undolist)==True:
        print("Cheltuielile au fost sterse cu succes! ")
    else:
        print("Nu exista acest tip de cheltuiala ")

def meniu_cautari(cheltuieli):
    """
    Afiseaza meniul utilizatorului si primeste comenzi de la acesta
    Va executa functiile din categoria Cautari pentru comanda aleasa
    """
    comenzi={"1":ui_cheltuieli_suma_data,"2":ui_cheltuiala_tip,"3":ui_cheltuiala_zi_suma}
    while True:
        print("Selectati functia dorita: ")
        print("1-Tipareste toate apartamentele care au cheltuieli mai mari decât o suma data")
        print("2-Tipareste cheltuielile de un anumit tip de la toate apartamentele")
        print("3-Tipareste toate cheltuielile efectuate inainte de o zi si mai mari decat o suma")
        print("0-Alegere categorie diferita")
        optiune=input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi[optiune](cheltuieli)
        else:
            print("Comanda invalida")

def ui_cheltuieli_suma_data(cheltuieli):
    """
    Primeste input-ul necesar functiei Cheltuieli_suma_data si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    suma=citire_suma()
    if tip_numeric(suma):
        if srv_cheltuieli_suma_data(cheltuieli,suma)==False:
            print("Valori invalide! ")
        else:
            print(srv_cheltuieli_suma_data(cheltuieli,suma))
    else:
        print("Nu ati dat valori valide ")
        ui_cheltuieli_suma_data(cheltuieli)

def ui_cheltuiala_tip(cheltuieli):
    """
    Primeste input-ul necesar functiei Cheltuiala_tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    tip=citire_tip()
    if srv_cheltuiala_tip(cheltuieli,tip)==False:
        print("Valori invalide! ")
    else:
        print(srv_cheltuiala_tip(cheltuieli,tip))

def ui_cheltuiala_zi_suma(cheltuieli):
    """
    Primeste input-ul necesar functiei Cheltuiala_zi_suma si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    zi=citire_zi()
    suma=citire_suma()
    if tip_numeric(zi) and tip_numeric(suma):
        if srv_cheltuiala_zi_suma(cheltuieli,zi,suma)==False:
            print("Valori invalide! ")
        else:
            print(srv_cheltuiala_zi_suma(cheltuieli,zi,suma))
    else:
        print("Nu ati dat valori invalide ")

def meniu_filtru(cheltuieli):
    """
    Afiseaza meniul utilizatorului si primeste comenzi de la acesta
    Va executa functiile din categoria Filtru pentru comanda aleasa
    """
    comenzi = {"1": ui_elimina_cheltuiala_tip,"2": ui_elimina_cheltuiala_mica}
    while True:
        print("Selectati functia dorita: ")
        print("1-Elimina toate cheltuielile de un anumit tip")
        print("2-Elimină toate cheluielile mai mici decât o sumă dată")
        print("0-Alegere categorie diferita")
        optiune=input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi[optiune](cheltuieli)
        else:
            print("Comanda invalida")

def ui_elimina_cheltuiala_tip(cheltuieli):
    """
    Primeste input-ul necesar functiei Elimina_cheltuiala_tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    tip=citire_tip()
    if srv_elimina_cheltuiala_tip(cheltuieli,tip)==False:
        print("Valori invalide! ")
    else:
        print(srv_elimina_cheltuiala_tip(cheltuieli,tip))

def ui_elimina_cheltuiala_mica(cheltuieli):
    """
    Primeste input-ul necesar functiei Elimina_cheltuiala_mica si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    suma=citire_suma()
    if tip_numeric(suma):
        if srv_elimina_cheltuiala_mica(cheltuieli,suma)==False:
            print("Valori invalide! ")
        else:
            print(srv_elimina_cheltuiala_mica(cheltuieli,suma))
    else:
        print("Nu ati dat valori valide ")
        ui_elimina_cheltuiala_mica(cheltuieli)

def meniu_rapoarte(cheltuieli):
    """
    Afiseaza meniul utilizatorului si primeste comenzi de la acesta
    Va executa functiile din categoria Rapoarte pentru comanda aleasa
    """
    comenzi={"1":ui_suma_dupa_tip,"2":ui_sortare_tip_cheltuiala,"3":ui_total_per_apartament}
    while True:
        print("Selectati functia dorita: ")
        print("1-Tipareste suma totala pentru un tip de cheltuiala")
        print("2-Tiparește toate apartamentele sortate după un tip de cheltuiala")
        print("3-Tiparește totalul de cheltuieli pentru un apartament dat")
        print("0-Alegere categorie diferita")
        optiune=input()
        if optiune=="0":
            return
        elif optiune in comenzi:
            comenzi[optiune](cheltuieli)
        else:
            print("Comanda invalida")

def ui_suma_dupa_tip(cheltuieli):
    """
    Primeste input-ul necesar functiei Suma_dupa_tip
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    tip=citire_tip()
    if srv_suma_dupa_tip(cheltuieli,tip)==False:
        print("Valori invalide! ")
    else:
        print(srv_suma_dupa_tip(cheltuieli,tip))

def ui_sortare_tip_cheltuiala(cheltuieli):
    """
    Primeste input-ul necesar functiei Sortare_tip_cheltuiala si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    tip=citire_tip()
    if srv_sortare_tip_cheltuiala(cheltuieli,tip)==False:
        print("Valori invalide! ")
    else:
        print(srv_sortare_tip_cheltuiala(cheltuieli,tip))

def ui_total_per_apartament(cheltuieli):
    """
    Primeste input-ul necesar functiei Total_per_apartament si verifica daca este de tip numeric
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza un mesaj corespunzator daca datele nu sunt de tip numeric
    """
    nr_apartament=citire_numar_apartament()
    if tip_numeric(nr_apartament):
        if srv_total_per_apartament(cheltuieli,nr_apartament)==False:
            print("Valori invalide! ")
        else:
            print(srv_total_per_apartament(cheltuieli,nr_apartament))
    else:
        print("Nu ati dat valori valide ")

def undo(cheltuieli):
    """
    Verifica daca se poate face undo
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return:
    """
    global undolist
    if len(undolist) <= 0:
        print("Nu mai puteti face undo! ")
    else:
        print("Undo cu succes! ")
        return deepcopy(srv_undo(cheltuieli,undolist))

########################################################################################################################

def afisare(cheltuieli):
    """
    Afiseaza entitatea ce memoreaza toate cheltuielile blocului de apartamente (adica afiseaza toate cheltuielile)
    :param cheltuieli: entitate ce memoreaza toate cheltuielile blocului de apartamente
    :return: -, afiseaza entitatea
    """
    print (cheltuieli)

def citire_numar_apartament():
    """
    Citeste o valoare ce ar trebui sa reprezinte un numar de apartament
    :return: valoarea citita
    """
    return input("Dati numarul apartamentului ")

def citire_suma():
    """
    Citeste o valoare ce ar trebui sa reprezinte o suma a unei cheltuieli
    :return: valoarea citita
    """
    return input("Dati suma cheltuielii ")

def citire_tip():
    """
    Citeste o valoare ce ar trebui sa reprezinte un tip de cheltuiala
    :return: valoarea citita
    """
    return input("Dati tipul cheltuielii ")

def citire_zi():
    """
        Citeste o valoare ce ar trebui sa reprezinte ziua efectuarii unei cheltuieli
        :return: valoarea citita
        """
    return input("Dati ziua cheltuielii ")

def tip_numeric(x):
    """
    Verifica daca o variabila poate fi convertita la tipul int
    :param x: string
    :return: True, daca poate fi convertita, False, in caz contrar
    """
    try:
        x=int(x)
        return True
    except:
        return False

