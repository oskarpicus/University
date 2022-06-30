#include "NumbersAddition.h"

Number* NumbersAddition::add(Number* number1, Number* number2)
{
	Number* largest = number1->getLength() > number2->getLength() ? number1 : number2;

	uint16_t* result = new uint16_t[largest->getLength() + 1];  // extra 1 for the last carry

	int i = 0;
	int j = 0;
	int counter = 0, carry = 0;

	while (i < number1->getLength() && j < number2->getLength()) {
		int sum = number1->getDigits()[i] + number2->getDigits()[j] + carry;
		result[counter] = (uint16_t)(sum % 10);
		carry = sum / 10;
		counter++;
		i++;
		j++;
	}

	while (i < number1->getLength()) {
		int sum = number1->getDigits()[i] + carry;
		result[counter] = (uint16_t)(sum % 10);
		carry = sum / 10;
		i++;
		counter++;
	}

	while (j < number2->getLength()) {
		int sum = number2->getDigits()[j] + carry;
		result[counter] = (uint16_t)(sum % 10);
		carry = sum / 10;
		j++;
		counter++;
	}

	if (carry > 0) {
		result[counter++] = (uint16_t)carry;
	}

	Number* nr = new Number(result, counter);
	nr->setCarry(carry);

	return nr;
}