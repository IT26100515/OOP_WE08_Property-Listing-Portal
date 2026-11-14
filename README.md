# Real Estate Property Listing and Viewing System

## About the Project

This is a real estate website developed as an Object-Oriented Programming project.

The website allows users to view and manage property listings and arrange appointments to view those properties. It has two main parts:

1. **Property Listing Management**
2. **Viewing Appointment Management**

The project uses Java for the backend, JSP for the website pages, and text files to store data.

## Project Objectives

The main objectives of this project are to:

- Manage details of properties available for sale or rent.
- Allow users to view available properties.
- Arrange in-person or virtual property viewings.
- Store property and appointment details in text files.
- Apply Object-Oriented Programming concepts in a practical project.

## Main Features

### 1. Property Listing Management

This part manages the properties displayed on the website.

Users can:

- Add a new property listing.
- View the list of properties.
- View the details of a property.
- Search for properties.
- Edit property information.
- Delete a property when it is not linked to a saved viewing appointment.

The system supports two types of listings:

- **Sale listings**
- **Rental listings**

Property details are stored in `data/listings.txt`.

### 2. Viewing Appointment Management

This part manages appointments for viewing properties.

Users can:

- Request a viewing for an existing property.
- View saved appointments.
- View the details of an appointment.
- Edit an appointment or update its status.
- Cancel an appointment.
- Permanently delete an appointment.

The system supports two types of viewings:

- **In-person viewing** — includes a meeting point.
- **Virtual viewing** — includes an HTTPS meeting link.

Appointment details are stored in `data/viewings.txt`.

## How the Two Parts Work Together

First, a property is added through Property Listing Management.

A user can then select that property and request a viewing appointment. The appointment stores the property's listing ID so that the system knows which property the appointment belongs to.

A property cannot be deleted while a saved viewing appointment refers to it. If an appointment is cancelled, its record remains saved; cancellation and deletion are different actions.

## Technologies Used

| Technology | Purpose |
| --- | --- |
| Java | Backend programming language |
| Spring Boot | Handles the web application and backend requests |
| JSP | Creates the website pages displayed in the browser |
| HTML | Defines the content and structure of the pages |
| CSS | Styles the website |
| Maven | Manages dependencies and builds the project |
| Text files | Store property and appointment records |
| Git and GitHub | Track changes and support collaboration |

## How the Application Works

When a user submits a form on the website:

1. A JSP page displays the form.
2. The controller receives the user's request.
3. The service checks the details and applies the relevant rules.
4. The repository reads or saves the data.
5. The updated result is displayed on the website.

For example, when a user requests a viewing:

```text
Viewing form
    ↓
ViewingController
    ↓
ViewingService
    ↓
ViewingRepository and FileRepository
    ↓
viewings.txt
```

## Data Storage

The project uses text files instead of an SQL database.

```text
data/
├── listings.txt
└── viewings.txt
```

- `listings.txt` stores property listing records.
- `viewings.txt` stores viewing appointment records.

The application automatically creates the data folder and files when required. The records normally remain saved when the application is stopped and can be loaded again when it is restarted from the same project location.

Each saved line contains a record in JSON format inside a `.txt` file.

## Object-Oriented Programming Concepts

### Encapsulation

The model classes keep their data in private fields and provide methods to access the values.

For example, the `Viewing` class stores appointment details such as customer name and appointment time.

### Inheritance

The project uses parent and child classes.

For property listings:

- `SaleListing` extends `Listing`.
- `RentalListing` extends `Listing`.

For viewing appointments:

- `InPersonViewing` extends `Viewing`.
- `VirtualViewing` extends `Viewing`.

### Abstraction

`Listing` and `Viewing` are abstract classes. They define common details and behaviour shared by their child classes.

### Polymorphism

Different child classes can provide their own behaviour.

For example:

- Sale and rental listings can provide different price labels.
- In-person and virtual viewings provide different meeting instructions.

## Project Structure

```text
OOP_WE08_Property-Listing-Portal/
│
├── pom.xml
├── README.md
│
├── data/
│   ├── listings.txt
│   └── viewings.txt
│
└── src/
    └── main/
        ├── java/
        │   └── lk/estate/
        │       ├── domain/
        │       ├── service/
        │       ├── storage/
        │       └── web/
        │
        ├── resources/
        │   ├── application.properties
        │   └── static/css/
        │
        └── webapp/
            └── WEB-INF/views/
                ├── listings/
                ├── viewings/
                └── fragments/
```

The `data` folder is created when the application needs it.

### Main folders

- **domain:** Classes that represent listings, viewings and related data.
- **service:** Application rules and operations.
- **storage:** Reading and writing data to text files.
- **web:** Controllers and form classes that handle website requests.
- **views:** JSP pages displayed to users.
- **static/css:** Website styling.

## Requirements

To run the project, you need:

- Java JDK 17 or 21
- Apache Maven
- A web browser
- An internet connection for Maven's first dependency download

You can check Java and Maven using:

```powershell
java -version
mvn -version
```

## How to Run the Project

### 1. Clone the repository

```powershell
git clone https://github.com/IT26100515/OOP_WE08_Property-Listing-Portal.git
```

### 2. Open the project folder

```powershell
cd OOP_WE08_Property-Listing-Portal
```

Make sure you are in the folder containing `pom.xml`.

### 3. Build the project

```powershell
mvn clean package
```

If the build succeeds, the terminal should show:

```text
BUILD SUCCESS
```

### 4. Start the application

```powershell
java -jar target/estate-demo-1.0.0.war
```

### 5. Open the website

Open a browser and visit:

**Property listings:**

```text
http://127.0.0.1:8080/listings
```

**Viewing appointments:**

```text
http://127.0.0.1:8080/viewings
```

To stop the application, return to the terminal and press:

```text
Ctrl + C
```

## Example Usage

1. Start the application.
2. Open the property listings page.
3. Add a sample property.
4. Open the property details.
5. Request a viewing for the property.
6. Enter a future date and time and the required meeting details.
7. Save the appointment.
8. Open the appointments page to view or update it.
9. Open the `data` folder to see the saved text files.

## Team Responsibilities

| Project part | Responsibility |
| --- | --- |
| Property Listing Management | Property listings, listing types and listing operations |
| Viewing Appointment Management | Viewing appointments, viewing types and appointment operations |

## Notes

- This is a classroom demonstration project.
- The application stores its data locally in text files.
- Use sample information when demonstrating the website.
- The application does not send emails or create video meeting links automatically.
