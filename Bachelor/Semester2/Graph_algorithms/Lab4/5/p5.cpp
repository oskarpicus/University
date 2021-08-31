#include<iostream>
#include<fstream>
#include<limits.h>
#include<vector>
#include<queue>
#include<utility>
using namespace std;
int main(int argc, char** argv) {

	//ifstream f(argv[1]);
	//ofstream g(argv[2]);

	ifstream f("in.txt");
	ofstream g("out.txt");

	int v, e;
	f >> v >> e;

	//contine elemente de tip vector cu perechi (indice nod, ponderea muchiei dintre ei)
	vector<vector<pair<int, int>>> lista_adiacenta(v);

	int* key = new int[v];
	int* parinte = new int[v];

	priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> q;

	for (int i = 0; i < e; i++) {
		int n1, n2, pondere;
		f >> n1 >> n2 >> pondere;
		lista_adiacenta[n1].push_back(pair<int, int>(n2, pondere));
		lista_adiacenta[n2].push_back(pair<int, int>(n1, pondere));
	}

	//initializare
	for (int i = 1; i < v; i++) {
		key[i] = INT_MAX;
		parinte[i] = -1;
	}

	//pentru radacina
	parinte[0] = -1;
	key[0] = 0; 
	q.push(pair<int, int>(0, 0));
	int cost = 0;

	int* scosi = new int[v](); //retinem daca nodul a fost sau nu scos din coada
	while (!q.empty()) {
		auto u = q.top().second; //extragem varful
		q.pop();
		scosi[u] = 1;
		for (auto& pereche : lista_adiacenta[u]) { //parcurgem nodurile adiacente cu u
			int varf = pereche.first,pondere=pereche.second;
			if (scosi[varf] == 0 && pondere < key[varf]) {
					parinte[varf] = u;
					if (key[varf] == INT_MAX) //calculam in acelasi timp si costul
						cost +=pondere;
					else
						cost = cost - key[varf] + pondere;
					key[varf] = pondere;
					q.push(pair<int,int>(key[varf],varf));
			}
		}
	}
	delete[] scosi;


	g << cost<<endl<<v - 1<<endl; //este arbore, deci are v-1 muchii (v = nr. noduri)
	
	//afisam muchiile in ordine crescatoare
	for (int curent = 0; curent < v; curent++) {

		int p = parinte[curent];
		int ultim = -1;
		bool ok = false;
		for (int i = curent; i < v; i++) {
			if (parinte[i] == curent)
			{
				if (curent<p && p < i && ok == false && p!=-1)
				{
					g << curent << " " << p << endl;
					g << curent << " " << i << endl;
					ok = true;
				}
				else
					g << curent << " " << i << endl;
			}
		}
		if(p!=-1 && curent<p && ok==false)
			g << curent << " " << p << endl;
	}
	

	delete[] key;
	delete[] parinte;
	f.close();
	g.close();
	return 0;
}
