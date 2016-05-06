# Series URL Query. CSV Format
CSV file is exported
## Request 
### URI
```
https://atsd_host:8443/api/v1/series/csv/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&columns=date,entity,metric,value
```
### curl
```css
curl https://atsd_host:8443/api/v1/series/csv/nurswgvml007/mpstat.cpu_busy?startDate=previous_hour&endDate=now&columns=date,entity,metric,value \
  -v -u {username}:{password} \
    -H "Content-Type: application/json" \
    -X GET
```
## Response
```
time,entity,metric,value
2015-05-12T14:00:00Z,nurswgvml007,mpstat.cpu_busy,7.0
2015-05-12T14:01:00Z,nurswgvml007,mpstat.cpu_busy,2.0
2015-05-12T14:02:00Z,nurswgvml007,mpstat.cpu_busy,5.0
2015-05-12T14:03:00Z,nurswgvml007,mpstat.cpu_busy,4.95
2015-05-12T14:04:00Z,nurswgvml007,mpstat.cpu_busy,0.0
```

<aside class="notice">
If endTime is not specified, endDate is used. If endDate is not specified an error is raised.
If startTime is not specified, startDate is used. If startDate is not specified, endDate is used minus interval. If no start can be established, an error is raised.
</aside>
