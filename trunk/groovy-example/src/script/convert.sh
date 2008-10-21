#!/usr/bin/sh
temp="${1}_"
echo "Converting file: ${1}"
iconv -f Cp1252 -t UTF-8 $1 > $temp
mv $temp $1
