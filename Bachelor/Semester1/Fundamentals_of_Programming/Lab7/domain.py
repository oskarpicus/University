#entitati - student, problema
class Student:
    """
    Tip de date abstract student
    Domain: {studentid,nume,grup,lista_note - studentid int, nume string, grup int, lista_note list}
    """
    def __init__(self,studentid,nume,grup):
        """
        Initializeaza un student
        :param studentid: int
        :param nume: string
        :param grup: int
        """
        self.studentid=studentid
        self.nume=nume
        self.grup=grup

    def __eq__(self, other):
        """ Egalitatea dintre doi studenti """
        return self.studentid==other.studentid

    def get_studentid (self):
        """
        Getter pentru ID-ul unui student
        :return: id-ul studentului
        """
        return self.studentid

    def get_nume (self):
        """
        Getter pentru numele unui student
        :return: numele studentului
        """
        return self.nume

    def get_grup (self):
        """
        Getter pentru grupul unui student
        :return: grupul studentului
        """
        return self.grup

    def set_nume(self,val):
        """
        Setter pentru numele unui student
        :param val: string
        :return: studentul actualizat
        """
        self.nume=val

    def set_grup(self,val):
        """
        Setter pentru grupul unui student
        :param val: int
        :return: studentul actualizat
        """
        self.grup=val

    def __str__(self):
        return str(self.get_studentid()) + " " + self.get_nume() + " " + str(self.get_grup()) + "\n"


class Problema:
    """
    Tip abstract de date problema de laborator
    Domain: {numar_laborator,numar_problema,descriere,zi,luna,an - numar_laborator int, numar_problema int, descriere string, zi int (0<zi<=31), luna int (0<luna<13), an int (an>=2000)}
    """
    def __init__(self,numar_laborator,numar_problema,descriere,zi,luna,an):
        """
        Initializeaza o problema de laborator
        :param numar_laborator: int
        :param numar_problema: int
        :param descriere: string
        :param zi: int, 0<zi<=31
        :param luna: int, 0<luna<=12
        :param an: int, 2000<=an
        """
        self.numar_laborator=numar_laborator
        self.numar_problema=numar_problema
        self.descriere=descriere
        self.zi=zi
        self.luna=luna
        self.an=an

    def __eq__(self, other):
        """ Egalitatea dintre doua probleme """
        return self.numar_laborator==other.numar_laborator and self.numar_problema==other.numar_problema

    def get_numar_problema(self):
        """
        Getter pentru numarul problemei unei probleme de laborator
        :return: numarul problemei problemei de laborator
        """
        return self.numar_problema

    def get_numar_laborator(self):
        """
        Getter pentru numarul laboratorului unei probleme de laborator
        :return: numarul laboratorului problemei de laborator
        """
        return self.numar_laborator

    def get_descriere(self):
        """
        Getter pentru descrierea unei probleme de laborator
        :return: descrierea problemei de laborator
        """
        return self.descriere

    def get_zi(self):
        """
        Getter pentru ziua deadline-ului unei probleme de laborator
        :return: ziua deadline-ului problemei de laborator
        """
        return self.zi

    def get_luna(self):
        """
        Getter pentru luna deadline-ului unei probleme de laborator
        :return: luna deadline-ului problemei de laborator
        """
        return self.luna

    def get_an(self):
        """
        Getter pentru anul deadline-ului unei probleme de laborator
        :return: anul deadline-ului problemei de laborator
        """
        return self.an

    def setter_descriere(self,val):
        """
        Setter pentru descrierea unei probleme de laborator
        :param val: string
        :return: problema actualizata
        """
        self.descriere=val

    def setter_zi(self,val):
        """
        Setter pentru ziua din deadline a unei probleme de laborator
        :param val: int
        :return: problema actualizata
        """
        self.zi=val

    def setter_luna(self,val):
        """
        Setter pentru luna din deadline a unei probleme de laborator
        :param val: int
        :return: problema actualizata
        """
        self.luna=val

    def setter_an(self,val):
        """
        Setter pentru anul din deadline a unei probleme de laborator
        :param val: int
        :return: problema actualizata
        """
        self.an=val

