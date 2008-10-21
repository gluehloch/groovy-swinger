#!/bin/bash
DIRECTORY="/cygdrive/d/projects/gluehloch/groovy-example_TRUN"
FILE_PATTERN="*.java *.xml *.txt *.groovy *.apt"

counter=0
for pattern in $FILE_PATTERN
do
  counter=$((counter + 1))
  echo $counter ":" $pattern
  find $DIRECTORY -iname $pattern -exec convert.sh {} \;
done

# Java Quellen
#find /cygdrive/d/projects/gluehloch/swinger-commons-0.8.0_TRUNK/ -iname '*.java' -exec convert.sh {} \;

# Alle .XML Dateien (evt das Encoding im Header umstellen!)
#find /cygdrive/d/projects/gluehloch/swinger-commons-0.8.0_TRUNK/ -iname '*.xml' -exec convert.sh {} \;

# Alle .TXT Dateien
#find /cygdrive/d/projects/gluehloch/swinger-commons-0.8.0_TRUNK/ -iname '*.txt' -exec convert.sh {} \;

# Alle .GROOVY Dateien
#find /cygdrive/d/projects/gluehloch/swinger-commons-0.8.0_TRUNK/ -iname '*.groovy' -exec convert.sh {} \;

# Alle .APT Dateien
#find /cygdrive/d/projects/gluehloch/swinger-commons-0.8.0_TRUNK/ -iname '*.apt' -exec convert.sh {} \;

# .PROPERTIES Dateien ignorieren, da per JDK-Default ISO 8859-1 angenommen wird.

exit 0
