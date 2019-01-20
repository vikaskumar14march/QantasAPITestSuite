# QantasAPITestSuite

Instruction for running REST API Test

1.	Setup Java environment on laptop/desktop. It includes installation of JDK and setting java variable.
2.	Download Maven and setup maven variable
3.	Download eclipse 
4.	Clone the Rest API TestSuite solution from below git urls. 
https://github.com/vikaskumar14march/QantasAPITestSuite.git
git@github.com:vikaskumar14march/QantasAPITestSuite.git

5.	Open eclipse and import maven based project (which was just downloaded or cloned).
6.	User can run the test either through eclipse or without eclipse by using maven command directly.

a.	For running the test from eclipse, right click on below file
testng.xml and run as testng suite
Testing.xml file is configured to run single API class file (WeatherBitAPITest)

b.	For running from outside eclipse, open the command prompt and traverse to project folder where pom.xml is present and run below command
Mvn test
pom.xml file is configured to invoke testing.xml which runs single API test
(First time maven run will kick off jar dependency download which might take few minutes)

7.	After test is run, result report can be opened from below location.
\test-output\ExtentReportsTestNG.html

 
