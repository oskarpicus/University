#include "UIGUI.h"
#include <qboxlayout.h>
#include <qformlayout.h>
#include<qlabel.h>
#include<qmessagebox.h>
#include<qdebug.h>
#include<qabstractbutton.h>

void UIGUI::initializare() {

	this->buton_clear = new QPushButton{ "Clear" };
	this->buton_adauga = new QPushButton{ "Adauga" };
	this->buton_sterge = new QPushButton{ "Sterge" };
	this->buton_undo = new QPushButton{ "Undo" };
	this->buton_CosCRUD = new QPushButton{ "CosCRUD" };
	this->buton_CosReadOnly = new QPushButton{ "CosReadOnly" };
	this->buton_modifica = new QPushButton{ "Modifica" };
	this->buton_cauta = new QPushButton{ "Cauta" };
	this->buton_filtrare_destinatie = new QPushButton{ "Filtrare\nDestinatie" };
	this->buton_filtrare_pret = new QPushButton{ "Filtrare\nPret" };
	this->text_denumire = new QLineEdit;
	this->text_destinatie = new QLineEdit;
	this->text_tip = new QLineEdit;
	this->text_pret = new QLineEdit;
	this->lista = new QListWidget;
	this->buton_sortare = new QPushButton{ "Sortare" }; 
	this->radio_buton_sortare1 = new QRadioButton("Dupa denumire");
	this->radio_buton_sortare2 = new QRadioButton("Dupa destinatie");
	this->radio_buton_sortare3 = new QRadioButton("Dupa tip");
	this->radio_buton_sortare4 = new QRadioButton("Dupa pret");
	this->buton_trimite = new QPushButton("Trimite");
	this->fereastra_sortare = new QWidget;
	this->layout_vertical = new QVBoxLayout;
	this->buton_cos_adauga=new QPushButton("Adauga Cos");
	this->buton_cos_golire=new QPushButton("Golire Cos");
	this->buton_cos_generare=new QPushButton("Genereaza Cos");
	  
	auto ly_principal = new QHBoxLayout;
	this->setLayout(ly_principal);

	auto stanga = new QWidget;
	ly_principal->addWidget(stanga);
	auto ly_vertical_stg = new QVBoxLayout;
	stanga->setLayout(ly_vertical_stg);
	ly_vertical_stg->addWidget(this->lista);

	auto ly_oriz_stanga_jos = new QVBoxLayout;
	ly_vertical_stg->addLayout(ly_oriz_stanga_jos);
	ly_oriz_stanga_jos->addWidget(this->buton_undo);
	ly_oriz_stanga_jos->addWidget(this->buton_CosCRUD);
	ly_oriz_stanga_jos->addWidget(this->buton_CosReadOnly);

	auto dreapta = new QWidget;
	auto ly_dreapta = new QVBoxLayout;
	dreapta->setLayout(ly_dreapta);

	auto formular = new QFormLayout;

	formular->addRow(new QLabel{ "Denumire" }, text_denumire);
	formular->addRow(new QLabel{ "Destinatie" }, text_destinatie);
	formular->addRow(new QLabel{ "Tip" }, text_tip);
	formular->addRow(new QLabel{ "Pret" }, text_pret);
	ly_dreapta->addLayout(formular);

	auto ly_stg_0 = new QHBoxLayout;
	formular->addRow(ly_stg_0);
	ly_stg_0->addWidget(this->buton_clear);

	auto ly_grid = new QGridLayout;
	formular->addRow(ly_grid);
	ly_grid->addWidget(this->buton_adauga, 0, 0);
	ly_grid->addWidget(this->buton_sterge, 0, 1);
	ly_grid->addWidget(this->buton_modifica, 1, 0);
	ly_grid->addWidget(this->buton_cauta, 1, 1);
	ly_grid->addWidget(this->buton_filtrare_destinatie, 2, 0);
	ly_grid->addWidget(this->buton_filtrare_pret,2,1);
	ly_grid->addWidget(this->buton_sortare, 3, 0);
	ly_grid->addWidget(buton_cos_adauga, 3, 1);
	ly_grid->addWidget(buton_cos_golire, 4, 0);
	ly_grid->addWidget(buton_cos_generare, 4, 1);

	/*auto ly_stg_1 = new QHBoxLayout;
	formular->addRow(ly_stg_1);
	ly_stg_1->addWidget(this->buton_sortare);*/

	ly_principal->addWidget(dreapta);

	auto ly_sortare = new QVBoxLayout;
	fereastra_sortare->setLayout(ly_sortare);
	ly_sortare->addWidget(radio_buton_sortare1);
	ly_sortare->addWidget(radio_buton_sortare2);
	ly_sortare->addWidget(radio_buton_sortare3);
	ly_sortare->addWidget(radio_buton_sortare4);
	ly_sortare->addWidget(buton_trimite);

	ly_principal->addLayout(layout_vertical);

	
}

