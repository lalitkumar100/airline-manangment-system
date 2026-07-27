# Airline Management System

**Simple Java console application for managing flights, bookings, passengers, and related domain objects.**

This repository contains a Java-based console application that models an airline management system. The application is organized into domain models, services that implement business logic, utility helpers (input, validation, id generation, menu UI), and custom exceptions for validation and error handling.

## Stack
- Language: Java (source files under `src/`)
- Runtime: plain Java (no build tool detected such as Maven or Gradle)
- Notable packages: domain models (`model`), services (`service`), utilities (`utility`), exceptions (`exception`)

## Key features
- Create and manage Flights, Routes, Airports, and Aircraft
- Create Bookings and Tickets for Passengers
- Payment and Refund handling
- Loyalty account tracking
- Revenue reporting and streaming task service utilities
- Input validation utilities and ID generation helpers

## Project structure (top-level important files/directories)
```
README.md                       <-- (this file)
.gitignore
Airline Managing System.iml
src/
  Main.java                      <-- application entry (runs Menu)
  com/crimsonlogic/arilinemanangmentsystem/
    exception/                   <-- custom exceptions
      InvalidCrewException.java
      InvalidHumanException.java
      InvalidInputException.java
      RecordNotFoundException.java
      ValidationException.java
    model/                       <-- domain model classes
      Aircraft.java
      Airport.java
      Booking.java
      Crew.java
      Flight.java
      LoyaltyAccount.java
      Passenger.java
      Payment.java
      Refund.java
      RevenueReport.java
      Route.java
      Seat.java
      Ticket.java
    service/                     <-- business logic services
      AirportAndAircraftService.java
      BookingService.java
      FlightService.java
      PassengerService.java
      RevenueReportService.java
      StreamTaskService.java
      TicketSercive.java
    utility/                     <-- helpers and UI
      FlightValidator.java
      IdGenerator.java
      InputUtil.java
      Menu.java
      ValidatorUtil.java
```

Notes:
- The Java source tree uses the package path `com.crimsonlogic.arilinemanangmentsystem` (note: folder name contains the current spelling from the repository).
- I inspected representative files such as `Main.java`, model classes, services, utilities, and exceptions to prepare this README.

## How to build and run
There is no build configuration file (pom.xml or build.gradle) in the repository. To compile and run the project using a standard Java toolchain, use the following steps from the repository root.

Prerequisites:
- Java SDK (JDK) 8+ installed and `javac` / `java` available on PATH.

Compile all .java files and run Main:

```bash
# from repository root
find src -name "*.java" > sources.txt
javac -d out @sources.txt
# run the Main class (use the fully-qualified package name if Main is in a package)
java -cp out Main
```

If `Main.java` is in a package (or the code references a package path), you may need to run it using its fully-qualified class name, for example:

```bash
# example if Main is declared with package com.crimsonlogic.arilinemanangmentsystem;
java -cp out com.crimsonlogic.arilinemanangmentsystem.Main
```

If compilation fails due to package / folder structure mismatches, make sure the directory layout under `src/` matches declared package statements in the .java files.

## Running with an IDE
- Import the project as a plain Java project into IntelliJ IDEA, Eclipse, or VSCode Java extensions. The repository contains an IntelliJ module file (`Airline Managing System.iml`) which may help with IntelliJ.

## Important classes (overview)
- Main / Menu: console entry-point and interactive UI
- Model: Aircraft, Airport, Route, Flight, Seat, Passenger, Booking, Ticket, Payment, Refund, LoyaltyAccount, RevenueReport
- Service: FlightService, BookingService, PassengerService, AirportAndAircraftService, RevenueReportService, StreamTaskService, TicketSercive
- Utility: InputUtil (console input helpers), IdGenerator (unique id creation), ValidatorUtil / FlightValidator
- Exception: ValidationException and specialized exceptions (InvalidInputException, InvalidHumanException, InvalidCrewException), RecordNotFoundException

## use cases Diagram
flowchart TD

flowchart TD

