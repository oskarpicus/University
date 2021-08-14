""" Modul ce contine functiile de test pentru modulele service, domain, valid, model si repo """

from service import *
from model import *
from repo import *

""" Modulul service """

def test_srv_adauga_cheltuiala():
    cheltuieli=creeaza_cheltuieli()
    assert srv_adauga_cheltuiala(cheltuieli,1,2,"apa",4,[])==True
    assert srv_adauga_cheltuiala(cheltuieli,-32,124,12,4,[])==False
    assert srv_adauga_cheltuiala(cheltuieli,12,52,"gaz",444,[])==False
    assert srv_adauga_cheltuiala(cheltuieli,12,12,"aslffaj",12,[])==False

def test_srv_modifica_cheltuiala():
    cheltuieli=creeaza_cheltuieli()
    assert srv_modifica_cheltuiala(cheltuieli,1,2,"apa",5,6,7,"apa",7,[])==None
    c=creeaza_c(1,2,"apa",5)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    assert srv_modifica_cheltuiala(cheltuieli,1,2,"apa",5,6,7,"gaz",7,[])==True
    assert srv_modifica_cheltuiala(cheltuieli,-42,5,"apa",6,1,2,"canal",6,[])==False

def test_srv_sterge_toate():
    cheltuieli=creeaza_cheltuieli()
    assert srv_sterge_toate(cheltuieli,12,[])==None
    c=creeaza_c(1,2,"apa",5)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    assert srv_sterge_toate(cheltuieli,-42,[])==False
    assert srv_sterge_toate(cheltuieli,124,[])==True
    assert srv_sterge_toate(cheltuieli,1,[])==True

def test_srv_sterge_consecutive():
    cheltuieli=creeaza_cheltuieli()
    assert srv_sterge_toate(cheltuieli,12,[])==None
    c = creeaza_c(1,2,"apa",5)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    assert srv_sterge_consecutive(cheltuieli,-32,12,[])==False
    assert srv_sterge_consecutive(cheltuieli,12,15,[])==True

def test_srv_sterge_dupa_tip():
    cheltuieli=creeaza_cheltuieli()
    assert srv_sterge_toate(cheltuieli,"gaz",[])==None
    assert srv_sterge_toate(cheltuieli,"asgas",[])==None
    c = creeaza_c(1,2,"apa",5)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    assert srv_sterge_dupa_tip(cheltuieli,"gaz",[])==True
    assert srv_sterge_dupa_tip(cheltuieli,"apa",[])==True

def test_srv_cheltuieli_suma_data():
    cheltuieli = creeaza_cheltuieli()
    assert srv_cheltuieli_suma_data(cheltuieli,-12)==False
    assert srv_cheltuieli_suma_data(cheltuieli,124)!=False

def test_srv_cheltuiala_tip():
    cheltuieli=creeaza_cheltuieli()
    assert srv_cheltuiala_tip(cheltuieli,"apa")!=False
    assert srv_cheltuiala_tip(cheltuieli,"Asfjlas")==False

def test_srv_cheltuiala_zi_suma():
    cheltuieli=creeaza_cheltuieli()
    assert srv_cheltuiala_zi_suma(cheltuieli,12,15)!=False
    assert srv_cheltuiala_zi_suma(cheltuieli,-32,12)==False
    assert srv_cheltuiala_zi_suma(cheltuieli,-32,-32)==False

def test_srv_elimina_cheltuiala_mica():
    cheltuieli=creeaza_cheltuieli()
    assert srv_elimina_cheltuiala_mica(cheltuieli,12)!=False
    assert srv_elimina_cheltuiala_mica(cheltuieli,-32)==False

def test_srv_elimina_cheltuiala_tip():
    cheltuieli=creeaza_cheltuieli()
    assert srv_elimina_cheltuiala_tip(cheltuieli,"124")==False
    assert srv_elimina_cheltuiala_tip(cheltuieli,"asvn")==False
    assert srv_elimina_cheltuiala_tip(cheltuieli,"apa")!=False

def test_srv_suma_dupa_tip():
    cheltuieli=creeaza_cheltuieli()
    assert srv_suma_dupa_tip(cheltuieli,"124")==False
    assert srv_suma_dupa_tip(cheltuieli,"asfcv")==False

def test_srv_sortare_tip_cheltuiala():
    cheltuieli=creeaza_cheltuieli()
    assert srv_sortare_tip_cheltuiala(cheltuieli,"124")==False
    assert srv_sortare_tip_cheltuiala(cheltuieli,"asfghgt")==False
    assert srv_sortare_tip_cheltuiala(cheltuieli,"altele")!=False

def test_srv_total_per_apartament():
    cheltuieli=creeaza_cheltuieli()
    assert srv_total_per_apartament(cheltuieli,1.2)==False
    assert srv_total_per_apartament(cheltuieli,-320)==False

""" Modulul valid """

def test_valideaza_numar_apartament():
    assert valideaza_numar_apartament(12)==True
    assert valideaza_numar_apartament(-1.2)==False
    assert valideaza_numar_apartament(-32)==False

def test_valideaza_suma():
    assert valideaza_suma(12)==True
    assert valideaza_suma(-32)==False

