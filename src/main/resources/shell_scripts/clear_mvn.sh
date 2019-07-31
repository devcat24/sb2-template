#!/bin/bash
export cpath=`pwd`
export mvn_target_list=`find ./ -name "target" -type d  |  sed -e 's#/target.*##' |  sed -e 's#^./##g' | sed -e :a -e '$!N; s/\n/ | /; ta'`

if [[ "$mvn_target_list" -eq 0 ]]; then
    echo "No 'maven target' directory"
    exit 0
fi

find ./ -name "target" -type d  |  sed -e 's#/target.*##' |  sed -e 's#^./##g' | sed -e 's#^#'"${cpath}/"'#' | sed -e 's/^/cd /g' | sed -e 's/$/; mvn clean ; sleep 2 ; clear ;/g' > __cleanup_mvn_projects.sh
chmod u+x ${cpath}/__cleanup_mvn_projects.sh
clear ; echo 'Clean-up maven projects [ '${mvn_target_list}' ]'; sleep 3
${cpath}/__cleanup_mvn_projects.sh
rm ${cpath}/__cleanup_mvn_projects.sh
clear ; 
echo "Finished 'mvn clean' for [ "${mvn_target_list}" ]"