class Nota:
    """
    Tip abstract de date nota
    Domain {student, problema,nota - student, un student, problema, o problema de laborator, nota, int }
    """
    def __init__(self,student,problema,nota):
        """
        Metoda pentru initializarea unui obiect de tip nota
        :param student: student
        :param problema: problema de laborator
        :param nota: int
        """
        self.student=student
        self.problema=problema
        self.nota=nota

    def get_nota(self):
        """ Getter pentru valoarea notei """
        return self.nota

    def get_student(self):
        """ Getter pentru studentul unei note """
        return self.student

    def get_problema(self):
        """ Getter pentru problema de laborator a unei note """
        return self.problema

    def __eq__(self, other):
        """
        Definirea relatiei de egalitate intre 2 note
        :param other: alta nota
        :return:
        """
        return self.get_student().get_studentid()==other.get_student().get_studentid() and self.get_problema().get_numar_laborator()==other.get_problema().get_numar_laborator() and self.get_problema().get_numar_problema()==other.get_problema().get_numar_problema()

    def __cmp__(self, other):
        return self.get_nota()>other.get_nota()

class AsignareStudentProblema:
    """ Tip abstract de date AsignareStudentProblema - pentru asignarea unei probleme unui student """
    def __init__(self,student,problema):
        """
        Se initializeaza un obiect de tipul AsignareStudentProblema
        :param student: un student
        :param problema: o problema de laborator
        """
        self.student=student
        self.problema=problema

    def get_student(self):
        """ Getter pentru studentul unei asignari """
        return self.student

    def get_problema(self):
        """ Getter pentru problema unei asignari """
        return self.problema

    def __eq__(self, other):
        """
        Defineste relatia de egalitate dintre doua asignari
        :param other: o asignare
        :return:
        """
        return self.get_student()==other.get_student() and self.get_problema()==other.get_problema()

class MedieDTO:
    """
    Tip abstract de date MedieDTO
    Domain: {nume,medie - nume, string (numele unui student), medie, int (media notelor unui student)}
    """
    def __init__(self,nume,medie):
        """
        Initializeaza un obiect de
        :param nume: string
        :param medie: int
        """
        self.__nume=nume
        self.__medie=medie

    def get_nume(self):
        """ Getter pentru numele unui student """
        return self.__nume

    def get_medie(self):
        """ Getter pentru media unui student """
        return self.__medie

    def __str__(self):
        return self.__nume + "cu media " +str(self.__medie)

def get_studentid (student):
        """
        Getter pentru ID-ul unui student
        :return: id-ul studentului
        """
        return student.studentid

def get_nume (student):
        """
        Getter pentru numele unui student
        :return: numele studentului
        """
        return student.nume

def get_grup (student):
        """
        Getter pentru grupul unui student
        :return: grupul studentului
        """
        return student.grup

def cmp_id(student1,student2):
    if student1.get_studentid()>student2.get_studentid():
        return 1
    elif student1.get_studentid()==student2.get_studentid():
        return 0
    return -1

def cmp_nume(nota1,nota2):
    if nota1.get_student().get_nume()>nota2.get_student().get_nume():
        return 1
    elif nota1.get_student().get_nume()==nota2.get_student().get_nume():
        return 0
    return -1

def cmp_nota(nota1,nota2):
    if nota1.get_nota()==nota2.get_nota():
        return 0
    elif nota1.get_nota()>nota2.get_nota():
        return 1
    return -1

def cmp_medie(medie1,medie2):
    if medie1.get_medie()>medie2.get_medie():
        return 1
    elif medie1.get_medie()==medie2.get_medie():
        return 0
    return -1