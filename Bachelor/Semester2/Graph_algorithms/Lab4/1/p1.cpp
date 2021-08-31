#include<vector>
#include<iostream>
#include<fstream>
#include<string>
using namespace std;
int main(int argc, char** argv)
{
	/*ifstream f(argv[1]);
	ofstream g(argv[2]);*/

	ifstream f("in.txt");
	ofstream g("out.txt");
	vector<int> v;
	int nr_varfuri;
	f >> nr_varfuri;
	g << nr_varfuri - 1<<endl;
	int* vf = new int[nr_varfuri](); //vector de frecventa pentru elementele din vectorul de tati - il initializam cu 0
	int radacina=-1;

	for (int i = 0; i < nr_varfuri; i++)
	{
		int x;
		f >> x;
		v.push_back(x);
		if (x == -1) //am gasit radacina
			radacina = i;
		else vf[x]++;
	}

	string K = "";
	while (vf[radacina] > 0) {

		//obtinem frunza minima - prima care are frecventa zero
		int i = 0;
		for (i = 0; i < nr_varfuri && vf[i] != 0; i++);

		//i - frunza
		vf[i] = -1; //marcam ca o eliminam
		int parinte = v[i]; //parintele nodului i
		vf[parinte]--; //ii eliminam fiul ==> ii scade frecventa
		K += to_string(parinte)+" ";
	}

	g << K;
	f.close();
	g.close();
	delete[] vf;
	return 0;
}