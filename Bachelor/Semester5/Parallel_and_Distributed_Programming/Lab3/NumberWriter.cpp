#include <fstream>
#include "NumberWriter.h"

void NumberWriter::write(Number* number, string destination)
{
	ofstream out(destination);
	if (!out) {
		throw exception();
	}

	// skip the trailing zeros
	int i = number->getLength() - 1;
	while (number->getDigits()[i] == 0) {
		i--;
	}

	for (; i >= 0; i--) {
		out << number->getDigits()[i];
	}

	out << endl;

	out.close();
}