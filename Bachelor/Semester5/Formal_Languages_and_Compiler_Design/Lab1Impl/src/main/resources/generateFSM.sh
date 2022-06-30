#!/bin/bash

output=$1

# generate the states

echo "{" > $output
echo '"states": [' >> $output
echo '{"symbol": "q0",' >> $output
echo '"type": "INITIAL"' >> $output
echo "}," >> $output

i=1
while [ $i -le 253 ]; do
	echo "{" >> $output
	
	line='"symbol": "'
	line=${line}'q'${i}'",'
	echo $line >> $output

	echo '"type": "FINAL"' >> $output
	echo "}," >> $output

	i=$((i+1))
done

echo "{" >> $output
echo '"symbol": "q254",' >> $output
echo '"type": "INTERMEDIARY"' >> $output
echo "}" >> $output

echo "]," >> $output

# generate the alphabet
letters=`echo {a..z}`
letters=${letters}" "`echo {A..Z}`
letters=${letters}" "`echo {0..9}`

alphabet="["
alphabetWithoutDigits="["

echo '"alphabet": ' >> $output
for el in $letters; do
	line='"'
	line=${line}${el}
	line=$line'",'
	if [ $el == "a" ]; then
		alphabet=${alphabet}'"'${el}'"'
		alphabetWithoutDigits=${alphabetWithoutDigits}'"'${el}'"'
	else
		alphabet=${alphabet}', "'${el}'"'
		if [ `echo $el | grep "[0-9]" -c` == "0" ]; then
			alphabetWithoutDigits=${alphabetWithoutDigits}', "'${el}'"'
		fi
	fi
done

alphabet=${alphabet}', "_"]'
alphabetWithoutDigits=${alphabetWithoutDigits}', "_"]'
echo $alphabet >> $output
echo "," >> $output

# generate the transitions
echo '"transitions": [' >> $output

# the first transition
echo "{" >> $output
echo '"from": { "symbol": "q0", "type": "INITIAL"},' >> $output
echo '"to": {' >> $output
echo '"symbol": "q1","type": "FINAL"},' >> $output

line='"symbols": '${alphabetWithoutDigits}'},'
echo $line >> $output

# generate the rest of the transitions for identifiers
i=2
prev=1
while [ $i -lt 251 ]; do
	line='{"from": {"symbol": "q'${prev}'","type": "FINAL"},'
	line=$line'"to": {"symbol": "q'${i}'","type": "FINAL"},'
	line=$line'"symbols": '${alphabet}'},' 
	echo $line >> $output
	prev=$i
	i=$((i+1))
done

# generate the transitions for the numerical constants

#digits="["
#nonZeroDigits="["
#for digit in `echo {0..9}`; do
#	if [ $digit == "9" ]; then
#		digits=${digits}' "'${digit}'"'
#		nonZeroDigits=${nonZeroDigits}' "'${digit}'"'
#	else
#		digits=${digits}' "'${digit}'",'
#		if [ $digit -ne 0 ]; then
#			nonZeroDigits=${nonZeroDigits}' "'${digit}'",'
#		fi
#	fi
#done
#
#digits=${digits}']'
#nonZeroDigits=${nonZeroDigits}']'
#
#line='{"from": {"symbol": "q0", "type": "INITIAL"},'
#line=$line'"to": {"symbol": "q251", "type": "FINAL"},'
#line=$line'"symbols": '${nonZeroDigits}'},'
#echo $line >> $output
#
#line='{"from": {"symbol": "q251", "type": "FINAL"},'
#line=$line'"to": {"symbol": "q251", "type": "FINAL"},'
#line=$line'"symbols": '${digits}'}'
#echo $line >> $output

echo "]}" >> $output
