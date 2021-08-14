from exceptii import ValidError
class ValidatorStudent:
    """
    Tip abstract de date pentru validarea unui student
    Domain: -
    """
    def __init__(self):
        """
        Initializeaza un obiect de tip ValidatorStudent
        """
        pass

    def validare_student(self,student):
        """
        Metoda pentru validarea unui student
        :param student: student
        :return: -, raises exception daca sunt erori
        """
        mesaj=""
        if not self.validare_id(student):
            mesaj+="ID-ul nu poate fi negativ\n"
        if not self.validare_nume(student):
            mesaj+="Numele nu poate fi vid\n"
        if not self.validare_grup(student):
            mesaj+="Numarul grupei nu poate fi negativ\n"
        if mesaj!="":
            raise ValidError(mesaj)

    def validare_nume(self,student):
        """
        Valideaza numele unui student
        :param student: student
        :return: True, daca numele studentului este corect, False, in caz contrar
        """
        if student.nume=="":
            return False
        return True

    def validare_id(self,student):
        """
        Valideaza ID-ul unui student
        :param student: student
        :return: True, daca ID-ul studentului este corect, False, in caz contrar
        """
        if student.studentid<0:
            return False
        return True

    def validare_grup(self,student):
        """
        Valideaza grupul unui student
        :param student: student
        :return: True, daca grupul studentului este corect, False, in caz contrar
        """
        if student.grup<0:
            return False
        return True

class ValidatorProblema:
    """
    Tip abstract de date pentru validarea unei probleme
    """
    def __init__(self):
        """
        Initializeaza un obiect de tip ValidatorProblema
        """
        pass

    def validare_problema(self,problema):
        """
        Metoda pentru validarea unei probleme
        :param problema: problema de laborator
        :return: -, raises exception daca sunt erori
        """
        mesaj=""
        if not self.validare_numar_laborator(problema):
            mesaj+="Numarul laboratorului nu poate fi negativ\n"
        if not self.validare_numar_problema(problema):
            mesaj+="Numarul problemei nu poate fi negativ\n"
        if not self.validare_zi(problema):
            mesaj+="Ziua pentru deadline este invalida\n"
        if not self.validare_luna(problema):
            mesaj+="Luna pentru deadline este invalida\n"
        if not self.validare_an(problema):
            mesaj+="Anul pentru deadline este invalid"
        if mesaj!="":
            raise ValidError(mesaj)

    def validare_numar_laborator(self,problema):
        """
        Valideaza un numar de laborator
        :param problema: problema de laborator
        :return: True, daca numarul de laborator este corect, False, in caz contrar
        """
        if problema.numar_laborator<0:
            return False
        return True

    def validare_numar_problema(self,problema):
        """
        Valideaza un numar de problema
        :param problema: problema de laborator
        :return: True, daca numarul de problema este corect, False, in caz contrar
        """
        if problema.numar_problema<0:
            return False
        return True

    def validare_zi(self,problema):
        """
        Valideaza ziua unui deadline de problema
        :param problema: problema de laborator
        :return: True, daca ziua este corecta, False, in caz contrar
        """
        if problema.zi>0 and problema.zi<=31:
            return True
        return False

    def validare_luna(self,problema):
        """
        Valideaza luna unui deadline de problema
        :param problema: problema de laborator
        :return: True, daca luna este corecta, False, in caz contrar
        """
        if problema.luna>0 and problema.luna<=12:
            return True
        return False

    def validare_an(self,problema):
        """
        Valideaza anul unui deadline de problema
        :param problema: problema de laborator
        :return: True, daca anul este corect, False, in caz contrar
        """
        if problema.an>=2000 and problema.an<=2019:
            return True
        return False

class ValidatorNote():
    """ Tip abstract de date ValidatorNote """
    def __init__(self):
        pass

    def validare_nota(self,nota):
        """
        Metoda pentru validarea unei note
        :param nota: int
        :return: -, raises exception daca nota nu este valida
        """
        if nota.get_nota()<=0 or nota.get_nota()>10:
            raise ValidError("Nota nu poate fi negativa/nula/mai mare ca 10")


