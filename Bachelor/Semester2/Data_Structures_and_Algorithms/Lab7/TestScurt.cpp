#include <assert.h>

#include "DO.h"
#include "Iterator.h"

#include <exception>
using namespace std;

bool relatie1(TCheie cheie1, TCheie cheie2) {
    if (cheie1 <= cheie2) {
        return true;
    }
    else {
        return false;
    }
}

void testAll() {
    DO dictOrd = DO(relatie1);
    assert(dictOrd.dim() == 0);
    assert(dictOrd.vid());
    dictOrd.adauga(1, 2);
    assert(dictOrd.dim() == 1);
    assert(!dictOrd.vid());
    assert(dictOrd.cauta(1) != NULL_TVALOARE);
    TValoare v = dictOrd.adauga(1, 3);
    assert(v == 2);
    assert(dictOrd.cauta(1) == 3);
    { Iterator it = dictOrd.iterator();
    it.prim();
    while (it.valid()) {
        TElem e = it.element();
        assert(e.second != NULL_TVALOARE);
        it.urmator();
    }}
    assert(dictOrd.sterge(1) == 3);
    assert(dictOrd.vid());

    //
    dictOrd.adauga(4, 5);
    dictOrd.adauga(9,1);
    dictOrd.adauga(10, 4);
    assert(dictOrd.dim() == 3);
    assert(dictOrd.sterge(9) == 1);
    assert(dictOrd.dim() == 2);
    assert(dictOrd.sterge(9) == NULL_TVALOARE);
    assert(dictOrd.dim() == 2);
    assert(dictOrd.sterge(10) == 4);
    assert(dictOrd.dim() == 1);
    assert(dictOrd.sterge(4) == 5);
    assert(dictOrd.dim() == 0 && dictOrd.vid());

    dictOrd.adauga(10, 4);
    dictOrd.adauga(2, 4);
    dictOrd.adauga(6, 4);
    dictOrd.adauga(1, 6);
    dictOrd.adauga(8, 100);
    auto iter = dictOrd.iterator();
    iter.prim();
    assert(iter.element() == std::make_pair(1,6));
    iter.urmator();
    assert(iter.element() == std::make_pair(2, 4));
    iter.urmator();
    assert(iter.element() == std::make_pair(6, 4)); 
    iter.urmator();
    assert(iter.element() == std::make_pair(8, 100));
    iter.urmator();
    assert(iter.element() == std::make_pair(10, 4));
}
