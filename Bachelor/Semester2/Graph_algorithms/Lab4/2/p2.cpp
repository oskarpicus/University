#include<iostream>
#include<fstream>
#include<string>
#include<queue>
using namespace std;
int main(int argc, char** argv) {

	//ifstream f(argv[1]);
	//ofstream g(argv[2]);
	ifstream f("in.txt");
	ofstream g("out.txt");
	int M; f >> M;
	g << M + 1 << endl;
	queue<int> K;
	int* vf = new int[M + 1]();
	int* tati = new int[M + 1]();
	for (int i = 0; i < M; i++)
	{
		int x;
		f >> x;
		K.push(x);
		vf[x]++;
	}
	int x, y;
	for (int i = 0; i < M; i++) {

		//gasim cel mai mic numar care nu se gaseste in secventa (are frecventa zero)
		for ( y = 0; y < M + 1 && vf[y] != 0; y++);
		
		//primul element din secventa
		x = K.front();
		K.pop();

		//avem muchia (x,y) ; x - parintele lui y
		tati[y] = x;

		//actualizam frecventele din coada - x este scos din coada, y, adaugat
		vf[x]--; vf[y]++;
	}

	//x- radacina
	tati[x] = -1;

	for (int i = 0; i < M + 1; i++)
		g << tati[i] << " ";
	f.close();
	g.close();
	delete[] vf;
	delete[] tati;
	return 0;
}