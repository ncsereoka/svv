# Svvitch - Software Verification and Validation project

This is an HTTP WebServer written in Java with:

- JUnit 4 unit tests;
- Mockito 3 for tests using mocking;
- Marathon GUI tests for the Swing UI;
- Selenium Web Tests.

I've also included some screenshots of both
[static](https://github.com/ncsereoka/svv/blob/master/static-analysis)
(using *spotbugs* plugin)
and
[dynamic analysis](https://github.com/ncsereoka/svv/blob/master/dynamic-analysis)
(with *JProfiler*)
of the project.

By using a build automation tool, it's much easier to get the JaCoCo test coverage reports.
It has also enabled me to use *spotbugs*, the spiritual successor to the now deprecated/hard to use *FindBugs*.

In this project, I've used Gradle. Why? I have already used Maven, time to try out new things.

## Walkthrough of the project

- Approach, project ideas and beginnings

- Unit tests using JUnit 4

- Mock tests using Mockito

- Marathon GUI tests
  - Start your local copy of Marathon.
  - Open the project folder *gui-tests*.
  - Once the GUI is open, you can select one TestCase file and then press Run to start the test.

- Selenium Web tests
