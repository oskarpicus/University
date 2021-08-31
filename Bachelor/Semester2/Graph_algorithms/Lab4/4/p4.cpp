#include<iostream>
#include<fstream>
#include<string>
#include<queue>
using namespace std;
struct Nod {
	char c,min_ascii; //caracterul asociat si caracterul ascii minim din subgraful aferent
	int fr; //frecventa
	Nod* stanga, * dreapta;

	//constructori
	Nod(char c, int fr, char ascii) :c{ c }, fr{ fr }, min_ascii{ ascii }, stanga{ nullptr }, dreapta{ nullptr }{};

	Nod(char c, int fr, Nod* stanga, Nod* dreapta, char ascii) :c{ c }, fr{ fr }, stanga{ stanga }, dreapta{ dreapta }, min_ascii{ ascii }{};
};
int main(int argc, char** argv) {

	//ifstream f(argv[1]);
	//ofstream g(argv[2]);
	ifstream f("in.txt");
	ofstream g("out.txt");
	//f >> noskipws; //sa nu sarim white space-uri

	//coada de prioritati - nodurile vor fi adaugate crescator dupa frecventa caracterului (in caz de neegalitate)
	//altfel, dupa cel mai mic cod ascii din subgraful aferent nodului
	auto cmp = [](Nod* n1, Nod* n2) { if (n1->fr != n2->fr) return n1->fr > n2->fr; //dupa frecventa
	return n1->min_ascii > n2->min_ascii; //dupa cel mai mic cod ascii din subgraf
	};
	priority_queue<Nod*, vector<Nod*>, decltype(cmp)> q(cmp);
	queue<Nod*> coada;

	//citim datele din fisier
	int n;
	f >> n;
	f >> noskipws; //sa nu sarim white space-uri
	for (int i = 0; i < n; i++) {
		char newline;
		if (i == 0) f >> newline;
		char ch,spatiu; int fr;
		f >> ch >> spatiu>>fr>>newline;
		auto nod = new Nod(ch, fr, ch);
		q.push(nod);
		coada.push(nod);
	}

	string mesaj;
	getline(f, mesaj);

	//construim arborele binar Huffman
	n = q.size();
	for (int i = 1; i <= n - 1; i++) {
		//extragem nodurile cu cea mai mica frecventa
		auto x = q.top(); q.pop();
		auto y = q.top(); q.pop();
		int frecventa = x->fr + y->fr;
		char minim_ascii = x->min_ascii < y->min_ascii ? x->min_ascii : y->min_ascii;
		//alocam un nou nod
		auto z = new Nod('\0', frecventa, x, y, minim_ascii);
		q.push(z); //il adaugam in coada
		coada.push(z);
	}

	auto radacina = q.top(); // q va avea size = 1, deci singurul element este chiar radacina arborelui
	auto curent = radacina;

	//parcurgem arborele in functie de caracterul curent din mesaj
	for (const auto& i : mesaj) {
		if (i == '0')
			curent = curent->stanga;
		else
			curent = curent->dreapta;
		if (curent->stanga == nullptr && curent->dreapta == nullptr) //este frunza
		{
			g << curent->c;
			curent = radacina;
		}
	}

	//eliberam memoria
	while (!coada.empty()) {
		auto nod = coada.front();
		coada.pop();
		delete nod;
	}
		
	f.close();
	g.close();
	return 0;
}