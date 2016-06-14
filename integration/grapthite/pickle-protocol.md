# Pickle Protocol

Pickle is a binary protocol for serializing and de-serializing Python objects. > Pickling is the process where a Python object hierarchy is converted into a byte stream, and > unpickling is the reverse (a byte stream is converted back into an object hierarchy).

[Learn more about Pickle protocol in Graphite.](resources/feeding-carbon.html?highlight=pickle#the-pickle-protocol)

Axibase Time Series Database supports Pickle protocol when ingesting messaged produced by Carbon daemons.

Pickle Format:

```
[(metric-1, (timestamp-1, value-1)), (metric-1, (timestamp-2, value-2)), ...]
```

The TCP port used by ATSD to receive data sent in pickle protocol from carbon-relays is configured in ATSD’s `server.properties` file, the default TCP port is 8084.

The maximum message length is 64 kb.

