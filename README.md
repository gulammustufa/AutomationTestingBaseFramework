This project contains basic setup for cucumber with java and JUnit 5+.
We can run tests in parallel using cucumber test context.

Command for running the tests:
mvn clean verify -DargLine="-DtestEnv=qa -Dbrowser=chrome" -D"cucumber.filter.tags= not @graphQL"

    - If browser is null then the default browser is chrome browser.
    - Browser name can be chrome, firefox.

If your tests require browser then include @driver in your feature file. It will open the browser. For api tests it is not necessary, so we don't need to include it.

For api tests, include @api.

5 threads are running parallel. You can change the count from junit-platform.properties file.

I have updated the Junit version. Now all scenarios are working parallel.

Lets us know if you have any query or suggestion.

**Steps for running only failed test cases:**
1. First of all copy the previous report to different location. For that copy target > cucumber-html-reports to another location.

   There are two ways to run it.
    1. Add tag manually
    - Add @failed tag in the failed scenarios.
    2. Run src/test/java/utility/AddFailedTagInFeatureFiles.java file.
    - It will add tag automatically.
    - Ex. `java src/test/java/utility/AddFailedTagInFeatureFiles.java`
   