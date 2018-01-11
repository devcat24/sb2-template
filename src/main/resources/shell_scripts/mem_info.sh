#!/bin/sh
if [ $# -ne 1 ]
then
	echo 'Usage: mem_info.sh <PID>'
	echo "-------------------------------------------------------------------"
	jps
	exit
fi

export jpid=$1
export dump_location=${jpid}_heapdump_`date +%Y%m%d_%H%M%S`.phd


echo "-------------------------------------------------------------------"
echo " Generating Heapdump                                               "
echo "-------------------------------------------------------------------"
jmap -dump:format=b,file=${dump_location} ${jpid}
