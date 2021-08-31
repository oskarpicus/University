#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Lab9.h"

class Lab9 : public QMainWindow
{
	Q_OBJECT

public:
	Lab9(QWidget *parent = Q_NULLPTR);

private:
	Ui::Lab9Class ui;
};
