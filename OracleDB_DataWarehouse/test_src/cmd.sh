# load data
sqlldr leitzmueller/oracle data=/ORCL/data/MOCK_DATA_APPS.csv control=/ORCL/test_src/apps_leitzmueller.ldr log=/ORCL/log/apps_$(date +"%m_%d_%Y").log bad=/ORCL/log/apps_$(date +"%m_%d_%Y").bad errors=20