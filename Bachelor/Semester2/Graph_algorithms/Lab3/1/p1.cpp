#include<iostream>
#include<fstream>
#include<vector>
#include<limits.h>
using namespace std;

class Arc {
public:
	int u, v, w; //nodurile + ponderea
	Arc(int u, int v, int w) :u{ u }, v{ v }, w{ w } {}
};

int main(int argc, char** argv)
{
	//ifstream f(argv[1]);
	//ofstream g(argv[2]);
	ifstream f("in.txt");
	ofstream g("out.txt");
	int nr_varfuri, nr_arce, sursa;
	f >> nr_varfuri >> nr_arce >> sursa;
	vector<Arc> v; //unde memorez arcele
	vector<int> d; //limitele superioare ale ponderii drumulurilor
	//citesc arcele
	for (int i = 0; i < nr_arce; i++)
	{
		int x,y, w;
		f >> x >> y >> w;
		v.push_back(Arc(x, y, w));
	}

	/*Bellman Ford*/

	//initializare
	for (int i = 0; i < nr_varfuri; i++)
		d.push_back(INT_MAX);
	d[sursa] = 0;

	for(int i=1;i<nr_varfuri;i++)
		for (Arc a : v)
		{
			//relaxare
			if (d[a.v] > d[a.u] + a.w && d[a.u]!=INT_MAX)
				d[a.v] = d[a.u] + a.w;
		}

	for (auto i : d)
		if (i == INT_MAX) g << "INF ";
		else g << i<<" ";

	f.close();
	g.close();
}