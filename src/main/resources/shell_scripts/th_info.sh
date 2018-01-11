#!/bin/sh
if [[ $# -ne 1 ]]
then
echo 'Usage: th_info.sh <PID>'
echo "-------------------------------------------------------------------"
jps
exit
fi


export jpid=$1
export top_cpu_count=20
export thread_info=${jpid}_thread_info_`date +%Y%m%d_%H%M%S`.txt

export tmp_dex_thread_id=tmp_dex_thread_id.tmp
export tmp_hex_thread_id=tmp_hex_thread_id.tmp

echo "-------------------------------------------------------------------" >> ${thread_info}
echo " CPU usage per thread in JVM ${jpid}   "`date +%Y/%m/%d_%H:%M:%S`    >> ${thread_info}
echo "-------------------------------------------------------------------" >> ${thread_info}
echo " ThreadID(HEX)  ThreadID(DEC)  Usage"                                >> ${thread_info}

ps -mo pcpu,lwp -p ${jpid}| awk '{if ($2 ~/[0-9]/) {print $2 " " $1""}}'  | sort -k 2 -r  | head -n ${top_cpu_count}  > ./${tmp_dex_thread_id}
cat ./${tmp_dex_thread_id} | awk '{print $1}' | xargs printf "%x\n"  > ./${tmp_hex_thread_id}
paste  ./${tmp_hex_thread_id}  ./${tmp_dex_thread_id}                      >> ./${thread_info}

echo "-------------------------------------------------------------------" >> ${thread_info}
echo " Full thread dump                                                  " >> ${thread_info}
echo "-------------------------------------------------------------------" >> ${thread_info}

jstack ${jpid} >> ${thread_info}

rm ./${tmp_dex_thread_id}  ./${tmp_hex_thread_id}

