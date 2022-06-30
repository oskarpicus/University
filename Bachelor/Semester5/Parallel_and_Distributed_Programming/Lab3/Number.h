#pragma once
#include <cstdint>

class Number {
private:
	uint16_t* digits;
	int length;
	int carry;
public:
	Number(uint16_t* digits, int length) {
		this->digits = digits;
		this->length = length;
	}

	uint16_t* getDigits();

	void setDigits(uint16_t* digits);

	int getLength();

	void setLength(int length);

	void decrementLength();

	void setCarry(int carry);

	int getCarry();

	~Number() {
		delete[] digits;
	}
};