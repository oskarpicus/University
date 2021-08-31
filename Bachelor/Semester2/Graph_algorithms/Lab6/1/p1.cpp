#include<iostream>
#include<fstream>
#include<stack>
#include<vector>
#include<limits.h>
using namespace std;

int main(int argc, char** argv) {

	//ifstream in(argv[1]);
	//ofstream out(argv[2]);

	ifstream in("in.txt");
	ofstream out("out.txt");

	int v, e; //nr varfuri, nr muchii
	in >> v >> e;

	if (e == v * (v - 1) / 2) //este graf complet
	{
		out << v << endl;
		for (int i = 0; i < v; i++)
			out << i << " ";
	}
	else if (e == 0) //graf vid
	{
		out << 1 << endl;
		for (int i = 0; i < v; i++)
			out << 0 << " ";
	}
	else {
		//algoritmul lui Kempe
		vector<vector<int>> matrice(v); //matricea de adiacenta
		vector<int> grade(v); //vector in care pastram gradul fiecarui nod
		stack<int> stiva;

		for (int i = 0; i < v; i++)
			matrice[i].resize(v);

		for (int i = 0; i < e; i++) { //citim muchiile grafului din fisier
			int nod1, nod2;
			in >> nod1 >> nod2;
			matrice[nod1][nod2] = 1;
			matrice[nod2][nod1] = 1;
			grade[nod1]++;
			grade[nod2]++;
		}

		for (int i = 0; i < v; i++) //stergem fiecare nod, in ordine crescatoare cu gradul sau
		{
			int minim = INT_MAX,nod=-1;
			for (int j = 0; j < v; j++) //obtinem varful nesters inca si cu gradul minim
				if (grade[j] != -1 && grade[j] <= minim)
				{
					minim = grade[j];
					nod = j;
				}

			//stergem nodul si il adaugam in stiva, reactualizam gradele
			for (int j = 0; j < v; j++) 
				if (matrice[nod][j] == 1 && grade[j]!=-1)
					grade[j]--;

			stiva.push(nod);
			grade[nod] = -1;
		}

		//vom folosi vectorul cu grade si pentru culori - este initializat cu -1, fiind "sterse" toate varfurile in structura repetitiva precedenta

		int max = -1; //va retine numarul de culori folosite

		for (int i = 0; i < v; i++) //"parcurgem" stiva
		{
			//extragem din varful stivei nodul curent - se vor obtine varfurile in ordinea descrescatoare a gradelor
			int curent = stiva.top();
			stiva.pop();

			vector<int> vf(v); //construim un vector de frecventa ce va contine de cate ori a fost folosita de catre varfurile adiacente o anumita culoare
			for (int j = 0; j < v; j++)
				if (matrice[curent][j] == 1 && grade[j]!=-1) //daca este adiacent cu nodul curent si a fost colorat
					vf[grade[j]]++; //marim frecventa culorii grade[j]

			//cautam culoarea minima care nu a fost folosita pentru varfurile adiacente cu curent
			int culoare = 0;
			for (; culoare < v && vf[culoare] != 0; culoare++);

			//setam culoarea nodului curent
			grade[curent] = culoare;
			if (culoare > max) //verificam daca am folosit o noua culoare si actualizam numarul de culori folosite
				max = culoare;
		}

		out << max+1 << endl; //afisam numarul de culori folosite
		for (int i = 0; i < v; i++) //afisam culoarea asignata fiecarui nod
			out << grade[i] << " ";

	}


	in.close();
	out.close();
	return 0;
}