def test_valideaza_tip():
    assert valideaza_tip("apa")==True
    assert valideaza_tip("gaz")==True
    assert valideaza_tip("12rf")==False
    assert valideaza_tip("asfvjas")==False

def test_valideaza_zi():
    assert valideaza_zi(4)==True
    assert valideaza_zi(122222)==False
    assert valideaza_zi(-32)==False

def test_valideaza_cheltuiala():
    c=creeaza_c(12,12,"apa",12)
    assert valideaza_cheltuiala(c)==True
    c=creeaza_c(-32,42,"gaaa",12)
    assert valideaza_cheltuiala(c)==False

def test_existenta_cheltuiala():
    cheltuieli=creeaza_cheltuieli()
    c1=creeaza_c(15,15,"apa",15)
    c2=creeaza_c(7,7,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    assert existenta_cheltuiala(cheltuieli,c1)
    assert existenta_cheltuiala(cheltuieli,c2)==False

def test_vid():
    cheltuieli=creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"apa",15)
    assert vid(cheltuieli)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    assert vid(cheltuieli)==False

""" Modulul repository """

def test_repo_adaugare_cheltuiala():
    cheltuiala=creeaza_cheltuieli()
    stare=deepcopy(cheltuiala)
    c=creeaza_c(1,2,"apa",5)
    repo_adauga_cheltuiala(cheltuiala,c,[])
    repo_adauga_cheltuiala(stare,c,[])
    assert cheltuiala==stare

def test_repo_modifica_cheltuiala():
    cheltuieli = creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"apa",15)
    c2=creeaza_c(20,20,"apa",20)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    temporar=deepcopy(cheltuieli)
    repo_modifica_cheltuiala(cheltuieli,c1,c2,[])
    assert cheltuieli!=temporar
    repo_modifica_cheltuiala(temporar,c1,c2,[])
    assert cheltuieli==temporar

def test_repo_sterge_toate():
    cheltuieli=creeaza_cheltuieli()
    temporar=creeaza_cheltuieli()
    c1=creeaza_c(12,12,"apa",12)
    repo_sterge_toate(cheltuieli,c1,[])
    assert temporar==cheltuieli

def test_repo_sterge_consecutive():
    cheltuieli=creeaza_cheltuieli()
    temporar=creeaza_cheltuieli()
    c1=creeaza_c(2,2,"apa",3)
    c2=creeaza_c(4,5,"gaz",6)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    repo_sterge_consecutive(cheltuieli,1,5,[])
    assert cheltuieli==temporar

def test_repo_sterge_dupa_tip():
    cheltuieli = creeaza_cheltuieli()
    temporar = creeaza_cheltuieli()
    c1 = creeaza_c(2,2,"apa",3)
    c2 = creeaza_c(4,5,"gaz",6)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    repo_adauga_cheltuiala(temporar,c2,[])
    repo_sterge_dupa_tip(cheltuieli,"apa",[])
    assert temporar==cheltuieli

def test_get_nr_apartament():
    c=creeaza_c(12,15,"apa",5)
    assert get_nr_apartament(c)==12
    c=creeaza_c(1111,12,"canal",6)
    assert get_nr_apartament(c)==1111

def test_get_suma():
    c=creeaza_c(12,15,"apa",5)
    assert get_suma(c)==15
    c=creeaza_c(1111,12222,"canal",6)
    assert get_suma(c)==12222

def test_get_tip():
    c=creeaza_c(12,15,"apa",5)
    assert get_tip(c)=="apa"
    c=creeaza_c(16,7,"canal",6)
    assert get_tip(c)=="canal"

def test_get_zi():
    c=creeaza_c(12,215,"apa",5)
    assert get_zi(c)==5
    c=creeaza_c(12,12,"apa",31)
    assert get_zi(c)==31

def test_setter_nr_apartament():
    c=creeaza_c(12,12,"apa",4)
    setter_nr_apartament(c,15)
    assert get_nr_apartament(c)==15

def test_setter_suma():
    c=creeaza_c(15,16,"gaz",6)
    setter_suma(c,10000)
    assert get_suma(c)==10000

def test_setter_tip():
    c=creeaza_c(15,15,"gaz",5)
    setter_tip(c,"altele")
    assert get_tip(c)=="altele"

def test_setter_zi():
    c=creeaza_c(16,16,"gaz",21)
    setter_zi(c,1)
    assert get_zi(c)==1

""" Modulul domain """

def test_dm_cheltuieli_suma_data():
    cheltuieli=creeaza_cheltuieli()
    c = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    c = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    c = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c,[])
    assert dm_cheltuieli_suma_data(cheltuieli,27)==[16]
    assert dm_cheltuieli_suma_data(cheltuieli,2)==[15,16]
    assert dm_cheltuieli_suma_data(cheltuieli,2000)==[]

def test_dm_cheltuiala_tip():
    cheltuieli = creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_cheltuiala_tip(cheltuieli,"gaz")==[c1,c2]
    assert dm_cheltuiala_tip(cheltuieli,"apa")==[c3]
    assert dm_cheltuiala_tip(cheltuieli,"altele")==[]

