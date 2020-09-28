#!/usr/bin/env bash

#for FILE in `ls /ORCL/data/*.csv`
for FILE in /ORCL/data/*.csv
do
	export FILENAME=`basename $FILE`
	export FILEDATE=`echo $FILENAME | sed -e 's/[^0-9]//g'`
	#export FILEBASE=`echo $FILENAME | sed -e 's/\..*//'`
   FILEBASE=`basename $FILE .csv`

	sqlldr leitzmueller/oracle data=/ORCL/data/$FILENAME control=/ORCL/src/apps_leitzmueller.ldr log=/ORCL/log/$FILEBASE.log bad=/ORCL/log/$FILEBASE.bad errors=50
	sqlplus leitzmueller/oracle <<!
           DELETE FROM APPS
           WHERE  FILE_ID in
                  (
                     SELECT FILE_ID
                     FROM   LOAD_DATE
                     WHERE  FILE_NAME = '$FILENAME'
                  );
           COMMIT;
!
	sqlplus leitzmueller/oracle <<!
           DELETE FROM LOAD_DATE
           WHERE  FILE_NAME = '$FILENAME';
           COMMIT;
!
	sqlplus leitzmueller/oracle <<!
           INSERT INTO LOAD_DATE VALUES(SEQ_FILE_ID.nextval, '$FILENAME', to_date ('$FILEDATE', 'YYYYMMDD'), sysdate);
           COMMIT;
!

	export LINES_FILE=`grep "Lines" /ORCL/data/$FILENAME | sed -e 's/[^0-9]*//g'`
	export LINES_TABLE=`sqlplus leitzmueller/oracle <<! | grep '!!!' | sed -e 's/[^0-9]*//g'
	   SELECT '!!!' || count(*) as LINES_TABLE
	   FROM   APPS_TMP;
!
`
	if [ $LINES_FILE -gt $[$LINES_TABLE+50] -o $LINES_FILE -lt $[$LINES_TABLE-50] ]
	then
	   >&2 echo Wrong file $FILENAME $LINES_FILE lines in the file $LINES_TABLE int the table
	   exit 17
	fi
	echo $LINES_FILE $LINES_TABLE >> /ORCL/src/lines.txt


	sqlplus leitzmueller/oracle <<!
           INSERT INTO APPS
           SELECT *
           FROM   APPS_TMP
                  INNER JOIN
                  (
                     SELECT FILE_ID
                     FROM   LOAD_DATE
                     WHERE  FILE_NAME = '$FILENAME'
                  )
                  ON 1=1;
           DELETE FROM APPS_TMP;
           COMMIT;
!
done
