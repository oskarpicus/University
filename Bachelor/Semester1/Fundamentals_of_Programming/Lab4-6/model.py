""" Modul ce creeaza entitati abstracte de tip cheltuiala """
def creeaza_cheltuieli():
    """
    Functie care creeaza entitatii care contin toate cheltuielile de la blocul de apartamente
    :return: entitatea
    """
    #return [[20,20,"apa",20],[15,15,"gaz",15],[1,1,"apa",1],[11,11,"altele",11]]
    #return {"nr_apartament":[20,15,1,11],"suma":[20,15,1,11],"tip":["apa","gaz","apa","altele"],"zi":[20,15,1,11]}
    return []
    #return {"nr_apartament": [],"suma": [],"tip": [],"zi": []}

def creeaza_c(nr_apartament,suma,tip,zi):
    """
    Functie care creeaza o cheltuiala cu nr_apartament,suma,tip,zi
    :param nr_apartament: int
    :param suma: int
    :param tip: string
    :param zi: int
    :return: cheltuiala
    """
    return [nr_apartament,suma,tip,zi]

def create_lista_cheltuieli(cheltuieli):
    """
    Functie care creeaza o lista cu toate cheltuielile memorate in entitatea cheltuieli
    :param cheltuieli: o entitate ce memoreaza toate cheltuielile unui bloc de apartamente
    :return: lista cu toate cheltuielile
    """
    lista = []
    if type(cheltuieli)==list:
        for i in cheltuieli:
            lista.append(i)
    else:
        for i in range(0,len(cheltuieli ["nr_apartament"])):
            c = [cheltuieli ["nr_apartament"][i],cheltuieli ["suma"][i],cheltuieli ["tip"][i],cheltuieli ["zi"][i]]
            lista.append(c)
    return lista