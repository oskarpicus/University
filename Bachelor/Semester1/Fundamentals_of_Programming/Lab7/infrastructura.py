""" Modulul infrastructura """
from domain import Student, Problema, Nota, AsignareStudentProblema
from exceptii import RepoError

class RepoStudentFile:

    def __init__ (self, fisier):
        self.__fisier = fisier
        self.repo = RepoStudent()
        self.load_from_file()

    def sterge_student (self, student):
        """
        Metoda pentru stergerea unui student din repository
        :param student: student
        :return: -, raises exception daca nu exista fstudentul dat
        """
        self.load_from_file()
        self.repo.sterge_student(student)
        self.update_file()

    def modificare_student (self, student):
        """
        Metoda pentru modificarea datelor unui student
        :param student: student
        :return: -, raises exception daca nu exista studentul dat
        """
        self.load_from_file()
        self.repo.modificare_student(student)
        self.update_file()

    def update_file (self):
        f = open(self.__fisier, "w+")
        for i in self.repo.lista_studenti:
            f.write(str(i.get_studentid()) + "," + str(i.get_nume()) + "," + str(i.get_grup()) + "\n")
        f.close()

    def adaugare_student (self, student):
        """
        Metoda pentru adaugarea unui student in repository
        :param student: student
        :return: lista_studenti actualizata cu adaugarea studentului (daca nu sunt erori)
        """
        self.load_from_file()
        self.repo.adaugare_student(student)
        self.append_to_file(student)

    def append_to_file (self, student):
        f = open(self.__fisier, "a")
        f.write(str(student.get_studentid()) + "," + str(student.get_nume()) + "," + str(
            student.get_grup()) + "\n")
        f.close()

    def load_from_file (self):
        f = open(self.__fisier, "r+")
        self.repo.lista_studenti=[]
        for linie in f:
            linie = linie.split(",")
            if linie!=[]:
                student = Student(int(linie [0]), linie [1], int(linie [2]))
                self.repo.adaugare_student(student)
        f.close()

    def get_studenti (self):
        """
        Metoda pentru returnarea listei cu toti studentii
        :return: lista cu toti studentii
        """
        return self.repo.lista_studenti

    def cauta_student (self, student):
        """
        Metoda pentru cautarea unui student
        :param student: student
        :return: True / False, daca studentul a fost sau nu deja adaugat
        """
        return self.repo.cauta_student_recursiv(student, 0)


class RepoProblemaFile:
    def __init__ (self):
        self.repo_problema = RepoProblema()
        self.__load_from_file()

    def adaugare_problema (self, problema):
        self.repo_problema.adaugare_problema(problema)
        self.__append_file(problema)

    def sterge_problema (self, problema):
        self.repo_problema.sterge_problema(problema)
        self.__update_file()

    def modificare_problema (self, problema):
        self.repo_problema.modificare_problema(problema)
        self.__update_file()

    def __load_from_file (self):
        f = open("fisier_probleme.txt", "r")
        for linie in f:
            linie = linie.split(",")
            if linie!=[]:
                problema = Problema(int(linie [0]), int(linie [1]), linie [2], int(linie [3]), int(linie [4]),
                                    int(linie [5]))
                self.repo_problema.adaugare_problema(problema)
        f.close()

    def __update_file (self):
        f = open("fisier_probleme.txt", "w")
        for i in self.repo_problema.lista_probleme:
            f.write(
                str(i.get_numar_laborator()) + "," + str(i.get_numar_problema()) + "," + i.get_descriere() + "," + str(
                    i.get_zi()) + "," + str(i.get_luna()) + "," + str(i.get_an()) + "\n")
        f.close()

    def __append_file (self, problema):
        f = open("fisier_probleme.txt", "a")
        f.write(str(problema.get_numar_laborator()) + "," + str(
            problema.get_numar_problema()) + "," + problema.get_descriere() + "," + str(problema.get_zi()) + "," + str(
            problema.get_luna()) + "," + str(problema.get_an()) + "\n")
        f.close()

    def get_probleme (self):
        return self.repo_problema.lista_probleme

    def cauta_problema (self, problema):
        return self.repo_problema.cauta_problema_recursiv(problema,0)


