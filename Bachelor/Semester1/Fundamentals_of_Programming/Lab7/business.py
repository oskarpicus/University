""" Modulul business """
from domain import Student,Problema,Nota,MedieDTO,AsignareStudentProblema,get_studentid,cmp_id,cmp_nume,cmp_nota,cmp_medie


def sortare (lista,reverse=False, compare=cmp_id):
    """
    Sorteaza prin insertion sort lista de studenti dupa o cheie si in functie de reverse
    :param cheie: o functie
    :param reverse: True/False
    :return: lista ordonata
    """
    #print("Insertion sort")
    for i in range(1, len(lista)):
        ind = i - 1
        a = lista [i]
        # insert a in the right position
        if reverse==False:
            while ind >= 0 and compare(a, lista [ind])==-1:
                lista [ind + 1] = lista [ind]
                ind -= 1
        else:
            while ind >= 0 and compare(a, lista [ind])==1:
                lista [ind + 1] = lista [ind]
                ind -= 1
        lista [ind + 1] = a
    return lista


def sortare2 (lista, reverse=False,compare=cmp_id):
    """
    Sorteaza prin combsort lista de studenti dupa o cheie si in functie de reverse
    :param cheie: functie
    :param reverse: True/False
    :return: lista sortata
    """
    #print("Comb sort")
    gap = len(lista)
    swaps = True
    while gap > 1 or swaps:
        gap = max(1, int(gap / 1.25))
        swaps = False
        for i in range(len(lista) - gap):
            j = i + gap
            if (compare(lista [i],(lista [j]))==1 and reverse==False) or (
                    compare(lista [i],lista [j])==-1 and reverse==True):
                lista [i], lista [j] = lista [j], lista [i]
                swaps = True
    return lista

class ServiceStudent:
    """
    Tip abstract de date service student
    Domain: {repo_student,valid_student - repo_student RepoStudent, valid_student ValidatorStudent}
    """
    def __init__(self,repo_student,valid_student):
        """
        Initializeaza un obiect de tip ServiceStudent
        :param repo_student: RepoStudent
        :param valid_student: ValidatorStudent
        """
        self.repo_student=repo_student
        self.valid_student=valid_student

    def sortare(self,reverse=False,compare=cmp_id):
        """
        Sorteaza prin insertion sort lista de studenti dupa o cheie si in functie de reverse
        :param cheie: o functie
        :param reverse: True/False
        :return: lista ordonata
        """
        print("Insertion sort")
        lista=self.repo_student.get_studenti()
        for i in range(1, len(lista)):
            ind = i - 1
            a = lista [i]
            # insert a in the right position
            if reverse==False:
                while ind >= 0 and compare(a,lista[ind])==-1:
                    lista [ind + 1] = lista [ind]
                    ind-=1
            else:
                while ind >= 0 and compare(a,lista[ind])==1:
                    lista [ind + 1] = lista [ind]
                    ind-=1
            lista [ind + 1] = a
        self.repo_student.repo.lista_studenti=lista
        self.repo_student.update_file()
        return lista

    def sortare2 (self,cheie=get_studentid,reverse=False):
        """
        Sorteaza prin combsort lista de studenti dupa o cheie si in functie de reverse
        :param cheie: functie
        :param reverse: True/False
        :return: lista sortata
        """
        print("Comb sort")
        lista=self.repo_student.get_studenti()
        gap = len(lista)
        swaps = True
        while gap > 1 or swaps:
            gap = max(1, int(gap / 1.25))
            swaps = False
            for i in range(len(lista) - gap):
                j = i + gap
                if (cheie(lista [i])> cheie(lista [j]) and reverse==False) or (cheie(lista[i])<cheie(lista[j]) and reverse==True):
                    lista [i], lista [j] = lista [j], lista [i]
                    swaps = True
        self.repo_student.repo.lista_studenti = lista
        self.repo_student.update_file()
        return lista

    def modificare_student(self,studentid,nume,grupa):
        """
        Metoda pentru validarea unui student in vederea modificarii sale in repo
        :param studentid: int
        :param nume: string
        :param grupa: int
        :return:
        """
        student=Student(studentid,nume,grupa)
        self.valid_student.validare_student(student)
        self.repo_student.modificare_student(student)

    def get_studenti(self):
        """
        Metoda pentru afisarea listei de studenti
        :return: lista de studenti
        """
        return self.repo_student.get_studenti()

    def adauga_student(self,studentid,nume,grup):
        """
        Metoda pentru validarea unui student in vederea adaugarii sale in repo
        :param studentid: int
        :param nume: string
        :param grup: int
        :return:
        """
        student=Student(studentid,nume,grup)
        self.valid_student.validare_student(student)
        self.repo_student.adaugare_student(student)

    def sterge_student(self,studentid):
        """
        Metoda pentru validarea unui student in vederea stergerii sale in repo
        :param studentid: int
        :return:
        """
        student = Student(studentid,"asf",21)
        self.valid_student.validare_student(student)
        self.repo_student.sterge_student(student)

    def cauta_student(self,studentid):
        """
        Metoda pentru validarea unui student in vederea cautarii sale in repo
        :param studentid: int
        :return: True, daca studentul se afla deja in repo, False, in caz contrar
        """
        student=Student(studentid,"asf",21)
        self.valid_student.validare_student(student)
        return self.repo_student.cauta_student(student)


