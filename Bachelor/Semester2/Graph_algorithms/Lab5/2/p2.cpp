#include<iostream>
#include<fstream>
#include<vector>
#include<utility>
#include<queue>
using namespace std;

int cap_rez(int u, int v, vector<vector<pair<int, int>>>& a) {
	//functie pentru determinarea capacitatii reziduale (u,v)

	if (a[u][v].first > 0) //exista arcul (u,v)
		return a[u][v].first - a[u][v].second;
	if (a[v][u].first > 0) //exista arcul (v,u)
		return a[v][u].second;
	return 0;
}


void pompare(int u, int v, vector<pair<int, int>>& varfuri, vector<vector<pair<int, int>>>& a) {

	int minim = varfuri[u].second < cap_rez(u, v, a) ? varfuri[u].second : cap_rez(u, v, a);

	a[u][v].second += minim; //creste fluxul
	a[v][u].second = -a[u][v].second; //descreste fluxul

	//se actualizeaza excesul
	varfuri[u].second -= minim;
	varfuri[v].second += minim;
}

void inaltare(int u, vector<pair<int, int>>& varfuri, vector<vector<pair<int, int>>>& a) {

	int minim = INT_MAX;
	//cautam inaltimea minima
	for (unsigned int i = 0; i < varfuri.size(); i++)
		if (minim > varfuri[i].first && cap_rez(u, i, a) > 0)
			minim = varfuri[i].first;

	varfuri[u].first = 1 + minim;
}

int main(int argc, char** argv) {

	//ifstream in(argv[1]);
	//ofstream out(argv[2]);

	ifstream in("in.txt");
	ofstream out("out.txt");

	int v, e;
	in >> v >> e;

	//initializarea preflux

	//initializarea cu 0 a vectorilor este facuta implicit
	vector<pair<int, int>> varfuri(v); // perechie de forma (inaltime varf, rezervor varf)
	vector<vector<pair<int, int>>> a(v); //matrice de adiacenta

	for (int i = 0; i < v; i++)
		a[i].resize(v);

	int sursa = 0, destinatie = v - 1;
	varfuri[sursa].first = v;

	for (int i = 0; i < e; i++) {
		int n1, n2, cap;
		in >> n1 >> n2 >> cap;
		a[n1][n2].first = cap; //capacitatea arcului
		if (n1 == sursa) //deci n2 este adiacent cu sursa
		{
			//fluxuri
			a[sursa][n2].second = cap;
			a[n2][sursa].second = (-1) * cap;
			//excesul
			varfuri[n2].second = cap;
			varfuri[sursa].second -= cap;
		}
	}

	while (1) {

		int u, varf;
		//cautam un varf diferit de sursa si destinatie care are exces
		for (u = 1; u < v - 1 && varfuri[u].second <= 0; u++);

		if (u == v - 1) //nu am gasit
			break;

		//cautam un varf  a.i. capacitate reziduala de (u,v) >0 si inaltime(u)=inaltime(varf)+1
		for (varf = 0; varf < v; varf++)
			if (cap_rez(u, varf, a) > 0 && varfuri[u].first == 1 + varfuri[varf].first)
				break;

		if (varf != v) //am gasit un astfel de varf
			pompare(u, varf, varfuri, a);
		else //nu s-a putut pomparea, dar u are exces ==> inaltare
			inaltare(u, varfuri, a);
	}

	out << varfuri[v - 1].second;
	
	return 0;
}