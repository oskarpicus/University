#!/bin/bash

flex lex.l
gcc lex.yy.c -std=c99
./a.out a.cpp
