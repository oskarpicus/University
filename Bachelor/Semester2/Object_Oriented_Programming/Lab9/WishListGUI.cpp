#include "WishListGUI.h"
#include <stdlib.h>
#include <time.h>

void CosCRUDGui::initializare() {
	buton_golire = new QPushButton{ "Golire" };
	buton_generare=new QPushButton{"Generare"};
	buton_adauga = new QPushButton{ "Adauga" };
	buton_export = new QPushButton{ "Export" };
	label_nr_oferte=new QLabel{"Numar oferte: "+QString::number(nr_oferte)};
	label_total_pret = new QLabel{ "Total pret: " +QString::number(total_pret)};
	field = new QLineEdit;
	buton_csv = new QRadioButton{ "CSV" };
	buton_html = new QRadioButton{ "HTML" };
	lista = new QListWidget;

	auto ly_v = new QVBoxLayout;
	this->setLayout(ly_v);
	ly_v->addWidget(lista);

	auto w = new QWidget;
	auto ly = new QGridLayout;
	w->setLayout(ly);
	ly->addWidget(buton_golire, 0, 0);
	ly->addWidget(buton_adauga, 0, 1);
	ly->addWidget(buton_generare, 1, 0);
	ly->addWidget(buton_export, 1, 1);
	ly->addWidget(buton_csv, 1, 2);
	ly->addWidget(buton_html, 1, 3);
	ly->addWidget(label_nr_oferte, 2, 0);
	ly->addWidget(label_total_pret, 2, 1);
	
	ly_v->addWidget(w);
	ly_v->addWidget(field);

}

void CosCRUDGui::incarca_lista(vector<Oferta> v) {
	this->lista->clear();
	for (const auto& o : v) {
		auto text = o.get_denumire() + " " + o.get_destinatie() + " " + o.get_tip() + " " + to_string(o.get_pret());
		auto item = new QListWidgetItem(QString::fromStdString(text), lista);
	}
}

void CosCRUDGui::connect_signals_slots() {
	QObject::connect(buton_golire, &QPushButton::clicked, this, [this] {
		this->service.service_golire_wishlist();
		//actualizeaza_labels();
		//incarca_lista(service.service_get_wishlist());
		});

	QObject::connect(buton_generare, &QPushButton::clicked, this, [this] {
		int nr = this->field->text().toInt();
		if (nr == 0)
			QMessageBox::warning(this, "Warning", "Trebuie sa dau un numar\npentru a genera oferte!");
		else {
			this->service.service_genereaza_wishlist(nr);
			nr_oferte += nr;
		//	this->actualizeaza_labels();
			//incarca_lista(service.service_get_wishlist());
		}
		});

	QObject::connect(buton_adauga, &QPushButton::clicked, this, [this] {
		string denumire = this->field->text().toStdString();
		if (denumire == "")
			QMessageBox::warning(this, "Warning", "Trebuie sa dati denumirea ofertei de adaugat !");
		else {
			try {
				this->service.service_adauga_wishlist(denumire);
			//	actualizeaza_labels();
			//	incarca_lista(service.service_get_wishlist());
				QMessageBox::information(this, "Adaugare", "Adaugarea a fost facuta\ncu succes !");
			}
			catch (RepoError& e) {
				QMessageBox::warning(this, "Warning Repo", QString::fromStdString(e.get_mesaj()));
			}
			catch (WishListError& e) {
				QMessageBox::warning(this, "Warning Wishlist", QString::fromStdString(e.get_mesaj()));
			}
		}
		});

	QObject::connect(this->buton_export, &QPushButton::clicked, this, [this] {
		string nume = this->field->text().toStdString();
		if (nume == "")
			QMessageBox::warning(this, "Warning", "Nu ati dat nume de fisier\nin care sa se exporte!");
		else {
			if (this->buton_csv->isChecked())
				this->service.service_exporta_wishlist_CSV(nume);
			else if (this->buton_html->isChecked())
				this->service.service_exporta_wishlist_HTML(nume);
			if (this->buton_csv->isChecked() || this->buton_html->isChecked())
				QMessageBox::information(this, "Export", "Exportarea a fost facuta cu succes!");
			else
				QMessageBox::warning(this, "Warning", "Nu ati selectat nicio optiune!");
		}
		});
}

void CosCRUDGui::actualizeaza_labels() {
	label_nr_oferte->setText("Numar oferte: " + QString::number(this->service.service_get_wishlist_size()));
	label_total_pret->setText("Total pret: " + QString::number(this->service.service_wishlist_pret_total()));
}

