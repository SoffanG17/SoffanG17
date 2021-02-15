# SoffanG17
This continuous integration server will compile the code of the repo, 
test it with the specified unit tests and notify the results through
a commit message. The URL for the HTTP requests is acquired through use of ngrok.

#### Run the Continuous Integration server
```
cd [your project root path]
mvn compile
mvn exec:java
ngrok http 8080
```
Create a file named token.txt with your personal access token on the first row and put it in src/main/java/.
How to create a token:
https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token

#### Make the build-history availible through http
```
cd [your project root path]/history
python3 -m http.server 8000
```
A list of  builds will be availible at http://[server-address]:8000

Each build will be stored as a .txt-file with the following naming: yyyy_mm_dd_hh_mm_ss.txt. 
OBS! numbers are not padded, eg. the fist of January will be 1_1, NOT 01_01.

Each build can be accessed directly by http://[server-address]:8000/yyyy_mm_dd_hh_mm_ss.txt
OBS! numbers are not padded, eg. the fist of January will be 1_1, NOT 01_01.

##### Dependancies
- Java 15
- Maven 3.6.3
- ngrok 2.3.35
- Python 3.xx

Observe that Maven must be in your PATH environment variable and the path to Maven must additionally be specified manually in the Compiler.java file.

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
- Communicating with GitHub API
- Remaking code skeleton with updated libs
- Documentation & tests

###### Eleonora Borzi
- Coding
- Testing
- Build automation
- P1

###### Henrik Kultala
- Github management
- Supervisor
- Wrangling Maven
- Test automation
- Coding
- Testing

###### Nils Merkel
- Coding
- Testing
- Build automation
- P1
