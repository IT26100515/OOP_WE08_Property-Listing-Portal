# Real Estate Project

One Java Spring Boot MVC application with JSP/JSTL pages and text-file storage.

## Responsibilities

- Property Listing Management: listing member.
- Viewing Appointment Management: viewing member (the original requester).
- Shared foundation and integration tests: coordinated entry once and joint review.

## Requirements and final startup

JDK 17 or 21, Maven 3.9+, Git and a browser. Open pom.xml as a Maven project in IntelliJ if using the IDE.

After both modules and test classes are integrated:

```text
mvn clean verify
java -jar target/estate-demo-1.0.0.war
```

Use local host 127.0.0.1, port 8080, path /listings or /viewings. Create a property before requesting a viewing. Stop the server with Ctrl+C.

## Data and rules

The application creates data/listings.txt and data/viewings.txt automatically. Each UTF-8 line is one JSON record. Restart from the same working directory or select the same app.data-dir argument. Runtime data and target/ are ignored.

All viewing times are Asia/Colombo local values, at :00 or :30. New/rescheduled viewings must be future times. Requested/confirmed appointments reserve slots; completed/cancelled records cannot be edited. Any retained viewing blocks its property's deletion.

This is a local single-JVM classroom demonstration without authentication. Use fictional data. It does not send email or create external video meetings.

## Branches and development

- main: shared integration branch.
- feature/property-listings: listing member's work.
- feature/viewing-appointments: viewing member's work.

Record actual implementation, checks and reviews under each person's own identity. The two members share one project and agree before changing shared files. The setup milestone compiles/packages; feature pages and tests are introduced during later milestones.