class RepoNoteFile:
    def __init__ (self):
        self.repo = RepoNote()
        self.repo_student = RepoStudentFile("fisier_studenti.txt")
        self.repo_problema = RepoProblemaFile()
        self.__load_from_file()

    def __load_from_file (self):
        f = open("fisier_note.txt", "r")
        for linie in f:
            linie = linie.split(",")
            if linie!=[]:
                try:
                    student = Student(int(linie [0]), "bba", 1)
                    student = self.repo_student.cauta_student(student)
                    problema = Problema(int(linie [1]), int(linie [2]), "a", 1, 1, 2001)
                    self.repo_problema.cauta_problema(problema)
                    nota = Nota(student, problema, float(linie [3]))
                    self.repo.asignare_nota(nota)
                except RepoError:
                    pass
        f.close()

    def __update_file (self):
        f = open("fisier_note.txt", "w")
        for i in self.repo.lista_note:
            f.write(
                str(i.get_student().get_studentid()) + "," + str(i.get_problema().get_numar_laborator()) + "," + str(
                    i.get_problema().get_numar_problema()) + "," + str(i.get_nota()) + "\n")
        f.close()

    def __append_file (self, i):
        f = open("fisier_note.txt", "a")
        f.write(str(i.get_student().get_studentid()) + "," + str(i.get_problema().get_numar_laborator()) + "," + str(
            i.get_problema().get_numar_problema()) + "," + str(i.get_nota()) + "\n")
        f.close()

    def asignare_nota (self, grade):
        self.repo.asignare_nota(grade)
        self.__append_file(grade)

    def get_all (self):
        return self.repo.get_all()


class RepoAsignareFile:
    def __init__ (self):
        self.repo = RepoAsignare()
        self.repo_student = RepoStudentFile("fisier_studenti.txt")
        self.repo_problema = RepoProblemaFile()
        self.__load_from_file()

    def __load_from_file (self):
        f = open("fisier_asignare.txt", "r")
        for linie in f:
            linie = linie.split(",")
            if linie!=[]:
                try:
                    student = Student(int(linie [0]), "bba", 1)
                    student = self.repo_student.cauta_student(student)
                    problema = Problema(int(linie [1]), int(linie [2]), "a", 1, 1, 2001)
                    problema = self.repo_problema.cauta_problema(problema)
                    asignare = AsignareStudentProblema(student, problema)
                    self.repo.asignare_student_problema(asignare)
                except RepoError:
                    pass
        f.close()

    def __update_file (self):
        f = open("fisier_asignare.txt", "w")
        for i in self.repo.lista_asig:
            f.write(
                str(i.get_student().get_studentid()) + "," + str(i.get_problema().get_numar_laborator()) + "," + str(
                    i.get_problema().get_numar_problema()) + "\n")
        f.close()

    def __append_file (self, i):
        f = open("fisier_asignare.txt", "a")
        f.write(str(i.get_student().get_studentid()) + "," + str(i.get_problema().get_numar_laborator()) + "," + str(
            i.get_problema().get_numar_problema()) + ",")
        f.close()

    def asignare_nota (self, asig):
        self.repo.asignare_student_problema(asig)
        self.__append_file(asig)

    def get_all (self):
        return self.repo.get_all()

    def asignare_student_problema (self, asignare):
        self.repo.asignare_student_problema(asignare)
        self.__update_file()


class RepoAsignare:
    """ Tip abstract de date RepoAsignare """

    def __init__ (self):
        self.lista_asig = []

    def asignare_student_problema (self, asignare):
        """
        Metoda pentru adaugarea unei asignari de problema la un student
        :param asignare: o asignare
        :return: -, raises exception daca asignarea nu este unica
        """
        self.validare_unicitate(asignare)
        self.lista_asig.append(asignare)

    def validare_unicitate (self, asignare):
        """
        Metoda pentru validarea unicitatii unei asignari
        :param asignare: o asignare
        :return: -, raises exception daca asignarea nu este unica
        """
        for i in self.lista_asig:
            if i==asignare:
                raise RepoError("Problema a fost deja asignata la acest student ")

    def get_all (self):
        """ Metoda pentru obtinerea tuturor asignarilor """
        return self.lista_asig


# Clase non file

