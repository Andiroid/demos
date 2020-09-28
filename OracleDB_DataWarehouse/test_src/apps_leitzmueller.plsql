create or replace
PROCEDURE PROC_CT_APPS IS
  v_tabcnt number;
BEGIN
  SELECT count(*) into v_tabcnt
  FROM   ALL_TABLES
  WHERE  OWNER = '&1' and
         TABLE_NAME = 'APPS';

  if v_tabcnt = 0 then
      EXECUTE IMMEDIATE
            'CREATE TABLE &1..APPS
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
END PROC_CT_APPS;
/
exit;
