# User Authentication

User authentication mechanisms implemented in Axibase Time Series Database specify the way users are granted access to protected application resources such as user interface views and API endpoints.

## Supported Authentication Mechanisms

### Form-based Authentication for User Interface

When an unauthenticated user requests access to a protected view in the user interface, the server redirects the user to a login page containing user name and password fields.

The user is granted access if the hashcode of the submitted password matches the stored hashcode for the specified user name.

### Basic Authentication for API

HTTP requests to an API URL are required to include the `Authorization` header with type `Basic`.

* Java Example:

```java
	URL url = new URL("http://10.102.0.6:8088/api/v1/series");
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("charset", "utf-8");

	String authString = userName + ":" + password;
	String authEncoded = DatatypeConverter.printBase64Binary(authString.getBytes());
	conn.setRequestProperty("Authorization", "Basic " + authEncoded);
```

* `curl` Example:

```bash
curl http://10.102.0.6:8088/api/v1/properties/query \
  --user {username}:{password} \
  --header "Content-Type: application/json" \
  --request POST \
  --data '[{"type":"disk","entity":"nurswgvml007","startDate":"2016-05-25T04:00:00Z","endDate":"now"}]'
```

If the `Authorization` header is missing, the HTTP client is prompted to provide user name and password. 

The API request is executed if the password hashcode matches the stored hashcode.

Once the user is authenticated, subsequent API requests can be executed without repetitive authorization.

## Password Requirements

* Password must contain at least **six** characters by default.

* The default minimum length can be adjusted in the `server.properties` file with the `user.password.min.length` setting.

* Passwords are case-sensitive.

* Password can contain the following characters:

    - Unicode character categorized as an alphanumeric character.
	
    - Special characters:

```
      ~!@#$%^&*_-+=`|\(){}[]:;”‘<>,.?/"'
```

## Built-in Account

-   When accessed for the first time after the installation, the database presents a web page for configuring the default administrator account. This built-in user account is assigned an `ADMIN` role which has `All Entities: Read / Write` permissions.

## Changing the Password

-   Users can modify their password by clicking on user icon in the top menu.

-   Users are not allowed to change their own role, group membership or entity permissions.
	
## Resetting Password

-   The database doesn't store user passwords in plain text, instead storing a hashcode of the password in order to protect user credentials. Therefore it is not possible to recover a lost password, only password change/reset operation is supported. 

-   To reset the password for a user account:

    - Open the `server.properties` file.
    - Add `user.password.reset.username={username}` and `user.password.reset.password={new-password}` settings and save the file.
	- Restart ATSD.
	- Remove the above settings from the `server.properties` file to prevent password resets on subsequent restarts.

## Anonymous API Access

-   To enable anonymous API access, set `api.anonymous.access.enabled=true` in the `server.properties` file.