void UIGUI::incarca_lista(vector<Oferta> l) {
	this->lista->clear();
	for (const auto& oferta : l) {
		QListWidgetItem* item = new QListWidgetItem{ QString::fromStdString(oferta.get_denumire()) + " " + QString::fromStdString(oferta.get_destinatie()),this->lista };
		item->setData(Qt::UserRole,QString::number(oferta.get_pret()));
	}
}

void UIGUI::connect_signals_slots() {

	QObject::connect(buton_cos_adauga, &QPushButton::clicked, this, [this] {
		string denumire = this->text_denumire->text().toStdString();
		if(denumire=="")
			QMessageBox::warning(this, "Warning", "Trebuie sa dati denumirea ofertei de adaugat !");
		else {
			try {
				this->service.service_adauga_wishlist(denumire);
				//QMessageBox::information(this, "Adaugare", "Adaugarea a fost facuta\ncu succes !");
			}
			catch (RepoError& e) {
				QMessageBox::warning(this, "Warning Repo", QString::fromStdString(e.get_mesaj()));
			}
			catch (WishListError& e) {
				QMessageBox::warning(this, "Warning Wishlist", QString::fromStdString(e.get_mesaj()));
			}
		}
		});

	QObject::connect(buton_cos_golire, &QPushButton::clicked, this, [this] {
		this->service.service_golire_wishlist();
		});

	QObject::connect(buton_cos_generare, &QPushButton::clicked, this, [this] {
		int nr = this->text_pret->text().toInt();
		if (nr == 0)
			QMessageBox::warning(this, "Warning", "Trebuie sa dau un numar\npentru a genera oferte!");
		else {
			this->service.service_genereaza_wishlist(nr);
		}
		});

	QObject::connect(this->buton_CosReadOnly, &QPushButton::clicked, this, [this] {
		auto gui = new CosReadOnlyGui(this->service);
		gui->show();
		});

	QObject::connect(this->buton_CosCRUD, &QPushButton::clicked, [&] {
		auto gui = new CosCRUDGui(this->service);
		gui->show();
		});

	QObject::connect(this->buton_sortare, &QPushButton::clicked, [&] {
		fereastra_sortare->show();
		});

	QObject::connect(this->buton_trimite, &QPushButton::clicked, [&] {
		if (radio_buton_sortare1->isChecked()) {
			this->incarca_lista(this->service.sortare_denumire());
			this->adauga_butoane(this->service.sortare_denumire());
		}
		else if (radio_buton_sortare2->isChecked()) {
			this->incarca_lista(this->service.sortare_destinatie());
			this->adauga_butoane(this->service.sortare_destinatie());
		}
		else if (radio_buton_sortare3->isChecked()) {
			this->incarca_lista(this->service.sortare_tip());
			this->adauga_butoane(this->service.sortare_tip());
		}
		else if (radio_buton_sortare4->isChecked()) {
			this->incarca_lista(this->service.sortare_pret());
			this->adauga_butoane(this->service.sortare_pret());
		}
		else
			QMessageBox::warning(this, "Warning", "Nu ati ales nicio optiune");
		fereastra_sortare->close();
		});

	QObject::connect(this->buton_undo, &QPushButton::clicked, [&] {
		try {
			this->service.undo();
			this->incarca_lista(this->service.service_get_all());
			this->adauga_butoane(this->service.service_get_all());
		}
		catch (OfertaError& o) {
			QMessageBox::warning(this, "Warning", QString::fromStdString(o.get_mesaj()));
		}
		});

	QObject::connect(this->buton_adauga, &QPushButton::clicked,this,&UIGUI::ui_adauga);

	QObject::connect(this->buton_clear, &QPushButton::clicked, this, [&] {
		this->text_denumire->clear();
		this->text_destinatie->clear();
		this->text_tip->clear();
		this->text_pret->clear();
		this->incarca_lista(this->service.service_get_all());
		});

	QObject::connect(this->buton_sterge,&QPushButton::clicked,this,&UIGUI::ui_sterge);

	QObject::connect(this->lista, &QListWidget::itemSelectionChanged, this, [&] {

		if (this->lista->selectedItems().isEmpty())
		{
			//golesc campurile
			this->text_denumire->setText("");
			this->text_destinatie->setText("");
			this->text_pret->setText("");
			this->text_tip->setText("");
			return;
		}

		QListWidgetItem* item = this->lista->selectedItems().at(0);
		string text = item->text().toStdString();
		string denumire = "";
		for (const auto& i : text) {
			if (i == ' ') break;
			denumire += i;
		}
		Oferta o = this->service.service_get_oferta(denumire);
		this->text_denumire->setText(QString::fromStdString(o.get_denumire()));
		this->text_destinatie->setText(QString::fromStdString(o.get_destinatie()));
		this->text_tip->setText(QString::fromStdString(o.get_tip()));
		this->text_pret->setText(QString::number(o.get_pret()));
		});

	QObject::connect(this->buton_modifica, &QPushButton::clicked, this, &UIGUI::ui_modifica);

	QObject::connect(this->buton_cauta, &QPushButton::clicked, this, [&]{
		string denumire = this->text_denumire->text().toStdString();
		try {
			bool val = this->service.service_cauta(denumire, "a", "a", 12);
			if (val == true)
				QMessageBox::information(this, "Cauta Oferta", "Oferta cautata exista!");
			else
				QMessageBox::information(this, "Cauta Oferta", "Oferta cautata nu exista!");
		}
		catch(ValidError& v) {
			QMessageBox::warning(this, "Warning Validator", QString::fromStdString(v.get_msg()));
		}
		});

	QObject::connect(this->buton_filtrare_pret, &QPushButton::clicked, this, [&] {
		int pret = this->text_pret->text().toInt();
		qDebug() << pret;
		this->buton_clear->clicked();
		if (pret != 0) {
			QMessageBox::information(this, "Filtrare pret", "Ofertele cu pretul dat sunt\nevidentiate cu galben");
			for (int i = 0; i < this->lista->count(); i++) {
				auto item = this->lista->item(i);
				if (item->data(Qt::UserRole).toInt() == pret)
					item->setBackground(QBrush{ Qt::yellow });
			}
		}
		else
			QMessageBox::warning(this, "Warning", "Pret invalid!");
		});

	QObject::connect(this->buton_filtrare_destinatie, &QPushButton::clicked, this, [&] {
		string destinatie = this->text_destinatie->text().toStdString();
		this->buton_clear->clicked();
		if (destinatie == "")
			QMessageBox::warning(this, "Warning", "Destinatie invalida!");
		else {
			QMessageBox::information(this,"Filtrare destinatie","Ofertele cu destinatia data sunt\nevidentiate cu albastru");
			for (int i = 0; i < this->lista->count(); i++) {
				auto item = this->lista->item(i);
				string text = item->text().toStdString();
				if (text.find(" " + destinatie) != string::npos)
					item->setBackground(QBrush{ Qt::cyan });
			}
		}
		});
}

