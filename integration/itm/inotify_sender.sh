#!/bin/bash
scriptDir="`dirname $(readlink -f $0)`"
url="$1"
logPostfix="`date +%s`"
newcsvLog="$scriptDir/../logs/newcsv_${logPostfix}.log"
sendcsvLog="$scriptDir/../logs/send_${logPostfix}.log"
cleanLog="$scriptDir/../logs/cleaner_${logPostfix}.log"
csvDir="$scriptDir/../csv"
tmpDir="$scriptDir/../tmp"
if [ "$url" = "" ]; then
	url="http://10.102.0.6:8088"
fi
touch $newcsvLog

function clean_up {
    ps -ef | grep "inotifywait -qdm --format %f -e moved_to -o $newcsvLog $csvDir" | awk '{print $2}' | xargs kill -9
    ps -ef | grep "$0" | awk '{print $2}' | xargs kill -9
}

trap clean_up SIGINT SIGTERM

function cleaner {
    while :
    do
        find $csvDir/ -type f -mmin +3 -exec basename {} \; | while read csvName 
        do
    	    rm -f $csvDir/$csvName
    	    echo "rm as old: $csvDir/$csvName >> $cleanLog"
        done
    
        find $csvDir/ -type f -mmin +1 -mmin -2 -exec basename {} \; | while read csvName
        do
    	    send $csvName cleaner &
        done
        sleep 65
    done 
}

function send {
    csvName="$1"
    byCleaner="$2"
    tmpLog=$tmpDir/$csvName
    touch $tmpLog
    ok="awaiting response... 200"
    error="awaiting response... 500"
    type="`echo $csvName | tr "_" "\n" | head -n1`"
    
    if [[ -n $(find $csvDir/$csvName -mmin -1 2>/dev/null) ]] || [ "$byCleaner" != "" ]; then
        wget -t 1 -T 10 --header="Content-type:text/csv" -O - --post-file="$csvDir/$csvName" "$url/csv?config=$type&processEvents=false&nowait=true" 2>$tmpLog
        ( grep -q "$ok" $tmpLog && echo `date`: ok $csvName >> $sendcsvLog && rm -f $csvDir/$csvName ) || ( grep -q "$error" $tmpLog && echo `date`: error $csvName >> $sendcsvLog && cat $tmpLog >> $sendcsvLog ) ||
        ( echo `date`: unknown $csvName >> $sendcsvLog && cat $tmpLog >> $sendcsvLog )
        
        echo "--------------------" >> $sendcsvLog
    fi
    rm -rf $tmpLog
}

rm -f $csvDir/*

cleaner & 

inotifywait -qdm --format %f -e moved_to -o $newcsvLog $csvDir
tail -f $newcsvLog | while read csvName
do
	send $csvName &
done

