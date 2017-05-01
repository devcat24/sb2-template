#!/bin/sh

export cuser=`whoami`;

if [ "${cuser}" != "root" ]; then
        echo "Run as root user";
	exit 0;
fi

if [ "$#" -ne 1 ]; then
        echo "Usage: pg_bak.sh [backup/restore]";
        exit 0;
fi

echo "1. Setup runtime variable"
export postgres_db_user=tomcat
export os_user=postgres
export db_name=mydb
#export backup_file=/opt/dev/workspace/tmp_work/pg_`hostname -s`-`date +%F`
export backup_file=/opt/dev/workspace/tmp_work/pg_backup
echo "  "

if [ "$1" = "backup" ]; then
	echo "2. Create backup dump" ;
	su -c "pg_dump -U ${postgres_db_user} -Fc ${db_name} > ${backup_file}" ${os_user} ;
fi

if [ "$1" = "restore" ]; then
	echo "2. Restore from dump" ;
	su -c "dropdb ${db_name}" ${os_user} ;
	su -c "createdb -O ${postgres_db_user} -E UTF8 ${db_name}" ${os_user} ;
	su -c "pg_restore -U ${postgres_db_user} -d ${db_name} ${backup_file}" ${os_user} ;
fi

exit 0;
