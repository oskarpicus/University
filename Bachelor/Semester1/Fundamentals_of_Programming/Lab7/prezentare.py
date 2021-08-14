""" Modulul prezentare """
from exceptii import RepoError,ValidError
from tabulate import tabulate
from random import randint,choice
from domain import get_grup,get_nume,get_studentid,cmp_id
import string
class Consola:
    """
    Tip abstract de date consola
    Domain: {srv_student,srv_problema - srv_student ServiceStudent, srv_problema ServiceProblema}
    """
    def __init__(self,srv_student,srv_problema,srv_note,srv_asig):
        """
        Initializeaza un obiect de tip Consola
        :param srv_student: ServiceStudent
        :param srv_problema: ServiceProblema
        """
        self.srv_asig=srv_asig
        self.srv_student=srv_student
        self.srv_problema=srv_problema
        self.srv_note=srv_note
        self.__comenzi={"18":self.ui_sortare,"17":self.ui_raport_lab,"10":self.ui_asignare_student_problema,"13":self.ui_afisare_dupa_medie,"12":self.ui_afisare_dupa_nota,"14":self.ui_asignare_nota,"1":self.ui_adauga_student,"7":self.afiseaza_lista_studenti,"2":self.ui_adauga_problema,"15":self.afiseaza_lista_probleme,"8":self.ui_cauta_student
                        , "9":self.ui_cauta_problema,"3":self.ui_sterge_student,"6":self.ui_modificare_student,"4":self.ui_sterge_problema,"5":self.ui_modificare_problema,"16":self.ui_studenti_random,"11":self.ui_afisare_dupa_nume}

    def ui_sortare(self):
        """
        Metoda pentru sortare
        :return:
        """
        """while True:
            print("Selectati cheia dorita: ")
            print("1 - Dupa ID ")
            print("2 - Dupa nume")
            print("3 - Dupa numarul grupei")
            cheie=input()
            if cheie=="1" or cheie=="2" or cheie=="3":
                break
        cheie=int(cheie)"""
        while True:
            print("Doriti ascendent? (Da/Nu)")
            rvs=input()
            if rvs=="Da" or rvs=="Nu":
                break
        """if cheie==1:
            cheie=get_studentid
        elif cheie==2:
            cheie=get_nume
        elif cheie==3:
            cheie=get_grup"""
        if rvs=="Da":
            rvs=False
        else:
            rvs=True
        compare=cmp_id
        l=self.srv_student.sortare(rvs,compare)
        print("Aceasta este lista sortata: ")
        rez = []
        for i in l:
            rez.append([str(i.get_studentid()),str(i.get_nume()),str(i.get_grup())])
        print(tabulate(rez, headers=["ID", "Nume","Grup"]))

    def ui_raport_lab(self):
        """
        Metoda pentru afisarea laboratoarelor dupa medie
        :return:
        """
        lista=self.srv_note.raport_lab()
        if lista==[]:
            print("Nu exista nicio nota adaugata ")
        else:
            l=[]
            for i in lista:
                l.append([str(i.get_nume()),str(i.get_medie())])
            print(tabulate(l,headers=["Numar laborator","Medie"]))

    def ui_asignare_student_problema(self):
        """
        Metoda pentru asignarea unei probleme la un student
        :return: -, raises exception daca datele sunt invalide
        """
        studentid=self.citire_studentid()
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        mesaj=""
        if not self.tip_numeric(studentid):
            mesaj+="ID student invalid \n"
        if not self.tip_numeric(numar_laborator):
            mesaj+="Numar laborator invalid \n"
        if not self.tip_numeric(numar_problema):
            mesaj+="Numar problema invalida \n"
        if mesaj!="":
            raise ValueError(mesaj)
        self.srv_asig.asignare_student_problema(int(studentid),int(numar_laborator),int(numar_problema))
        print("Asignarea a fost facuta cu succes!\nLista asignarilor este: ")
        asig=self.srv_asig.get_all() #lista cu asignari
        #se afiseaza toate asignarile
        lista=[]
        for i in asig:
            lista.append([i.get_student().get_nume(),str(i.get_problema().get_numar_laborator()),str(i.get_problema().get_numar_problema())])
        print(tabulate(lista,headers=["Nume","Numar laborator","Numar problema"]))

    def ui_afisare_dupa_medie(self):
        """
        Afiseaza studentii cu media notelor mai mica decat 5
        :return: -
        """
        lista=self.srv_note.afisare_dupa_medie()
        if lista==[]:
            print("Niciun student nu are media mai mica decat 5")
        else:
            l=[]
            for i in lista:
                l.append([str(i.get_nume()),str(i.get_medie())])
            print(tabulate(l,headers=["Nume","Medie"]))

    def ui_afisare_dupa_nota(self):
        """
        Afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat după nota
        :return: -, raises exception daca datele nu sunt valide
        """
        numar_laborator = self.citire_numar_laborator()
        numar_problema = self.citire_numar_problema()
        if not self.tip_numeric(numar_problema) or not self.tip_numeric(numar_laborator):
            raise ValueError("Date invalide ")
        note=self.srv_note.afisare_dupa_nota(int(numar_laborator),int(numar_problema))
        lista = []
        for i in note:
            lista.append([str(i.student.get_studentid()),str(i.student.get_nume()),str(i.nota)])
        if lista==[]:
            print("Nu exista niciun student cu note la problema data ")
        else:
            print(tabulate(lista,headers=["ID","Nume","Nota"]))

    def ui_afisare_dupa_nume(self):
        """
        Primeste input-ul necesar functiei Afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat alfabetic după nume
        :return: -, raise exception daca datele nu sunt valide
        """
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        if not self.tip_numeric(numar_problema) or not self.tip_numeric(numar_laborator):
            raise ValueError("Date invalide ")
        note=self.srv_note.afisare_dupa_nume(int(numar_laborator),int(numar_problema))
        lista=[]
        for i in note:
            lista.append([str(i.get_student().get_studentid()),str(i.get_student().get_nume()),str(i.nota)])
        if lista==[]:
            print("Nu exista niciun student cu note la problema data ")
        else:
            print(tabulate(lista,headers=["ID","Nume","Nota"]))

    def ui_studenti_random(self):
        """
        Adauga in repo studenti random
        :return:
        """
        numar=input("Dati cati studenti doriti sa adaugati ")
        if not self.tip_numeric(numar):
            print("Valoare invalida ")
        else:
            numar=int(numar)
            for i in range(0,numar):
                studentid=randint(1,100000000000000000000000000000000)
                nume=''.join(choice(string.ascii_uppercase) for _ in range(6))
                grup=randint(1,100000000000000000000000000000000000)
                try:
                    self.srv_student.adauga_student(studentid,nume,grup)
                except RepoError:
                    i-=1
            print("Adaugarile au fost facute cu succes ")

    def afiseaza_lista_studenti(self):
        """
        Afiseaza lista de studenti
        :return: -, afiseaza lista de studenti
        """
        if len(self.srv_student.get_studenti())==0:
            print("Nu exista niciun student adaugat! ")
        else:
            lista=[]
            studenti=self.srv_student.get_studenti()
            for i in studenti:
                l=[str(i.get_studentid()),str(i.get_nume()),str(i.get_grup())]
                lista.append(l)
            print(tabulate(lista,headers=["ID","Nume","Grup"]))

    def afiseaza_lista_probleme(self):
        """
        Afiseaza lista de probleme de laborator
        :return: -, afiseaza lista de probleme de laborator
        """
        if self.srv_problema.get_probleme()==[]:
            print("Nu exista nicio problema adaugata! ")
        else:
            lista=[]
            probleme=self.srv_problema.get_probleme()
            for i in probleme:
                l=[str(i.get_numar_laborator()),str(i.get_numar_problema()),str(i.get_descriere()),[(i.get_zi()) ,(i.get_luna()),(i.get_an())]]
                lista.append(l)
            print(tabulate(lista,headers=["Numar Laborator","Numar problema","Descriere","Deadline (DD.MM.YY)"]))

    def ui_cauta_student(self):
        """
        Primeste input-ul necesar functiei cauta_student si valideaza tipul numeric
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        studentid = self.citire_studentid()
        mesaj=""
        if not self.tip_numeric(studentid):
            mesaj += "ID student invalid \n"
        if mesaj!="":
            raise ValueError(mesaj)
        if type(self.srv_student.cauta_student(int(studentid)))!=None:
            print(f"Studentul cu ID-ul {studentid} exista in baza de date! ")
        else:
            print(f"Studentul cu ID-ul {studentid} nu exista in baza de date! ")

    def ui_cauta_problema(self):
        """
        Primeste input-ul necesar functiei cauta_problema si valideaza tipul numeric
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        mesaj=""
        if not self.tip_numeric(numar_laborator):
            mesaj+="Numar de laborator invalid \n"
        if not self.tip_numeric(numar_problema):
            mesaj+="Numar de problema invalid \n"
        if mesaj!="":
            raise ValueError(mesaj)
        if type(self.srv_problema.cauta_problema(int(numar_laborator),int(numar_problema)))!=None:
            print(f"Problema {numar_problema} din laborator {numar_laborator} exista in baza de date!")
        else:
            print(f"Problema {numar_problema} din laborator {numar_laborator} nu exista in baza de date!")

    def ui_adauga_student(self):
        """
        Primeste input-ul necesar functiei adauga student si valideaza tipul numeric
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        studentid=self.citire_studentid()
        nume=self.citire_nume()
        grupa=self.citire_grupa()
        mesaj=""
        if not self.tip_numeric(studentid):
            mesaj+="ID student invalid \n"
        if not self.tip_numeric(grupa):
            mesaj+="Grupa student invalida \n"
        if mesaj!="":
            raise ValueError(mesaj)
        self.srv_student.adauga_student(int(studentid),nume,int(grupa))
        print("Adaugarea a fost facuta cu succes ")

    def ui_adauga_problema(self):
        """
        Primeste input-ul necesar functiei adauga problema si valideaza tipul numeric
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        descriere=self.citire_descriere()
        deadline=self.citire_deadline()
        deadline=deadline.split(".")
        mesaj=""
        if not self.tip_numeric(numar_laborator):
            mesaj+="Numar de laborator invalid \n"
        if not self.tip_numeric(numar_problema):
            mesaj+="Numar de problema invalid \n"
        if len(deadline)!=3:
            mesaj+="Deadline invalid \n"
        else:
            zi,luna,an=deadline
            if not self.tip_numeric(zi):
                mesaj+="Ziua din deadline este invalida \n"
            if not self.tip_numeric(luna):
                mesaj+="Luna din deadline este invalida \n"
            if not self.tip_numeric(an):
                mesaj+="Anul din deadline este invalid"
        if mesaj!="":
            raise ValueError(mesaj)
        zi,luna,an = deadline
        self.srv_problema.adauga_problema(int(numar_laborator),int(numar_problema),descriere,int(zi),int(luna),int(an))
        print("Adaugarea a fost facuta cu succes ")

    def ui_modificare_problema(self):
        """
        Primeste input-ul necesar functiei modificare_problema
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        numar_laborator=self.citire_numar_laborator()
        if not self.tip_numeric(numar_laborator):
            raise ValueError("Numar de laborator invalid \n")
        numar_problema=self.citire_numar_problema()
        if not self.tip_numeric(numar_problema):
            raise ValueError("Numar de problema invalid \n")
        print("Dati datele problemei modificate ")
        descriere = self.citire_descriere()
        deadline = self.citire_deadline()
        deadline = deadline.split(".")
        mesaj = ""
        if len(deadline)!=3:
            mesaj += "Deadline invalid \n"
        else:
            zi,luna,an = deadline
            if not self.tip_numeric(zi):
                mesaj += "Ziua din deadline este invalida \n"
            if not self.tip_numeric(luna):
                mesaj += "Luna din deadline este invalida \n"
            if not self.tip_numeric(an):
                mesaj += "Anul din deadline este invalid"
        if mesaj!="":
            raise ValueError(mesaj)
        zi,luna,an = deadline
        self.srv_problema.modificare_problema(int(numar_laborator),int(numar_problema),descriere,int(zi),int(luna),int(an))
        print("Modificarea a fost facuta cu succes ")

    def ui_sterge_student(self):
        """
        Primeste input-ul necesar functiei sterge_student
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        studentid=self.citire_studentid()
        if not self.tip_numeric(studentid):
            raise ValueError("ID student invalid \n")
        self.srv_student.sterge_student(int(studentid))
        print("Studentul a fost sters cu succes! ")

    def ui_sterge_problema(self):
        """
        Primeste input-ul necesar functiei sterge_problema
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        mesaj=""
        if not self.tip_numeric(numar_laborator):
            mesaj+="Numar de laborator invalid \n"
        if not self.tip_numeric(numar_problema):
            mesaj+="Numar de problema invalid \n"
        if mesaj!="":
            raise ValueError(mesaj)
        self.srv_problema.sterge_problema(int(numar_laborator),int(numar_problema))
        print("Problema a fost stearsa cu succes! ")

    def ui_modificare_student(self):
        """
        Primeste input-ul necesar functiei modificare_student
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        studentid=self.citire_studentid()
        if not self.tip_numeric(studentid):
            raise ValueError("ID student invalid \n")
        print("Dati datele ce vor modifica studentul ")
        nume=self.citire_nume()
        grupa=self.citire_grupa()
        if not self.tip_numeric(grupa):
            raise ValueError("Grupa student invalida \n")
        self.srv_student.modificare_student((int)(studentid),nume,int(grupa))
        print("Studentul a fost modificat cu succes ")


    def ui_asignare_nota(self):
        """
        Primeste input-ul necesar functiei asignare_nota
        :return: -, raises exception daca nu se valideaza tipul numeric
        """
        studentid=self.citire_studentid()
        numar_laborator=self.citire_numar_laborator()
        numar_problema=self.citire_numar_problema()
        nota=input("Dati nota ")
        mesaj=""
        if not self.tip_numeric(studentid):
            mesaj+="ID student invalid \n"
        if not self.tip_numeric(numar_laborator):
            mesaj+="Numar de laborator invalid \n"
        if not self.tip_numeric(numar_problema):
            mesaj+="Numar de problema invalida"
        try:
            nota=float(nota)
        except ValueError:
            mesaj+="Nota invalida"
        if mesaj!="":
            raise ValueError(mesaj)
        self.srv_note.asignare_nota(int(studentid),int(numar_laborator),int(numar_problema),float(nota))
        print("Nota a fost adaugata cu succes! ")


    def run(self): #recursiv
        """
        Metoda pentru afisarea meniului utilizatorului. Primeste comenzi de la acesta
        :return: -
        """
        print("Alegeti functionalitatea dorita ")
        #while True:
        print("1 - Adaugarea unui nou student ")
        print("2 - Adaugarea unei noi probleme de laborator ")
        print("3 - Stergerea unui student ")
        print("4 - Stergerea unei probleme laborator ")
        print("5 - Modificarea unei probleme de laborator ")
        print("6 - Modificarea datelor unui student ")
        print("7 - Afisarea studentilor ")
        print("8 - Cautarea unui student ")
        print("9 - Cautarea unei probleme de laborator ")
        print("10 - Asignarea unei probleme de laborator unui student ")
        print("11 - Afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat alfabetic după nume ")
        print("12 - Afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat după nota ")
        print("13 - Afisarea tuturor studenților cu media notelor de laborator mai mica decât 5. (nume student și notă) ")
        print("14 - Asignarea unei note unui student ")
        print("15 - Afisarea problemelor de laborator ")
        print("16 - Adauga un numar de studenti cu date random")
        print("17 - Raport lab-uri dupa medie ")
        print("18 - Sortare lista studenti dupa un criteriu anume ")
        print("0 - Iesire din aplicatie ")
        cmd=input()
        self.srv_student.repo_student.load_from_file()
        if cmd=="0":
            return
        elif cmd in self.__comenzi:
            try:
                self.__comenzi[cmd]()
            except ValueError as mesaj:
                print("Eroare UI:\n",mesaj)
            except ValidError as mesaj:
                print("Eroare Service: \n",mesaj)
            except RepoError as mesaj:
                print("Eroare Repo: \n",mesaj)
        else:
            print("Comanda invalida! ")
        self.run()

    def tip_numeric(self,x):
        """
        Verifica daca o valoare poate fi convertita la tipul numeric
        :param x: string
        :return: True, daca poate fi convertita, False, in caz contrar
        """
        try:
            x=int(x)
            return True
        except ValueError:
            return False

    def citire_studentid(self):
        """
        Functie pentru citirea unui ID de student
        :return: ID-ul studentului citit
        """
        return input("Dati ID-ul studentului ")

    def citire_nume(self):
        """
        Functie pentru citirea unui nume de student
        :return: numele studentului citit
        """
        return input("Dati numele studentului ")

    def citire_grupa(self):
        """
        Functie pentru citirea grupei unui student
        :return: grupa studentului citit
        """
        return input("Dati grupa studentului ")

    def citire_numar_laborator(self):
        """
        Functie pentru citirea numarului de laborator unei probleme
        :return: numarul de laborator a problemei citite
        """
        return input("Dati numarul laboratorului ")

    def citire_numar_problema(self):
        """
        Functie pentru citirea numarului problemei unei probleme
        :return: numarul de problema a problemei citite
        """
        return input("Dati numarul problemei ")

    def citire_descriere(self):
        """
        Functie pentru citirea descrierii unei probleme de laborator
        :return: descrierea problemei citite
        """
        return input("Dati descrierea problemei de laborator ")

    def citire_deadline(self):
        """
        Functie pentru citirea deadline-ului unei probleme de laborator
        :return: deadline-ul problemei citite
        """
        return input("Dati deadline-ul problemei de laborator in format DD.MM.YY ")
