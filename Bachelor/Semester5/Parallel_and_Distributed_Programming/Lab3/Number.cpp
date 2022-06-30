#include <cstdint>
#include "Number.h"
#include <iostream>

uint16_t* Number::getDigits()
{
	return this->digits;
}

int Number::getLength()
{
	return this->length;
}

void Number::setCarry(int carry) {
	this->carry = carry;
}

int Number::getCarry() {
	return carry;
}

void Number::decrementLength() {
	this->length--;
}

void Number::setDigits(uint16_t* digits) {
	this->digits = digits;
}

void Number::setLength(int length) {
	this->length = length;
}
