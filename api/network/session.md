## Command: `session`

Session command authenticates the client with the database for the duration of connection.

```
session a:API-NxO2jUBBoD21wRqV
```

| **Field** | **Required** |
|---|---|
| a         | yes          |

## Command: `exit`

> Request

```
exit
```
> Response

```
Goodbye
```

The server responds `Goodbye` and closes the connection.

## Command: `time`

> Request

```
time
```

> Response

```
1426260311663
```

The server responds with current server time in milliseconds.
