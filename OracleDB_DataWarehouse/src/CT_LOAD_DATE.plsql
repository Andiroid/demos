create or replace 
PROCEDURE PROC_CT_LOAD_DATE IS
  v_tabcnt number;
BEGIN
  SELECT count(*) into v_tabcnt
  FROM   ALL_TABLES
  WHERE  OWNER = 'LEITZMUELLER' and
         TABLE_NAME = 'LOAD_DATE';
         
  if v_tabcnt = 0 then
      EXECUTE IMMEDIATE
      'CREATE TABLE LEITZMUELLER.LOAD_DATE
			(
                            FILE_ID    INTEGER,
                            FILE_NAME  VARCHAR(40),
                            FILE_DATE  DATE,
                            LOAD_DATE  TIMESTAMP,
                            PRIMARY KEY (FILE_ID)
			)';
  end if;

  SELECT count(*) into v_tabcnt
  FROM   ALL_SEQUENCES
  WHERE  SEQUENCE_OWNER = 'LEITZMUELLER' and
         SEQUENCE_NAME = 'SEQ_FILE_ID';

  if v_tabcnt = 0 then
      EXECUTE IMMEDIATE
      'CREATE SEQUENCE SEQ_FILE_ID
       START WITH 1';
  end if;
END PROC_CT_LOAD_DATE;
/
exit;
