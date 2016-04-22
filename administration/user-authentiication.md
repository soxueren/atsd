# User Authentication


User authentication mechanisms implemented in Axibase Time Series
Database specify the way users are granted access to protected
application resources such as UI views and API URLs. The database
supports the following authentication mechanisms:

## Form-based Authentication for UI views:

When an unauthenticated user requests access to a protected view, the
server redirects the user to a login page containing username and
password input fields. If the hashcode for the submitted password
matches the stored hashcode for the specified username, the user is
granted access.

## Basic Authentication for API URLs:

HTTP requests to an API URL are required to include ‘Authorization’
header, type Basic.\
 If the header is missing, the HTTP client is prompted to provide
username and password. The API request is executed if the password
hashcode matches the stored value.

## Password Requirements

-   Passwords are case-sensitive.
-   Password must contain at least 6 characters. The default minimum
    length can be adjusted in server.properties file with
    `user.password.min.length` setting.
-   Password can consist of the following characters:

– Unicode character categorized as an alphanumeric character\
 – Special characters: \~!@\#\$%\^&\*\_-+=\`|\\(){}[]:;”‘\<\>,.?/

## Implementation Notes

-   The database doesn’t store user passwords in plain text, instead
    storing a hashcode of the password in order to protect user
    credentials. Therefore it is not possible to recover a lost
    password, only password change/reset operation is supported.
-   When accessed for the first time after the installation, the
    database presents a web page for configuring the default
    administrator account.\
     This user account is assigned ROLE\_ADMIN role and Full Access
    Permission.
-   To reset the password for a user account, add
    `user.password.reset.username={username}` and
    `user.password.reset.password=secret` settings to server.properties
    file, restart ATSD, and remove the settings from server.properties
    file to prevent resets on subsequent restarts.
-   Users can modify their password by clicking on user icon in the top
    menu.
-   Users are not allowed to change their own role, group membership or
    entity permissions.
-   To disable anonymous access to API, set
    `api.anonymous.access.enabled=false` in server.properties file.
-   There is no user authentication implemented for network command
    received via TCP/UDP.
