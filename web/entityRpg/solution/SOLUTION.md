## Solution

First we need to get the password of which is stored in `/app/home/password.txt` to do this we will use XXE vulnerability, but there is a tricky part that we can not use phrases like .dtd or everything what matches regex like `:\/\/.*\/`
We will just set a simple http server with python script and the script will send malicious data to server. The URL in XML will be without last slash so it will not match regex protection.

- run solution.py // Set IP and collaborator in script first
- run collaborator
- upload malicious.xml

Next step is to create a xml file with a high character statistics which will be enougth to kill the boss. From last step we got password which is `ADMINCTFPASSWORD`. Now we can use it! Simple way is to go to https://8gwifi.org/CipherFunctions.jsp choose AES and write stats that we want, in format attack:health:currHealth so lets say we want `999999999:999999999:999999999` encrypt this data with AES and our password we received before, set characterHash and upload XML! The example of this XML is in solution folder and it is named hacker.xml
There is a lot of hint in error pages which will be helpful to know how the data is encrypted etc.

Now we can Kill boss and grap the flag!
