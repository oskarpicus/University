""" Modul cu teste pentru straturi """
from business import *
from infrastructura import *
from validator import *
from exceptii import RepoError,ValidError




class Teste:

    """ Business """
    #Clasa ServiceStudent
    def test_srv_adauga_student(self):
        studentid=23
        grup=216
        nume="Ion"
        student=Student(studentid,nume,grup)
        assert student.get_nume()==nume
        assert student.get_grup()==grup
        assert student.get_studentid()==studentid

    def test_srv_validare_student(self):
        repo_student = RepoStudent()
        valid_student = ValidatorStudent()
        service_student=ServiceStudent(repo_student,valid_student)
        studentid=23
        grup=216
        nume="Ion"
        student=Student(studentid,nume,grup)
        service_student.valid_student.validare_student(student)
        studentid=-23
        grup=-32
        nume=""
        student = Student(studentid,nume,grup)
        try:
            service_student.valid_student.validare_student(student)
            assert False
        except ValidError as e:
            assert str(e)=="ID-ul nu poate fi negativ\nNumele nu poate fi vid\nNumarul grupei nu poate fi negativ\n"

    def test_srv_cauta_student(self):
        repo_student=RepoStudent()
        valid_student=ValidatorStudent()
        service_student=ServiceStudent(repo_student,valid_student)
        student=Student(12,"ana",20)
        repo_student.adaugare_student(student)
        assert service_student.cauta_student(12)
        try:
            service_student.cauta_student(124)
            assert False
        except:
            assert True

    def test_srv_afisare_lista_studenti(self):
        repo_student = RepoStudent()
        valid_student = ValidatorStudent()
        service_student = ServiceStudent(repo_student,valid_student)
        assert len(service_student.get_studenti())==0
        student = Student(12,"ana",20)
        repo_student.adaugare_student(student)
        assert len(service_student.get_studenti())==1

    def test_srv_get_studenti(self):
        student=Student(1,"Ana",1)
        repo_student=RepoStudent()
        valid_student=ValidatorStudent()
        service_student=ServiceStudent(repo_student,valid_student)
        assert len(service_student.get_studenti())==0
        repo_student.adaugare_student(student)
        assert len(service_student.get_studenti())==1

    #Clasa ServiceProblema
    def test_srv_afisare_lista_probleme(self):
        repo_problema=RepoProblema()
        valid_problema=ValidatorProblema()
        service_problema=ServiceProblema(repo_problema,valid_problema)
        assert service_problema.get_probleme()==[]
        problema=Problema(12,12,"12",12,12,2012)
        repo_problema.adaugare_problema(problema)
        assert len(service_problema.get_probleme())==1

    def test_srv_adauga_problema(self):
        repo_problema=RepoProblema()
        valid_problema=ValidatorProblema()
        service_problema=ServiceProblema(repo_problema,valid_problema)
        problema=Problema(12,12,"12",12,12,2012)
        service_problema.adauga_problema(12,12,"12",12,12,2012)
        assert problema.get_luna()==12
        assert problema.get_zi()==12
        assert problema.get_descriere()=="12"
        assert problema.get_numar_problema()==12
        assert problema.get_numar_laborator()==12
        assert problema.get_an()==2012

    def test_srv_validare_problema(self):
        repo_problema = RepoProblema()
        valid_problema = ValidatorProblema()
        service_problema = ServiceProblema(repo_problema,valid_problema)
        problema = Problema(12,12,"12",12,12,2012)
        service_problema.valid_problema.validare_problema(problema)
        problema=Problema(-12,12,"12",12,12,2012)
        try:
            service_problema.valid_problema.validare_problema(problema)
            assert False
        except ValidError as e:
            assert str(e)=="Numarul laboratorului nu poate fi negativ\n"

    def test_srv_cauta_problema(self):
        repo_problema = RepoProblema()
        valid_problema = ValidatorProblema()
        service_problema=ServiceProblema(repo_problema,valid_problema)
        problema=Problema(1,1,"as",1,1,2001)
        try:
            service_problema.cauta_problema(problema.get_numar_laborator(),problema.get_numar_problema())
            assert False
        except:
            assert True
        repo_problema.adaugare_problema(problema)
        assert service_problema.cauta_problema(problema.get_numar_laborator(),problema.get_numar_problema())

    def test_srv_get_probleme(self):
        repo_problema = RepoProblema()
        valid_problema = ValidatorProblema()
        service_problema = ServiceProblema(repo_problema,valid_problema)
        problema = Problema(1,1,"as",1,1,2001)
        assert len(service_problema.get_probleme())==0
        repo_problema.adaugare_problema(problema)
        assert len(service_problema.get_probleme())==1

    #Clasa ServiceNote
    def test_srv_asignare_nota(self):
        repo_problema = RepoProblema()
        repo_note=RepoNote()
        repo_student = RepoStudent()
        valid_note=ValidatorNote()
        service_note=ServiceNote(repo_student,repo_problema,repo_note,valid_note)
        try:
            service_note.asignare_nota(12,12,12,12)
            assert False
        except:
            assert True
        student=Student(1,"Ana",1)
        problema=Problema(1,1,"aaa",2,2,2003)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,3)

    def test_afisare_dupa_nume(self):
        repo_problema = RepoProblema()
        repo_note = RepoNote()
        repo_student = RepoStudent()
        valid_note = ValidatorNote()
        service_note = ServiceNote(repo_student,repo_problema,repo_note,valid_note)
        student = Student(1,"Ana",1)
        problema = Problema(1,1,"aaa",2,2,2003)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,3)
        assert len(service_note.afisare_dupa_nume(1,1))==1
        student=Student(2,"maria",2)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,6)
        assert len(service_note.afisare_dupa_nume(1,1))==2
        student=Student(3,"bob",3) #nu are probleme asignate
        repo_student.adaugare_student(student)
        assert len(service_note.afisare_dupa_nume(1,1))==2
        lista=service_note.afisare_dupa_nume(1,1)
        assert lista==[Nota(Student(1,"Ana",1),problema,3),Nota(Student(2,"maria",2),problema,6)]

    def test_afisare_dupa_nota(self):
        repo_problema = RepoProblema()
        repo_note = RepoNote()
        repo_student = RepoStudent()
        valid_note = ValidatorNote()
        service_note = ServiceNote(repo_student,repo_problema,repo_note,valid_note)
        student = Student(1,"Ana",1)
        problema = Problema(1,1,"aaa",2,2,2003)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,3)
        assert len(service_note.afisare_dupa_nota(1,1))==1
        student = Student(2,"maria",2)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,6)
        assert len(service_note.afisare_dupa_nota(1,1))==2
        student = Student(3,"bob",3) #nu are probleme
        repo_student.adaugare_student(student)
        assert len(service_note.afisare_dupa_nota(1,1))==2
        lista=service_note.afisare_dupa_nume(1,1)
        assert lista==[Nota(Student(1,"Ana",1),problema,3),Nota(Student(2,"maria",2),problema,6)]

    def test_afisare_dupa_medie(self):
        repo_problema = RepoProblema()
        repo_note = RepoNote()
        repo_student = RepoStudent()
        valid_note = ValidatorNote()
        service_note = ServiceNote(repo_student,repo_problema,repo_note,valid_note)
        student = Student(1,"Ana",1)
        problema = Problema(1,1,"aaa",2,2,2003)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,3)
        student=Student(2,"maria",2)
        repo_student.adaugare_student(student)
        service_note.asignare_nota(student.studentid,problema.numar_laborator,problema.numar_problema,10)
        assert len(service_note.afisare_dupa_medie())==1

    def test_srv_asignare_student_problema(self):
        repo_problema=RepoProblema()
        repo_student=RepoStudent()
        repo_asig=RepoAsignare()
        srv_asig=ServiceAsignareStudentProblema(repo_student,repo_problema,repo_asig)
        student=Student(1,"ana",1)
        problema=Problema(1,1,"aa",1,1,2001)
        try:
            srv_asig.asignare_student_problema(student.get_studentid(),problema.get_numar_laborator(),problema.get_numar_problema())
            assert False
        except RepoError:
            assert True
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        srv_asig.asignare_student_problema(student.get_studentid(),problema.get_numar_laborator(),problema.get_numar_problema())

    """ Validator """
    #Clasa ValidatorStudent
    def test_validare_nume(self):
        valid_student=ValidatorStudent()
        studenid=23
        nume=""
        grup=4
        student=Student(studenid,nume,grup)
        assert valid_student.validare_nume(student)==False
        nume="asfj"
        student=Student(studenid,nume,grup)
        assert valid_student.validare_nume(student)==True

    def test_validare_grup(self):
        valid_student = ValidatorStudent()
        studenid = 23
        nume = "arf3"
        grup = -32
        student = Student(studenid,nume,grup)
        assert valid_student.validare_grup(student)==False
        grup=32
        student= Student(studenid,nume,grup)
        assert valid_student.validare_grup(student)==True

    def test_validare_id(self):
        valid_student = ValidatorStudent()
        studenid = -23
        nume = "arf3"
        grup = -32
        student = Student(studenid,nume,grup)
        assert valid_student.validare_id(student)==False
        studenid = 32
        student = Student(studenid,nume,grup)
        assert valid_student.validare_id(student)==True

    def test_validare_student(self):
        valid_student = ValidatorStudent()
        student=Student(-23,"asas",23)
        try:
            valid_student.validare_student(student)
            assert False
        except:
            assert True
        student=Student(1,"aa",1)
        valid_student.validare_student(student)

    #Clasa ValidatorProblema
    def test_validare_numar_laborator(self):
        valid_problema=ValidatorProblema()
        problema=Problema(23,14,"1224",12,12,2013)
        assert valid_problema.validare_numar_laborator(problema)
        problema=Problema(-32,15,"124",12,12,2013)
        assert not valid_problema.validare_numar_laborator(problema)

    def test_validare_numar_problema(self):
        valid_problema=ValidatorProblema()
        problema=Problema(12,12,"122",12,12,2013)
        assert valid_problema.validare_numar_problema(problema)
        problema=Problema(12,-32,"23",12,12,2013)
        assert not valid_problema.validare_numar_problema(problema)

    def test_validare_zi(self):
        valid_problema=ValidatorProblema()
        problema=Problema(12,12,"122",12,12,2013)
        assert valid_problema.validare_zi(problema)
        problema=Problema(12,12,"122",-32,20,2012)
        assert not valid_problema.validare_zi(problema)
        problema=Problema(12,12,"122",324,20,2013)
        assert not valid_problema.validare_zi(problema)

    def test_validare_luna(self):
        valid_problema=ValidatorProblema()
        problema=Problema(12,12,"122",12,12,2013)
        assert valid_problema.validare_luna(problema)
        problema=Problema(12,12,"122",12,333,2013)
        assert not valid_problema.validare_luna(problema)
        problema=Problema(12,12,"122",12,-32,2013)
        assert not valid_problema.validare_luna(problema)

    def test_validare_an(self):
        valid_problema=ValidatorProblema()
        problema=Problema(12,12,"122",12,12,2013)
        assert valid_problema.validare_an(problema)
        problema=Problema(12,12,"122",12,12,2099)
        assert not valid_problema.validare_an(problema)
        problema=Problema(12,12,"122",12,12,1)
        assert not valid_problema.validare_an(problema)

    def test_validare_problema(self):
        valid_problema=ValidatorProblema()
        problema = Problema(12,12,"122",12,12,2013)
        valid_problema.validare_problema(problema)
        problema = Problema(12,12,"122",12,12,2099)
        try:
            valid_problema.validare_problema(problema)
            assert False
        except:
            assert True

    #Clasa ValidatorNote
    def test_validare_nota(self):
        validator_nota=ValidatorNote()
        student=Student(1,"aa",1)
        problema=Problema(1,1,"aa",1,1,2004)
        val=5
        grade=Nota(student,problema,val)
        validator_nota.validare_nota(grade)
        grade=Nota(student,problema,-4)
        try:
            validator_nota.validare_nota(grade)
            assert False
        except ValidError:
            assert True

    """ Infrastructura """
    #Clasa RepoStudent
    def test_repo_adauga_student(self):
        repo_student=RepoStudent()
        student=Student(1,"Ana",20)
        repo_student.adaugare_student(student)
        assert len(repo_student.lista_studenti)==1
        try:
            repo_student.adaugare_student(student)
            assert False
        except RepoError as e:
            assert str(e)=="Studentul exista deja! "
    
    def test_validare_unicitate_student(self):
        repo_student = RepoStudent()
        student = Student(1,"Ana",20)
        repo_student.validare_unicitate(student)
        repo_student.adaugare_student(student)
        try:
            repo_student.validare_unicitate(student)
            assert False
        except RepoError as e:
            assert str(e)=="Studentul exista deja! "

    def test_repo_cauta_student(self):
        repo_student = RepoStudent()
        student = Student(12222,"Ana",20)
        repo_student.adaugare_student(student)
        assert repo_student.cauta_student(student)
        student=Student(222222,"Ana",20)
        try:
            repo_student.cauta_student(student)
            assert False
        except:
            assert True
    
    def test_repo_afisare_lista_studenti(self):
        repo_student = RepoStudent()
        student=Student(16666666,"Ana",20)
        repo_student.adaugare_student(student)
        assert len(repo_student.get_studenti())==1
    
    def test_get_studentid(self):
        student=Student(122222,"Ana",20)
        assert student.get_studentid()==122222
    
    def test_get_nume(self):
        student = Student(122222,"Ana",20)
        assert student.get_nume()=="Ana"
    
    def test_get_grup(self):
        student = Student(122222,"Ana",20)
        assert student.get_grup()==20

    
    def test_set_nume(self):
        student = Student(1,"Ana",20)
        student.set_nume("Ion")
        assert student.get_nume()=="Ion"

    def test_set_grup(self):
        student = Student(1,"Ana",20)
        student.set_grup(2000)
        assert student.get_grup()==2000

    #Clasa RepoProblema
    def test_repo_adaugare_problema(self):
        repo_problema=RepoProblema()
        problema=Problema(20,20,"20",20,12,2019)
        repo_problema.adaugare_problema(problema)
        assert len(repo_problema.lista_probleme)==1
        try:
            repo_problema.adaugare_problema(problema)
            assert False
        except RepoError as e:
            assert str(e)=="Problema exista deja! "
    
    def test_validare_unicitate_problema(self):
        repo_problema = RepoProblema()
        problema = Problema(20,20,"20",20,12,2019)
        repo_problema.validare_unicitate(problema)
        repo_problema.adaugare_problema(problema)
        try:
            repo_problema.validare_unicitate(problema)
            assert False
        except RepoError as e:
            assert str(e)=="Problema exista deja! "

    def test_repo_afisare_lista_probleme(self):
        repo_problema = RepoProblema()
        assert repo_problema.get_probleme()==[]
        problema = Problema(20,20,"20",20,12,2019)
        repo_problema.adaugare_problema(problema)
        assert len(repo_problema.get_probleme())==1
    
    def test_get_numar_problema(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_numar_problema()==20

    def test_get_numar_laborator(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_numar_laborator()==20
    
    def test_get_descriere(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_descriere()=="20"

    def test_get_zi(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_zi()==20
    
    def test_get_luna(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_luna()==12

    def test_get_an(self):
        problema = Problema(20,20,"20",20,12,2019)
        assert problema.get_an()==2019

    def test_repo_cauta_problema(self):
        repo_problema=RepoProblema()
        problema=Problema(8,8,"8",8,8,2008)
        try:
            repo_problema.cauta_problema(problema)
            assert False
        except:
            assert True
        repo_problema.adaugare_problema(problema)
        assert repo_problema.cauta_problema(problema)

    def test_sterge_student(self):
        repo_student=RepoStudent()
        student=Student(1,"Ana",1)
        try:
            repo_student.sterge_student(student)
            assert False
        except RepoError as e:
            assert str(e)=="Nu exista acest student in baza de date "
        repo_student.adaugare_student(student)
        repo_student.sterge_student(student)
        assert repo_student.get_studenti()==[]

    def test_sterge_problema(self):
        repo_problema=RepoProblema()
        problema=Problema(1,1,"Ana",5,5,2005)
        try:
            repo_problema.sterge_problema(problema)
            assert False
        except RepoError as e:
            assert str(e)=="Problema data nu se afla in baza de date! "
        repo_problema.adaugare_problema(problema)
        repo_problema.sterge_problema(problema)
        assert len(repo_problema.get_probleme())==0

    def test_modificare_student(self):
        repo_student=RepoStudent()
        student=Student(1,"ana",1)
        try:
            repo_student.modificare_student(student)
            assert False
        except RepoError as e:
            assert str(e)=="Nu exista acest student in baza de date "
        repo_student.adaugare_student(student)
        student_modificat=Student(1,"ananananana",5)
        repo_student.modificare_student(student_modificat)
        assert student_modificat in repo_student.get_studenti()

    def test_modificare_problema(self):
        repo_problema=RepoProblema()
        problema=Problema(1,1,"AAAAA",1,1,2001)
        try:
            repo_problema.modificare_problema(problema)
            assert False
        except RepoError as e:
            assert str(e)=="Problema data nu se afla in baza de date! "
        repo_problema.adaugare_problema(problema)
        problema_modificata=Problema(1,1,"BBBBB",2,2,2002)
        repo_problema.modificare_problema(problema_modificata)
        assert problema_modificata in repo_problema.get_probleme()

    #Clasa RepoNote
    def test_repo_asignare_nota(self):
        student=Student(1,"aa",1)
        problema=Problema(1,1,"aa",1,1,2001)
        grade=Nota(student,problema,3)
        repo_note=RepoNote()
        repo_note.asignare_nota(grade)
        try:
            repo_note.asignare_nota(grade)
            assert False
        except RepoError:
            assert True

    def test_repo_asignare_student_problema(self):
        repo_problema = RepoProblema()
        repo_student = RepoStudent()
        repo_asig = RepoAsignare()
        student = Student(1,"ana",1)
        problema = Problema(1,1,"aa",1,1,2001)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        asignare=AsignareStudentProblema(student,problema)
        repo_asig.asignare_student_problema(asignare)
        assert len(repo_asig.lista_asig)==1
        try:
            repo_asig.asignare_student_problema(asignare)
            assert False
        except RepoError:
            assert True
        assert len(repo_asig.lista_asig)==1

    def test_asig_validare_unicitate(self):
        repo_problema = RepoProblema()
        repo_student = RepoStudent()
        repo_asig = RepoAsignare()
        student = Student(1,"ana",1)
        problema = Problema(1,1,"aa",1,1,2001)
        repo_problema.adaugare_problema(problema)
        repo_student.adaugare_student(student)
        asignare = AsignareStudentProblema(student,problema)
        assert repo_asig.validare_unicitate(asignare)==None
        repo_asig.asignare_student_problema(asignare)
        try:
            repo_asig.validare_unicitate(asignare)
            assert False
        except RepoError:
            assert True

    def test_citire_fisier(self):
        pass

    def run_tests(self):
        self.test_asig_validare_unicitate()
        self.test_repo_asignare_student_problema()
        self.test_srv_asignare_student_problema()
        self.test_afisare_dupa_medie()
        self.test_afisare_dupa_nume()
        self.test_repo_asignare_nota()
        self.test_validare_problema()
        self.test_modificare_problema()
        self.test_modificare_student()
        self.test_sterge_problema()
        self.test_sterge_student()
        self.test_repo_adaugare_problema()
        self.test_repo_afisare_lista_studenti()
        self.test_validare_unicitate_problema()
        self.test_get_numar_laborator()
        self.test_get_numar_problema()
        self.test_get_descriere()
        self.test_get_zi()
        self.test_get_luna()
        self.test_get_an()
        self.test_srv_adauga_student()
        self.test_srv_validare_student()
        self.test_validare_nume()
        self.test_validare_grup()
        self.test_validare_id()
        self.test_srv_afisare_lista_studenti()
        self.test_srv_cauta_student()
        self.test_validare_an()
        self.test_validare_zi()
        self.test_validare_luna()
        self.test_validare_numar_laborator()
        self.test_validare_numar_problema()
        self.test_srv_adauga_problema()
        self.test_srv_afisare_lista_probleme()
        self.test_srv_validare_problema()
        self.test_repo_adauga_student()
        self.test_repo_afisare_lista_studenti()
        self.test_repo_cauta_student()
        self.test_validare_unicitate_student()
        self.test_get_grup()
        self.test_get_nume()
        self.test_get_studentid()
        self.test_set_grup()
        self.test_set_nume()
        self.test_srv_cauta_problema()
        self.test_repo_cauta_problema()
        self.test_srv_asignare_nota()
        self.test_validare_student()
        self.test_srv_get_studenti()
        self.test_srv_get_probleme()
        self.test_validare_nota()
        self.test_afisare_dupa_nota()
