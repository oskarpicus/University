#include "service.h"
#include "domain.h"
#include <fstream>
#include <numeric>

Service::Service(Repo& repo,Valid& valid): repo{repo},valid{valid} {}


void Service::service_adauga(const string& denumire, const string& destinatie, const string& tip, const int& pret)
{
	Oferta o = Oferta(denumire, destinatie, tip, pret);
	this->valid.valideaza_oferta(o);
	this->repo.repo_adauga(o);
	this->undolist.push_back(make_unique<UndoAdauga>(repo, o));
}

bool Service::service_cauta(const string& denumire, const string& destinatie, const string& tip, const int& pret) const
{
	Oferta o = Oferta(denumire, destinatie, tip, pret);
	this->valid.valideaza_oferta(o);
	return this->repo.repo_cauta(o);
}

void Service::service_sterge(const string& denumire, const string& destinatie, const string& tip, const int& pret)
{
	Oferta o = Oferta(denumire, destinatie, tip, pret);
	this->valid.valideaza_oferta(o);
	this->repo.repo_sterge(o);
	this->undolist.push_back(make_unique<UndoSterge>(repo, o));
}

void Service::service_modifica(const string& denumire1, const string& destinatie1, const string& tip1, const int& pret1, const string& denumire2, const string& destinatie2, const string& tip2, const int& pret2)
{
	Oferta o1 = Oferta(denumire1, destinatie1, tip1, pret1),o2=Oferta(denumire2, destinatie2, tip2, pret2);
	this->valid.valideaza_oferta(o1);
	this->valid.valideaza_oferta(o2);
	this->repo.repo_modifica(o1, o2);
	this->undolist.push_back(make_unique<UndoModifica>(repo, o1,o2));
}

int Service::service_dim() const
{
	return this->repo.repo_dim();
}

const Lista<Oferta> Service::service_get_all()  {
	return this->repo.repo_get_all();
}

const Lista<Oferta> Service::service_filtrare_destinatie(string destinatie) {
	Lista<Oferta> rez;
	Lista<Oferta> toate = repo.repo_get_all();
	copy_if(toate.begin(), toate.end(), back_inserter(rez), [destinatie](const Oferta& o) { return o.get_destinatie() == destinatie; });
	return rez;
}

const Lista<Oferta> Service::service_filtrare_pret(int pret) {
	Lista<Oferta> rez;
	Lista<Oferta> toate = repo.repo_get_all();
	copy_if(toate.begin(), toate.end(), back_inserter(rez), [pret](const Oferta& o) noexcept { return o.get_pret() == pret; });
	return rez;
}


const Lista<Oferta> Service::sortare_denumire() {
	auto rez = repo.repo_get_all();
	sort(rez.begin(), rez.end(), [](const Oferta& o1, const Oferta& o2) {
		return o1.get_denumire() < o2.get_denumire();
		});
	return rez;
}

const Lista<Oferta> Service::sortare_tip() {
	auto rez = repo.repo_get_all();
	sort(rez.begin(), rez.end(), [](const Oferta& o1, const Oferta& o2) {
		return o1.get_tip() < o2.get_tip();
		});
	return rez;
}

const Lista<Oferta> Service::sortare_destinatie() {
	auto rez = repo.repo_get_all();
	sort(rez.begin(), rez.end(), [](const Oferta& o1, const Oferta& o2) {
		return o1.get_destinatie() < o2.get_destinatie();
		});
	return rez;
}

const Lista<Oferta> Service::sortare_pret() {
	auto rez = repo.repo_get_all();
	sort(rez.begin(), rez.end(), [](const Oferta& o1, const Oferta& o2) noexcept {
		return o1.get_pret() < o2.get_pret();
		});
	return rez;
}

void Service::service_golire_wishlist() noexcept {
	this->wishlist.golire_wishlist();
}

void Service::service_adauga_wishlist(const string denumire) {
	auto o = repo.repo_get_oferta(denumire);
	wishlist.adauga_wishlist(o);
}

void Service::service_exporta_wishlist_CSV(string nume_fisier) const {
	ofstream f(nume_fisier);
	auto wish = wishlist.get_wishlist();
	for (const auto& o : wish)
		f << o.get_denumire() << "," << o.get_destinatie() << "," << o.get_tip() << "," << o.get_pret() << std::endl;
	f.close();
}

void Service::service_genereaza_wishlist(int nr) {
	std::mt19937 mt{ std::random_device{}() };
	const std::uniform_int_distribution<> dist(1, 3000);
	for (int i = 0; i < nr; i++)
	{
		const string denumire = random_string(), tip = random_string(), destinatie = random_string();
		Oferta o = Oferta(denumire, destinatie, tip, dist(mt));
		wishlist.adauga_wishlist(o);
	}
}

string Service::random_string() const {
	std::mt19937 mt{ std::random_device{}() };
	const std::uniform_int_distribution<> dist(1, 15);
	const int lungime = dist(mt); //lungimea string-ului de generat
	const std::string CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	std::random_device random_device;
	std::mt19937 generator(random_device());
	const std::uniform_int_distribution<> distribution(0, CHARACTERS.size() - 1);
	std::string random_string;
	for (int i = 0; i < lungime; ++i)
		random_string += CHARACTERS.at(distribution(generator));
	return random_string;
}

void Service::service_exporta_wishlist_HTML(string nume_fisier) const {
	ofstream out(nume_fisier);
	out << "<html>" << endl;
	out << "<head><style>"<<endl;
	out<<"body{"<<endl<<"background-color: coral;}"<<endl<<"</style></head>"<<endl;
	out << "<body>"<<endl;// style = \"background - color:grey; \">" << endl;
	out << "<table border=\"1\" style=\"width:100 % \">" << endl;
	auto wish = wishlist.get_wishlist();
	out << "<tr><td>Denumire</td><td>Destinatie</td><td>Tip</td><td>Pret</td></tr>";
	for (const auto& o : wish)
	{
		out << "<tr>" << endl;
		out << "<td>" << o.get_denumire() << "</td>" << endl;
		out << "<td>" << o.get_destinatie() << "</td>" << endl;
		out << "<td>" << o.get_tip() << "</td>" << endl;
		out<< "<td>"<< o.get_pret() << "</td>" << endl;
		out << "</tr>" << endl;
	}
	out << "</table>"<<endl;
	out << "<img src=\"vacanta.jpg\" height=\"500\" width=\"500\" alt=""Poza cu vacanta"">";
	out << "<p> Vacanta placuta ! </p></body></html>";
	out.close();
}

int Service::service_wishlist_pret_total() {
	auto toate = this->service_get_wishlist();
	const int preturi = accumulate(toate.begin(), toate.end(), 0, [](int t,const Oferta& o) noexcept {return t+o.get_pret(); });
	return preturi;
}

void Service::undo() {
	if (undolist.empty())
		throw OfertaError("Nu se mai poate face undo!\n");
	undolist.back()->do_undo();
	undolist.pop_back();
}
