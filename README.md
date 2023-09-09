This project contains basic setup for cucumber with java and JUnit.
We can run tests in parallel using cucumber test context.

Command for running the tests:
mvn clean verify -DargLine="-DtestEnv=qa -Dbrowser=firefox" -D"cucumber.filter.tags=@ip-info"

    - If browser is null then the default browser is chrome browser.
    - Browser name can be chrome, firefox.

If your tests require browser then include @driver in your feature file. It will open the browser. For api tests it is not necessary, so we don't need to include it.

For api tests, include @api.

3 threads are running parallel. You can change the count from pom.xml file > threadCount.

Currently, I am still working on adding dynamic env.

Lets us know if you have any query or suggestion.
