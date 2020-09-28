create or replace 
PROCEDURE PROC_CT_APPS_TMP IS
  v_tabcnt number;
BEGIN
  SELECT count(*) into v_tabcnt
  FROM   ALL_TABLES
  WHERE  OWNER = 'LEITZMUELLER' and
         TABLE_NAME = 'APPS_TMP';
         
  if v_tabcnt = 0 then
      EXECUTE IMMEDIATE
      'CREATE TABLE LEITZMUELLER.APPS_TMP
			(
                PURCHASE_DATE       DATE,
                PURCHASE_HOUR       INTEGER,
                REGION_ID           INTEGER,
                TYPE_ID             INTEGER,
                APP_ID              INTEGER,
                PREMIUM_APP         INTEGER,
                BASIC_APP           INTEGER,
                CANCELED_PURCHASES  INTEGER,
                REVENUE             NUMBER (12,2)
			)';
  end if;
END PROC_CT_APPS_TMP;
/
exit;
