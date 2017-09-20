#!/bin/bash

CONTAINER_NAME=$1
ATSD_HOST=localhost
ATSD_PORT=8443
USR=axibase
PWD=axibase
DATA_FILE=IBM_adjusted.txt

divider="\n============\n"

if [ ! -f $DATA_FILE ]; then
  echo "Data file not found $DATA_FILE"
  exit 1
fi

if ! docker ps --format '{{.Names}}' | grep -q "$CONTAINER_NAME"; then
  echo "Container not found $CONTAINER_NAME"
  exit 1 
fi

echo -e $divider

echo "Connecting to https://$ATSD_HOST:$ATSD_PORT as '$USR'. Container: ${CONTAINER_NAME}"

echo -e $divider

# import parser
csv_parser_response=$( curl -s -u $USR:$PWD --insecure -F "file=@stock-parser.xml" -F "overwrite=on" -F "send=Import" "https://$ATSD_HOST:$ATSD_PORT/csv/configs/import" )

if [[ $csv_parser_response == *"Import executed successfully"* ]]; then
  echo "CSV parser imported"
else
  echo "CSV parser import error"
  echo $csv_parser_response
  exit 1
fi

echo -e $divider

echo "Uploading data file $DATA_FILE"
echo ""
# upload data file in wait mode
curl -u $USR:$PWD --insecure -X POST -T $DATA_FILE "https://$ATSD_HOST:$ATSD_PORT/api/v1/csv?config=stock-parser&wait=true"

echo ""
echo "Data file uploaded $DATA_FILE"

echo -e $divider

echo "Initiating data compaction"

# initiate compaction in wait mode
curl -s -u $USR:$PWD --insecure --data "start_compaction=Start+ATSD+compaction&wait=true" "https://$ATSD_HOST:$ATSD_PORT/admin/compaction"

for i in {1..30}; do
  compaction_status_resp=$( curl -s -u $USR:$PWD --insecure "https://$ATSD_HOST:$ATSD_PORT/admin/compaction" )
	if [[ $compaction_status_resp == *"Compaction ended"* ]]; then
	  echo "Compaction task complete."
	  break
	else
	  echo "Compaction task in progress..."
	fi
  sleep 10
done

echo -e $divider

# fetch table 'd' size
#table_size_mb=$( \
#curl -s -u $USR:$PWD --insecure "https://$ATSD_HOST:$ATSD_PORT/admin/database-tables" \
#  | grep "/portals/ser.*table=d"| sed 's/<a href.*">//g' | sed "s/<\/a>//g" | sed "s/[^0-9]*//g" \
#) ; echo "Current data table size reported by the database, in megabytes: $table_size_mb"

#echo -e $divider

# ATSD on AWS EMR S3: aws s3 ls --summarize --recursive s3://atsd | grep -v "/archive/" | grep "atsd_d/"

disk_size_bytes=$( docker exec -it $CONTAINER_NAME /opt/atsd/hadoop/bin/hdfs dfs -du -s /hbase/data/default/atsd_d  | awk '{print $1;}' )

echo "Data size on disk, in bytes: $disk_size_bytes"

echo -e $divider

# calculate row count in the data file 
data_file_line_count=$(cat $DATA_FILE | wc -l | sed "s/[^0-9]*//g")

echo "Line count in the input file: $data_file_line_count"
echo "'time:value' samples in the input file: $(($data_file_line_count*5))"

echo -e $divider

row_count_db=$( curl -s -u $USR:$PWD --insecure --request POST --data 'q=SELECT COUNT(value) FROM stock.open' "https://$ATSD_HOST:$ATSD_PORT/api/sql" | sed -e "1d" | sed "s/[^0-9]*//g")

echo "Row count in the database: $row_count_db"
echo "'time:value' samples in the database: $(($row_count_db*5))"

echo -e $divider

disk_per_sample=$(echo "$disk_size_bytes/($row_count_db*5)" | bc -l)
disk_per_row=$(echo "$disk_size_bytes/($row_count_db)" | bc -l)

echo "Disk used, bytes per sample: $disk_per_sample"
echo "Disk used, bytes per row:    $disk_per_row"

#echo ""
#echo "Based on database-repored size:"
#echo "$table_size_mb*1024*1024/($row_count_db*5)" | bc -l

echo -e $divider



