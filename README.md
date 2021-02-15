# SoffanG17
This continuous integration server will compile the code of the repo, 
test it with the specified unit tests and notify the results through
a commit message. The URL for the HTTP requests is acquired through use of ngrok.

#### Run the Continuous Integration server
```
javac -cp servlet-api-2.5.jar:jetty-all-7.0.2.v20100331.jar ContinuousIntegrationServer.java
java -cp .:servlet-api-2.5.jar:jetty-all-7.0.2.v20100331.jar ContinuousIntegrationServer
./ngrok http 8080
```

Create a file named with your personal access token on the first row and put it in src/main/java/.
How to create a token:
https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token

##### Dependancies
- Java 15
- Jupiter JUNIT 5.0
- Jetty Server 11
- Jetty Client 11
- Javax servlet 2.5
- ngrok

##### Commit template
Issue-#[issueNr]-[Feat/Fix/Doc/Refac]-[Title]

##### Branch template
Issue-#[issueNr]-[Feat/Fix/Doc/Refac]-[Title]

##### Pull Requests
Make a Pull Request when an Issue has been completed. The Pull Request has to be reviewed by at least 2 other members and should be linked to the corresponding Issue. When the Pull Request has been merged, the corresponding issue should be closed and the corresponding branch deleted.

###### Pull request naming template
Issue #[issueNr] [Title]  
or  
Issue-#[issueNr]-[Title]

Add "Closes #[issueNr]" to the description or link the issue manually in the GUI.

##### Statement of contributions:
###### Sebastian Fagerlind
Communicating with GitHub API
Remaking code skeleton with updated libs
Documentation & tests


###### Eleonora Borzi
P1

###### Henrik Kultala


###### Nils Merkel
Testing sdfdsfadsfasdsd
