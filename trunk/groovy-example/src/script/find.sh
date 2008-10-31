#!/bin/bash
DIRECTORY="/cygdrive/d/Projects/sourceforge/gluehloch-sheeter_TRUNK"
FILE_PATTERN="*.java *.xml *.txt *.groovy *.apt"

counter=0
for pattern in $FILE_PATTERN
do
  counter=$((counter + 1))
  echo $counter ":" $pattern
  find $DIRECTORY -iname $pattern -exec ./convert.sh {} \;
done

# .PROPERTIES Dateien ignorieren, da per JDK-Default ISO 8859-1 angenommen wird.

exit 0
