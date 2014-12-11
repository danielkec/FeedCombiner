FeedCombiner
======

####Compilation
FeedCombiner is standard webapp maven project(tested on Apache Maven 3.2.1,Oracle Java 1.8 and 1.7).
After unpacking just run in the same folder as pom.xml is:
```
mvn install
```
When successfull bulding and testing is finished, packaged war file is prepared at:
```
pom.xml/../target/feedcombiner-1.0-SNAPSHOT.war
```

####Runing
After deploying war file on the GlassFish server (tested on GlassFish 4.1 Web Profile),
visit:
```
${server_ip:port}/feedcombiner/rest/expose
```
Expose rest endpoint returns html UI with overview of the all combined feeds(there are two mock ones after start). The overview page have means to create, delete, modify(have to be confirmed by update button) combined feeds. Every combined feed have hyperlinks to rest endpoints returnig their feed in HTML, JSON or ATOM format. 

####REST Endpoints
|REST Endpoint url|description|
|----------------|--------------|
|http://localhost:8080/rest/overview | XSLT rendered HTML overview|
|http://localhost:8080/rest/overview/html/{feedName}| XSLT rendered HTML feed|
|http://localhost:8080/rest/overview/json/{feedName}| ATOM generated by ROME converted to JSON|
|http://localhost:8080/rest/overview/atom/{feedName}| ATOM generated by ROME|
|http://localhost:8080/rest/manage|Returns list of all combined feeds in JSON|
|http://localhost:8080/rest/manage/create|Creates a new combined feed, query params are: title,descriptions,urls...|
|http://localhost:8080/rest/manage/update|Updates a combined feed, query params are: title,descriptions,urls...|
|http://localhost:8080/rest/manage/delete|Removes combined feed by its name/title, query param is: title|
|http://localhost:8080/rest/timer|Returns time left to the next feeds refresh in seconds|
|http://localhost:8080/rest/timer/getinterval|Returns current interval between refreshes in seconds|
http://localhost:8080/rest/timer/setinterval|Sets new interval in seconds between the feed refreshes, query param is: interval|