void UIGUI::ui_adauga() {
	try {
		this->service.service_adauga(text_denumire->text().toStdString(), text_destinatie->text().toStdString(), text_tip->text().toStdString(), stoi(text_pret->text().toStdString()));
		this->incarca_lista(service.service_get_all());
		this->adauga_butoane(this->service.service_get_all());
	}
	catch (ValidError& v) {
		QMessageBox::warning(this, "Warning Validator", QString::fromStdString(v.get_msg()));
	}
	catch (RepoError& r) {
		QMessageBox::warning(this, "Warning Repo", QString::fromStdString(r.get_mesaj()));
	}
	catch (OfertaError& o) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(o.get_mesaj()));
	}
	catch (invalid_argument) //eroarea aruncata de stoi
	{
		QMessageBox::warning(this,"Warning","Date invalide numeric!");
	}
}

void UIGUI::ui_sterge() {
	try {
		this->service.service_sterge(text_denumire->text().toStdString(), text_destinatie->text().toStdString(), text_tip->text().toStdString(), text_pret->text().toInt());
		this->incarca_lista(service.service_get_all());
		this->adauga_butoane(this->service.service_get_all());
	}
	catch (ValidError& v) {
		QMessageBox::warning(this, "Warning Validator", QString::fromStdString(v.get_msg()));
	}
	catch (RepoError& r) {
		QMessageBox::warning(this, "Warning Repo", QString::fromStdString(r.get_mesaj()));
	}
	catch (OfertaError& o) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(o.get_mesaj()));
	}
	this->adauga_butoane(this->service.service_get_all());
}

