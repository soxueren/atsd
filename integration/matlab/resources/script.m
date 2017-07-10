driver = 'com.axibase.tsd.driver.jdbc.AtsdDriver';
url = 'jdbc:axibase:atsd:127.0.0.1:8443';
username = 'axibase';
password = 'axibase';
conn_atsd = database('', username, password, driver, url);
% sql query to get prices for 2017 year
sqlquery = 'SELECT datetime, tags.category, value FROM inflation.cpi.categories.price WHERE datetime BETWEEN "2013-01-01T00:00:00Z" AND "2017-01-01T00:00:00Z" ORDER BY 1, 2';
% get cursor from ATSD
curs = exec(conn_atsd, sqlquery);
% fetch data from cursor
res = fetch(curs);
% fetch prices resultset from data as cell array
prices_resultset = res.Data
% fetch datetime column
datetimes = prices_resultset(:,1)
% get every 10th record from datetimes to form list of years
datetimes = datetimes(1:10:length(datetimes))
% fetch third column from prices resultset (which contains value field)
% convert column to numeric array
prices = cell2mat(prices_resultset(:,3))
% sql query to get weights for 2017 year
sqlquery = 'SELECT tags.category, value FROM inflation.cpi.categories.weight WHERE datetime = "2017-01-01T00:00:00Z" ORDER BY 1';
% get cursor from ATSD
curs = exec(conn_atsd, sqlquery);
% fetch data from cursor
res = fetch(curs);
% fetch weights resultset from data as cell array
weights_resultset = res.Data;
% fetch second column from weights resultset (which contains value field)
% convert column to numeric array
weights = cell2mat(weights_resultset(:,2));
% repeat weights column for 2017 year 5 times to get 2013-2017 weights range
weights = repmat(weights, 5, 1)
% element wise multiplicate 2 columns
inflation_cpi_price = prices .* weights / 1000
% sum inflation prices for each year
inflation_cpi_composite_price = sum(reshape(inflation_cpi_price, 10, 5))
% form list of entities for result payload
entity = 'bls.gov';
entities = repmat(cellstr(entity), size(datetimes, 1), 1)
% append Entity, Datetime and Inflation columns
payload = [entities, datetimes, num2cell(inflation_cpi_composite_price)']
% colnames is a cell array which describes names and order of columns in payload
colnames = {'entity', 'datetime', 'value'};
% insert data into ATSD
insert(conn_atsd, 'inflation.cpi.composite.price', colnames, payload);