#include "NumberReader.h"

#include <fstream>

Number* NumberReader::readNumber(string filename)
{
	ifstream in(filename);
	if (!in) {
		throw exception();
	}

	int nrDigits;
	in >> nrDigits;

	uint16_t* digits = new uint16_t[nrDigits];

	for (int i = nrDigits - 1; i >= 0; i--) {
		char digit;
		in >> digit;
		digits[i] = digit - '0';
	}

	in.close();

	return new Number(digits, nrDigits);
}