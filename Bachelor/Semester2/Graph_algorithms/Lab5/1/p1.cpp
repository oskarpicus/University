#include<iostream>
#include<fstream>
#include<vector>
#include<limits.h>
#include<queue>
#include<utility>
using namespace std;


bool BFS(vector<vector<pair<int, int>>>& a, int s, int t, vector<int>& parinte) {
	/*
	Functie pentru a determina daca exista o cale reziduala intre un nod sursa si un nod destinatie
	pre: a- vector<vector<pair<int,int>>> matricea de adiacenta a grafului, s- int nodul sursa, t- int nodul destinatie, parinte- vector<int> tablou in care
		stocheaza parintii nodurilor vizitate
	post: true, daca exista o cale reziduala intre s si t, false, altfel
	*/

	queue<int> q;
	q.push(s);

	//initializam parintii
	for (unsigned int i = 0; i < a.size(); i++)
		parinte[i] = -1;
	parinte[s] = s;

	while (!q.empty()) {
		int v = q.front();
		q.pop();

		for (unsigned int w = 0; w < a.size(); w++) {

			if (w == v) //nu exista bucle in graf
				continue;
			if (a[v][w].first - a[v][w].second <= 0) //doar daca are capacitatea reziduala >0 consideram arcul
				continue;

			if (parinte[w] == -1) { //nu a fost vizitat pana acum
				q.push(w);
				parinte[w] = v;
			}

		}
	}

	//returnam daca varful destinatie a fost vizitat in parcurgere (adica are un parinte), deci exista o cale reziduala de la s la t
	return parinte[t] != -1;
}

int main(int argc, char** argv) {

	//ifstream in(argv[1]);
	//ofstream out(argv[2]);

	ifstream in("in.txt");
	ofstream out("out.txt");

	int v, e;
	in >> v >> e;

	//initializam matricea de adiacenta - pentru 2 indici i si j, a[i][j] = (capacitate arc i->j, flux arc i->j)
	vector < vector<pair<int, int>>> a(v);

	for (int i = 0; i < v; i++)
	{
		a[i].resize(v);
		for (int j = 0; j < v; j++)
		{
			a[i][j].first = 0; a[i][j].second = 0;
		}
	}

	for (int i = 0; i < e; i++) {
		int n1, n2, cap;
		in >> n1 >> n2 >> cap;
		a[n1][n2] = pair<int, int>(cap, 0); //capacitatea si fluxul
	}

	//vector din care vom determina drumul obtinut de BFS
	vector<int> parinte(v);

	int sursa = 0, destinatie = v - 1;
	int flux_total = 0;

	while (BFS(a, sursa, destinatie, parinte)) { //cat timp mai exista cai reziduale

		//obtinem capacitatea reziduala minima
		int cap_rez = INT_MAX;
		for (int p1 = destinatie; p1 != sursa; p1 = parinte[p1]) { //parcurgem arcele componente drumului dintre s si t

			int p2 = parinte[p1];
			cap_rez = cap_rez < (a[p2][p1].first - a[p2][p1].second) ? cap_rez : (a[p2][p1].first - a[p2][p1].second);
		}

		//actualizam arcele de pe acest drum
		for (int p1 = destinatie; p1 != sursa; p1 = parinte[p1]) {
			int p2 = parinte[p1];
			a[p1][p2].second -= cap_rez;
			a[p2][p1].second += cap_rez;
		}
		flux_total += cap_rez;
	}

	out << flux_total;

	in.close();
	out.close();
	return 0;
}