# Svvitch - Software Verification and Validation project

This is an HTTP WebServer written in Java with:

- JUnit 4 unit tests;
- Mockito 3 for tests using mocking;
- Marathon GUI tests for the Swing UI;
- Selenium Web Tests.

We've also included some screenshots of both
[static](https://github.com/ncsereoka/svv/blob/master/static-analysis)
(using the *SpotBugs* plugin)
and
[dynamic analysis](https://github.com/ncsereoka/svv/blob/master/dynamic-analysis)
(with *JProfiler*)
of the project.

By using a build automation tool, it's much easier to get the JaCoCo test coverage reports.
It has also enabled me to use *SpotBugs*, the spiritual successor to the now deprecated/hard to use *FindBugs*.

In this project, we've used Gradle. Why? We have already used Maven, time to try out new things.

## Walkthrough of the project

- Approach, project ideas and beginnings
  - We've started from the given example of the HttpServer and a basic Gradle project;
  - Slowly carved out individual classes, abstractions of a particular functionality from the control flow, such as `HttpConnection` and other Runnables;
  - Then, we mvoved on to actual parsing of requests. We have application skeleton up,and we can start using TDD for the parsing of the contents HTTP requests;
  - We will import the given test site which we will use later on;
  - We also start working on the GUI, adapting it to the design from the example;
  - We create a Component Map for global lookup of relevant components;
  - Create a 'main' class which will handle the state of both the server and the GUI;
  - Set up controls and consequences of control actions on GUI and server state;
  - Created configuration management classes;
  - Allowed only valid values for the ports/configuration folders;
  - Added tests for application state;
  - Refactored to have asynchronous behaviour in the server;
  - Configuration persistence;
  - Request parsing tests;
  - Valid configuration test;
  - Mockito is great at mocking the behaviour of the server/client sockets;
  - Try out CSS and index resolving;
  - Added SpotBugs;
  - Added JaCoCo reports;
  - Increased codebase coverage;
  - Added GUI tests;
  - Added Web tests.

## Structure

We've landed on the following structure:

- the `Svvitch` application main class, responsible for reading the configuration file and starting everything up;
- `Configuration` (and its loader) class which represents the current global state of the application, i.e. relevant values regarding folder variables and status of the server (RUNNING, STOPPED OR MAINTENANCE);
- Interface classes and event listeners taking care of the GUI (creating the Swing component hierarchy, server interaction);
- Server classes, encapsulating different processes from the protocol: connection handling, request handling, request parsing, response and overall state management;
- Utility classes, used for validating and file interaction.

## Unit tests using JUnit 4
  
- Configuration
  - configuration file related;
  - valid values;
  - persistence.
- Request parsing
  - validity;
  - methods;
  - headers;
  - url.
- Resource loading
- File loading
- Port validation

## Mock tests using Mockito

Useful to 'mock out' annoying/irrelevant behavior from control flow

- mocking interface loading to test application startup;
- mocking events to test listeners;
- mocking socket connection data to test connection handling and server runtime.

## Marathon GUI tests

- Start your local copy of Marathon.
- Open the project folder *gui-tests*.
- Once the GUI is open, you can select one TestCase file and then press Run to start the test.

## Selenium Web tests

## Live Demo

- `gradle build` to show whole process
- `gradle jar` to generate the JAR file
- We are going to copy the resulting artifact into the main directory, in order to use the *www* and *maintenance* folders. We'll also create a custom configuration file `config.properties` with our configuration values.
- Depending on the configuration, the server will now start with or without a GUI.