class ServiceProblema:
    """
    Tip abstract de date service problema de laborator
    Domain: {repo_problema,valid_problema - repo_problema RepoProblema, valid_problema ValidatorProblema}
    """
    def __init__(self,repo_problema,valid_problema):
        """
        Initializeaza un obiect de tip ServiceProblema
        :param repo_problema: RepoProblema
        :param valid_problema: ValidatorProblema
        """
        self.repo_problema=repo_problema
        self.valid_problema=valid_problema

    def cauta_problema(self,numar_laborator,numar_problema):
        """
        Metoda pentru validarea unei probleme in vederea cautarii sale in repo
        :param numar_laborator: int
        :param numar_problema: int
        :return: True / False, daca problema exista sau nu in repo, raises exception daca datele nu sunt valide
        """
        problema=Problema(numar_laborator,numar_problema,"a",12,12,2012)
        self.valid_problema.validare_problema(problema)
        return self.repo_problema.cauta_problema(problema)

    def modificare_problema(self,numar_laborator,numar_problema,descriere,zi,luna,an):
        """
        Metoda pentru validarea unei probleme in vederea modificarii sale in repo
        :param numar_laborator: int
        :param numar_problema: int
        :param descriere: string
        :param zi: int, 0<zi<=31
        :param luna: int, 0<luna<=12
        :param an: int, 2000<=an<=2019
        :return:
        """
        problema=Problema(numar_laborator,numar_problema,descriere,zi,luna,an)
        self.valid_problema.validare_problema(problema)
        self.repo_problema.modificare_problema(problema)

    def sterge_problema(self,numar_laborator,numar_problema):
        """
        Metoda pentru validarea unei probleme in vederea stergerii sale din repo
        :param numar_laborator: int
        :param numar_problema: int
        :return:
        """
        problema=Problema(numar_laborator,numar_problema,"a",12,12,2012)
        self.valid_problema.validare_problema(problema)
        self.repo_problema.sterge_problema(problema)

    def get_probleme(self):
        """
        Metoda pentru afisarea listei de probleme
        :return: lista de probleme
        """
        return self.repo_problema.get_probleme()

    def adauga_problema(self,numar_laborator,numar_problema,descriere,zi,luna,an):
        """
        Metoda pentru validarea unei probleme in vederea adaugarii sale in repo
        :param numar_laborator: int
        :param numar_problema: int
        :param descriere: string
        :param zi: int, 0<zi<=32
        :param luna: int, 0<luna<=12
        :param an: int, 2000<=an
        :return:
        """
        problema=Problema(numar_laborator,numar_problema,descriere,zi,luna,an)
        self.valid_problema.validare_problema(problema)
        self.repo_problema.adaugare_problema(problema)

