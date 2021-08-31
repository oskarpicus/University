#include<iostream>
#include<fstream>
#include<string>
#include<utility>
#include<map>
#include<queue>
using namespace std;

typedef struct Nod {
	char c, min_ascii; //caracterul asociat si codul ascii minim din subgraful aferent
	int fr; //frecventa
	Nod* stang, * drept;

	//constructori
	Nod(char c, int fr, char ascii) :c{ c }, fr{ fr }, stang{ nullptr }, drept{ nullptr }, min_ascii{ ascii }{};

	Nod(char c, int fr, Nod* stang, Nod* drept, char ascii) :c{ c }, fr{ fr }, stang{ stang }, drept{ drept }, min_ascii{ ascii }{};
}Nod;

typedef pair<int, string> Pereche;
int main(int argc, char** argv) {

	//ifstream f(argv[1]);
	//ofstream g(argv[2]);
	ifstream f("in.txt");
	ofstream g("out.txt");
	void parcurge(Nod * nod, string str, map<char, Pereche> & m); //forward declaration
	map <char, Pereche> m; //dictionar cu elemente de tip (caracter, (frecventa caracterului, codul Huffman al caracterului) )
	string mesaj;
	f >> noskipws; //sa nu sarim white space-uri
	getline(f, mesaj);

	for (auto c : mesaj) {
		if (m.find(c) == m.end()) //cheie (caracter) noua (nou)
			m.insert(pair<char, Pereche>(c, pair<int, string>(1, "")));
		else //incrementam frecventa
			m[c].first++;
	}

	//coada de prioritati - nodurile vor fi adaugate crescator dupa frecventa caracterului (in caz de neegalitate)
	//altfel, dupa cel mai mic cod ascii din subgraful aferent nodului
	auto cmp = [](Nod* n1, Nod* n2) { if (n1->fr != n2->fr) return n1->fr > n2->fr; //dupa frecventa
	return n1->min_ascii > n2->min_ascii; //dupa cel mai mic cod ascii din subgraf
	};
	priority_queue<Nod*, vector<Nod*>, decltype(cmp)> q(cmp);

	g << m.size() << endl;
	for (auto& i : m)
	{
		g << i.first << " " << i.second.first << endl; //afisam caracterul si frecventa acestuia in mesaj
		q.push(new Nod(i.first, i.second.first, i.first)); //adaugam noduri frunza in coada - au min_ascii = caracterul asociat
	}

	//construim arborele binar Huffman
	int n = q.size();
	for (int i = 1; i <= n - 1; i++) {
		//extragem nodurile cu cea mai mica frecventa
		auto x = q.top(); q.pop();
		auto y = q.top(); q.pop();
		int frecventa = x->fr + y->fr; 
		char minim_ascii = x->min_ascii < y->min_ascii ? x->min_ascii : y->min_ascii;
		//alocam un nou nod
		auto z = new Nod('\0', frecventa, x, y, minim_ascii);
		q.push(z); //il adaugam in coada
	}

	auto radacina = q.top(); // q va avea size = 1, deci singurul element este chiar radacina arborelui

	parcurge(radacina, "", m);

	for (auto c : mesaj) //afisam codul fiecarui caracter din mesaj
		g << m[c].second;
	
	f.close();
	g.close();
	return 0;
}

//Functie pentru parcurgerea arborelui binar Huffman - se construiesc si codurile Huffman asociate fiecarui caracter
void parcurge(Nod* nod, string str, map<char, Pereche>& m) {
	
	if (nod == nullptr) //am ajuns la sfarsitul parcurgerii
		return;

	if (nod->drept == nullptr && nod->stang == nullptr) //este frunza, deci un caracter
		m[nod->c].second = str; //ii definim codul

	parcurge(nod->stang, str + "0", m); //parcurgem dupa ramura stanga a nodului curent
	parcurge(nod->drept, str + "1", m); //parcurgem dupa ramura dreapta a nodului curent
	delete nod;
}