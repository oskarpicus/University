#pragma once

#include <string>
#include <vector>
#include "Number.h"

using namespace std;

class NumberReader {
public:
	Number* readNumber(string filename);
};