class ServiceNote:
    def __init__(self,repo_student,repo_problema,repo_note,valid_note):
        """
        Initializeaza un obiect de tipul ServiceNote
        :param repo_student: RepoStudent
        :param repo_problema: RepoProblema
        :param repo_note: RepoNote
        :param valid_note: ValidatorNote
        """
        self.repo_note=repo_note
        self.valid_note=valid_note
        self.repo_student=repo_student
        self.repo_problema=repo_problema
        self.valid_student=None
        self.service_student=ServiceStudent(self.repo_student,self.valid_student)

    def asignare_nota(self,studentid,numar_laborator,numar_problema,nota):
        """
        Metoda pentru validarea datelor pentru functia asignare_nota
        :param studentid: int
        :param numar_laborator: int
        :param numar_problema: int
        :param nota: float
        :return: -, raises exception daca sunt erori
        """
        student=Student(studentid,"aa",12)
        student=self.repo_student.cauta_student(student)
        problema=Problema(numar_laborator,numar_problema,"aaa",1,1,2001)
        problema=self.repo_problema.cauta_problema(problema)
        grade=Nota(student,problema,nota)
        self.valid_note.validare_nota(grade)
        self.repo_note.asignare_nota(grade)

    def afisare_dupa_nume(self,numar_laborator,numar_problema):
        """
        Metoda pentru afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat alfabetic după nume
        :param numar_laborator: int
        :param numar_problema: int
        :return: lista de studentii care au note la problema data, ordonata alfabetica
        """
        note=self.repo_note.get_all() #lista cu note
        problema=Problema(numar_laborator,numar_problema,"11",1,1,2001)
        problema=self.repo_problema.cauta_problema(problema)
        lista=[]
        for i in note:
            if i.problema==problema:
                lista.append(i)
        #lista.sort(key=lambda x:x.student.get_nume(),reverse=False)
        lista=sortare(lista,False,cmp_nume)
        return lista

    def afisare_dupa_nota(self,numar_laborator,numar_problema):
        """
        Metoda pentru Afisarea listei de studenți și a notele lor la o problema de laborator dat, ordonat după nota
        :param numar_laborator: int
        :param numar_problema: int
        :return:
        """
        lista=self.afisare_dupa_nume(numar_laborator,numar_problema)
        #lista.sort(key=lambda x:x.get_nota(),reverse=True)
        #lista.sort(key=lambda x:()
        lista=sortare(lista,True,cmp_nota)
        return lista

    def afisare_dupa_medie(self):
        """
        Metoda pentru afisarea studentilor cu media mai mica decat 5
        :return: lista studentilor cu media mai mica decat 5
        """
        note=self.repo_note.get_all()
        situatie={}
        for nota in note:
            studentid=nota.get_student().get_studentid()
            if studentid not in situatie:
                situatie[studentid]=[]
            situatie[studentid].append(nota.get_nota())
        rez=[]
        for item in situatie.items():
            nume=self.repo_student.cauta_student(Student(item[0],"a",1)).get_nume()
            medie= sum(item[1])/len(item[1])
            if medie<5:
                mediadto=MedieDTO(nume,medie)
                rez.append(mediadto)
        #rez.sort(key=lambda  x:x.get_medie(),reverse=True)
        rez=sortare(rez,True,cmp_medie)
        return rez

    def raport_lab(self):
        note=self.repo_note.get_all()
        situatie={}
        for nota in note:
            numar_laborator=nota.get_problema().get_numar_laborator()
            if numar_laborator not in situatie:
                situatie[numar_laborator]=[]
            situatie[numar_laborator].append(nota.get_nota())
        rez=[]
        for item in situatie.items():
            lab=self.repo_problema.cauta_problema(Problema(item[0],1,"a",1,1,2001)).get_numar_laborator()
            medie=sum(item[1])/len(item[1])
            mediadto=MedieDTO(lab,medie)
            rez.append(mediadto)
        rez.sort(key=lambda  x: (x.get_medie(), x.get_nume()),reverse=False)
        return rez

class ServiceAsignareStudentProblema():
    """ Tip abstract de date ServiceAsignareStudentProblema """
    def __init__ (self,repo_student,repo_problema,repo_asig):
        self.repo_student=repo_student
        self.repo_problema=repo_problema
        self.repo_asig=repo_asig

    def asignare_student_problema(self,studentid,numar_laborator,numar_problema):
        """
        Metoda pentru asignarea unei probleme la un student
        :param studentid: int
        :param numar_laborator: int
        :param numar_problema: int
        :return: -, raises exception daca datele nu sunt valide
        """
        student=Student(studentid,"bbb",1)
        problema=Problema(numar_laborator,numar_problema,"11",1,1,2001)
        student=self.repo_student.cauta_student(student)
        problema=self.repo_problema.cauta_problema(problema)
        asignare=AsignareStudentProblema(student,problema)
        self.repo_asig.asignare_student_problema(asignare)

    def get_all(self):
        """
        Metoda pentru obtinerea tuturor asignarilor
        :return: toate asignarile
        """
        return self.repo_asig.get_all()