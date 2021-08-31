#pragma once
#include<qwidget.h>
#include<qpushbutton.h>
#include<qlabel.h>
#include"service.h"
#include<qlineedit.h>
#include <qlayout.h>
#include<qmessagebox.h>
#include<qradiobutton.h>
#include <qpainter.h>
#include <qdebug>
#include <qlistwidget.h>
#include <stdlib.h>
#include <time.h>
#define MODULO 300

class CosCRUDGui:public QWidget, public Observer {
private:
	QPushButton* buton_golire;
	QPushButton* buton_generare;
	QPushButton* buton_adauga;
	QPushButton* buton_export;
	QRadioButton* buton_csv;
	QRadioButton* buton_html;
	QListWidget* lista;
	QLabel* label_nr_oferte;
	QLabel* label_total_pret;
	int nr_oferte=0;
	int total_pret = 0;
	QLineEdit* field;

	Service& service;

	void actualizeaza_labels();
	void initializare();
	void connect_signals_slots();
	void incarca_lista(vector<Oferta> v);
public:
	CosCRUDGui(Service& s) :service{ s } {
		initializare();
		connect_signals_slots();
		incarca_lista(s.service_get_wishlist());
		s.addObserver(this);
	}

	void update() override {
		qDebug() << "update";
		actualizeaza_labels();
		incarca_lista(service.service_get_wishlist());
	}

	~CosCRUDGui() {
		service.removeObserver(this);
	}
};


class CosReadOnlyGui : public QWidget, public Observer {
private:
	Service& service;
public:
	CosReadOnlyGui(Service& s) :service{ s } {
		s.addObserver(this);
	}

	void update() override {
		repaint();
	}

	void paintEvent(QPaintEvent* ev) override {
		QPainter p{ this };
		//time_t t;
		//srand((unsigned)time(&t));
		auto size = this->service.service_get_wishlist_size();
		int x = 0, y = 0;
		/*for (int i = 0; i < size; i++)
		{
			/*QRectF rect = QRectF(i * 100, 0, 100, 100);
			QPainterPath path;
			path.moveTo(rect.left() + (rect.width() / 2), rect.top());
			path.lineTo(rect.bottomLeft());
			path.lineTo(rect.bottomRight());
			path.lineTo((rect.left() + (rect.width() / 2)), rect.top());
			p.fillPath(path, QBrush(QColor("pink")));
			p.drawLine(i * 10, i * 10, height(), width());
		}*/
		for (int i = 0; i < size; i++) {
			int x = abs(rand()), y = abs(rand());
			qDebug() << x << " " << y;
			if (i < size / 3)
				p.drawLine(x%MODULO, y% MODULO, height(), width());
			else if (i < size / 2)
				p.drawRect(x% MODULO, y% MODULO, 20, 60);
			else
				p.drawEllipse(x% MODULO, y% MODULO, 90, 40);
		}
	}

	~CosReadOnlyGui() {
		service.removeObserver(this);
	}
};