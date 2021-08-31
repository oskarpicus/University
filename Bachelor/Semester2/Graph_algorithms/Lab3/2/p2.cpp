#include<iostream>
#include<fstream>
#include<queue>
#include<algorithm>
#include<vector>
#include<limits.h>
#include<stdlib.h>
using namespace std;
class Arc {
	//clasa de obiecte de tip Arc
public:
	int u, v, w; //nodurile ce compun arcul (u,v) + pondere

	/*
	Constructor
	pre: u,v,w: int
	*/
	Arc(int u, int v, int w) :u{ u }, v{ v }, w{ w }{}
};

bool cmp(Arc a1, Arc a2) {
	/*
	Functie care compara 2 arce dupa numarul nodului sursa - in caz de egalitate, dupa nodul destinatie
	*/
	if (a1.u != a2.u) return a1.u < a2.u;
	return a1.v < a2.v;
}

int main(int argc, char** argv)
{
	//ifstream f(argv[1]);
	//ofstream g(argv[2]);
	ifstream f("in.txt");
	ofstream g("out.txt");
	int nr_varfuri, nr_arce;
	f >> nr_varfuri >> nr_arce;
	vector<Arc> v; //vector pentru retinerea arcelor
	int s = nr_varfuri; //nodul nou - pentru G'
	vector<int> d; //limitele superioare a ponderii drumului pentru G' - va retine ulterior valorile functiei h:V->R din algoritm
	vector<int> distanta; //limitele superioare a ponderii drumului pentru alg. lui Dijkstra

	//citesc arcele
	for (int i = 0; i < nr_arce; i++)
	{
		int x, y, w;
		f >> x >> y >> w;
		v.push_back(Arc(x, y, w));
	}

	for (int i = 0; i < nr_varfuri; i++)
		v.push_back(Arc(s, i, 0)); //noile arce pentru G'

	//determinam graful nou definit, v2=vectorul de Arce
	int nr_varfuri_prim = nr_varfuri + 1, nr_arce_prim = v.size();

	/*Bellman Ford*/
	//s - nod sursa

	//initializare
	for (int i = 0; i < nr_varfuri_prim; i++)
	{
		d.push_back(INT_MAX);
		if (i != nr_varfuri_prim - 1)
			distanta.push_back(INT_MAX); //initializez pentru alg. lui Dijsktra
	}
	d[s] = 0;


	for (int i = 1; i < nr_varfuri_prim; i++) //parcurg varfurile grafului G'
		for (Arc& arc : v) //iterez arcele
			//relaxare
			if (d[arc.v] > d[arc.u] + arc.w && d[arc.u] != INT_MAX)
				d[arc.v] = d[arc.u] + arc.w;

	bool ok = true;
	for (Arc& arc : v)
		if (d[arc.v] > d[arc.u] + arc.w && d[arc.u] != INT_MAX)
		{
			ok = false; break;
		}

	if (ok == false) //avem circuite negative
	{
		g << "-1";
		f.close();
		g.close();
		exit(0);
	}

	//graful nu are circuite negative, deci v.d=delta(s,v), pt orice v din V

	sort(v.begin(), v.end(), cmp); //sortez arcele dupa cmp


	// h (din algoritm) = d
	int counter = 0;
	for (Arc& arc : v)
	{
		arc.w = arc.w + d[arc.u] - d[arc.v]; //recalculam ponderile
		if (arc.u != s) //doar arcele initiale
		{
			g << arc.u << " " << arc.v << " " << arc.w << endl; //afisez arcele dupa reponderare
			if (counter <= nr_arce)
				v[counter].w = arc.w; //actualizez arcele
		}
		counter++;
	}

	int** D = (int**)malloc(nr_varfuri * sizeof(int*)); //aloc memorie pentru matricea rezultat

	for (int i = 0; i < nr_varfuri; i++) //parcurg varfurile
	{
		D[i] = (int*)malloc(nr_varfuri * sizeof(int));

		/*Dijkstra*/
		//i - nodul sursa

		//initializare

		//coada cu prioritati - nodurile se adauga dupa atributul distanta (crescator)
		auto compare = [distanta](int nod1, int nod2) { return distanta[nod1] > distanta[nod2]; };
		priority_queue<int, vector<int>, decltype(compare)> q(compare);

		for (int j = 0; j < nr_varfuri; j++)
			distanta[j] = INT_MAX;
		distanta[i] = 0;
		q.push(i);

		while (!q.empty())
		{
			int u = q.top();
			q.pop();

			for (Arc& arc : v)
			{
				if (arc.u == u) //varfuri adiacente
				{
					//relaxare, arc.v = nod adiacent
					if (distanta[arc.v] > distanta[arc.u] + arc.w && distanta[arc.u] != INT_MAX)
					{
						distanta[arc.v] = distanta[arc.u] + arc.w;
						//il adaug in coada - daca a mai fost adaugat odata, doar parcurge for-ul si nu modifica nimic
						q.push(arc.v);
					}
				}
				if (arc.u > u)
					break;
			}

		}

		//u.d = delta(i,u), pt orice u din V
		for (int j = 0; j < nr_varfuri; j++)
			if (distanta[j] != INT_MAX)
				D[i][j] = distanta[j] - d[i] + d[j];
			else
				D[i][j] = INT_MAX;
	}

	//afisez rezultatul
	for (int i = 0; i < nr_varfuri; i++)
	{
		for (int j = 0; j < nr_varfuri; j++)
			if (D[i][j] == INT_MAX)
				g << "INF ";
			else g << D[i][j] << " ";
		g << endl;
		free(D[i]);
	}
	free(D);

	f.close();
	g.close();
	return 0;
}