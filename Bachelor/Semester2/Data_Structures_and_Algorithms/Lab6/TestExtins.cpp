#include <assert.h>
#include "Matrice.h"
#include "TestExtins.h"
#include <iostream>
#include <exception>

using namespace std;


void testCreeaza() {
	Matrice m(10, 10);
	assert(m.nrLinii() == 10);
	assert(m.nrColoane() == 10);
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			assert(m.element(i, j) == NULL_TELEMENT);
}

void testAdauga() {
	Matrice m(10, 10);
	for (int j = 0; j < m.nrColoane(); j++)
		m.modifica(4, j, 3);
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			if (i == 4)
				assert(m.element(i, j) == 3);
			else
				assert(m.element(i, j) == NULL_TELEMENT);
}

void testQuantity() {//scopul e sa adaugam multe date
	Matrice m(300, 300);
	for (int i = m.nrLinii() / 2; i < m.nrLinii(); i++) {
		for (int j = 0; j < m.nrColoane() / 2; j++)
		{
			int v1 = j;
			int v2 = m.nrColoane() - v1 - 1;
			if (i % 2 == 0 && v1 % 2 == 0)
				m.modifica(i, v1, i * v1);
			else
				if (v1 % 3 == 0)
					m.modifica(i, v1, i + v1);
			if (i % 2 == 0 && v2 % 2 == 0)
				m.modifica(i, v2, i * v2);
			else
				if (v2 % 3 == 0)
					m.modifica(i, v2, i + v2);
		}
	}
	for (int i = 0; i <= m.nrLinii() / 2; i++) {
		for (int j = 0; j < m.nrColoane() / 2; j++)
		{
			int v1 = j;
			int v2 = m.nrColoane() - v1 - 1;
			if (i % 2 == 0 && v1 % 2 == 0)
				m.modifica(i, v1, i * v1);
			else
				if (v1 % 3 == 0)
					m.modifica(i, v1, i + v1);
			if (i % 2 == 0 && v2 % 2 == 0)
				m.modifica(i, v2, i * v2);
			else
				if (v2 % 3 == 0)
					m.modifica(i, v2, i + v2);
		}
	}
	for (int i = 0; i < m.nrLinii(); i++)
		for (int j = 0; j < m.nrColoane(); j++)
			if (i % 2 == 0 && j % 2 == 0)
				assert(m.element(i, j) == i * j);
			else
				if (j % 3 == 0)
					assert(m.element(i, j) == i + j);
				else assert(m.element(i, j) == 0);
}

void testExceptii() {
	Matrice m(10, 10);
	try {
		m.element(-1, -1);
	}
	catch (exception&) {
		assert(true); //ar trebui sa arunce exceptie
	}
	try {
		m.modifica(12, 0, 1);
	}
	catch (exception&) {
		assert(true); //ar trebui sa arunce exceptie
	}
	try {
		assert(m.nrLinii());
	}
	catch (exception&) {
		assert(false); //nu ar trebui sa arunce exceptie
	}
}

void testnou() {
	Matrice m(5, 5);
	assert(m.sumaDeasupraDiagonalaSecundara() == 0);
	m.modifica(0, 0, -34);
	assert(m.sumaDeasupraDiagonalaSecundara() == -34);
	//modificam elemente care nu sunt deasupra diag secundare
	m.modifica(0, 4, 87);
	m.modifica(4, 4, 47);
	assert(m.sumaDeasupraDiagonalaSecundara() == -34);
	m.modifica(1, 2, -5);
	assert(m.sumaDeasupraDiagonalaSecundara() == -39);
	m.modifica(0, 0, 9); //modificam un element deja modificat
	assert(m.sumaDeasupraDiagonalaSecundara() == 4);
	//modificam pe diagonala secundara
	m.modifica(3, 1, 100);
	assert(m.sumaDeasupraDiagonalaSecundara() == 4);

	m.modifica(0, 0, 0);
	assert(m.sumaDeasupraDiagonalaSecundara() == -5);
	m.modifica(1, 2, 0);
	assert(m.sumaDeasupraDiagonalaSecundara() == 0);
	m.modifica(3, 1, 0);
	m.modifica(0, 1, 4);
	assert(m.sumaDeasupraDiagonalaSecundara() == 4);
	m.modifica(0, 1, 0);
	assert(m.sumaDeasupraDiagonalaSecundara() == 0);
	m.modifica(0, 4, 0);
	m.modifica(4, 4, 0);
	


	Matrice m2(3, 3);
	assert(m2.sumaDeasupraDiagonalaSecundara() == 0);
	for (int i = 0; i < 3; i++)
		for (int j = 0; j < 3; j++)
			m2.modifica(i, j, 0);
	assert(m2.sumaDeasupraDiagonalaSecundara() == 0);
	for (int i = 0; i < 3; i++)
		for (int j = 0; j < 3; j++)
			m2.modifica(i, j, i+j);
	for (int i = 0; i < 3; i++)
		for (int j = 0; j < 3; j++)
			m2.modifica(i ,j, 0);
	assert(m2.sumaDeasupraDiagonalaSecundara() == 0);

	Matrice m3(20, 20);
	for(int i=0;i<20;i++)
		for (int j = 0; j < 20; j++) {
			assert(m3.modifica(i, j, (i + j) * (i + j))==NULL_TELEMENT);
			assert(m3.element(i, j) == (i + j) * (i + j));
		}
	for (int i = 0; i < 20; i++)
		for (int j = 0; j < 20; j++) {
			assert(m3.modifica(i, j, 0) == (i + j) * (i + j));
			assert(m3.element(i, j) == 0);
		}
}


void testAllExtins() {
	testCreeaza();
	testAdauga();
	testQuantity();
	testExceptii();
	testnou();
}