#include<iostream>
#include<fstream>
#include<stack>
#include<algorithm>
#include<vector>
using namespace std;

int main(int argc, char** argv) {

	ifstream in(argv[1]);
	ofstream out(argv[2]);

	int v, e;
	in >> v >> e;

	vector<vector<int>> lista_adiacenta(v);

	//construim lista de adiacenta a grafului
	for (int i = 0; i < e; i++) {
		int x, y;
		in >> x >> y;
		//graful este neorientat
		lista_adiacenta[x].push_back(y);
		lista_adiacenta[y].push_back(x);
	}

	//Algoritmul lui Hierholzer

	stack<int> noduri_ciclu_C; //va memora nodurile componente ciclului curent
	vector<int> noduri_ciclu_R; //va memora nodurile componente ale ciclului Eulerian 

	//nod de start
	noduri_ciclu_C.push(0);
	int nod_curent = 0;


	while (!noduri_ciclu_C.empty()) { //cat timp mai avem muchii (deci mai avem cicluri pe care le putem inlantui)

		//daca nodul curent mai are muchii (deci ciclurile curente nu au marcat toate muchiile)
		if (!lista_adiacenta[nod_curent].empty()) {

			//il adaugam la ciclul curent
			noduri_ciclu_C.push(nod_curent);

			//obtinem un nod adiacent cu acesta - il luam pe ultimul din lista pentru ca avem acces direct, iar stergerea sa va fi in timp constant
			int n = lista_adiacenta[nod_curent].back();

			//este graf neorientat ==> pentru a sterge o muchie, trebuie sa stergem din cele 2 liste de adiacente ale nodurilor componente

			//localizam nodul in lista de adiacenta al celui de-al doilea nod (nodul urmator)
			auto it = find_if(lista_adiacenta[n].begin(), lista_adiacenta[n].end(), [nod_curent](int nod) {
				return nod == nod_curent;
				});

			//si stergem
			lista_adiacenta[n].erase(it);
			lista_adiacenta[nod_curent].pop_back();

			//actualizam nodul curent
			nod_curent = n;
		}
		else { //nu mai avem muchii ==> trebuie sa ne intoarcem si sa inlantuim ciclul curent la cel total
			noduri_ciclu_R.push_back(nod_curent);

			//mergem inapoi in ciclul curent
			nod_curent = noduri_ciclu_C.top();
			noduri_ciclu_C.pop();
		}
	}


	//afisam ciclul total - in ordine inversa pentru a reconstrui pasii pe care i-am urmat
	for (int i = noduri_ciclu_R.size() - 1; i >= 1; i--)
		out << noduri_ciclu_R[i] << " ";

	in.close();
	out.close();
	return 0;
}