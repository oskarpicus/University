from unittest import TestCase
from infrastructura import *
from validator import *
from business import *
from exceptii import *

class TesteServiceStudent(TestCase):
    def setUp(self) -> None:
        self.valid = ValidatorStudent()
        self.repo = RepoStudent()
        self.service = ServiceStudent(self.repo,self.valid)
        self.student=Student(1,"3",10)

    def test_adauga_student (self):
        self.assertRaisesRegex(ValidError,"Numele nu poate fi vid\n",self.service.adauga_student,3,"",5)
        self.assertRaisesRegex(ValidError,"Numarul grupei nu poate fi negativ\n",self.service.adauga_student,4,"5",-5)
        self.assertRaisesRegex(ValidError,"ID-ul nu poate fi negativ\n",self.service.adauga_student,-2,"4",4)
        self.service.adauga_student(1,"2",3)

    def test_modificare_student(self):
        self.assertRaisesRegex(RepoError,"Nu exista acest student in baza de date ",self.service.modificare_student,1,"3",10)
        self.repo.adaugare_student(self.student)
        self.service.modificare_student(1,"5",11)
        self.assertTrue(self.student.get_grup()==11)
        self.assertTrue(self.student.get_nume()=="5")

    def test_cauta_student(self):
        self.assertRaises(RepoError,self.service.cauta_student,1)
        self.repo.adaugare_student(self.student)
        self.service.cauta_student(self.student.get_studentid())

    def test_sterge_student(self):
        self.assertRaises(RepoError,self.service.sterge_student,1)
        self.repo.adaugare_student(self.student)
        self.service.sterge_student(self.student.get_studentid())

    def test_get_all(self):
        self.repo.adaugare_student(self.student)
        self.assertTrue(len(self.service.get_studenti())==1)

    def tearDown(self) -> None:
        self.student=Student(1,"3",10)

class TesteServiceProblema(TestCase):
    def setUp(self) -> None:
        self.valid=ValidatorProblema()
        self.repo=RepoProblema()
        self.service=ServiceProblema(self.repo,self.valid)
        self.problema=Problema(1,1,"a",1,1,2001)

    def test_cauta_problema(self):
        self.assertRaises(RepoError,self.service.cauta_problema,1,1)
        self.assertRaises(ValidError,self.service.cauta_problema,2,-3)
        self.repo.adaugare_problema(self.problema)
        self.assertTrue(self.service.cauta_problema(1,1)==self.problema)

    def test_modificare_problema(self):
        self.assertRaises(ValidError,self.service.modificare_problema,-3,3, "aaa",20002,19,2002)
        self.assertRaises(RepoError,self.service.modificare_problema,1,1,"a",1,1,2001)
        self.repo.adaugare_problema(self.problema)
        self.service.modificare_problema(1,1,"aaaa",2,2,2002)
        self.assertTrue(Problema(1,1,"aaa",2,2,2002) in self.repo.lista_probleme)

    def test_sterge_problema(self):
        self.assertRaises(ValidError,self.service.sterge_problema,-3,3)
        self.assertRaises(RepoError,self.service.sterge_problema,1,1)
        self.repo.adaugare_problema(self.problema)
        self.service.sterge_problema(1,1)
        self.assertFalse(Problema(1,1,"a",1,1,2001) in self.repo.lista_probleme)

    def test_adauga_problema(self):
        self.assertRaises(ValidError,self.service.adauga_problema,-3,3,"aaa",20002,19,2002)
        self.service.adauga_problema(1,1,"a",1,1,2001)
        self.assertTrue(Problema(1,1,"a",1,1,2001) in self.repo.lista_probleme)
        self.assertRaises(RepoError,self.service.adauga_problema,1,1,"a",1,1,2001)