class RepoProblema:
    """
    Tip abstract de date repository problema de laborator
    Domain: {lista_probleme - lista_probleme list}
    """

    def __init__ (self):
        """
        Initializeaza un obiect de tipul RepoProblema
        """
        self.lista_probleme = []

    def cauta_problema (self, problema):
        """
        Metoda pentru cautarea unei probleme de laborator
        :param problema: problema de laborator
        :return: problema, daca se afla in lista, raises exception, altfel
        """
        for i in self.lista_probleme:
            if problema==i:
                return i
        raise RepoError("Problema nu exista ")

    def cauta_problema_recursiv(self,problema,pozitie):
        """
        Metoda pentru cautarea unei probleme de laborator
        :param problema: problema de laborator
        :param pozitie: int
        :return: problema, daca se afla in lista, raises exception, altfel
        """
        if pozitie==len(self.lista_probleme):
            raise RepoError("Problema nu exista (Recursiv) ")
        if self.lista_probleme[pozitie]==problema:
            return self.lista_probleme[pozitie]
        return self.cauta_problema_recursiv(problema,pozitie+1)

    def sterge_problema (self, problema):
        """
        Metoda pentru stergerea unei probleme de laborator
        :param problema: problema de laborator
        :return:
        """
        if not problema in self.lista_probleme:
            raise RepoError("Problema data nu se afla in baza de date! ")
        for i in self.lista_probleme:
            if i==problema:
                self.lista_probleme.remove(i)
                break

    def adaugare_problema (self, problema):
        """
        Metoda pentru adaugarea unei probleme de laborator
        :param problema: problema de laborator
        :return: lista_probleme actualizata cu adaugarea studentului (daca nu sunt erori)
        """
        self.validare_unicitate(problema)
        self.lista_probleme.append(problema)

    def validare_unicitate (self, problema):
        """
        Metoda pentru validarea unicitatii unei probleme de laborator
        :param problema: problema de laborator
        :return: -, daca problema nu exista deja, raises Exception, in caz contrar
        """
        for i in self.lista_probleme:
            if problema==i:
                raise RepoError("Problema exista deja! ")

    def modificare_problema (self, problema):
        """
        Metoda pentru modificarea unei probleme
        :param problema: problema de laborator
        :return: -, daca problema nu exista deja, raises exception
        """
        if not problema in self.lista_probleme:
            raise RepoError("Problema data nu se afla in baza de date! ")
        for i in self.lista_probleme:
            if i==problema:
                i.setter_descriere(problema.get_descriere())
                i.setter_zi(problema.get_zi())
                i.setter_luna(problema.get_luna())
                i.setter_an(problema.get_an())
                break

    def get_probleme (self):
        """
        Metoda pentru crearea unui string cu toate probleme de laborator
        :return: string cu toate probleme de laborator
        """
        return self.lista_probleme


class RepoNote:
    def __init__ (self):
        """ Initializeaza un obiect de tipul RepoNote """
        self.lista_note = []

    def asignare_nota (self, grade):
        """
        Metoda pentru asignarea unei note unui student
        :param grade: o nota
        :return: -, se actualizeaza lista cu note
        """
        for i in self.lista_note:
            if i==grade:
                raise RepoError("Nota a fost adaugata deja! ")
        self.lista_note.append(grade)

    def get_all (self):
        """
        Metoda pentru obtinerea tuturor notelor
        :return: lista notelor
        """
        return self.lista_note


