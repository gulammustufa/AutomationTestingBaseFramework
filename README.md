This project contains basic setup for cucumber with java and JUnit.
We can run tests in parallel using cucumber test context.

Command for running the tests: 
    mvn clean verify -DargLine="-Dbrowser=chrome"

    - If browser is null then the default browser is chrome browser.
    - Browser name can be chrome, firefox.