```mermaid
    A([Start]) --> B[Main Menu]

    B -->|1. Admin| C[Admin Menu]
    B -->|2. Passenger| D[Passenger Menu]
    B -->|3. Stream API Tasks| E[Stream Menu]
    B -->|0. Exit| Z([End])

    C -->|1| F[Flight Management]
    C -->|2| G[Booking & Reports]
    C -->|0| B



    F --> F1[Add Flight]
    F --> F2[Update Flight]
    F --> F3[Remove Flight]
    F --> F4[Search Flight]
    F --> F5[Display All Flights]
    F --> F6[Generate Tickets]
    F -->|0| C

    F4 --> FS1[Search By Flight ID]
    F4 --> FS2[Search By Source & Destination]
    F4 --> FS3[Search By Source Destination & Date]
    F4 --> F



    G --> G1[Display All Bookings]
    G --> G2[Display Flight Bookings]
    G --> G3[Search Booking By ID]
    G --> G4[Revenue Report]
    G -->|0| C



    D --> P1[Search Flights]
    D --> P2[Book Ticket]
    D --> P3[Cancel Booking]
    D --> P4[Update Seat]
    D --> P5[Register Passenger]
    D --> P6[Generate Boarding Pass]
    D --> P7[Check In Passenger]
    D -->|0| B


    E --> S1[List Available Flights]
    E --> S2[Filter Flights By Destination]
    E --> S3[Filter Delayed Flights]
    E --> S4[Sort Flights By Departure]
    E --> S5[Highest Ticket Fare]
    E --> S6[Lowest Ticket Fare]
    E --> S7[Average Ticket Fare]
    E --> S8[Group Flights By Destination]
    E --> S9[Sort Passengers By Loyalty]
    E --> S10[Group Passengers By Tier]
    E --> S11[Highest Spending Passenger]
    E --> S12[Count Passengers Per Route]
    E --> S13[Total Ticket Revenue]
    E --> S14[Total Refund Amount]
    E --> S15[Top Revenue Flights]
    E --> S16[Distinct Aircraft Types]
    E --> S17[Earliest Departure]
    E --> S18[Latest Arrival]
    E --> S19[Distinct Destinations]
    E --> S20[Cancelled Bookings]
    E --> S21[Group Bookings By Status]
    E --> S22[Booking Count Per Flight]
    E --> S23[Most Booked Flight]
    E --> S24[Overbooked Flights]
    E --> S25[Empty Flights]
    E --> S26[Fare Summary Statistics]
    E --> S27[All Flights Departed]
    E --> S28[Any Seats Available]
    E --> S29[FlatMap Passengers From Bookings]
    E --> S30[Immutable Flight List]
    E -->|0| B
```
##flowchart LR
```mermaid
    Admin([Admin])
    Passenger([Passenger])

    subgraph Airline Management System

        UC1(Add Flight)
        UC2(Update Flight)
        UC3(Remove Flight)
        UC4(Search Flight)
        UC5(Display All Flights)
        UC6(Generate Tickets)

        UC7(View All Bookings)
        UC8(View Flight Bookings)
        UC9(Find Booking By ID)
        UC10(Revenue Report)

        UC11(Register Passenger)
        UC12(Book Ticket)
        UC13(Cancel Booking)
        UC14(View Ticket)
        UC15(Generate Boarding Pass)
        UC16(Check-In)

    end

    Admin --> UC1
    Admin --> UC2
    Admin --> UC3
    Admin --> UC4
    Admin --> UC5
    Admin --> UC6
    Admin --> UC7
    Admin --> UC8
    Admin --> UC9
    Admin --> UC10

    Passenger --> UC4
    Passenger --> UC11
    Passenger --> UC12
    Passenger --> UC13
    Passenger --> UC14
    Passenger --> UC15
    Passenger --> UC16
```
## Known issues / notes
- There is no build file (Maven/Gradle); the repo uses plain source files. Adding a build system (Maven or Gradle) would make compilation and dependency management easier.
- The package path directory name `arilinemanangmentsystem` appears in the tree; verify package declarations in source files if you reorganize folders.
- I did not find unit tests or test frameworks in the repository.

## Suggested next steps
- Add a `pom.xml` or `build.gradle` to make building and running easier.
- Add a CI workflow to compile and (later) test the project.
- Add example data or a seed script to exercise the application quickly.
- Optionally add a UML/diagram (I can add a Mermaid diagram file if you want).

## Contributing
Contributions are welcome. Suggested workflow:
1. Fork the repository
2. Create a feature branch
3. Run and test your changes locally
4. Open a pull request with a clear description of the change

## Contact / Author
Repository owner: @lalitkumar100 (GitHub)