class RepoStudent:
    """
    Tip abstract de date repository de student
    Domain: {lista_studenti - lista_studenti list}
    """

    def __init__ (self):
        """
        Initializeaza un obiect de tipul RepoStudent
        """
        self.lista_studenti = []

    def get_studenti (self):
        """
        Metoda pentru returnarea listei cu toti studentii
        :return: lista cu toti studentii
        """
        return self.lista_studenti

    def adaugare_student (self, student):
        """
        Metoda pentru adaugarea unui student in repository
        :param student: student
        :return: lista_studenti actualizata cu adaugarea studentului (daca nu sunt erori)
        """
        self.validare_unicitate(student)
        for i in self.lista_studenti:
            if i.get_nume()==student.get_nume():
                numele = student.get_nume()
                if not "(" in numele:
                    numele += " (1)"
                    student.set_nume(numele)
                else:
                    lista = list(numele)
                    numar = int(lista [-2])
                    numar += 1
                    lista [-2] = str(numar)
                    numele = "".join(lista)
                    student.set_nume(numele)
        self.lista_studenti.append(student)

    def validare_unicitate (self, student):
        """
        Metoda pentru validarea unicitatii unui student
        :param student: student
        :return: -, daca studentul nu a fost deja adaugat, raises Exception, in caz contrar
        """
        for i in self.lista_studenti:
            if i==student:
                raise RepoError("Studentul exista deja! ")

    def cauta_student (self, student):
        """
        Metoda pentru cautarea unui student
        :param student: student
        :return: student, daca se afla in lista, raises exception, altfel
        """
        for i in self.lista_studenti:
            if student==i:
                return i
        raise RepoError("Studentul nu exista! ")

    def cauta_student_recursiv(self,student,pozitie):
        """
        Metoda pentru cautarea unui student
        :param student: Student
        :param pozitie: int
        :return: student, daca se afla in lista, raises exception, altfel
        """
        if pozitie==len(self.lista_studenti):
            raise RepoError("Studentul nu exista (Recursiv)! ")
        if self.lista_studenti[pozitie]==student:
            return self.lista_studenti[pozitie]
        return self.cauta_student_recursiv(student,pozitie+1)

    def sterge_student (self, student):
        """
        Metoda pentru stergerea unui student din repository
        :param student: student
        :return: -, raises exception daca nu exista studentul dat
        """
        if not student in self.lista_studenti:
            raise RepoError("Nu exista acest student in baza de date ")
        for i in self.lista_studenti:
            if i==student:
                self.lista_studenti.remove(i)
                break

    def modificare_student (self, student):
        """
        Metoda pentru modificarea datelor unui student
        :param student: student
        :return: -, raises exception daca nu exista studentul dat
        """
        if not student in self.lista_studenti:
            raise RepoError("Nu exista acest student in baza de date ")
        for i in self.lista_studenti:
            if i==student:
                i.set_nume(student.get_nume())
                i.set_grup(student.get_grup())
                break

""" 
lucreaza doar cu fisierul, nu cu lista
class NewRepoFile:
    def __init__ (self):
        # RepoStudent.__init__(self)
        self.__file = "newRepoFile.txt"
        self.__auxiliarFile = "auxiliarFile.txt"

    def cauta_student (self, student):
        f = open(self.__file, "r")
        ok = False
        for linie in f:
            linie = linie.split(",")
            if int(linie [0])==student.get_studentid():
                ok = True
        if ok:
            return student
        f.close()
        raise RepoError("Studentul nu exista! ")

    def cauta_id (self, studentid):
        f = open(self.__file, "r")
        for linie in f:
            linie = linie.split(",")
            if int(linie [0])==studentid:
                raise RepoError("Studentul exista deja! ")
        f.close()

    def adaugare_student (self, student):
        self.validare_unicitate(student)
        f = open(self.__file, "a")
        linie = str(student.get_studentid()) + "," + student.get_nume() + "," + str(student.get_grup()) + ",\n"
        f.write(linie)
        f.close()

    def sterge_student (self, student):
        f = open(self.__file, "r")
        g = open(self.__auxiliarFile, "w")
        for linie in f:
            linie = linie.split(",")
            if int(linie [0])!=student.get_studentid():
                l = str(linie [0]) + "," + linie [1] + "," + str(linie [2]) + ",\n"
                g.write(l)
        f.close()
        g.close()
        g = open(self.__auxiliarFile, "r")
        f = open(self.__file, "w")
        for linie in g:
            f.write(linie)
        f.close()
        g.close()

    def modificare_student (self, student):
        f = open(self.__file, "r")
        g = open(self.__auxiliarFile, "w")
        for linie in f:
            linie = linie.split(",")
            if int(linie [0])!=student.get_studentid():
                l = str(linie [0]) + "," + linie [1] + "," + str(linie [2]) + ",\n"
            else:
                l = str(student.studentid) + "," + student.get_nume() + "," + str(student.get_grup()) + ",\n"
            g.write(l)
        g.close()
        g = open(self.__auxiliarFile, "r")
        f = open(self.__file, "w")
        for linie in g:
            f.write(linie)
        f.close()
        g.close()

    def get_studenti (self):
        f = open(self.__file, "r")
        lista = []
        for linie in f:
            linie = linie.split(",")
            if linie!=[]:
                lista.append(Student(int(linie [0]), linie [1], int(linie [2])))
        f.close()
        return lista

    def validare_unicitate (self, student):
        f = open(self.__file, "r")
        ok = True
        for linie in f:
            if int(linie [0])==student.get_studentid():
                ok = False
        f.close()
        if not ok:
            raise RepoError("Studentul a fost adaugat deja! ")
            """
