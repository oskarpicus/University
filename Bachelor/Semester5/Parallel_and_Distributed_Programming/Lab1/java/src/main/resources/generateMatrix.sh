#!/bin/bash

# retrieve the parameters

nrLinesPixelMatrix=$1
nrColumnsPixelMatrix=$2

nrLinesKernel=$3
nrColumnsKernel=$4

i=0

maxRandom=32767

# the dimensions of the pixel matrix
echo "$1 $2" > date.txt

# generate the pixel matrix
while [ $i -lt "$nrLinesPixelMatrix" ]; do
  j=1

  row="$(($RANDOM%255))"

  while [ $j -lt "$nrColumnsPixelMatrix" ]; do

    R=$(($RANDOM%255))
    row="$row $R"
    j=$((j+1))

  done

  echo "$row" >> date.txt
  i=$((i+1))
done

# generate the kernel matrix
i=0

echo "$nrLinesKernel $nrColumnsKernel" >> date.txt

while [ $i -lt "$nrLinesKernel" ]; do

  j=1

  # generate a floating point number. Shell does not support them by default
  row=`echo "print($RANDOM/$maxRandom)" | python`

  while [ $j -lt "$nrColumnsKernel" ]; do
    number=`echo "print($RANDOM/$maxRandom)" | python`
    row="$row $number"
    j=$((j+1))
  done

  echo "$row" >> date.txt

  i=$((i+1))

done
