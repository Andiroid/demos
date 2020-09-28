#!/usr/bin/env bash

sqlplus leitzmueller/oracle @/ORCL/src/CT_LOAD_DATE.plsql
sqlplus leitzmueller/oracle @/ORCL/src/CT_APPS_TMP.plsql
sqlplus leitzmueller/oracle @/ORCL/src/CT_APPS.plsql
sqlplus leitzmueller/oracle @/ORCL/src/CT_LOAD_DATE.sql
sqlplus leitzmueller/oracle @/ORCL/src/CT_APPS_TMP.sql
sqlplus leitzmueller/oracle @/ORCL/src/CT_APPS.sql
