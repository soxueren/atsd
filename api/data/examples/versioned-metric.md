# Versioned metric
## Request

```json
[
    {
        "entity": "e-vers",
        "metric": "m-vers",
        "data": [
            {
                "t": 1447834771665,
                "v": 513,
                "version": {
                    "status": "provisional",
                    "source": "t540p"
                }
            }
        ]
    }
]
```

|Name | Description|
|---|---|
|status | Status describing value change event.|
|source | Source that generated value change event.|

## Response