void UIGUI::ui_modifica() {
	if (this->lista->selectedItems().isEmpty())
	{
		QMessageBox::warning(this, "Warning", "Trebuie sa completati campurile!");
		//golesc campurile
		this->text_denumire->setText("");
		this->text_destinatie->setText("");
		this->text_pret->setText("");
		this->text_tip->setText("");
		return;
	}

	QListWidgetItem* item = this->lista->selectedItems().at(0);
	string text = item->text().toStdString();
	string denumire = "";
	for (const auto& i : text) {
		if (i == ' ') break;
		denumire += i;
	}
	Oferta o1 = this->service.service_get_oferta(denumire);

	//construim oferta cu datele actualizate
	string den2 = this->text_denumire->text().toStdString();
	string dest2 = this->text_destinatie->text().toStdString();
	string tip2 = this->text_tip->text().toStdString();
	int pret = this->text_pret->text().toInt();

	try {
		this->service.service_modifica(o1.get_denumire(), o1.get_destinatie(), o1.get_tip(), o1.get_pret(), den2, dest2, tip2, pret);
		this->incarca_lista(service.service_get_all());
	}
	catch (ValidError& v) {
		QMessageBox::warning(this, "Warning Validator", QString::fromStdString(v.get_msg()));
	}
	catch (RepoError& r) {
		QMessageBox::warning(this, "Warning Repo", QString::fromStdString(r.get_mesaj()));
	}
	catch (OfertaError& o) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(o.get_mesaj()));
	}
}

void UIGUI::adauga_butoane(vector<Oferta> l) {

	//sterg toate butoanele
	if (layout_vertical != NULL) {
		QLayoutItem* item;
		while ((item = layout_vertical->takeAt(0)) != NULL) {
			delete item->widget();
			delete item;
		}
	}

	//adaug
	for (auto& o : l) {
		auto buton = new QPushButton(QString::fromStdString(o.get_denumire()));
		layout_vertical->addWidget(buton);
		QObject::connect(buton, &QPushButton::clicked, this, [this,buton,o] {
			this->service.service_sterge(o.get_denumire(), o.get_destinatie(), o.get_tip(), o.get_pret());
			this->incarca_lista(this->service.service_get_all());
			delete buton;
			});
	}
}