class TesteServiceNote(TestCase):
    def setUp(self) -> None:
        self.valid=ValidatorNote()
        self.repo_note=RepoNote()
        self.repo_student=RepoStudent()
        self.repo_problema=RepoProblema()
        self.service=ServiceNote(self.repo_student,self.repo_problema,self.repo_note,self.valid)
        self.repo_student.adaugare_student(Student(1,"a",1))
        self.repo_problema.adaugare_problema(Problema(1,1,"a",1,1,2001))
        self.nota=Nota(Student(1,"a",1),Problema(1,1,"a",1,1,2001),5)

    def test_asignare_nota(self):
        self.assertRaises(RepoError,self.service.asignare_nota,4,1,1,5)
        self.assertRaises(RepoError,self.service.asignare_nota,4,4,1,5)
        self.assertRaises(RepoError,self.service.asignare_nota,1,4,1,5)
        self.assertRaises(RepoError,self.service.asignare_nota,-4,1,1,3)
        self.assertRaises(RepoError,self.service.asignare_nota,4,-1,1,3)
        self.assertRaises(RepoError,self.service.asignare_nota,4,1,-1,3)
        self.assertRaises(ValidError,self.service.asignare_nota,1,1,1,13)
        self.assertRaises(ValidError,self.service.asignare_nota,1,1,1,-3)
        self.service.asignare_nota(1,1,1,7)
        self.assertTrue(self.nota in self.repo_note.lista_note)

    def test_afisare_dupa_nume(self):
        student=Student(2,"maria",2)
        self.repo_student.adaugare_student(student)
        self.repo_note.asignare_nota(self.nota)
        self.assertTrue(len(self.service.afisare_dupa_nume(1,1))==1)
        self.assertTrue(len(self.service.afisare_dupa_nume(1,1))==1)
        self.repo_note.asignare_nota(Nota(student,Problema(1,1,"a",1,1,2001),6))
        self.assertTrue(len(self.service.afisare_dupa_nume(1,1))==2)
        self.assertTrue(self.service.afisare_dupa_nume(1,1)==[Nota(Student(1,"a",1),Problema(1,1,"a",1,1,2001),5),Nota(student,Problema(1,1,"a",1,1,2001),6)])

    def test_afisare_dupa_nota(self):
        student = Student(2,"maria",2)
        self.repo_student.adaugare_student(student)
        self.repo_note.asignare_nota(self.nota)
        self.assertTrue(len(self.service.afisare_dupa_nota(1,1))==1)
        self.assertTrue(len(self.service.afisare_dupa_nota(1,1))==1)
        self.repo_note.asignare_nota(Nota(student,Problema(1,1,"a",1,1,2001),6))
        self.assertTrue(len(self.service.afisare_dupa_nume(1,1))==2)
        self.assertTrue(self.service.afisare_dupa_nota(1,1)==[Nota(student,Problema(1,1,"a",1,1,2001),6),Nota(Student(1,"a",1),Problema(1,1,"a",1,1,2001),5)])

    def test_afisare_dupa_medie(self):
        self.assertTrue(self.service.afisare_dupa_medie()==[])
        student = Student(2,"maria",2)
        self.repo_student.adaugare_student(student)
        self.repo_note.asignare_nota(Nota(student,Problema(1,1,"a",1,1,2001),2))
        self.assertTrue(len(self.service.afisare_dupa_medie())==1)
        self.assertTrue(self.service.afisare_dupa_medie()[0].get_nume()=="maria")

#modulul infrastructura
class TesteRepoStudent(TestCase):
    def setUp(self) -> None:
        self.repo=RepoStudent()
        self.student=Student(1,"ana",1)
        self.repo.adaugare_student(self.student)

    def tearDown(self) -> None:
        self.student=Student(1,"ana",1)

    def test_repo_adaugare_student(self):
        self.assertRaises(RepoError,self.repo.adaugare_student,self.student)
        self.student=Student(2,"ana",1)
        self.repo.adaugare_student(self.student)
        self.assertTrue(self.repo.lista_studenti[1].get_nume()=="ana (1)")

    def test_repo_validare_unicitate(self):
        self.assertRaisesRegex(RepoError,"Studentul exista deja! ",self.repo.validare_unicitate,self.student)

    def test_repo_cauta_student(self):
        self.assertTrue(self.repo.cauta_student(self.student)==self.student)
        self.assertRaises(RepoError,self.repo.cauta_student,Student(2,"aa",4))

    def test_repo_sterge_student(self): #Black Box
        self.assertRaises(RepoError,self.repo.sterge_student,Student(2,"aa",5))
        self.repo.sterge_student(self.student)
        self.assertTrue(self.repo.lista_studenti==[])

    def test_repo_modificare_student(self):
        self.assertRaises(RepoError,self.repo.modificare_student,Student(2,"aa",5))
        student_modificat=Student(1,"66",66)
        self.repo.modificare_student(student_modificat)
        self.assertTrue(student_modificat in self.repo.lista_studenti)

    #teste pentru fisier
class TesteRepoFisier(TestCase):
    def setUp(self) -> None:
        self.repo=RepoStudentFile("fisier_test.txt")

    def tearDown(self) -> None:
        f=open("fisier_test.txt","w")
        f.write("")
        f.close()

    def test_store_to_file(self):
        student=Student(1000,"1000",1000)
        self.repo.adaugare_student(student)
        self.assertTrue(self.repo.cauta_student(student)==student)

    def test_load_from_file(self):
        self.repo.load_from_file()
        student = Student(1000,"1000",1000)
        self.repo.adaugare_student(student)
        lista_studenti=self.repo.get_studenti()
        self.assertTrue(lista_studenti==[Student(1000,"1000",1000)])