def test_dm_cheltuiala_zi_suma():
    cheltuieli = creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_cheltuiala_zi_suma(cheltuieli,30,2)==cheltuieli
    assert dm_cheltuiala_zi_suma(cheltuieli,30,15)==[c2]

def test_total_cheltuieli_per_apartament():
    cheltuieli = creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert total_cheltuieli_per_apartament(cheltuieli,16)==28
    assert total_cheltuieli_per_apartament(cheltuieli,1222)==0
    assert total_cheltuieli_per_apartament(cheltuieli,15)==15

def test_lista_cu_apartamente():
    cheltuieli = creeaza_cheltuieli()
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert lista_cu_apartamente(cheltuieli)==[get_nr_apartament(c1),get_nr_apartament(c2)]

def test_dm_elimina_cheltuiala_tip():
    cheltuieli = creeaza_cheltuieli()
    assert dm_elimina_cheltuiala_tip(cheltuieli,"altele")==[]
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_elimina_cheltuiala_tip(cheltuieli,"gaz")==[c3]

def test_dm_elimina_cheltuiala_mica():
    cheltuieli = creeaza_cheltuieli()
    assert dm_elimina_cheltuiala_tip(cheltuieli,23)==[]
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_elimina_cheltuiala_mica(cheltuieli,400)==[]
    assert dm_elimina_cheltuiala_mica(cheltuieli,14)==[c1,c2]

def test_dm_suma_dupa_tip():
    cheltuieli = creeaza_cheltuieli()
    assert dm_elimina_cheltuiala_tip(cheltuieli,"gaz")==0
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_suma_dupa_tip(cheltuieli,"gaz")==31
    assert dm_suma_dupa_tip(cheltuieli,"altele")==0
    assert dm_suma_dupa_tip(cheltuieli,"apa")==12

def test_dm_sortare_tip_cheltuiala():
    cheltuieli = creeaza_cheltuieli()
    assert dm_elimina_cheltuiala_tip(cheltuieli,"gaz")==[]
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_sortare_tip_cheltuiala(cheltuieli,"gaz")==[c1,c2,c3]
    assert dm_sortare_tip_cheltuiala(cheltuieli,"apa")==[c3,c1,c2]
    assert dm_sortare_tip_cheltuiala(cheltuieli,"altele")==cheltuieli

def test_dm_total_per_apartament():
    cheltuieli = creeaza_cheltuieli()
    assert dm_total_per_apartament(cheltuieli,12)==0
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert dm_total_per_apartament(cheltuieli,12)==0
    assert dm_total_per_apartament(cheltuieli,16)==28

""" Modulul model """
def test_creeaza_c():
    assert creeaza_c(15,15,"apa",2)==[15,15,"apa",2]

def test_create_lista_cheltuieli():
    cheltuieli = creeaza_cheltuieli()
    assert create_lista_cheltuieli(cheltuieli)==[]
    c1 = creeaza_c(15,15,"gaz",5)
    repo_adauga_cheltuiala(cheltuieli,c1,[])
    c2 = creeaza_c(16,16,"gaz",21)
    repo_adauga_cheltuiala(cheltuieli,c2,[])
    c3 = creeaza_c(16,12,"apa",4)
    repo_adauga_cheltuiala(cheltuieli,c3,[])
    assert create_lista_cheltuieli(cheltuieli)==[c1,c2,c3]

def run_all_tests():
    test_srv_adauga_cheltuiala()
    test_srv_modifica_cheltuiala()
    test_srv_sterge_toate()
    test_srv_sterge_consecutive()
    test_srv_sterge_dupa_tip()
    test_srv_cheltuieli_suma_data()
    test_srv_cheltuiala_tip()
    test_srv_cheltuiala_zi_suma()
    test_srv_elimina_cheltuiala_mica()
    test_srv_elimina_cheltuiala_tip()
    test_srv_suma_dupa_tip()
    test_srv_sortare_tip_cheltuiala()
    test_srv_total_per_apartament()
    test_valideaza_numar_apartament()
    test_valideaza_suma()
    test_valideaza_tip()
    test_valideaza_zi()
    test_valideaza_cheltuiala()
    test_vid()
    test_existenta_cheltuiala()
    test_repo_adaugare_cheltuiala()
    test_repo_modifica_cheltuiala()
    test_repo_sterge_toate()
    test_repo_sterge_consecutive()
    test_repo_sterge_dupa_tip()
    test_get_nr_apartament()
    test_get_suma()
    test_get_tip()
    test_get_zi()
    test_setter_nr_apartament()
    test_setter_suma()
    test_setter_tip()
    test_setter_zi()
    test_dm_cheltuieli_suma_data()
    test_dm_cheltuiala_tip()
    test_dm_cheltuiala_zi_suma()
    test_total_cheltuieli_per_apartament()
    test_lista_cu_apartamente()
    test_dm_elimina_cheltuiala_tip()
    test_dm_elimina_cheltuiala_mica()
    test_dm_sortare_tip_cheltuiala()
    test_dm_total_per_apartament()
    test_create_lista_cheltuieli()
    test_creeaza_c()