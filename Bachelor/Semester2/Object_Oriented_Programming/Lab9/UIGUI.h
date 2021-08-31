#pragma once
#include<qwidget.h>
#include "service.h"
#include<QtWidgets/qpushbutton.h>
#include<qlineedit.h>
#include<qlistwidget.h>
#include<vector>
#include<qgridlayout>
#include<qradiobutton.h>
#include "domain.h"
#include <qboxlayout.h>
#include "WishListGUI.h"

class UIGUI: public QWidget {
private:
	Service& service;

	QPushButton* buton_clear;
	QPushButton* buton_adauga;
	QPushButton* buton_sterge;
	QPushButton* buton_undo;
	QPushButton* buton_CosCRUD;
	QPushButton* buton_CosReadOnly;
	QPushButton* buton_modifica;
	QPushButton* buton_cauta;
	QPushButton* buton_filtrare_pret;
	QPushButton* buton_filtrare_destinatie;
	QPushButton* buton_sortare;
	QPushButton* buton_trimite;
	QPushButton* buton_cos_adauga;
	QPushButton* buton_cos_golire;
	QPushButton* buton_cos_generare;
	QLineEdit* text_denumire;
	QLineEdit* text_destinatie;
	QLineEdit* text_tip;
	QLineEdit* text_pret;
	QListWidget* lista;
	QRadioButton* radio_buton_sortare1;
	QRadioButton* radio_buton_sortare2;
	QRadioButton* radio_buton_sortare3;
	QRadioButton* radio_buton_sortare4;
	QWidget* fereastra_sortare;
	QVBoxLayout* layout_vertical;


	void initializare();

	void incarca_lista(vector<Oferta> l);

	void connect_signals_slots();

	void adauga_butoane(vector<Oferta> l);

public:
	friend class CosCRUDGui;

	UIGUI(Service& s) : service{ s } {
		initializare();
		incarca_lista(service.service_get_all());
		connect_signals_slots();
		adauga_butoane(s.service_get_all());
	}

	void ui_adauga();

	void ui_sterge();

	void ui_modifica();


};