class TesteRepoProblema(TestCase):
    def setUp(self) -> None:
        self.repo=RepoProblema()
        self.problema=Problema(1,1,"a",2,3,2002)
        self.problema2=Problema(2,2,"a",2,2,2002)
        self.repo.adaugare_problema(self.problema)

    def test_repo_adauga_problema(self):
        self.assertRaisesRegex(RepoError,"Problema exista deja! ",self.repo.adaugare_problema,self.problema)
        self.repo.adaugare_problema(self.problema2)
        self.assertTrue(self.problema2 in self.repo.lista_probleme)

    def test_repo_cauta_problema(self):
        self.assertTrue(self.repo.cauta_problema(self.problema)==self.problema)
        self.assertRaisesRegex(RepoError,"Problema nu exista ",self.repo.cauta_problema,self.problema2)

    def test_repo_sterge_problema(self):
        self.assertRaisesRegex(RepoError,"Problema data nu se afla in baza de date! ",self.repo.sterge_problema,self.problema2)
        self.repo.sterge_problema(self.problema)
        self.assertTrue(len(self.repo.lista_probleme)==0)

    def test_repo_modificare_problema(self):
        self.assertRaisesRegex(RepoError,"Problema data nu se afla in baza de date! ",self.repo.sterge_problema,
                               self.problema2)
        problema=Problema(1,1,"aaa",2,3,2002)
        self.repo.modificare_problema(problema)
        self.assertTrue(problema in self.repo.lista_probleme)

class TesteRepoNote(TestCase):
    def setUp(self) -> None:
        self.repo=RepoNote()
        self.nota=Nota(Student(1,"ana",1),Problema(1,1,"a",1,1,2001),4)

    def test_asignare_nota(self):
        self.repo.asignare_nota(self.nota)
        self.assertRaisesRegex(RepoError,"Nota a fost adaugata deja! ",self.repo.asignare_nota,self.nota)

class TesteRepoAsignare(TestCase):
    def setUp(self) -> None:
        self.repo=RepoAsignare()
        self.asig=AsignareStudentProblema(Student(1,"ana",1),Problema(1,1,"a",1,1,2001))

    def test_asignare_student_problema(self):
        self.repo.asignare_student_problema(self.asig)
        self.assertRaisesRegex(RepoError,"Problema a fost deja asignata la acest student ",self.repo.asignare_student_problema,self.asig)

#modulul validator
class TesteValidatorStudent(TestCase):
    def setUp(self) -> None:
        self.valid=ValidatorStudent()
        self.student1=Student(-4,"a",5)
        self.student2=Student(2,"",5)
        self.student3=Student(2,"a",-4)
        self.student4=Student(-4,"",-4)

    def test_validare_student(self):
        self.valid.validare_student(Student(1,"2",3))
        self.assertRaisesRegex(ValidError,"ID-ul nu poate fi negativ\n",self.valid.validare_student,self.student1)
        self.assertRaisesRegex(ValidError,"Numele nu poate fi vid\n",self.valid.validare_student,self.student2)
        self.assertRaisesRegex(ValidError,"Numarul grupei nu poate fi negativ\n",self.valid.validare_student,self.student3)
        self.assertRaisesRegex(ValidError,"ID-ul nu poate fi negativ\nNumele nu poate fi vid\nNumarul grupei nu poate fi negativ\n",self.valid.validare_student,self.student4)

class TesteValidatorProblema(TestCase):
    def setUp(self) -> None:
        self.valid=ValidatorProblema()
        self.problema1=Problema(-2,3,"a",1,2,2001)
        self.problema2=Problema(3,-4,"a",1,1,2001)
        self.problema4=Problema(1,1,"a",222,3,2001)
        self.problema5=Problema(1,1,"a",221,23,2001)
        self.problema6=Problema(1,1,"a",1,1,1999)
        self.problema7=Problema(-3,-3,"",311,32,20000)
        self.problema8=Problema(1,1,"a",1,1,2001)

    def test_validare_problema(self):
        self.valid.validare_problema(self.problema8)
        self.assertRaisesRegex(ValidError,"Numarul laboratorului nu poate fi negativ\n",self.valid.validare_problema,self.problema1)
        self.assertRaisesRegex(ValidError,"Numarul problemei nu poate fi negativ\n",self.valid.validare_problema,self.problema2)
        self.assertRaisesRegex(ValidError,"Ziua pentru deadline este invalida\n",self.valid.validare_problema,self.problema4)
        self.assertRaisesRegex(ValidError,"Luna pentru deadline este invalida\n",self.valid.validare_problema,self.problema5)
        self.assertRaisesRegex(ValidError,"Anul pentru deadline este invalid",self.valid.validare_problema,self.problema6)
        self.assertRaisesRegex(ValidError,"Numarul laboratorului nu poate fi negativ\nNumarul problemei nu poate fi negativ\nZiua pentru deadline este invalida\nLuna pentru deadline este invalida\nAnul pentru deadline este invalid",self.valid.validare_problema,self.problema7)

class TesteValidatorNote(TestCase):
    def setUp(self) -> None:
        self.valid=ValidatorNote()
        student=Student(1,"a",1)
        problema=Problema(1,1,"a",1,1,2001)
        self.nota=Nota(student,problema,-3)

    def test_validare_nota(self):
        self.assertRaisesRegex(ValidError,"Nota nu poate fi negativa/nula/mai mare ca 10",self.valid.validare_nota,self.nota)
