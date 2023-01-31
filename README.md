# Book Manager

This project manage book objects and expose rest APIs on it.

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8181 in your browser.

## Using the application

Once started the application redirect to main page (on root path) which is login page.
Username must exists on db to make login.
At now the username which can be used for login is:
username: f.morisi
password: tffweàf#@4


Once you've logged in the application redirect to book manager page in which you can make CRUD operations on this object

N.B. Database instance (PostgresQL) is on https://customer.elephantsql.com/instance and is always running.

## Using rest API
**User** objects can be manager with "/users" api
**Book** objects can be managed with "/books" api

All APIs required a Basic authentication with these credentials: 
 username: default_api_user
 password: evxami9gwe@

You can find API collection (for Insomnia client) on file "Insomnia-collection.json"


## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/mytodo-1.0-SNAPSHOT.jar`
